package allthings.iot.dos.monitor.job;

import allthings.iot.dos.constant.Constants;
import allthings.iot.dos.dto.IotDosServiceInfoDto;
import allthings.iot.dos.monitor.producer.ServiceInfoProducer;
import allthings.iot.dos.monitor.utils.InfoUtil;
import allthings.iot.dos.monitor.utils.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.sun.management.OperatingSystemMXBean;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.NetworkIF;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryType;
import java.lang.management.RuntimeMXBean;
import java.math.BigDecimal;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @author :  txw
 * @FileName :  MonitorJob
 * @CreateDate :  2019/5/7
 * @Description :  dmp
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
@Component
@EnableScheduling
public class MonitorJob {

    private static final Logger LOGGER = LoggerFactory.getLogger(MonitorJob.class);
    private static final int KB = 1024;
    private static final int MB = 1024 * 1024;
    private static final int GB = 1024 * 1024 * 1024;
    DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
    @Autowired
    private ServiceInfoProducer serviceInfoProducer;
    @Value("${server.port}")
    private String port;
    @Value("${iot.service.level}")
    private Integer serviceLevels;
    @Value("${iot.service.testUrl:test}")
    private String testUrl;

    //    @Autowired
//    private ChannelCache channelCache;
    @Value("${iot.service.code}")
    private String serviceCode;

    @Scheduled(cron = "*/2 * * * * ?")
    public void getSystemInfo() throws UnknownHostException {
        IotDosServiceInfoDto iotDosServiceInfoDto = new IotDosServiceInfoDto();
        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();
        RuntimeMXBean bean = ManagementFactory.getRuntimeMXBean();

        getJvmInfo(iotDosServiceInfoDto);
        getDiskInfo(iotDosServiceInfoDto);
        getNetWorkInfo(iotDosServiceInfoDto, hal.getNetworkIFs());

        long startTime = bean.getStartTime();
        iotDosServiceInfoDto.setRunTime(dateTimeFormatter.print(bean.getStartTime()));
        iotDosServiceInfoDto.setDiffTime(getDatePoor(startTime, System.currentTimeMillis()));

        Map<String, String> manifestProperty = InfoUtil.getManifestProperty();
//        LOGGER.info("读取maven构建信息--------->>" + JSON.toJSONString(manifestProperty));
        iotDosServiceInfoDto.setTitle(manifestProperty.get("Implementation-Title"));
        iotDosServiceInfoDto.setVersion(manifestProperty.get("Implementation-Version"));
        iotDosServiceInfoDto.setBuildTime(manifestProperty.get("build-time"));

//        LOGGER.info("读取cpu信息---------");
        iotDosServiceInfoDto.setCpu(formatDouble(hal.getProcessor().getSystemCpuLoad() * 100));

//        iotDosServiceInfoDto.setCpu((double) InfoUtil.getCpu());
//        iotDosServiceInfoDto.setNetSpeed((double) InfoUtil.getNetSpeed());

        iotDosServiceInfoDto.setIp(InfoUtil.getLocalHostLANAddress().getHostAddress());
        iotDosServiceInfoDto.setReportTime(System.currentTimeMillis());
        iotDosServiceInfoDto.setPort(port);

        serviceInfoProducer.sendToQueue(iotDosServiceInfoDto, Constants.IOT_DOS_SERVICE_INFO_ALL);
    }

    @Scheduled(cron = "0 */1 * * * ?")
    public void getPlatFormStatus() {
        if (serviceLevels == 2 && !"test".equals(testUrl)) {
            IotDosServiceInfoDto iotDosServiceInfoDto = new IotDosServiceInfoDto();
            List<String> platformStatus = new ArrayList<>();
            String[] split = testUrl.split("\\|");
            for (String str : split) {
                Boolean status = HttpUtil.getJSONTest(str.split(",")[1]);
                String statusInfo = str.split(",")[0] + "|" + status;
                platformStatus.add(statusInfo);
            }
            iotDosServiceInfoDto.setPlatform(platformStatus);
            iotDosServiceInfoDto.setServiceCode(serviceCode);
            serviceInfoProducer.sendToQueue(iotDosServiceInfoDto, Constants.IOT_DOS_PLATFORM_STATUS);
            LOGGER.info("上报第三方平台信息完成------>>" + JSON.toJSONString(iotDosServiceInfoDto));
        }
    }

    /**
     * 获取jvm信息
     *
     * @param iotDosServiceInfoDto
     */
    private void getJvmInfo(IotDosServiceInfoDto iotDosServiceInfoDto) {
        double heap = 0, heapRatio = 0, heapMax = 0, nonHeap = 0, nonHeapRatio = 0, nonHeapMax = 0;

        List<MemoryPoolMXBean> platformMXBeans = ManagementFactory.getPlatformMXBeans(MemoryPoolMXBean.class);
        for (MemoryPoolMXBean memoryPoolBean : platformMXBeans) {
            if (MemoryType.HEAP.equals(memoryPoolBean.getType())) {
                heapMax += memoryPoolBean.getUsage().getMax();
                heap += memoryPoolBean.getUsage().getUsed();
            } else {
                nonHeapMax += memoryPoolBean.getUsage().getMax();
                nonHeap += memoryPoolBean.getUsage().getUsed();
            }
        }

        heapRatio = heap / heapMax;
        nonHeapRatio = nonHeap / nonHeapMax;
        iotDosServiceInfoDto.setHeap(formatDouble(heap / MB));
        iotDosServiceInfoDto.setHeapMax(formatDouble(heapMax / MB));
        iotDosServiceInfoDto.setHeapRatio(formatDouble(heapRatio * 100));
        iotDosServiceInfoDto.setNonHeap(formatDouble(nonHeap / MB));
        iotDosServiceInfoDto.setNonHeapMax(formatDouble(nonHeapMax / MB));
        iotDosServiceInfoDto.setNonHeapRatio(formatDouble(nonHeapRatio * 100));
    }

    /**
     * 获取内存及磁盘信息
     *
     * @param iotDosServiceInfoDto
     */
    private void getDiskInfo(IotDosServiceInfoDto iotDosServiceInfoDto) {
        OperatingSystemMXBean osMxBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

        double totalDiskSpace = 0, usableDiskSpace = 0;
        long totalRAM = 0, avaliableRAM = 0;
        File[] disks = File.listRoots();
        for (File file : disks) {
            usableDiskSpace += file.getUsableSpace() * 1.0 / GB;
            totalDiskSpace += file.getTotalSpace() * 1.0 / GB;
        }
        iotDosServiceInfoDto.setTotalDiskSpace(formatDouble(totalDiskSpace));
        iotDosServiceInfoDto.setUsableDiskSpace(formatDouble(usableDiskSpace));

        totalRAM = osMxBean.getTotalPhysicalMemorySize() / MB;
        avaliableRAM = osMxBean.getFreePhysicalMemorySize() / MB;
        iotDosServiceInfoDto.setTotalRAM(totalRAM);
        iotDosServiceInfoDto.setAvaliableRAM(avaliableRAM);
    }

    /**
     * 获取网络信息
     *
     * @param iotDosServiceInfoDto
     * @param networkIFs
     */
    private void getNetWorkInfo(IotDosServiceInfoDto iotDosServiceInfoDto, NetworkIF[] networkIFs) {
//        String os = System.getProperty("os.name").toLowerCase();
//        LOGGER.info("操作系统：{}", os);
//        if (os.indexOf("windows") < 0) {
//            float totalBandwidth = 1000;
//            float netUsage = 0.0f;
//            Process pro1, pro2;
//            Runtime r = Runtime.getRuntime();
//            try {
//                String command = "cat /proc/net/dev";
//                //第一次采集流量数据
//                long startTime = System.currentTimeMillis();
//                pro1 = r.exec(command);
//                BufferedReader in1 = new BufferedReader(new InputStreamReader(pro1.getInputStream()));
//                String line = null;
//                long inSize1 = 0, outSize1 = 0;
//                while ((line = in1.readLine()) != null) {
//                    line = line.trim();
//                    if (line.startsWith("eth0")) {
//                        String[] temp = line.split("\\s+");
//                        inSize1 = Long.parseLong(temp[0].substring(5));    //Receive bytes,单位为Byte
//                        outSize1 = Long.parseLong(temp[8]);                //Transmit bytes,单位为Byte
//                        break;
//                    }
//                }
//                in1.close();
//                pro1.destroy();
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    StringWriter sw = new StringWriter();
//                    e.printStackTrace(new PrintWriter(sw));
////                log.error("NetUsage休眠时发生InterruptedException. " + e.getMessage());
////                log.error(sw.toString());
//                }
//                //第二次采集流量数据
//                long endTime = System.currentTimeMillis();
//                pro2 = r.exec(command);
//                BufferedReader in2 = new BufferedReader(new InputStreamReader(pro2.getInputStream()));
//                long inSize2 = 0, outSize2 = 0;
//                while ((line = in2.readLine()) != null) {
//                    line = line.trim();
//                    if (line.startsWith("eth0")) {
//                        String[] temp = line.split("\\s+");
//                        inSize2 = Long.parseLong(temp[0].substring(5));
//                        outSize2 = Long.parseLong(temp[8]);
//                        break;
//                    }
//                }
//                if (inSize1 != 0 && outSize1 != 0 && inSize2 != 0 && outSize2 != 0) {
//                    float interval = (float) (endTime - startTime) / 1000;
//                    //网口传输速度,单位为bps
//                    float curRate = (float) (inSize2 - inSize1 + outSize2 - outSize1) * 8 / (1000000 * interval);
//                    netUsage = curRate / totalBandwidth;
//                    iotDosServiceInfoDto.setNetSpeed((double) curRate);
//                }
//                in2.close();
//                pro2.destroy();
//            } catch (IOException e) {
//                StringWriter sw = new StringWriter();
//                LOGGER.warn("获取网络速率异常：" + e);
//            }
//        } else {
        double received = 0;
        double transmitted = 0;
        double speed = 0;
        for (NetworkIF net : networkIFs) {
            boolean hasData = net.getBytesRecv() > 0 || net.getBytesSent() > 0 || net.getPacketsRecv() > 0
                    || net.getPacketsSent() > 0;
            received = received + (hasData ? net.getBytesRecv() : 0) * 1.0 / MB;
            transmitted = transmitted + (hasData ? net.getBytesSent() : 0) * 1.0 / MB;
            if (net.getNetworkInterface().getDisplayName().indexOf("Virtual") == -1) {
                speed += net.getSpeed();
            }
        }
        iotDosServiceInfoDto.setNetSpeed(formatDouble(speed * 1.0 / MB));
//        }
    }


    /**
     * 计算时间差
     *
     * @param startDate
     * @param nowDate
     * @return
     */
    private String getDatePoor(Long startDate, Long nowDate) {

        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // 获得两个时间的毫秒时间差异
        long diff = nowDate - startDate;
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        return day + "天" + hour + "小时" + min + "分钟";
    }

    /**
     * 格式化double
     *
     * @param num
     * @param scale
     * @return
     */
    private double formatDouble(double num, int scale) {
        return new BigDecimal(num).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    private double formatDouble(double num) {
        return formatDouble(num, 1);
    }


    /**
     * 格式化float
     *
     * @param num
     * @param scale
     * @return
     */
    private float formatFloat(float num, int scale) {
        return new BigDecimal(num).setScale(scale, BigDecimal.ROUND_HALF_UP).floatValue();
    }

    private float formatFloat(float num) {
        return formatFloat(num, 1);
    }

}

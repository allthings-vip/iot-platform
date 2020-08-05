package allthings.iot.dos.monitor.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ClassUtils;
import org.springframework.util.FileCopyUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author :  txw
 * @FileName :  InfoUtil
 * @CreateDate :  2019/5/8
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
public class InfoUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(InfoUtil.class);
    private final static float TotalBandwidth = 1000;    //网口带宽,Mbps

    /**
     * 读取MANIFEST.MF文件
     *
     * @return
     */
    public static Map<String, String> getManifestProperty() {
        Map<String, String> property = new HashMap<>();
        JarFile jarFile = null;
        String content = "";
        try {
            // 获取jar的运行路径，因linux下jar的路径为”file:/app/.../test
            // .jar!/BOOT-INF/class!/“这种格式，所以需要去掉”file:“和”!/BOOT-INF/class!/“
            String jarFilePath = ClassUtils.getDefaultClassLoader().getResource("").getPath().replace("!/BOOT-INF" +
                    "/classes!/", "");
            if (jarFilePath.startsWith("file")) {
                jarFilePath = jarFilePath.substring(5);
            }
            // 通过JarFile的getJarEntry方法读取META-INF/MANIFEST.MF
            jarFile = new JarFile(jarFilePath);
            JarEntry entry = jarFile.getJarEntry("META-INF/MANIFEST.MF");
            // 如果读取到MANIFEST.MF文件内容，则转换为string
            if (entry != null) {
                StringWriter writer = new StringWriter();
                FileCopyUtils.copy(new InputStreamReader(jarFile.getInputStream(entry), "utf-8"), writer);
                jarFile.close();
                content = writer.toString();
            } else {
                return property;
            }
        } catch (Exception ex) {
            return property;
        } finally {
            try {
                if (jarFile != null) {
                    jarFile.close();
                }
            } catch (IOException e) {
                return property;
            }
        }
        return getProperty(content);
    }

    /**
     * 通过字符解析，MANIFEST.MF 文件中的属性值
     *
     * @param content MANIFEST.MF 文件中的字符串
     * @return
     */
    private static Map<String, String> getProperty(String content) {
//        LOGGER.info("读取manifest.mf----->>" + content);
        Map<String, String> result = new HashMap<>();
        if ("".equals(content.trim())) {
            return result;
        }
        String[] propertys = content.split("\n");
        if (propertys == null || propertys.length == 0) {
            return result;
        }
        String[] temp;
        try {
            for (int i = 0; i < propertys.length; i++) {
                temp = propertys[i].split(": ");
                if (temp.length == 2) {
                    result.put(temp[0].trim(), temp[1].trim());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取本机网络信息
     *
     * @return
     * @throws UnknownHostException
     */
    public static InetAddress getLocalHostLANAddress() throws UnknownHostException {
        try {
            InetAddress candidateAddress = null;
            // 遍历所有的网络接口
            for (Enumeration ifaces = NetworkInterface.getNetworkInterfaces(); ifaces.hasMoreElements(); ) {
                NetworkInterface iface = (NetworkInterface) ifaces.nextElement();
                // 在所有的接口下再遍历IP
                for (Enumeration inetAddrs = iface.getInetAddresses(); inetAddrs.hasMoreElements(); ) {
                    InetAddress inetAddr = (InetAddress) inetAddrs.nextElement();
                    // 排除loopback类型地址
                    if (!inetAddr.isLoopbackAddress()) {
                        if (inetAddr.isSiteLocalAddress()) {
                            // 如果是site-local地址，就是它了
                            return inetAddr;
                        } else if (candidateAddress == null) {
                            // site-local类型的地址未被发现，先记录候选地址
                            candidateAddress = inetAddr;
                        }
                    }
                }
            }
            if (candidateAddress != null) {
                return candidateAddress;
            }
            // 如果没有发现 non-loopback地址.只能用最次选的方案
            InetAddress jdkSuppliedAddress = InetAddress.getLocalHost();
            if (jdkSuppliedAddress == null) {
                throw new UnknownHostException("The JDK InetAddress.getLocalHost() method unexpectedly returned null.");
            }
            return jdkSuppliedAddress;
        } catch (Exception e) {
            UnknownHostException unknownHostException = new UnknownHostException(
                    "Failed to determine LAN address: " + e);
            unknownHostException.initCause(e);
            throw unknownHostException;
        }
    }

    /**
     * @param
     * @return float, 网络带宽使用率, 小于1
     * @Purpose:采集网络带宽使用率
     */
    public static float getNetSpeed() {
        float netUsage = 0.0f;
        Process pro1, pro2;
        Runtime r = Runtime.getRuntime();
        try {
            String command = "cat /proc/net/dev";
            //第一次采集流量数据
            long startTime = System.currentTimeMillis();
            pro1 = r.exec(command);
            BufferedReader in1 = new BufferedReader(new InputStreamReader(pro1.getInputStream()));
            String line = null;
            long inSize1 = 0, outSize1 = 0;
            while ((line = in1.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("eth0")) {
                    String[] temp = line.split("\\s+");
                    inSize1 = Long.parseLong(temp[0].substring(5));    //Receive bytes,单位为Byte
                    outSize1 = Long.parseLong(temp[8]);                //Transmit bytes,单位为Byte
                    break;
                }
            }
            in1.close();
            pro1.destroy();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                StringWriter sw = new StringWriter();
                e.printStackTrace(new PrintWriter(sw));
            }
            //第二次采集流量数据
            long endTime = System.currentTimeMillis();
            pro2 = r.exec(command);
            BufferedReader in2 = new BufferedReader(new InputStreamReader(pro2.getInputStream()));
            long inSize2 = 0, outSize2 = 0;
            while ((line = in2.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("eth0")) {
                    String[] temp = line.split("\\s+");
                    inSize2 = Long.parseLong(temp[0].substring(5));
                    outSize2 = Long.parseLong(temp[8]);
                    break;
                }
            }
            if (inSize1 != 0 && outSize1 != 0 && inSize2 != 0 && outSize2 != 0) {
                float interval = (float) (endTime - startTime) / 1000;
                //网口传输速度,单位为bps
                float curRate = (float) (inSize2 - inSize1 + outSize2 - outSize1) * 8 / (1000000 * interval);
                netUsage = curRate / TotalBandwidth;
//                log.info("本节点网口速度为: " + curRate + "Mbps");
//                log.info("本节点网络带宽使用率为: " + netUsage);
            }
            in2.close();
            pro2.destroy();
        } catch (IOException e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
        }
        return netUsage * TotalBandwidth;
    }


    public static float getCpu() {
        float cpuUsage = 0;
        Process pro1, pro2;
        Runtime r = Runtime.getRuntime();
        try {
            String command = "cat /proc/stat";
            //第一次采集CPU时间
            long startTime = System.currentTimeMillis();
            pro1 = r.exec(command);
            BufferedReader in1 = new BufferedReader(new InputStreamReader(pro1.getInputStream()));
            String line = null;
            long idleCpuTime1 = 0, totalCpuTime1 = 0;    //分别为系统启动后空闲的CPU时间和总的CPU时间
            while ((line = in1.readLine()) != null) {
                if (line.startsWith("cpu")) {
                    line = line.trim();
                    String[] temp = line.split("\\s+");
                    idleCpuTime1 = Long.parseLong(temp[4]);
                    for (String s : temp) {
                        if (!s.equals("cpu")) {
                            totalCpuTime1 += Long.parseLong(s);
                        }
                    }
                    break;
                }
            }
            in1.close();
            pro1.destroy();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                StringWriter sw = new StringWriter();
                e.printStackTrace(new PrintWriter(sw));
            }
            //第二次采集CPU时间
            long endTime = System.currentTimeMillis();
            pro2 = r.exec(command);
            BufferedReader in2 = new BufferedReader(new InputStreamReader(pro2.getInputStream()));
            long idleCpuTime2 = 0, totalCpuTime2 = 0;    //分别为系统启动后空闲的CPU时间和总的CPU时间
            while ((line = in2.readLine()) != null) {
                if (line.startsWith("cpu")) {
                    line = line.trim();
                    String[] temp = line.split("\\s+");
                    idleCpuTime2 = Long.parseLong(temp[4]);
                    for (String s : temp) {
                        if (!s.equals("cpu")) {
                            totalCpuTime2 += Long.parseLong(s);
                        }
                    }
                    break;
                }
            }
            if (idleCpuTime1 != 0 && totalCpuTime1 != 0 && idleCpuTime2 != 0 && totalCpuTime2 != 0) {
                cpuUsage = 1 - (float) (idleCpuTime2 - idleCpuTime1) / (float) (totalCpuTime2 - totalCpuTime1);
            }
            in2.close();
            pro2.destroy();
        } catch (IOException e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
        }
        return cpuUsage;
    }

}

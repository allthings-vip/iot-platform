package allthings.iot.dos.monitor.job;

import com.alibaba.fastjson.JSON;
import allthings.iot.dos.constant.Constants;
import allthings.iot.dos.dto.IotDosServiceInfoDto;
import allthings.iot.dos.monitor.producer.ServiceInfoProducer;
import allthings.iot.dos.monitor.utils.InfoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author :  txw
 * @FileName :  SimpleInfoProducter
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
@Component
public class SimpleInfoJob implements ApplicationRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleInfoJob.class);

    @Autowired
    private ServiceInfoProducer serviceInfoProducer;

    @Value("${iot.service.level}")
    private Integer serviceLevels;

    @Value("${iot.service.code}")
    private String serviceCode;

    @Value("${server.port}")
    private String port;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        IotDosServiceInfoDto iotDosServiceInfoDto = new IotDosServiceInfoDto();
        Map<String, String> manifestProperty = InfoUtil.getManifestProperty();

        iotDosServiceInfoDto.setIp(InfoUtil.getLocalHostLANAddress().getHostAddress());
        iotDosServiceInfoDto.setTitle(manifestProperty.get("Implementation-Title"));
        iotDosServiceInfoDto.setReportTime(System.currentTimeMillis());

        iotDosServiceInfoDto.setLevels(serviceLevels);
        iotDosServiceInfoDto.setPort(port);
        iotDosServiceInfoDto.setServiceCode(serviceCode);
        serviceInfoProducer.sendToQueue(iotDosServiceInfoDto, Constants.IOT_DOS_SERVICE_INFO_SIMPLE);
        LOGGER.info("上报本机服务信息完成------>>" + JSON.toJSONString(iotDosServiceInfoDto));
    }
}

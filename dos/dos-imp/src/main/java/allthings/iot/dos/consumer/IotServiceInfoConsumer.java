package allthings.iot.dos.consumer;

import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.IotDosBizConfig;
import allthings.iot.dos.api.IotMonitorService;
import allthings.iot.dos.constant.Constants;
import allthings.iot.dos.dto.IotDosServiceInfoDto;
import allthings.iot.dos.dto.IotExternalDevicePlatformDTO;
import allthings.iot.dos.dto.IotServiceDTO;
import allthings.iot.util.redis.ICentralCacheService;
import allthings.iot.util.rocketmq.IConsumer;
import allthings.iot.util.rocketmq.msg.IRocketMsgListener;
import allthings.iot.util.rocketmq.msg.RocketMsg;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * @author :  txw
 * @FileName :  IotServiceInfoConsumer
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
public class IotServiceInfoConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(IotServiceInfoConsumer.class);

    @Autowired
    private IotDosBizConfig iotDosBizConfig;

    private IConsumer consumer;

    @Autowired
    private ICentralCacheService cache;

    @Autowired
    private IotMonitorService iotMonitorService;

    private void handleMessage(String content) {
        IotDosServiceInfoDto iotDosServiceInfoDto = JSON.parseObject(content, IotDosServiceInfoDto.class);
        if (iotDosServiceInfoDto == null || StringUtils.isEmpty(iotDosServiceInfoDto.getIp())) {
            return;
        }
        cache.putMapValue(Constants.IOT_DOS_SERVICE_INFO, iotDosServiceInfoDto.getIp() + ":" +
                iotDosServiceInfoDto.getPort(), JSON.toJSONString(iotDosServiceInfoDto));
    }

    private void handleSimpleMessage(String content) {
        IotDosServiceInfoDto iotDosServiceInfoDto = JSON.parseObject(content, IotDosServiceInfoDto.class);
        if (iotDosServiceInfoDto == null || StringUtils.isEmpty(iotDosServiceInfoDto.getIp())) {
            return;
        }

        IotServiceDTO iotServiceDTO = iotMonitorService.getIotServiceByIPAndPort(iotDosServiceInfoDto.getIp(),
                iotDosServiceInfoDto.getPort()).getData();
        if (iotServiceDTO == null) {
            iotServiceDTO = new IotServiceDTO();
            iotServiceDTO.setCreateOperatorId(0L);
            iotServiceDTO.setModifyOperatorId(0L);
            iotServiceDTO.setIp(iotDosServiceInfoDto.getIp());
            iotServiceDTO.setPort(iotDosServiceInfoDto.getPort());
        }

        iotServiceDTO.setLevels(iotDosServiceInfoDto.getLevels());
        iotServiceDTO.setServiceName(iotDosServiceInfoDto.getServiceCode());
        iotServiceDTO.setServiceCode(iotDosServiceInfoDto.getServiceCode());
        iotServiceDTO.setStatus(true);

        iotMonitorService.saveService(iotServiceDTO);
    }

    private void handlePlatFormMessage(String content) {
        IotDosServiceInfoDto iotDosServiceInfoDto = JSON.parseObject(content, IotDosServiceInfoDto.class);
        if (iotDosServiceInfoDto == null) {
            return;
        }

        List<String> platform = iotDosServiceInfoDto.getPlatform();
        for (String str : platform) {
            String code = str.split("\\|")[0];
            Boolean status = Boolean.parseBoolean(str.split("\\|")[1]);
            ResultDTO<IotExternalDevicePlatformDTO> bizReturn = iotMonitorService.getPlatFormByCode(code);
            if (!bizReturn.isSuccess()) {
                continue;
            }
            IotExternalDevicePlatformDTO platformDTO = bizReturn.getData();

            IotExternalDevicePlatformDTO platformInfo = new IotExternalDevicePlatformDTO();
            if (platformDTO == null) {
                continue;
            } else {
                BeanUtils.copyProperties(platformDTO, platformInfo);
            }
            platformInfo.setStampDate(null);
            if (platformInfo.getStatus() != null && platformInfo.getStatus().equals(status) &&
                    !StringUtils.isEmpty(platformInfo.getDependencyService()) &&
                    platformInfo.getDependencyService().equals(iotDosServiceInfoDto.getServiceCode())) {
                continue;
            }

            platformInfo.setDependencyService(iotDosServiceInfoDto.getServiceCode());
            platformInfo.setStatus(status);
            iotMonitorService.savePlatForm(platformInfo);
        }

        ResultDTO<List<IotExternalDevicePlatformDTO>> bizRet =
                iotMonitorService.getPlatFormByDependencyService(iotDosServiceInfoDto.getServiceCode());
        List<IotExternalDevicePlatformDTO> platforms = new ArrayList<>();
        if (bizRet.isSuccess()) {
            platforms = bizRet.getData();

        }
        for (IotExternalDevicePlatformDTO plat : platforms) {
            boolean flag = false;
            for (String str : platform) {
                String code = str.split("\\|")[0];
                if (code.equals(plat.getPlatformCode())) {
                    flag = true;
                }
            }
            if (!flag) {
                plat.setStatus(false);
                iotMonitorService.savePlatForm(plat);
            }
        }
    }

    @PostConstruct
    public void init() {
        consumer = iotDosBizConfig.getFactory().createConsumer(() -> Constants.IOT_DOS_MONITOR_GROUP_CONSUMER);

        consumer.subscribe(Constants.IOT_DOS_MONITOR_TOPIC, null, new
                IRocketMsgListener() {
                    @Override
                    public void onSuccess(List<RocketMsg> messages) throws Exception {
                        for (RocketMsg rocketMsg : messages) {
                            if (Constants.IOT_DOS_SERVICE_INFO_ALL.equals(rocketMsg.getTags())) {
                                handleMessage(rocketMsg.getContent());
                            } else if (Constants.IOT_DOS_SERVICE_INFO_SIMPLE.equals(rocketMsg.getTags())) {
                                handleSimpleMessage(rocketMsg.getContent());
                            } else if (Constants.IOT_DOS_PLATFORM_STATUS.equals(rocketMsg.getTags())) {
                                handlePlatFormMessage(rocketMsg.getContent());
                            }
                        }
                    }

                    @Override
                    public void onFailed(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
    }

}

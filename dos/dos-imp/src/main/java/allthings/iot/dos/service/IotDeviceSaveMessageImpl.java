package allthings.iot.dos.service;

import allthings.iot.dos.IotDosBizConfig;
import allthings.iot.dos.constant.Constants;
import allthings.iot.dos.dto.query.IotDeviceMessageDTO;
import allthings.iot.dos.api.IotDeviceSaveMessageService;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import allthings.iot.util.rocketmq.IProducer;
import allthings.iot.util.rocketmq.msg.RocketMsg;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @description:
 * @author: fengchangxin
 * @create: 2018-12-21 10:46
 */
@Service("iotDeviceSaveMessageService")
public class IotDeviceSaveMessageImpl implements IotDeviceSaveMessageService {
    private static final Logger LOGGER = LoggerFactory.getLogger(IotDeviceSaveMessageImpl.class);
//    @Autowired
//    private IFactory factory;
//
//    @PostConstruct
//    public void init() {
//        allthings.iot.dos.monitor.producer = factory.createProducer(new IProducerConfig() {
//            @Override
//            public String getProducerId() {
//                return String.join("-", Constants.DOS_SERVICE_TO_TASK_GROUP, this.getClass().getCanonicalName());
//            }
//        });
//    }

    private IProducer producer;

    @Autowired
    private IotDosBizConfig iotDosBizConfig;

    @PostConstruct
    public void init() {
        producer = iotDosBizConfig.getFactory().createProducer(() -> String.join("-",
                Constants.DOS_SERVICE_TO_TASK_GROUP, this.getClass().getSimpleName()));
    }

    @Override
    public void sendMessage(List<String> deviceIds, Long iotProjectId) {
        try {
            IotDeviceMessageDTO iotDeviceMessageDTO = new IotDeviceMessageDTO();
            iotDeviceMessageDTO.setDeviceIds(deviceIds);
            iotDeviceMessageDTO.setIotProjectId(iotProjectId);
            String topic = Constants.DOS_SERVICE_TO_TASK_TOPIC;
            RocketMsg rocketMsg = new RocketMsg(topic);
            rocketMsg.setContent(JSON.toJSONString(iotDeviceMessageDTO));
            producer.syncSend(rocketMsg);
        } catch (Exception e) {
            LOGGER.error("send device message to task error,", e);
        }

    }
}

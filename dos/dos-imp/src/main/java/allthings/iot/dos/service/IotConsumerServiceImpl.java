package allthings.iot.dos.service;

import allthings.iot.dos.constant.Constants;
import allthings.iot.dos.constant.RoleCode;
import allthings.iot.dos.consumer.IotLoggerProducer;
import allthings.iot.dos.dao.IotDeviceDao;
import allthings.iot.dos.dao.IotPassTypeDao;
import allthings.iot.dos.dto.IotDevicePassDto;
import allthings.iot.dos.dto.query.IotLogDTO;
import allthings.iot.dos.model.IotDevice;
import allthings.iot.dos.api.IotConsumerService;
import allthings.iot.dos.api.IotDevicePassService;
import com.google.common.collect.Lists;
import allthings.iot.des.client.IotDesFeignClient;
import allthings.iot.des.dto.IotDesEventTypeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import allthings.iot.common.dto.Result;
import allthings.iot.common.msg.DeviceConnectionMsg;
import allthings.iot.common.msg.DeviceEventMsg;
import allthings.iot.common.msg.DeviceMsg;
import allthings.iot.common.msg.IMsg;

import java.util.List;
import java.util.Map;

/**
 * @author tyf
 * @date 2019/03/18 14:38
 */
@Service("iotConsumerService")
public class IotConsumerServiceImpl implements IotConsumerService {

    @Autowired
    private IotDeviceDao deviceDao;
    @Autowired
    private IotLoggerProducer producer;
    @Autowired
    private IotDesFeignClient desFeignClient;
    @Autowired
    private IotPassTypeDao iotPassTypeDao;
    @Autowired
    private IotDevicePassService iotDevicePassService;

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, rollbackFor = Exception.class)
    public void saveDeviceLogger(IMsg msg) {
        String deviceCode = msg.getSourceDeviceId();
        IotLogDTO logDTO = new IotLogDTO();
        logDTO.setCreateOperatorId(0L);
        logDTO.setModifyOperatorId(0L);
        logDTO.setLoggerTime(msg.getOccurTime());
        logDTO.setUserId(0L);
        logDTO.setLogTypeCode(Constants.DEVICE_LOGGER_TYPE);
        logDTO.setAssociationType(IotDevice.class.getSimpleName());
        logDTO.setDeviceCode(deviceCode);

        if (msg instanceof DeviceConnectionMsg) {
            DeviceConnectionMsg connectionMsg = (DeviceConnectionMsg) msg;
            boolean isConnected = connectionMsg.isConnected();
            if (isConnected) {
                logDTO.setLogContent("监测到设备【" + deviceCode + "】上线");
            } else {
                logDTO.setLogContent("监测到设备【" + deviceCode + "】离线");
            }
            producer.sendToQueue(logDTO);
        }
        if (msg instanceof DeviceMsg) {
            logDTO.setLogContent("设备【" + deviceCode + "】下发指令成功, 指令码【" + msg.getMsgCode() + "】");
            producer.sendToQueue(logDTO);
        }

        if (msg instanceof DeviceEventMsg) {
            Map<String, Object> params = msg.getParams();
            String eventTypeCode = String.valueOf(params.get("eventTypeCode"));
            Result<IotDesEventTypeDto> bizReturn = desFeignClient.getIotEventTypeByEventTypeCode(eventTypeCode);
            if (!Result.isSuccess(bizReturn) || bizReturn.getRet() == null) {
                return;
            }
            IotDesEventTypeDto desEventTypeDto = bizReturn.getRet();
            logDTO.setLogContent("收到设备【" + deviceCode + "】上报事件, 事件类型【" + desEventTypeDto.getEventTypeName() + "】");
            producer.sendToQueue(logDTO);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void saveDevicePass(IMsg msg) {
        String deviceCode = msg.getSourceDeviceId();
        List<IotDevice> devices = deviceDao.getIotDeviceByDeviceCode(deviceCode);
        if (CollectionUtils.isEmpty(devices)) {
            return;
        }
        Map<String, Object> params = msg.getParams();
        if (CollectionUtils.isEmpty(params)) {
            return;
        }
        List<Map<String, Object>> passList = (List<Map<String, Object>>) params.get("devicePass");
        if (CollectionUtils.isEmpty(passList)) {
            return;
        }
        List<IotDevicePassDto> devicePassDtoList = Lists.newArrayList();
        for (IotDevice device : devices) {
            for (Map<String, Object> pass : passList) {
                String passTypeCode = String.valueOf(pass.get("passTypeCode"));
                Long iotPassTypeId = iotPassTypeDao.getIotPassTypeIdByPassTypeCode(passTypeCode,
                        device.getIotDeviceTypeId());
                if (iotPassTypeId == null) {
                    continue;
                }
                IotDevicePassDto devicePassDto = new IotDevicePassDto();
                devicePassDto.setIotDeviceId(device.getIotDeviceId());
                devicePassDto.setPassCode(String.valueOf(pass.get("passCode")));
                devicePassDto.setPassName(String.valueOf(pass.get("passName")));
                devicePassDto.setCreateOperatorId(RoleCode.CREATE_OPERATOR_ID);
                devicePassDto.setModifyOperatorId(RoleCode.CREATE_OPERATOR_ID);
                devicePassDto.setExtendPropertiesAlias(String.valueOf(pass.get("extendProperties")));
                devicePassDto.setIotPassTypeId(iotPassTypeId);
                devicePassDto.setPassTypeCode(passTypeCode);
                devicePassDtoList.add(devicePassDto);
            }
        }
        iotDevicePassService.batchSaveOrUpdateDevicePass(devicePassDtoList);
    }
}

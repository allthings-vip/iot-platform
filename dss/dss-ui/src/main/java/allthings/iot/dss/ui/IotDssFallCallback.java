package allthings.iot.dss.ui;

import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.dto.IotDeviceDetailDTO;
import allthings.iot.dos.dto.IotDeviceStatusBatchQueryDTO;
import allthings.iot.dos.dto.IotEventPushUrlDto;
import allthings.iot.dos.dto.query.IotIovProtocolCodeQueryDto;
import allthings.iot.dss.constant.Constant;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author tyf
 * @date 2019/05/06 15:01:38
 */
@Component
public class IotDssFallCallback implements IotDssApi {
    @Override
    public ResultDTO<List<Long>> getIotDeviceIdByDeviceCode(String deviceCode) {
        return Constant.callbackResult;
    }

    @Override
    public ResultDTO<IotEventPushUrlDto> getEventPushUrlByIotProjectId(Long iotProjectId) {
        return Constant.callbackResult;
    }

    @Override
    public ResultDTO<List<Long>> getIotProjectIdByDeviceCode(String deviceCode) {
        return Constant.callbackResult;
    }

    @Override
    public ResultDTO<IotIovProtocolCodeQueryDto> getProtocolByDeviceCode(String deviceCode) {
        return Constant.callbackResult;
    }

    @Override
    public ResultDTO<List<IotIovProtocolCodeQueryDto>> getProtocolByDeviceCodes(@RequestBody IotDeviceStatusBatchQueryDTO batchQueryDTO) {
        return Constant.callbackResult;
    }

    @Override
    public ResultDTO<String> getDeviceIdByDeviceCode(String deviceCode) {
        return Constant.callbackResult;
    }

    @Override
    public ResultDTO<IotDeviceDetailDTO> getIotDeviceDetail(String deviceCode, Long iotProjectId) {
        return Constant.callbackResult;
    }

    @Override
    public ResultDTO<List<IotDeviceDetailDTO>> getDeviceByProjectId(Long iotProjectId) {
        return Constant.callbackResult;
    }

    @Override
    public ResultDTO<String> getDeviceIdByBizCode(String bizCode) {
        return Constant.callbackResult;
    }

    @Override
    public ResultDTO<IotIovProtocolCodeQueryDto> getProtocolByBizCode(String bizCode) {
        return Constant.callbackResult;
    }
}

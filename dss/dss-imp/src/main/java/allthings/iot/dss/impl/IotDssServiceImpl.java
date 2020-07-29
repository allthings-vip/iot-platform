package allthings.iot.dss.impl;

import allthings.iot.dss.api.IotDssService;
import com.tf56.iot.dos.client.api.IotDeviceApi;
import com.tf56.iot.dos.client.api.IotEventPushUrlApi;
import com.tf56.iot.dos.constant.RoleCode;
import com.tf56.iot.dos.dto.IotDeviceDTO;
import com.tf56.iot.dos.dto.IotDeviceDetailDTO;
import com.tf56.iot.dos.dto.IotDeviceStatusBatchQueryDTO;
import com.tf56.iot.dos.dto.IotEventPushUrlDto;
import com.tf56.iot.dos.dto.query.IotIovProtocolCodeQueryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tf56.iot.common.dto.ResultDTO;

import java.util.List;

/**
 * @author tyf
 * @date 2019/03/29 14:29
 */
@Service("iotDssService")
public class IotDssServiceImpl implements IotDssService {

    @Autowired
    private IotDeviceApi iotDeviceApi;
    @Autowired
    private IotEventPushUrlApi iotEventPushUrlApi;

    @Override
    public ResultDTO<List<Long>> getIotDeviceIdByDeviceCode(String deviceCode) {
        return iotDeviceApi.getIotDeviceIdByDeviceCode(deviceCode);
    }

    @Override
    public ResultDTO<IotEventPushUrlDto> getEventPushUrlByIotProjectId(Long iotProjectId) {
        IotEventPushUrlDto eventPushUrlDto = new IotEventPushUrlDto();
        eventPushUrlDto.setIotProjectId(iotProjectId);
        eventPushUrlDto.setRoleCode(RoleCode.ADMIN);
        return iotEventPushUrlApi.getEventPushUrlByIotProjectId(eventPushUrlDto);
    }

    @Override
    public ResultDTO<List<Long>> getIotProjectIdByDeviceCode(String deviceCode) {
        return iotDeviceApi.getIotProjectIdByDeviceCode(deviceCode);
    }

    @Override
    public ResultDTO<IotIovProtocolCodeQueryDto> getProtocolByDeviceCode(String deviceCode) {
        IotDeviceDTO iotDeviceDTO = new IotDeviceDTO();
        iotDeviceDTO.setDeviceCode(deviceCode);
        return iotDeviceApi.getProtocolByDeviceCode(iotDeviceDTO);
    }

    @Override
    public ResultDTO<List<IotIovProtocolCodeQueryDto>> getProtocolByDeviceCodes(IotDeviceStatusBatchQueryDTO batchQueryDTO) {
        return iotDeviceApi.getProtocolByDeviceCodes(batchQueryDTO);
    }

    @Override
    public ResultDTO<String> getDeviceIdByDeviceCode(String deviceCode) {
        return iotDeviceApi.getDeviceIdByDeviceCode(deviceCode);
    }

    @Override
    public ResultDTO<IotDeviceDetailDTO> getIotDeviceDetail(String deviceCode, Long iotProjectId) {
        return iotDeviceApi.getIotDeviceDetail(deviceCode, iotProjectId);
    }

    @Override
    public ResultDTO<List<IotDeviceDetailDTO>> getDeviceByProjectId(Long iotProjectId) {
        return iotDeviceApi.getDeviceListByProjectId(iotProjectId);
    }

    @Override
    public ResultDTO<String> getDeviceIdByBizCode(String bizCode) {
        return iotDeviceApi.getDeviceIdByBizCode(bizCode);
    }

    @Override
    public ResultDTO<IotIovProtocolCodeQueryDto> getProtocolByBizCode(String bizCode) {
        return iotDeviceApi.getProtocolByBizCode(bizCode);

    }
}

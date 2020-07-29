package allthings.iot.dss.controller;

import allthings.iot.dss.api.IotDssService;
import allthings.iot.dss.ui.IotDssApi;
import com.alibaba.fastjson.JSON;
import allthings.iot.dos.dto.IotDeviceDetailDTO;
import allthings.iot.dos.dto.IotDeviceStatusBatchQueryDTO;
import allthings.iot.dos.dto.IotEventPushUrlDto;
import allthings.iot.dos.dto.query.IotIovProtocolCodeQueryDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import allthings.iot.common.dto.ResultDTO;

import java.util.List;

/**
 * @author tyf
 * @date 2019/04/16 17:57:09
 */
@RestController
@RequestMapping
public class IotDssController implements IotDssApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(IotDssController.class);

    @Autowired
    private IotDssService iotDssService;

    @Override
    public ResultDTO<List<Long>> getIotDeviceIdByDeviceCode(@RequestParam("deviceCode") String deviceCode) {
        ResultDTO<List<Long>> bizReturn = iotDssService.getIotDeviceIdByDeviceCode(deviceCode);
        LOGGER.info(JSON.toJSONString(bizReturn));
        return bizReturn;
    }

    @Override
    public ResultDTO<IotEventPushUrlDto> getEventPushUrlByIotProjectId(@RequestParam("iotProjectId") Long iotProjectId) {
        return iotDssService.getEventPushUrlByIotProjectId(iotProjectId);
    }

    @Override
    public ResultDTO<List<Long>> getIotProjectIdByDeviceCode(@RequestParam("deviceCode") String deviceCode) {
        return iotDssService.getIotProjectIdByDeviceCode(deviceCode);
    }

    @Override
    public ResultDTO<IotIovProtocolCodeQueryDto> getProtocolByDeviceCode(@RequestParam("deviceCode") String deviceCode) {
        return iotDssService.getProtocolByDeviceCode(deviceCode);
    }

    @Override
    public ResultDTO<List<IotIovProtocolCodeQueryDto>> getProtocolByDeviceCodes(@RequestBody IotDeviceStatusBatchQueryDTO batchQueryDTO) {
        return iotDssService.getProtocolByDeviceCodes(batchQueryDTO);
    }

    @Override
    public ResultDTO<String> getDeviceIdByDeviceCode(@RequestParam("deviceCode") String deviceCode) {
        return iotDssService.getDeviceIdByDeviceCode(deviceCode);
    }

    @Override
    public ResultDTO<IotDeviceDetailDTO> getIotDeviceDetail(@RequestParam("deviceCode") String deviceCode,
                                                            @RequestParam("iotProjectId") Long iotProjectId) {
        return iotDssService.getIotDeviceDetail(deviceCode, iotProjectId);
    }


    @Override
    public ResultDTO<List<IotDeviceDetailDTO>> getDeviceByProjectId(@RequestParam("iotProjectId") Long iotProjectId) {
        return iotDssService.getDeviceByProjectId(iotProjectId);
    }


    @Override
    public ResultDTO<String> getDeviceIdByBizCode(@RequestParam("bizCode") String bizCode) {
        return iotDssService.getDeviceIdByBizCode(bizCode);
    }


    @Override
    public ResultDTO<IotIovProtocolCodeQueryDto> getProtocolByBizCode(@RequestParam("bizCode") String bizCode) {
        return iotDssService.getProtocolByBizCode(bizCode);
    }

}

package allthings.iot.dos.controller;

import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.api.IotDevicePassService;
import allthings.iot.dos.client.api.IotDevicePassApi;
import allthings.iot.dos.dto.IotDeviceDTO;
import allthings.iot.dos.dto.IotDevicePassDto;
import allthings.iot.dos.dto.IotVisResultDTO;
import allthings.iot.dos.dto.query.IotDevicePassQueryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author tyf
 * @date 2019/07/05 09:25:25
 */
@RestController
public class IotDevicePassController implements IotDevicePassApi {

    @Autowired
    private IotDevicePassService iotDevicePassService;

    @Override
    public ResultDTO<Integer> saveDevicePass(@RequestBody IotDevicePassDto devicePassDto) {
        return iotDevicePassService.saveOrUpdateDevicePass(devicePassDto);
    }

    @Override
    public ResultDTO<List<IotDevicePassDto>> getDevicePassList(@RequestBody IotDeviceDTO deviceDTO) {
        return iotDevicePassService.getDevicePassList(deviceDTO);
    }

    @Override
    public ResultDTO<?> getDevicePassDetail(@RequestParam("iotDosDevicePassId") Long iotDosDevicePassId) {
        return iotDevicePassService.getDevicePassDetail(iotDosDevicePassId);
    }

    @Override
    public ResultDTO<IotVisResultDTO> getPassLiveStream(@RequestBody IotDevicePassQueryDTO iotDevicePassQueryDto) {
        return iotDevicePassService.getPassLiveStream(iotDevicePassQueryDto);
    }

    @Override
    public ResultDTO<IotVisResultDTO> getPassPlayBack(@RequestBody IotDevicePassQueryDTO iotDevicePassQueryDto) {
        return iotDevicePassService.getPassPlayBack(iotDevicePassQueryDto);
    }

    @Override
    public ResultDTO<Integer> controlDevicePass(@RequestBody IotDevicePassQueryDTO iotDevicePassQueryDTO) {
        return iotDevicePassService.controlDevicePass(iotDevicePassQueryDTO);
    }

    @Override
    public ResultDTO<Integer> stopControlDevicePass(@RequestBody IotDevicePassQueryDTO iotDevicePassQueryDTO) {
        return iotDevicePassService.stopControlDevicePass(iotDevicePassQueryDTO);
    }

    @Override
    public ResultDTO<List<IotDevicePassDto>> getIotDevicePassListByIotPassTypeId(@RequestParam("iotDeviceId") Long iotDeviceId,
                                                                                 @RequestParam(value = "iotPassTypeId",
                                                                                         required = false) Long iotPassTypeId) {
        return iotDevicePassService.getIotDevicePassList(iotDeviceId, iotPassTypeId);
    }
}
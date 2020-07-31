package allthings.iot.dos.controller;

import allthings.iot.dos.api.IotDevicePassBiz;
import allthings.iot.dos.client.api.IotDevicePassApi;
import allthings.iot.dos.dto.IotDeviceDTO;
import allthings.iot.dos.dto.IotDevicePassDto;
import allthings.iot.dos.dto.IotVisResultDTO;
import allthings.iot.dos.dto.query.IotDevicePassQueryDTO;
import allthings.iot.dos.service.IotDevicePassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tf56.iot.common.dto.ResultDTO;

import java.util.List;

/**
 * @author tyf
 * @date 2019/07/05 09:25:25
 */
@RestController
public class IotDevicePassController extends IotDosBaseController implements IotDevicePassApi {

    /**
     * 此处不要用biz,因为biz暴露给dubbo使用，此controller是spring cloud的接口实现；
     *
     * 所以此处使用sevice即可，后续所有相关的biz都有可能移除
     */
    @Autowired
    private IotDevicePassBiz devicePassBiz;

    @Autowired
    private IotDevicePassService iotDevicePassService;

    @Override
    public ResultDTO<Integer> saveDevicePass(@RequestBody IotDevicePassDto devicePassDto) {
        return getResult(devicePassBiz.saveDevicePass(devicePassDto));
    }

    @Override
    public ResultDTO<List<IotDevicePassDto>> getDevicePassList(@RequestBody IotDeviceDTO deviceDTO) {
        return getResult(devicePassBiz.getDevicePassList(deviceDTO));
    }

    @Override
    public ResultDTO<?> getDevicePassDetail(@RequestParam("iotDosDevicePassId") Long iotDosDevicePassId) {
        return getResult(devicePassBiz.getDevicePassDetail(iotDosDevicePassId));
    }

    @Override
    public ResultDTO<IotVisResultDTO> getPassLiveStream(@RequestBody IotDevicePassQueryDTO iotDevicePassQueryDto) {
        return getResult(devicePassBiz.getPassLiveStream(iotDevicePassQueryDto));
    }

    @Override
    public ResultDTO<IotVisResultDTO> getPassPlayBack(@RequestBody IotDevicePassQueryDTO iotDevicePassQueryDto) {
        return getResult(devicePassBiz.getPassPlayBack(iotDevicePassQueryDto));
    }

    @Override
    public ResultDTO<Integer> controlDevicePass(@RequestBody IotDevicePassQueryDTO iotDevicePassQueryDTO) {
        return getResult(devicePassBiz.controlDevicePass(iotDevicePassQueryDTO));
    }

    @Override
    public ResultDTO<Integer> stopControlDevicePass(@RequestBody IotDevicePassQueryDTO iotDevicePassQueryDTO) {
        return getResult(devicePassBiz.stopControlDevicePass(iotDevicePassQueryDTO));
    }

    @Override
    public ResultDTO<List<IotDevicePassDto>> getIotDevicePassListByIotPassTypeId(@RequestParam("iotDeviceId") Long iotDeviceId,
                                                                                 @RequestParam(value = "iotPassTypeId",
                                                                                         required = false) Long iotPassTypeId) {
        return getResult(iotDevicePassService.getIotDevicePassList(iotDeviceId, iotPassTypeId));
    }
}
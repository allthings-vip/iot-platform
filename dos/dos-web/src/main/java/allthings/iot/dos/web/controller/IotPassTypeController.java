package allthings.iot.dos.web.controller;

import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.client.api.IotPassTypeApi;
import allthings.iot.dos.dto.IotPassTypeDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author luhao
 * @date 2020/2/17 11:50
 */
@RestController
public class IotPassTypeController extends BaseController {

    @Resource
    private IotPassTypeApi iotPassTypeApi;

    @GetMapping(value = "/passtype/getAllByIotDeviceType")
    public ResultDTO<List<IotPassTypeDTO>> getAllByIotDeviceType(@RequestParam("iotDeviceTypeId") Long iotDeviceTypeId) {
        return iotPassTypeApi.getAllByIotDeviceType(iotDeviceTypeId);
    }

}

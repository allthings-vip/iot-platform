package allthings.iot.dos.controller;

import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.api.IotPassTypeService;
import allthings.iot.dos.client.api.IotPassTypeApi;
import allthings.iot.dos.dto.IotPassTypeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author luhao
 * @date 2020/2/17 11:50
 */
@RestController
public class IotPassTypeController implements IotPassTypeApi {

    private IotPassTypeService iotPassTypeService;

    @Autowired
    public IotPassTypeController(IotPassTypeService iotPassTypeService) {
        this.iotPassTypeService = iotPassTypeService;
    }

    @Override
    public ResultDTO<List<IotPassTypeDTO>> getAllByIotDeviceType(@RequestParam("iotDeviceTypeId") Long iotDeviceTypeId) {
        return ResultDTO.newSuccess(iotPassTypeService.getAllByIotDeviceType(iotDeviceTypeId));
    }


}

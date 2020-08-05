package allthings.iot.dos.controller;

import allthings.iot.common.dto.PageResult;
import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.api.IotDeviceTypeService;
import allthings.iot.dos.client.api.IotDeviceTypeApi;
import allthings.iot.dos.dto.IotDeviceTypeDTO;
import allthings.iot.dos.dto.query.IotDeviceTypeDeleteDTO;
import allthings.iot.dos.dto.query.IotDeviceTypeQueryDTO;
import allthings.iot.dos.dto.query.IotProjectDeviceTypeDTO;
import allthings.iot.dos.dto.query.IotProjectSimpleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author tyf
 * @date 2019/07/02 10:06:19
 */
@RestController
public class IotDeviceTypeController implements IotDeviceTypeApi {

    @Autowired
    private IotDeviceTypeService iotDeviceTypeBiz;

    @Override
    public ResultDTO<Integer> updateIotDeviceType(@RequestBody IotDeviceTypeDTO iotDeviceTypeDTO) {
        return iotDeviceTypeBiz.updateIotDeviceType(iotDeviceTypeDTO);
    }

    @Override
    public ResultDTO<Long> saveIotDeviceType(@RequestBody IotDeviceTypeDTO iotDeviceTypeDTO) {
        return iotDeviceTypeBiz.saveIotDeviceType(iotDeviceTypeDTO);
    }

    @Override
    public ResultDTO<Integer> updateIotDeviceTypeStatus(@RequestParam("iotDeviceTypeIds") Long[] iotDeviceTypeIds,
                                                        @RequestParam("modifyOperatorId") Long modifyOperatorId,
                                                        @RequestParam("isEnabled") Integer isEnabled) {
        return iotDeviceTypeBiz.updateIotDeviceTypeStatus(iotDeviceTypeIds, modifyOperatorId, isEnabled);
    }

    @Override
    public ResultDTO<Integer> deleteIotDeviceType(@RequestBody IotDeviceTypeDeleteDTO iotDeviceTypeDeleteDTO) {
        return iotDeviceTypeBiz.deleteIotDeviceType(iotDeviceTypeDeleteDTO);
    }

    @Override
    public ResultDTO<PageResult<IotDeviceTypeQueryDTO>> getIotDeviceTypeList(@RequestBody IotProjectSimpleDTO iotProjectSimpleDTO) {
        return iotDeviceTypeBiz.getIotDeviceTypeList(iotProjectSimpleDTO);
    }

    @Override
    public ResultDTO<IotDeviceTypeDTO> getIotDeviceTypeDetail(@RequestBody IotDeviceTypeDTO iotDeviceTypeDTO) {
        return iotDeviceTypeBiz.getIotDeviceTypeDetail(iotDeviceTypeDTO);
    }

    @Override
    public ResultDTO<List<IotProjectDeviceTypeDTO>> getIotDeviceTypeByIotProjectId(@RequestBody IotDeviceTypeDTO iotDeviceTypeDTO) {
        return iotDeviceTypeBiz.getIotDeviceTypeByIotProjectId(iotDeviceTypeDTO);
    }

    @Override
    public ResultDTO<Integer> judgeProject(@RequestParam("iotProjectId") Long iotProjectId,
                                           @RequestParam("userId") Long userId,
                                           @RequestParam("roleCode") String roleCode) {
        return iotDeviceTypeBiz.judgeProject(iotProjectId, userId, roleCode);
    }
}

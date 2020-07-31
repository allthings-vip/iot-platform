package allthings.iot.dos.controller;

import allthings.iot.dos.api.IotDeviceTypeBiz;
import allthings.iot.dos.client.api.IotDeviceTypeApi;
import allthings.iot.dos.dto.IotDeviceTypeDTO;
import allthings.iot.dos.dto.query.IotDeviceTypeDeleteDTO;
import allthings.iot.dos.dto.query.IotDeviceTypeQueryDTO;
import allthings.iot.dos.dto.query.IotProjectDeviceTypeDTO;
import allthings.iot.dos.dto.query.IotProjectSimpleDTO;
import com.tf56.core.BizReturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tf56.iot.common.dto.PageResult;
import tf56.iot.common.dto.ResultDTO;

import java.util.List;

/**
 * @author tyf
 * @date 2019/07/02 10:06:19
 */
@RestController
public class IotDeviceTypeController extends IotDosBaseController implements IotDeviceTypeApi {

    @Autowired
    private IotDeviceTypeBiz iotDeviceTypeBiz;

    @Override
    public ResultDTO<Integer> updateIotDeviceType(@RequestBody IotDeviceTypeDTO iotDeviceTypeDTO) {
        BizReturn<Integer> bizReturn = iotDeviceTypeBiz.updateIotDeviceType(iotDeviceTypeDTO);
        return getResult(bizReturn);
    }

    @Override
    public ResultDTO<Long> saveIotDeviceType(@RequestBody IotDeviceTypeDTO iotDeviceTypeDTO) {
        BizReturn<Long> bizReturn = iotDeviceTypeBiz.saveIotDeviceType(iotDeviceTypeDTO);
        return getResult(bizReturn);
    }

    @Override
    public ResultDTO<Integer> updateIotDeviceTypeStatus(@RequestParam("iotDeviceTypeIds") Long[] iotDeviceTypeIds,
                                                        @RequestParam("operator") String operator,
                                                        @RequestParam("isEnabled") Integer isEnabled) {
        return getResult(iotDeviceTypeBiz.updateIotDeviceTypeStatus(iotDeviceTypeIds, operator, isEnabled));
    }

    @Override
    public ResultDTO<Integer> deleteIotDeviceType(@RequestBody IotDeviceTypeDeleteDTO iotDeviceTypeDeleteDTO) {
        return getResult(iotDeviceTypeBiz.deleteIotDeviceType(iotDeviceTypeDeleteDTO));
    }

    @Override
    public ResultDTO<PageResult<IotDeviceTypeQueryDTO>> getIotDeviceTypeList(@RequestBody IotProjectSimpleDTO iotProjectSimpleDTO) {
        return getResult(iotDeviceTypeBiz.getIotDeviceTypeList(iotProjectSimpleDTO));
    }

    @Override
    public ResultDTO<IotDeviceTypeDTO> getIotDeviceTypeDetail(@RequestBody IotDeviceTypeDTO iotDeviceTypeDTO) {
        return getResult(iotDeviceTypeBiz.getIotDeviceTypeDetail(iotDeviceTypeDTO));
    }

    @Override
    public ResultDTO<List<IotProjectDeviceTypeDTO>> getIotDeviceTypeByIotProjectId(@RequestBody IotDeviceTypeDTO iotDeviceTypeDTO) {
        return getResult(iotDeviceTypeBiz.getIotDeviceTypeByIotProjectId(iotDeviceTypeDTO));
    }

    @Override
    public ResultDTO<Integer> judgeProject(@RequestParam("iotProjectId") Long iotProjectId,
                                           @RequestParam("userId") Long userId,
                                           @RequestParam("roleCode") String roleCode) {
        return getResult(iotDeviceTypeBiz.judgeProject(iotProjectId, userId, roleCode));
    }
}

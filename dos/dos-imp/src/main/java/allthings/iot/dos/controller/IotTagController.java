package allthings.iot.dos.controller;

import allthings.iot.dos.api.IotTagBiz;
import allthings.iot.dos.client.api.IotTagApi;
import allthings.iot.dos.dto.IotDeviceDTO;
import allthings.iot.dos.dto.IotDeviceTagDTO;
import allthings.iot.dos.dto.IotTagDTO;
import allthings.iot.dos.dto.query.IotTagQueryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tf56.iot.common.dto.ResultDTO;

import java.util.List;

/**
 * @author tyf
 * @date 2019/07/04 14:36:36
 */
@RestController
public class IotTagController extends IotDosBaseController implements IotTagApi {

    @Autowired
    private IotTagBiz tagBiz;

    @Override
    public ResultDTO<List<IotTagQueryDTO>> getIotTagList(@RequestBody IotTagDTO iotTagDTO) {
        return getResult(tagBiz.getIotTagList(iotTagDTO));
    }

    @Override
    public ResultDTO<List<IotDeviceDTO>> getDeviceByIotTagIdAndIotProjectId(@RequestBody IotTagQueryDTO iotTagQueryDTO, @RequestParam("choose") Boolean choose) {
        return getResult(tagBiz.getDeviceByIotTagIdAndIotProjectId(iotTagQueryDTO, choose));
    }

    @Override
    public ResultDTO<Integer> deleteTagByTagId(@RequestBody IotTagDTO iotTagDTO) {
        return getResult(tagBiz.deleteTagByTagId(iotTagDTO));
    }

    @Override
    public ResultDTO<Integer> saveTag(@RequestBody IotTagDTO iotTagDTO) {
        return getResult(tagBiz.saveTag(iotTagDTO));
    }

    @Override
    public ResultDTO<Integer> saveDeviceOfTag(@RequestBody IotDeviceTagDTO iotDeviceTagDTO) {
        return getResult(tagBiz.saveDeviceOfTag(iotDeviceTagDTO));
    }

    @Override
    public ResultDTO<Integer> deleteDeviceOfTag(@RequestBody IotDeviceTagDTO iotDeviceTagDTO) {
        return getResult(tagBiz.deleteDeviceOfTag(iotDeviceTagDTO));
    }

}

package allthings.iot.dos.controller;

import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.api.IotTagService;
import allthings.iot.dos.client.api.IotTagApi;
import allthings.iot.dos.dto.IotDeviceDTO;
import allthings.iot.dos.dto.IotDeviceTagDTO;
import allthings.iot.dos.dto.IotTagDTO;
import allthings.iot.dos.dto.query.IotTagQueryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author tyf
 * @date 2019/07/04 14:36:36
 */
@RestController
public class IotTagController implements IotTagApi {

    @Autowired
    private IotTagService tagBiz;

    @Override
    public ResultDTO<List<IotTagQueryDTO>> getIotTagList(@RequestBody IotTagDTO iotTagDTO) {
        return tagBiz.getIotTagList(iotTagDTO);
    }

    @Override
    public ResultDTO<List<IotDeviceDTO>> getDeviceByIotTagIdAndIotProjectId(@RequestBody IotTagQueryDTO iotTagQueryDTO, @RequestParam("choose") Boolean choose) {
        return tagBiz.getDeviceByIotTagIdAndIotProjectId(iotTagQueryDTO, choose);
    }

    @Override
    public ResultDTO<Integer> deleteTagByTagId(@RequestBody IotTagDTO iotTagDTO) {
        return tagBiz.deleteTagByTagId(iotTagDTO);
    }

    @Override
    public ResultDTO<Integer> saveTag(@RequestBody IotTagDTO iotTagDTO) {
        return tagBiz.saveTag(iotTagDTO);
    }

    @Override
    public ResultDTO<Integer> saveDeviceOfTag(@RequestBody IotDeviceTagDTO iotDeviceTagDTO) {
        return tagBiz.saveDeviceOfTag(iotDeviceTagDTO);
    }

    @Override
    public ResultDTO<Integer> deleteDeviceOfTag(@RequestBody IotDeviceTagDTO iotDeviceTagDTO) {
        return tagBiz.deleteDeviceOfTag(iotDeviceTagDTO);
    }

}

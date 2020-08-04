package allthings.iot.dos.web.controller;

import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.client.api.IotTagApi;
import allthings.iot.dos.dto.IotDeviceDTO;
import allthings.iot.dos.dto.IotDeviceTagDTO;
import allthings.iot.dos.dto.IotTagDTO;
import allthings.iot.dos.dto.query.IotTagQueryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author :  luhao
 * @FileName :  IotTagController
 * @CreateDate :  2018-5-10
 * @Description :
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
@RestController
public class IotTagController extends BaseController {
    @Autowired
    private IotTagApi iotTagApi;

    /**
     * 获取标签列表
     *
     * @param iotProjectId
     * @return
     */
    @GetMapping(value = "/tag/list")
    public ResultDTO<List<IotTagQueryDTO>> getIotTagList(@RequestParam("iotProjectId") Long iotProjectId) {
        IotTagDTO iotTagDTO = new IotTagDTO();
        iotTagDTO.setIotProjectId(iotProjectId);
        setDto(iotTagDTO);
        return iotTagApi.getIotTagList(iotTagDTO);
    }

    /**
     * 获取标签下未选设备列表
     *
     * @param iotProjectId
     * @param iotTagId
     * @param keywords
     * @return
     */
    @GetMapping(value = "/tag/device/choose/list")
    public ResultDTO<List<IotDeviceDTO>> getUnchooseDevice(@RequestParam("iotProjectId") Long iotProjectId,
                                                           @RequestParam("iotTagId") Long iotTagId,
                                                           @RequestParam(value = "keywords", required = false) String keywords) {
        IotTagQueryDTO iotTagQueryDTO = new IotTagQueryDTO();
        iotTagQueryDTO.setIotProjectId(iotProjectId);
        iotTagQueryDTO.setIotTagId(iotTagId);
        iotTagQueryDTO.setKeywords(keywords);
        setDto(iotTagQueryDTO);
        return iotTagApi.getDeviceByIotTagIdAndIotProjectId(iotTagQueryDTO, true);
    }

    /**
     * 获取标签下已选设备列表
     *
     * @param iotProjectId
     * @param iotTagId
     * @param keywords
     * @return
     */
    @GetMapping(value = "/tag/device/unchoose/list")
    public ResultDTO<List<IotDeviceDTO>> getChooseDevice(@RequestParam("iotProjectId") Long iotProjectId,
                                                         @RequestParam("iotTagId") Long iotTagId,
                                                         @RequestParam(value = "keywords", required = false) String keywords) {
        IotTagQueryDTO iotTagQueryDTO = new IotTagQueryDTO();
        iotTagQueryDTO.setIotProjectId(iotProjectId);
        iotTagQueryDTO.setIotTagId(iotTagId);
        iotTagQueryDTO.setKeywords(keywords);
        setDto(iotTagQueryDTO);
        return iotTagApi.getDeviceByIotTagIdAndIotProjectId(iotTagQueryDTO, false);
    }

    /**
     * 新增标签
     *
     * @param iotTagDTO
     * @return
     */
    @PostMapping("/tag/save")
    public ResultDTO<Integer> saveTag(@RequestBody IotTagDTO iotTagDTO) {
        setDto(iotTagDTO);
        return iotTagApi.saveTag(iotTagDTO);
    }

    /**
     * 删除标签
     *
     * @param iotTagDTO
     * @return
     */
    @PostMapping("/tag/delete")
    public ResultDTO<Integer> deleteTag(@RequestBody IotTagDTO iotTagDTO) {
        setDto(iotTagDTO);
        return iotTagApi.deleteTagByTagId(iotTagDTO);
    }

    /**
     * 标签下设备增加
     *
     * @param iotDeviceTagDTO
     * @return
     */
    @PostMapping("tag/device/save")
    public ResultDTO<Integer> addDevice(@RequestBody IotDeviceTagDTO iotDeviceTagDTO) {
        setDto(iotDeviceTagDTO);
        return iotTagApi.saveDeviceOfTag(iotDeviceTagDTO);
    }

    /**
     * 标签下设备删除
     *
     * @param iotDeviceTagDTO
     * @return
     */
    @PostMapping("tag/device/delete")
    public ResultDTO<Integer> deleteDevice(@RequestBody IotDeviceTagDTO iotDeviceTagDTO) {
        setDto(iotDeviceTagDTO);
        return iotTagApi.deleteDeviceOfTag(iotDeviceTagDTO);
    }

}

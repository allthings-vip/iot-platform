package allthings.iot.dos.api;

import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.dto.IotDeviceDTO;
import allthings.iot.dos.dto.IotDeviceTagDTO;
import allthings.iot.dos.dto.IotTagDTO;
import allthings.iot.dos.dto.query.IotTagQueryDTO;

import java.util.List;

/**
 * @author :  luhao
 * @FileName :  IotTagBiz
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
public interface IotTagService {
    /**
     * 获取标签列表
     *
     * @param iotTagDTO
     * @return
     */
    ResultDTO<List<IotTagQueryDTO>> getIotTagList(IotTagDTO iotTagDTO);

    /**
     * 获取标签下设备
     *
     * @param iotTagQueryDTO
     * @param choose
     * @return
     */
    ResultDTO<List<IotDeviceDTO>> getDeviceByIotTagIdAndIotProjectId(IotTagQueryDTO iotTagQueryDTO, Boolean choose);

    /**
     * 删除标签
     *
     * @param iotTagDTO
     * @return
     */
    ResultDTO<Integer> deleteTagByTagId(IotTagDTO iotTagDTO);

    /**
     * 保存标签
     *
     * @param iotTagDto
     * @return
     */
    ResultDTO<Integer> saveTag(IotTagDTO iotTagDto);

    /**
     * 批量新增标签下设备
     *
     * @param iotDeviceTagDTO
     * @return
     */
    ResultDTO<Integer> saveDeviceOfTag(IotDeviceTagDTO iotDeviceTagDTO);

    /**
     * 批量删除标签下设备
     *
     * @param iotDeviceTagDTO
     * @return
     */
    ResultDTO<Integer> deleteDeviceOfTag(IotDeviceTagDTO iotDeviceTagDTO);

    /**
     * 通过标签名称获取标签ID
     *
     * @param tagName
     * @param iotProjectId
     * @return
     */
    ResultDTO<Long> getIdByTagName(String tagName, Long iotProjectId);
}

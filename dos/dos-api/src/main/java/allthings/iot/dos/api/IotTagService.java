package allthings.iot.dos.api;

import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.dto.IotDeviceDTO;
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
     * @param iotProjectId
     * @return
     */
    ResultDTO<List<IotTagQueryDTO>> getIotTagList(Long iotProjectId);

    /**
     * 获取标签下设备
     *
     * @param iotTagId
     * @param iotProjectId
     * @param keywords
     * @param choose
     * @return
     */
    ResultDTO<List<IotDeviceDTO>> getDeviceByIotTagIdAndIotProjectId(Long iotTagId, Long iotProjectId,
                                                                     String keywords, Boolean choose);

    /**
     * 删除标签
     *
     * @param iotTagId
     * @param iotProjectId
     * @return
     */
    ResultDTO<Integer> deleteTagByTagId(Long iotTagId, Long iotProjectId, Long operatorId);

    /**
     * 保存标签
     *
     * @param iotTagDto
     * @param operatorId
     * @return
     */
    ResultDTO<Integer> saveTag(IotTagDTO iotTagDto, Long operatorId);

    /**
     * 批量新增标签下设备
     *
     * @param iotDeviceId
     * @param iotTagId
     * @param operatorId
     * @return
     */
    ResultDTO<Integer> saveDeviceOfTag(List<Long> iotDeviceId, Long iotTagId, Long operatorId);

    /**
     * 批量删除标签下设备
     *
     * @param iotDeviceId
     * @param iotTagId
     * @param operatorId
     * @return
     */
    ResultDTO<Integer> deleteDeviceOfTag(List<Long> iotDeviceId, Long iotTagId, Long operatorId);

    /**
     * 通过标签名称获取标签ID
     *
     * @param tagName
     * @param iotProjectId
     * @return
     */
    ResultDTO<Long> getIdByTagName(String tagName, Long iotProjectId);
}

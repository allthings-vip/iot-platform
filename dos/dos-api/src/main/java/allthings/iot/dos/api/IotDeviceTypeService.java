package allthings.iot.dos.api;

import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.dto.IotDeviceTypeDTO;
import allthings.iot.dos.dto.query.IotDeviceTypeDeleteDTO;
import allthings.iot.dos.dto.query.IotDeviceTypeQueryDTO;
import allthings.iot.dos.dto.query.IotDeviceTypeSimpleDTO;
import allthings.iot.dos.dto.query.IotProjectDeviceTypeDTO;
import allthings.iot.dos.dto.query.IotProjectSimpleDTO;
import allthings.iot.common.dto.PageResult;

import java.util.List;

/**
 * @author :  luhao
 * @FileName :  IotDeviceType
 * @CreateDate :  2018/4/29
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
public interface IotDeviceTypeService {
    /**
     * 修改设备类型
     *
     * @param iotDeviceTypeDTO
     * @return
     */
    ResultDTO<Integer> updateIotDeviceType(IotDeviceTypeDTO iotDeviceTypeDTO);

    /**
     * 新增设备类型
     *
     * @param iotDeviceTypeDTO
     * @return
     */
    ResultDTO<Long> saveIotDeviceType(IotDeviceTypeDTO iotDeviceTypeDTO);

    /**
     * 更新设备类型状态
     *
     * @param iotDeviceTypeIds
     * @param modifyOperatorId
     * @param isEnabled
     * @return
     */
    ResultDTO<Integer> updateIotDeviceTypeStatus(Long[] iotDeviceTypeIds, Long modifyOperatorId, Integer isEnabled);

    /**
     * 删除设备类型
     *
     * @param iotDeviceTypeDeleteDTO
     * @return
     */
    ResultDTO<Integer> deleteIotDeviceType(IotDeviceTypeDeleteDTO iotDeviceTypeDeleteDTO);

    /**
     * 通过设备编码删除设备类型
     *
     * @param iotDeviceTypeDeleteDTO
     * @return
     */
    ResultDTO<Integer> deleteIotDeviceTypeByCode(IotDeviceTypeDeleteDTO iotDeviceTypeDeleteDTO);

    /**
     * 查询列表
     *
     * @param iotProjectSimpleDTO
     * @return
     */
    ResultDTO<PageResult<IotDeviceTypeQueryDTO>> getIotDeviceTypeList(IotProjectSimpleDTO iotProjectSimpleDTO);

    /**
     * 查询列表(开放平台)
     *
     * @param iotProjectSimpleDTO
     * @return
     */
    ResultDTO<PageResult<IotDeviceTypeSimpleDTO>> getOpenIotDeviceTypeList(IotProjectSimpleDTO iotProjectSimpleDTO);

    /**
     * 查询设备类型详情
     *
     * @param iotDeviceTypeDTO
     * @return
     */
    ResultDTO<IotDeviceTypeDTO> getIotDeviceTypeDetail(IotDeviceTypeDTO iotDeviceTypeDTO);

    /**
     * 根据项目获取设备类型
     *
     * @param iotDeviceTypeDTO
     * @return
     */
    ResultDTO<List<IotProjectDeviceTypeDTO>> getIotDeviceTypeByIotProjectId(IotDeviceTypeDTO iotDeviceTypeDTO);

    /**
     * 判断用户是否拥有权限
     *
     * @param iotProjectId
     * @param roleCode
     * @param userId
     * @return
     */
    ResultDTO<Integer> judgeProject(Long iotProjectId, Long userId, String roleCode);

    /**
     * 通过设备类型编码获取设备类型ID
     *
     * @param deviceTypeCode
     * @param iotProjectId
     * @return
     */
    ResultDTO<Long> getDeviceTypeIdByTypeCode(String deviceTypeCode, Long iotProjectId);

    /**
     * 通过设备类型名称获取设备类型ID
     *
     * @param deviceTypeName
     * @param iotProjectId
     * @return
     */
    ResultDTO<Long> getDeviceTypeIdByTypeName(String deviceTypeName, Long iotProjectId);

}

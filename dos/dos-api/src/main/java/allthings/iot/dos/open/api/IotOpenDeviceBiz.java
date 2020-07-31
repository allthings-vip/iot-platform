package allthings.iot.dos.open.api;

import allthings.iot.common.dto.PageResult;
import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.dto.IotOpenApiResponseDeviceDTO;
import allthings.iot.dos.dto.open.IotDeviceListQueryDTO;
import allthings.iot.dos.dto.open.IotDeviceOpenDTO;
import allthings.iot.dos.dto.open.IotDeviceTypeDeleteByCodeDTO;
import allthings.iot.dos.dto.open.IotDeviceTypeSaveDTO;
import allthings.iot.dos.dto.open.IotEventQueryDTO;
import allthings.iot.dos.dto.open.IotLogQueryDTO;
import allthings.iot.dos.dto.open.IotOpenDeviceDTO;
import allthings.iot.dos.dto.open.IotOpenDeviceTypeListQueryDTO;
import allthings.iot.dos.dto.query.IotDeviceEventDTO;
import allthings.iot.dos.dto.query.IotDeviceStatusQueryDTO;
import allthings.iot.dos.dto.query.IotDeviceTypeSimpleDTO;
import allthings.iot.dos.dto.query.IotFactorQueryDTO;
import allthings.iot.dos.dto.query.IotLogDTO;

import java.util.List;

/**
 * @author :  txw
 * @FileName :  IotOpenDeviceBiz
 * @CreateDate :  2018/11/14
 * @Description :  dmp
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
public interface IotOpenDeviceBiz {

    /**
     * 获取事件列表
     *
     * @param iotEventQueryDTO
     * @return
     */
    ResultDTO<PageResult<IotDeviceEventDTO>> getEventList(IotEventQueryDTO iotEventQueryDTO);

    /**
     * 通过设备类型编码获取因子列表
     *
     * @param deviceTypeCode
     * @param iotProjectId
     * @return
     */
    ResultDTO<List<IotFactorQueryDTO>> getFactorListByDevcieTypeCode(String deviceTypeCode, Long iotProjectId);

    /**
     * 获取日志列表
     *
     * @param iotLogQueryDTO
     * @return
     */
    ResultDTO<PageResult<IotLogDTO>> getLogList(IotLogQueryDTO iotLogQueryDTO);

    /**
     * 批量修改设备启用状态
     *
     * @param deviceCodes
     * @param status
     * @param iotProjectId
     * @return
     */
    ResultDTO<Integer> updateDeviceStatus(List<String> deviceCodes, Integer status, Long iotProjectId);

    /**
     * 批量获取设备状态
     *
     * @param deviceCodes
     * @param iotProjectId
     * @return
     */
    ResultDTO<List<IotDeviceStatusQueryDTO>> getDeviceStatusBatch(List<String> deviceCodes, Long iotProjectId);

    /**
     * 新增/保存设备信息
     *
     * @param iotDeviceOpenDTO
     * @return
     */
    ResultDTO<Integer> saveOrUpdateDevice(IotDeviceOpenDTO iotDeviceOpenDTO);

    /**
     * 新增/保存设备信息(返回deviceCode)
     *
     * @param iotDeviceOpenDTO
     * @return
     */
    ResultDTO<IotOpenApiResponseDeviceDTO> saveOrUpdateDeviceOpenApi(IotDeviceOpenDTO iotDeviceOpenDTO);

    /**
     * 批量新增/保存设备信息
     *
     * @param iotDeviceOpenDTOS
     * @param iotProjectId
     * @return
     */
    ResultDTO<List<IotOpenApiResponseDeviceDTO>> saveOrUpdateDeviceBatch(List<IotDeviceOpenDTO> iotDeviceOpenDTOS,
                                                                         Long iotProjectId);

    /**
     * 获取设备列表
     *
     * @param iotDeviceListQueryDTO
     * @return
     */
    ResultDTO<PageResult<IotOpenDeviceDTO>> getDeviceList(IotDeviceListQueryDTO iotDeviceListQueryDTO);

    /**
     * 删除设备类型
     *
     * @param iotDeviceTypeDeleteByCodeDTO
     * @return
     */
    ResultDTO<Integer> deleteDeviceType(IotDeviceTypeDeleteByCodeDTO iotDeviceTypeDeleteByCodeDTO);

    /**
     * 新增/修改设备类型
     *
     * @param iotDeviceTypeSaveDTO
     * @return
     */
    ResultDTO<Integer> saveOrUpdateDeviceType(IotDeviceTypeSaveDTO iotDeviceTypeSaveDTO);

    /**
     * 获取设备类型列表
     *
     * @param iotOpenDeviceTypeListQueryDTO
     * @return
     */
    ResultDTO<PageResult<IotDeviceTypeSimpleDTO>> getDeviceTypeList(IotOpenDeviceTypeListQueryDTO iotOpenDeviceTypeListQueryDTO);
}

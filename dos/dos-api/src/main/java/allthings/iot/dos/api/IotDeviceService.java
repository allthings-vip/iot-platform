package allthings.iot.dos.api;

import allthings.iot.common.dto.PageResult;
import allthings.iot.common.dto.QueryResult;
import allthings.iot.common.dto.ResultDTO;
import allthings.iot.common.msg.DeviceConnectionMsg;
import allthings.iot.des.dto.query.IotDesDeviceEventListQueryDto;
import allthings.iot.des.dto.query.IotDesEventDetailDto;
import allthings.iot.dos.dto.IotDeviceDTO;
import allthings.iot.dos.dto.IotDeviceDetailDTO;
import allthings.iot.dos.dto.IotDeviceErrorMsgDTO;
import allthings.iot.dos.dto.IotDeviceRegisterBatchDTO;
import allthings.iot.dos.dto.IotDeviceSaveBatchDTO;
import allthings.iot.dos.dto.IotDeviceSimpleDTO;
import allthings.iot.dos.dto.IotDeviceStatusBatchQueryDTO;
import allthings.iot.dos.dto.IotDeviceStatusDTO;
import allthings.iot.dos.dto.IotOpenApiResponseDeviceDTO;
import allthings.iot.dos.dto.query.IotDeviceCountQueryDTO;
import allthings.iot.dos.dto.query.IotDeviceCountTitleDTO;
import allthings.iot.dos.dto.query.IotDeviceDetailQueryDTO;
import allthings.iot.dos.dto.query.IotDeviceEventDetailQueryDto;
import allthings.iot.dos.dto.query.IotDeviceHistoryQueryDTO;
import allthings.iot.dos.dto.query.IotDeviceListBaseQueryDTO;
import allthings.iot.dos.dto.query.IotDeviceLocationQueryDTO;
import allthings.iot.dos.dto.query.IotDeviceLoggerQueryListDto;
import allthings.iot.dos.dto.query.IotDeviceMonitorQueryListDTO;
import allthings.iot.dos.dto.query.IotDeviceQueryByCodeDTO;
import allthings.iot.dos.dto.query.IotDeviceQueryDTO;
import allthings.iot.dos.dto.query.IotDeviceQueryListDTO;
import allthings.iot.dos.dto.query.IotDeviceStatusCountDTO;
import allthings.iot.dos.dto.query.IotDeviceStatusQueryDTO;
import allthings.iot.dos.dto.query.IotDosDeviceEventQueryListDto;
import allthings.iot.dos.dto.query.IotDosQueryDTO;
import allthings.iot.dos.dto.query.IotIovProtocolCodeQueryDto;

import java.util.List;
import java.util.Map;

/**
 * @author :  luhao
 * @FileName :  IotDeviceBiz
 * @CreateDate :  2018/5/4
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
public interface IotDeviceService {

    void saveDeviceStatus(DeviceConnectionMsg msg);

    /**
     * 保存设备
     *
     * @param iotDeviceDTO
     * @return
     */
    ResultDTO<Long> saveIotDevice(IotDeviceDTO iotDeviceDTO);

    /**
     * 批量保存设备
     *
     * @param iotDeviceSaveBatchDTO
     * @return
     */
    ResultDTO<List<IotDeviceErrorMsgDTO>> saveIotDevice(IotDeviceSaveBatchDTO iotDeviceSaveBatchDTO);

    /**
     * 开发平台批量保存设备
     *
     * @param iotProjectId
     * @param iotDeviceList
     * @return
     */
    ResultDTO<List<IotOpenApiResponseDeviceDTO>> saveOpenIotDevice(Long iotProjectId,
                                                                   List<IotDeviceDTO> iotDeviceList) throws Exception;

    /**
     * 更新设备
     *
     * @param iotDeviceDTO
     * @return
     */
    ResultDTO<Long> updateIotDevice(IotDeviceDTO iotDeviceDTO);

    /**
     * 删除设备
     *
     * @param iotDeviceIds
     * @param modifyOperatorId
     * @return
     */
    ResultDTO<Integer> deleteIotDevice(Long[] iotDeviceIds, Long modifyOperatorId);

    /**
     * 更新设备可用状态
     *
     * @param iotDeviceStatusDTO
     * @return
     */
    ResultDTO<Integer> updateIotDeviceStatus(IotDeviceStatusDTO iotDeviceStatusDTO);

    /**
     * 通过设备编码批量更新设备启用状态
     *
     * @param deviceCodes
     * @param status
     * @param iotProjectId
     * @return
     */
    ResultDTO<Integer> updateIotDeviceStatus(List<String> deviceCodes, Integer status, Long iotProjectId);

    /**
     * 分在线状态查询设备数量
     *
     * @param iotDeviceQueryListDTO
     * @return
     */
    ResultDTO<IotDeviceStatusCountDTO> getDeviceCountByOnlineStatus(IotDeviceQueryListDTO iotDeviceQueryListDTO);

    /**
     * 根据类型查询设备数量
     *
     * @param iotDosQueryDTO
     * @return
     */
    ResultDTO<List<IotDeviceCountQueryDTO>> getDeviceCountByType(IotDosQueryDTO iotDosQueryDTO);

    /**
     * 获取设备数量Top
     *
     * @param startDatetime
     * @param endDatetime
     * @param top
     * @param type
     * @return
     */
    ResultDTO<QueryResult<IotDeviceCountTitleDTO>> getDeviceCountTopByType(Long startDatetime, Long endDatetime,
                                                                           Integer top, String type);

    /**
     * 通过设备ID查询设备详情
     *
     * @param iotDeviceDTO
     * @return
     */
    ResultDTO<IotDeviceDetailQueryDTO> getIotDeviceDetail(IotDeviceDTO iotDeviceDTO);

    /**
     * 查询设备
     *
     * @param iotDeviceQueryListDTO
     * @return
     */
    ResultDTO<PageResult<IotDeviceQueryDTO>> getIotDeviceByIotProjectId(IotDeviceQueryListDTO iotDeviceQueryListDTO);

    /**
     * 查询设备列表(开放平台接口)
     *
     * @param connected
     * @param iotProjectId
     * @param keywords
     * @param pageIndex
     * @param pageSize
     * @param enabled
     * @return
     */
    ResultDTO<PageResult<IotDeviceQueryDTO>> getIotOpenDeviceList(Boolean connected, Long iotProjectId,
                                                                  String keywords, Integer pageIndex,
                                                                  Integer pageSize, Boolean enabled);

    /**
     * @param iotProjectId
     * @param deviceCodes
     * @param enabled
     * @return
     */
    ResultDTO<PageResult<IotDeviceQueryDTO>> getIotOpenDeviceList(Long iotProjectId, List<String> deviceCodes,
                                                                  Boolean enabled);


    /**
     * 通过设备编码查询设备详情
     *
     * @param iotDeviceQueryByCodeDTO
     * @return
     */
    ResultDTO<IotDeviceDetailQueryDTO> getIotDeviceDetail(IotDeviceQueryByCodeDTO iotDeviceQueryByCodeDTO);

    /**
     * 按标签查询设备类型分布
     *
     * @param iotTagId
     * @param startDatetime
     * @param endDatetime
     * @return
     */
    ResultDTO<QueryResult<IotDeviceCountTitleDTO>> getDeviceCountByTag(Long iotTagId, Long startDatetime,
                                                                       Long endDatetime);

    /**
     * 根据设备编码列表获取设备连接状态
     *
     * @param iotDeviceStatusBatchQueryDTO 设备编码列表
     * @return 设备连接状态列表
     */
    ResultDTO<List<IotDeviceStatusQueryDTO>> getIotDeviceStatus(IotDeviceStatusBatchQueryDTO iotDeviceStatusBatchQueryDTO);

    /**
     * 根据设备编码查询协议
     *
     * @param deviceCode 设备编码
     * @return 设备协议
     */
    ResultDTO<IotIovProtocolCodeQueryDto> getProtocolByDeviceCode(String deviceCode);

    /**
     * 根据设备编码查询协议
     *
     * @param deviceCodes 设备编码列表
     * @return 设备协议
     */
    ResultDTO<List<IotIovProtocolCodeQueryDto>> getProtocolByDeviceCodes(List<String> deviceCodes);

    /**
     * 根据设备编码查询协议
     *
     * @param iotDeviceIds 设备ID
     * @return 设备协议
     */
    ResultDTO<IotOpenApiResponseDeviceDTO> getProtocolByIotDeviceId(List<Long> iotDeviceIds);

    /**
     * 查询设备（注册状态）
     *
     * @param queryListDTO
     * @return
     */
    ResultDTO<PageResult<IotDeviceDTO>> getIotDeviceByRegister(IotDeviceMonitorQueryListDTO queryListDTO);

    /**
     * 批量注册设备
     *
     * @param iotDeviceRegisterBatchDTO
     * @return
     */
    ResultDTO<Integer> updateIotDeviceRegisterBatch(IotDeviceRegisterBatchDTO iotDeviceRegisterBatchDTO);


    /**
     * 查询设备分布
     *
     * @param iotDeviceLocationQueryDTO
     * @return
     */
    ResultDTO<List<IotDeviceQueryDTO>> getDeviceLocation(IotDeviceLocationQueryDTO iotDeviceLocationQueryDTO);

    /**
     * 查询历史轨迹
     *
     * @param historyQueryDTO
     * @return
     * @throws Exception
     */
    ResultDTO<List<Map<String, String>>> history(IotDeviceHistoryQueryDTO historyQueryDTO);

    /**
     * 通过DeviceCode获取DeviceId
     *
     * @param deviceCode
     * @return
     */
    ResultDTO<String> getDeviceIdByDeviceCode(String deviceCode, Long iotProjectId);


    /**
     * 通过DeviceCode获取DeviceId
     *
     * @param deviceCode
     * @return
     */
    ResultDTO<String> getDeviceIdByDeviceCode(String deviceCode);

    /**
     * 通过DeviceCode获取IotDeviceId
     *
     * @param deviceCode
     * @return
     */
    ResultDTO<Long> getIotDeviceIdByDeviceCode(String deviceCode, Long iotProjectId);

    /**
     * 通过deviceId获取项目ID
     *
     * @param deviceId
     * @return
     */
    ResultDTO<List<Long>> getIotProjectIdByDeviceId(String deviceId);

    /**
     * 通过deviceCode获取项目ID
     *
     * @param deviceCode
     * @return
     */
    ResultDTO<List<Long>> getIotProjectIdByDeviceCode(String deviceCode);

    /**
     * 获取项目下设备基础信息
     *
     * @return
     */
    ResultDTO<List<IotDeviceSimpleDTO>> getSimpleDevices();


    /**
     * 根据设备编码获取iotDeviceId
     *
     * @param deviceCode
     * @return
     */
    ResultDTO<List<Long>> getIotDeviceIdByDeviceCode(String deviceCode);


    /**
     * 查询设备事件列表
     *
     * @param deviceDTO
     * @return
     */
    ResultDTO<PageResult<IotDesDeviceEventListQueryDto>> getDeviceEventList(IotDosDeviceEventQueryListDto deviceDTO);

    /**
     * 查询设备事件详情
     *
     * @param deviceEventDetailQueryDto
     * @return
     */
    ResultDTO<IotDesEventDetailDto> getDeviceEventDetail(IotDeviceEventDetailQueryDto deviceEventDetailQueryDto);


    /**
     * 根据iotDeviceId查询日志列表
     *
     * @param deviceLoggerQueryListDto
     * @return
     */
    ResultDTO<?> getDeviceLoggerListByIotDeviceId(IotDeviceLoggerQueryListDto deviceLoggerQueryListDto);

    /**
     * 根据设备id查询设备信息
     *
     * @param iotDeviceId
     * @return
     */
    IotDeviceDTO getIotDeviceByIotDeviceId(Long iotDeviceId);

//    /**
//     * 根据设备编码查询设备信息
//     *
//     * @param deviceCode
//     * @return
//     */
//    List<IotDeviceDTO> getIotDeviceByDeviceCode(String deviceCode);

    /**
     * 根据业务编码查询设备id
     *
     * @param bizCode 业务编码
     * @return
     */
    ResultDTO<String> getDeviceIdByBizCode(String bizCode);

    /**
     * 根据设备编码查询协议
     *
     * @param bizCode 业务编码
     * @return 设备协议
     */
    ResultDTO<IotIovProtocolCodeQueryDto> getProtocolByBizCode(String bizCode);

    /**
     * 通过设备编码获取设备基本属性详情
     *
     * @param deviceCode
     * @return
     */
    ResultDTO<IotDeviceDetailDTO> getIotDeviceBasicByDeviceCode(String deviceCode, Long iotProjectId);

    /**
     * 根据项目ID查询项目所有设备
     *
     * @param iotProjectId
     * @return
     */
    ResultDTO<List<IotDeviceDetailDTO>> getDeviceListByProjectId(Long iotProjectId);

    /**
     * 自定义匹配字段查询设备列表
     *
     * @param iotDeviceListBaseQueryDTO
     * @return
     */
    ResultDTO<List<IotDeviceQueryDTO>> getDeviceListCustom(IotDeviceListBaseQueryDTO iotDeviceListBaseQueryDTO);

    /**
     * 查询设备列表
     *
     * @param iotProjectId 项目id
     * @param protocolCode 协议编码
     * @return
     */
    ResultDTO<List<IotDeviceDetailDTO>> getIotDevicesByProjectIdAndProtocolCode(Long iotProjectId, String protocolCode);
}

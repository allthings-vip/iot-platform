package allthings.iot.dos.dao;

import allthings.iot.common.dto.QueryResult;
import allthings.iot.dos.dto.IotDeviceDTO;
import allthings.iot.dos.dto.IotOpenApiResponseDeviceDTO;
import allthings.iot.dos.dto.query.IotDeviceDetailQueryDTO;
import allthings.iot.dos.dto.query.IotDeviceListBaseQueryDTO;
import allthings.iot.dos.dto.query.IotDeviceQueryDTO;
import allthings.iot.dos.dto.query.IotDeviceStatusCountDTO;
import allthings.iot.dos.dto.query.IotDeviceStatusQueryDTO;
import allthings.iot.dos.dto.query.IotIovProtocolCodeQueryDto;
import allthings.iot.dos.model.offline.IotDeviceCount;
import allthings.iot.dos.model.offline.IotDeviceCountTag;

import java.util.List;

/**
 * @author :  luhao
 * @FileName :  IotDeviceQueryDao
 * @CreateDate :  2018-5-15
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
public interface IotDeviceQueryDao {
    /**
     * 设备列表
     *
     * @param connected
     * @param iotDeviceTypeId
     * @param iotProjectId
     * @param iotTagId
     * @param keywords
     * @param pageIndex
     * @param pageSize
     * @return
     */
    QueryResult<IotDeviceQueryDTO> getIotDeviceByIotProjectId(Boolean connected, Long iotDeviceTypeId, Long
            iotProjectId, Long iotTagId, String keywords, Integer pageIndex, Integer pageSize);

    /**
     * 获取各状态数量
     *
     * @param connected
     * @param iotDeviceTypeId
     * @param iotProjectId
     * @param iotTagId
     * @param keywords
     * @return
     */
    IotDeviceStatusCountDTO getDeviceCountByOnlineStatus(Boolean connected, Long iotDeviceTypeId, Long iotProjectId,
                                                         Long
                                                                 iotTagId, String keywords);


    /**
     * 获取设备列表(开放平台)
     *
     * @param connected
     * @param iotProjectId
     * @param keywords
     * @param pageIndex
     * @param pageSize
     * @return
     */
    QueryResult<IotDeviceQueryDTO> getIotOpenDeviceList(Boolean connected, Long iotProjectId,
                                                        String keywords, Integer pageIndex, Integer pageSize,
                                                        Boolean enabled);

    /**
     * 获取设备列表(开放平台)
     *
     * @param iotProjectId
     * @param deviceCodes
     * @param enabled
     * @return
     */
    QueryResult<IotDeviceQueryDTO> getIotOpenDeviceList(Long iotProjectId, List<String> deviceCodes, Boolean enabled);

    /**
     * 设备详情
     *
     * @param iotDeviceId
     * @return
     */
    IotDeviceDetailQueryDTO getIotDeviceByIotDeviceId(Long iotDeviceId);

    /**
     * 设备详情
     *
     * @param deviceCode
     * @param iotProjectId
     * @return
     */
    IotDeviceDetailQueryDTO getIotDeviceByDeviceCode(String deviceCode, Long iotProjectId);

    /**
     * 获取在线设备数量
     *
     * @param startDatetime
     * @param endDatetime
     * @return
     */
    Long getOnlineDeviceCount(Long startDatetime, Long endDatetime);


    /**
     * 统计设备数量
     *
     * @param dayBeforeZeroHour
     * @param zeroHour
     * @param iotProjectIds
     * @return
     */
    List<IotDeviceCount> getIotDeviceCount(Long dayBeforeZeroHour, Long zeroHour, List<Long> iotProjectIds);

    /**
     * @param dayBeforeZeroHour
     * @param zeroHour
     * @param iotTags
     * @return
     */
    List<IotDeviceCountTag> getIotDeviceCountTag(Long dayBeforeZeroHour, Long zeroHour, List<Long> iotTags);

    /**
     * 根据设备编码列表获取设备连接状态
     *
     * @param deviceCodes  设备编码列表
     * @param iotProjectId
     * @return 设备连接状态列表
     */
    List<IotDeviceStatusQueryDTO> getIotDeviceStatus(List<String> deviceCodes, Long iotProjectId);

    /**
     * 根据设备编码查询协议
     *
     * @param deviceCode 设备编码
     * @return 设备协议
     */
    List<IotIovProtocolCodeQueryDto> getProtocolByDeviceCode(String deviceCode);

    /**
     * 根据设备编码查询协议
     *
     * @param deviceCodes 设备编码
     * @return 设备协议
     */
    List<IotIovProtocolCodeQueryDto> getProtocolByDeviceCodes(List<String> deviceCodes);

    /**
     * 根据设备id列表查询协议
     *
     * @param deviceIds 设备编码
     * @return 设备协议
     */
    List<IotIovProtocolCodeQueryDto> getProtocolByDeviceIds(List<String> deviceIds);

    /**
     * 根据设备ID查询协议编码
     *
     * @param iotDeviceIds 设备ID
     * @return 设备协议
     */
    List<IotOpenApiResponseDeviceDTO> getProtocolByIotDeviceId(List<Long> iotDeviceIds);

    /**
     * 查询设备列表（注册）
     *
     * @param iotProtocolId
     * @param deviceCode
     * @param registerStatus
     * @param pageIndex
     * @param pageSize
     * @return
     */
    List<IotDeviceDTO> getIotDeviceByRegister(Long iotProtocolId, String deviceCode, Boolean registerStatus,
                                              Integer pageIndex, Integer pageSize);

    /**
     * 根据业务编码查询协议
     *
     * @param bizCode 业务编码
     * @return 设备协议
     */
    IotIovProtocolCodeQueryDto getProtocolByBizCode(String bizCode);

    /**
     * 自定义匹配字段查询设备列表
     *
     * @param iotDeviceListBaseQueryDTO
     * @return
     */
    List<IotDeviceQueryDTO> getDeviceListCustom(IotDeviceListBaseQueryDTO iotDeviceListBaseQueryDTO);
}

package allthings.iot.ktv.dao;

import allthings.iot.common.msg.DeviceDataMsg;
import allthings.iot.ktv.common.dto.ComparisonCriteriaDto;
import allthings.iot.ktv.common.dto.GpsDeviceDto;
import allthings.iot.ktv.common.dto.PageQueryDto;
import allthings.iot.ktv.common.dto.PageResultDto;

import java.util.List;
import java.util.Map;

/**
 * @author :  luhao
 * @FileName :  IKtvDataDao
 * @CreateDate :  2018/1/18
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
public interface IKtvDataDao {
    /**
     * 保存ktv数据
     *
     * @param deviceMsg
     */
    void saveKtvData(DeviceDataMsg deviceMsg);


    /**
     * 查询因子值
     *
     * @param partyId
     * @param deviceId
     * @param startDatetime
     * @param endDatetime
     * @param factors
     * @return
     */
    List<Map<String, String>> queryKv(String partyId, String deviceId, Long startDatetime, Long endDatetime,
                                      String[] factors);


    /**
     * 查询最新因子值
     *
     * @param partyId
     * @param deviceId
     * @param factors
     * @return
     */
    List<Map<String, String>> queryKvLatest(String partyId, String deviceId, String[] factors);

    /**
     * 查询因子最新数据
     *
     * @param partyId
     * @param rowKey
     * @return
     */
    Map<String, String> queryKvLast(String partyId, String rowKey);

    /**
     * 根据过滤条件查询最新因子值
     *
     * @param partyId
     * @param deviceId
     * @param factors
     * @param comparisonCriteriaDtoList
     * @return
     */
    List<Map<String, String>> queryKvCriteriaLatest(String partyId, String deviceId, String[] factors,
                                                    List<ComparisonCriteriaDto> comparisonCriteriaDtoList);

    /**
     * 查询最大值
     *
     * @param partyId
     * @param deviceId
     * @param startDatetime
     * @param endDatetime
     * @param factor
     * @return
     * @throws Throwable
     */
    Map<String, String> queryMax(String partyId, String deviceId, Long startDatetime, Long endDatetime, String
            factor) throws
            Throwable;

    /**
     * 查询第一条/最后一条
     *
     * @param partyId
     * @param deviceId
     * @param startDatetime
     * @param endDatetime
     * @param factor
     * @return
     */
    Map<String, String> queryFirstLast(String partyId, String deviceId, Long startDatetime, Long endDatetime, String[]
            factor, boolean isFirst);


    /**
     * 保存ktv数据
     *
     * @param gpsDeviceDto
     */
    void saveKtvData(GpsDeviceDto gpsDeviceDto);


    /**
     * 查询停车时间统计
     *
     * @param deviceId
     * @param startDatetime
     * @param endDatetime
     * @return
     */
    List<Map<String, String>> queryStopTime(String deviceId, Long startDatetime, Long endDatetime);


    /**
     * 查询里程油耗统计
     *
     * @param deviceId
     * @param startDatetime
     * @param endDatetime
     * @return
     */
    List<Map<String, String>> queryOilMileage(String deviceId, Long startDatetime, Long endDatetime);

    /**
     * 分页查询设备因子数据
     *
     * @param queryDto
     * @return
     */
    PageResultDto queryKvPage(PageQueryDto queryDto);

}

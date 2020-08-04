package allthings.iot.ktv.api;

import allthings.iot.common.dto.Result;
import allthings.iot.common.msg.DeviceDataMsg;
import allthings.iot.ktv.common.dto.BatchQueryDto;
import allthings.iot.ktv.common.dto.GpsDeviceDto;
import allthings.iot.ktv.common.dto.IotKtvFactorReportTimeDto;
import allthings.iot.ktv.common.dto.PageQueryDto;
import allthings.iot.ktv.common.dto.PageResultDto;
import allthings.iot.ktv.common.dto.QueryDto;
import allthings.iot.ktv.common.dto.QueryListCriteriaDto;

import java.util.List;
import java.util.Map;

/**
 * @author :  luhao
 * @FileName :  IKtvDataService
 * @CreateDate :  2018/1/17
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
public interface IKtvDataService {

    /**
     * 查询因子值
     *
     * @param queryDto 查询参数
     * @return 查询结果
     */
    Result<List<Map<String, String>>> queryKv(QueryDto queryDto);

    /**
     * 分页查询因子值
     *
     * @param queryDto 查询参数
     * @return 查询结果
     */
    Result<PageResultDto> queryKvPage(PageQueryDto queryDto);

    /**
     * 查询最新因子值
     *
     * @param queryDto 查询参数
     * @return 查询结果
     */
    Result<List<Map<String, String>>> queryKvLatest(QueryDto queryDto);

    /**
     * 查询最大第一条或者最后一条数据
     *
     * @param queryDto 查询参数
     * @param isFirst  是否第一条
     * @return 查询结果
     */
    Result<Map<String, String>> queryFirstLast(QueryDto queryDto, boolean isFirst);

    /**
     * 查询第一条数据
     *
     * @param queryDto 查询参数
     * @return 查询结果
     */
    Result<List<Map<String, Object>>> queryFirst(QueryDto queryDto);

    /**
     * 查询最后一条数据
     *
     * @param queryDto 查询参数
     * @return 查询结果
     */
    Result<List<Map<String, Object>>> queryLast(QueryDto queryDto);

    /**
     * 根据条件查询最后一条符合的数据
     *
     * @param queryListCriteriaDto 查询参数
     * @return 查询结果
     */
    @Deprecated
    Result<Map<String, List<Map<String, String>>>> queryKvCriteriaLatest(QueryListCriteriaDto queryListCriteriaDto);

    /**
     * 查询某个设备的因子在指定时间范围内的最大值
     *
     * @param queryDto 查询参数
     * @return 查询结果
     */
    Result<Map<String, String>> queryMax(QueryDto queryDto);

    /**
     * 获取时间段内的行数
     *
     * @param startDatetime 开始时间
     * @param endDatetime   结束时间
     * @return 查询结果
     */
    Result<Long> getRowCountByTimeRange(Long startDatetime, Long endDatetime);

    /**
     * 获取点位数量
     *
     * @return
     */
    Result<Map<String, String>> getPointCount();

    /**
     * 重置点位数量
     *
     * @return 查询结果
     */
    Result<?> resetPointCount();

    /**
     * 保存因子数据
     *
     * @param gpsDeviceDto 因子数据
     */
    void saveKtvData(GpsDeviceDto gpsDeviceDto);

    /**
     * 批量查询最后一条数据
     *
     * @param batchQueryDto 查询参数
     * @return 查询结果
     */
    Result<List<Map<String, String>>> batchQueryKvLatest(BatchQueryDto batchQueryDto);


    /**
     * 保存因子最新上报时间
     *
     * @param dataMsg 因子数据
     */
    void saveOrUpdateReportTime(DeviceDataMsg dataMsg);

    /**
     * 保存ktv数据
     *
     * @param dataMsg
     */
    void handleKtvData(DeviceDataMsg dataMsg);

    /**
     * 查询最新因子值
     *
     * @param queryDto
     * @return
     */
    Result<List<Map<String, String>>> queryKvLast(QueryDto queryDto);

    /**
     * 批量查询设备最新因子数据
     *
     * @param batchQueryDto
     * @return
     */
    Result<Map<String, List<Map<String, String>>>> batchQueryKvLast(BatchQueryDto batchQueryDto);

    /**
     * 批量查询设备最后上传因子数据时间
     *
     * @param deviceIds
     * @return
     */
    Result<List<IotKtvFactorReportTimeDto>> batchQueryDeviceLastReportTime(List<String> deviceIds);

    /**
     * 查询里程油耗列表
     *
     * @param deviceCodes
     * @param startDatetime
     * @param endDatetime
     * @return
     */
    Result<List<Map<String, Object>>> queryOilMileageList(List<String> deviceCodes, Long startDatetime, Long endDatetime);

    /**
     * 查询停车时长列表
     *
     * @param deviceCodes
     * @param startDatetime
     * @param endDatetime
     * @return
     */
    Result<List<Map<String, Object>>> queryStopTimeList(List<String> deviceCodes, Long startDatetime, Long endDatetime);


    /**
     * 根据业务编码查询设备历史数据列表
     *
     * @param queryDto
     * @return
     */
    Result<List<Map<String, String>>> queryKvByBusiness(QueryDto queryDto);

    /**
     * 根据业务编码查询设备最新数据
     *
     * @param queryDto
     * @return
     */
    Result<List<Map<String, String>>> queryBusinessLast(QueryDto queryDto);

}
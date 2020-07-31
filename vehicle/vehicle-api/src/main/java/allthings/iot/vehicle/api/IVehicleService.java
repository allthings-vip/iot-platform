package allthings.iot.vehicle.api;

import allthings.iot.common.dto.QueryResult;
import allthings.iot.common.dto.Result;
import allthings.iot.ktv.common.dto.OilQueryDto;
import allthings.iot.ktv.common.dto.OilResultDto;
import allthings.iot.ktv.common.dto.PageResultDto;
import allthings.iot.ktv.common.dto.QueryDto;
import allthings.iot.ktv.common.dto.QueryListCriteriaDto;
import allthings.iot.vehicle.common.dto.GpsDto;
import allthings.iot.vehicle.common.dto.GpsFenceDto;
import allthings.iot.vehicle.common.dto.GpsFenceTaskDto;
import allthings.iot.vehicle.common.dto.GpsLQueryDto;
import allthings.iot.vehicle.common.dto.GpsQueryDto;
import allthings.iot.vehicle.common.dto.SaveDeviceDto;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author :  luhao
 * @FileName :  IVehicleService
 * @CreateDate :  2018/1/15
 * @Description : 车辆数据服务接口
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
public interface IVehicleService {
    /**
     * 保存gps数据
     *
     * @param gpsList
     * @return Result
     * @throws Exception io
     */
    Result<?> saveGps(List<GpsDto> gpsList) throws Exception;

    /**
     * 查询gps数据
     *
     * @param gpsQueryDto
     * @return
     */
    Result<QueryResult<Map<String, String>>> queryGpsList(GpsQueryDto gpsQueryDto) throws Exception;

    /**
     * 查询gps数据(未处理停车时间)
     *
     * @param gpsQueryDto
     * @return
     */
    Result<QueryResult<Map<String, String>>> queryUnHandStopTimeGpsList(GpsQueryDto gpsQueryDto) throws Exception;

    /**
     * 查询实时数据
     *
     * @param gpsLQueryDto
     * @return
     * @throws IOException
     */
    Result<QueryResult<Map<String, String>>> queryGpsLatest(GpsLQueryDto gpsLQueryDto) throws Exception;

    /**
     * 保存电子围栏数据
     *
     * @param gpsFenceDto
     * @return
     */
    Result<?> saveOrUpdateFence(GpsFenceDto gpsFenceDto) throws Exception;

    /**
     * 查询电子围栏数据
     *
     * @param entityId
     * @return
     */
    Result<?> queryFence(String entityId) throws Exception;

    /**
     * 新增自定义围栏任务接口
     *
     * @param gpsFenceTaskDto
     * @return
     */
    Result<?> saveFenceTask(GpsFenceTaskDto gpsFenceTaskDto) throws Exception;

    /**
     * 查询油耗
     *
     * @param oilQueryDto
     * @return
     */
    Result<OilResultDto> queryOilFuel(OilQueryDto oilQueryDto);

    /**
     * 查询最新的kv数据
     *
     * @param queryDto
     * @return
     */
    Result<Map.Entry<String, List<Map<String, Object>>>> queryLatest(QueryDto queryDto);

    /**
     * 批量查询最新的kv数据
     *
     * @param queryListCriteriaDto
     * @return
     */
    Result<Map.Entry<String, List<Map<String, Object>>>> queryBathLatest(QueryListCriteriaDto queryListCriteriaDto);

    /**
     * 查询里程
     *
     * @param queryDto
     * @return
     */
    Result<Map<String, Double>> queryMileage(QueryDto queryDto);

    /**
     * 查询油量
     *
     * @param queryDto
     * @return
     */
    Result<Map<String, Double>> queryOil(QueryDto queryDto);

    /**
     * 保存车机数据
     *
     * @param saveDeviceDto
     * @return
     */
    Result<?> saveDevice(SaveDeviceDto saveDeviceDto);

    /**
     * 查询最新的kv数据
     *
     * @param queryDto
     * @return
     */
    Result<Map.Entry<String, List<Map<String, Object>>>> queryKvLast(QueryDto queryDto);

    /**
     * 批量查询最新的kv数据
     *
     * @param queryListCriteriaDto
     * @return
     */
    Result<Map.Entry<String, List<Map<String, Object>>>> queryBathLast(QueryListCriteriaDto queryListCriteriaDto);

    /**
     * 车贷管家接口，GPS状态查询
     *
     * @param deviceCode
     * @return
     */
    Result<?> getGpsStatus(String deviceCode);

    /**
     * 根据业务编码查询设备轨迹列表
     *
     * @param gpsQueryDto
     * @return
     */
    Result<List<Map<String, String>>> getBusinessList(GpsQueryDto gpsQueryDto);

    /**
     * 根据业务编码查询设备最新数据
     *
     * @param gpsQueryDto
     * @return
     */
    Result<Map<String, String>> getBusinessLatesPoint(GpsQueryDto gpsQueryDto);

    /**
     * 查询车辆出车记录
     *
     * @param gpsQueryDto
     * @return
     */
    Result<?> getDepartureRecord(GpsQueryDto gpsQueryDto);

    /**
     * 分页查询轨迹数据
     *
     * @param queryDto 查询参数
     * @return 查询结果
     */
    Result<PageResultDto> queryGpsListPage(GpsQueryDto queryDto);

}

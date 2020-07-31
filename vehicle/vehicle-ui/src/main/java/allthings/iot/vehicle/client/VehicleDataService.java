package allthings.iot.vehicle.client;

import allthings.iot.common.dto.QueryResult;
import allthings.iot.common.dto.Result;
import allthings.iot.ktv.common.dto.OilQueryDto;
import allthings.iot.ktv.common.dto.OilResultDto;
import allthings.iot.ktv.common.dto.PageResultDto;
import allthings.iot.ktv.common.dto.QueryDto;
import allthings.iot.ktv.common.dto.QueryListCriteriaDto;
import allthings.iot.vehicle.client.hystrix.VehicleDataServiceCallBack;
import allthings.iot.vehicle.common.dto.GpsDto;
import allthings.iot.vehicle.common.dto.GpsFenceDto;
import allthings.iot.vehicle.common.dto.GpsFenceTaskDto;
import allthings.iot.vehicle.common.dto.GpsLQueryDto;
import allthings.iot.vehicle.common.dto.GpsQueryDto;
import allthings.iot.vehicle.common.dto.SaveDeviceDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: fengchangxin
 * @create: 2019-05-06 18:12
 */
@FeignClient(name = "vehicle-data", fallback = VehicleDataServiceCallBack.class)
public interface VehicleDataService {
    /**
     * 保存gps数据
     *
     * @param gpsList
     * @return
     * @throws Exception
     */
    @PostMapping("/gps/save")
    Result<?> saveGps(@RequestBody List<GpsDto> gpsList) throws Exception;

    /**
     * 查询gps数据
     *
     * @param gpsQueryDto
     * @return
     * @throws Exception
     */
    @PostMapping("/gps/getList")
    Result<QueryResult<Map<String, String>>> queryGpsList(@RequestBody GpsQueryDto gpsQueryDto) throws Exception;

    /**
     * 查询gps数据(未处理停车时间)
     *
     * @param gpsQueryDto
     * @return
     * @throws Exception
     */
    @PostMapping("/gps/stopTime/getList")
    Result<QueryResult<Map<String, String>>> queryUnHandStopTimeGpsList(@RequestBody GpsQueryDto gpsQueryDto) throws Exception;

    /**
     * 查询实时数据
     *
     * @param gpsLQueryDto
     * @return
     * @throws Exception
     */
    @PostMapping("/gps/getLatestPoint")
    Result<QueryResult<Map<String, String>>> queryGpsLatest(@RequestBody GpsLQueryDto gpsLQueryDto) throws Exception;

    /**
     * 保存电子围栏数据
     *
     * @param gpsFenceDto
     * @return
     * @throws Exception
     */
    @PostMapping("/fence/saveOrUpdate")
    Result<?> saveOrUpdateFence(@RequestBody GpsFenceDto gpsFenceDto) throws Exception;

    /**
     * 查询电子围栏数据
     *
     * @param entityId
     * @return
     * @throws Exception
     */
    @GetMapping("/fence/getById")
    Result<?> queryFence(@RequestParam("entityId") String entityId) throws Exception;

    /**
     * 新增自定义围栏任务接口
     *
     * @param gpsFenceTaskDto
     * @return
     * @throws Exception
     */
    @PostMapping("/fence/task/saveOrUpdate")
    Result<?> saveFenceTask(@RequestBody GpsFenceTaskDto gpsFenceTaskDto) throws Exception;

    /**
     * 查询油耗
     *
     * @param oilQueryDto
     * @return
     */
    @PostMapping("/fuel")
    Result<OilResultDto> queryOilFuel(@RequestBody OilQueryDto oilQueryDto);

    /**
     * 查询最新的kv数据
     *
     * @param queryDto
     * @return
     */
    @PostMapping("/latest")
    Result<Map.Entry<String, List<Map<String, Object>>>> queryLatest(@RequestBody QueryDto queryDto);

    /**
     * 批量查询最新的kv数据
     *
     * @param queryListCriteriaDto
     * @return
     */
    @PostMapping("/batch/latest")
    Result<Map.Entry<String, List<Map<String, Object>>>> queryBathLatest(@RequestBody QueryListCriteriaDto queryListCriteriaDto);

    /**
     * 查询里程
     *
     * @param queryDto
     * @return
     */
    @PostMapping("/mileage")
    Result<Map<String, Double>> queryMileage(@RequestBody QueryDto queryDto);

    /**
     * 查询油量
     *
     * @param queryDto
     * @return
     */
    @PostMapping("/oil")
    Result<Map<String, Double>> queryOil(@RequestBody QueryDto queryDto);

    /**
     * 保存车机数据
     *
     * @param saveDeviceDto
     * @return
     */
    @PostMapping("/device/save")
    Result<?> saveDevice(@RequestBody SaveDeviceDto saveDeviceDto);

    /**
     * 查询最新的kv数据
     *
     * @param queryDto
     * @return
     */
    @PostMapping("/getLast")
    Result<Map.Entry<String, List<Map<String, Object>>>> queryKvLast(@RequestBody QueryDto queryDto);

    /**
     * 批量查询最新的kv数据
     *
     * @param queryListCriteriaDto
     * @return
     */
    @PostMapping("/batch/last")
    Result<Map.Entry<String, List<Map<String, Object>>>> queryBathLast(@RequestBody QueryListCriteriaDto queryListCriteriaDto);


    /**
     * 车贷管家接口，GPS状态查询
     *
     * @param deviceCodes
     * @return
     */
    @GetMapping("/getGpsStatus")
    Result<?> getGpsStatus(@RequestParam("deviceCodes") String deviceCodes);

    /**
     * 根据业务编码查询设备轨迹列表
     *
     * @param gpsQueryDto
     * @return
     */
    @PostMapping("/business/getList")
    Result<List<Map<String, String>>> getBusinessList(@RequestBody GpsQueryDto gpsQueryDto);


    /**
     * 根据业务编码查询设备最新数据
     *
     * @param gpsQueryDto
     * @return
     */
    @PostMapping("/business/getLatesPoint")
    Result<Map<String, String>> getBusinessLatesPoint(@RequestBody GpsQueryDto gpsQueryDto);


    /**
     * 查询车辆出车记录
     *
     * @param gpsQueryDto
     * @return
     */
    @PostMapping("/business/getDepartureRecord")
    Result<?> getDepartureRecord(@RequestBody GpsQueryDto gpsQueryDto);

    /**
     * 分页查询历史轨迹
     *
     * @param queryDto 查询参数
     * @return 查询结果
     */
    @PostMapping("/gps/getGpsListPage")
    Result<PageResultDto> queryGpsListPage(GpsQueryDto queryDto);
}

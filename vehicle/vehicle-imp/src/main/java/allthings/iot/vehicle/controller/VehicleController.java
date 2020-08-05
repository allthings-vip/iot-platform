package allthings.iot.vehicle.controller;

import allthings.iot.common.dto.QueryResult;
import allthings.iot.common.dto.Result;
import allthings.iot.ktv.common.dto.OilQueryDto;
import allthings.iot.ktv.common.dto.OilResultDto;
import allthings.iot.ktv.common.dto.PageResultDto;
import allthings.iot.ktv.common.dto.QueryDto;
import allthings.iot.ktv.common.dto.QueryListCriteriaDto;
import allthings.iot.vehicle.api.IVehicleService;
import allthings.iot.vehicle.client.VehicleDataApi;
import allthings.iot.vehicle.common.dto.GpsLQueryDto;
import allthings.iot.vehicle.common.dto.GpsQueryDto;
import allthings.iot.vehicle.common.dto.SaveDeviceDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: fengchangxin
 * @create: 2019-05-06 11:36
 */
@RestController
public class VehicleController implements VehicleDataApi {
    private static final Logger LOGGER = LoggerFactory.getLogger(VehicleController.class);
    @Autowired
    private IVehicleService vehicleData;

    public Result<QueryResult<Map<String, String>>> queryUnHandStopTimeGpsList(@RequestBody GpsQueryDto gpsQueryDto) {
        try {
            return vehicleData.queryUnHandStopTimeGpsList(gpsQueryDto);
        } catch (Exception e) {
            LOGGER.error("查询异常", e);
            return Result.newFaild("查询异常");
        }
    }

    public Result<QueryResult<Map<String, String>>> queryGpsList(@RequestBody GpsQueryDto gpsQueryDto) {
        try {
            return vehicleData.queryGpsList(gpsQueryDto);
        } catch (Exception e) {
            LOGGER.error("查询异常", e);
            return Result.newFaild("查询异常");
        }
    }

    public Result<QueryResult<Map<String, String>>> queryGpsLatest(@RequestBody GpsLQueryDto lQueryDto) {
        try {
            return vehicleData.queryGpsLatest(lQueryDto);
        } catch (Exception ex) {
            LOGGER.error("查询异常", ex);
            return Result.newFaild("查询异常");
        }
    }

    public Result<OilResultDto> queryOilFuel(@RequestBody OilQueryDto oilQueryDto) {
        try {
            return vehicleData.queryOilFuel(oilQueryDto);
        } catch (IllegalArgumentException ee) {
            LOGGER.error("查询油量信息异常", ee);
            return Result.newFaild(ee.getMessage());
        } catch (Exception ex) {
            LOGGER.error("查询油量信息异常", ex);
            return Result.newFaild("查询油量信息异常");
        }
    }

    public Result<Map.Entry<String, List<Map<String, Object>>>> queryLatest(@RequestBody QueryDto queryDto) {
        try {
            return vehicleData.queryLatest(queryDto);
        } catch (IllegalArgumentException ee) {
            LOGGER.error("查询最新KV数据信息异常", ee);
            return Result.newFaild(ee.getMessage());
        } catch (Exception ex) {
            LOGGER.error("查询最新KV数据信息异常", ex);
            return Result.newFaild("查询最新KV数据信息异常");
        }
    }

    public Result<Map.Entry<String, List<Map<String, Object>>>> queryBathLatest(@RequestBody QueryListCriteriaDto queryListCriteriaDto) {
        try {
            return vehicleData.queryBathLatest(queryListCriteriaDto);
        } catch (IllegalArgumentException ee) {
            LOGGER.error("查询最新KV数据异常", ee);
            return Result.newFaild(ee.getMessage());
        } catch (Exception ex) {
            LOGGER.error("查询最新KV数据异常", ex);
            return Result.newFaild("查询最新KV数据异常");
        }
    }

    public Result<Map<String, Double>> queryMileage(@RequestBody QueryDto queryDto) {
        try {
            return vehicleData.queryMileage(queryDto);
        } catch (IllegalArgumentException ee) {
            LOGGER.error("查询里程数据异常", ee);
            return Result.newFaild(ee.getMessage());
        } catch (Exception ex) {
            LOGGER.error("查询里程数据异常", ex);
            return Result.newFaild("查询里程数据异常");
        }
    }

    public Result<Map<String, Double>> queryOil(@RequestBody QueryDto queryDto) {
        try {
            return vehicleData.queryOil(queryDto);
        } catch (IllegalArgumentException ee) {
            LOGGER.error("查询油量数据异常", ee);
            return Result.newFaild(ee.getMessage());
        } catch (Exception ex) {
            LOGGER.error("查询油量数据异常", ex);
            return Result.newFaild("查询油量数据异常");
        }
    }

    public Result<?> saveDevice(@RequestBody SaveDeviceDto saveDeviceDto) {
        try {
            return vehicleData.saveDevice(saveDeviceDto);
        } catch (Exception ex) {
            LOGGER.error("保存车机数据失败", ex);
            return Result.newFaild("保存车机数据失败");
        }
    }

    public Result<Map.Entry<String, List<Map<String, Object>>>> queryKvLast(@RequestBody QueryDto queryDto) {
        try {
            return vehicleData.queryKvLast(queryDto);
        } catch (IllegalArgumentException ee) {
            LOGGER.error("查询最新KV数据信息异常", ee);
            return Result.newFaild(ee.getMessage());
        } catch (Exception ex) {
            LOGGER.error("查询最新KV数据信息异常", ex);
            return Result.newFaild("查询最新KV数据信息异常");
        }
    }

    public Result<Map.Entry<String, List<Map<String, Object>>>> queryBathLast(@RequestBody QueryListCriteriaDto queryListCriteriaDto) {
        try {
            return vehicleData.queryBathLast(queryListCriteriaDto);
        } catch (IllegalArgumentException ee) {
            LOGGER.error("查询最新KV数据异常", ee);
            return Result.newFaild(ee.getMessage());
        } catch (Exception ex) {
            LOGGER.error("查询最新KV数据异常", ex);
            return Result.newFaild("查询最新KV数据异常");
        }
    }

    public Result<List<Map<String, String>>> getBusinessList(@RequestBody GpsQueryDto gpsQueryDto) {
        try {
            return vehicleData.getBusinessList(gpsQueryDto);
        } catch (Exception e) {
            LOGGER.error("查询车辆轨迹列表失败", e);
            return Result.newFaild("查询车辆轨迹列表失败");
        }
    }

    public Result<Map<String, String>> getBusinessLatesPoint(@RequestBody GpsQueryDto gpsQueryDto) {
        try {
            return vehicleData.getBusinessLatesPoint(gpsQueryDto);
        } catch (Exception e) {
            LOGGER.error("查询车辆最新数据失败", e);
            return Result.newFaild("查询车辆最新数据失败");
        }
    }

    public Result<?> getDepartureRecord(@RequestBody GpsQueryDto gpsQueryDto) {
        try {
            return vehicleData.getDepartureRecord(gpsQueryDto);
        } catch (Exception e) {
            LOGGER.error("查询车辆出车记录失败", e);
            return Result.newFaild("查询车辆出车记录失败");
        }
    }

    public Result<PageResultDto> queryGpsListPage(@RequestBody GpsQueryDto queryDto) {
        try {
            return vehicleData.queryGpsListPage(queryDto);
        } catch (Exception e) {
            LOGGER.error("分页查询轨迹失败，参数：{}，错误信息：{}", queryDto, e);
            return Result.newFaild("查询轨迹失败");
        }
    }
}

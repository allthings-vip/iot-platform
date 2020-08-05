package allthings.iot.vehicle.client.hystrix;

import org.springframework.stereotype.Component;
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
import allthings.iot.vehicle.client.VehicleDataApi;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: fengchangxin
 * @create: 2019-05-06 18:13
 */
@Component
public class VehicleDataApiCallBack implements VehicleDataApi {
    @Override
    public Result<QueryResult<Map<String, String>>> queryGpsList(GpsQueryDto gpsQueryDto) {
        return CallBackError.CALLBACK_ERROR;
    }

    @Override
    public Result<QueryResult<Map<String, String>>> queryGpsLatest(GpsLQueryDto gpsLQueryDto) {
        return CallBackError.CALLBACK_ERROR;
    }
    @Override
    public Result<QueryResult<Map<String, String>>> queryUnHandStopTimeGpsList(GpsQueryDto gpsQueryDto) {
        return CallBackError.CALLBACK_ERROR;
    }

    @Override
    public Result<OilResultDto> queryOilFuel(OilQueryDto oilQueryDto) {
        return CallBackError.CALLBACK_ERROR;
    }

    @Override
    public Result<Map.Entry<String, List<Map<String, Object>>>> queryLatest(QueryDto queryDto) {
        return CallBackError.CALLBACK_ERROR;
    }

    @Override
    public Result<Map.Entry<String, List<Map<String, Object>>>> queryBathLatest(QueryListCriteriaDto queryListCriteriaDto) {
        return CallBackError.CALLBACK_ERROR;
    }

    @Override
    public Result<Map<String, Double>> queryMileage(QueryDto queryDto) {
        return CallBackError.CALLBACK_ERROR;
    }

    @Override
    public Result<Map<String, Double>> queryOil(QueryDto queryDto) {
        return CallBackError.CALLBACK_ERROR;
    }

    @Override
    public Result<?> saveDevice(SaveDeviceDto saveDeviceDto) {
        return CallBackError.CALLBACK_ERROR;
    }

    @Override
    public Result<Map.Entry<String, List<Map<String, Object>>>> queryKvLast(QueryDto queryDto) {
        return CallBackError.CALLBACK_ERROR;
    }

    @Override
    public Result<Map.Entry<String, List<Map<String, Object>>>> queryBathLast(QueryListCriteriaDto queryListCriteriaDto) {
        return CallBackError.CALLBACK_ERROR;
    }

    @Override
    public Result<List<Map<String, String>>> getBusinessList(GpsQueryDto gpsQueryDto) {
        return CallBackError.CALLBACK_ERROR;
    }

    @Override
    public Result<Map<String, String>> getBusinessLatesPoint(GpsQueryDto gpsQueryDto) {
        return CallBackError.CALLBACK_ERROR;
    }

    @Override
    public Result<?> getDepartureRecord(GpsQueryDto gpsQueryDto) {
        return CallBackError.CALLBACK_ERROR;
    }

    @Override
    public Result<PageResultDto> queryGpsListPage(GpsQueryDto queryDto) {
        return CallBackError.CALLBACK_ERROR;
    }
}

package allthings.iot.dos.open.service;

import allthings.iot.common.dto.PageResult;
import allthings.iot.common.dto.QueryResult;
import allthings.iot.common.dto.Result;
import allthings.iot.common.dto.ResultDTO;
import allthings.iot.constant.gps.GpsMsgParam;
import allthings.iot.dos.api.IotDeviceService;
import allthings.iot.dos.api.IotProjectService;
import allthings.iot.dos.constant.ErrorCode;
import allthings.iot.dos.dto.open.IotGpsDTO;
import allthings.iot.dos.dto.open.IotGpsListDTO;
import allthings.iot.dos.dto.open.IotGpsPageDto;
import allthings.iot.dos.dto.open.IotKtvFactorDTO;
import allthings.iot.dos.dto.open.IotQueryConditionDTO;
import allthings.iot.dos.dto.open.IotSaveDeviceDTO;
import allthings.iot.dos.dto.open.IotSimpleQueryDTO;
import allthings.iot.dos.dto.open.IotTrackPageQueryDto;
import allthings.iot.dos.dto.open.IotTrackQueryListDTO;
import allthings.iot.dos.dto.open.IotVehicleHistoryQueryDTO;
import allthings.iot.dos.dto.open.IotVehicleLastestBatchDTO;
import allthings.iot.dos.dto.query.IotDeviceQueryDTO;
import allthings.iot.dos.open.api.IotOpenVehicleService;
import allthings.iot.dos.open.api.IotProjectUtilService;
import allthings.iot.ktv.client.KtvDataClient;
import allthings.iot.ktv.common.dto.ComparisonCriteriaDto;
import allthings.iot.ktv.common.dto.OilQueryDto;
import allthings.iot.ktv.common.dto.OilResultDto;
import allthings.iot.ktv.common.dto.PageResultDto;
import allthings.iot.ktv.common.dto.QueryDto;
import allthings.iot.ktv.common.dto.QueryListCriteriaDto;
import allthings.iot.vehicle.client.VehicleDataApi;
import allthings.iot.vehicle.common.dto.GpsDto;
import allthings.iot.vehicle.common.dto.GpsQueryDto;
import allthings.iot.vehicle.common.dto.SaveDeviceDto;
import allthings.iot.vehicle.common.dto.SaveKtvFactorDto;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author :  txw
 * @FileName :  IotOpenVehicleBizImpl
 * @CreateDate :  2018/11/12
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
@Service("iotOpenVehicleBiz")
public class IotOpenVehicleServiceImpl implements IotOpenVehicleService {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
    private static Logger LOGGER = LoggerFactory.getLogger(IotOpenVehicleServiceImpl.class);
    @Autowired
    private VehicleDataApi vehicleDataService;
    @Autowired
    private KtvDataClient ktvDataClient;
    @Autowired
    private IotProjectUtilService iotProjectUtilBiz;
    @Autowired
    private IotProjectService iotProjectService;
    @Autowired
    private IotDeviceService iotDeviceService;
    @Value("${upload.max.point}")
    private Integer maxPoint;

    @Override
    public ResultDTO<PageResult<IotGpsDTO>> getTrackList(IotTrackQueryListDTO iotTrackQueryListDTO) {
        try {
            if (StringUtils.isEmpty(iotTrackQueryListDTO.getDeviceCode())) {
                return ResultDTO.newFail(ErrorCode.ERROR_3007.getCode(),
                        ErrorCode.ERROR_3007.getMessage());
            }
            if (iotTrackQueryListDTO.getStartDatetime() == null || iotTrackQueryListDTO.getEndDatetime() == null) {
                return ResultDTO.newFail(ErrorCode.ERROR_5052.getCode(),
                        ErrorCode.ERROR_5052.getMessage());
            }
            if (iotTrackQueryListDTO.getPageSize() == null || iotTrackQueryListDTO.getPageIndex() == null) {
                return ResultDTO.newFail(ErrorCode.ERROR_5053.getCode(),
                        ErrorCode.ERROR_5053.getMessage());
            }
            ResultDTO<Long> bizReturn = iotProjectUtilBiz.hasDevice(iotTrackQueryListDTO.getClientId(),
                    Lists.newArrayList(iotTrackQueryListDTO.getDeviceCode()), true);
            if (!bizReturn.isSuccess()) {
                return ResultDTO.newFail(bizReturn.getCode(), bizReturn.getMsg());
            }
            GpsQueryDto gpsQueryDto = new GpsQueryDto();
            gpsQueryDto.setDeviceid(iotTrackQueryListDTO.getDeviceCode());
            gpsQueryDto.setFormat(iotTrackQueryListDTO.getCoordinate());
            gpsQueryDto.setStarttime(getTime(iotTrackQueryListDTO.getStartDatetime()));
            gpsQueryDto.setEndtime(getTime(iotTrackQueryListDTO.getEndDatetime()));
            gpsQueryDto.setPartyid(-100);
            gpsQueryDto.setPageSize(iotTrackQueryListDTO.getPageSize());
            gpsQueryDto.setSkipCount((iotTrackQueryListDTO.getPageIndex() - 1) * iotTrackQueryListDTO.getPageSize());
            Result<QueryResult<Map<String, String>>> result =
                    vehicleDataService.queryUnHandStopTimeGpsList(gpsQueryDto);
            if (result.getRet() == null || result.getRc() != 0) {
                LOGGER.warn("查询轨迹错误：{}", JSON.toJSONString(result));
                return ResultDTO.newFail("查询结果为空");
            }
            QueryResult<Map<String, String>> queryResult = result.getRet();
            List<Map<String, String>> resultList = queryResult.getItems();
            List<IotGpsDTO> ret = new ArrayList<>();
            for (Map<String, String> map : resultList) {
                if (StringUtils.isBlank(map.get("latitude")) || StringUtils.isBlank(map.get("longitude"))) {
                    continue;
                }
                IotGpsDTO iotGpsDTO = processResult(map);
                iotGpsDTO.setRealtime(DATE_TIME_FORMATTER.parseMillis(map.get("realtime")));
                ret.add(iotGpsDTO);
            }

            PageResult<IotGpsDTO> pageResult = new PageResult<>((int) queryResult.getRowCount(), ret);
            return ResultDTO.newSuccess(pageResult);
        } catch (Exception e) {
            LOGGER.error(String.format("查询异常，参数：%s，异常：", JSON.toJSONString(iotTrackQueryListDTO)), e);
            return ResultDTO.newFail("查询失败");
        }
    }

    @Override
    public ResultDTO<PageResult<Map<String, String>>> getTrackDuplicateList(IotTrackQueryListDTO iotTrackQueryListDTO) {
        try {
            if (StringUtils.isEmpty(iotTrackQueryListDTO.getDeviceCode())) {
                return ResultDTO.newFail(ErrorCode.ERROR_3007.getCode(),
                        ErrorCode.ERROR_3007.getMessage());
            }
            if (iotTrackQueryListDTO.getStartDatetime() == null || iotTrackQueryListDTO.getEndDatetime() == null) {
                return ResultDTO.newFail(ErrorCode.ERROR_5052.getCode(),
                        ErrorCode.ERROR_5052.getMessage());
            }
            if (iotTrackQueryListDTO.getPageSize() == null || iotTrackQueryListDTO.getPageIndex() == null) {
                return ResultDTO.newFail(ErrorCode.ERROR_5053.getCode(),
                        ErrorCode.ERROR_5053.getMessage());
            }
            ResultDTO<Long> bizReturn = iotProjectUtilBiz.hasDevice(iotTrackQueryListDTO.getClientId(),
                    Lists.newArrayList(iotTrackQueryListDTO.getDeviceCode()), true);
            if (!bizReturn.isSuccess()) {
                return ResultDTO.newFail(bizReturn.getCode(), bizReturn.getMsg());
            }
            GpsQueryDto gpsQueryDto = new GpsQueryDto();
            gpsQueryDto.setDeviceid(iotTrackQueryListDTO.getDeviceCode());
            gpsQueryDto.setFormat(iotTrackQueryListDTO.getCoordinate());
            gpsQueryDto.setStarttime(getTime(iotTrackQueryListDTO.getStartDatetime()));
            gpsQueryDto.setEndtime(getTime(iotTrackQueryListDTO.getEndDatetime()));
            gpsQueryDto.setPartyid(-100);
            gpsQueryDto.setPageSize(iotTrackQueryListDTO.getPageSize());
            gpsQueryDto.setSkipCount((iotTrackQueryListDTO.getPageIndex() - 1) * iotTrackQueryListDTO.getPageSize());
            QueryResult<Map<String, String>> queryResult = vehicleDataService.queryGpsList(gpsQueryDto).getRet();
            List<Map<String, String>> resultList = queryResult.getItems();
            for (Map<String, String> map : resultList) {
                map.put("deviceCode", map.get("deviceid"));
                map.remove("deviceid");
            }

            PageResult<Map<String, String>> pageResult = new PageResult<>((int) queryResult.getRowCount(),
                    queryResult.getItems());
            return ResultDTO.newSuccess(pageResult);
        } catch (Exception e) {
            LOGGER.error(String.format("查询异常，参数：%s，异常：", JSON.toJSONString(iotTrackQueryListDTO)), e);
            return ResultDTO.newFail("查询失败");
        }
    }

    @Override
    public ResultDTO<List<IotGpsDTO>> getGpsLatest(IotTrackQueryListDTO iotTrackQueryListDTO) {

        try {
            if (CollectionUtils.isEmpty(iotTrackQueryListDTO.getDeviceCodes())) {
                return ResultDTO.newFail("设备编码列表为空");
            }
            List<String> containDeviceCodes = getContain(iotTrackQueryListDTO.getClientId(),
                    iotTrackQueryListDTO.getDeviceCodes(), true);
            if (CollectionUtils.isEmpty(containDeviceCodes)) {
                return ResultDTO.newFail("设备都无权限");
            }
            List<IotGpsDTO> gpsDtos = new ArrayList<>();
            QueryListCriteriaDto queryListCriteriaDto = new QueryListCriteriaDto();
            String[] factors = new String[]{GpsMsgParam.ATTR_GPS_LONGITUDE, GpsMsgParam.ATTR_GPS_LATITUDE,
                    GpsMsgParam.ATTR_GPS_ADDRESS, GpsMsgParam.ATTR_GPS_DATETIME, GpsMsgParam.ATTR_GPS_SPEED};
            if ("BD09".equals(iotTrackQueryListDTO.getCoordinate())) {
                factors = new String[]{GpsMsgParam.ATTR_GPS_BD09_LONGITUDE, GpsMsgParam.ATTR_GPS_BD09_LATITUDE,
                        GpsMsgParam.ATTR_GPS_ADDRESS, GpsMsgParam.ATTR_GPS_DATETIME, GpsMsgParam.ATTR_GPS_SPEED};
            } else if ("GCJ02".equals(iotTrackQueryListDTO.getCoordinate())) {
                factors = new String[]{GpsMsgParam.ATTR_GPS_GCJ02_LONGITUDE, GpsMsgParam.ATTR_GPS_GCJ02_LATITUDE,
                        GpsMsgParam.ATTR_GPS_ADDRESS, GpsMsgParam.ATTR_GPS_DATETIME, GpsMsgParam.ATTR_GPS_SPEED};
            }

//            LOGGER.info("查询最新位置的因子：{}", JSON.toJSONString(factors));

            queryListCriteriaDto.setDeviceIds(containDeviceCodes);
            queryListCriteriaDto.setFactors(factors);
            queryListCriteriaDto.setPartyId(-100);
            Result<Map.Entry<String, List<Map<String, Object>>>> result =
                    vehicleDataService.queryBathLast(queryListCriteriaDto);
            Map.Entry ret1 = result.getRet();
            if (ret1.getValue() == null) {
                return ResultDTO.newFail("查询结果为空");
            }
            List<Map<String, Object>> returnList = (List<Map<String, Object>>) ret1.getValue();
            for (Map<String, Object> ret : returnList) {
                List<Map<String, Object>> dataList = (List<Map<String, Object>>) ret.get("datas");

                for (String deviceCode : containDeviceCodes) {
                    if (deviceCode.equals(ret.get("deviceId"))) {
                        IotGpsDTO iotGpsDto = new IotGpsDTO();
                        for (Map<String, Object> map : dataList) {
                            String value = String.valueOf(map.get("value"));
                            if ((GpsMsgParam.ATTR_GPS_LONGITUDE.equals(map.get("factor")) ||
                                    GpsMsgParam.ATTR_GPS_BD09_LONGITUDE.equals(map.get("factor")) ||
                                    GpsMsgParam.ATTR_GPS_GCJ02_LONGITUDE.equals(map.get("factor"))) &&
                                    !StringUtils.isEmpty(value)) {
                                iotGpsDto.setLongitude(Double.parseDouble(value));
                            }
                            if (GpsMsgParam.ATTR_GPS_ADDRESS.equals(map.get("factor")) && !StringUtils.isEmpty(value)) {
                                iotGpsDto.setAddress(value);
                            }
                            if ((GpsMsgParam.ATTR_GPS_LATITUDE.equals(map.get("factor")) ||
                                    GpsMsgParam.ATTR_GPS_BD09_LATITUDE.equals(map.get("factor")) ||
                                    GpsMsgParam.ATTR_GPS_GCJ02_LATITUDE.equals(map.get("factor"))) &&
                                    !StringUtils.isEmpty(value)) {
                                iotGpsDto.setLatitude(Double.parseDouble(value));
                            }
                            if (GpsMsgParam.ATTR_GPS_DATETIME.equals(map.get("factor")) &&
                                    !StringUtils.isEmpty(value)) {
                                iotGpsDto.setRealtime(Long.parseLong(value));
                            }
                            if (GpsMsgParam.ATTR_GPS_SPEED.equals(map.get("factor")) && !StringUtils.isEmpty(value)) {
                                iotGpsDto.setSpeed(Double.parseDouble(value));
                            }
                        }
                        iotGpsDto.setMode(1);
                        iotGpsDto.setDeviceCode(deviceCode);
                        gpsDtos.add(iotGpsDto);
                    }
                }
            }
            return ResultDTO.newSuccess(gpsDtos);
        } catch (Exception e) {
            LOGGER.error(String.format("查询最新位置异常，参数：%s，异常：", JSON.toJSONString(iotTrackQueryListDTO)), e);
            return ResultDTO.newFail("查询失败");
        }
    }

    @Override
    public ResultDTO<Integer> saveVehicle(IotSaveDeviceDTO iotSaveDeviceDTO) {
        try {
            if (StringUtils.isEmpty(iotSaveDeviceDTO.getDeviceCode())) {
                return ResultDTO.newFail(ErrorCode.ERROR_3007.getCode(),
                        ErrorCode.ERROR_3007.getMessage());
            }
            if (CollectionUtils.isEmpty(iotSaveDeviceDTO.getGpsList())) {
                return ResultDTO.newFail(ErrorCode.ERROR_4020.getCode(),
                        ErrorCode.ERROR_4020.getMessage());
            }
            ResultDTO<Long> bizReturn = iotProjectUtilBiz.hasDevice(iotSaveDeviceDTO.getClientId(),
                    Lists.newArrayList(iotSaveDeviceDTO.getDeviceCode()), true);
            if (!bizReturn.isSuccess()) {
                return ResultDTO.newFail(bizReturn.getCode(), bizReturn.getMsg());
            }

            // 每次上传点位数量限制，每次最多上传100点位，超过100点位舍弃
            if (iotSaveDeviceDTO.getGpsList().size() > maxPoint) {
                LOGGER.info("上传车辆点位数据，数据量过大，限制每次最大上传：{}，点位数量：{}，点位列表：{}",
                        maxPoint, iotSaveDeviceDTO.getGpsList().size(), iotSaveDeviceDTO.getGpsList());
                iotSaveDeviceDTO.setGpsList(iotSaveDeviceDTO.getGpsList().subList(0, maxPoint - 1));
            }

            SaveDeviceDto saveDto = new SaveDeviceDto();
            saveDto.setDeviceId(iotSaveDeviceDTO.getDeviceCode());
            List<GpsDto> gpsDtos = new ArrayList<>();
            List<SaveKtvFactorDto> ktvFactorDtos = new ArrayList<>();
            for (IotGpsListDTO iotGpsListDTO : iotSaveDeviceDTO.getGpsList()) {
                if (iotGpsListDTO.getLatitude() == null || iotGpsListDTO.getLongitude() == null) {
                    return ResultDTO.newFail(ErrorCode.ERROR_3038.getCode(),
                            ErrorCode.ERROR_3038.getMessage());
                }
                if (iotGpsListDTO.getRealtime() == null) {
                    return ResultDTO.newFail(ErrorCode.ERROR_3039.getCode(),
                            ErrorCode.ERROR_3039.getMessage());
                }
                GpsDto gpsDto = new GpsDto();
                gpsDto.setCoordinates(iotGpsListDTO.getCoordinate());
                gpsDto.setDeviceId(iotSaveDeviceDTO.getDeviceCode());
                gpsDto.setDirection(getString(iotGpsListDTO.getDirection()));
                gpsDto.setLatitude(getString(iotGpsListDTO.getLatitude()));
                gpsDto.setLongitude(getString(iotGpsListDTO.getLongitude()));
                gpsDto.setPartyid("-100");
                gpsDto.setRealtime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(iotGpsListDTO.getRealtime())));
                gpsDto.setSpeed(getString(iotGpsListDTO.getSpeed()));
                gpsDtos.add(gpsDto);
            }
            if (!CollectionUtils.isEmpty(iotSaveDeviceDTO.getKvsList())) {
                for (IotKtvFactorDTO iotKtvFactorDTO : iotSaveDeviceDTO.getKvsList()) {
                    SaveKtvFactorDto saveKtvFactorDto = new SaveKtvFactorDto();
                    saveKtvFactorDto.setDeviceId(iotSaveDeviceDTO.getDeviceCode());
                    saveKtvFactorDto.setCode(iotKtvFactorDTO.getCode());
                    saveKtvFactorDto.setRealtime(iotKtvFactorDTO.getRealtime());
                    saveKtvFactorDto.setValue(iotKtvFactorDTO.getValue());
                    ktvFactorDtos.add(saveKtvFactorDto);
                }
            }
            saveDto.setGpsList(gpsDtos);
            saveDto.setKvs(ktvFactorDtos);
            Result result = vehicleDataService.saveDevice(saveDto);
            if (result.getRc() != 0) {
                return ResultDTO.newFail("保存车机数据失败" + result.getErr());
            }
        } catch (Exception e) {
            LOGGER.error(String.format("保存车机数据，参数：%s，异常:", JSON.toJSONString(iotSaveDeviceDTO)), e);
            return ResultDTO.newFail("保存车机数据异常" + e.getMessage());
        }
        return ResultDTO.newSuccess();
    }

    @Override
    public ResultDTO<List<Map<String, Object>>> vehicleList(IotVehicleHistoryQueryDTO iotVehicleListQueryDTO) {
        if (StringUtils.isEmpty(iotVehicleListQueryDTO.getDeviceCode())) {
            return ResultDTO.newFail(ErrorCode.ERROR_3007.getCode(),
                    ErrorCode.ERROR_3007.getMessage());
        }
        if (iotVehicleListQueryDTO.getStartDatetime() == null || iotVehicleListQueryDTO.getEndDatetime() == null) {
            return ResultDTO.newFail(ErrorCode.ERROR_5052.getCode(),
                    ErrorCode.ERROR_5052.getMessage());
        }
        if (iotVehicleListQueryDTO.getPageSize() == null || iotVehicleListQueryDTO.getPageIndex() == null) {
            return ResultDTO.newFail(ErrorCode.ERROR_5053.getCode(),
                    ErrorCode.ERROR_5053.getMessage());
        }
        ResultDTO<Long> bizReturn = iotProjectUtilBiz.hasDevice(iotVehicleListQueryDTO.getClientId(),
                Lists.newArrayList(iotVehicleListQueryDTO.getDeviceCode()), true);
        if (!bizReturn.isSuccess()) {
            return ResultDTO.newFail(bizReturn.getCode(), bizReturn.getMsg());
        }

        String[] factors = iotVehicleListQueryDTO.getFactors();
        if (factors == null || factors.length == 0) {
            return ResultDTO.newFail(ErrorCode.ERROR_4006.getCode(),
                    ErrorCode.ERROR_4006.getMessage());
        }
        List<String> strings = Lists.newArrayList(factors);
//        strings.add(GpsMsgParam.ATTR_GPS_LONGITUDE);
//        strings.add(GpsMsgParam.ATTR_GPS_LATITUDE);
//        strings.add(GpsMsgParam.ATTR_GPS_SPEED);
//        strings.add(GpsMsgParam.ATTR_GPS_DATETIME);

        QueryDto queryDto = new QueryDto();
        queryDto.setDeviceId(iotVehicleListQueryDTO.getDeviceCode());
        queryDto.setFactors(strings.toArray(new String[strings.size()]));
        queryDto.setPartyId("-100");
        queryDto.setStartDatetime(iotVehicleListQueryDTO.getStartDatetime());
        queryDto.setEndDatetime(iotVehicleListQueryDTO.getEndDatetime());

        List<Map<String, String>> result = ktvDataClient.queryKv(queryDto).getRet();
        List<Map<String, Object>> maps = new ArrayList<>();

        for (Map<String, String> temp : result) {
            Map<String, Object> map = new HashMap<>();
//            map.put("longitude", temp.get(GpsMsgParam.ATTR_GPS_LONGITUDE));
//            map.put("latitude", temp.get(GpsMsgParam.ATTR_GPS_LATITUDE));
//            map.put("speed", temp.get(GpsMsgParam.ATTR_GPS_SPEED));
//            map.put("realtime", temp.get(GpsMsgParam.ATTR_GPS_DATETIME));
            map.put("mode", 1);
            map.put("deviceCode", iotVehicleListQueryDTO.getDeviceCode());
            List<Map<String, String>> ktvs = new ArrayList<>();
            for (String key : temp.keySet()) {
                Map<String, String> kv = new HashMap<>();
                kv.put("factor", key);
                kv.put("value", temp.get(key));
                ktvs.add(kv);
            }
            map.put("ktvs", ktvs);
            maps.add(map);
        }

        return ResultDTO.newSuccess(maps);
    }

    @Override
    public ResultDTO<List<Map<String, Object>>> vehicleBatchList(IotVehicleLastestBatchDTO iotVehicleLastestBatchDTO) {
        if (CollectionUtils.isEmpty(iotVehicleLastestBatchDTO.getDeviceCodes())) {
            return ResultDTO.newFail(ErrorCode.ERROR_1006.getCode(),
                    ErrorCode.ERROR_1006.getMessage());
        }
        ResultDTO<Long> bizReturn = iotProjectUtilBiz.hasDevice(iotVehicleLastestBatchDTO.getClientId(),
                iotVehicleLastestBatchDTO.getDeviceCodes(), true);
        if (!bizReturn.isSuccess()) {
            return ResultDTO.newFail(bizReturn.getCode(), bizReturn.getMsg());
        }
        String[] factors = iotVehicleLastestBatchDTO.getFactors();
        if (factors == null || factors.length == 0) {
            return ResultDTO.newFail(ErrorCode.ERROR_4006.getCode(),
                    ErrorCode.ERROR_4006.getMessage());
        }

        QueryListCriteriaDto queryListCriteriaDto = new QueryListCriteriaDto();
        queryListCriteriaDto.setPartyId(-100);
        queryListCriteriaDto.setFactors(factors);
        queryListCriteriaDto.setDeviceIds(iotVehicleLastestBatchDTO.getDeviceCodes());
        List<ComparisonCriteriaDto> conditions = new ArrayList<>();
        if (!CollectionUtils.isEmpty(iotVehicleLastestBatchDTO.getConditions())) {
            for (IotQueryConditionDTO iotQueryConditionDTO : iotVehicleLastestBatchDTO.getConditions()) {
                ComparisonCriteriaDto comparisonCriteriaDto = new ComparisonCriteriaDto();
                comparisonCriteriaDto.setComparison(iotQueryConditionDTO.getComparison());
                comparisonCriteriaDto.setFactor(iotQueryConditionDTO.getFactor());
                comparisonCriteriaDto.setValue(iotQueryConditionDTO.getValue());
                conditions.add(comparisonCriteriaDto);
            }
            queryListCriteriaDto.setConditions(conditions);
        }
        Result<Map.Entry<String, List<Map<String, Object>>>> result =
                vehicleDataService.queryBathLast(queryListCriteriaDto);
        Map.Entry<String, List<Map<String, Object>>> ret1 = result.getRet();
        List<Map<String, Object>> returnList = ret1.getValue();
        for (Map<String, Object> map : returnList) {
            map.put("deviceCode", map.get("deviceId"));
            map.remove("deviceId");
        }
        return ResultDTO.newSuccess(returnList);
    }

    @Override
    public ResultDTO<Map<String, Double>> queryOil(IotSimpleQueryDTO iotSimpleQueryDTO) {
        if (StringUtils.isEmpty(iotSimpleQueryDTO.getDeviceCode())) {
            return ResultDTO.newFail(ErrorCode.ERROR_3007.getCode(),
                    ErrorCode.ERROR_3007.getMessage());
        }
        if (iotSimpleQueryDTO.getStartDatetime() == null || iotSimpleQueryDTO.getEndDatetime() == null) {
            return ResultDTO.newFail(ErrorCode.ERROR_5052.getCode(),
                    ErrorCode.ERROR_5052.getMessage());
        }
        ResultDTO<?> bizReturn = iotProjectUtilBiz.hasDevice(iotSimpleQueryDTO.getClientId(),
                Lists.newArrayList(iotSimpleQueryDTO.getDeviceCode()), true);
        if (!bizReturn.isSuccess()) {
            return ResultDTO.newFail(bizReturn.getCode(), bizReturn.getMsg());
        }
        QueryDto queryDto = new QueryDto();
        queryDto.setPartyId("-100");
        queryDto.setDeviceId(iotSimpleQueryDTO.getDeviceCode());
        queryDto.setEndDatetime(iotSimpleQueryDTO.getEndDatetime());
        queryDto.setStartDatetime(iotSimpleQueryDTO.getStartDatetime());
        Result<Map<String, Double>> result = vehicleDataService.queryOil(queryDto);
        if (result.getRc() != 0) {
            return ResultDTO.newFail(result.getErr());
        }
        return ResultDTO.newSuccess(result.getRet());
    }

    @Override
    public ResultDTO<Map<String, Object>> queryOilFuel(IotSimpleQueryDTO iotSimpleQueryDTO) {
        if (StringUtils.isEmpty(iotSimpleQueryDTO.getDeviceCode())) {
            return ResultDTO.newFail(ErrorCode.ERROR_3007.getCode(),
                    ErrorCode.ERROR_3007.getMessage());
        }
        if (iotSimpleQueryDTO.getStartDatetime() == null || iotSimpleQueryDTO.getEndDatetime() == null) {
            return ResultDTO.newFail(ErrorCode.ERROR_5052.getCode(),
                    ErrorCode.ERROR_5052.getMessage());
        }
        ResultDTO<Long> bizReturn = iotProjectUtilBiz.hasDevice(iotSimpleQueryDTO.getClientId(),
                Lists.newArrayList(iotSimpleQueryDTO.getDeviceCode()), true);
        if (!bizReturn.isSuccess()) {
            return ResultDTO.newFail(bizReturn.getCode(), bizReturn.getMsg());
        }
        OilQueryDto oilQueryDto = new OilQueryDto();
        oilQueryDto.setDeviceId(iotSimpleQueryDTO.getDeviceCode());
        oilQueryDto.setEndDatetime(iotSimpleQueryDTO.getEndDatetime());
        oilQueryDto.setStartDatetime(iotSimpleQueryDTO.getStartDatetime());
        oilQueryDto.setPartyId("-100");
        if (iotSimpleQueryDTO.getTankCapacity() != null) {
            oilQueryDto.setTankCapacity(iotSimpleQueryDTO.getTankCapacity());
        } else {
            oilQueryDto.setTankCapacity(200);
        }
        Result<OilResultDto> result = vehicleDataService.queryOilFuel(oilQueryDto);
        if (result.getRc() != 0) {
            return ResultDTO.newFail(result.getErr());
        }
        OilResultDto oilResultDto = result.getRet();
        Map<String, Object> map = new HashMap<>();
        map.put("deviceCode", oilResultDto.getDeviceId());
        map.put("gpsMileage", oilResultDto.getGpsMileage());
        map.put("oil", oilResultDto.getOil());
        return ResultDTO.newSuccess(map);
    }

    @Override
    public ResultDTO<Map<String, Double>> queryMileage(IotSimpleQueryDTO iotSimpleQueryDTO) {
        if (StringUtils.isEmpty(iotSimpleQueryDTO.getDeviceCode())) {
            return ResultDTO.newFail(ErrorCode.ERROR_3007.getCode(),
                    ErrorCode.ERROR_3007.getMessage());
        }
        if (iotSimpleQueryDTO.getStartDatetime() == null || iotSimpleQueryDTO.getEndDatetime() == null) {
            return ResultDTO.newFail(ErrorCode.ERROR_5052.getCode(),
                    ErrorCode.ERROR_5052.getMessage());
        }
        ResultDTO<Long> bizReturn = iotProjectUtilBiz.hasDevice(iotSimpleQueryDTO.getClientId(),
                Lists.newArrayList(iotSimpleQueryDTO.getDeviceCode()), true);
        if (!bizReturn.isSuccess()) {
            return ResultDTO.newFail(bizReturn.getCode(), bizReturn.getMsg());
        }
        QueryDto queryDto = new QueryDto();
        queryDto.setPartyId("-100");
        queryDto.setDeviceId(iotSimpleQueryDTO.getDeviceCode());
        queryDto.setEndDatetime(iotSimpleQueryDTO.getEndDatetime());
        queryDto.setStartDatetime(iotSimpleQueryDTO.getStartDatetime());
        Result<Map<String, Double>> result = vehicleDataService.queryMileage(queryDto);
        if (result.getRc() != 0) {
            return ResultDTO.newFail(result.getErr());
        }
        return ResultDTO.newSuccess(result.getRet());
    }

    @Override
    public ResultDTO<IotGpsPageDto> queryKvPage(IotTrackPageQueryDto queryDto) {
        if (StringUtils.isBlank(queryDto.getDeviceCode())) {
            return ResultDTO.newFail(ErrorCode.ERROR_3007.getCode(),
                    ErrorCode.ERROR_3007.getMessage());
        }
        if (queryDto.getStartDatetime() == null || queryDto.getEndDatetime() == null) {
            return ResultDTO.newFail(ErrorCode.ERROR_5052.getCode(),
                    ErrorCode.ERROR_5052.getMessage());
        }
        ResultDTO<Long> bizReturn = iotProjectUtilBiz.hasDevice(queryDto.getClientId(),
                Lists.newArrayList(queryDto.getDeviceCode()), true);
        if (!bizReturn.isSuccess()) {
            return ResultDTO.newFail(bizReturn.getCode(), bizReturn.getMsg());
        }
        GpsQueryDto gpsQueryDto = new GpsQueryDto();
        gpsQueryDto.setDeviceid(queryDto.getDeviceCode());
        gpsQueryDto.setStartTime(queryDto.getStartDatetime());
        gpsQueryDto.setEndTime(queryDto.getEndDatetime());
        gpsQueryDto.setPageSize(queryDto.getPageSize());
        gpsQueryDto.setDateTime(queryDto.getDateTime());
        gpsQueryDto.setFormat(queryDto.getCoordinate());
        gpsQueryDto.setPartyid(-100);
        Result<PageResultDto> result = vehicleDataService.queryGpsListPage(gpsQueryDto);
        if (!Result.isSuccess(result)) {
            return ResultDTO.newFail(result.getErr());
        }
        PageResultDto pageResultDto = result.getRet();
        List<Map<String, String>> mapList = pageResultDto.getDataList();
        if (CollectionUtils.isEmpty(mapList)) {

        }

        List<IotGpsDTO> ret = new ArrayList<>();
        for (Map<String, String> map : mapList) {
            IotGpsDTO iotGpsDTO = processResult(map);
            iotGpsDTO.setRealtime(Long.parseLong(map.get("realtime")));
            ret.add(iotGpsDTO);
        }

        IotGpsPageDto gpsPageDto = new IotGpsPageDto(ret, pageResultDto.isPageDown());

        return ResultDTO.newSuccess(gpsPageDto);
    }

    private IotGpsDTO processResult(Map<String, String> map) {
        IotGpsDTO iotGpsDTO = new IotGpsDTO();
        iotGpsDTO.setAddress(map.get("address"));
        iotGpsDTO.setDeviceCode(map.get("deviceid"));
        iotGpsDTO.setLatitude(Double.parseDouble(map.get("latitude")));
        iotGpsDTO.setLongitude(Double.parseDouble(map.get("longitude")));
        if (!StringUtils.isEmpty(map.get("mode"))) {
            iotGpsDTO.setMode(Integer.parseInt(map.get("mode")));
        }
        iotGpsDTO.setSpeed(Double.parseDouble(map.get("speed")));

        return iotGpsDTO;
    }

    private String getTime(Long time) {
        Date date = new Date(time);
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sd.format(date);
    }

    private String getString(Object object) {
        return object == null ? null : object.toString();
    }

    private List<String> getContain(String clientId, List<String> deviceCodes, Boolean enabled) {
        Long iotProjectId = iotProjectService.getProjectByClientId(clientId).getIotProjectId();
        List<String> containDeviceCodes = new ArrayList<>();
        ResultDTO<PageResult<IotDeviceQueryDTO>> bizReturn = iotDeviceService.getIotOpenDeviceList(iotProjectId,
                deviceCodes, enabled);
        if (!bizReturn.isSuccess()) {
            return containDeviceCodes;
        }
        PageResult<IotDeviceQueryDTO> pageResult = bizReturn.getData();
        if (pageResult == null || CollectionUtils.isEmpty(pageResult.getData())) {
            return containDeviceCodes;
        }

        List<IotDeviceQueryDTO> dtoList = pageResult.getData();
        for (String deviceCode : deviceCodes) {
            for (IotDeviceQueryDTO dto : dtoList) {
                if (deviceCode.equals(dto.getDeviceCode()) || deviceCode.endsWith(dto.getDeviceCode())) {
                    containDeviceCodes.add(deviceCode);
                    break;
                }
            }
        }
        return containDeviceCodes;
    }
}
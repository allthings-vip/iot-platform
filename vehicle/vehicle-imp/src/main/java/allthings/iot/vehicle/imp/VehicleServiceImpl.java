package allthings.iot.vehicle.imp;

import allthings.iot.common.dto.QueryResult;
import allthings.iot.common.dto.Result;
import allthings.iot.constant.gps.GpsConstant;
import allthings.iot.constant.gps.GpsMsgParam;
import allthings.iot.constant.gps.OilType;
import allthings.iot.ktv.client.KtvDataClient;
import allthings.iot.ktv.common.dto.BatchQueryDto;
import allthings.iot.ktv.common.dto.ComparisonCriteriaDto;
import allthings.iot.ktv.common.dto.GpsDeviceDto;
import allthings.iot.ktv.common.dto.KvsDto;
import allthings.iot.ktv.common.dto.OilQueryDto;
import allthings.iot.ktv.common.dto.OilResultDto;
import allthings.iot.ktv.common.dto.PageQueryDto;
import allthings.iot.ktv.common.dto.PageResultDto;
import allthings.iot.ktv.common.dto.QueryDto;
import allthings.iot.ktv.common.dto.QueryListCriteriaDto;
import allthings.iot.util.gps.enums.CoorType;
import allthings.iot.util.gps.util.GpsUtil;
import allthings.iot.vehicle.api.IVehicleService;
import allthings.iot.vehicle.common.constant.ColumnConstant;
import allthings.iot.vehicle.common.dto.GpsDto;
import allthings.iot.vehicle.common.dto.GpsLQueryDto;
import allthings.iot.vehicle.common.dto.GpsQueryDto;
import allthings.iot.vehicle.common.dto.ResultDto;
import allthings.iot.vehicle.common.dto.SaveDeviceDto;
import allthings.iot.vehicle.common.dto.SaveKtvFactorDto;
import allthings.iot.vehicle.util.VehicleDateTimeUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import retrofit2.Response;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author :  luhao
 * @FileName :  VechileServiceImpl
 * @CreateDate :  2018/1/15
 * @Description :
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) tp.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
@Service("vehicleData")
public class VehicleServiceImpl implements IVehicleService {
    @Value("${upload.point.max}")
    private Integer uploadMax;
    @Autowired
    private KtvDataClient ktvDataClient;

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

    private static final Logger LOGGER = LoggerFactory.getLogger(VehicleServiceImpl.class);

    @Override
    public Result<QueryResult<Map<String, String>>> queryGpsList(GpsQueryDto gpsQueryDto) throws Exception {
        gpsQueryDto.setPageSize(10000);
        gpsQueryDto.setSkipCount(0);
        String[] factors = {GpsMsgParam.ATTR_GPS_LATITUDE, GpsMsgParam.ATTR_GPS_LONGITUDE, GpsMsgParam.ATTR_GPS_SPEED,
                GpsMsgParam.ATTR_GPS_ADDRESS, GpsMsgParam.ATTR_GPS_PROVINCE, GpsMsgParam.ATTR_GPS_CITY, GpsMsgParam.ATTR_GPS_REGION,
                GpsMsgParam.ATTR_GPS_BD09_LONGITUDE, GpsMsgParam.ATTR_GPS_BD09_LATITUDE, GpsMsgParam.ATTR_GPS_GCJ02_LATITUDE,
                GpsMsgParam.ATTR_GPS_GCJ02_LONGITUDE};

        if (gpsQueryDto.getPartyid() == 0 || StringUtils.isEmpty(gpsQueryDto.getDeviceid())) {
            LOGGER.warn("会员ID或者设备编码为空");
            return Result.newFaild("PartyId Or DeviceId is null");
        }

        if (StringUtils.isEmpty(gpsQueryDto.getStarttime()) || StringUtils.isEmpty(gpsQueryDto.getEndtime())) {
            LOGGER.warn("开始时间或者结束时间为空");
            return Result.newFaild("StartTime Or EndTime is null");
        }

        QueryDto queryDto = new QueryDto();
        queryDto.setPartyId(String.valueOf(gpsQueryDto.getPartyid()));
        queryDto.setDeviceId(gpsQueryDto.getDeviceid());
        queryDto.setStartDatetime(DATE_TIME_FORMATTER.parseMillis(gpsQueryDto.getStarttime()));
        queryDto.setEndDatetime((DATE_TIME_FORMATTER.parseMillis(gpsQueryDto.getEndtime())));
        queryDto.setFactors(factors);

        Result<List<Map<String, String>>> result = ktvDataClient.queryKv(queryDto);
        List<Map<String, String>> mapList = result.getRet();
        String format = gpsQueryDto.getFormat();
        List<Map<String, String>> resultList = handStopTime(mapList, gpsQueryDto.getDeviceid(), format, false);
        QueryResult<Map<String, String>> queryResult = new QueryResult<>(resultList, resultList.size());
        return Result.newSuccess(queryResult);
    }

    @Override
    public Result<QueryResult<Map<String, String>>> queryUnHandStopTimeGpsList(GpsQueryDto gpsQueryDto) {
        try {
            gpsQueryDto.setPageSize(10000);
            gpsQueryDto.setSkipCount(0);
            String[] factors = {GpsMsgParam.ATTR_GPS_LATITUDE, GpsMsgParam.ATTR_GPS_LONGITUDE, GpsMsgParam.ATTR_GPS_SPEED,
                    GpsMsgParam.ATTR_GPS_ADDRESS, GpsMsgParam.ATTR_GPS_PROVINCE, GpsMsgParam.ATTR_GPS_CITY, GpsMsgParam.ATTR_GPS_REGION,
                    GpsMsgParam.ATTR_GPS_BD09_LONGITUDE, GpsMsgParam.ATTR_GPS_BD09_LATITUDE, GpsMsgParam.ATTR_GPS_GCJ02_LATITUDE,
                    GpsMsgParam.ATTR_GPS_GCJ02_LONGITUDE};
            if (gpsQueryDto.getPartyid() == 0 || StringUtils.isEmpty(gpsQueryDto.getDeviceid())) {
                LOGGER.warn("会员ID或者设备编码为空");
                return Result.newFaild("PartyId Or DeviceId is null");
            }

            if (gpsQueryDto.getStarttime() == null || gpsQueryDto.getEndtime() == null) {
                LOGGER.warn("开始时间或者结束时间为空");
                return Result.newFaild("StartTime Or EndTime is null");
            }

            QueryDto queryDto = new QueryDto();
            String deviceId = gpsQueryDto.getDeviceid();
            queryDto.setPartyId(String.valueOf(gpsQueryDto.getPartyid()));
            queryDto.setDeviceId(deviceId);
            queryDto.setStartDatetime(DATE_TIME_FORMATTER.parseMillis(gpsQueryDto.getStarttime()));
            queryDto.setEndDatetime((DATE_TIME_FORMATTER.parseMillis(gpsQueryDto.getEndtime())));
            queryDto.setFactors(factors);

            Result<List<Map<String, String>>> result = ktvDataClient.queryKv(queryDto);
            List<Map<String, String>> resultList = result.getRet();
            String format = gpsQueryDto.getFormat();
            processResult(format, deviceId, resultList, true);
            QueryResult<Map<String, String>> queryResult = new QueryResult<>(resultList, resultList.size());
            return Result.newSuccess(queryResult);
        } catch (Exception e) {
            LOGGER.error(String.format("查询gps数据(未处理停车时间)异常，参数：%s", gpsQueryDto.toString()), e);
            return Result.newFaild("查询gps数据失败");
        }
    }

    /**
     * 处理停车时间
     *
     * @param mapList
     * @return
     */
    private List<Map<String, String>> handStopTime(List<Map<String, String>> mapList, String deviceId, String format, boolean isBusiness) {
        if (CollectionUtils.isEmpty(mapList)) {
            return Lists.newArrayList();
        }

        String startRealTime = "";
        //记录停留点的第一条数据
        Map<String, String> firstMap = null;

        String endtime;

        List<Map<String, String>> resultList = Lists.newArrayList();

        for (int i = 0; i < mapList.size(); i++) {
            Map<String, String> data = mapList.get(i);
            if (GpsConstant.BD09.equalsIgnoreCase(format)) {
                data.put(ColumnConstant.COL_GPS_LONGITUDE, data.remove(GpsMsgParam.ATTR_GPS_BD09_LONGITUDE));
                data.put(ColumnConstant.COL_GPS_LATITUDE, data.remove(GpsMsgParam.ATTR_GPS_BD09_LATITUDE));
                data.remove(GpsMsgParam.ATTR_GPS_GCJ02_LATITUDE);
                data.remove(GpsMsgParam.ATTR_GPS_GCJ02_LONGITUDE);
                data.remove(GpsMsgParam.ATTR_GPS_LATITUDE);
                data.remove(GpsMsgParam.ATTR_GPS_LONGITUDE);
            } else if (GpsConstant.GCJ02.equalsIgnoreCase(format)) {
                data.put(ColumnConstant.COL_GPS_LONGITUDE, data.remove(GpsMsgParam.ATTR_GPS_GCJ02_LONGITUDE));
                data.put(ColumnConstant.COL_GPS_LATITUDE, data.remove(GpsMsgParam.ATTR_GPS_GCJ02_LATITUDE));
                data.remove(GpsMsgParam.ATTR_GPS_BD09_LATITUDE);
                data.remove(GpsMsgParam.ATTR_GPS_BD09_LONGITUDE);
                data.remove(GpsMsgParam.ATTR_GPS_LATITUDE);
                data.remove(GpsMsgParam.ATTR_GPS_LONGITUDE);
            } else {
                data.put(ColumnConstant.COL_GPS_LONGITUDE, data.remove(GpsMsgParam.ATTR_GPS_LONGITUDE));
                data.put(ColumnConstant.COL_GPS_LATITUDE, data.remove(GpsMsgParam.ATTR_GPS_LATITUDE));
                data.remove(GpsMsgParam.ATTR_GPS_BD09_LATITUDE);
                data.remove(GpsMsgParam.ATTR_GPS_BD09_LONGITUDE);
                data.remove(GpsMsgParam.ATTR_GPS_GCJ02_LATITUDE);
                data.remove(GpsMsgParam.ATTR_GPS_GCJ02_LONGITUDE);
            }

            String datetime = data.remove(GpsMsgParam.ATTR_GPS_DATETIME);
            if (isBusiness) {
                data.put(ColumnConstant.COL_GPS_REALTIME, datetime);
            } else {
                data.put(ColumnConstant.COL_GPS_REALTIME, DATE_TIME_FORMATTER.print(Long.parseLong(datetime)));
            }

            data.put("speed", data.remove(GpsMsgParam.ATTR_GPS_SPEED));
            data.put("deviceid", deviceId);
            data.put("model", "");

            String speed = data.get("speed");
            data.put(ColumnConstant.COL_GPS_STARTREALTIME, data.get(ColumnConstant.COL_GPS_REALTIME));
            data.put(ColumnConstant.COL_GPS_ENDREALTIME, data.get(ColumnConstant.COL_GPS_REALTIME));

            if (StringUtils.isBlank(speed)) {
                resultList.add(data);
                continue;
            }

            if (i == mapList.size() - 1 && Double.parseDouble(speed) <= 3 && firstMap != null) {
                startRealTime = firstMap.get(ColumnConstant.COL_GPS_REALTIME);
                endtime = data.get(ColumnConstant.COL_GPS_REALTIME);
                Map<String, String> resultMap = Maps.newHashMap(firstMap);
                if (isBusiness) {
                    resultMap.put(ColumnConstant.COL_GPS_STOPTIME, String.valueOf(Long.parseLong(endtime) -
                            Long.parseLong(startRealTime)));
                } else {
                    resultMap.put(ColumnConstant.COL_GPS_STOPTIME, String.valueOf(DATE_TIME_FORMATTER.parseMillis(endtime) -
                            DATE_TIME_FORMATTER.parseMillis(startRealTime)));
                }

                resultMap.put(ColumnConstant.COL_GPS_STARTREALTIME, startRealTime);
                resultMap.put(ColumnConstant.COL_GPS_ENDREALTIME, endtime);

                resultList.add(resultMap);
                continue;
            } else if (i == mapList.size() - 1 && Double.parseDouble(speed) <= 3 && firstMap == null) {
                Map<String, String> resultMap = Maps.newHashMap(data);
                resultMap.put(ColumnConstant.COL_GPS_STOPTIME, String.valueOf(0));
                resultMap.put(ColumnConstant.COL_GPS_STARTREALTIME, data.get(ColumnConstant.COL_GPS_REALTIME));
                resultMap.put(ColumnConstant.COL_GPS_ENDREALTIME, data.get(ColumnConstant.COL_GPS_REALTIME));
                resultList.add(resultMap);
                continue;
            }

            if (Double.parseDouble(speed) <= 3 && firstMap == null) {
                firstMap = data;
            } else if (Double.parseDouble(speed) > 3 && firstMap != null) {
                startRealTime = firstMap.get(ColumnConstant.COL_GPS_REALTIME);
                endtime = data.get(ColumnConstant.COL_GPS_REALTIME);

                Map<String, String> resultMap = Maps.newHashMap(firstMap);
                if (isBusiness) {
                    resultMap.put(ColumnConstant.COL_GPS_STOPTIME, String.valueOf(Long.parseLong(endtime) -
                            Long.parseLong(startRealTime)));
                } else {
                    resultMap.put(ColumnConstant.COL_GPS_STOPTIME, String.valueOf(DATE_TIME_FORMATTER.parseMillis(endtime) -
                            DATE_TIME_FORMATTER.parseMillis(startRealTime)));
                }
                resultMap.put(ColumnConstant.COL_GPS_STARTREALTIME, startRealTime);
                resultMap.put(ColumnConstant.COL_GPS_ENDREALTIME, endtime);

                resultList.add(resultMap);
                resultList.add(data);
                firstMap = null;
            } else if (Double.parseDouble(speed) > 3 && firstMap == null) {
                resultList.add(data);
            }
        }

        return resultList;
    }

    @Override
    public Result<QueryResult<Map<String, String>>> queryGpsLatest(GpsLQueryDto gpsLQueryDto) throws Exception {
        String[] factors = {GpsMsgParam.ATTR_GPS_SPEED, GpsMsgParam.ATTR_GPS_LONGITUDE, GpsMsgParam.ATTR_GPS_LATITUDE, GpsMsgParam.ATTR_GPS_REGION,
                GpsMsgParam.ATTR_GPS_CITY, GpsMsgParam.ATTR_GPS_PROVINCE, GpsMsgParam.ATTR_GPS_ADDRESS};
        String deviceIds = gpsLQueryDto.getDeviceIds();
        if (StringUtils.isBlank(deviceIds)) {
            LOGGER.warn("设备编码为空");
            return Result.newFaild("设备编码不能为空");
        }

        String[] deviceCodes = deviceIds.split(",");
        BatchQueryDto batchQueryDto = new BatchQueryDto();
        batchQueryDto.setDeviceIds(Lists.newArrayList(deviceCodes));
        batchQueryDto.setFactors(factors);
        batchQueryDto.setPartyId("-100");
        Result<Map<String, List<Map<String, String>>>> result = ktvDataClient.batchQueryKvLast(batchQueryDto);
        if (!Result.isSuccess(result)) {
            return Result.newFaild(result.getRc(), result.getErr());
        }
        Map<String, List<Map<String, String>>> resultMap = result.getRet();
        List<Map<String, String>> resultList = Lists.newArrayList();
        for (String deviceCode : deviceCodes) {
            List<Map<String, String>> mapList = resultMap.get(deviceCode);
            if (CollectionUtils.isEmpty(mapList)) {
                continue;
            }
            Map<String, String> mapResult = Maps.newHashMap();
            mapResult.put("deviceid", deviceCode);
            for (Map<String, String> map : mapList) {
                mapResult.putAll(map);
            }
            String gpsDateTime = mapResult.remove(GpsMsgParam.ATTR_GPS_DATETIME);
            mapResult.put(ColumnConstant.COL_GPS_REALTIME, DATE_TIME_FORMATTER.print(Long.parseLong(gpsDateTime)));
            mapResult.put("speed", mapResult.remove(GpsMsgParam.ATTR_GPS_SPEED));
            mapResult.put(ColumnConstant.COL_GPS_LATITUDE, mapResult.remove(GpsMsgParam.ATTR_GPS_LATITUDE));
            mapResult.put(ColumnConstant.COL_GPS_LONGITUDE, mapResult.remove(GpsMsgParam.ATTR_GPS_LONGITUDE));
            resultList.add(mapResult);
        }

        QueryResult<Map<String, String>> queryResult = new QueryResult<>(resultList, resultList.size());
        return Result.newSuccess(queryResult);
    }

    @Override
    public Result<OilResultDto> queryOilFuel(OilQueryDto oilQueryDto) {
        String[] factors = {GpsMsgParam.ATTR_GPS_MILEAGE, GpsMsgParam.ATTR_OIL, GpsMsgParam.ATTR_OIL_TYPE, GpsMsgParam
                .ATTR_GPS_SPEED, GpsMsgParam.ATTR_GPS_DATETIME, allthings.iot.ktv.common.ColumnConstant.KTV_COL_DEVICE_ID,
                allthings.iot.ktv.common.ColumnConstant.KTV_COL_PARTY_ID};
        QueryDto queryDto = new QueryDto();
        queryDto.setDeviceId(oilQueryDto.getDeviceId());
        queryDto.setPartyId(oilQueryDto.getPartyId());
        queryDto.setStartDatetime(oilQueryDto.getStartDatetime());
        queryDto.setEndDatetime(oilQueryDto.getEndDatetime());
        queryDto.setFactors(factors);

        List<Map<String, String>> params = ktvDataClient.queryKv(queryDto).getRet();
        if (CollectionUtils.isEmpty(params)) {
            OilResultDto oilResultDto = new OilResultDto();
            oilResultDto.setDeviceId(oilQueryDto.getDeviceId());
            return Result.newSuccess(oilResultDto);
        }

        return Result.newSuccess(handleOil(params, oilQueryDto.getDeviceId(), oilQueryDto.getTankCapacity()));
    }


    /**
     * 判断是否有加油
     *
     * @param paramsList
     * @param deviceId
     * @param tankCapacity
     * @return
     */
    private OilResultDto handleOil(List<Map<String, String>> paramsList, String deviceId, int tankCapacity) {
        OilResultDto oilResultDto = new OilResultDto();

        int length = paramsList.size();

        Map<String, String> paramsFirst = paramsList.get(0);

        String attrOil = paramsFirst.get(GpsMsgParam.ATTR_OIL) == null ? "0" : paramsFirst.get(GpsMsgParam.ATTR_OIL);

        double oilFirst = Double.parseDouble(attrOil);
        String oilTypeFirst = paramsFirst.get(GpsMsgParam.ATTR_OIL_TYPE);
        double realOilFirst = getOil(oilFirst, oilTypeFirst, tankCapacity);
        long gpsDatetimeFirst = Long.parseLong(paramsFirst.get(GpsMsgParam.ATTR_GPS_DATETIME));
        String attrGpsMileage = paramsFirst.get(GpsMsgParam.ATTR_GPS_MILEAGE) == null ? "0" : paramsFirst.get(GpsMsgParam.ATTR_GPS_MILEAGE);
        float gpsMileageFirst = Float.parseFloat(attrGpsMileage);

        //加油过程中采集的点
        List<Map<String, String>> tempList = Lists.newArrayList();
        tempList.add(paramsFirst);
        //为了判断加油临时设置的变量
        long minDatetime = gpsDatetimeFirst;
        double minRealOil = realOilFirst;

        //开始里程
        float startMileage = gpsMileageFirst;
        //结束里程
        float endMileage = gpsMileageFirst;
        //加油的时间段和加油起始值
        List<TimeRange> addOilList = Lists.newArrayList();
        //总加油量
        BigDecimal totalAddOil = new BigDecimal(0);
        //记录开始的油量
        double startOil = realOilFirst;
        //记录结束的油量
        double endOil = realOilFirst;

        for (int i = 1; i < length; i++) {
            Map<String, String> params = paramsList.get(i);
            String attr = params.get(GpsMsgParam.ATTR_OIL) == null ? "0" : paramsFirst.get(GpsMsgParam.ATTR_OIL);
            double oil = Double.parseDouble(attr);
            String oilType = params.get(GpsMsgParam.ATTR_OIL_TYPE);
            int gpsSpeed = (int) Float.parseFloat(params.get(GpsMsgParam.ATTR_GPS_SPEED));
            String mileage = paramsFirst.get(GpsMsgParam.ATTR_GPS_MILEAGE) == null ? "0" : paramsFirst.get(GpsMsgParam.ATTR_GPS_MILEAGE);
            float gpsMileage = Float.parseFloat(mileage);
            long gpsDatetime = Long.parseLong(params.get(GpsMsgParam.ATTR_GPS_DATETIME));

            double realOil = getOil(oil, oilType, tankCapacity);
            //速度小于3，油量比前一次大
            if (gpsSpeed < 3 && realOil >= realOilFirst) {
                tempList.add(params);
            } else {
                //速度小于3，持续时间大于60秒，油量大于20L
                if (realOilFirst - minRealOil >= 20L && gpsDatetimeFirst - minDatetime > 60000) {
                    totalAddOil = totalAddOil.add(new BigDecimal(String.valueOf
                            (realOilFirst))).subtract(new BigDecimal(String.valueOf(minRealOil)));
                    TimeRange timeRange = new TimeRange(minDatetime, minRealOil, gpsDatetimeFirst, realOilFirst);
                    //加油时间段
                    addOilList.add(timeRange);
                }

                //下一个加油的判断
                minDatetime = gpsDatetime;
                minRealOil = realOil;
                //加油过程明细列表
                tempList = Lists.newArrayList(params);
            }

            realOilFirst = realOil;
            gpsDatetimeFirst = gpsDatetime;
            endMileage = gpsMileage;
            if (gpsSpeed >= 3 && realOil <= endOil) {
                endOil = realOil;
            }
        }

        oilResultDto.setDeviceId(deviceId);
        oilResultDto.setGpsMileage(endMileage - startMileage);
        oilResultDto.setOil(totalAddOil.add(new BigDecimal(String.valueOf(startOil))).subtract(new BigDecimal(String
                .valueOf(endOil))).doubleValue());

        return oilResultDto;
    }


    /**
     * @param oil
     * @param oilType
     * @param tankCapacity
     * @return
     */
    private double getOil(double oil, String oilType, int tankCapacity) {
        if (OilType.ZSL.name().equals(oilType)) {
            return oil;
        } else if (OilType.BL.name().equals(oilType)) {
            return oil * tankCapacity * 0.01;
        } else {
            return oil;
        }
    }

    @Override
    public Result<Map.Entry<String, List<Map<String, Object>>>> queryLatest(QueryDto queryDto) {
        Result<List<Map<String, String>>> result = ktvDataClient.queryKvLatest(queryDto);
        if (!Result.isSuccess(result)) {
            return Result.newFaild(result.getRc(), result.getErr());
        }

        List<Map<String, String>> resultList = result.getRet();

        String[] factors = queryDto.getFactors();
        List<Map<String, Object>> returnList = lastDataDeal(resultList, factors);
//        for (String factor : factors) {
//            Map<String, Object> maps = Maps.newHashMap();
//            Map<String, String> resultMap = getFactorMap(factor, resultList);
//            if (resultMap != null) {
//                maps.put(tf56.iot.ktv.common.ColumnConstant.KTV_COL_DATETIME, resultMap.get(GpsMsgParam
//                        .ATTR_GPS_DATETIME));
//                maps.put(tf56.iot.ktv.common.ColumnConstant.KTV_COL_FACTOR, factor);
//                maps.put(tf56.iot.ktv.common.ColumnConstant.KTV_COL_VALUE, resultMap.get(factor));
//
//                returnList.add(maps);
//            }
//        }

        return Result.newSuccess(Maps.immutableEntry("items", returnList));
    }

    @Override
    public Result<Map.Entry<String, List<Map<String, Object>>>> queryBathLatest(QueryListCriteriaDto queryListCriteriaDto) {
        List<String> deviceIds = queryListCriteriaDto.getDeviceIds();
        if (deviceIds.isEmpty()) {
            return Result.newFaild("设备编码不能为空");
        }
        String[] factors = queryListCriteriaDto.getFactors();
        if (factors == null || factors.length <= 0) {
            return Result.newFaild("因子不能为空");
        }
        if (queryListCriteriaDto.getPartyId() == null) {
            return Result.newFaild("会员id不能为空");
        }
        List<ComparisonCriteriaDto> conditionList = queryListCriteriaDto.getConditions();
        Set<String> factorSet = Sets.newHashSet();
        List<String> factorList = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(conditionList)) {
            for (ComparisonCriteriaDto dto : conditionList) {
                factorList.add(dto.getFactor());
            }
        }
        factorSet.addAll(factorList);
        factorSet.addAll(Lists.newArrayList(factors));
        List<Map<String, Object>> returnList = Lists.newArrayList();
        for (String deviceId : deviceIds) {
            QueryDto queryDto = new QueryDto();
            queryDto.setPartyId("-100");
            queryDto.setDeviceId(deviceId);
            queryDto.setFactors(factorSet.toArray(new String[factorSet.size()]));

            Result<List<Map<String, String>>> result = ktvDataClient.queryKvLatest(queryDto);
            if (!Result.isSuccess(result)) {
                continue;
            }

            List<Map<String, String>> resultList = result.getRet();
            if (!CollectionUtils.isEmpty(conditionList)) {
                for (ComparisonCriteriaDto dto : conditionList) {
                    Map<String, String> resultMap = getFactorMap(dto.getFactor(), resultList);
                    if (resultMap != null) {
                        String value = resultMap.get(dto.getFactor());
                        if (!dto.getValue().equals(value)) {
                            resultList = Lists.newArrayList();
                        }
                    }
                }
            }

            Map<String, Object> returnMap = Maps.newHashMap();
            // 处理gps_datetime因子值
//            List<Map<String, Object>> dataList = Lists.newArrayList();
//            long gpsDatetime = 0L;
//            for (String factor : factors) {
//                Map<String, String> resultMap = getFactorMap(factor, resultList);
//                if (resultMap != null) {
//                    long dateTime = Long.parseLong(resultMap.get(GpsMsgParam.ATTR_GPS_DATETIME));
//                    if (dateTime > gpsDatetime) {
//                        gpsDatetime = dateTime;
//                    }
//                    if (factor.equals(GpsMsgParam.ATTR_GPS_DATETIME)) {
//                        continue;
//                    }
//                    Map<String, Object> maps = Maps.newHashMap();
//                    maps.put(tf56.iot.ktv.common.ColumnConstant.KTV_COL_DATETIME, dateTime);
//                    maps.put(tf56.iot.ktv.common.ColumnConstant.KTV_COL_VALUE, resultMap.get(factor));
//                    maps.put(tf56.iot.ktv.common.ColumnConstant.KTV_COL_FACTOR, factor);
//                    dataList.add(maps);
//                }
//            }
//            if (Lists.newArrayList(factors).contains(GpsMsgParam.ATTR_GPS_DATETIME)) {
//                Map<String, Object> maps = Maps.newHashMap();
//                maps.put(tf56.iot.ktv.common.ColumnConstant.KTV_COL_DATETIME, gpsDatetime);
//                maps.put(tf56.iot.ktv.common.ColumnConstant.KTV_COL_VALUE, gpsDatetime);
//                maps.put(tf56.iot.ktv.common.ColumnConstant.KTV_COL_FACTOR, GpsMsgParam.ATTR_GPS_DATETIME);
//                dataList.add(maps);
//            }
            List<Map<String, Object>> dataList = lastDataDeal(resultList, factors);
            returnMap.put("datas", dataList);
            returnMap.put("deviceId", deviceId);
            returnList.add(returnMap);
        }
        return Result.newSuccess(Maps.immutableEntry("items", returnList));
    }

    /**
     * map进行转换
     *
     * @param factor
     * @param resultList
     * @return
     */
    private Map<String, String> getFactorMap(String factor, List<Map<String, String>> resultList) {
        if (CollectionUtils.isEmpty(resultList)) {
            return null;
        }
        for (Map<String, String> map : resultList) {
            String value = map.get(factor);
            if (StringUtils.isNotBlank(value)) {
                return map;
            }
        }

        return null;
    }

    @Override
    public Result<Map<String, Double>> queryMileage(QueryDto queryDto) {
        queryDto.setFactors(new String[]{GpsMsgParam.ATTR_GPS_MILEAGE});
        Result<List<Map<String, Object>>> firstResult = ktvDataClient.queryFirst(queryDto);
        if (!Result.isSuccess(firstResult)) {
            return Result.newFaild(firstResult.getRc(), firstResult.getErr());
        }

        Result<List<Map<String, Object>>> lastResult = ktvDataClient.queryLast(queryDto);
        if (!Result.isSuccess(lastResult)) {
            return Result.newFaild(lastResult.getRc(), lastResult.getErr());
        }

        List<Map<String, Object>> firstMap = firstResult.getRet();
        if (CollectionUtils.isEmpty(firstMap)) {
            LOGGER.warn("firstMap为空，数据库中无数据,参数={}", JSON.toJSONString(queryDto));
            return Result.newFaild("数据库中无数据");
        }

        List<Map<String, Object>> lastMap = lastResult.getRet();
        if (CollectionUtils.isEmpty(lastMap)) {
            LOGGER.warn("lastMap为空，数据库中无数据,参数={}", JSON.toJSONString(queryDto));
            return Result.newFaild("数据库中无数据");
        }

        Map<String, Object> firstMileageMap = firstMap.get(0);
        Double firstMileage = Double.valueOf(firstMileageMap.get(allthings.iot.ktv.common.ColumnConstant.KTV_COL_VALUE).toString());

        Map<String, Object> lastMileageMap = lastMap.get(0);
        Double lastMileage = Double.valueOf(lastMileageMap.get(allthings.iot.ktv.common.ColumnConstant.KTV_COL_VALUE).toString());

        Map<String, Double> mileageMap = Maps.newHashMap();
        mileageMap.put(allthings.iot.ktv.common.ColumnConstant.KTV_COL_MILEAGE, lastMileage - firstMileage);

        return Result.newSuccess(mileageMap);
    }

    @Override
    public Result<Map<String, Double>> queryOil(QueryDto queryDto) {
        queryDto.setFactors(new String[]{GpsMsgParam.ATTR_OIL_TYPE, GpsMsgParam.ATTR_OIL});
        Result<Map<String, String>> firstResult = ktvDataClient.queryFirstLast(queryDto, false);
        if (!Result.isSuccess(firstResult)) {
            return Result.newFaild(firstResult.getRc(), firstResult.getErr());
        }

        Result<Map<String, String>> lastResult = ktvDataClient.queryFirstLast(queryDto, true);
        if (!Result.isSuccess(lastResult)) {
            return Result.newFaild(lastResult.getRc(), lastResult.getErr());
        }

        Map<String, String> firstMap = firstResult.getRet();
        if (CollectionUtils.isEmpty(firstMap)) {
            LOGGER.warn("firstMap为空，数据库中无数据,参数={}", JSON.toJSONString(queryDto));
            return Result.newFaild("数据库中无数据");
        }

        Map<String, String> lastMap = lastResult.getRet();
        if (CollectionUtils.isEmpty(lastMap)) {
            LOGGER.warn("lastMap为空，数据库中无数据,参数={}", JSON.toJSONString(queryDto));
            return Result.newFaild("数据库中无数据");
        }

        double firstOil = Double.parseDouble(firstMap.get(GpsMsgParam.ATTR_OIL));

        double lastOil = Double.parseDouble(lastMap.get(GpsMsgParam.ATTR_OIL));

        Map<String, Double> oilMap = Maps.newHashMap();
        oilMap.put(allthings.iot.ktv.common.ColumnConstant.KTV_COL_STARTOIL, getOil(firstOil, firstMap.get(GpsMsgParam
                .ATTR_OIL_TYPE), 200));
        oilMap.put(allthings.iot.ktv.common.ColumnConstant.KTV_COL_ENDOIL, getOil(lastOil, lastMap.get(GpsMsgParam
                .ATTR_OIL_TYPE), 200));

        return Result.newSuccess(oilMap);
    }

    @Override
    public Result<?> saveDevice(SaveDeviceDto saveDeviceDto) {
        try {
            if (saveDeviceDto == null) {
                LOGGER.warn("未传入参数");
                return Result.newFaild("参数不能为空");
            }
            String deviceId = saveDeviceDto.getDeviceId();
            if (StringUtils.isBlank(deviceId)) {
                LOGGER.warn("设备号不能为空,参数：{}", JSON.toJSONString(saveDeviceDto));
                return Result.newFaild("设备号不能为空");
            }
            List<GpsDto> gpsList = saveDeviceDto.getGpsList();
            if (CollectionUtils.isEmpty(gpsList)) {
                LOGGER.warn("GPS数据不能为空, 参数：{}", JSON.toJSONString(saveDeviceDto));
                return Result.newFaild("GPS数据不能为空");
            }

            // 上传点位限制，每次上传最大为100点位，超过100点位，保留前100点位超过部分舍弃
            if (uploadMax > 0 && gpsList.size() > uploadMax) {
                LOGGER.warn("单次上传点位超过{}，点位数量：{}，点位列表：{}", uploadMax, gpsList.size(), JSON.toJSONString(gpsList));
                gpsList = gpsList.subList(0, uploadMax - 1);
            }

            gpsDeal(gpsList, deviceId);
            saveDeviceDto.setGpsList(gpsList);
            saveKvs(saveDeviceDto);
            return Result.newSuccess();
        } catch (IllegalArgumentException iae) {
            LOGGER.error("参数异常，参数：{}，错误信息：{}", JSON.toJSONString(saveDeviceDto), iae);
            return Result.newFaild(iae.getMessage());
        } catch (Exception ee) {
            LOGGER.error("保存车机数据失败， 车机数据：{}，异常信息：{}", JSON.toJSONString(saveDeviceDto), ee);
            return Result.newFaild("保存车机数据失败");
        }
    }

    @Override
    public Result<Map.Entry<String, List<Map<String, Object>>>> queryKvLast(QueryDto queryDto) {
        Result<List<Map<String, String>>> result = ktvDataClient.queryKvLast(queryDto);
        if (!Result.isSuccess(result)) {
            return Result.newFaild(result.getRc(), result.getErr());
        }
        List<Map<String, String>> mapList = result.getRet();
        String[] factors = queryDto.getFactors();
        List<Map<String, Object>> returnList = lastDataDeal(mapList, factors);
        return Result.newSuccess(Maps.immutableEntry("items", returnList));
    }

    @Override
    public Result<Map.Entry<String, List<Map<String, Object>>>> queryBathLast(QueryListCriteriaDto queryListCriteriaDto) {
        List<String> deviceIds = queryListCriteriaDto.getDeviceIds();
        if (deviceIds.isEmpty()) {
            LOGGER.warn("查询设备最新数据，设备编码为空，参数：{}", JSON.toJSONString(queryListCriteriaDto));
            return Result.newFaild("设备编码不能为空");
        }

        String[] factors = queryListCriteriaDto.getFactors();
        if (factors == null || factors.length <= 0) {
            LOGGER.warn("查询设备最新因子数据，因子编码列表为空，参数：{}", JSON.toJSONString(queryListCriteriaDto));
            return Result.newFaild("因子不能为空");
        }

        Integer partyId = queryListCriteriaDto.getPartyId();
        if (queryListCriteriaDto.getPartyId() == null) {
            LOGGER.warn("查询设备最新因子数据，会员id为空， 参数：{}", JSON.toJSONString(queryListCriteriaDto));
            return Result.newFaild("会员id不能为空");
        }

        List<ComparisonCriteriaDto> conditionList = queryListCriteriaDto.getConditions();
        Set<String> factorSet = Sets.newHashSet();
        List<String> factorList = Lists.newArrayList();
        boolean condition = (conditionList != null && !CollectionUtils.isEmpty(conditionList));
        if (condition) {
            for (ComparisonCriteriaDto dto : conditionList) {
                String factor = dto.getFactor();
                if (StringUtils.isBlank(factor)) {
                    continue;
                }
                factorList.add(factor);
            }
        }

        factorSet.addAll(factorList);
        factorSet.addAll(Lists.newArrayList(factors));
        List<Map<String, Object>> returnList = Lists.newArrayList();

        BatchQueryDto batchQueryDto = new BatchQueryDto();
        batchQueryDto.setFactors(factorSet.toArray(new String[factorSet.size()]));
        batchQueryDto.setPartyId(String.valueOf(partyId));
        batchQueryDto.setDeviceIds(deviceIds);
        Result<Map<String, List<Map<String, String>>>> result = ktvDataClient.batchQueryKvLast(batchQueryDto);
        if (!Result.isSuccess(result)) {
            LOGGER.error("调用ktv批量查询设备最新数据失败，原始传入参数：{}， 传入ktv参数：{}，ktv返回结果：{}",
                    JSON.toJSONString(queryListCriteriaDto), JSON.toJSONString(batchQueryDto), JSON.toJSONString(result));
            return Result.newFaild(result.getRc(), result.getErr());
        }
        Map<String, List<Map<String, String>>> resultMap = result.getRet();
        Set<String> deviceIdSet = resultMap.keySet();
        List<String> deviceIdList = Lists.newArrayList(deviceIdSet);
        for (String deviceId : deviceIdList) {
            boolean isOpen = false;
            List<Map<String, String>> mapList = resultMap.get(deviceId);
            if (CollectionUtils.isEmpty(mapList)) {
                continue;
            }
            List<Map<String, String>> list = Lists.newArrayList();
            if (condition) {
                for (Map<String, String> map : mapList) {
                    if (isOpen) {
                        break;
                    }
                    for (ComparisonCriteriaDto dto : conditionList) {
                        String factor = dto.getFactor();
                        if (StringUtils.isBlank(factor)) {
                            list.add(map);
                            continue;
                        }
                        String value = dto.getValue();
                        if (StringUtils.isBlank(value)) {
                            list.add(map);
                            continue;
                        }
                        String factorValue = map.get(factor);
                        if (StringUtils.isBlank(factorValue)) {
                            list.add(map);
                            continue;
                        }
                        if (value.equals(factorValue)) {
                            list.add(map);
                        } else {
                            list = Lists.newArrayList();
                            isOpen = true;
                            break;
                        }
                    }
                }
            } else {
                list.addAll(mapList);
            }
            Map<String, Object> returnMap = Maps.newHashMap();
            List<Map<String, Object>> dataList = lastDataDeal(list, factors);
            returnMap.put("datas", dataList);
            returnMap.put("deviceId", deviceId);
            returnList.add(returnMap);
        }
        return Result.newSuccess(Maps.immutableEntry("items", returnList));
    }

    @Override
    public Result<List<Map<String, String>>> getBusinessList(GpsQueryDto gpsQueryDto) {
        if (gpsQueryDto == null) {
            return Result.newFaild("参数不能为空");
        }
        String format = gpsQueryDto.getFormat();

        String businessCode = gpsQueryDto.getBusinessCode();
        if (org.apache.commons.lang.StringUtils.isBlank(businessCode)) {
            LOGGER.warn("根据业务编码查询数据，业务编码为空，参数：{}", JSON.toJSONString(gpsQueryDto));
            return Result.newFaild("业务编码不能为空");
        }
        Long startTime = gpsQueryDto.getStartTime();
        Long endTime = gpsQueryDto.getEndTime();
        if (startTime == null || endTime == null) {
            LOGGER.warn("根据业务编码查询数据，时间参数为空，参数：{}", JSON.toJSONString(gpsQueryDto));
            return Result.newFaild("StartTime Or EndTime is null");
        }
        if (startTime > endTime) {
            LOGGER.warn("根据业务编码查询数据，时间参数错误，开始时间大于结束时间，参数：{}", JSON.toJSONString(gpsQueryDto));
            return Result.newFaild("开始时间大于结束时间");
        }

        String[] factors;
        if (GpsConstant.BD09.equalsIgnoreCase(format)) {
            factors = new String[]{GpsMsgParam.ATTR_GPS_SPEED, GpsMsgParam.ATTR_GPS_BD09_LONGITUDE, GpsMsgParam.ATTR_GPS_BD09_LATITUDE,
                    GpsMsgParam.ATTR_GPS_DIRECTION};
        } else if (GpsConstant.GCJ02.equalsIgnoreCase(format)) {
            factors = new String[]{GpsMsgParam.ATTR_GPS_SPEED, GpsMsgParam.ATTR_GPS_GCJ02_LONGITUDE, GpsMsgParam.ATTR_GPS_GCJ02_LATITUDE,
                    GpsMsgParam.ATTR_GPS_DIRECTION};
        } else {
            factors = new String[]{GpsMsgParam.ATTR_GPS_SPEED, GpsMsgParam.ATTR_GPS_LONGITUDE, GpsMsgParam.ATTR_GPS_LATITUDE,
                    GpsMsgParam.ATTR_GPS_DIRECTION};
        }

        QueryDto queryDto = new QueryDto();
        queryDto.setDeviceId(businessCode);
        queryDto.setStartDatetime(gpsQueryDto.getStartTime());
        queryDto.setEndDatetime(gpsQueryDto.getEndTime());
        queryDto.setFactors(factors);
        queryDto.setPartyId("-100");
        Result<List<Map<String, String>>> result = ktvDataClient.queryKvByBusiness(queryDto);
        if (!Result.isSuccess(result)) {
            LOGGER.warn("调用queryKvByBusiness失败，失败原因：{}", result.getErr());
            return result;
        }

        List<Map<String, String>> mapList = result.getRet();
        List<Map<String, String>> resultList = handStopTime(mapList, gpsQueryDto.getDeviceid(), format, true);
        for (Map<String, String> map : resultList) {
            String direction = map.remove(GpsMsgParam.ATTR_GPS_DIRECTION);
            map.put("direction", direction == null ? "-1" : direction);
            map.put("businessCode", businessCode);
            map.remove("deviceid");
            map.remove("model");
        }
        return Result.newSuccess(resultList);
    }

    @Override
    public Result<Map<String, String>> getBusinessLatesPoint(GpsQueryDto gpsQueryDto) {
        if (gpsQueryDto == null) {
            return Result.newFaild("参数不能为空");
        }
        String format = gpsQueryDto.getFormat();
        String[] factors;
        if (GpsConstant.BD09.equalsIgnoreCase(format)) {
            factors = new String[]{GpsMsgParam.ATTR_GPS_SPEED, GpsMsgParam.ATTR_GPS_BD09_LONGITUDE,
                    GpsMsgParam.ATTR_GPS_BD09_LATITUDE, GpsMsgParam.ATTR_GPS_DIRECTION,
                    GpsMsgParam.ATTR_GPS_LONGITUDE, GpsMsgParam.ATTR_GPS_LATITUDE};
        } else if (GpsConstant.GCJ02.equalsIgnoreCase(format)) {
            factors = new String[]{GpsMsgParam.ATTR_GPS_SPEED, GpsMsgParam.ATTR_GPS_GCJ02_LONGITUDE,
                    GpsMsgParam.ATTR_GPS_GCJ02_LATITUDE, GpsMsgParam.ATTR_GPS_DIRECTION,
                    GpsMsgParam.ATTR_GPS_LONGITUDE, GpsMsgParam.ATTR_GPS_LATITUDE};
        } else {
            factors = new String[]{GpsMsgParam.ATTR_GPS_SPEED, GpsMsgParam.ATTR_GPS_LONGITUDE,
                    GpsMsgParam.ATTR_GPS_LATITUDE, GpsMsgParam.ATTR_GPS_DIRECTION};
        }

        String businessCode = gpsQueryDto.getBusinessCode();
        if (StringUtils.isBlank(businessCode)) {
            LOGGER.warn("根据业务编码查询点位数据，业务编码为空，参数：{}", JSON.toJSONString(gpsQueryDto));
            return Result.newFaild("业务编码不能为空");
        }

        QueryDto queryDto = new QueryDto();
        queryDto.setDeviceId(businessCode);
        queryDto.setFactors(factors);
        queryDto.setPartyId("-100");

        Result<List<Map<String, String>>> result = ktvDataClient.queryBusinessLast(queryDto);
        if (!Result.isSuccess(result)) {
            LOGGER.warn("根据业务编码查询点位数据，ktv查询返回错误，参数：{}，ktv参数：{}，ktv返回结果：{}",
                    JSON.toJSONString(gpsQueryDto), JSON.toJSONString(queryDto), JSON.toJSONString(result));
            return Result.newFaild(result.getRc(), result.getErr());
        }

        List<Map<String, String>> mapList = result.getRet();
        Map<String, String> mapResult = Maps.newHashMap();
        if (CollectionUtils.isEmpty(mapList)) {
            LOGGER.warn("根据业务编码查询点位数据，ktv查询返回数据为空，参数：{}，ktv参数：{}，ktv返回结果：{}",
                    JSON.toJSONString(gpsQueryDto), JSON.toJSONString(queryDto), JSON.toJSONString(result));
            return Result.newSuccess(mapResult);
        }

        mapResult.put("businessCode", businessCode);
        for (Map<String, String> map : mapList) {
            mapResult.putAll(map);
        }

        mapResult.put(ColumnConstant.COL_GPS_REALTIME, mapResult.remove(GpsMsgParam.ATTR_GPS_DATETIME));
        mapResult.put("speed", mapResult.remove(GpsMsgParam.ATTR_GPS_SPEED));
        String lon = mapResult.remove(GpsMsgParam.ATTR_GPS_LONGITUDE);
        String lat = mapResult.remove(GpsMsgParam.ATTR_GPS_LATITUDE);
//        if (!StringUtils.isBlank(lat) && !StringUtils.isBlank(lon)) {
//            Map<String, String> resultMap = ktvLbsManager.addressDecode(Double.valueOf(lon), Double.valueOf(lat), GpsConstant.WGS84);
//            mapResult.put(GpsMsgParam.ATTR_GPS_ADDRESS, resultMap.get(GpsMsgParam.ATTR_GPS_ADDRESS));
//        }

        if (GpsConstant.BD09.equalsIgnoreCase(format)) {
            mapResult.put(ColumnConstant.COL_GPS_LATITUDE, mapResult.remove(GpsMsgParam.ATTR_GPS_BD09_LATITUDE));
            mapResult.put(ColumnConstant.COL_GPS_LONGITUDE, mapResult.remove(GpsMsgParam.ATTR_GPS_BD09_LONGITUDE));
        } else if (GpsConstant.GCJ02.equalsIgnoreCase(format)) {
            mapResult.put(ColumnConstant.COL_GPS_LATITUDE, mapResult.remove(GpsMsgParam.ATTR_GPS_GCJ02_LATITUDE));
            mapResult.put(ColumnConstant.COL_GPS_LONGITUDE, mapResult.remove(GpsMsgParam.ATTR_GPS_GCJ02_LONGITUDE));
        } else {
            mapResult.put(ColumnConstant.COL_GPS_LATITUDE, lat);
            mapResult.put(ColumnConstant.COL_GPS_LONGITUDE, lon);
        }
        String direction = mapResult.remove(GpsMsgParam.ATTR_GPS_DIRECTION);
        mapResult.put("direction", direction == null ? "-1" : direction);
        return Result.newSuccess(mapResult);
    }

    @Override
    public Result<Map<String, String>> getDepartureRecord(GpsQueryDto gpsQueryDto) {
        if (gpsQueryDto == null) {
            return Result.newFaild("参数不能为空");
        }

        Map<String, String> resultMap = Maps.newHashMap();
        String[] factors = {GpsMsgParam.ATTR_GPS_MILEAGE, GpsMsgParam.ATTR_GPS_SPEED};

        String businessCode = gpsQueryDto.getBusinessCode();
        if (StringUtils.isBlank(businessCode)) {
            LOGGER.warn("根据业务编码查询出车收车记录，业务编码为空，参数：{}", JSON.toJSONString(gpsQueryDto));
            return Result.newFaild("业务编码不能为空");
        }

        Long dateTime = gpsQueryDto.getDateTime();
        if (dateTime == null) {
            LOGGER.warn("根据业务编码查询出车收车记录，时间为空，参数：{}", JSON.toJSONString(gpsQueryDto));
            return Result.newFaild("时间不能为空");
        }

        long startTime = VehicleDateTimeUtil.getMillisDayStart(dateTime);
        long endTime = startTime + 24 * 60 * 60 * 1000;

        QueryDto queryDto = new QueryDto();
        queryDto.setDeviceId(businessCode);
        queryDto.setStartDatetime(startTime);
        queryDto.setEndDatetime(endTime);
        queryDto.setFactors(factors);
        queryDto.setPartyId("-100");
        Result<List<Map<String, String>>> result = ktvDataClient.queryKvByBusiness(queryDto);
        if (!Result.isSuccess(result)) {
            LOGGER.warn("根据业务编码查询出车收车记录，ktv查询点位数据返回错误，参数：{}， ktv参数：{}，ktv返回结果：{}",
                    JSON.toJSONString(gpsQueryDto), JSON.toJSONString(queryDto), JSON.toJSONString(result));
            return Result.newFaild(result.getRc(), result.getErr());
        }

        Map<String, String> startMap = Maps.newHashMap();
        Map<String, String> endMap = Maps.newHashMap();
        List<Map<String, String>> mapList = result.getRet();
        if (CollectionUtils.isEmpty(mapList)) {
            LOGGER.warn("根据业务编码查询出车收车记录，ktv查询点位数据返回数据为空，参数：{}， ktv参数：{}，ktv返回结果：{}",
                    JSON.toJSONString(gpsQueryDto), JSON.toJSONString(queryDto), JSON.toJSONString(result));
            return Result.newSuccess(resultMap);
        }

        for (Map<String, String> map : mapList) {
            Double speed = Double.valueOf(map.get(GpsMsgParam.ATTR_GPS_SPEED) == null ? "0" : map.get(GpsMsgParam.ATTR_GPS_SPEED));
            if (speed > 0) {
                startMap = map;
                break;
            }
        }

        if (CollectionUtils.isEmpty(startMap)) {
            return Result.newFaild("没有出车记录");
        }

        for (int i = mapList.size() - 1; i >= 0; i--) {
            Map<String, String> map = mapList.get(i);
            Double speed = Double.valueOf(map.get(GpsMsgParam.ATTR_GPS_SPEED) == null ? "0" : map.get(GpsMsgParam.ATTR_GPS_SPEED));
            if (speed > 0) {
                endMap = map;
                break;
            }
        }

        if (startMap.get(GpsMsgParam.ATTR_GPS_DATETIME).equals(endMap.get(GpsMsgParam.ATTR_GPS_DATETIME))) {
            return Result.newFaild("没有出车记录");
        }

        String departureTime = startMap.get(GpsMsgParam.ATTR_GPS_DATETIME);
        String returnTime = endMap.get(GpsMsgParam.ATTR_GPS_DATETIME);
        String runTime = String.valueOf(Long.parseLong(returnTime) - Long.parseLong(departureTime));
        String departureMileage = startMap.get(GpsMsgParam.ATTR_GPS_MILEAGE) == null ? "0" : startMap.get(GpsMsgParam.ATTR_GPS_MILEAGE);
        String returnMileage = endMap.get(GpsMsgParam.ATTR_GPS_MILEAGE) == null ? "0" : endMap.get(GpsMsgParam.ATTR_GPS_MILEAGE);
        String dayMileage = String.valueOf(new DecimalFormat("0.00").format(Double.parseDouble(returnMileage) - Double.parseDouble(departureMileage)));
        resultMap.put("businessCode", businessCode);
        resultMap.put("departureTime", departureTime);
        resultMap.put("returnTime", returnTime);
        resultMap.put("runTime", runTime);
        resultMap.put("departureMileage", departureMileage);
        resultMap.put("returnMileage", returnMileage);
        resultMap.put("dayMileage", dayMileage);
        return Result.newSuccess(resultMap);
    }

    @Override
    public Result<PageResultDto> queryGpsListPage(GpsQueryDto gpsQueryDto) {
        if (gpsQueryDto == null) {
            return Result.newFaild("参数不能为空");
        }
        String format = gpsQueryDto.getFormat();
        String[] factors;
        if (GpsConstant.GCJ02.equalsIgnoreCase(format)) {
            factors = new String[]{GpsMsgParam.ATTR_GPS_SPEED, GpsMsgParam.ATTR_GPS_ADDRESS, GpsMsgParam.ATTR_GPS_PROVINCE,
                    GpsMsgParam.ATTR_GPS_CITY, GpsMsgParam.ATTR_GPS_REGION, GpsMsgParam.ATTR_GPS_GCJ02_LATITUDE,
                    GpsMsgParam.ATTR_GPS_GCJ02_LONGITUDE};
        } else if (GpsConstant.BD09.equalsIgnoreCase(format)) {
            factors = new String[]{GpsMsgParam.ATTR_GPS_SPEED, GpsMsgParam.ATTR_GPS_ADDRESS, GpsMsgParam.ATTR_GPS_PROVINCE,
                    GpsMsgParam.ATTR_GPS_CITY, GpsMsgParam.ATTR_GPS_REGION, GpsMsgParam.ATTR_GPS_BD09_LONGITUDE,
                    GpsMsgParam.ATTR_GPS_BD09_LATITUDE};
        } else {
            factors = new String[]{GpsMsgParam.ATTR_GPS_LATITUDE, GpsMsgParam.ATTR_GPS_LONGITUDE, GpsMsgParam.ATTR_GPS_SPEED,
                    GpsMsgParam.ATTR_GPS_ADDRESS, GpsMsgParam.ATTR_GPS_PROVINCE, GpsMsgParam.ATTR_GPS_CITY, GpsMsgParam.ATTR_GPS_REGION};
        }

        if (gpsQueryDto.getPartyid() == 0 || StringUtils.isBlank(gpsQueryDto.getDeviceid())) {
            LOGGER.warn("查询GPS数据，设备号或会员id为空，参数：{}", JSON.toJSONString(gpsQueryDto));
            return Result.newFaild("PartyId Or DeviceId is null");
        }

        if (gpsQueryDto.getStartTime() == null || gpsQueryDto.getEndTime() == null) {
            LOGGER.warn("查询GPS数据，开始时间或结束时间为空，参数：{}", JSON.toJSONString(gpsQueryDto));
            return Result.newFaild("StartTime Or EndTime is null");
        }
        Integer pageSize = gpsQueryDto.getPageSize();
        if (pageSize == null || pageSize <= 0) {
            LOGGER.warn("分页查询轨迹数据失败，分页参数错误，参数：{}", gpsQueryDto.toString());
            return Result.newFaild("分页参数异常，请检查参数");
        }
        PageQueryDto queryDto = new PageQueryDto();
        String deviceId = gpsQueryDto.getDeviceid();
        queryDto.setPartyId(String.valueOf(gpsQueryDto.getPartyid()));
        queryDto.setDeviceId(deviceId);
        queryDto.setStartDatetime(gpsQueryDto.getStartTime());
        queryDto.setEndDatetime(gpsQueryDto.getEndTime());
        queryDto.setFactors(factors);
        queryDto.setPageSize(gpsQueryDto.getPageSize());
        queryDto.setEndRowTime(gpsQueryDto.getDateTime());
        Result<PageResultDto> result = ktvDataClient.queryKvPage(queryDto);
        if (!Result.isSuccess(result)) {
            LOGGER.warn("查询GPS数据，ktv返回错误，参数：{}，ktv参数：{}，ktv返回结果：{}", JSON.toJSONString(gpsQueryDto),
                    JSON.toJSONString(queryDto), JSON.toJSONString(result));
            return result;
        }

        PageResultDto pageResultDto = result.getRet();
        List<Map<String, String>> queryResultList = pageResultDto.getDataList();

        if (CollectionUtils.isEmpty(queryResultList)) {
            LOGGER.warn("查询GPS数据，查询数据为空，参数：{}，ktv参数：{}，ktv返回：{}", JSON.toJSONString(gpsQueryDto),
                    JSON.toJSONString(queryDto), JSON.toJSONString(result));
            return result;
        }

        processResult(format, deviceId, queryResultList, false);
        return result;
    }

    /**
     * 处理查询结果
     *
     * @param format
     * @param deviceId
     * @param queryResultList
     */
    private void processResult(String format, String deviceId, List<Map<String, String>> queryResultList, boolean dateFormat) {
        for (Map<String, String> map : queryResultList) {
            if (GpsConstant.GCJ02.equalsIgnoreCase(format)) {
                map.put(ColumnConstant.COL_GPS_LONGITUDE, map.remove(GpsMsgParam.ATTR_GPS_GCJ02_LONGITUDE));
                map.put(ColumnConstant.COL_GPS_LATITUDE, map.remove(GpsMsgParam.ATTR_GPS_GCJ02_LATITUDE));
            } else if (GpsConstant.BD09.equalsIgnoreCase(format)) {
                map.put(ColumnConstant.COL_GPS_LONGITUDE, map.remove(GpsMsgParam.ATTR_GPS_BD09_LONGITUDE));
                map.put(ColumnConstant.COL_GPS_LATITUDE, map.remove(GpsMsgParam.ATTR_GPS_BD09_LATITUDE));
            } else {
                map.put(ColumnConstant.COL_GPS_LONGITUDE, map.remove(GpsMsgParam.ATTR_GPS_LONGITUDE));
                map.put(ColumnConstant.COL_GPS_LATITUDE, map.remove(GpsMsgParam.ATTR_GPS_LATITUDE));
            }
            String datetime = map.remove(GpsMsgParam.ATTR_GPS_DATETIME);
            if (dateFormat) {
                map.put(ColumnConstant.COL_GPS_REALTIME, DATE_TIME_FORMATTER.print(Long.parseLong(datetime)));
                map.put("model", "");
            } else {
                map.put(ColumnConstant.COL_GPS_REALTIME, datetime);
            }
            map.put("speed", map.remove(GpsMsgParam.ATTR_GPS_SPEED));
            map.put("deviceid", deviceId);
        }
    }

    /**
     * @param mapList
     * @param factors
     * @return
     */
    private List<Map<String, Object>> lastDataDeal(List<Map<String, String>> mapList, String[] factors) {
        List<Map<String, Object>> returnList = Lists.newArrayList();
        if (CollectionUtils.isEmpty(mapList)) {
            return returnList;
        }
        long gpsDatetime = 0L;
        for (Map<String, String> map : mapList) {
            for (String factor : factors) {
                String value = map.get(factor);
                if (StringUtils.isBlank(value)) {
                    continue;
                }
                long dateTime = Long.parseLong(map.get(GpsMsgParam.ATTR_GPS_DATETIME));
                if (dateTime > gpsDatetime) {
                    gpsDatetime = dateTime;
                }
                if (GpsMsgParam.ATTR_GPS_DATETIME.equals(factor)) {
                    continue;
                }
                Map<String, Object> maps = Maps.newHashMap();
                maps.put(allthings.iot.ktv.common.ColumnConstant.KTV_COL_DATETIME, dateTime);
                maps.put(allthings.iot.ktv.common.ColumnConstant.KTV_COL_FACTOR, factor);
                maps.put(allthings.iot.ktv.common.ColumnConstant.KTV_COL_VALUE, value);
                returnList.add(maps);
            }
        }
        if (Lists.newArrayList(factors).contains(GpsMsgParam.ATTR_GPS_DATETIME)) {
            Map<String, Object> maps = Maps.newHashMap();
            maps.put(allthings.iot.ktv.common.ColumnConstant.KTV_COL_DATETIME, gpsDatetime);
            maps.put(allthings.iot.ktv.common.ColumnConstant.KTV_COL_FACTOR, GpsMsgParam.ATTR_GPS_DATETIME);
            maps.put(allthings.iot.ktv.common.ColumnConstant.KTV_COL_VALUE, gpsDatetime);
            returnList.add(maps);
        }
        return returnList;
    }

    /**
     * 校验经纬是否为空
     *
     * @param gpsList
     * @param deviceId
     */
    private void gpsDeal(List<GpsDto> gpsList, String deviceId) {
        for (GpsDto dto : gpsList) {
            dto.setDeviceId(deviceId);
            if (StringUtils.isBlank(dto.getPartyid())) {
                dto.setPartyid("-100");
            }

            String longitude = dto.getLongitude();
            String latitude = dto.getLatitude();
            if (StringUtils.isBlank(longitude) || StringUtils.isBlank(latitude)) {
                throw new IllegalArgumentException("经纬度不能为空");
            }
            if (StringUtils.isBlank(dto.getRealtime())) {
                throw new IllegalArgumentException("时间不能为空");
            }
        }
    }

    /**
     * 处理lbs的返回结果
     *
     * @param response
     * @return
     */
    private Result<?> handleResult(Response<? extends ResultDto> response) {
        if (response.isSuccessful()) {
            ResultDto resultDto = response.body();
            if (resultDto != null) {
                if (resultDto.getCode() != 0) {
                    return Result.newFaild(resultDto.getMsg());
                }
                return Result.newSuccess(resultDto.getData());
            }
            return Result.newSuccess();
        } else {
            return Result.newFaild();
        }
    }


    /**
     * 保存kv 数据
     *
     * @param saveDeviceDto
     */
    private void saveKvs(SaveDeviceDto saveDeviceDto) {
        List<GpsDto> gpsList = saveDeviceDto.getGpsList();
        List<SaveKtvFactorDto> kvs = saveDeviceDto.getKvs();
        List<KvsDto> kvsDtoList = Lists.newArrayList();
        for (GpsDto gpsDto : gpsList) {
            long realtime = DATE_TIME_FORMATTER.parseMillis(gpsDto.getRealtime());
            if (StringUtils.isNotBlank(gpsDto.getSpeed())) {
                KvsDto speed = new KvsDto();
                speed.setFactorId(GpsMsgParam.ATTR_GPS_SPEED);
                speed.setFactorValue(gpsDto.getSpeed());
                speed.setRealTime(realtime);
                kvsDtoList.add(speed);
            }

            gpsConvertor(Double.valueOf(gpsDto.getLongitude()),
                    Double.valueOf(gpsDto.getLatitude()), kvsDtoList, realtime, gpsDto.getCoordinates());
        }

        if (!CollectionUtils.isEmpty(kvs)) {
            for (SaveKtvFactorDto factorDto : kvs) {
                KvsDto kvsDto = new KvsDto();
                kvsDto.setFactorValue(factorDto.getValue());
                kvsDto.setFactorId(factorDto.getCode());
                kvsDto.setRealTime(factorDto.getRealtime());
                kvsDtoList.add(kvsDto);
            }
        }

        GpsDeviceDto gpsDeviceDto = new GpsDeviceDto();
        gpsDeviceDto.setDeviceId(saveDeviceDto.getDeviceId());
        gpsDeviceDto.setKvs(kvsDtoList);

        ktvDataClient.saveKtvData(gpsDeviceDto);
    }

    /**
     * 坐标系转换
     *
     * @param longitude
     * @param latitude
     * @param kvsDtoList
     * @param realTime
     * @param format
     */
    private void gpsConvertor(Double longitude, Double latitude, List<KvsDto> kvsDtoList, long realTime, String format) {
        if (GpsConstant.GCJ02.equalsIgnoreCase(format)) {
            double[] gcj02Gps = {longitude, latitude};
            structureDto(gcj02Gps, GpsConstant.GCJ02, kvsDtoList, realTime);
            double[] bd09Gps = GpsUtil.gpsConvertor(longitude, latitude, CoorType.GCJ02, CoorType.BD09LL);
            structureDto(bd09Gps, GpsConstant.BD09, kvsDtoList, realTime);
            double[] wgs84Gps = GpsUtil.gpsConvertor(longitude, latitude, CoorType.GCJ02, CoorType.WGS84);
            structureDto(wgs84Gps, GpsConstant.WGS84, kvsDtoList, realTime);
        } else if (GpsConstant.BD09.equalsIgnoreCase(format)) {
            double[] bd09Gps = {longitude, latitude};
            structureDto(bd09Gps, GpsConstant.BD09, kvsDtoList, realTime);
            double[] gcj02Gps = GpsUtil.gpsConvertor(longitude, latitude, CoorType.BD09LL, CoorType.GCJ02);
            structureDto(gcj02Gps, GpsConstant.GCJ02, kvsDtoList, realTime);
            double[] wgs84Gps = GpsUtil.gpsConvertor(longitude, latitude, CoorType.BD09LL, CoorType.WGS84);
            structureDto(wgs84Gps, GpsConstant.WGS84, kvsDtoList, realTime);
        } else if (StringUtils.isBlank(format) || GpsConstant.WGS84.equalsIgnoreCase(format)) {
            double[] wgs84Gps = {longitude, latitude};
            structureDto(wgs84Gps, GpsConstant.WGS84, kvsDtoList, realTime);
            double[] bd09Gps = GpsUtil.gpsConvertor(longitude, latitude, CoorType.WGS84, CoorType.BD09LL);
            structureDto(bd09Gps, GpsConstant.BD09, kvsDtoList, realTime);
            double[] gcj02Gps = GpsUtil.gpsConvertor(longitude, latitude, CoorType.WGS84, CoorType.GCJ02);
            structureDto(gcj02Gps, GpsConstant.GCJ02, kvsDtoList, realTime);
        } else {
            LOGGER.warn("坐标系错误，参数为：{}", format);
            throw new IllegalArgumentException("坐标系错误");
        }
    }

//    private void addFactor(Double longitude, Double latitude, List<KvsDto> kvsDtoList, long realTime, String format) {
//        try {
//            Map<String, String> resultMap = ktvLbsManager.addressDecode(longitude, latitude, StringUtils.isEmpty(format) ? format : format.toUpperCase());
//            if (!CollectionUtils.isEmpty(resultMap)) {
//                KvsDto address = new KvsDto();
//                address.setRealTime(realTime);
//                address.setFactorId(GpsMsgParam.ATTR_GPS_ADDRESS);
//                address.setFactorValue(resultMap.get(GpsMsgParam.ATTR_GPS_ADDRESS));
//                kvsDtoList.add(address);
//
//                KvsDto city = new KvsDto();
//                city.setRealTime(realTime);
//                city.setFactorId(resultMap.get(GpsMsgParam.ATTR_GPS_CITY));
//                city.setFactorValue(resultMap.get(GpsMsgParam.ATTR_GPS_ADDRESS));
//                kvsDtoList.add(city);
//
//                KvsDto provice = new KvsDto();
//                provice.setRealTime(realTime);
//                provice.setFactorId(GpsMsgParam.ATTR_GPS_PROVINCE);
//                provice.setFactorValue(resultMap.get(GpsMsgParam.ATTR_GPS_PROVINCE));
//                kvsDtoList.add(provice);
//
//                KvsDto regino = new KvsDto();
//                regino.setRealTime(realTime);
//                regino.setFactorId(GpsMsgParam.ATTR_GPS_REGION);
//                regino.setFactorValue(resultMap.get(GpsMsgParam.ATTR_GPS_REGION));
//                kvsDtoList.add(regino);
//            }
//        } catch (Exception e) {
//            LOGGER.warn("调用LBS接口查询地址异常", e);
//        }
//
//    }

    /**
     * @param latLon
     * @param format
     * @param kvsDtoList
     * @param realTime
     */
    private void structureDto(double[] latLon, String format, List<KvsDto> kvsDtoList, long realTime) {
        KvsDto lat = new KvsDto();
        lat.setRealTime(realTime);
        if (GpsConstant.BD09.equalsIgnoreCase(format)) {
            lat.setFactorId(GpsMsgParam.ATTR_GPS_BD09_LATITUDE);
        } else if (GpsConstant.GCJ02.equalsIgnoreCase(format)) {
            lat.setFactorId(GpsMsgParam.ATTR_GPS_GCJ02_LATITUDE);
        } else {
            lat.setFactorId(GpsMsgParam.ATTR_GPS_LATITUDE);
        }
        lat.setFactorValue(String.valueOf(latLon[1]));
        kvsDtoList.add(lat);

        KvsDto lon = new KvsDto();
        lon.setRealTime(realTime);
        if (GpsConstant.BD09.equalsIgnoreCase(format)) {
            lon.setFactorId(GpsMsgParam.ATTR_GPS_BD09_LONGITUDE);
        } else if (GpsConstant.GCJ02.equalsIgnoreCase(format)) {
            lon.setFactorId(GpsMsgParam.ATTR_GPS_GCJ02_LONGITUDE);
        } else {
            lon.setFactorId(GpsMsgParam.ATTR_GPS_LONGITUDE);
        }
        lon.setFactorValue(String.valueOf(latLon[0]));
        kvsDtoList.add(lon);
    }
}
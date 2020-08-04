package allthings.iot.ktv.service;

import allthings.iot.common.dto.Result;
import allthings.iot.common.dto.ResultDTO;
import allthings.iot.common.msg.DeviceDataMsg;
import allthings.iot.constant.gps.GpsMsgParam;
import allthings.iot.dos.dto.IotDeviceDTO;
import allthings.iot.dos.dto.IotDeviceStatusBatchQueryDTO;
import allthings.iot.dos.dto.query.IotIovProtocolCodeQueryDto;
import allthings.iot.dss.ui.IotDssApi;
import allthings.iot.ktv.api.IKtvDataService;
import allthings.iot.ktv.common.ColumnConstant;
import allthings.iot.ktv.common.Constant;
import allthings.iot.ktv.common.dto.BatchQueryDto;
import allthings.iot.ktv.common.dto.GpsDeviceDto;
import allthings.iot.ktv.common.dto.IotKtvFactorReportTimeDto;
import allthings.iot.ktv.common.dto.KvsDto;
import allthings.iot.ktv.common.dto.PageQueryDto;
import allthings.iot.ktv.common.dto.PageResultDto;
import allthings.iot.ktv.common.dto.QueryDto;
import allthings.iot.ktv.common.dto.QueryListCriteriaDto;
import allthings.iot.ktv.consumer.KtvDataProducer;
import allthings.iot.ktv.dao.IKtvDataDao;
import allthings.iot.ktv.dao.IKtvDataOfflineDao;
import allthings.iot.util.gps.enums.CoorType;
import allthings.iot.util.gps.util.GpsUtil;
import allthings.iot.util.redis.ICentralCacheService;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * @author :  luhao
 * @FileName :  KtvDataServiceImpl
 * @CreateDate :  2018/1/17
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
@Service("ktvData")
public class KtvDataServiceImpl implements IKtvDataService {
    @Autowired
    private IKtvDataDao ktvDataDao;

    @Autowired
    private IKtvDataOfflineDao ktvDataOfflineDao;

    @Autowired
    private ICentralCacheService cache;

    @Autowired
    private IotDssApi dssApi;
    @Autowired
    private KtvDataProducer ktvDataProducer;

    public static final String REPORT_TIME_FORMAT = "%s@%s";

    static final String LBS_RETRY_NUMBER = "retry_number";

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static final Pattern PATTERN = Pattern.compile("^[-\\+]?[\\d]*$");

    private static final Logger LOGGER = LoggerFactory.getLogger(KtvDataServiceImpl.class);

    @Override
    public void saveOrUpdateReportTime(DeviceDataMsg dataMsg) {
        Map<String, Object> params = dataMsg.getParams();
        //采集时间
        Object gpsDatetime = params.get(GpsMsgParam.ATTR_GPS_DATETIME);
        long dataTime;
        if (gpsDatetime == null) {
            dataTime = dataMsg.getOccurTime();
        } else {
            dataTime = Long.parseLong(String.valueOf(gpsDatetime));
        }
        String deviceId = dataMsg.getSourceDeviceType() + dataMsg.getSourceDeviceId();

        Set<Map.Entry<String, Object>> entrySet = params.entrySet();
        Iterator<Map.Entry<String, Object>> iterator = entrySet.iterator();
        Map<String, String> cacheData = Maps.newHashMap();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();
            String reportTimeAndValue = String.format(REPORT_TIME_FORMAT, String.valueOf(dataTime), String.valueOf(params.get(entry.getKey())));
            cache.putMapValue(deviceId, entry.getKey(), reportTimeAndValue);
//            cacheData.put(entry.getKey(), reportTimeAndValue);
        }
        try {
            // todo 方法使用
//            cache.putObject(deviceId, cacheData);
        } catch (Exception e) {
            LOGGER.error(String.format("保存因子数据和时间失败，数据内容: %s，异常信息", JSON.toJSONString(dataMsg)), e);
        }
    }

    private void saveData(long dataTime, String deviceId, String factor, String value) {
        try {
            String reportTime = cache.getMapField(deviceId, factor, String.class);
            String reportTimeAndValue = String.format(REPORT_TIME_FORMAT, dataTime, value);
            if (reportTime == null) {
                cache.putMapValue(deviceId, factor, reportTimeAndValue);
            } else {
                String time = StringUtils.substringBefore(reportTime, "@");
                if (Long.parseLong(time) < dataTime) {
                    cache.putMapValue(deviceId, factor, reportTimeAndValue);
                }
            }

        } catch (Exception e) {
            LOGGER.error(String.format("保存因子数据和时间失败，参数内容:【dataTime=%s,deviceId=%s,factor=%s,value=%s】，异常信息",
                    dataTime, deviceId, factor, value), e);
        }
    }

    /**
     * 处理ktv数据
     *
     * @param dataMsg 设备数据消息
     */
    @Override
    public void handleKtvData(DeviceDataMsg dataMsg) {
        Map<String, Object> params = dataMsg.getParams();
        //处理批量上传的情况
        List<Map<String, Object>> dataContent = (List<Map<String, Object>>) params.remove("dataContent");
        if (!CollectionUtils.isEmpty(dataContent)) {
            for (Map<String, Object> data : dataContent) {
                params.putAll(data);
                addFormat(params);
                dataMsg.setParams(params);
                try {
                    saveOrUpdateReportTime(dataMsg);
                    ktvDataDao.saveKtvData(dataMsg);
                } catch (InvalidDataAccessApiUsageException e) {
                    LOGGER.error(String.format("批量保存设备数据失败数据内容：%s，异常信息", JSON.toJSONString(dataMsg)), e);
                }
            }
        } else {
            try {
                addFormat(params);
                dataMsg.setParams(params);
                saveOrUpdateReportTime(dataMsg);
                ktvDataDao.saveKtvData(dataMsg);
            } catch (InvalidDataAccessApiUsageException e) {
                LOGGER.error(String.format("保存设备数据失败，数据内容：%s, 异常信息", JSON.toJSONString(dataMsg)), e);
            }
        }
    }

    private void addFormat(Map<String, Object> params) {
        // 查询车辆地址信息
        String nullStr = "null";
        String latitude = String.valueOf(params.get(GpsMsgParam.ATTR_GPS_LATITUDE));
        String longitude = String.valueOf(params.get(GpsMsgParam.ATTR_GPS_LONGITUDE));

        if (!StringUtils.isBlank(latitude) && !nullStr.equalsIgnoreCase(latitude) && !nullStr.equalsIgnoreCase(longitude) && !StringUtils.isBlank(longitude)) {
            double lon = Double.parseDouble(longitude);
            double lat = Double.parseDouble(latitude);
            boolean isOfChina = GpsUtil.outOfChina(lon, lat);
            if (!isOfChina) {
                double[] bd09Gps = GpsUtil.gpsConvertor(lon, lat, CoorType.WGS84, CoorType.BD09LL);
                params.put(GpsMsgParam.ATTR_GPS_BD09_LONGITUDE, String.valueOf(bd09Gps[0]));
                params.put(GpsMsgParam.ATTR_GPS_BD09_LATITUDE, String.valueOf(bd09Gps[1]));
                double[] gcj02Gps = GpsUtil.gpsConvertor(lon, lat, CoorType.WGS84, CoorType.GCJ02);
                params.put(GpsMsgParam.ATTR_GPS_GCJ02_LONGITUDE, String.valueOf(gcj02Gps[0]));
                params.put(GpsMsgParam.ATTR_GPS_GCJ02_LATITUDE, String.valueOf(gcj02Gps[1]));
//                LOGGER.info("坐标系转换结果：WGS84:【lon:{}，lat:{}】，BD09：【lon:{}，lat:{}】，GCJ02：【lon:{}，lat:{}】",
//                        lon, lat, bd09Gps.getLongitude(), bd09Gps.getLatitude(), gcj02Gps.getLongitude(), gcj02Gps.getLatitude());
            }
        }

    }

    @Override
    public Result<Map<String, String>> getPointCount() {
        try {
            return Result.newSuccess(cache.getAll(Constant.POINT_COUNT_KEY, String.class));
        } catch (Exception e) {
            LOGGER.error("查询点位数量异常，异常信息", e);
            return Result.newFaild();
        }
    }

    @Override
    public Result<?> resetPointCount() {
        try {
            cache.putMapValue(Constant.POINT_COUNT_KEY, Constant.POINT_YESTERDAY_COUNT, cache.getMapField(Constant.POINT_COUNT_KEY,
                    Constant.POINT_TODAY_COUNT, String.class));
            cache.putMapValue(Constant.POINT_COUNT_KEY, Constant.POINT_TODAY_COUNT, "0");
            return Result.newSuccess();
        } catch (Exception e) {
            LOGGER.error("重置点位数量异常", e);
            return Result.newFaild();
        }
    }

    @Override
    public Result<Map<String, String>> queryMax(QueryDto queryDto) {
        validateKV(queryDto);
        try {
            String deviceId = getDeviceIdByDeviceCode(queryDto.getDeviceId());
            if (StringUtils.isBlank(deviceId)) {
                LOGGER.warn("查询最大值，没有找到设备号【{}】的记录，查询参数：{}", queryDto.getDeviceId(), JSON.toJSONString(queryDto));
                return Result.newFaild("没有找到设备号【" + queryDto.getDeviceId() + "】的记录");
            }
            String[] factors = queryDto.getFactors();
            for (String factor : factors) {
                Map<String, String> maxValue = ktvDataDao.queryMax(queryDto.getPartyId(), deviceId,
                        queryDto.getStartDatetime(), queryDto.getEndDatetime(), factor);
                if (maxValue == null) {
                    LOGGER.warn("查询最大值，设备【{}】在时间段【{}-{}】内无数据，无法获取最大值", queryDto.getDeviceId(), queryDto.getStartDatetime(), queryDto.getEndDatetime());
                    return Result.newFaild("无数据，无法获取最大值");
                }


            }
            return Result.newSuccess();
        } catch (Throwable ee) {
            LOGGER.error(String.format("查询最大值数据出错, 参数：%s， 异常信息", JSON.toJSONString(queryDto)), ee);
            return Result.newFaild("查询数据异常");
        }
    }

    @Override
    public Result<Long> getRowCountByTimeRange(Long startDatetime, Long endDatetime) {
        return Result.newSuccess(ktvDataOfflineDao.getRowCountByTimeRange(startDatetime, endDatetime));
    }

    @Override
    public Result<List<Map<String, String>>> queryKv(QueryDto queryDto) {
        validateKV(queryDto);
        String deviceId = getDeviceIdByDeviceCode(queryDto.getDeviceId());
        if (StringUtils.isBlank(deviceId)) {
            LOGGER.warn("没有找到设备号【" + queryDto.getDeviceId() + "】的记录");
            return Result.newFaild("没有找到设备号【" + queryDto.getDeviceId() + "】的记录");
        }
        List<Map<String, String>> params = ktvDataDao.queryKv(queryDto.getPartyId(), deviceId,
                queryDto.getStartDatetime(), queryDto.getEndDatetime(), queryDto.getFactors());
        return Result.newSuccess(params);
    }

    @Override
    public Result<PageResultDto> queryKvPage(PageQueryDto queryDto) {
        try {
            validateKV(queryDto);
            if (queryDto.getPageSize() == null || queryDto.getPageSize() <= 0) {
                LOGGER.warn("分页参数错误，参数：{}", JSON.toJSONString(queryDto));
                return Result.newFaild("分页参数错误");
            }
            String deviceId = getDeviceIdByDeviceCode(queryDto.getDeviceId());
            if (StringUtils.isBlank(deviceId)) {
                LOGGER.warn("没有找到设备号【" + queryDto.getDeviceId() + "】的记录");
                return Result.newFaild("没有找到设备号【" + queryDto.getDeviceId() + "】的记录");
            }
            queryDto.setDeviceId(deviceId);
            if (ktvDataDao.queryKvPage(queryDto) == null) {
                LOGGER.warn("分页查询设备历史轨迹失败，参数：{}", queryDto.toString());
                return Result.newFaild("分页查询数据失败");
            }
            return Result.newSuccess(ktvDataDao.queryKvPage(queryDto));
        } catch (Exception e) {
            LOGGER.error("查询历史轨迹数据异常，参数：{}，错误信息：{}", queryDto.toString(), e.toString());
            return Result.newFaild("分页查询数据失败");
        }
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Result<List<Map<String, String>>> queryKvLatest(QueryDto queryDto) {
        validateKVLatest(queryDto);
        String deviceId = getFullDeviceCode(queryDto.getDeviceId());
        List<String> factors = Lists.newArrayList(queryDto.getFactors());
        List<Map<String, String>> resultList = Lists.newArrayList();
        try {
            Map<String, String> params = cache.getAll(deviceId, String.class);
            Map<String, String> map = Maps.newHashMap();
            for (String factor : factors) {
                String factorValue = params.get(factor);
                if (StringUtils.isBlank(factorValue)) {
                    continue;
                }
                String[] timeAndValue = factorValue.split("@");
                if (timeAndValue.length < 2) {
                    continue;
                }
                map.put(GpsMsgParam.ATTR_GPS_DATETIME, timeAndValue[0]);
                map.put(factor, timeAndValue[1]);
            }
            if (!CollectionUtils.isEmpty(map)) {
                resultList.add(map);
            }
            return Result.newSuccess(resultList);
        } catch (Exception e) {
            LOGGER.error(String.format("查询最新实时数据异常，参数：%s，异常信息", queryDto.toString()), e);
            return Result.newFaild("查询异常");
        }

    }


    @Override
    @Deprecated
    public Result<Map<String, List<Map<String, String>>>> queryKvCriteriaLatest(QueryListCriteriaDto queryListCriteriaDto) {
        validateKvCriteriaLatest(queryListCriteriaDto);
        Map<String, List<Map<String, String>>> paramsMap = Maps.newHashMap();
        List<String> iotDeviceIds = queryListCriteriaDto.getDeviceIds();
        for (String iotDeviceId : iotDeviceIds) {
            String deviceId = getDeviceIdByDeviceCode(iotDeviceId);
            if (StringUtils.isBlank(deviceId)) {
                LOGGER.warn("没有找到设备号【" + deviceId + "】的记录");
                return Result.newFaild("没有找到设备号【" + deviceId + "】的记录");
            }
            List<Map<String, String>> result = Lists.newArrayList();
            List<Map<String, String>> params = ktvDataDao.queryKvCriteriaLatest(
                    String.valueOf(queryListCriteriaDto.getPartyId()), deviceId, queryListCriteriaDto.getFactors(),
                    queryListCriteriaDto.getConditions());
            Map<String, String> param = Maps.newHashMap();
            if (params.size() > 1) {
                param = params.get(0);
                for (Map<String, String> map : params) {
                    if (Long.parseLong(map.get(GpsMsgParam.ATTR_GPS_DATETIME)) > Long.parseLong(param.get(GpsMsgParam.ATTR_GPS_DATETIME))) {
                        param = map;
                    }
                }
                result.add(param);
            } else {
                result = params;
            }
            paramsMap.put(iotDeviceId, result);
        }
        return Result.newSuccess(paramsMap);
    }

    @Override
    public Result<Map<String, String>> queryFirstLast(QueryDto queryDto, boolean isFirst) {
        validateKV(queryDto);
        String deviceId = getDeviceIdByDeviceCode(queryDto.getDeviceId());
        if (StringUtils.isBlank(deviceId)) {
            LOGGER.warn("没有找到设备号【" + deviceId + "】的记录");
            return Result.newFaild("没有找到设备号【" + deviceId + "】的记录");
        }
        Map<String, String> flMap = ktvDataDao.queryFirstLast(queryDto.getPartyId(), deviceId,
                queryDto.getStartDatetime(), queryDto.getEndDatetime(), queryDto.getFactors(), isFirst);

        return Result.newSuccess(flMap);
    }

    @Override
    public Result<List<Map<String, Object>>> queryFirst(QueryDto queryDto) {
        validateKV(queryDto);

        String deviceId = getDeviceIdByDeviceCode(queryDto.getDeviceId());
        if (StringUtils.isBlank(deviceId)) {
            LOGGER.warn("没有找到设备号【" + deviceId + "】的记录");
            return Result.newFaild("没有找到设备号【" + deviceId + "】的记录");
        }

        List<Map<String, Object>> list = Lists.newArrayList();
        String[] factors = queryDto.getFactors();
        for (String factor : factors) {
            Map<String, Object> maps = Maps.newHashMap();
            Map<String, String> firstValue = ktvDataDao.queryFirstLast(queryDto.getPartyId(), deviceId,
                    queryDto.getStartDatetime(), queryDto.getEndDatetime(), new String[]{factor}, false);
            if (firstValue == null) {
                continue;
            }

            maps.put(ColumnConstant.KTV_COL_FACTOR, factor);
            maps.put(ColumnConstant.KTV_COL_DATETIME, firstValue.get(GpsMsgParam.ATTR_GPS_DATETIME));
            maps.put(ColumnConstant.KTV_COL_VALUE, firstValue.get(factor));

            list.add(maps);
        }

        return Result.newSuccess(list);
    }

    @Override
    public Result<List<Map<String, Object>>> queryLast(QueryDto queryDto) {
        validateKV(queryDto);
        String deviceId = getDeviceIdByDeviceCode(queryDto.getDeviceId());
        if (StringUtils.isBlank(deviceId)) {
            LOGGER.warn("没有找到设备号【" + deviceId + "】的记录");
            return Result.newFaild("没有找到设备号【" + deviceId + "】的记录");
        }
        List<Map<String, Object>> list = Lists.newArrayList();
        String[] factors = queryDto.getFactors();
        for (String factor : factors) {
            Map<String, Object> maps = Maps.newHashMap();
            Map<String, String> firstValue = ktvDataDao.queryFirstLast(queryDto.getPartyId(), deviceId,
                    queryDto.getStartDatetime(), queryDto.getEndDatetime(), new String[]{factor}, true);
            if (firstValue == null) {
                continue;
            }

            maps.put(ColumnConstant.KTV_COL_FACTOR, factor);
            maps.put(ColumnConstant.KTV_COL_DATETIME, firstValue.get(GpsMsgParam.ATTR_GPS_DATETIME));
            maps.put(ColumnConstant.KTV_COL_VALUE, firstValue.get(factor));

            list.add(maps);
        }

        return Result.newSuccess(list);
    }

    /**
     * 校验输入
     *
     * @param queryDto
     * @throws IllegalArgumentException
     */
    private void validateKVLatest(QueryDto queryDto) throws IllegalArgumentException {
        if (queryDto == null) {
            LOGGER.warn("参数为空");
            throw new IllegalArgumentException("参数不能为空");
        }
        if (StringUtils.isBlank(queryDto.getPartyId())) {
            LOGGER.warn("会员id为空，参数：{}", JSON.toJSONString(queryDto));
            throw new IllegalArgumentException("会员编码不能为空");
        }

        //判断为空或者null
        if (StringUtils.isBlank(queryDto.getDeviceId())) {
            LOGGER.warn("设备编码为空，参数：{}", JSON.toJSONString(queryDto));
            throw new IllegalArgumentException("设备编码不能为空");
        }


        String[] factors = queryDto.getFactors();
        if (factors == null || factors.length <= 0) {
            LOGGER.warn("因子编码为空，参数：{}", JSON.toJSONString(queryDto));
            throw new IllegalArgumentException("因子代码不能为空");
        }
    }

    /**
     * 校验输入
     *
     * @param queryDto
     * @throws IllegalArgumentException
     */
    private void validateKVLast(BatchQueryDto queryDto) throws IllegalArgumentException {
        if (queryDto == null) {
            LOGGER.warn("参数为空");
            throw new IllegalArgumentException("参数不能为空");
        }
        if (StringUtils.isBlank(queryDto.getPartyId())) {
            LOGGER.warn("会员id为空，参数：{}", JSON.toJSONString(queryDto));
            throw new IllegalArgumentException("会员编码不能为空");
        }

        //判断为空或者null
        if (CollectionUtils.isEmpty(queryDto.getDeviceIds())) {
            LOGGER.warn("设备编码为空，参数：{}", JSON.toJSONString(queryDto));
            throw new IllegalArgumentException("设备编码不能为空");
        }


        String[] factors = queryDto.getFactors();
        if (factors == null || factors.length <= 0) {
            LOGGER.warn("因子编码为空，参数：{}", JSON.toJSONString(queryDto));
            throw new IllegalArgumentException("因子代码不能为空");
        }
    }

    private void validateKvCriteriaLatest(QueryListCriteriaDto queryListCriteriaDto) throws IllegalArgumentException {
        if (queryListCriteriaDto.getPartyId() == null) {
            LOGGER.warn("会员id为空，参数：{}", JSON.toJSONString(queryListCriteriaDto));
            throw new IllegalArgumentException("会员编码不能为空");
        }

        //判断为空或者null
        List<String> iotDeviceIds = queryListCriteriaDto.getDeviceIds();
        if (iotDeviceIds.isEmpty()) {
            LOGGER.warn("设备编码为空，参数：{}", JSON.toJSONString(queryListCriteriaDto));
            throw new IllegalArgumentException("设备编码不能为空");
        }


        String[] factors = queryListCriteriaDto.getFactors();
        if (factors == null || factors.length <= 0) {
            LOGGER.warn("因子编码为空，参数：{}", JSON.toJSONString(queryListCriteriaDto));
            throw new IllegalArgumentException("因子代码不能为空");
        }
    }

    /**
     * 校验输入
     *
     * @param queryDto
     * @throws IllegalArgumentException
     */
    private void validateKV(QueryDto queryDto) throws IllegalArgumentException {
        validateKVLatest(queryDto);

        //起始时间不能为空
        Long startDatetime = queryDto.getStartDatetime();
        if (startDatetime == null) {
            LOGGER.warn("查询起始时间为空，参数：{}", queryDto.toString());
            throw new IllegalArgumentException("查询起始时间不能为空");
        }

        //结束时间不能为空
        Long endDatetime = queryDto.getEndDatetime();
        if (endDatetime == null) {
            LOGGER.warn("查询结束时间为空，参数：{}", queryDto.toString());
            throw new IllegalArgumentException("查询结束时间不能为空");
        }

        //判断时间大小
        if (startDatetime > endDatetime) {
            LOGGER.warn("查询起始时间大于结束时间，参数：{}", queryDto.toString());
            throw new IllegalArgumentException("起始时间不能大于结束时间");
        }
    }

    @Override
    public void saveKtvData(GpsDeviceDto gpsDeviceDto) {

        //处理批量上传的情况
        String deviceId = gpsDeviceDto.getDeviceId();
        List<KvsDto> kvsDtos = gpsDeviceDto.getKvs();
        String deviceFull = getFullDeviceCode(deviceId);
        for (KvsDto kvsDto : kvsDtos) {
            saveData(kvsDto.getRealTime(), deviceFull, kvsDto.getFactorId(), kvsDto.getFactorValue());
        }
        String device = getDeviceIdByDeviceCode(deviceId);
        if (StringUtils.isBlank(device)) {
            LOGGER.warn("没有找到设备号【" + deviceId + "】的记录");
            throw new IllegalArgumentException("没有找到设备号【" + deviceId + "】的记录");
        }
        gpsDeviceDto.setDeviceId(device);
        ktvDataDao.saveKtvData(gpsDeviceDto);

    }

    @Override
    public Result<List<Map<String, String>>> batchQueryKvLatest(BatchQueryDto batchQueryDto) {
        List<String> deviceIds = batchQueryDto.getDeviceIds();
        List<Map<String, String>> result = Lists.newArrayList();
        for (String deviceId : deviceIds) {
            QueryDto queryDto = new QueryDto();
            queryDto.setDeviceId(deviceId);
            queryDto.setPartyId(batchQueryDto.getPartyId());
            queryDto.setFactors(batchQueryDto.getFactors());
            Result<List<Map<String, String>>> queryResult = queryKvLatest(queryDto);
            List<Map<String, String>> resultMap = queryResult.getRet();
            if (CollectionUtils.isEmpty(resultMap)) {
                continue;
            }
            Map<String, String> deviceMap = Maps.newHashMap();
            for (Map<String, String> map : resultMap) {
                deviceMap.putAll(map);
            }
            deviceMap.put("deviceId", deviceId);
            result.add(deviceMap);
        }
        return Result.newSuccess(result);
    }

    @Override
    public Result<List<Map<String, String>>> queryKvLast(QueryDto queryDto) {
        validateKVLatest(queryDto);
        String[] factors = queryDto.getFactors();
        String deviceId = getFullDeviceCode(queryDto.getDeviceId());
        List<String> factorList = Lists.newArrayList(factors);
        return getCacheList(deviceId, factorList);
    }

    @Override
    public Result<Map<String, List<Map<String, String>>>> batchQueryKvLast(BatchQueryDto batchQueryDto) {
        validateKVLast(batchQueryDto);
        List<String> deviceIds = batchQueryDto.getDeviceIds();
        Map<String, List<Map<String, String>>> resultMap = Maps.newHashMap();
        Map<String, String> deviceCodeList = getFullDeviceCodeBatch(deviceIds);
        List<String> factorList = Arrays.asList(batchQueryDto.getFactors());
        Set<Map.Entry<String, String>> entrySet = deviceCodeList.entrySet();
        for (Map.Entry<String, String> entry : entrySet) {
            Result<List<Map<String, String>>> result = getCacheList(entry.getValue(), factorList);
            List<Map<String, String>> maps = result.getRet();
            if (!CollectionUtils.isEmpty(maps)) {
                resultMap.put(entry.getKey(), maps);
            }
        }
        return Result.newSuccess(resultMap);
    }

    @Override
    public Result<List<IotKtvFactorReportTimeDto>> batchQueryDeviceLastReportTime(List<String> deviceIds) {
        List<IotKtvFactorReportTimeDto> list = Lists.newArrayList();
        String device = null;
        try {
            for (String deviceId : deviceIds) {
                device = deviceId;
                String deviceCode = getFullDeviceCode(deviceId);
                Map<String, String> map = cache.getAll(deviceCode, String.class);
                Set<String> set = map.keySet();
                for (String factor : set) {
                    IotKtvFactorReportTimeDto dto = new IotKtvFactorReportTimeDto();
                    String timeAndValue = map.get(factor);
                    String time = StringUtils.substringBefore(timeAndValue, "@");
                    if (StringUtils.isBlank(time)) {
                        continue;
                    }
                    dto.setDeviceId(deviceId);
                    dto.setFactorCode(factor);
                    dto.setReportTime(Long.parseLong(time));
                    list.add(dto);
                }
            }
        } catch (Exception e) {
            LOGGER.error(String.format("查询设备最新上报时间失败，设备编码：%s，message", device), e);
            return Result.newFaild("查询设备最新上报时间失败");
        }
        return Result.newSuccess(list);
    }

    @Override
    public Result<List<Map<String, Object>>> queryOilMileageList(List<String> deviceCodes, Long startDatetime, Long endDatetime) {
        Result result = checkParamStatic(deviceCodes, startDatetime, endDatetime);
        if (!Result.isSuccess(result)) {
            LOGGER.warn("查询油耗参数校验不通过，{}，参数：【deviceCodes={},startDatetime={},endDatetime={}】", result.toString(),
                    JSON.toJSONString(deviceCodes), startDatetime, endDatetime);
            return result;
        }
        List<Map<String, Object>> resultData = Lists.newArrayList();
        for (String deviceCode : deviceCodes) {
            Map<String, Object> resultMap = Maps.newHashMap();
            String deviceId = getDeviceIdByDeviceCode(deviceCode);
            List<Map<String, String>> deviceData = ktvDataDao.queryOilMileage(deviceId, startDatetime, endDatetime);
            for (Map<String, String> oilMileageMap : deviceData) {
                String rowKey = oilMileageMap.remove(ColumnConstant.ROW_KEY);
                String dateTime = rowKey.substring(rowKey.length() - 13);
                oilMileageMap.put(GpsMsgParam.ATTR_GPS_DATETIME, dateTime);
            }
            resultMap.put("dataList", deviceData);
            resultMap.put("deviceCode", deviceCode);
            resultData.add(resultMap);
        }
        return Result.newSuccess(resultData);
    }


    @Override
    public Result<List<Map<String, Object>>> queryStopTimeList(List<String> deviceCodes, Long startDatetime, Long endDatetime) {
        Result result = checkParamStatic(deviceCodes, startDatetime, endDatetime);
        if (!Result.isSuccess(result)) {
            LOGGER.warn("查询停车时间，参数校验不通过，{}, 参数：【deviceCodes={},startDatetime={},endDatetime={}】",
                    result.toString(), JSON.toJSONString(deviceCodes), startDatetime, endDatetime);
            return result;
        }

        List<Map<String, Object>> resultData = Lists.newArrayList();
        for (String deviceCode : deviceCodes) {
            Map<String, Object> resultMap = Maps.newHashMap();
            String deviceId = getDeviceIdByDeviceCode(deviceCode);
            List<Map<String, String>> deviceData = ktvDataDao.queryStopTime(deviceId, startDatetime, endDatetime);
            List<Map<String, String>> stopData = Lists.newArrayList();
            for (Map<String, String> stopTimeMap : deviceData) {
                stopTimeMap.remove(GpsMsgParam.ATTR_GPS_DATETIME);
                String rowKey = stopTimeMap.remove(ColumnConstant.ROW_KEY);
                String startTime = stopTimeMap.get("start_time");
                if (StringUtils.isBlank(startTime)) {
                    startTime = rowKey.substring(rowKey.length() - 13);
                }
                try {
                    Map<String, String> stopMap = Maps.newHashMap();
                    String timeEnd = stopTimeMap.get("time_end");
                    if (timeEnd == null) {
                        continue;
                    }
                    if (!isNumber(timeEnd)) {
                        timeEnd = String.valueOf(sdf.parse(timeEnd).getTime());
                    }
                    stopMap.put("endTime", timeEnd);
                    stopMap.put("startTime", startTime);
                    stopMap.put(ColumnConstant.POST_POSITION, stopTimeMap.get(ColumnConstant.POST_POSITION));
                    stopMap.put(ColumnConstant.POST_LONGITUDE, stopTimeMap.get(ColumnConstant.POST_LONGITUDE));
                    stopMap.put(ColumnConstant.POST_LATITUDE, stopTimeMap.get(ColumnConstant.POST_LATITUDE));
                    stopMap.put(ColumnConstant.POST_POST_TIME, stopTimeMap.get(ColumnConstant.POST_POST_TIME));
                    stopData.add(stopMap);
                } catch (ParseException e) {
                    LOGGER.error(String.format("处理停车时间异常，参数：【deviceCode=%s,startDatetime=%s,endDatetime=%s】,异常信息",
                            deviceCode, startDatetime, endDatetime), e);
                }
            }
            resultMap.put("dataList", stopData);
            resultMap.put("deviceCode", deviceCode);
            resultData.add(resultMap);
        }
        return Result.newSuccess(resultData);
    }

    @Override
    public Result<List<Map<String, String>>> queryKvByBusiness(QueryDto queryDto) {
        validateKV(queryDto);
        String bizCode = queryDto.getDeviceId();
        if (StringUtils.isBlank(bizCode)) {
            LOGGER.warn("根据业务编码查询因子数据，业务编码为空，参数：{}", queryDto.toString());
            return Result.newFaild("业务编码不能为空");
        }
        String deviceId = getDeviceIdByBizCode(bizCode);
        if (StringUtils.isBlank(deviceId)) {
            LOGGER.warn("没有找到业务编码【" + queryDto.getDeviceId() + "】的记录");
            return Result.newFaild("没有找到业务编码【" + queryDto.getDeviceId() + "】的记录");
        }
        queryDto.setPartyId("-100");
        List<Map<String, String>> params = ktvDataDao.queryKv(queryDto.getPartyId(), deviceId,
                queryDto.getStartDatetime(), queryDto.getEndDatetime(), queryDto.getFactors());
        return Result.newSuccess(params);
    }

    @Override
    public Result<List<Map<String, String>>> queryBusinessLast(QueryDto queryDto) {

        String[] factors = queryDto.getFactors();
        String bizCode = queryDto.getDeviceId();
        if (StringUtils.isBlank(bizCode)) {
            LOGGER.warn("根据业务编码查询因子最新数据，业务编码为空，参数：{}", queryDto.toString());
            return Result.newFaild("业务编码不能为空");
        }
        String deviceId = getFullDeviceCodeByBizCode(bizCode);
        if (StringUtils.isBlank(deviceId)) {
            LOGGER.warn("根据业务编码查询设备编码为空，业务编码：{}", bizCode);
            return Result.newFaild("请检查业务编码是否正确");
        }
        List<String> factorList = Lists.newArrayList(factors);
        return getCacheList(deviceId, factorList);
    }

    private Result<List<Map<String, String>>> getCacheList(String deviceId, List<String> factorList) {
        List<Map<String, String>> resultList = Lists.newArrayList();
        try {
            Map<String, String> map = cache.getAll(deviceId, String.class);
            Set<String> set = map.keySet();
            for (String factor : set) {
                if (factor.equals(GpsMsgParam.ATTR_GPS_DATETIME)) {
                    continue;
                }
                if (factorList.contains(factor)) {
                    String timeAndValue = map.get(factor);
                    if (StringUtils.isBlank(timeAndValue)) {
                        continue;
                    }
                    String[] timeValueArr = timeAndValue.split("@");
                    if (timeValueArr.length < 2) {
                        continue;
                    }
                    Map<String, String> resultMap = Maps.newHashMap();
                    resultMap.put(GpsMsgParam.ATTR_GPS_DATETIME, timeValueArr[0]);
                    resultMap.put(factor, timeValueArr[1]);
                    resultList.add(resultMap);
                }
            }
        } catch (Exception e) {
            LOGGER.error(String.format("查询因子最细数据失败, 设备编码：%s， message", deviceId), e);
            return Result.newFaild("查询因子最新数据失败");
        }
        return Result.newSuccess(resultList);
    }

    /**
     * 判断字符串是否为数字
     *
     * @param str
     * @return
     */
    private boolean isNumber(String str) {
        return PATTERN.matcher(str).matches();
    }

    private Result<?> checkParamStatic(List<String> deviceCodes, Long startDatetime, Long endDatetime) {
        if (CollectionUtils.isEmpty(deviceCodes)) {
            LOGGER.warn("参数校验不通过，设备编码为空");
            return Result.newFaild("设备编码不能为空！");
        }

        if (startDatetime == null || endDatetime == null || startDatetime > endDatetime) {
            LOGGER.warn("参数校验不通过，时间参数不合法，startDatetime={}，endDatetime={}", startDatetime, endDatetime);
            return Result.newFaild("时间参数不合法！");
        }
        return Result.newSuccess();
    }

    /**
     * 查询deviceId
     *
     * @param deviceCode
     * @return
     */
    private String getDeviceIdByDeviceCode(String deviceCode) {
        ResultDTO<String> resultDTO = dssApi.getDeviceIdByDeviceCode(deviceCode);
        if (!ResultDTO.MSG_SUCCESS.equals(resultDTO.getMsg())) {
            LOGGER.warn("通过deviceCode获取deviceId失败，message：{}", resultDTO.toString());
            return null;
        }
        return resultDTO.getData();
    }

    private String getDeviceIdByBizCode(String bizCode) {
        ResultDTO<String> resultDTO = dssApi.getDeviceIdByBizCode(bizCode);
        if (!ResultDTO.MSG_SUCCESS.equals(resultDTO.getMsg())) {
            LOGGER.warn("通过业务编码【{}】查询设备id失败，message：{}", bizCode, resultDTO.toString());
            return null;
        }
        if (StringUtils.isBlank(resultDTO.getData())) {
            LOGGER.warn("没有找到业务编码【{}】的记录", bizCode);
            return null;
        }
        return resultDTO.getData();
    }

    /**
     * 获取完整的deviceCode
     *
     * @param deviceCode
     * @return
     */
    private String getFullDeviceCode(String deviceCode) {
        IotDeviceDTO iotDeviceDTO = new IotDeviceDTO();
        iotDeviceDTO.setDeviceCode(deviceCode);
        ResultDTO<IotIovProtocolCodeQueryDto> protocolDto = dssApi.getProtocolByDeviceCode(deviceCode);
        if (!ResultDTO.MSG_SUCCESS.equals(protocolDto.getMsg())) {
            LOGGER.warn("查询设备协议编码错误，设备编码：{}，返回结果：{}", deviceCode, protocolDto.toString());
            return deviceCode;
        }
        IotIovProtocolCodeQueryDto protocolCodeQueryDto = protocolDto.getData();
        if (protocolCodeQueryDto == null) {
            LOGGER.warn("查询设备协议编码为空，设备编码：{}", deviceCode);
            return deviceCode;
        }
        String protocolCode = protocolCodeQueryDto.getProtocolCode();
        if (!deviceCode.startsWith(protocolCode)) {
            deviceCode = protocolCode + deviceCode;
        }
        return deviceCode;
    }


    /**
     * 获取完整的deviceCode
     *
     * @param deviceCodes
     * @return
     */
    private Map<String, String> getFullDeviceCodeBatch(List<String> deviceCodes) {
        Map<String, String> deviceMap = Maps.newHashMap();
        IotDeviceStatusBatchQueryDTO batchQueryDTO = new IotDeviceStatusBatchQueryDTO();
        batchQueryDTO.setDeviceCodes(deviceCodes);
        deviceCodes.forEach((deviceCode) -> deviceMap.put(deviceCode, deviceCode));
        ResultDTO<List<IotIovProtocolCodeQueryDto>> protocolDtoList = dssApi.getProtocolByDeviceCodes(batchQueryDTO);

        if (!ResultDTO.MSG_SUCCESS.equals(protocolDtoList.getMsg())) {
            LOGGER.warn("查询设备协议编码错误，设备编码：{}，返回结果：{}", deviceCodes, protocolDtoList.toString());
            return deviceMap;
        }
        List<IotIovProtocolCodeQueryDto> protocolCodeQueryDtos = protocolDtoList.getData();
        if (CollectionUtils.isEmpty(protocolCodeQueryDtos)) {
            LOGGER.warn("查询设备协议编码为空，设备编码：{}", deviceCodes);
            return deviceMap;
        }
        protocolCodeQueryDtos.forEach((protocolDto) -> {
                    String deviceCode = protocolDto.getDeviceCode();
                    String protocolCode = protocolDto.getProtocolCode();
                    if (!deviceCode.startsWith(protocolCode)) {
                        deviceMap.put(deviceCode, protocolCode + deviceCode);
                    }
                }
        );
        return deviceMap;
    }

    private String getFullDeviceCodeByBizCode(String bizCode) {
        ResultDTO<IotIovProtocolCodeQueryDto> protocolDto = dssApi.getProtocolByBizCode(bizCode);
        if (!ResultDTO.MSG_SUCCESS.equals(protocolDto.getMsg())) {
            LOGGER.warn("根据业务编码【{}】查询协议失败, 返回结果：{}", bizCode, protocolDto.toString());
            return null;
        }
        IotIovProtocolCodeQueryDto protocolCodeQueryDto = protocolDto.getData();
        if (protocolCodeQueryDto == null) {
            LOGGER.warn("根据业务编码【{}】查询协议为空", bizCode);
            return null;
        }
        return protocolCodeQueryDto.getProtocolCode() + protocolCodeQueryDto.getDeviceCode();
    }

}

package allthings.iot.ktv.dao.imp;

import allthings.iot.common.msg.DeviceDataMsg;
import allthings.iot.constant.gps.GpsMsgParam;
import allthings.iot.ktv.common.ColumnConstant;
import allthings.iot.ktv.common.Constant;
import allthings.iot.ktv.common.dto.ComparisonCriteriaDto;
import allthings.iot.ktv.common.dto.GpsDeviceDto;
import allthings.iot.ktv.common.dto.KvsDto;
import allthings.iot.ktv.common.dto.PageQueryDto;
import allthings.iot.ktv.common.dto.PageResultDto;
import allthings.iot.ktv.dao.IKtvDataDao;
import allthings.iot.util.hbase.api.HbaseTemplate;
import allthings.iot.util.hbase.api.RowMapper;
import allthings.iot.util.redis.ICentralCacheService;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Mutation;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.coprocessor.AggregationClient;
import org.apache.hadoop.hbase.client.coprocessor.DoubleColumnInterpreter;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.PageFilter;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author :  luhao
 * @FileName :  KtvDataDao
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
@Repository
public class KtvDataDao implements IKtvDataDao {
    @Autowired
    private HbaseTemplate hbaseTemplate;

    @Autowired
    private ICentralCacheService cache;

    private static final Logger LOGGER = LoggerFactory.getLogger(KtvDataDao.class);

    private static final long MONTH_TIME = 30 * 24 * 60 * 60 * 1000L;

//    @PostConstruct
//    public void init() throws IOException {
//        HBaseAdmin admin = (HBaseAdmin) hbaseTemplate.getConnection().getAdmin();
//        TableName tableName = TableName.valueOf(Constant.KTV_DATA_TABLE);
//        if (admin.tableExists(tableName)) {
//            LOGGER.info("table Exists!");
//        } else {
//            //默认只有一个列族
//            HTableDescriptor hBaseTable = new HTableDescriptor(tableName);
//            hBaseTable.addFamily(new HColumnDescriptor(Constant.KTV_COL_FAMILY));
//            admin.createTable(hBaseTable);
//            LOGGER.info("create table Success!");
//        }
//    }

    @Override
    public void saveKtvData(DeviceDataMsg deviceMsg) {
        saveKtvDataHistory(deviceMsg);
    }

    /**
     * 保存历史数据
     *
     * @param deviceMsg
     */
    private void saveKtvDataHistory(DeviceDataMsg deviceMsg) {
        //参数
        Map<String, Object> params = deviceMsg.getParams();

        //采集时间
        Object gpsDatetime = params.get(GpsMsgParam.ATTR_GPS_DATETIME);
        long dataTime;
        if (gpsDatetime == null) {
            dataTime = deviceMsg.getOccurTime();
        } else {
            dataTime = Long.parseLong(String.valueOf(gpsDatetime));
        }
        if (dataTime < 1509465600000L || dataTime > System.currentTimeMillis() + MONTH_TIME) {
            LOGGER.info("设备数据时间超出保存范围，数据：{}", JSON.toJSONString(deviceMsg));
            return;
        }
        int month = getMonth(dataTime);
        int year = getYear(dataTime);

        String deviceId = DigestUtils.md5Hex(deviceMsg.getSourceDeviceType() + deviceMsg.getSourceDeviceId());
        String rowKey = deviceId + dataTime;
        //列簇字节数组
        byte[] colFamilyBytes = Bytes.toBytes(Constant.KTV_COL_FAMILY);
        //设置
        Put put = new Put(Bytes.toBytes(rowKey), dataTime);

        Set<Map.Entry<String, Object>> entrySet = params.entrySet();
        Iterator<Map.Entry<String, Object>> iterator = entrySet.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();
            if (entry.getKey().equals(GpsMsgParam.ATTR_GPS_DATETIME)) {
                continue;
            }
            put.addColumn(colFamilyBytes, Bytes.toBytes(entry.getKey()),
                    Bytes.toBytes(String.valueOf(entry.getValue())));
        }

        List<Mutation> mutations = new ArrayList<>();
        mutations.add(put);

        hbaseTemplate.saveOrUpdates(getTableName(year, month, deviceId), mutations);
    }

    @Override
    public List<Map<String, String>> queryKv(String partyId, String deviceId, Long startDatetime, Long endDatetime,
                                             String[] factors) {
        byte[] colFamily = Bytes.toBytes(Constant.KTV_COL_FAMILY);

        List<Map<String, Long>> timeByMonth = getTimeByMonth(startDatetime, endDatetime);
        KVRowMapper kvRowMapper = new KVRowMapper();
        List<Map<String, String>> result = new ArrayList<>();
        Scan scan = new Scan();
        byte[] zeroBytes = new byte[]{0};
        for (String factor : factors) {
            scan.addColumn(colFamily, Bytes.toBytes(factor));
        }

        for (Map<String, Long> map : timeByMonth) {
            Long start = map.get("startTime");
            Long end = map.get("endTime");

            scan.setStartRow(Bytes.toBytes(deviceId + start));
            byte[] stopRowBytes = Bytes.toBytes(deviceId + end);

            scan.setStopRow(Bytes.add(stopRowBytes, zeroBytes));

            result.addAll(hbaseTemplate.find(getTableName(getYear(start), getMonth(start), deviceId), scan, kvRowMapper));
        }

        return result;
    }

    @Override
    public List<Map<String, String>> queryKvLatest(String partyId, String deviceId, String[] factors) {
        byte[] colFamily = Bytes.toBytes(Constant.KTV_COL_FAMILY);

        String endDatetime = String.valueOf(System.currentTimeMillis() + 5 * 60 * 1000);
        //这么写不是很好，但是没有好的办法，此时间是此系统未上线之前的时间。。。
        String startDatetime = "1509465600000";
        Filter filter = new PageFilter(1);

        Scan scan = new Scan();
        scan.setReversed(true);
        scan.setStartRow(Bytes.toBytes(deviceId + endDatetime));
        scan.setStopRow(Bytes.toBytes(deviceId + startDatetime));
        scan.setFilter(filter);

        for (String factor : factors) {
            scan.addColumn(colFamily, Bytes.toBytes(factor));
        }

        return hbaseTemplate.find(Constant.KTV_DATA_TABLE, scan, new KVRowMapper());
    }

    @Override
    public Map<String, String> queryKvLast(String partyId, String rowKey) {
        long dataTime = Long.valueOf(StringUtils.substring(rowKey, rowKey.length() - 14, rowKey.length() - 1));
        return hbaseTemplate.get(getTableName(getYear(dataTime), getMonth(dataTime), rowKey), rowKey, Constant.KTV_COL_FAMILY, new KVRowMapper());
    }

    @Override
    public List<Map<String, String>> queryKvCriteriaLatest(String partyId, String deviceId, String[] factors,
                                                           List<ComparisonCriteriaDto> comparisonCriteriaDtoList) {
        byte[] colFamily = Bytes.toBytes(Constant.KTV_COL_FAMILY);
        String endDatetime = String.valueOf(System.currentTimeMillis() + 5 * 60 * 1000);
        //这么写不是很好，但是没有好的办法，此时间是此系统未上线之前的时间。。。
        String startDatetime = "1509465600000";
        FilterList filterList = new FilterList();
        if (!CollectionUtils.isEmpty(comparisonCriteriaDtoList)) {
            for (ComparisonCriteriaDto criteriaDto : comparisonCriteriaDtoList) {
                if ("=".equals(criteriaDto.getComparison())) {
                    SingleColumnValueFilter valueEqualFilter = new SingleColumnValueFilter(colFamily, Bytes.toBytes(criteriaDto.getFactor()),
                            CompareFilter.CompareOp.EQUAL, Bytes.toBytes(String.valueOf(criteriaDto.getValue())));
                    filterList.addFilter(valueEqualFilter);
                }
            }
        }

        Filter pageFilter = new PageFilter(1);
        filterList.addFilter(pageFilter);

        Scan scan = new Scan();
        scan.setReversed(true);
        scan.setFilter(filterList);
        for (String factor : factors) {
            scan.addColumn(colFamily, Bytes.toBytes(factor));
        }

        List<Map<String, String>> resultList = Lists.newArrayList();

        List<Map<String, Long>> month = getTimeByMonth(Long.valueOf(startDatetime), Long.valueOf(endDatetime));
        for (Map<String, Long> map : month) {
            long startTime = map.get("startTime");
            long endTime = map.get("endTime");
            scan.setStartRow(Bytes.toBytes(deviceId + endTime));
            scan.setStopRow(Bytes.toBytes(deviceId + startTime));
            resultList.addAll(hbaseTemplate.find(Constant.KTV_DATA_TABLE, scan, new KVRowMapper()));
        }
        return resultList;

    }

    /**
     * 油耗mapper
     */
    private class KVRowMapper implements RowMapper<Map<String, String>> {
        @Override
        public Map<String, String> mapRow(Result result, int i) throws Exception {
            List<Cell> list = result.listCells();
            Map<String, String> params = Maps.newHashMap();
            if (CollectionUtils.isEmpty(list)) {
                return params;
            }
            long timestamp = 0;
            for (Cell cell : list) {
                String qualifier = Bytes.toString(CellUtil.cloneQualifier(cell));
                timestamp = cell.getTimestamp();
                params.put(qualifier, Bytes.toString(CellUtil.cloneValue(cell)));
            }

            params.put(GpsMsgParam.ATTR_GPS_DATETIME, String.valueOf(timestamp));
            return params;
        }
    }

    @Override
    public Map<String, String> queryMax(String partyId, String deviceId, Long startDatetime, Long endDatetime,
                                        String factor) throws Throwable {
        AggregationClient client = new AggregationClient(hbaseTemplate.getConfiguration());
        byte[] colFamily = Bytes.toBytes(Constant.KTV_COL_FAMILY);

        List<Filter> filters = Lists.newArrayList();
        //设备标识参数
        SingleColumnValueFilter filterDeviceId = new SingleColumnValueFilter(colFamily, Bytes.toBytes(ColumnConstant
                .KTV_COL_DEVICE_ID), CompareFilter.CompareOp.EQUAL, Bytes
                .toBytes(deviceId));
        filterDeviceId.setFilterIfMissing(true);

        filters.add(filterDeviceId);
        //partyId参数
        SingleColumnValueFilter filterPartyId = new SingleColumnValueFilter(colFamily, Bytes.toBytes(ColumnConstant
                .KTV_COL_PARTY_ID),
                CompareFilter.CompareOp.EQUAL, Bytes
                .toBytes(partyId));
        filterPartyId.setFilterIfMissing(true);
        filters.add(filterPartyId);

        FilterList filterList = new FilterList(filters);

        DoubleColumnInterpreter doubleColumnInterpreter = new DoubleColumnInterpreter();
        Scan scan = new Scan();
        scan.addColumn(colFamily, Bytes.toBytes(factor));
        scan.setFilter(filterList);
        List<Map<String, Long>> month = getTimeByMonth(startDatetime, endDatetime);
        // 设置一个最小初始值
        double maxValue = -99999999999999.99;
        for (Map<String, Long> map : month) {
            Long start = map.get("startTime");
            Long end = map.get("endTime");
            //增加时间范围查询
            scan.setColumnFamilyTimeRange(colFamily, startDatetime, endDatetime);
            TableName tableName = TableName.valueOf(getTableName(getYear(start), getMonth(end), deviceId));
            double value = client.max(tableName, doubleColumnInterpreter, scan);
            if (value > maxValue) {
                maxValue = value;
            }
        }

        return null;
    }

    @Override
    public Map<String, String> queryFirstLast(String partyId, String deviceId, Long startDatetime, Long endDatetime,
                                              String[] factors, boolean isFirst) {
        byte[] colFamily = Bytes.toBytes(Constant.KTV_COL_FAMILY);

        Filter filter = new PageFilter(1);
        KVRowMapper kvRowMapper = new KVRowMapper();
        Scan scan = new Scan();
        scan.setReversed(isFirst);
        scan.setFilter(filter);
        for (String factor : factors) {
            scan.addColumn(colFamily, Bytes.toBytes(factor));
        }

        List<Map<String, String>> rowList = new ArrayList<>();
        List<Map<String, Long>> timeByMonth = getTimeByMonth(startDatetime, endDatetime);
        for (Map<String, Long> map : timeByMonth) {
            Long start = map.get("startTime");
            Long end = map.get("endTime");
            if (!isFirst) {
                scan.setStartRow(Bytes.toBytes(deviceId + start));
                scan.setStopRow(Bytes.toBytes(deviceId + end));
            } else {
                scan.setStartRow(Bytes.toBytes(deviceId + end));
                scan.setStopRow(Bytes.toBytes(deviceId + start));
            }
            rowList.addAll(hbaseTemplate.find(getTableName(getYear(start), getMonth(start), deviceId), scan, kvRowMapper));
        }

        if (CollectionUtils.isEmpty(rowList)) {
            return null;
        }

        return rowList.get(0);
    }

    @Override
    public void saveKtvData(GpsDeviceDto gpsDeviceDto) {

        String deviceId = gpsDeviceDto.getDeviceId();
        //列簇字节数组
        byte[] colFamilyBytes = Bytes.toBytes(Constant.KTV_COL_FAMILY);

        List<KvsDto> list = gpsDeviceDto.getKvs();
        for (KvsDto dto : list) {
            //设置
            List<Mutation> mutations = new ArrayList<>();
            long dataTime = dto.getRealTime();
            if (dataTime < 1509465600000L || dataTime > System.currentTimeMillis() + MONTH_TIME) {
                LOGGER.info("设备数据时间超出保存范围，数据：{}", JSON.toJSONString(dto));
                continue;
            }
            String rowKey = deviceId + dataTime;

            Put put = new Put(Bytes.toBytes(rowKey), dataTime);
            put.addColumn(colFamilyBytes, Bytes.toBytes(dto.getFactorId()),
                    Bytes.toBytes(String.valueOf(dto.getFactorValue())));
            mutations.add(put);
            hbaseTemplate.saveOrUpdates(getTableName(getYear(dataTime), getMonth(dataTime), deviceId), mutations);
        }
    }

    @Override
    public List<Map<String, String>> queryStopTime(String deviceId, Long startDatetime, Long endDatetime) {
        Scan scan = new Scan();
        scan.setStartRow(Bytes.toBytes(deviceId + startDatetime));
        scan.setStopRow(Bytes.toBytes(deviceId + endDatetime));
        return hbaseTemplate.find(Constant.KTV_STOP_TIME_TABLE, scan, new KVRowMapperKey());
    }

    @Override
    public List<Map<String, String>> queryOilMileage(String deviceId, Long startDatetime, Long endDatetime) {
        Scan scan = new Scan();
        scan.setStartRow(Bytes.toBytes(deviceId + startDatetime));
        scan.setStopRow(Bytes.toBytes(deviceId + endDatetime));
        return hbaseTemplate.find(Constant.KTV_OIL_MILEAGE_TABLE, scan, new KVRowMapperKey());
    }

    @Override
    public PageResultDto queryKvPage(PageQueryDto queryDto) {
        byte[] colFamily = Bytes.toBytes(Constant.KTV_COL_FAMILY);

        Long startTime = queryDto.getEndRowTime();
        if (startTime == null) {
            startTime = queryDto.getStartDatetime();
        }

        List<Map<String, Long>> timeByMonth = getTimeByMonth(startTime, queryDto.getEndDatetime());
        List<Map<String, String>> queryResult = Lists.newArrayList();
        Scan scan = new Scan();
        byte[] zeroBytes = new byte[]{0};
        String[] factors = queryDto.getFactors();
        for (String factor : factors) {
            scan.addColumn(colFamily, Bytes.toBytes(factor));
        }
        int pageSize = queryDto.getPageSize();

        String deviceId = queryDto.getDeviceId();

        for (Map<String, Long> map : timeByMonth) {
            if (queryResult.size() > pageSize) {
                break;
            }
            Long start = map.get("startTime");
            Long end = map.get("endTime");
            Filter pageFilter = new PageFilter(pageSize + 1 - queryResult.size());
            scan.setFilter(pageFilter);

            scan.setStartRow(Bytes.toBytes(deviceId + start + 0));
            byte[] stopRowBytes = Bytes.toBytes(deviceId + end);

            scan.setStopRow(Bytes.add(stopRowBytes, zeroBytes));

            queryResult.addAll(hbaseTemplate.find(getTableName(getYear(start), getMonth(start), deviceId), scan, new KVRowMapper()));
        }
        boolean pageDown = queryResult.size() > pageSize;
        if (pageDown) {
            queryResult.remove(queryResult.size() - 1);
        }

        return new PageResultDto(queryResult, pageDown);

    }

    /**
     * 格式化返回类型，加入rowKey
     */
    private class KVRowMapperKey implements RowMapper<Map<String, String>> {
        @Override
        public Map<String, String> mapRow(Result result, int i) throws Exception {
            List<Cell> list = result.listCells();
            Map<String, String> params = Maps.newHashMap();
            if (CollectionUtils.isEmpty(list)) {
                return params;
            }
            long timestamp = 0;
            for (Cell cell : list) {
                byte[] rowKeyBytes = CellUtil.cloneRow(cell);
                String qualifier = Bytes.toString(CellUtil.cloneQualifier(cell));
                timestamp = cell.getTimestamp();
                params.put(qualifier, Bytes.toString(CellUtil.cloneValue(cell)));
                params.put(ColumnConstant.ROW_KEY, Bytes.toString(rowKeyBytes));
            }

            params.put(GpsMsgParam.ATTR_GPS_DATETIME, String.valueOf(timestamp));
            return params;
        }
    }

    /**
     * 按月拆分时间段
     *
     * @param startDate
     * @param endDate
     * @return
     */
    private List<Map<String, Long>> getTimeByMonth(Long startDate, Long endDate) {
        List<Map<String, Long>> list = Lists.newArrayList();
        Date start = new Date(startDate);
        Date end = new Date(endDate);
        Calendar startTmp = Calendar.getInstance();
        // 设置日期起始时间
        startTmp.setTime(start);
        Calendar cale = Calendar.getInstance();
        Calendar c = Calendar.getInstance();
        c.setTime(end);

        Calendar c1 = Calendar.getInstance();
        c1.setTime(start);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(end);

        // 判断是否到结束日期
        while (startTmp.getTime().before(end) || startTmp.getTime().equals(end)) {
            Map<String, Long> keyValueForDate = new HashMap<>(2);
            cale.setTime(startTmp.getTime());
            if (startTmp.getTime().equals(start)) {
                cale.set(Calendar.DAY_OF_MONTH, startTmp.getActualMaximum(Calendar.DAY_OF_MONTH));
                setLastSecond(cale);
                keyValueForDate.put("startTime", start.getTime());
                if (end.after(cale.getTime())) {
                    keyValueForDate.put("endTime", cale.getTimeInMillis());
                    startTmp.setTime(new Date(cale.getTimeInMillis()));
                } else {
                    keyValueForDate.put("endTime", end.getTime());
                    startTmp.setTime(end);
                }
            } else if (startTmp.get(Calendar.MONTH) + 1 == getMonth(end) && startTmp.get(Calendar.YEAR) == c.get(Calendar.YEAR)) {
                //取第一天
                cale.set(Calendar.DAY_OF_MONTH, 1);
                setFirstSecond(cale);
                keyValueForDate.put("startTime", cale.getTimeInMillis());
                keyValueForDate.put("endTime", end.getTime());
                startTmp.setTime(end);
            } else {
                //取第一天
                cale.set(Calendar.DAY_OF_MONTH, 1);
                setFirstSecond(cale);
                keyValueForDate.put("startTime", cale.getTimeInMillis());

                cale.set(Calendar.DAY_OF_MONTH, startTmp.getActualMaximum(Calendar.DAY_OF_MONTH));
                setLastSecond(cale);
                keyValueForDate.put("endTime", cale.getTimeInMillis());
                startTmp.setTime(new Date(cale.getTimeInMillis()));

            }
            list.add(keyValueForDate);
            // 进行当前日期月份加1
            startTmp.add(Calendar.MILLISECOND, 1);

        }
        return list;
    }

    private void setFirstSecond(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    private void setLastSecond(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
    }

    private int getMonth(Long time) {
        Date d = new Date(time);
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        return c.get(Calendar.MONTH) + 1;
    }

    private int getMonth(Date time) {
        Calendar c = Calendar.getInstance();
        c.setTime(time);
        return c.get(Calendar.MONTH) + 1;
    }

    private int getYear(Long time) {
        Date d = new Date(time);
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        return c.get(Calendar.YEAR);
    }

    private int getYear(Date time) {
        Calendar c = Calendar.getInstance();
        c.setTime(time);
        return c.get(Calendar.YEAR);
    }

    private String getTableName(int year, int month, String deviceId) {
        String monthStr;
        if (month > 9) {
            monthStr = String.valueOf(month);
        } else {
            monthStr = "0" + month;
        }
        String tableName = Constant.KTV_DATA_TABLE + year + monthStr;
//        int hash = Math.abs(deviceId.hashCode()) % 10;
//        tableName += hash;
        return tableName;
    }
}

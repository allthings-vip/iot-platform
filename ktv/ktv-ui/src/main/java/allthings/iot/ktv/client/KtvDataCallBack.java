package allthings.iot.ktv.client;

import allthings.iot.common.dto.Result;
import allthings.iot.common.msg.DeviceDataMsg;
import allthings.iot.ktv.client.constant.Constant;
import allthings.iot.ktv.common.dto.BatchQueryDto;
import allthings.iot.ktv.common.dto.GpsDeviceDto;
import allthings.iot.ktv.common.dto.IotKtvFactorReportTimeDto;
import allthings.iot.ktv.common.dto.PageQueryDto;
import allthings.iot.ktv.common.dto.PageResultDto;
import allthings.iot.ktv.common.dto.QueryDto;
import allthings.iot.ktv.common.dto.QueryListCriteriaDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author tyf
 * @date 2019/05/09 18:45:28
 */
@Component
public class KtvDataCallBack implements KtvDataClient {
    @Override
    public Result<List<Map<String, String>>> queryKv(QueryDto queryDto) {
        return Constant.CALLBACK_RESULT;
    }

    @Override
    public Result<PageResultDto> queryKvPage(PageQueryDto queryDto) {
        return Constant.CALLBACK_RESULT;
    }

    @Override
    public Result<List<Map<String, String>>> queryKvLatest(QueryDto queryDto) {
        return Constant.CALLBACK_RESULT;
    }

    @Override
    public Result<Map<String, String>> queryFirstLast(QueryDto queryDto, boolean isFirst) {
        return Constant.CALLBACK_RESULT;
    }

    @Override
    public Result<List<Map<String, Object>>> queryFirst(QueryDto queryDto) {
        return Constant.CALLBACK_RESULT;
    }

    @Override
    public Result<List<Map<String, Object>>> queryLast(QueryDto queryDto) {
        return Constant.CALLBACK_RESULT;
    }

    @Override
    public Result<Map<String, List<Map<String, String>>>> queryKvCriteriaLatest(QueryListCriteriaDto queryListCriteriaDto) {
        return Constant.CALLBACK_RESULT;
    }

    @Override
    public Result<Map<String, String>> queryMax(QueryDto queryDto) {
        return Constant.CALLBACK_RESULT;
    }

    @Override
    public Result<Long> getRowCountByTimeRange(Long startDatetime, Long endDatetime) {
        return Constant.CALLBACK_RESULT;
    }

    @Override
    public Result<Map<String, String>> getPointCount() {
        return Constant.CALLBACK_RESULT;
    }

    @Override
    public Result<?> resetPointCount() {
        return Constant.CALLBACK_RESULT;
    }

    @Override
    public void saveKtvData(GpsDeviceDto gpsDeviceDto) {

    }

    @Override
    public Result<List<Map<String, String>>> batchQueryKvLatest(BatchQueryDto batchQueryDto) {
        return Constant.CALLBACK_RESULT;
    }

    @Override
    public void saveOrUpdateReportTime(DeviceDataMsg dataMsg) {

    }

    @Override
    public void handleKtvData(DeviceDataMsg dataMsg) {

    }

    @Override
    public void handleKtvAddress(DeviceDataMsg dataMsg) {

    }

    @Override
    public Result<List<Map<String, String>>> queryKvLast(QueryDto queryDto) {
        return Constant.CALLBACK_RESULT;
    }

    @Override
    public Result<Map<String, List<Map<String, String>>>> batchQueryKvLast(BatchQueryDto batchQueryDto) {
        return Constant.CALLBACK_RESULT;
    }

    @Override
    public Result<List<IotKtvFactorReportTimeDto>> batchQueryDeviceLastReportTime(String[] deviceIds) {
        return Constant.CALLBACK_RESULT;
    }

    @Override
    public Result<List<Map<String, Object>>> queryOilMileageList(String[] deviceCodes, Long startDatetime, Long endDatetime) {
        return Constant.CALLBACK_RESULT;
    }

    @Override
    public Result<List<Map<String, Object>>> queryStopTimeList(String[] deviceCodes, Long startDatetime, Long endDatetime) {
        return Constant.CALLBACK_RESULT;
    }

    @Override
    public Result<List<Map<String, String>>> queryKvByBusiness(QueryDto queryDto) {
        return Constant.CALLBACK_RESULT;
    }

    @Override
    public Result<List<Map<String, String>>> queryBusinessLast(QueryDto queryDto) {
        return Constant.CALLBACK_RESULT;
    }
}

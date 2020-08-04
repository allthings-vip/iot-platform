package allthings.iot.ktv.controller;

import allthings.iot.common.dto.Result;
import allthings.iot.common.msg.DeviceDataMsg;
import allthings.iot.ktv.api.IKtvDataService;
import allthings.iot.ktv.common.dto.BatchQueryDto;
import allthings.iot.ktv.common.dto.GpsDeviceDto;
import allthings.iot.ktv.common.dto.IotKtvFactorReportTimeDto;
import allthings.iot.ktv.common.dto.PageQueryDto;
import allthings.iot.ktv.common.dto.PageResultDto;
import allthings.iot.ktv.common.dto.QueryDto;
import allthings.iot.ktv.common.dto.QueryListCriteriaDto;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


/**
 * @author tyf
 * @date 2019/05/09 18:18:47
 */
@RestController
public class KtvDataController {

    @Autowired
    private IKtvDataService ktvDataService;


    /**
     * 查询因子值
     *
     * @param queryDto
     * @return
     */
    @PostMapping("/ktv/data/queryKv")
    public Result<List<Map<String, String>>> queryKv(@RequestBody QueryDto queryDto) {
        return ktvDataService.queryKv(queryDto);
    }

    /**
     * 查询最新因子值
     *
     * @param queryDto
     * @return
     */
    @PostMapping("/ktv/data/queryKvLatest")
    public Result<List<Map<String, String>>> queryKvLatest(@RequestBody QueryDto queryDto) {
        return ktvDataService.queryKvLatest(queryDto);
    }

    /**
     * 查询第一条或者最后一条数据
     *
     * @param queryDto
     * @param isFirst
     * @return
     */
    @PostMapping("/ktv/data/queryFirstLast")
    Result<Map<String, String>> queryFirstLast(@RequestBody QueryDto queryDto, @RequestParam("isFirst") boolean isFirst) {
        return ktvDataService.queryFirstLast(queryDto, isFirst);
    }

    /**
     * 查询第一条数据
     *
     * @param queryDto
     * @return
     */
    @PostMapping("/ktv/data/queryFirst")
    Result<List<Map<String, Object>>> queryFirst(@RequestBody QueryDto queryDto) {
        return ktvDataService.queryFirst(queryDto);
    }

    /**
     * 查询最后一条数据
     *
     * @param queryDto
     * @return
     */
    @PostMapping("/ktv/data/queryLast")
    Result<List<Map<String, Object>>> queryLast(@RequestBody QueryDto queryDto) {
        return ktvDataService.queryLast(queryDto);
    }

    /**
     * 根据条件查询最后一条符合的数据
     *
     * @param queryListCriteriaDto
     * @return
     */
    @PostMapping("/ktv/data/queryKvCriteriaLatest")
    @Deprecated
    Result<Map<String, List<Map<String, String>>>> queryKvCriteriaLatest(@RequestBody QueryListCriteriaDto queryListCriteriaDto) {
        return ktvDataService.queryKvCriteriaLatest(queryListCriteriaDto);
    }

    /**
     * 查询某个设备的因子在指定时间范围内的最大值
     *
     * @param queryDto
     * @return
     */
    @PostMapping("/ktv/data/queryMax")
    Result<Map<String, String>> queryMax(@RequestBody QueryDto queryDto) {
        return ktvDataService.queryMax(queryDto);
    }

    /**
     * 获取时间段内的行数
     *
     * @param startDatetime
     * @param endDatetime
     * @return
     */
    @GetMapping("/ktv/data/getRowCountByTimeRange")
    public Result<Long> getRowCountByTimeRange(@RequestParam("startDatetime") Long startDatetime,
                                               @RequestParam("endDatetime") Long endDatetime) {
        return ktvDataService.getRowCountByTimeRange(startDatetime, endDatetime);
    }

    /**
     * 获取点位数量
     *
     * @return
     */
    @GetMapping("/ktv/data/getPointCount")
    public Result<Map<String, String>> getPointCount() {
        return ktvDataService.getPointCount();
    }

    /**
     * 重置点位数量
     *
     * @return
     */
    @GetMapping("/ktv/data/resetPointCount")
    public Result<?> resetPointCount() {
        return ktvDataService.resetPointCount();
    }

    /**
     * 保存因子数据
     *
     * @param gpsDeviceDto
     * @return
     */
    @PostMapping("/ktv/data/saveKtvData")
    public void saveKtvData(@RequestBody GpsDeviceDto gpsDeviceDto) {
        ktvDataService.saveKtvData(gpsDeviceDto);
    }

    /**
     * 批量查询最后一条数据
     *
     * @param batchQueryDto
     * @return
     */
    @PostMapping("/ktv/data/batchQueryKvLatest")
    public Result<List<Map<String, String>>> batchQueryKvLatest(@RequestBody BatchQueryDto batchQueryDto) {
        return ktvDataService.batchQueryKvLatest(batchQueryDto);
    }

    /**
     * 保存因子最新上报时间
     *
     * @param dataMsg
     */
    @PostMapping("/ktv/data/saveOrUpdateReportTime")
    public void saveOrUpdateReportTime(@RequestBody DeviceDataMsg dataMsg) {
        ktvDataService.saveOrUpdateReportTime(dataMsg);
    }

    /**
     * 保存ktv数据
     *
     * @param dataMsg
     */
    @PostMapping("/ktv/data/handleKtvData")
    public void handleKtvData(@RequestBody DeviceDataMsg dataMsg) {
        ktvDataService.handleKtvData(dataMsg);
    }

    /**
     * 查询最新因子值
     *
     * @param queryDto
     * @return
     */
    @PostMapping("/ktv/data/queryKvLast")
    public Result<List<Map<String, String>>> queryKvLast(@RequestBody QueryDto queryDto) {
        return ktvDataService.queryKvLast(queryDto);
    }

    /**
     * 批量查询设备最新因子数据
     *
     * @param batchQueryDto
     * @return
     */
    @PostMapping("/ktv/data/batchQueryKvLast")
    public Result<Map<String, List<Map<String, String>>>> batchQueryKvLast(@RequestBody BatchQueryDto batchQueryDto) {
        return ktvDataService.batchQueryKvLast(batchQueryDto);
    }

    /**
     * 批量查询设备最后上传因子数据时间
     *
     * @param deviceIds
     * @return
     */
    @GetMapping("/ktv/data/batchQueryDeviceLastReportTime")
    public Result<List<IotKtvFactorReportTimeDto>> batchQueryDeviceLastReportTime(
            @RequestParam("deviceIds") String[] deviceIds) {
        return ktvDataService.batchQueryDeviceLastReportTime(Lists.newArrayList(deviceIds));
    }

    /**
     * 查询里程油耗列表
     *
     * @param deviceCodes
     * @param startDatetime
     * @param endDatetime
     * @return
     */
    @GetMapping("/ktv/data/queryOilMileageList")
    public Result<List<Map<String, Object>>> queryOilMileageList(@RequestParam("deviceCodes") String[] deviceCodes,
                                                                 @RequestParam("startDatetime") Long startDatetime,
                                                                 @RequestParam("endDatetime") Long endDatetime) {
        return ktvDataService.queryOilMileageList(Lists.newArrayList(deviceCodes), startDatetime, endDatetime);
    }

    /**
     * 查询停车时长列表
     *
     * @param deviceCodes
     * @param startDatetime
     * @param endDatetime
     * @return
     */
    @GetMapping("/ktv/data/queryStopTimeList")
    public Result<List<Map<String, Object>>> queryStopTimeList(@RequestParam("deviceCodes") String[] deviceCodes,
                                                               @RequestParam("startDatetime") Long startDatetime,
                                                               @RequestParam("endDatetime") Long endDatetime) {
        return ktvDataService.queryStopTimeList(Lists.newArrayList(deviceCodes), startDatetime, endDatetime);
    }

    /**
     * 根据业务编码查询车辆轨迹
     *
     * @param queryDto
     * @return
     */
    @PostMapping("/ktv/data/queryKvByBusiness")
    public Result<List<Map<String, String>>> queryKvByBusiness(@RequestBody QueryDto queryDto) {
        return ktvDataService.queryKvByBusiness(queryDto);
    }

    /**
     * 根据业务编码查询车辆最新数据
     *
     * @param queryDto
     * @return
     */
    @PostMapping("/ktv/data/queryBusinessLast")
    public Result<List<Map<String, String>>> queryBusinessLast(@RequestBody QueryDto queryDto) {
        return ktvDataService.queryBusinessLast(queryDto);
    }

    @PostMapping("/ktv/data/queryKvPage")
    public Result<PageResultDto> queryKvPage(@RequestBody PageQueryDto queryDto) {
        return ktvDataService.queryKvPage(queryDto);
    }

    /**
     * 健康检查接口
     *
     * @return
     */
    @GetMapping("/ktvDataService/health/check")
    public Result<?> healthCheck() {
        return Result.newSuccess();
    }
}

package allthings.iot.ktv.client;

import allthings.iot.common.dto.Result;
import allthings.iot.common.msg.DeviceDataMsg;
import allthings.iot.ktv.common.dto.BatchQueryDto;
import allthings.iot.ktv.common.dto.GpsDeviceDto;
import allthings.iot.ktv.common.dto.IotKtvFactorReportTimeDto;
import allthings.iot.ktv.common.dto.PageQueryDto;
import allthings.iot.ktv.common.dto.PageResultDto;
import allthings.iot.ktv.common.dto.QueryDto;
import allthings.iot.ktv.common.dto.QueryListCriteriaDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @author tyf
 * @date 2019/05/09 18:44:13
 */
@FeignClient(name = "ktv-service", fallback = KtvDataCallBack.class)
public interface KtvDataClient {
    /**
     * 查询因子值
     *
     * @param queryDto
     * @return
     */
    @PostMapping("/ktv/data/queryKv")
    Result<List<Map<String, String>>> queryKv(@RequestBody QueryDto queryDto);

    /**
     * 查询最新因子值
     *
     * @param queryDto
     * @return
     */
    @PostMapping("/ktv/data/queryKvLatest")
    Result<List<Map<String, String>>> queryKvLatest(@RequestBody QueryDto queryDto);

    /**
     * 查询最大第一条或者最后一条数据
     *
     * @param queryDto
     * @param isFirst
     * @return
     */
    @PostMapping("/ktv/data/queryFirstLast")
    Result<Map<String, String>> queryFirstLast(@RequestBody QueryDto queryDto, @RequestParam("isFirst") boolean isFirst);

    /**
     * 查询第一条数据
     *
     * @param queryDto
     * @return
     */
    @PostMapping("/ktv/data/queryFirst")
    Result<List<Map<String, Object>>> queryFirst(@RequestBody QueryDto queryDto);

    /**
     * 查询最后一条数据
     *
     * @param queryDto
     * @return
     */
    @PostMapping("/ktv/data/queryLast")
    Result<List<Map<String, Object>>> queryLast(@RequestBody QueryDto queryDto);

    /**
     * 根据条件查询最后一条符合的数据
     *
     * @param queryListCriteriaDto
     * @return
     */
    @PostMapping("/ktv/data/queryKvCriteriaLatest")
    @Deprecated
    Result<Map<String, List<Map<String, String>>>> queryKvCriteriaLatest(@RequestBody QueryListCriteriaDto queryListCriteriaDto);

    /**
     * 查询某个设备的因子在指定时间范围内的最大值
     *
     * @param queryDto
     * @return
     */
    @PostMapping("/ktv/data/queryMax")
    Result<Map<String, String>> queryMax(@RequestBody QueryDto queryDto);

    /**
     * 获取时间段内的行数
     *
     * @param startDatetime
     * @param endDatetime
     * @return
     */
    @GetMapping("/ktv/data/getRowCountByTimeRange")
    Result<Long> getRowCountByTimeRange(@RequestParam("startDatetime") Long startDatetime,
                                        @RequestParam("endDatetime") Long endDatetime);

    /**
     * 获取点位数量
     *
     * @return
     */
    @GetMapping("/ktv/data/getPointCount")
    Result<Map<String, String>> getPointCount();

    /**
     * 重置点位数量
     *
     * @return
     */
    @GetMapping("/ktv/data/resetPointCount")
    Result<?> resetPointCount();

    /**
     * 保存因子数据
     *
     * @param gpsDeviceDto
     * @return
     */
    @PostMapping("/ktv/data/saveKtvData")
    void saveKtvData(@RequestBody GpsDeviceDto gpsDeviceDto);

    /**
     * 批量查询最后一条数据
     *
     * @param batchQueryDto
     * @return
     */
    @PostMapping("/ktv/data/batchQueryKvLatest")
    Result<List<Map<String, String>>> batchQueryKvLatest(@RequestBody BatchQueryDto batchQueryDto);


    /**
     * 保存因子最新上报时间
     *
     * @param dataMsg
     */
    @PostMapping("/ktv/data/saveOrUpdateReportTime")
    void saveOrUpdateReportTime(@RequestBody DeviceDataMsg dataMsg);

    /**
     * 保存ktv数据
     *
     * @param dataMsg
     */
    @PostMapping("/ktv/data/handleKtvData")
    void handleKtvData(@RequestBody DeviceDataMsg dataMsg);

    /**
     * 保存地址
     *
     * @param dataMsg
     */
    @PostMapping("/ktv/data/handleKtvAddress")
    void handleKtvAddress(@RequestBody DeviceDataMsg dataMsg);

    /**
     * 查询最新因子值
     *
     * @param queryDto
     * @return
     */
    @PostMapping("/ktv/data/queryKvLast")
    Result<List<Map<String, String>>> queryKvLast(@RequestBody QueryDto queryDto);

    /**
     * 批量查询设备最新因子数据
     *
     * @param batchQueryDto
     * @return
     */
    @PostMapping("/ktv/data/batchQueryKvLast")
    Result<Map<String, List<Map<String, String>>>> batchQueryKvLast(@RequestBody BatchQueryDto batchQueryDto);

    /**
     * 批量查询设备最后上传因子数据时间
     *
     * @param deviceIds
     * @return
     */
    @GetMapping("/ktv/data/batchQueryDeviceLastReportTime")
    Result<List<IotKtvFactorReportTimeDto>> batchQueryDeviceLastReportTime(@RequestParam("deviceIds") String[] deviceIds);

    /**
     * 查询里程油耗列表
     *
     * @param deviceCodes
     * @param startDatetime
     * @param endDatetime
     * @return
     */
    @GetMapping("/ktv/data/queryOilMileageList")
    Result<List<Map<String, Object>>> queryOilMileageList(@RequestParam("deviceCodes") String[] deviceCodes,
                                                          @RequestParam("startDatetime") Long startDatetime,
                                                          @RequestParam("endDatetime") Long endDatetime);

    /**
     * 查询停车时长列表
     *
     * @param deviceCodes
     * @param startDatetime
     * @param endDatetime
     * @return
     */
    @GetMapping("/ktv/data/queryStopTimeList")
    Result<List<Map<String, Object>>> queryStopTimeList(@RequestParam("deviceCodes") String[] deviceCodes,
                                                        @RequestParam("startDatetime") Long startDatetime,
                                                        @RequestParam("endDatetime") Long endDatetime);

    /**
     * 根据业务编码查询设备历史数据列表
     *
     * @param queryDto
     * @return
     */
    @PostMapping("/ktv/data/queryKvByBusiness")
    Result<List<Map<String, String>>> queryKvByBusiness(@RequestBody QueryDto queryDto);

    /**
     * 根据业务编码查询设备最新数据
     *
     * @param queryDto
     * @return
     */
    @PostMapping("/ktv/data/queryBusinessLast")
    Result<List<Map<String, String>>> queryBusinessLast(@RequestBody QueryDto queryDto);

    /**
     * 分页查询设备轨迹
     *
     * @param queryDto
     * @return
     */
    @PostMapping("/ktv/data/queryKvPage")
    Result<PageResultDto> queryKvPage(@RequestBody PageQueryDto queryDto);

}

package allthings.iot.dos.client.api;

import allthings.iot.common.dto.QueryResult;
import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.client.fallback.IotDosFallBack;
import allthings.iot.dos.dto.query.IotDeviceAlarmDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author luhao
 * @date 2020/2/17 14:40
 */
@FeignClient(name = "${service.name}", fallback = IotDosFallBack.class)
public interface IotAlarmApi {

    /**
     * 查询告警列表
     *
     * @param deviceId
     * @param alarmCodes
     * @param beginTime
     * @param endTime
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @GetMapping("/dos/service/getDeviceAlarmsByDeviceId")
    ResultDTO<QueryResult<IotDeviceAlarmDTO>> getDeviceAlarmsByDeviceId(@RequestParam("deviceId") String deviceId,
                                                                        @RequestParam("alarmCodes") List<String> alarmCodes,
                                                                        @RequestParam("beginTime") long beginTime,
                                                                        @RequestParam("endTime") long endTime,
                                                                        @RequestParam("pageIndex") int pageIndex,
                                                                        @RequestParam("pageSize") int pageSize);

    /**
     * 查询设备最后5分钟内的报警
     *
     * @param deviceCode
     * @return
     */
    @GetMapping(value = "/alarms/latest")
    ResultDTO<QueryResult<IotDeviceAlarmDTO>> getRealAlarmList(@RequestParam("deviceCode") String deviceCode);


}

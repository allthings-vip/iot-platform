package allthings.iot.dos.controller;

import allthings.iot.common.dto.QueryResult;
import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.api.IotAlarmService;
import allthings.iot.dos.client.api.IotAlarmApi;
import allthings.iot.dos.dto.query.IotDeviceAlarmDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author tyf
 * @date 2019/07/04 14:25:37
 */
@RestController
public class IotAlarmController implements IotAlarmApi {

    @Autowired
    private IotAlarmService alarmService;

    @Override
    public ResultDTO<QueryResult<IotDeviceAlarmDTO>> getDeviceAlarmsByDeviceId(@RequestParam("deviceId") String deviceId,
                                                                               @RequestParam("alarmCodes") List<String> alarmCodes,
                                                                               @RequestParam("beginTime") long beginTime,
                                                                               @RequestParam("endTime") long endTime,
                                                                               @RequestParam("pageIndex") int pageIndex,
                                                                               @RequestParam("pageSize") int pageSize) {
        return alarmService.getDeviceAlarmsByDeviceId(deviceId, alarmCodes, beginTime, endTime, pageIndex,
                pageSize);
    }

    @Override
    public ResultDTO<QueryResult<IotDeviceAlarmDTO>> getRealAlarmList(@RequestParam("deviceCode") String deviceCode) {
        Long beginTime = System.currentTimeMillis() - 5 * 60 * 1000;
        Long endTime = System.currentTimeMillis() + 3 * 1000;
        Integer pageIndex = 1;
        Integer pageSize = 1000;
        return alarmService.getDeviceAlarmsByDeviceId(deviceCode, null, beginTime, endTime, pageIndex,
                pageSize);
    }

}

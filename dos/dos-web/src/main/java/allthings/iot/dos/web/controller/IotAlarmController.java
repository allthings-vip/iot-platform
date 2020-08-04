package allthings.iot.dos.web.controller;

import allthings.iot.common.dto.QueryResult;
import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.client.api.IotAlarmApi;
import allthings.iot.dos.dto.query.IotDeviceAlarmDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author :  luhao
 * @FileName :  IotAlarmEventController
 * @CreateDate :  2018-5-12
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
@RestController
public class IotAlarmController extends BaseController {
    @Autowired
    private IotAlarmApi iotAlarmApi;

    @GetMapping(value = "/alarms")
    public ResultDTO<QueryResult<IotDeviceAlarmDTO>> getAlarmList(@RequestParam("deviceCode") String deviceCode,
                                                                  @RequestParam("startDatetime") Long startDatetime,
                                                                  @RequestParam("endDatetime") Long endDatetime,
                                                                  @RequestParam("pageIndex") Integer pageIndex,
                                                                  @RequestParam("pageSize") Integer pageSize) {
        return iotAlarmApi.getDeviceAlarmsByDeviceId(deviceCode, null, startDatetime, endDatetime, pageIndex,
                pageSize);
    }

    @GetMapping(value = "/alarms/latest")
    public ResultDTO<QueryResult<IotDeviceAlarmDTO>> getRealAlarmList(@RequestParam("deviceCode") String deviceCode) {
        //取5分钟内的报警
        Long startDatetime = System.currentTimeMillis() - 5 * 60 * 1000;
        Long endDatetime = System.currentTimeMillis() + 3 * 1000;
        Integer pageIndex = 1;
        Integer pageSize = 1000;
        return iotAlarmApi.getDeviceAlarmsByDeviceId(deviceCode, null, startDatetime, endDatetime, pageIndex,
                pageSize);
    }
}

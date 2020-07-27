package allthings.iot.dms.controller;

import allthings.iot.common.dto.QueryResult;
import allthings.iot.common.dto.Result;
import allthings.iot.dms.dto.DeviceLogDto;
import allthings.iot.dms.service.DeviceLogServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author :  sylar
 * @FileName :  DeviceLogController
 * @CreateDate :  2017/11/08
 * @Description :  日志管理页面控制器
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) allthings-vip All Rights Reserved
 * *******************************************************************************************
 */
@RestController
@RequestMapping("/deviceManagerService/dms")
public class DeviceLogController {
    @Autowired
    DeviceLogServiceImpl deviceLogServiceImpl;

    @RequestMapping(value = "/getDeviceLogsByTime", method = RequestMethod.GET)
    public Result<QueryResult<DeviceLogDto>> getDeviceLogsByTime(@RequestParam(required = false) String deviceId, @RequestParam(required =
            false) String deviceType, @RequestParam(required = false) String logType, long beginTime, long endTime,
                                                                 int pageIndex, int pageSize) {
        return Result.newSuccess(deviceLogServiceImpl.getDeviceLogsByTime(deviceId, deviceType, logType, beginTime,
                endTime, pageIndex, pageSize));
    }
}

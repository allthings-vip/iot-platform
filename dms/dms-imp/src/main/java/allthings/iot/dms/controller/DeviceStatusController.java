package allthings.iot.dms.controller;

import allthings.iot.common.dto.Result;
import allthings.iot.dms.service.DeviceStatusServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author :  sylar
 * @FileName :  DeviceStatusController
 * @CreateDate :  2017/11/08
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
@RequestMapping("/dms")
public class DeviceStatusController {

    @Autowired
    DeviceStatusServiceImpl deviceStatusServiceImpl;

    @RequestMapping(value = "/getDeviceStatus", method = RequestMethod.GET)
    public Result<?> getDeviceStatus(String deviceId) {
        return Result.newSuccess(deviceStatusServiceImpl.getDeviceStatus(deviceId));
    }

    @RequestMapping(value = "/getDeviceStatusBatch", method = RequestMethod.GET)
    public Result<?> getDeviceStatusBatch(String[] deviceIds) {
        return Result.newSuccess(deviceStatusServiceImpl.getDeviceStatusBatch(deviceIds));
    }
}
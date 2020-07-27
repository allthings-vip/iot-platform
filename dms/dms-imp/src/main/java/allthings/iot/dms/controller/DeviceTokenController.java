package allthings.iot.dms.controller;

import allthings.iot.common.dto.QueryResult;
import allthings.iot.common.dto.Result;
import allthings.iot.dms.dto.DeviceTokenDto;
import allthings.iot.dms.service.DeviceTokenServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author :  sylar
 * @FileName :  DeviceTokenController
 * @CreateDate :  2017/11/08
 * @Description :
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
public class DeviceTokenController {

    @Autowired
    DeviceTokenServiceImpl deviceTokenServiceImpl;

    @RequestMapping(value = "/countOfDeviceToken", method = RequestMethod.GET)
    public Result<Long> countOfDeviceToken() {
        return Result.newSuccess(deviceTokenServiceImpl.countOfDeviceToken());
    }


    @RequestMapping(value = "/generateDeviceId", method = RequestMethod.GET)
    public Result<String> generateDeviceId(String deviceType, String token) {
        return Result.newSuccess(deviceTokenServiceImpl.generateDeviceId(deviceType, token));
    }


    @RequestMapping(value = "/getDeviceTokenByDeviceId", method = RequestMethod.GET)
    public Result<DeviceTokenDto> getDeviceTokenByDeviceId(String deviceId) {
        return Result.newSuccess(deviceTokenServiceImpl.getDeviceTokenByDeviceId(deviceId));
    }


    @RequestMapping(value = "/getDeviceTokenByToken", method = RequestMethod.GET)
    public Result<DeviceTokenDto> getDeviceTokenByToken(String token) {
        return Result.newSuccess(deviceTokenServiceImpl.getDeviceTokenByToken(token));
    }


    @RequestMapping(value = "/getDeviceTokensByDeviceType", method = RequestMethod.GET)
    public Result<QueryResult<DeviceTokenDto>> getDeviceTokensByDeviceType(String deviceType, int pageIndex, int pageSize) {
        return Result.newSuccess(deviceTokenServiceImpl.getDeviceTokensByDeviceType(deviceType, pageIndex, pageSize));
    }
}

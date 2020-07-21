package allthings.iot.dms.controller;

import allthings.iot.common.dto.Result;
import allthings.iot.dms.service.DeviceInfoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author :  sylar
 * @FileName :  DeviceInfoController
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
public class DeviceInfoController {

    @Autowired
    DeviceInfoServiceImpl deviceInfoServiceImpl;

    @RequestMapping(value = "/countOfDeviceInfo", method = RequestMethod.GET)
    public Result<?> countOfDeviceInfo() {
        return Result.newSuccess(deviceInfoServiceImpl.countOfDeviceInfo());
    }


    @RequestMapping(value = "/countOfDeviceInfoByDeviceType", method = RequestMethod.GET)
    public Result<?> countOfDeviceInfoByDeviceType(String deviceType) {
        return Result.newSuccess(deviceInfoServiceImpl.countOfDeviceInfoByDeviceType(deviceType));
    }


    @RequestMapping(value = "/countOfDeviceInfoByDeviceTypeAndVersionCode", method = RequestMethod.GET)
    public Result<?> countOfDeviceInfoByDeviceTypeAndVersionCode(String deviceType, int versionCode) {
        return Result.newSuccess(deviceInfoServiceImpl.countOfDeviceInfoByDeviceTypeAndVersionCode(deviceType,
                versionCode));
    }


    @RequestMapping(value = "/getDeviceInfoById", method = RequestMethod.GET)
    public Result<?> getDeviceInfoById(long id) {
        return Result.newSuccess(deviceInfoServiceImpl.getDeviceInfoById(id));
    }


    @RequestMapping(value = "/getDeviceInfoByDeviceId", method = RequestMethod.GET)
    public Result<?> getDeviceInfoByDeviceId(String deviceId) {
        return Result.newSuccess(deviceInfoServiceImpl.getDeviceInfoByDeviceId(deviceId));
    }


    @RequestMapping(value = "/getDeviceInfoByMac", method = RequestMethod.GET)
    public Result<?> getDeviceInfoByMac(String mac) {
        return Result.newSuccess(deviceInfoServiceImpl.getDeviceInfoByMac(mac));
    }


    @RequestMapping(value = "/getDeviceInfosByDeviceType", method = RequestMethod.GET)
    public Result<?> getDeviceInfosByDeviceType(String deviceType, int pageIndex, int pageSize) {
        return Result.newSuccess(deviceInfoServiceImpl.getDeviceInfosByDeviceType(deviceType, pageIndex, pageSize));
    }


    @RequestMapping(value = "/getDeviceInfosByDeviceTypeAndVersion", method = RequestMethod.GET)
    public Result<?> getDeviceInfosByDeviceTypeAndVersion(String deviceType, int versionCode, int pageIndex, int
            pageSize) {
        return Result.newSuccess(deviceInfoServiceImpl.getDeviceInfosByDeviceTypeAndVersion(deviceType, versionCode,
                pageIndex, pageSize));
    }

}

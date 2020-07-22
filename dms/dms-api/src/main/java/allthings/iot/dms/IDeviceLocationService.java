package allthings.iot.dms;

import allthings.iot.dms.dto.DeviceLocationDto;

/**
 * @author :  sylar
 * @FileName :  IDeviceLocationService
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
public interface IDeviceLocationService {
    /**
     * 绑定设备位置
     *
     * @param userId       用户ID
     * @param deviceType   设备类型
     * @param deviceId     设备ID
     * @param coorType     坐标类型：0-国际坐标  1-高德坐标(国测局坐标)  2-百度坐标
     * @param lon          经度
     * @param lat          纬度
     * @param locationDesc 位置描述
     * @return 数据库记录编码
     */
    long bindLocation(String userId, String deviceType, String deviceId, int coorType, double lon, double lat, String
            locationDesc);

    /**
     * 获取设备位置
     *
     * @param deviceId 设备ID
     * @param coorType 坐标类型：0-国际坐标  1-高德坐标(国测局坐标)  2-百度坐标
     * @return 设备坐标结构体
     */
    DeviceLocationDto getLocation(String deviceId, int coorType);
}

package allthings.iot.dms.dao;

import allthings.iot.dms.entity.IotDeviceToken;
import allthings.iot.util.jpa.BaseRepository;

/**
 * @author :  sylar
 * @FileName :  DeviceTokenDao
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
public interface DeviceTokenDao extends BaseRepository<IotDeviceToken, Long> {

    /**
     * 根据设备id获取设备token信息
     *
     * @param deviceId
     * @return
     */
    IotDeviceToken getByDeviceId(String deviceId);

    /**
     * 根据token获取设备token信息
     *
     * @param token
     * @return
     */
    IotDeviceToken getByToken(String token);
}

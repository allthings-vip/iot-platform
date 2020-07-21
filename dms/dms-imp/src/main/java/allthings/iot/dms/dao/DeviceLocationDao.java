package allthings.iot.dms.dao;

import allthings.iot.dms.entity.IotDeviceLocation;
import allthings.iot.util.jpa.BaseRepository;

/**
 * @author :  sylar
 * @FileName :  DeviceLocationDao
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
public interface DeviceLocationDao extends BaseRepository<IotDeviceLocation, Long> {

    /**
     * 根据设备id获取位置信息
     *
     * @param deviceId
     * @return
     */
    IotDeviceLocation getByDeviceId(String deviceId);
}

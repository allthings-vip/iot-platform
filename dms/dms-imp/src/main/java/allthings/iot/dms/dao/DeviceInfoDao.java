package allthings.iot.dms.dao;

import allthings.iot.dms.entity.IotDeviceInfo;
import allthings.iot.util.jpa.BaseRepository;

/**
 * @author :  sylar
 * @FileName :  DeviceInfoDao
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
public interface DeviceInfoDao extends BaseRepository<IotDeviceInfo, Long> {

    /**
     * 根据设备id获取设备信息
     *
     * @param deviceId
     * @return
     */
    IotDeviceInfo getByDeviceId(String deviceId);

    /**
     * 根据设备mac获取设备信息
     *
     * @param mac
     * @return
     */
    IotDeviceInfo getByMac(String mac);

    /**
     * bid获取设备信息
     *
     * @param bid
     * @return
     */
    IotDeviceInfo getByBid(String bid);
}

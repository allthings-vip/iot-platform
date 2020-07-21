package allthings.iot.dms.dao;

import allthings.iot.dms.entity.IotDeviceOtaFile;
import allthings.iot.util.jpa.BaseRepository;

/**
 * @author :  sylar
 * @FileName :  DeviceOtaFileDao
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
public interface DeviceOtaFileDao extends BaseRepository<IotDeviceOtaFile, Long> {
    /**
     * 根据设备类型和版本号获取设备在线升级文件
     *
     * @param deviceType
     * @param versionCode
     * @return
     */
    IotDeviceOtaFile getByDeviceTypeAndVersionCode(String deviceType, int versionCode);
}

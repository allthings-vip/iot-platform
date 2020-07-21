package allthings.iot.dms.dao;

import allthings.iot.dms.entity.IotDeviceOwner;
import allthings.iot.util.jpa.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author :  luhao
 * @FileName :  DeviceOwnerDao
 * @CreateDate :  2016/6/28
 * @Description :
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) www.XXXXX.com   All Rights Reserved
 * *******************************************************************************************
 */
public interface DeviceOwnerDao extends BaseRepository<IotDeviceOwner, Long> {

    /**
     * 根据deviceid获取deviceOwner
     *
     * @param deviceId
     * @return
     */
    @Query(" from IotDeviceOwner where deviceId=:deviceId and isBound=true ")
    IotDeviceOwner getDeviceOwnerByDeviceId(@Param("deviceId") String deviceId);


    /**
     * 解除绑定拥有者的所有设备
     *
     * @param ownerId
     */
    @Modifying
    @Query(" update IotDeviceOwner set isBound = false, unBindDatetime=UNIX_TIMESTAMP() where ownerId=:ownerId and " +
            "isBound=true ")
    void unBindDeviceOwner(@Param("ownerId") String ownerId);


    /**
     * 解除绑定指定的设备
     *
     * @param ownerId
     * @param deviceList
     */
    @Modifying
    @Query(" update IotDeviceOwner set isBound = false, unBindDatetime=UNIX_TIMESTAMP() where ownerId=:ownerId and " +
            "deviceId in( :deviceList ) and isBound=true ")
    void unBindDeviceOwner(@Param("ownerId") String ownerId, @Param("deviceList") List<String> deviceList);
}

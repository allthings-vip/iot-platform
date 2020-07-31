package allthings.iot.dos.dao;

import allthings.iot.dos.model.IotDeviceStatus;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import allthings.iot.util.jpa.BaseRepository;

import java.util.Date;

/**
 * @author :  luhao
 * @FileName :  IotDeviceStatusDao
 * @CreateDate :  2018-5-15
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
public interface IotDeviceStatusDao extends BaseRepository<IotDeviceStatus, Long> {

    @Modifying
    @Query(value = " update iot_dos_device_status set " +
            "node_id =:nodeId,terminal_ip =:terminalIp,is_connected=:connected," +
            "latest_disconnect_datetime=:latestDisConnectDatetime,is_deleted=:isDeleted," +
            "create_operator_id=1 WHERE iot_dos_device_id=:iotDeviceId ", nativeQuery = true)
    Integer updateIotDeviceOfflineStatus(@Param("iotDeviceId") Long iotDeviceId, @Param("nodeId") String nodeId,
                                         @Param("terminalIp") String terminalIp, @Param("connected") Boolean connected,
                                         @Param("latestDisConnectDatetime") Date latestDisConnectDatetime,
                                         @Param("isDeleted") Boolean isDeleted);

    @Modifying
    @Query(value = " update iot_dos_device_status set " +
            "node_id =:nodeId,terminal_ip =:terminalIp,is_connected=:connected," +
            "latest_connect_datetime=:latestConnectDatetime,is_deleted=:isDeleted," +
            "create_operator_id=1 WHERE iot_dos_device_id=:iotDeviceId ", nativeQuery = true)
    Integer updateIotDeviceOnlineStatus(@Param("iotDeviceId") Long iotDeviceId, @Param("nodeId") String nodeId,
                                        @Param("terminalIp") String terminalIp, @Param("connected") Boolean connected,
                                        @Param("latestConnectDatetime") Date latestConnectDatetime,
                                        @Param("isDeleted") Boolean isDeleted);

}

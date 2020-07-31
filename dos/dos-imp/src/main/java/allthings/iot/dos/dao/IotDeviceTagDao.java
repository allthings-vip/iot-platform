package allthings.iot.dos.dao;

import allthings.iot.dos.model.IotDeviceTag;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import allthings.iot.util.jpa.BaseRepository;

import java.util.List;

/**
 * @author :  txw
 * @FileName :  IotTagDao
 * @CreateDate :  2018/10/31
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
public interface IotDeviceTagDao extends BaseRepository<IotDeviceTag, Long> {

    @Query("select iotTagId from IotDeviceTag where isDeleted=false and iotDeviceId=:iotDeviceId")
    List<Long> getTagIdByDeviceId(@Param("iotDeviceId") Long iotDeviceId);

    @Modifying
    @Query("update IotDeviceTag set isDeleted=true, stampDate = CURRENT_TIMESTAMP, modifyOperatorId =:operatorId " +
            " where iotDeviceId=:iotDeviceId and isDeleted=false ")
    Integer deleteAllByDeviceId(@Param("iotDeviceId") Long iotDeviceId, @Param("operatorId") Long operatorId);
}

package allthings.iot.dos.dao;

import allthings.iot.dos.model.IotDeviceTypeTag;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import allthings.iot.util.jpa.BaseRepository;

import java.util.List;

/**
 * @author :  luhao
 * @FileName :  IotDeviceTypeTagDao
 * @CreateDate :  2018/5/2
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
public interface IotDeviceTypeTagDao extends BaseRepository<IotDeviceTypeTag, Long> {
    /**
     * 根据设备类型ID查询设备类型标签
     *
     * @param iotDeviceTypeId
     * @param isDeleted
     * @return
     */
    List<IotDeviceTypeTag> getByIotDeviceTypeIdAndIsDeleted(Long iotDeviceTypeId, boolean isDeleted);

    /**
     * 删除设备类型标签
     *
     * @param iotDeviceTypeId
     * @param operator
     * @return
     */
    @Modifying
    @Query(" update IotDeviceTypeTag set isDeleted=true ,operator=:operator where iotDeviceTypeId=:iotDeviceTypeId" +
            " and  isDeleted=false ")
    Integer deleteByIotDeviceTypeId(@Param("iotDeviceTypeId") Long iotDeviceTypeId, @Param("operator") String operator);


    @Query(" from IotDeviceTypeTag t where t.iotTagId =:iotTagId and isDeleted = false ")
    List<IotDeviceTypeTag> getByIotTagId(@Param("iotTagId") Long iotTagId);

}

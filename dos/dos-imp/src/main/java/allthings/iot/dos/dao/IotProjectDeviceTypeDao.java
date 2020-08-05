package allthings.iot.dos.dao;

import allthings.iot.dos.dto.query.IotProjectDeviceTypeDTO;
import allthings.iot.dos.model.IotProjectDeviceType;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import allthings.iot.util.jpa.BaseRepository;

import java.util.List;

/**
 * @author :  luhao
 * @FileName :  IotProjectDeviceTypeDao
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
public interface IotProjectDeviceTypeDao extends BaseRepository<IotProjectDeviceType, Long> {

    @Modifying
    @Query(" update IotProjectDeviceType set isDeleted=true,modifyOperatorId=:modifyOperatorId where iotProjectId=:iotProjectId and " +
            "isDeleted=false ")
    Integer deleteByIotProjectId(@Param("iotProjectId") Long iotProjectId, @Param("modifyOperatorId") Long modifyOperatorId);

    @Query(" from IotProjectDeviceType where iotProjectId=:iotProjectId and isDeleted=:isDeleted ")
    List<IotProjectDeviceType> getByIotProjectIdAndDeleted(@Param("iotProjectId") Long iotProjectId, @Param
            ("isDeleted") boolean
            isDeleted);

    @Query(" select new allthings.iot.dos.dto.query.IotProjectDeviceTypeDTO(tp.iotDeviceTypeId,tp.deviceTypeName, tp" +
            ".deviceTypeCode) from " +
            "IotDeviceType tp where tp.iotProjectId=:iotProjectId and tp.isDeleted=false ")
    List<IotProjectDeviceTypeDTO> getIotDeviceTypeByIotProjectIdAndDeleted(@Param("iotProjectId") Long iotProjectId);


    @Query(value = "select new allthings.iot.dos.dto.query.IotProjectDeviceTypeDTO(ipdt.iotDeviceTypeId,(select idt" +
            ".deviceTypeName from IotDeviceType idt where idt" +
            ".iotDeviceTypeId = ipdt.iotDeviceTypeId)) from IotProjectDeviceType ipdt where ipdt.iotDeviceTypeId in " +
            "(:iotDeviceTypeIds) and ipdt.isDeleted = false group by ipdt.iotDeviceTypeId having count(ipdt" +
            ".iotDeviceTypeId) > 0")
    List<IotProjectDeviceTypeDTO> getIotProjectDeviceTypeByIotDeviceTypeId(@Param("iotDeviceTypeIds") List<Long>
                                                                                   iotDeviceTypeIds);


}

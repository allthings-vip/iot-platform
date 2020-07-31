package allthings.iot.dos.dao;

import allthings.iot.dos.dto.query.IotDataAggTypeQueryDTO;
import allthings.iot.dos.model.IotDataAggType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import allthings.iot.util.jpa.BaseRepository;

import java.util.List;

/**
 * @author :  luhao
 * @FileName :  IotDataAggTypeDao
 * @CreateDate :  2018/4/27
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
public interface IotDataAggTypeDao extends BaseRepository<IotDataAggType, Long> {

    @Query(" select new com.allthings.iot.dos.dto.query.IotDataAggTypeQueryDTO(iotDataAggTypeId,dataAggTypeName," +
            "dataAggTypeCode) from IotDataAggType where isDeleted=:isDeleted ")
    List<IotDataAggTypeQueryDTO> getAllByDeleted(@Param("isDeleted") boolean isDeleted);

}

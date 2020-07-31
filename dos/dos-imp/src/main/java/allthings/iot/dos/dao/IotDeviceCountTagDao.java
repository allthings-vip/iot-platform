package allthings.iot.dos.dao;

import allthings.iot.dos.dto.query.IotDeviceCountTitleDTO;
import allthings.iot.dos.model.offline.IotDeviceCountTag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import allthings.iot.util.jpa.BaseRepository;

/**
 * @author :  luhao
 * @FileName :  IotDeviceCountTagDao
 * @CreateDate :  2018-5-17
 * @Description :
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :getTopGroupByByIotTagId
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
public interface IotDeviceCountTagDao extends BaseRepository<IotDeviceCountTag, Long> {

    @Query(value =
            " select new com.allthings.iot.dos.dto.query.IotDeviceCountTitleDTO(SUM(idct.dayQuantity),it.iotTagId, it" +
                    ".tagName)" +
                    " from " +
                    " IotDeviceCountTag idct,IotTag it where it.iotTagId = idct.iotTagId group by " +
                    " idct.iotTagId order by SUM(idct.dayQuantity) desc ")
    Page<IotDeviceCountTitleDTO> getTopGroupByByIotTagId(@Param("pageable") Pageable pageable);

}

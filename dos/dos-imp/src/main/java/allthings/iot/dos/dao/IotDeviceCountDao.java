package allthings.iot.dos.dao;

import allthings.iot.dos.dto.query.IotDeviceCountQueryDTO;
import allthings.iot.dos.dto.query.IotDeviceCountTitleDTO;
import allthings.iot.dos.model.offline.IotDeviceCount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import allthings.iot.util.jpa.BaseRepository;

import java.util.Date;
import java.util.List;

/**
 * @author :  luhao
 * @FileName :  IotDeviceCountDao
 * @CreateDate :  2018-5-16
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
public interface IotDeviceCountDao extends BaseRepository<IotDeviceCount, Long> {

    /**
     * 查询设备趋势
     *
     * @param startDatetime
     * @param endDatetime
     * @param iotProjectId
     * @return
     */
    @Query(value =
            " select new allthings.iot.dos.dto.query.IotDeviceCountQueryDTO(sum(dayBeforeQuantity),inputDate) from " +
                    "IotDeviceCount where   inputDate>=:startDatetime and inputDate <:endDatetime and " +
                    "iotProjectId=:iotProjectId and isDeleted=false group by inputDate ")
    List<IotDeviceCountQueryDTO> getDayBeforeQuantityByDateRange(@Param("startDatetime") Date startDatetime,
                                                                 @Param("endDatetime") Date endDatetime,
                                                                 @Param("iotProjectId") Long iotProjectId);


    /**
     * 查询设备新增趋势
     *
     * @param startDatetime
     * @param endDatetime
     * @param iotProjectId
     * @return
     */
    @Query(value = " select new allthings.iot.dos.dto.query.IotDeviceCountQueryDTO(sum(dayQuantity),inputDate) from " +
            "IotDeviceCount where   inputDate>=:startDatetime and inputDate <:endDatetime and " +
            "iotProjectId=:iotProjectId and isDeleted=false group by inputDate ")
    List<IotDeviceCountQueryDTO> getDayQuantityByDateRange(@Param("startDatetime") Date startDatetime,
                                                           @Param("endDatetime") Date endDatetime,
                                                           @Param("iotProjectId") Long iotProjectId);

    /**
     * 查询
     *
     * @param inputDate
     * @param iotDeviceTypeId
     * @param iotProjectId
     * @return
     */
    @Query("from IotDeviceCount where inputDate=:inputDate and iotDeviceTypeId=:iotDeviceTypeId and " +
            "iotProjectId=:iotProjectId and isDeleted=false ")
    IotDeviceCount getIotDeviceCount(@Param("inputDate") Date inputDate,
                                     @Param("iotDeviceTypeId") Long iotDeviceTypeId,
                                     @Param("iotProjectId") Long iotProjectId);

    @Query(value =
            "select new allthings.iot.dos.dto.query.IotDeviceCountTitleDTO(SUM(ipc.dayQuantity),ip.iotDeviceTypeId," +
                    "ip" +
                    ".deviceTypeName) " +
                    "from IotDeviceCount ipc,IotDeviceType ip where ipc" +
                    ".iotDeviceTypeId = ip.iotDeviceTypeId GROUP BY ip.iotDeviceTypeId order by SUM(ipc.dayQuantity) " +
                    "desc ")
    Page<IotDeviceCountTitleDTO> getTopGroupByIotDeviceTypeId(@Param("pageable") Pageable pageable);


    @Query("select new allthings.iot.dos.dto.query.IotDeviceCountTitleDTO(SUM(ipc.dayQuantity),ip.iotProjectId,ip" +
            ".projectName) from " +
            "IotDeviceCount ipc,IotProject ip where ipc.iotProjectId =" +
            " ip.iotProjectId GROUP BY ip.iotProjectId order by SUM(ipc.dayQuantity) desc ")
    Page<IotDeviceCountTitleDTO> getTopGroupByIotProjectId(@Param("pageable") Pageable pageable);


    @Query(value =
            "select new allthings.iot.dos.dto.query.IotDeviceCountTitleDTO(SUM(ipc.dayQuantity),ip.iotDeviceTypeId," +
                    "ip" +
                    ".deviceTypeName) " +
                    "from IotDeviceCount ipc,IotDeviceType ip where ipc" +
                    ".iotDeviceTypeId = ip.iotDeviceTypeId and ip.iotDeviceTypeId in (:iotDeviceTypeIds) GROUP BY ip" +
                    ".iotDeviceTypeId order " +
                    "by " +
                    "SUM" +
                    "(ipc" +
                    ".dayQuantity) " +
                    "desc ")
    List<IotDeviceCountTitleDTO> getTopGroupByIotDeviceTypeId(@Param("iotDeviceTypeIds") Long[] iotDeviceTypeIds);

}

package allthings.iot.dos.dao;

import allthings.iot.dos.dto.query.IotPointCountQueryDTO;
import allthings.iot.dos.dto.query.IotPointCountTitleDTO;
import allthings.iot.dos.model.offline.IotPointCount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import allthings.iot.util.jpa.BaseRepository;

import java.util.Date;
import java.util.List;

/**
 * @author :  luhao
 * @FileName :  IotPointCountDao
 * @CreateDate :  2018-5-20
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
public interface IotPointCountDao extends BaseRepository<IotPointCount, Long> {

    /**
     * 查询时间段内数据点
     *
     * @param startDatetime
     * @param endDatetime
     * @param iotProjectId
     * @return
     */
    @Query(value =
            " select new allthings.iot.dos.dto.query.IotPointCountQueryDTO(sum(dayBeforeQuantity),inputDate) from " +
                    "IotPointCount where inputDate>=:startDatetime and inputDate <:endDatetime and " +
                    "iotProjectId=:iotProjectId" +
                    " and isDeleted=false group by inputDate ")
    List<IotPointCountQueryDTO> getDayBeforeQuantityByDateRange(@Param("startDatetime") Date startDatetime,
                                                                @Param("endDatetime") Date endDatetime,
                                                                @Param("iotProjectId") Long iotProjectId);

    /**
     * 查询时间段内新增点数
     *
     * @param startDatetime
     * @param endDatetime
     * @param iotProjectId
     * @return
     */
    @Query(value = " select new allthings.iot.dos.dto.query.IotPointCountQueryDTO(sum(dayQuantity),inputDate) from " +
            "IotPointCount where  inputDate>=:startDatetime and inputDate <:endDatetime and " +
            "iotProjectId=:iotProjectId and isDeleted=false group by inputDate ")
    List<IotPointCountQueryDTO> getDayQuantityByDateRange(@Param("startDatetime") Date startDatetime,
                                                          @Param("endDatetime") Date endDatetime,
                                                          @Param("iotProjectId") Long iotProjectId);


    @Query("select new allthings.iot.dos.dto.query.IotPointCountTitleDTO( SUM(ipc.dayQuantity),ip.projectName) from " +
            "IotPointCount ipc,IotProject ip where ipc.iotProjectId =" +
            " ip.iotProjectId GROUP BY ip.iotProjectId order by SUM(ipc.dayQuantity) desc ")
    Page<IotPointCountTitleDTO> getTopGroupByIotProjectId(@Param("pageable") Pageable pageable);


    @Query("select new allthings.iot.dos.dto.query.IotPointCountTitleDTO(SUM(ipc.dayQuantity),ip.deviceTypeName) from " +
            "IotPointCount ipc,IotDeviceType ip where ipc.iotDeviceTypeId = ip.iotDeviceTypeId GROUP BY ip" +
            ".iotDeviceTypeId order by SUM(ipc.dayQuantity) desc")
    Page<IotPointCountTitleDTO> getTopGroupByIotDeviceTypeId(@Param("pageable") Pageable pageable);


    /**
     * 管理员查询首页概况
     *
     * @return
     */
    @Query(value = "SELECT SUM(day_before_quantity+day_quantity) from iot_dos_point_count ipc, (SELECT " +
            "iot_dos_project_id,iot_dos_devicetype_Id,MAX(gmt_modified) gmt_modified from iot_dos_point_count " +
            "where is_deleted=FALSE GROUP BY iot_dos_project_id,iot_dos_devicetype_Id ) pc WHERE " +
            "ipc.iot_dos_project_id=pc.iot_dos_project_id and ipc.gmt_modified=pc.gmt_modified  and " +
            "ipc.iot_dos_devicetype_Id=pc.iot_dos_devicetype_Id and ipc.is_deleted=FALSE", nativeQuery = true)
    Long getIotPointCount();

    /**
     * 用户查询首页概况
     *
     * @param iotUserId
     * @return
     */
    @Query(value =
            "SELECT SUM(day_before_quantity+day_quantity) from iot_dos_point_count ipc,iot_dos_user_project up ," +
                    "(SELECT " +
                    " iot_dos_project_id,iot_dos_devicetype_Id,MAX(gmt_modified) gmt_modified from " +
                    "iot_dos_point_count " +
                    " where is_deleted=FALSE GROUP BY iot_dos_project_id,iot_dos_devicetype_Id ) pc WHERE " +
                    " ipc.iot_dos_project_id=pc.iot_dos_project_id and ipc.gmt_modified=pc.gmt_modified  and " +
                    " ipc.iot_dos_devicetype_Id=pc.iot_dos_devicetype_Id and ipc.is_deleted=FALSE" +
                    " and up.iot_dos_user_id=:iotUserId and up.iot_dos_project_id=ipc.iot_dos_project_id and up" +
                    ".is_deleted=FALSE",
            nativeQuery = true)
    Long getIotPointCountByIotUserId(@Param("iotUserId") Long iotUserId);

    /**
     * 项目数据点数
     *
     * @param iotProjectId
     * @return
     */
    @Query(value = "SELECT SUM(day_before_quantity+day_quantity) " +
            "FROM iot_dos_point_count ipc ,(SELECT  iot_dos_project_id,iot_dos_devicetype_Id," +
            "MAX(gmt_modified) gmt_modified FROM iot_dos_point_count  WHERE is_deleted=FALSE GROUP BY " +
            "iot_dos_project_id,iot_dos_devicetype_Id ) pc WHERE  ipc.iot_dos_devicetype_Id=pc.iot_dos_devicetype_Id " +
            "AND pc.iot_dos_project_id=ipc.iot_dos_project_id AND pc.gmt_modified=ipc.gmt_modified AND " +
            "ipc.iot_dos_project_id=:iotProjectId AND ipc.is_deleted=FALSE",
            nativeQuery = true)
    Long getIotPointCountByIotProjectId(@Param("iotProjectId") Long iotProjectId);

    /**
     * 项目新增点数
     *
     * @param iotProjectId
     * @return
     */
    @Query(value =
            "SELECT SUM(day_quantity)FROM iot_dos_point_count ipc ,(SELECT  iot_dos_project_id,iot_dos_devicetype_Id," +
                    "MAX(gmt_modified) gmt_modified FROM iot_dos_point_count  WHERE is_deleted=FALSE GROUP BY " +
                    "iot_dos_project_id,iot_dos_devicetype_Id ) pc WHERE  ipc.iot_dos_devicetype_Id=pc" +
                    ".iot_dos_devicetype_Id " +
                    "AND pc.iot_dos_project_id=ipc.iot_dos_project_id AND pc.gmt_modified=ipc.gmt_modified AND " +
                    "ipc.iot_dos_project_id=:iotProjectId AND ipc.is_deleted=FALSE",
            nativeQuery = true)
    Long getAddedIotPointCountByIotProjectId(@Param("iotProjectId") Long iotProjectId);


    @Query("from IotPointCount where inputDate=:inputDate and iotProjectId=:iotProjectId and " +
            "iotDeviceTypeId=:iotDeviceTypeId and isDeleted=false ")
    IotPointCount getIotPointCountByIotProjectIdAndIotDeviceTypeId(@Param("inputDate") Date inputDate,
                                                                   @Param("iotProjectId") Long iotProjectId,
                                                                   @Param("iotDeviceTypeId") Long iotDeviceTypeId);

}

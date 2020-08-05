package allthings.iot.dos.dao;

import allthings.iot.dos.dto.IotDeviceDTO;
import allthings.iot.dos.dto.query.IotDeviceTypeTagDTO;
import allthings.iot.dos.dto.query.IotProjectSimpleDTO;
import allthings.iot.dos.dto.query.IotTagQueryDTO;
import allthings.iot.dos.model.IotTag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import allthings.iot.util.jpa.BaseRepository;

import java.util.List;

/**
 * @author :  luhao
 * @FileName :  IotTagDao
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
public interface IotTagDao extends BaseRepository<IotTag, Long> {

    @Query("select new allthings.iot.dos.dto.query.IotDeviceTypeTagDTO(tag.iotTagId, tag.tagName) from IotTag tag " +
            "where " +
            "exists (select t.iotTagId from " +
            "IotDeviceTypeTag t " +
            "where t.iotDeviceTypeId=:iotDeviceTypeId and t.isDeleted=:isDeleted and tag.iotTagId = t.iotTagId) " +
            "and tag.isDeleted=:isDeleted ")
    List<IotDeviceTypeTagDTO> getByIotDeviceTypeId(@Param("iotDeviceTypeId") Long iotDeviceTypeId, @Param
            ("isDeleted") boolean isDeleted);

    @Query(" select t.iotTagId from IotTag t where t.isDeleted = false ")
    Page<Long> getIotTagIdPage(@Param("pageable") Pageable pageable);

    /**
     * 获取标签列表
     *
     * @param isDeleted
     * @param iotProjectId
     * @return
     */
    @Query("select new allthings.iot.dos.dto.query.IotTagQueryDTO(tag.iotTagId, tag.tagName) from IotTag tag where " +
            "tag.isDeleted=:isDeleted and tag.iotProjectId=:iotProjectId")
    List<IotTagQueryDTO> getAllByIsDeleted(@Param("isDeleted") boolean isDeleted,
                                           @Param("iotProjectId") Long iotProjectId);

    /**
     * 获取标签下已选设备
     *
     * @param iotTagId
     * @param iotProjectId
     * @param keywords
     * @return
     */
    @Query("select new allthings.iot.dos.dto.IotDeviceDTO(dt.deviceTypeName, device.deviceCode, device.deviceName, " +
            "device.bizCode, device.iotDeviceId)" +
            " from IotDevice device, IotDeviceType dt where device.isDeleted=false and dt.isDeleted=false " +
            " and device.iotDeviceTypeId = dt.iotDeviceTypeId " +
            " and device.iotDeviceId in(select dt.iotDeviceId from IotDeviceTag dt " +
            " where dt.iotTagId=:iotTagId and dt.isDeleted=false)" +
            " and device.iotProjectId =:iotProjectId" +
            " and (dt.deviceTypeName like :keywords or device.deviceCode like :keywords " +
            " or device.deviceName like :keywords or device.bizCode like :keywords)")
    List<IotDeviceDTO> getDeviceByIotTagIdAndIotProjectId(@Param("iotTagId") Long iotTagId,
                                                          @Param("iotProjectId") Long iotProjectId,
                                                          @Param("keywords") String keywords);

    /**
     * 获取标签下已选设备
     *
     * @param iotTagId
     * @param iotProjectId
     * @return
     */
    @Query("select new allthings.iot.dos.dto.IotDeviceDTO(dt.deviceTypeName, device.deviceCode, device.deviceName, " +
            "device.bizCode, device.iotDeviceId)" +
            " from IotDevice device, IotDeviceType dt where device.isDeleted=false and dt.isDeleted=false " +
            " and device.iotDeviceTypeId = dt.iotDeviceTypeId " +
            " and device.iotDeviceId in(select dt.iotDeviceId from IotDeviceTag dt " +
            " where dt.iotTagId=:iotTagId and dt.isDeleted=false)" +
            " and device.iotProjectId =:iotProjectId")
    List<IotDeviceDTO> getDeviceByIotTagIdAndIotProjectId(@Param("iotTagId") Long iotTagId,
                                                          @Param("iotProjectId") Long iotProjectId);

    /**
     * 获取标签下未选设备
     *
     * @param iotTagId
     * @param iotProjectId
     * @param keywords
     * @return
     */
    @Query("select new allthings.iot.dos.dto.IotDeviceDTO(dt.deviceTypeName, device.deviceCode, device.deviceName, " +
            "device.bizCode, device.iotDeviceId)" +
            " from IotDevice device, IotDeviceType dt where device.isDeleted=false and dt.isDeleted=false " +
            " and device.iotDeviceTypeId = dt.iotDeviceTypeId " +
            " and device.iotDeviceId not in(select dt.iotDeviceId from IotDeviceTag dt " +
            " where dt.iotTagId=:iotTagId and dt.isDeleted=false)" +
            " and device.iotProjectId =:iotProjectId" +
            " and (dt.deviceTypeName like :keywords or device.deviceCode like :keywords " +
            " or device.deviceName like :keywords or device.bizCode like :keywords)")
    List<IotDeviceDTO> getUnchooseDeviceByIotTagIdAndIotProjectId(@Param("iotTagId") Long iotTagId,
                                                                  @Param("iotProjectId") Long iotProjectId,
                                                                  @Param("keywords") String keywords);

    /**
     * 获取标签下未选设备
     *
     * @param iotTagId
     * @param iotProjectId
     * @return
     */
    @Query("select new allthings.iot.dos.dto.IotDeviceDTO(dt.deviceTypeName, device.deviceCode, device.deviceName, " +
            "device.bizCode, device.iotDeviceId)" +
            " from IotDevice device, IotDeviceType dt where device.isDeleted=false and dt.isDeleted=false " +
            " and device.iotDeviceTypeId = dt.iotDeviceTypeId " +
            " and device.iotDeviceId not in(select dt.iotDeviceId from IotDeviceTag dt " +
            " where dt.iotTagId=:iotTagId and dt.isDeleted=false)" +
            " and device.iotProjectId =:iotProjectId")
    List<IotDeviceDTO> getUnchooseDeviceByIotTagIdAndIotProjectId(@Param("iotTagId") Long iotTagId,
                                                                  @Param("iotProjectId") Long iotProjectId);

    @Query(value = "select new allthings.iot.dos.dto.query.IotProjectSimpleDTO(it.iotProjectId,(select ip.projectName " +
            "from IotProject ip where ip.iotProjectId = it.iotProjectId and ip.isDeleted=false )) " +
            "from IotTag it where it.iotProjectId in (:iotProjectIds) and it.isDeleted = false group by it" +
            ".iotProjectId having count(it.iotProjectId) > 0 ")
    List<IotProjectSimpleDTO> getIotTagByIotProjectId(@Param("iotProjectIds") List<Long> iotProjectIds);


    /**
     * 删除标签
     *
     * @param iotTagId
     * @param iotProjectId
     * @param operatorId
     * @return
     */
    @Modifying
    @Query("update IotTag set isDeleted=true, stampDate = CURRENT_TIMESTAMP, modifyOperatorId =:operatorId " +
            "where iotTagId=:iotTagId and " +
            "iotProjectId=:iotProjectId")
    Integer deleteTagByTagId(@Param("iotTagId") Long iotTagId, @Param("iotProjectId") Long iotProjectId,
                             @Param("operatorId") Long operatorId);

    /**
     * 删除标签下所有设备
     *
     * @param iotTagId
     * @param operatorId
     * @return
     */
    @Modifying
    @Query("update IotDeviceTag set isDeleted=true, stampDate = CURRENT_TIMESTAMP, modifyOperatorId =:operatorId " +
            "where iotTagId=:iotTagId")
    Integer deleteAllDeviceByTagId(@Param("iotTagId") Long iotTagId, @Param("operatorId") Long operatorId);

    @Modifying
    @Query("update IotDeviceTag set isDeleted=true, stampDate = CURRENT_TIMESTAMP, modifyOperatorId =:operatorId " +
            "where iotDeviceId=:iotDeviceId")
    Integer deleteAllTagByDeviceId(@Param("iotDeviceId") Long iotDeviceId, @Param("operatorId") Long operatorId);


    /**
     * 取消选择标签下的设备
     *
     * @param iotDeviceId
     * @param iotTagId
     * @param operatorId
     * @return
     */
    @Modifying
    @Query("update IotDeviceTag set isDeleted=true, stampDate = CURRENT_TIMESTAMP, modifyOperatorId =:operatorId " +
            "where iotTagId=:iotTagId and iotDeviceId in (:iotDeviceId)")
    Integer deleteDeviceOfTag(@Param("iotDeviceId") List<Long> iotDeviceId, @Param("iotTagId") Long iotTagId,
                              @Param("operatorId") Long operatorId);

    /**
     * 判断标签是否已存在
     *
     * @param tagName
     * @param iotProjectId
     * @return
     */
    @Query("select count(t.iotTagId) from IotTag t where t.tagName =:tagName and t.iotProjectId =:iotProjectId " +
            "and t.isDeleted = false ")
    Integer getByTagName(@Param("tagName") String tagName, @Param("iotProjectId") Long iotProjectId);

    /**
     * 获取标签ID
     *
     * @param tagName
     * @param iotProjectId
     * @return
     */
    @Query("select t.iotTagId from IotTag t where t.tagName =:tagName and t.iotProjectId =:iotProjectId " +
            "and t.isDeleted = false ")
    Long getIdByTagName(@Param("tagName") String tagName, @Param("iotProjectId") Long iotProjectId);


}

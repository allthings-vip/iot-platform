package allthings.iot.dos.dao;

import allthings.iot.dos.dto.IotDeviceDetailDTO;
import allthings.iot.dos.dto.IotDeviceSimpleDTO;
import allthings.iot.dos.dto.query.IotProjectSimpleDTO;
import allthings.iot.dos.model.IotDevice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import allthings.iot.util.jpa.BaseRepository;

import java.util.Date;
import java.util.List;

/**
 * @author :  luhao
 * @FileName :  IotDeviceDao
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
public interface IotDeviceDao extends BaseRepository<IotDevice, Long> {

    @Query(" select deviceCode from IotDevice where iotProjectId=:iotProjectId and isEnabled=:isEnabled and " +
            "isDeleted=false ")
    List<String> getCountByIotProjectIdAndEnabled(@Param("iotProjectId") Long iotProjectId, @Param("isEnabled") boolean
            isEnabled);


    @Query(" select deviceCode from IotDevice where iotProjectId=:iotProjectId and " +
            "isDeleted=false ")
    List<String> getDeviceCodeByIotProjectId(@Param("iotProjectId") Long iotProjectId);

    @Query(" select concat(ip.protocolCode,'@',t.deviceCode) from IotDevice t ,IotDeviceType idt,IotProtocol ip where" +
            " t.iotDeviceTypeId =  idt.iotDeviceTypeId and idt.iotProtocolId = ip.iotProtocolId and t.isDeleted=false" +
            " " +
            "and t.iotProjectId =:iotProjectId and t.iotDeviceTypeId=:iotDeviceTypeId ")
    List<String> getIotDeviceByIotProjectId(@Param("iotProjectId") Long iotProjectId, @Param("iotDeviceTypeId") Long
            iotDeviceTypeId);

    @Query(" select iotDeviceId from IotDevice where iotProjectId=:iotProjectId and iotDeviceTypeId=:iotDeviceTypeId " +
            "and " +
            "isDeleted=false ")
    List<Long> getIotDeviceIdByIotProjectIdAndIotDeviceTypeId(@Param("iotProjectId") Long iotProjectId, @Param
            ("iotDeviceTypeId")
            Long iotDeviceTypeId);

    /**
     * 通过项目ID获取设备ID列表
     *
     * @param iotProjectId
     * @return
     */
    @Query(" select iotDeviceId from IotDevice where iotProjectId=:iotProjectId and isDeleted=false ")
    List<Long> getIotDeviceIdByIotProjectId(@Param("iotProjectId") Long iotProjectId);

    @Modifying
    @Query(" update IotDevice set isDeleted=true ,modifyOperatorId=:modifyOperatorId where iotDeviceId=:iotDeviceId and " +
            "isDeleted=false")
    Integer deleteIotDeviceByIotDeviceId(@Param("iotDeviceId") Long iotDeviceId, @Param("modifyOperatorId") Long modifyOperatorId);


    /**
     * 批量更新设备状态
     *
     * @param iotDeviceId
     * @param beforeStatus
     * @param afterStatus
     * @param operatorId
     * @return
     */
    @Modifying
    @Query("update IotDevice set isEnabled=:afterStatus , modifyOperatorId=:operatorId where iotDeviceId=:iotDeviceId" +
            " and " +
            "isEnabled=:beforeStatus and isDeleted=false ")
    Integer updateIotDeviceStatus(@Param("iotDeviceId") Long iotDeviceId, @Param("beforeStatus") boolean beforeStatus,
                                  @Param("afterStatus") boolean afterStatus, @Param("operatorId") Long operatorId);

    /**
     * 批量更新设备状态
     *
     * @param deviceCodes
     * @param beforeStatus
     * @param afterStatus
     * @return
     */
    @Modifying
    @Query("update IotDevice set isEnabled=:afterStatus where deviceCode in(:deviceCodes) and " +
            "isEnabled=:beforeStatus and iotProjectId=:iotProjectId and isDeleted=false ")
    Integer updateIotDeviceStatus(@Param("deviceCodes") List<String> deviceCodes,
                                  @Param("beforeStatus") boolean beforeStatus,
                                  @Param("afterStatus") boolean afterStatus, @Param("iotProjectId") Long iotProjectId);


    @Query(" from IotDevice where iotDeviceId=:iotDeviceId and isDeleted=false ")
    IotDevice getByIotDeviceId(@Param("iotDeviceId") Long iotDeviceId);


    /**
     * 通过设备自增ID获取deviceId
     *
     * @param iotDeviceIds
     * @return
     */
    @Query("select deviceId from IotDevice where iotDeviceId in (:iotDeviceIds) and isDeleted=false")
    List<String> getDeviceIdsByIotDeviceIds(@Param("iotDeviceIds") List<Long> iotDeviceIds);


    @Query(value =
            "select new allthings.iot.dos.dto.query.IotProjectSimpleDTO(it.iotProjectId,(select ip.projectName from" +
                    " IotProject " +
                    "ip where ip.iotProjectId = it.iotProjectId and ip.isDeleted=false )) " +
                    "from IotDevice it where it.iotProjectId in (:iotProjectIds) and it.isDeleted = false group by it" +
                    ".iotProjectId " +
                    "having count(it.iotProjectId) > 0 ")
    List<IotProjectSimpleDTO> getIotProjectDeviceByIotProjectId(@Param("iotProjectIds") List<Long> iotProjectIds);

    @Query(" select count(iotDeviceId) from IotDevice where inputDate >=:startDatetime and inputDate<:endDatetime and" +
            " isDeleted = false ")
    Long getAddedCountByRange(@Param("startDatetime") Date startDatetime, @Param("endDatetime") Date endDatetime);


    @Query(" select count(t.iotDeviceId) from IotDevice t,IotDeviceStatus id where t.iotDeviceId = id.iotDeviceId and" +
            " t.isDeleted = false and id.connected=true and id.isDeleted = false and t.iotProjectId=:iotProjectId")
    Long getOnlineCountByRange(@Param("iotProjectId") Long iotProjectId);

    @Query(" select t.deviceCode from IotDevice t where t.isDeleted = false ")
    Page<String> getTotalCountByRange(@Param("pageable") Pageable pageable);

    /**
     * 通过设备编码获取设备ID
     *
     * @param deviceCode
     * @return
     */
    @Query(" select t.iotDeviceId from IotDevice t where t.deviceCode=:deviceCode " +
            " and t.isDeleted=false ")
    List<Long> getIotDeviceIdByDeviceCode(@Param("deviceCode") String deviceCode);


    /**
     * 通过设备编码获取设备ID(已经注册的)
     *
     * @param deviceCode
     * @return
     */
    @Query(" select t.iotDeviceId from IotDevice t where t.deviceCode=:deviceCode " +
            " and t.isDeleted=false and t.registerStatus=true")
    List<Long> getRegisterIotDeviceIdByDeviceCode(@Param("deviceCode") String deviceCode);


    /**
     * 根据设备编码查询设备信息
     *
     * @param deviceCode
     * @return
     */
    @Query(" from IotDevice t where t.deviceCode=:deviceCode and t.isDeleted=false ")
    List<IotDevice> getIotDeviceByDeviceCode(@Param("deviceCode") String deviceCode);

    /**
     * 根据设备号查询设备信息
     *
     * @param deviceCode
     * @param iotProjectId
     * @return
     */
    @Query(" from IotDevice where deviceCode=:deviceCode and iotProjectId=:iotProjectId and isDeleted=false ")
    IotDevice getIotDeviceByDeviceCodeAndIotProjectId(@Param("deviceCode") String deviceCode,
                                                      @Param("iotProjectId") Long iotProjectId);


    /**
     * 获取设备在线数量
     *
     * @param iotDeviceIds
     * @return
     */
    @Query(" select count(t.iotDeviceId) from IotDevice t,IotDeviceStatus id where t.iotDeviceId in (:iotDeviceIds) " +
            "and t.iotDeviceId = id.iotDeviceId and t.isDeleted = false and id.connected=true ")
    Long getOnlineCount(@Param("iotDeviceIds") Long[] iotDeviceIds);

    /**
     * 通过设备编码获取设备主要信息
     *
     * @param deviceCode
     * @return
     */
    @Query("select new allthings.iot.dos.dto.IotDeviceSimpleDTO(it.iotDeviceId,it.deviceCode,ip.protocolCode) from " +
            "IotDevice it , IotDeviceType idt, IotProtocol ip " +
            "where it.iotDeviceTypeId = idt.iotDeviceTypeId and idt.iotProtocolId = ip.iotProtocolId and " +
            "it.deviceCode =:deviceCode and it.iotProjectId=:iotProjectId and it.isDeleted = false")
    IotDeviceSimpleDTO getIotDeviceSimpleByDeviceCode(@Param("deviceCode") String deviceCode,
                                                      @Param("iotProjectId") Long iotPorjectId);

    @Query(" select count(iotDeviceId) from IotDevice where registerDate >=:startDatetime and " +
            "registerDate<:endDatetime and" +
            "  iotProjectId=:iotProjectId and iotDeviceTypeId=:iotDeviceTypeId and isDeleted = false ")
    Long getCountByRange(@Param("iotProjectId") Long iotProjectId, @Param("iotDeviceTypeId") Long iotDeviceTypeId,
                         @Param("startDatetime") Date startDatetime, @Param("endDatetime") Date endDatetime);

    @Query(" SELECT count(it.iotDeviceId) FROM IotDevice it,IotDeviceTypeTag tyt WHERE it.inputDate >=:startDatetime " +
            " AND it.inputDate <:endDatetime AND it.iotDeviceTypeId = tyt.iotDeviceTypeId " +
            " AND tyt.isDeleted = FALSE and it.isDeleted = false and tyt.iotTagId = :iotTagId ")
    Long getCountByTagRange(@Param("iotTagId") Long iotTagId, @Param("startDatetime") Date startDatetime,
                            @Param("endDatetime") Date endDatetime);

    @Modifying
    @Query(" UPDATE IotDevice SET modifyOperatorId=:operatorId, deviceName=:deviceName, " +
            " firmwareModel=:firmwareModel,firmwareVersion=:firmwareVersion, iotDeviceTypeId=:iotDeviceTypeId, " +
            " iotProjectId=:iotProjectId,  latitude=:latitude, longitude=:longitude, mac=:mac, " +
            " bizCode=:bizCode, registerStatus=true, description=:description , extendProperties=:extendProperties, " +
            "agencyName=:agencyName" +
            " WHERE iotDeviceId=:iotDeviceId and isDeleted=false ")
    Integer updateIotDevice(@Param("iotDeviceId") Long iotDeviceId,
                            @Param("deviceName") String deviceName, @Param("bizCode") String bizCode,
                            @Param("iotProjectId") Long iotProjectId, @Param("iotDeviceTypeId") Long iotDeviceTypeId,
                            @Param("firmwareModel") String firmwareModel,
                            @Param("firmwareVersion") String firmwareVersion,
                            @Param("mac") String mac, @Param("latitude") Double latitude,
                            @Param("longitude") Double longitude, @Param("description") String description,
                            @Param("operatorId") Long operatorId, @Param("extendProperties") String extendProperties,
                            @Param("agencyName") String agencyName);

    @Modifying
    @Query(" UPDATE IotDevice SET createOperatorId=:operatorId, modifyOperatorId=:operatorId, deviceName=:deviceName," +
            " " +
            " firmwareModel=:firmwareModel,firmwareVersion=:firmwareVersion, iotDeviceTypeId=:iotDeviceTypeId, " +
            " iotProjectId=:iotProjectId,  latitude=:latitude, longitude=:longitude, mac=:mac, " +
            " bizCode=:bizCode, registerStatus=true, description=:description " +
            " WHERE iotDeviceId=:iotDeviceId and isDeleted=false ")
    Integer updateIotDeviceRegister(@Param("iotDeviceId") Long iotDeviceId,
                                    @Param("deviceName") String deviceName, @Param("bizCode") String bizCode,
                                    @Param("iotProjectId") Long iotProjectId,
                                    @Param("iotDeviceTypeId") Long iotDeviceTypeId,
                                    @Param("firmwareModel") String firmwareModel,
                                    @Param("firmwareVersion") String firmwareVersion,
                                    @Param("mac") String mac, @Param("latitude") Double latitude,
                                    @Param("longitude") Double longitude, @Param("description") String description,
                                    @Param("operatorId") Long operatorId);

    @Query("select count(iotDeviceId) from IotDevice where iotProjectId=:iotProjectId and " +
            "iotDeviceTypeId=:iotDeviceTypeId and isDeleted=false ")
    Long getCountByIotProjectIdAndIotDeviceTypeId(@Param("iotProjectId") Long iotProjectId,
                                                  @Param("iotDeviceTypeId") Long iotDeviceTypeId);

    @Query("select count(id.iotDeviceId) from IotDevice id, IotDeviceType idt where id.iotDeviceTypeId=idt" +
            ".iotDeviceTypeId " +
            " and id.iotProjectId=:iotProjectId and idt.deviceTypeCode=:deviceTypeCode and id.isDeleted=false ")
    Long getCountByIotProjectIdAndDeviceTypeCode(@Param("iotProjectId") Long iotProjectId,
                                                 @Param("deviceTypeCode") String deviceTypeCode);

    @Modifying
    @Query(" UPDATE IotDevice SET createOperatorId=:operatorId, modifyOperatorId=:operatorId, " +
            "iotDeviceTypeId=:iotDeviceTypeId, " +
            " iotProjectId=:iotProjectId,  registerStatus=true, registerDate = CURRENT_TIMESTAMP " +
            " WHERE iotDeviceId in (:iotDeviceIds) and isDeleted=false ")
    Integer updateIotDeviceRegisterBatch(@Param("iotDeviceIds") List<Long> iotDeviceIds,
                                         @Param("iotProjectId") Long iotProjectId,
                                         @Param("iotDeviceTypeId") Long iotDeviceTypeId,
                                         @Param("operatorId") Long operatorId);

    /**
     * 管理员查询接入设备数量
     *
     * @return
     */
    @Query("select count(iotDeviceId) from IotDevice where isDeleted=false and registerStatus=true ")
    Long getDeviceCountByAdmin();

    /**
     * 用户查询接入设备数量
     *
     * @param iotUserId
     * @return
     */
    @Query("select count(d.iotDeviceId) from IotDevice d, IotUserProject up where up.iotUserId=:iotUserId " +
            "and d.iotProjectId=up.iotProjectId and up.isDeleted=false  and d.isDeleted=false ")
    Long getDeviceCount(@Param("iotUserId") Long iotUserId);

    /**
     * 查询项目下设备总数量
     *
     * @param iotProjectId
     * @param isDeleted
     * @return
     */
    Long countByIotProjectIdAndIsDeleted(Long iotProjectId, Boolean isDeleted);

    /**
     * 判断设备是否已经注册
     *
     * @param iotDeviceId
     * @return
     */
    @Query("select count(d.iotDeviceId) from IotDevice d where d.isDeleted=false and d.registerStatus=true " +
            "and d.iotDeviceId=:iotDeviceId")
    Integer getIsRegister(@Param("iotDeviceId") Long iotDeviceId);

    /**
     * 通过deviceCode获取deviceId
     *
     * @param deviceCode
     * @return
     */
    @Query("select d.deviceId from IotDevice d where d.isDeleted=false and d.deviceCode=:deviceCode " +
            "and d.iotProjectId=:iotProjectId")
    String getDeviceIdByDeviceCode(@Param("deviceCode") String deviceCode,
                                   @Param("iotProjectId") Long iotProjectId);

    /**
     * 通过deviceCode获取deviceId
     *
     * @param deviceCode
     * @return
     */
    @Query("select d.deviceId from IotDevice d where d.isDeleted=false and d.deviceCode=:deviceCode ")
    List<String> getDeviceIdByDeviceCode(@Param("deviceCode") String deviceCode);

    /**
     * 通过deviceCode获取deviceId
     *
     * @param deviceCode
     * @return
     */
    @Query("select d.iotDeviceId from IotDevice d where d.isDeleted=false and d.deviceCode=:deviceCode " +
            "and d.iotProjectId=:iotProjectId")
    Long getIotDeviceIdByDeviceCode(@Param("deviceCode") String deviceCode,
                                    @Param("iotProjectId") Long iotProjectId);

    /**
     * 通过设备编码获取项目ID
     *
     * @param deviceCode
     * @return
     */
    @Query("select d.iotProjectId from IotDevice d where d.isDeleted=false and d.deviceCode=:deviceCode")
    List<Long> getIotProjectIdByDeviceCode(@Param("deviceCode") String deviceCode);

    /**
     * 通过deviceId获取项目ID
     *
     * @param deviceId
     * @return
     */
    @Query("select d.iotProjectId from IotDevice d where d.isDeleted=false and d.deviceId=:deviceId")
    List<Long> getIotProjectIdByDeviceId(@Param("deviceId") String deviceId);

    /**
     * 通过deviceId获取设备编码
     *
     * @param deviceId
     * @return
     */
    @Query(nativeQuery = true, value = "select d.device_code deviceCode from iot_dos_device d where d.is_deleted=false and d.device_id=:deviceId limit 1")
    List<String> getDeviceCodeByDeviceId(@Param("deviceId") String deviceId);

    /**
     * 通过设备编码获取设备ID
     *
     * @param deviceCode
     * @return
     */
    @Query("select d.iotDeviceId from IotDevice d where d.isDeleted=false and d.deviceCode=:deviceCode")
    List<Long> getIdByDeviceCode(@Param("deviceCode") String deviceCode);

    /**
     * 获取所有设备基础信息
     *
     * @return
     */
    @Query("select new allthings.iot.dos.dto.IotDeviceSimpleDTO(it.iotDeviceId, it.deviceCode, it.iotProjectId) " +
            " from IotDevice it " +
            " where it.isDeleted = false")
    List<IotDeviceSimpleDTO> getAll();

    /**
     * task查询deviceId
     *
     * @param pageable
     * @return
     */
    @Query(" select t.deviceId from IotDevice t where t.isDeleted = false and t.registerStatus=true ")
    Page<String> getDeviceTotalByRange(@Param("pageable") Pageable pageable);

    /**
     * 根据业务编码查询设备id
     *
     * @param bizCode 业务编码
     * @return
     */
    @Query("select d.deviceId from IotDevice d where d.bizCode =:bizCode and d.isDeleted = false")
    String getDeviceIdByBizCode(@Param("bizCode") String bizCode);

    /**
     * 查询设备详情
     *
     * @param deviceCode
     * @param iotProjectId
     * @return
     */
    @Query("select new allthings.iot.dos.dto.IotDeviceDetailDTO(d.iotDeviceId,d.iotDeviceTypeId,t.deviceTypeName," +
            "t.deviceTypeCode,d.iotProjectId,d.deviceCode,d.deviceName,d.isEnabled,d.registerStatus,d.agencyName) " +
            "from IotDevice d,IotDeviceType t where d.deviceCode=:deviceCode and d.iotDeviceTypeId=t.iotDeviceTypeId " +
            "and d.isDeleted=false and d.iotProjectId=:iotProjectId and t.isDeleted=false ")
    IotDeviceDetailDTO getDeviceDetail(@Param("deviceCode") String deviceCode,
                                       @Param("iotProjectId") Long iotProjectId);

    /**
     * 根据项目ID查询所有设备基本属性
     *
     * @param iotProjectId
     * @return
     */
    @Query("select new allthings.iot.dos.dto.IotDeviceDetailDTO(d.iotDeviceId,d.iotDeviceTypeId,t.deviceTypeName," +
            "t.deviceTypeCode,d.iotProjectId,d.deviceCode,d.deviceName,d.isEnabled,d.registerStatus,d.agencyName) " +
            "from IotDevice d,IotDeviceType t where d.iotProjectId=:iotProjectId and d.iotDeviceTypeId=t" +
            ".iotDeviceTypeId " +
            "and d.isDeleted=false and t.isDeleted=false ")
    List<IotDeviceDetailDTO> getDeviceListByProjectId(@Param("iotProjectId") Long iotProjectId);

    @Modifying
    @Query(" UPDATE IotDevice SET deviceId=:deviceId WHERE iotDeviceId=:iotDeviceId and isDeleted=false ")
    void updateDeviceId(@Param("iotDeviceId") Long iotDeviceId, @Param("deviceId") String deviceId);

    /**
     * 根据项目ID查询所有设备基本属性
     *
     * @param iotProjectId
     * @return
     */
    @Query("select new allthings.iot.dos.dto.IotDeviceDetailDTO(d.iotDeviceId,d.iotDeviceTypeId,t.deviceTypeName," +
            "t.deviceTypeCode,d.iotProjectId,d.deviceCode,d.deviceName,d.isEnabled,d.registerStatus,d.agencyName, p" +
            ".protocolCode) " +
            "from IotDevice d,IotDeviceType t, IotProtocol p where p.iotProtocolId = t.iotProtocolId " +
            "and d.iotProjectId=:iotProjectId and d.iotDeviceTypeId=t.iotDeviceTypeId and d.isDeleted=false and t" +
            ".isDeleted=false and p.protocolCode =:protocolCode ")
    List<IotDeviceDetailDTO> getIotDevicesByProjectIdAndProtocolCode(@Param("iotProjectId") Long iotProjectId,
                                                                     @Param("protocolCode") String protocolCode);
}

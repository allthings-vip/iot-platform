package allthings.iot.dos.dao;

import allthings.iot.dos.dto.IotDeviceTypeDTO;
import allthings.iot.dos.dto.query.IotDeviceTypeSimpleDTO;
import allthings.iot.dos.dto.query.IotProjectSimpleDTO;
import allthings.iot.dos.dto.query.IotProtocolQueryDTO;
import allthings.iot.dos.model.IotDeviceType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import allthings.iot.util.jpa.BaseRepository;

import java.util.List;

/**
 * @author :  luhao
 * @FileName :  IotDeviceTypeDao
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
public interface IotDeviceTypeDao extends BaseRepository<IotDeviceType, Long> {

    /**
     * 更新设备类型状态
     *
     * @param iotDeviceTypeId
     * @param operator
     * @param currentStatus
     * @param beforeStatus
     * @return
     */
    @Modifying
    @Query("update IotDeviceType set isEnabled=:currentStatus,operator=:operator where " +
            "iotDeviceTypeId=:iotDeviceTypeId and isEnabled=:beforeStatus and isDeleted=false ")
    Integer updateIotDeviceTypeStatus(@Param("iotDeviceTypeId") Long iotDeviceTypeId, @Param("operator") String
            operator, @Param("currentStatus") boolean currentStatus, @Param("beforeStatus") boolean beforeStatus);

    /**
     * 删除设备类型
     *
     * @param iotDeviceTypeId
     * @param iotProjectId
     * @param modifyOperatorId
     * @return
     */
    @Modifying
    @Query(" update IotDeviceType set isDeleted=true ,modifyOperatorId=:modifyOperatorId where " +
            "iotDeviceTypeId=:iotDeviceTypeId and iotProjectId=:iotProjectId and isDeleted=false ")
    Integer deleteByIotDeviceTypeId(@Param("iotDeviceTypeId") Long iotDeviceTypeId,
                                    @Param("iotProjectId") Long iotProjectId,
                                    @Param("modifyOperatorId") Long modifyOperatorId);

    /**
     * 通过编码删除设备类型
     *
     * @param deviceTypeCodes
     * @param iotProjectId
     * @return
     */
    @Modifying
    @Query(" update IotDeviceType set isDeleted=true where deviceTypeCode in(:deviceTypeCodes) " +
            " and iotProjectId=:iotProjectId and isDeleted=false ")
    Integer deleteIotDeviceTypeByCode(@Param("deviceTypeCodes") List<String> deviceTypeCodes,
                                      @Param("iotProjectId") Long iotProjectId);

    /**
     * 修改设备类型
     *
     * @param iotDeviceTypeId
     * @param iotProjectId
     * @param deviceTypeName
     * @param description
     * @param imageUrl
     * @param modifyOperatorId
     * @return
     */
    @Modifying
    @Query(" update IotDeviceType set deviceTypeName=:deviceTypeName,description=:description,imageUrl=:imageUrl," +
            "modifyOperatorId=:modifyOperatorId, manufacturer=:manufacturer where iotDeviceTypeId=:iotDeviceTypeId " +
            "and iotProjectId=:iotProjectId " +
            "and isDeleted=false ")
    Integer updateIotDeviceType(@Param("iotDeviceTypeId") Long iotDeviceTypeId,
                                @Param("iotProjectId") Long iotProjectId,
                                @Param("deviceTypeName") String deviceTypeName,
                                @Param("description") String description,
                                @Param("imageUrl") String imageUrl,
                                @Param("modifyOperatorId") Long modifyOperatorId,
                                @Param("manufacturer") String manufacturer);

    /**
     * 查询所有设备类型
     *
     * @param isDeleted
     * @return
     */
    @Query(" from IotDeviceType where isDeleted=:isDeleted ")
    List<IotDeviceType> getAllByIsDeleted(@Param("isDeleted") boolean isDeleted);

    /**
     * 用户统计所有设备类型数量
     *
     * @return
     */
    @Query(" select count(dt.iotDeviceTypeId) from IotDeviceType dt, IotUserProject up where up.iotUserId=:iotUserId" +
            " and up.iotProjectId=dt.iotProjectId and dt.isDeleted=false and up.isDeleted=false")
    Long getTotalCount(@Param("iotUserId") Long iotUserId);

    /**
     * 管理员统计设备类型数量
     *
     * @return
     */
    @Query(" select count(iotDeviceTypeId) from IotDeviceType where isDeleted=false ")
    Long getDeviceTypeCount();

    /**
     * 查询项目下设备类型数量
     *
     * @param iotProjectId
     * @param isDeleted
     * @return
     */
    Long countByIotProjectIdAndIsDeleted(Long iotProjectId, Boolean isDeleted);

    /**
     * 通过项目ID和设备类型编码查询
     *
     * @param deviceTypeCode
     * @param iotProjectId
     * @return
     */
    @Query(" select iotDeviceTypeId from IotDeviceType where deviceTypeCode=:deviceTypeCode and " +
            "iotProjectId=:iotProjectId and isDeleted=false ")
    Long getIotDeviceTypeIdByDeviceTypeCode(@Param("deviceTypeCode") String deviceTypeCode,
                                            @Param("iotProjectId") Long iotProjectId);


    /**
     * 通过项目ID和设备类型名称 查询
     *
     * @param deviceTypeName
     * @param iotProjectId
     * @return
     */
    @Query(" select iotDeviceTypeId from IotDeviceType where deviceTypeName=:deviceTypeName and " +
            "iotProjectId=:iotProjectId and isDeleted=false ")
    Long getIotDeviceTypeIdByDeviceTypeName(@Param("deviceTypeName") String deviceTypeName,
                                            @Param("iotProjectId") Long iotProjectId);


    /**
     * 通过项目ID和设备类型名称 查询
     *
     * @param deviceTypeName
     * @return
     */
    @Query(" select iotDeviceTypeId from IotDeviceType where deviceTypeName=:deviceTypeName and isDeleted=false " +
            "and isEnabled=true")
    Long getIotDeviceTypeIdByDeviceTypeName(@Param("deviceTypeName") String deviceTypeName);


    /**
     * 协议
     *
     * @param iotProtocolIds
     * @return
     */
    @Query(" select new com.allthings.iot.dos.dto.query.IotProtocolQueryDTO(idt.iotProtocolId,ip.protocolName,ip" +
            ".protocolCode)" +
            " from IotDeviceType idt,IotProtocol ip where ip .iotProtocolId = idt.iotProtocolId " +
            " and idt.iotProtocolId in (:iotProtocolIds) GROUP BY idt.iotProtocolId having count(idt.iotProtocolId) >" +
            " 1 ")
    List<IotProtocolQueryDTO> getIotDeviceTypeByIotProtocolId(@Param("iotProtocolIds") List<Long> iotProtocolIds);

    /**
     * 通过设备类型ID和项目ID查询
     *
     * @param iotDeviceTypeId
     * @param iotProjectId
     * @return
     */
    @Query(" from IotDeviceType where iotDeviceTypeId=:iotDeviceTypeId and iotProjectId=:iotProjectId and " +
            "isDeleted=false ")
    IotDeviceType getIotDeviceTypeByIotDeviceTypeId(@Param("iotDeviceTypeId") Long iotDeviceTypeId,
                                                    @Param("iotProjectId") Long iotProjectId);

    /**
     * 通过项目ID查询
     *
     * @param iotProjectIds
     * @return
     */
    @Query(value = "select new com.allthings.iot.dos.dto.query.IotProjectSimpleDTO(it.iotProjectId,(select ip.projectName" +
            " from IotProject ip where ip.iotProjectId = it.iotProjectId and ip.isDeleted=false )) " +
            "from IotDeviceType it where it.iotProjectId in (:iotProjectIds) and it.isDeleted = false group by it" +
            ".iotProjectId having count(it.iotProjectId) > 0 ")
    List<IotProjectSimpleDTO> getIotProjectDeviceTypeByIotProjectId(@Param("iotProjectIds") List<Long> iotProjectIds);

    /**
     * 通过项目ID查询类型
     *
     * @param iotProjectId
     * @return
     */
    @Query(value = "select new com.allthings.iot.dos.dto.IotDeviceTypeDTO(iotDeviceTypeId, deviceTypeName) FROM " +
            "IotDeviceType WHERE iotProjectId=:iotProjectId AND isDeleted=false")
    List<IotDeviceTypeDTO> getIotDeviceTypeByIotProjectId(@Param("iotProjectId") Long iotProjectId);

    /**
     * 查询设备类型列表
     *
     * @param iotProjectId
     * @param keywords
     * @return
     */
    @Query(value =
            " select new com.allthings.iot.dos.dto.query.IotDeviceTypeSimpleDTO(idt.description, idt.deviceTypeCode, idt" +
                    ".deviceTypeName, " +
                    " idt.imageUrl, idt.inputDate, ipt.projectName, ip.protocolName, ip.protocolCode, idt" +
                    ".manufacturer) " +
                    " from IotDeviceType idt, IotProtocol ip, IotProject ipt " +
                    " where idt.iotProtocolId = ip.iotProtocolId " +
                    " and ipt.iotProjectId=idt.iotProjectId and idt.iotProjectId=:iotProjectId " +
                    " and idt.deviceTypeName like :keywords and idt.isDeleted=false ")
    Page<IotDeviceTypeSimpleDTO> getOpenIotDeviceTypeList(@Param("iotProjectId") Long iotProjectId,
                                                          @Param("keywords") String keywords,
                                                          @Param("pageable") Pageable pageable);

    /**
     * 查询设备类型列表数量
     *
     * @param iotProjectId
     * @param keywords
     * @return
     */
    @Query(value = " select count(1) from iot_dos_devicetype idt, iot_dos_protocol ip " +
            " where idt.iot_dos_protocol_id = ip.iot_dos_protocol_id and idt.iot_dos_project_id=:iotProjectId " +
            " and idt.devicetype_name like :keywords and idt.is_deleted=false ", nativeQuery = true)
    Long getOpenIotDeviceTypeListCount(@Param("iotProjectId") Long iotProjectId,
                                       @Param("keywords") String keywords);

    @Query("from IotDeviceType where deviceTypeCode=:deviceTypeCode and iotProjectId=:iotProjectId and " +
            "isDeleted=false ")
    IotDeviceType getIotDeviceTypeByDeviceTypeCode(@Param("deviceTypeCode") String deviceTypeCode,
                                                   @Param("iotProjectId") Long iotProjectId);

    @Query("from IotDeviceType where deviceTypeCode in (:deviceTypeCodes) and iotProjectId=:iotProjectId and " +
            "isDeleted=false ")
    List<IotDeviceType> getIotDeviceTypeByDeviceTypeCodes(@Param("deviceTypeCodes") List<String> deviceTypeCodes,
                                                          @Param("iotProjectId") Long iotProjectId);
}

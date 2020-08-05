package allthings.iot.dos.dao;

import allthings.iot.dos.dto.query.IotProtocolQueryDTO;
import allthings.iot.dos.model.IotProtocol;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import allthings.iot.util.jpa.BaseRepository;

import java.util.List;

/**
 * @author :  luhao
 * @FileName :  IotProtocolDao
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
public interface IotProtocolDao extends BaseRepository<IotProtocol, Long> {
    /**
     * 查询全部协议
     *
     * @param isDeleted
     * @return
     */
    @Query(" select new allthings.iot.dos.dto.query.IotProtocolQueryDTO(iotProtocolId, protocolName, protocolCode) " +
            "from " +
            "IotProtocol where isDeleted=:isDeleted")
    List<IotProtocolQueryDTO> getAllByIsDeleted(@Param("isDeleted") boolean isDeleted);

    /**
     * 通过协议ID查询
     *
     * @param iotProtocolId
     * @return
     */
    @Query(" from IotProtocol  where iotProtocolId=:iotProtocolId and isDeleted=false ")
    IotProtocol getByIotProtocolId(@Param("iotProtocolId") Long iotProtocolId);

    @Modifying
    @Query(" update IotProtocol set protocolName=:protocolName,protocolCode=:protocolCode,description=:description," +
            "  serverDomain=:serverDomain,serverIp=:serverIp,serverPort=:serverPort," +
            "testServerDomain=:testServerDomain," +
            "testServerIp=:testServerIp,testServerPort=:testServerPort,modifyOperatorId=:modifyOperatorId where " +
            "iotProtocolId=:iotProtocolId " +
            "and " +
            "isDeleted = false ")
    Integer updateIotProtocol(@Param("iotProtocolId") Long iotProtocolId, @Param("protocolName") String protocolName,
                              @Param("protocolCode") String protocolCode, @Param("description") String description,
                              @Param("serverDomain") String serverDomain, @Param("serverIp") String serverIp,
                              @Param("serverPort") Integer serverPort, @Param("testServerDomain") String
                                      testServerDomain,
                              @Param("testServerIp") String testServerIp, @Param("testServerPort") Integer
                                      testServerPort, @Param("modifyOperatorId") Long modifyOperatorId);

    @Modifying
    @Query(" update IotProtocol set isDeleted = true ,modifyOperatorId=:modifyOperatorId where iotProtocolId=:iotProtocolId and " +
            "isDeleted = false")
    Integer deleteByIotProtocolId(@Param("iotProtocolId") Long iotProtocolId, @Param("modifyOperatorId") Long modifyOperatorId);

    /**
     * 通过设备ID获取协议ID
     *
     * @param iotDeviceId
     * @return
     */
    @Query("select p.iotProtocolId from IotProtocol p, IotDevice d, IotDeviceType dt " +
            "where d.iotDeviceId=:iotDeviceId and d.iotDeviceTypeId = dt.iotDeviceTypeId " +
            "and dt.iotProtocolId = p.iotProtocolId and p.isDeleted = false and d.isDeleted=false")
    Long getIdByIotDeviceId(@Param("iotDeviceId") Long iotDeviceId);

    /**
     * 通过设备类型编码获取协议ID
     *
     * @param deviceTypeCode
     * @return
     */
    @Query("select p.iotProtocolId from IotProtocol p, IotDeviceType dt " +
            "where dt.deviceTypeCode=:deviceTypeCode and dt.iotProtocolId = p.iotProtocolId " +
            " and p.isDeleted = false and dt.isDeleted=false and dt.iotProjectId=:iotProjectId ")
    Long getIdByIotDeviceTypeCode(@Param("deviceTypeCode") String deviceTypeCode,
                                  @Param("iotProjectId") Long iotProjectId);

    /**
     * 通过设备类型编码获取协议code
     *
     * @param deviceTypeCode
     * @return
     */
    @Query("select p.protocolCode from IotProtocol p, IotDeviceType dt " +
            "where dt.deviceTypeCode=:deviceTypeCode and dt.iotProtocolId = p.iotProtocolId " +
            " and p.isDeleted = false and dt.isDeleted=false and dt.iotProjectId=:iotProjectId ")
    String getCodeByIotDeviceTypeCode(@Param("deviceTypeCode") String deviceTypeCode,
                                      @Param("iotProjectId") Long iotProjectId);

    /**
     * 通过设备类型编码获取协议ID
     *
     * @param deviceTypeName
     * @return
     */
    @Query("select p.iotProtocolId from IotProtocol p, IotDeviceType dt " +
            "where dt.deviceTypeName=:deviceTypeName and dt.iotProtocolId = p.iotProtocolId " +
            " and p.isDeleted = false and dt.isDeleted=false ")
    Long getIdByIotDeviceTypeName(@Param("deviceTypeName") String deviceTypeName);

    /**
     * 通过协议编码获取ID
     *
     * @param protocolCode
     * @return
     */
    @Query(" select p.iotProtocolId from IotProtocol p where p.protocolCode =:protocolCode and p.isDeleted = false")
    Long getIdByCode(@Param("protocolCode") String protocolCode);

    /**
     * 通过设备类型ID获取协议
     *
     * @param iotDeviceTypeId
     * @return
     */
    @Query("FROM IotProtocol p, IotDeviceType dt " +
            "where dt.iotDeviceTypeId=:iotDeviceTypeId and dt.iotProtocolId = p.iotProtocolId " +
            " and p.isDeleted = false and dt.isDeleted=false ")
    IotProtocol getByDeviceTypeId(@Param("iotDeviceTypeId") Long iotDeviceTypeId);
}

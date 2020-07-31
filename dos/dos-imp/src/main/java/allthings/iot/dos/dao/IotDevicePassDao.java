package allthings.iot.dos.dao;

import allthings.iot.dos.dto.IotDevicePassDto;
import allthings.iot.dos.model.IotDevicePass;
import allthings.iot.util.jpa.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author tyf
 * @date 2019/02/26 19:18
 */
public interface IotDevicePassDao extends BaseRepository<IotDevicePass, Long> {

    /**
     * 根据设备唯一编码查询设备通道信息列表
     *
     * @param iotDeviceId
     * @return
     */
    @Query("select new allthings.iot.dos.dto.IotDevicePassDto(iotDevicePassId, passCode, passName, extendProperties) " +
            "from IotDevicePass where iotDeviceId = :iotDeviceId and isDeleted=false ")
    List<IotDevicePassDto> queryDevicePassByIotDeviceId(@Param("iotDeviceId") Long iotDeviceId);


    /**
     * 更新通道信息
     *
     * @param passCode
     * @param passName
     * @param extendProperties
     * @param iotDevicePassId
     * @param iotPassTypeId
     * @param passId
     * @return
     */
    @Modifying
    @Query("update IotDevicePass set passCode=:passCode, passName=:passName, extendProperties=:extendProperties, " +
            "iotDevicePassId=:iotPassTypeId, passId=:passId where iotDevicePassId=:iotDevicePassId")
    Integer updateDevicePass(@Param("passCode") String passCode, @Param("passName") String passName,
                             @Param("extendProperties") String extendProperties,
                             @Param("iotDevicePassId") Long iotDevicePassId,
                             @Param("iotPassTypeId") Long iotPassTypeId, @Param("passId") String passId);

    /**
     * @param iotDeviceId
     */
    @Modifying
    @Query("update IotDevicePass set isDeleted=true where iotDeviceId=:iotDeviceId and isDeleted=false")
    void deleteIotDevicePassByIotDeviceId(@Param("iotDeviceId") Long iotDeviceId);

    /**
     * 根据通道编码查询通道id
     *
     * @param passCode
     * @return
     */
    @Query("select passId from IotDevicePass where isDeleted = false and passCode=:passCode")
    String getPassIdByPassCode(@Param("passCode") String passCode);

    /**
     * @param iotDeviceId
     * @param passCode
     * @return
     */
    @Query("from IotDevicePass where iotDeviceId=:iotDeviceId and passCode=:passCode and isDeleted=false")
    IotDevicePass queryIotDevicePassByIotDeviceIdAndPassCode(@Param("iotDeviceId") Long iotDeviceId, @Param("passCode"
    ) String passCode);

    /**
     * 根据通道类型获取通道
     *
     * @param iotDeviceId
     * @param iotPassTypeId
     * @return
     */
    @Query("select new allthings.iot.dos.dto.IotDevicePassDto(idp.iotDevicePassId,idp.passCode,idp.passName," +
            "idp.extendProperties)  from IotDevicePass idp where idp.iotDeviceId=:iotDeviceId and " +
            "idp.iotPassTypeId=:iotPassTypeId and idp.isDeleted=false")
    List<IotDevicePassDto> queryIotDevicePassByIotDeviceIdAndIotPassTypeId(@Param("iotDeviceId") Long iotDeviceId, @Param(
            "iotPassTypeId") Long iotPassTypeId);

    /**
     * 通过通道ID和项目ID查询视频通道
     *
     * @param iotDevicePassId
     * @param iotProjectId
     * @param passTypeCode
     * @return
     */
    @Query("select new allthings.iot.dos.dto.IotDevicePassDto(idp.iotDevicePassId,idp.passCode,idp.passName,idp" +
            ".extendProperties) " +
            "from IotDevicePass idp,IotDevice id,IotPassType ip where idp.iotDevicePassId=:iotDevicePassId and " +
            "idp.iotPassTypeId=ip.iotPassTypeId and ip.passTypeCode=:passTypeCode and idp.iotDeviceId=id.iotDeviceId " +
            "and id.iotProjectId=:iotProjectId and idp.isDeleted=false and id.isDeleted=false and ip.isDeleted=false ")
    IotDevicePassDto queryDevicePassByProjectId(@Param("iotDevicePassId") Long iotDevicePassId,
                                                @Param("iotProjectId") Long iotProjectId,
                                                @Param("passTypeCode") String passTypeCode);

}

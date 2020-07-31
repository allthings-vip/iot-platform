package allthings.iot.dos.dao;

import allthings.iot.dos.model.IotPassType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import allthings.iot.util.jpa.BaseRepository;

import java.util.List;

/**
 * @author tyf
 * @date 2019/03/14 19:25
 */
public interface IotPassTypeDao extends BaseRepository<IotPassType, Long> {

    /**
     * 查询通道类型id
     *
     * @param passTypeCode
     * @param iotDeviceTypeId
     * @return
     */
    @Query("select iotPassTypeId from IotPassType where passTypeCode=:passTypeCode and isDeleted=false and " +
            "iotDeviceTypeId=:iotDeviceTypeId")
    Long getIotPassTypeIdByPassTypeCode(@Param("passTypeCode") String passTypeCode,
                                        @Param("iotDeviceTypeId") Long iotDeviceTypeId);


    /**
     * 查询所有的通道类型
     *
     * @param deleted
     * @return
     */
    @Query(" from IotPassType where isDeleted =:deleted and iotDeviceTypeId=:iotDeviceTypeId ")
    List<IotPassType> getAllByIotDeviceType(@Param("iotDeviceTypeId") Long iotDeviceTypeId,
                                      @Param("deleted") Boolean deleted);

}

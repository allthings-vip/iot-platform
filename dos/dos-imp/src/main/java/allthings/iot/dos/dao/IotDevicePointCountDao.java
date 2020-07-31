package allthings.iot.dos.dao;

import allthings.iot.dos.model.offline.IotDevicePointCount;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import allthings.iot.util.jpa.BaseRepository;

/**
 * @description:
 * @author: fengchangxin
 * @create: 2018-12-27 14:11
 */

public interface IotDevicePointCountDao extends BaseRepository<IotDevicePointCount, Long> {
    /**
     * 通过时间和deviceId查询
     *
     * @param deviceId
     * @return
     */
    @Query("from IotDevicePointCount where deviceId=:deviceId and isDeleted = false ")
    IotDevicePointCount getDevicePointCount(@Param("deviceId") String deviceId);

    /**
     * 查询设备所有数据
     *
     * @param deviceId
     * @return
     */
    @Query("from IotDevicePointCount where deviceId=:deviceId and isDeleted = false ")
    IotDevicePointCount getDevicePointList(@Param("deviceId") String deviceId);
}

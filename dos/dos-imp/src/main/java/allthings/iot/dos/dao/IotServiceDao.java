package allthings.iot.dos.dao;

import allthings.iot.dos.model.IotService;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import allthings.iot.util.jpa.BaseRepository;

/**
 * @author txw
 * @date 2019/03/12 10:42
 */
public interface IotServiceDao extends BaseRepository<IotService, Long> {

    /**
     * 通过ip获取服务信息
     *
     * @param ip
     * @param port
     * @return
     */
    @Query("FROM IotService s WHERE s.ip =:ip AND s.port =:port AND s.isDeleted = false")
    IotService getIotServiceByIPAndPort(@Param("ip") String ip, @Param("port") String port);

    /**
     * 删除服务信息
     *
     * @param iotServiceId
     * @return
     */
    @Modifying
    @Query("UPDATE IotService s SET s.isDeleted = true WHERE s.iotServiceId =:iotServiceId AND s.isDeleted = false ")
    Integer deleteByIotServiceId(@Param("iotServiceId") Long iotServiceId);
}

package allthings.iot.dos.dao;

import allthings.iot.dos.model.IotLoggerType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import allthings.iot.util.jpa.BaseRepository;

import java.util.List;

/**
 * @author tyf
 * @date 2019/03/12 15:05
 */
public interface IotLoggerTypeDao extends BaseRepository<IotLoggerType, Long> {

    /**
     * 根据日志类型编码查询日志类型id
     *
     * @param loggerTypeCode
     * @return
     */
    @Query("select iotLoggerTypeId from IotLoggerType where isDeleted = false and loggerTypeCode =:loggerTypeCode")
    Long getIotLoggerTypeIdByLoggerTypeCode(@Param("loggerTypeCode") String loggerTypeCode);

    /**
     * 查询日志类型
     *
     * @return
     */
    @Query("FROM IotLoggerType WHERE isDeleted = false ")
    List<IotLoggerType> getSystemLoggerType();
}

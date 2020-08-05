package allthings.iot.dos.dao;

import allthings.iot.dos.model.IotEventPushUrl;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import allthings.iot.util.jpa.BaseRepository;

/**
 * @author tyf
 * @date 2019/03/08 9:09
 */
public interface IotEventPushUrlDao extends BaseRepository<IotEventPushUrl, Long> {

    /**
     * 根据项目id查询事件推送地址
     *
     * @param iotProjectId
     * @return
     */
    @Query("from IotEventPushUrl where isDeleted=false and iotProjectId=:iotProjectId")
    IotEventPushUrl getPushUrlByIotProjectId(@Param("iotProjectId") Long iotProjectId);

    /**
     * 更新事件推送地址
     *
     * @param iotProjectId
     * @param pushUrl
     * @param modifyOperatorId
     * @return
     */
    @Modifying
    @Query("update IotEventPushUrl set pushUrl=:pushUrl, modifyOperatorId=:modifyOperatorId where isDeleted=false and" +
            " iotProjectId=:iotProjectId")
    Integer updateEventPushUrl(@Param("iotProjectId") Long iotProjectId, @Param("pushUrl") String pushUrl, @Param(
            "modifyOperatorId") long modifyOperatorId);

}

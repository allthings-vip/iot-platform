package allthings.iot.dos.api;

import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.dto.IotEventPushUrlDto;

/**
 * @author tyf
 * @date 2019/03/08 9:23
 */
public interface IotEventPushUrlService {

    /**
     * 保存事件推送地址
     *
     * @param iotEventPushUrlDto
     * @return
     */
    ResultDTO<Long> saveIotEventPushUrl(IotEventPushUrlDto iotEventPushUrlDto);


    /**
     * 根据项目id查询事件推送地址
     *
     * @param iotEventPushUrlDto
     * @return
     */
    ResultDTO<IotEventPushUrlDto> getEventPushUrlByIotProjectId(IotEventPushUrlDto iotEventPushUrlDto);


    /**
     * 更新事件推送地址
     *
     * @param iotEventPushUrlDto
     * @return
     */
    ResultDTO<Long> updateIotEventPushUrl(IotEventPushUrlDto iotEventPushUrlDto);

}

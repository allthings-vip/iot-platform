package allthings.iot.des.manager.impl;

import com.alibaba.fastjson.JSON;
import allthings.iot.des.dto.query.IotDesEventData;
import allthings.iot.des.dto.query.IotEventPushDto;
import allthings.iot.des.http.HttpSyncService;
import allthings.iot.des.manager.IotDesDeviceEventPush;
import org.springframework.stereotype.Service;
import allthings.iot.common.msg.DeviceEventMsg;

import java.util.List;
import java.util.Map;

/**
 * @author tyf
 * @date 2019/03/07 15:02
 */
@Service("iotDesDeviceEventPublish")
public class IotDesDeviceEventPushImpl implements IotDesDeviceEventPush {
    @Override
    public void publishDeviceEvent(String url, DeviceEventMsg msg) throws Exception {
        Map<String, Object> params = msg.getParams();
        IotEventPushDto publishDto = new IotEventPushDto();
        publishDto.setDeviceCode(msg.getSourceDeviceId());
        String eventData = String.valueOf(params.get("eventData"));
        List<IotDesEventData> eventDataList = JSON.parseArray(eventData, IotDesEventData.class);
        publishDto.setEventData(eventDataList);
        publishDto.setEventDescription(msg.getEventDescription());
        publishDto.setEventSource(String.valueOf(params.get("eventSource")));
        publishDto.setEventTime(Long.parseLong(String.valueOf(params.get("eventTime"))));
        publishDto.setVideo(params.get("video") == null ? null : String.valueOf(params.get("video")));
        publishDto.setImage(params.get("image") == null ? null : String.valueOf(params.get("image")));

        String param = JSON.toJSONString(publishDto);
        HttpSyncService.requestPost(url, param, msg);
    }
}

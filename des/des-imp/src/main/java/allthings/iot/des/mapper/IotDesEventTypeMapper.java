package allthings.iot.des.mapper;

import allthings.iot.des.dto.IotDesEventTypeDto;
import allthings.iot.des.model.IotDesEventType;
import allthings.iot.des.util.IotDesMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author tyf
 * @date 2019/03/05 14:47
 */
public interface IotDesEventTypeMapper extends IotDesMapper<IotDesEventType> {

    /**
     * 根据事件类型编码查询事件类型
     *
     * @param eventTypeCode
     * @return
     */
    Long getEventTypeByEventTypeCode(@Param("eventTypeCode") String eventTypeCode);

    /**
     * 根据事件类型编码查询事件类型
     *
     * @param eventTypeCode
     * @return
     */
    IotDesEventTypeDto getIotEventTypeByEventTypeCode(@Param("eventTypeCode") String eventTypeCode);

}

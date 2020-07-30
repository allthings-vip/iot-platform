package allthings.iot.des.mapper;

import allthings.iot.des.dto.query.IotDesDeviceEventListQueryDto;
import allthings.iot.des.dto.query.IotDesEventDetailDto;
import allthings.iot.des.model.IotDesDeviceEvent;
import allthings.iot.des.util.IotDesMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author tyf
 * @date 2019/03/04 11:04
 */
public interface IotDesDeviceEventMapper extends IotDesMapper<IotDesDeviceEvent> {

    /**
     * 保存设备事件
     *
     * @param iotDesDeviceEventModel
     * @return
     */
    Long saveIotDesDeviceEvent(IotDesDeviceEvent iotDesDeviceEventModel);

    /**
     * 根据设备事件id查询设备事件详情
     *
     * @param iotDesDeviceEventId
     * @return
     */
    IotDesEventDetailDto queryEventDetailByDeviceEventId(@Param("iotDesDeviceEventId") Long iotDesDeviceEventId);

    /**
     * 根据iotDeviceId查询设备事件列表
     *
     * @param iotDeviceId
     * @param startTime
     * @param endTime
     * @return
     */
    List<IotDesDeviceEventListQueryDto> getEventListByIotDeviceId(@Param("iotDeviceId") Long iotDeviceId,
                                                                  @Param("startTime") String startTime,
                                                                  @Param("endTime") String endTime);

}

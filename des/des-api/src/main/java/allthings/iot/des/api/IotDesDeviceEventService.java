package allthings.iot.des.api;

import allthings.iot.des.dto.IotDesEventTypeDto;
import allthings.iot.des.dto.query.IotDesDeviceEventListQueryDto;
import allthings.iot.des.dto.query.IotDesDeviceEventSaveDto;
import allthings.iot.des.dto.query.IotDesEventDetailDto;
import allthings.iot.common.dto.PageResult;
import allthings.iot.common.dto.Result;
import allthings.iot.common.msg.DeviceEventMsg;

/**
 * @author tyf
 * @date 2019/03/05 13:48
 */
public interface IotDesDeviceEventService {

    /**
     * 保存设备事件
     *
     * @param iotDesDeviceEventSaveDto
     */
    void saveIotDesDeviceEvent(IotDesDeviceEventSaveDto iotDesDeviceEventSaveDto);

    /**
     * 根据iotDeviceId查询设备事件列表
     *
     * @param iotDeviceId
     * @param startTime
     * @param endTime
     * @param pageIndex
     * @param pageSize
     * @return
     */
    Result<PageResult<IotDesDeviceEventListQueryDto>> getDeviceEventListByIotDeviceId(Long iotDeviceId, Long startTime, Long endTime, Integer pageIndex, Integer pageSize);

    /**
     * 根据设备事件id查询设备事件详情
     *
     * @param iotDesDeviceEventId
     * @return
     */
    Result<IotDesEventDetailDto> getEventDetailByIotDesDeviceEventId(Long iotDesDeviceEventId);

    /**
     * 推送设备事件
     *
     * @param msg
     * @throws Exception
     */
    void pushDeviceEvent(DeviceEventMsg msg) throws Exception;

    /**
     * 根据事件类型编码查询事件类型信息
     *
     * @param eventTypeCode
     * @return
     */
    Result<IotDesEventTypeDto> getEventTypeByEventTypeCode(String eventTypeCode);


}

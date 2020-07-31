package allthings.iot.des.client;

import allthings.iot.common.dto.PageResult;
import allthings.iot.common.dto.Result;
import allthings.iot.des.client.constant.Constant;
import allthings.iot.des.dto.IotDesEventTypeDto;
import allthings.iot.des.dto.query.IotDesDeviceEventListQueryDto;
import allthings.iot.des.dto.query.IotDesDeviceEventSaveDto;
import allthings.iot.des.dto.query.IotDesEventDetailDto;
import org.springframework.stereotype.Component;

/**
 * @author tyf
 * @date 2019/05/15 14:40:28
 */
@Component
public class IotDesFallCallback implements IotDesFeignClient {
    @Override
    public void saveIotDesDeviceEvent(IotDesDeviceEventSaveDto iotDesDeviceEventSaveDto) {

    }

    @Override
    public Result<PageResult<IotDesDeviceEventListQueryDto>> getDeviceEventListByIotDeviceId(Long iotDeviceId, Long startTime, Long endTime, Integer pageIndex, Integer pageSize) {
        return Constant.FALL_CULL_BACK;
    }

    @Override
    public Result<IotDesEventDetailDto> getEventDetailByIotDesDeviceEventId(Long iotDesDeviceEventId) {
        return Constant.FALL_CULL_BACK;
    }

    @Override
    public Result<IotDesEventTypeDto> getIotEventTypeByEventTypeCode(String eventTypeCode) {
        return Constant.FALL_CULL_BACK;
    }
}

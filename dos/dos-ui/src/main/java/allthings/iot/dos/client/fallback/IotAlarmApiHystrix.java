package allthings.iot.dos.client.fallback;

import allthings.iot.dos.client.api.IotAlarmApi;
import allthings.iot.dos.client.constant.Constant;
import allthings.iot.dos.dto.query.IotDeviceAlarmDTO;
import org.springframework.stereotype.Component;
import allthings.iot.common.dto.QueryResult;
import allthings.iot.common.dto.ResultDTO;

import java.util.List;

/**
 * @author luhao
 * @date 2020/2/17 14:42
 */
@Component
public class IotAlarmApiHystrix implements IotAlarmApi {

    @Override
    public ResultDTO<QueryResult<IotDeviceAlarmDTO>> getDeviceAlarmsByDeviceId(String deviceId,
                                                                               List<String> alarmCodes,
                                                                               long beginTime, long endTime,
                                                                               int pageIndex, int pageSize) {
        return Constant.FALL_CULL_BACK;
    }

    @Override
    public ResultDTO<QueryResult<IotDeviceAlarmDTO>> getRealAlarmList(String deviceCode) {
        return Constant.FALL_CULL_BACK;
    }
}

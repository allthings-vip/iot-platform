package allthings.iot.dos.controller;

import allthings.iot.dos.api.IotLoggerBiz;
import allthings.iot.dos.client.api.IotLoggerApi;
import allthings.iot.dos.dto.IotLoggerTypeDto;
import allthings.iot.dos.dto.query.IotDeviceLoggerListDto;
import allthings.iot.dos.dto.query.IotDeviceLoggerQueryListDto;
import allthings.iot.dos.dto.query.IotLogDTO;
import allthings.iot.dos.dto.query.IotSystemLoggerQueryListDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tf56.iot.common.dto.PageResult;
import tf56.iot.common.dto.ResultDTO;

import java.util.List;

/**
 * @author tyf
 * @date 2019/07/05 10:25:15
 */
@RestController
public class IotLoggerController extends IotDosBaseController implements IotLoggerApi {

    @Autowired
    private IotLoggerBiz loggerBiz;

    @Override
    public ResultDTO<Object> saveIotDeviceLogger(@RequestBody IotLogDTO logDTO) {
        loggerBiz.saveIotDeviceLogger(logDTO);
        return ResultDTO.newSuccess();
    }

    @Override
    public ResultDTO<PageResult<IotDeviceLoggerListDto>> getDeviceLoggerList(@RequestBody IotDeviceLoggerQueryListDto iotDeviceLoggerQueryListDto) {
        return getResult(loggerBiz.getDeviceLoggerList(iotDeviceLoggerQueryListDto));
    }

    @Override
    public ResultDTO<PageResult<IotDeviceLoggerListDto>> getSystemLoggerList(@RequestBody IotSystemLoggerQueryListDto iotSystemLoggerQueryListDto) {
        return getResult(loggerBiz.getSystemLoggerList(iotSystemLoggerQueryListDto));
    }

    @Override
    public ResultDTO<Long> getLoggerTypeIdByLoggerTypeCode(@RequestParam("loggerTypeCode") String loggerTypeCode) {
        return getResult(loggerBiz.getLoggerTypeIdByLoggerTypeCode(loggerTypeCode));
    }

    @Override
    public ResultDTO<List<IotLoggerTypeDto>> getSystemLoggerType() {
        return getResult(loggerBiz.getSystemLoggerType());
    }


}

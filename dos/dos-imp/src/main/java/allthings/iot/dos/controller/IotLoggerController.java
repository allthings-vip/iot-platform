package allthings.iot.dos.controller;

import allthings.iot.common.dto.PageResult;
import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.api.IotLoggerService;
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

import java.util.List;

/**
 * @author tyf
 * @date 2019/07/05 10:25:15
 */
@RestController
public class IotLoggerController implements IotLoggerApi {

    @Autowired
    private IotLoggerService loggerBiz;

    @Override
    public ResultDTO<Object> saveIotDeviceLogger(@RequestBody IotLogDTO logDTO) {
        loggerBiz.saveIotDeviceLogger(logDTO);
        return ResultDTO.newSuccess();
    }

    @Override
    public ResultDTO<PageResult<IotDeviceLoggerListDto>> getDeviceLoggerList(@RequestBody IotDeviceLoggerQueryListDto iotDeviceLoggerQueryListDto) {
        return loggerBiz.queryLoggerList(iotDeviceLoggerQueryListDto);
    }

    @Override
    public ResultDTO<PageResult<IotDeviceLoggerListDto>> getSystemLoggerList(@RequestBody IotSystemLoggerQueryListDto iotSystemLoggerQueryListDto) {
        return loggerBiz.querySystemLoggerList(iotSystemLoggerQueryListDto);
    }

    @Override
    public ResultDTO<Long> getLoggerTypeIdByLoggerTypeCode(@RequestParam("loggerTypeCode") String loggerTypeCode) {
        return loggerBiz.getLoggerTypeIdByLoggerTypeCode(loggerTypeCode);
    }

    @Override
    public ResultDTO<List<IotLoggerTypeDto>> getSystemLoggerType() {
        return loggerBiz.getSystemLoggerType();
    }


}

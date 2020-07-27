package allthings.iot.dms.controller;

import allthings.iot.common.dto.QueryResult;
import allthings.iot.common.dto.Result;
import allthings.iot.dms.dto.MsgLogDto;
import allthings.iot.dms.service.MsgLogServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tyf
 * @date 2019/07/01 17:47:44
 */
@RestController
@RequestMapping("/deviceManagerService/dms")
public class MsgLogController {

    @Autowired
    private MsgLogServiceImpl msgLogService;

    @GetMapping("/getMsgLogs")
    public Result<QueryResult<MsgLogDto>> getMsgLogs(String deviceType, String deviceId, String msgType, long beginTime, long endTime,
                                                     int pageIndex, int pageSize) {
        return Result.newSuccess(msgLogService.getMsgLogs(deviceType, deviceId, msgType, beginTime, endTime, pageIndex, pageSize));
    }

}

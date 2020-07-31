package allthings.iot.dos.controller;

import allthings.iot.common.dto.PageResult;
import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.api.IotLogService;
import allthings.iot.dos.client.api.IotLogApi;
import allthings.iot.dos.dto.query.IotLogDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tyf
 * @date 2019/07/04 16:33:04
 */
@RestController
public class IotLogController extends IotDosBaseController implements IotLogApi {

    @Autowired
    private IotLogService logBiz;

    @Override
    public ResultDTO<PageResult<IotLogDTO>> getMsgLogs(@RequestParam("deviceCode") String deviceCode,
                                                       @RequestParam("msgType") String msgType,
                                                       @RequestParam("beginDatetime") long beginDatetime,
                                                       @RequestParam("endDatetime") long endDatetime,
                                                       @RequestParam("pageIndex") int pageIndex,
                                                       @RequestParam("pageSize") int pageSize,
                                                       @RequestParam("iotProjectId") Long iotProjectId) {
        return logBiz.getMsgLogs(deviceCode, msgType, beginDatetime, endDatetime, pageIndex, pageSize,
                iotProjectId, 1L, "admin");

    }

}

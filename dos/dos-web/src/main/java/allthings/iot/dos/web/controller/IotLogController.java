package allthings.iot.dos.web.controller;

import allthings.iot.common.dto.PageResult;
import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.client.api.IotLogApi;
import allthings.iot.dos.client.api.IotLoggerApi;
import allthings.iot.dos.dto.IotLoggerTypeDto;
import allthings.iot.dos.dto.query.IotDeviceLoggerListDto;
import allthings.iot.dos.dto.query.IotLogDTO;
import allthings.iot.dos.dto.query.IotSystemLoggerQueryListDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author :  luhao
 * @FileName :  IotLogController
 * @CreateDate :  2018-5-30
 * @Description :
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
@RestController
public class IotLogController extends BaseController {
    @Autowired
    private IotLogApi iotLogApi;

    @Autowired
    private IotLoggerApi iotLoggerApi;

    /**
     * @param deviceCode
     * @param startDatetime
     * @param endDatetime
     * @param pageIndex
     * @param pageSize
     * @param iotProjectId
     * @return
     */
    @GetMapping("/logs/list")
    public ResultDTO<PageResult<IotLogDTO>> getLogs(@RequestParam("deviceCode") String deviceCode,
                                                    @RequestParam("startDatetime") Long startDatetime,
                                                    @RequestParam("endDatetime") Long endDatetime,
                                                    @RequestParam("pageIndex") Integer pageIndex,
                                                    @RequestParam("pageSize") Integer pageSize,
                                                    @RequestParam("iotProjectId") Long iotProjectId) {
        return iotLogApi.getMsgLogs(deviceCode, null, startDatetime, endDatetime, pageIndex, pageSize,
                iotProjectId, 1L, "admin");
    }

    @GetMapping("/systemlogs/list")
    public ResultDTO<PageResult<IotDeviceLoggerListDto>> getSystemLogs(@RequestParam("startDatetime") Long startDatetime,
                                                                       @RequestParam("endDatetime") Long endDatetime,
                                                                       @RequestParam("pageIndex") Integer pageIndex,
                                                                       @RequestParam("pageSize") Integer pageSize,
                                                                       @RequestParam(value = "iotProjectId",
                                                                               required = false) Long iotProjectId,
                                                                       @RequestParam(value = "loggertype", required =
                                                                               false) String type) {
        IotSystemLoggerQueryListDto iotSystemLoggerQueryListDto = new IotSystemLoggerQueryListDto();
        iotSystemLoggerQueryListDto.setEndTime(endDatetime);
        iotSystemLoggerQueryListDto.setIotProjectId(iotProjectId);
        iotSystemLoggerQueryListDto.setLoggerTypeCode(type);
        iotSystemLoggerQueryListDto.setStartTime(startDatetime);
        iotSystemLoggerQueryListDto.setPageIndex(pageIndex);
        iotSystemLoggerQueryListDto.setPageSize(pageSize);
        setDto(iotSystemLoggerQueryListDto);

        return iotLoggerApi.getSystemLoggerList(iotSystemLoggerQueryListDto);

    }

    @GetMapping("/system/loggertype")
    public ResultDTO<List<IotLoggerTypeDto>> getSystemLoggerType() {
        return iotLoggerApi.getSystemLoggerType();
    }

}

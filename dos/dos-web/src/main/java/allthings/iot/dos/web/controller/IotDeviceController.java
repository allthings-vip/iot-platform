package allthings.iot.dos.web.controller;

import allthings.iot.common.dto.PageResult;
import allthings.iot.common.dto.QueryResult;
import allthings.iot.common.dto.ResultDTO;
import allthings.iot.des.dto.query.IotDesDeviceEventListQueryDto;
import allthings.iot.des.dto.query.IotDesEventDetailDto;
import allthings.iot.dos.client.api.IotDeviceApi;
import allthings.iot.dos.client.api.IotDevicePassApi;
import allthings.iot.dos.client.api.IotDeviceTypeApi;
import allthings.iot.dos.client.api.IotLoggerApi;
import allthings.iot.dos.constant.Constants;
import allthings.iot.dos.constant.ErrorCode;
import allthings.iot.dos.dto.IotDeviceDTO;
import allthings.iot.dos.dto.IotDeviceErrorMsgDTO;
import allthings.iot.dos.dto.IotDevicePassDto;
import allthings.iot.dos.dto.IotDeviceRegisterBatchDTO;
import allthings.iot.dos.dto.IotDeviceSaveBatchDTO;
import allthings.iot.dos.dto.IotDeviceStatusBatchQueryDTO;
import allthings.iot.dos.dto.IotDeviceStatusDTO;
import allthings.iot.dos.dto.IotVisResultDTO;
import allthings.iot.dos.dto.query.IotDeviceCountQueryDTO;
import allthings.iot.dos.dto.query.IotDeviceCountTitleDTO;
import allthings.iot.dos.dto.query.IotDeviceDetailQueryDTO;
import allthings.iot.dos.dto.query.IotDeviceEventDetailQueryDto;
import allthings.iot.dos.dto.query.IotDeviceHistoryQueryDTO;
import allthings.iot.dos.dto.query.IotDeviceLocationQueryDTO;
import allthings.iot.dos.dto.query.IotDeviceLoggerListDto;
import allthings.iot.dos.dto.query.IotDeviceLoggerQueryListDto;
import allthings.iot.dos.dto.query.IotDeviceMonitorQueryListDTO;
import allthings.iot.dos.dto.query.IotDevicePassQueryDTO;
import allthings.iot.dos.dto.query.IotDeviceQueryListDTO;
import allthings.iot.dos.dto.query.IotDeviceQueryByCodeDTO;
import allthings.iot.dos.dto.query.IotDeviceQueryDTO;
import allthings.iot.dos.dto.query.IotDeviceStatusCountDTO;
import allthings.iot.dos.dto.query.IotDeviceStatusQueryDTO;
import allthings.iot.dos.dto.query.IotDosDeviceEventQueryListDto;
import allthings.iot.dos.dto.query.IotDosQueryDTO;
import allthings.iot.dos.dto.query.IotIovProtocolCodeQueryDto;
import allthings.iot.dos.exception.IllegalTemplateException;
import allthings.iot.dos.web.excel.IotDeviceExcelImport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author :  luhao
 * @FileName :  IotDeviceController
 * @CreateDate :  2018-5-7
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
public class IotDeviceController extends BaseController {
    @Autowired
    private IotDeviceApi iotDeviceApi;

    @Autowired
    private IotDeviceTypeApi iotDeviceTypeApi;

    @Autowired
    private IotDevicePassApi iotDevicePassApi;

    @Autowired
    private IotLoggerApi iotLoggerApi;

    @Autowired
    private IotDeviceExcelImport iotDeviceExcelImport;


    /**
     * 新增设备
     *
     * @param iotDeviceDTO
     * @return
     */
    @PostMapping(value = "/devices/save")
    public ResultDTO<Long> saveIotDevice(@RequestBody IotDeviceDTO iotDeviceDTO) {
        setDto(iotDeviceDTO);
        return iotDeviceApi.saveIotDevice(iotDeviceDTO);
    }

    /**
     * 修改设备
     *
     * @param iotDeviceDTO
     * @return
     */
    @PostMapping(value = "/devices/update")
    public ResultDTO<Long> updateIotDevice(@RequestBody IotDeviceDTO iotDeviceDTO) {
        setDto(iotDeviceDTO);
        return iotDeviceApi.updateIotDevice(iotDeviceDTO);
    }

    /**
     * 获取设备列表
     *
     * @param iotDeviceTypeId
     * @param iotProjectId
     * @param iotTagId
     * @param keywords
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @GetMapping(value = "/devices/list")
    public ResultDTO<PageResult<IotDeviceQueryDTO>> getIotDeviceList(@RequestParam(value = "connected", required =
            false) Boolean connected,
                                                                     @RequestParam(value = "iotDeviceTypeId",
                                                                             required = false) Long iotDeviceTypeId,
                                                                     @RequestParam(value = "iotProjectId") Long iotProjectId,
                                                                     @RequestParam(value = "iotTagId", required =
                                                                             false) Long iotTagId,
                                                                     @RequestParam(value = "keywords", required =
                                                                             false) String keywords,
                                                                     @RequestParam("pageIndex") Integer pageIndex,
                                                                     @RequestParam("pageSize") Integer pageSize) {
        IotDeviceQueryListDTO iotDeviceQueryListDTO = new IotDeviceQueryListDTO();
        iotDeviceQueryListDTO.setConnected(connected);
        iotDeviceQueryListDTO.setIotDeviceTypeId(iotDeviceTypeId);
        iotDeviceQueryListDTO.setIotProjectId(iotProjectId);
        iotDeviceQueryListDTO.setIotTagId(iotTagId);
        iotDeviceQueryListDTO.setKeywords(keywords);
        iotDeviceQueryListDTO.setPageIndex(pageIndex);
        iotDeviceQueryListDTO.setPageSize(pageSize);
        setDto(iotDeviceQueryListDTO);
        return iotDeviceApi.getIotDeviceByIotProjectId(iotDeviceQueryListDTO);
    }

    /**
     * 批量更新设备状态
     *
     * @param iotDeviceStatusDTO
     * @return
     */
    @PostMapping(value = "/devices/status/update")
    public ResultDTO<Integer> updateIotDeviceStatus(@RequestBody IotDeviceStatusDTO iotDeviceStatusDTO) {
        setDto(iotDeviceStatusDTO);
        return iotDeviceApi.updateIotDeviceStatus(iotDeviceStatusDTO);
    }


    /**
     * 获取项目内设备各状态数量
     *
     * @param iotProjectId
     * @return
     */
    @GetMapping(value = "/devices/counts/status")
    public ResultDTO<IotDeviceStatusCountDTO> getIotDeviceCountByStatus(@RequestParam(value = "connected", required =
            false) Boolean connected,
                                                                        @RequestParam(value = "iotDeviceTypeId",
                                                                                required = false) Long iotDeviceTypeId,
                                                                        @RequestParam(value = "iotProjectId") Long iotProjectId,
                                                                        @RequestParam(value = "iotTagId", required =
                                                                                false) Long iotTagId,
                                                                        @RequestParam(value = "keywords", required =
                                                                                false) String keywords) {
        IotDeviceQueryListDTO iotDeviceQuerListDTO = new IotDeviceQueryListDTO();
        iotDeviceQuerListDTO.setConnected(connected);
        iotDeviceQuerListDTO.setIotDeviceTypeId(iotDeviceTypeId);
        iotDeviceQuerListDTO.setIotProjectId(iotProjectId);
        iotDeviceQuerListDTO.setIotTagId(iotTagId);
        iotDeviceQuerListDTO.setKeywords(keywords);
        setDto(iotDeviceQuerListDTO);
        return iotDeviceApi.getDeviceCountByOnlineStatus(iotDeviceQuerListDTO);
    }

    /**
     * 设备Excel导入
     *
     * @param iotProjectId
     * @param deviceFile
     * @return
     * @throws IOException
     */
    @PostMapping(value = "/devices/import")
    public ResultDTO<List<IotDeviceErrorMsgDTO>> importDevice(@RequestParam("iotProjectId") Long iotProjectId,
                                                              @RequestParam("deviceFile") MultipartFile deviceFile) throws IOException {
        try {

            ResultDTO<Integer> judge = iotDeviceTypeApi.judgeProject(iotProjectId, 1L, "admin");
            if (!judge.isSuccess()) {
                return ResultDTO.newFail(ErrorCode.ERROR_8014.getCode(), ErrorCode.ERROR_8014.getMessage());
            }
            Map<String, List<?>> listMap = iotDeviceExcelImport.importDeviceExcel(iotProjectId,
                    getUser().getIotUserId(),
                    deviceFile);

            List<IotDeviceErrorMsgDTO> errorList = (List<IotDeviceErrorMsgDTO>) listMap.get(IotDeviceExcelImport
                    .VALIDATE_ERROR);
            if (errorList != null) {
                ResultDTO resultDTO = ResultDTO.newFail(ErrorCode.ERROR_3036.getCode(),
                        ErrorCode.ERROR_3036.getMessage());
                resultDTO.setData(errorList);
                return resultDTO;
            }

            List<IotDeviceDTO> deviceList = (List<IotDeviceDTO>) listMap.get(IotDeviceExcelImport.DEVICE_INFO);
            IotDeviceSaveBatchDTO iotDeviceSaveBatchDTO = new IotDeviceSaveBatchDTO();
            iotDeviceSaveBatchDTO.setIotProjectId(iotProjectId);
            iotDeviceSaveBatchDTO.setIotDeviceList(deviceList);
            setDto(iotDeviceSaveBatchDTO);
            ResultDTO<List<IotDeviceErrorMsgDTO>> result = iotDeviceApi.saveIotDevice(iotDeviceSaveBatchDTO);
            if (!result.isSuccess()) {
                result.setCode(ErrorCode.ERROR_3036.getCode());
                result.setMsg(ErrorCode.ERROR_3036.getMessage());
            }
            return result;
        } catch (IllegalTemplateException it) {
            return ResultDTO.newFail(it.getErrorCode(), it.getMessage());
        }
    }

    /**
     * 通过设备ID获取设备详细信息
     *
     * @param iotDeviceId
     * @return
     */
    @GetMapping(value = "/devices/get")
    public ResultDTO<IotDeviceDetailQueryDTO> getIotDeviceDetail(@RequestParam("iotDeviceId") Long iotDeviceId,
                                                                 @RequestParam("iotProjectId") Long iotProjectId) {
        IotDeviceDTO iotDeviceDTO = new IotDeviceDTO();
        iotDeviceDTO.setIotDeviceId(iotDeviceId);
        iotDeviceDTO.setIotProjectId(iotProjectId);
        setDto(iotDeviceDTO);
        return iotDeviceApi.getIotDeviceDetail(iotDeviceDTO);
    }

    /**
     * 通过设备编码获取设备详细信息
     *
     * @param deviceCode
     * @return
     */
    @GetMapping(value = "/devices/get/code")
    public ResultDTO<IotDeviceDetailQueryDTO> getIotDeviceDetail(@RequestParam("deviceCode") String deviceCode,
                                                                 @RequestParam("iotProjectId") Long iotProjectId) {
        IotDeviceQueryByCodeDTO iotDeviceQueryByCodeDTO = new IotDeviceQueryByCodeDTO();
        iotDeviceQueryByCodeDTO.setDeviceCode(deviceCode);
        iotDeviceQueryByCodeDTO.setIotProjectId(iotProjectId);
        setDto(iotDeviceQueryByCodeDTO);
        return iotDeviceApi.getIotDeviceDetail(iotDeviceQueryByCodeDTO);
    }

    /**
     * 设备监控列表
     *
     * @param registerStatus
     * @param iotProtocolId
     * @param deviceCode
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @GetMapping(value = "devices/monitor/list")
    public ResultDTO<PageResult<IotDeviceDTO>> getIotDevices(@RequestParam(value = "registerStatus",
            required = false) Boolean registerStatus,
                                                             @RequestParam(value = "iotProtocolId", required = false) Long iotProtocolId,
                                                             @RequestParam(value = "deviceCode", required = false) String deviceCode,
                                                             @RequestParam("pageIndex") Integer pageIndex,
                                                             @RequestParam("pageSize") Integer pageSize) {
        IotDeviceMonitorQueryListDTO iotDeviceMonitorQueryListDTO = new IotDeviceMonitorQueryListDTO();
        iotDeviceMonitorQueryListDTO.setRegisterStatus(registerStatus);
        iotDeviceMonitorQueryListDTO.setIotProtocolId(iotProtocolId);
        iotDeviceMonitorQueryListDTO.setDeviceCode(deviceCode);
        iotDeviceMonitorQueryListDTO.setPageIndex(pageIndex);
        iotDeviceMonitorQueryListDTO.setPageSize(pageSize);

        setDto(iotDeviceMonitorQueryListDTO);

        return iotDeviceApi.getIotDeviceByRegister(iotDeviceMonitorQueryListDTO);
    }

    /**
     * 设备注册
     *
     * @param iotDeviceDTO
     * @return
     */
    @PostMapping(value = "/devices/register")
    public ResultDTO<Long> updateRegister(@RequestBody IotDeviceDTO iotDeviceDTO) {
        setDto(iotDeviceDTO);
        return iotDeviceApi.updateIotDeviceRegister(iotDeviceDTO);
    }

    /**
     * 设备批量注册
     *
     * @param iotDeviceRegisterBatchDTO
     * @return
     */
    @PostMapping(value = "/devices/registerbatch")
    public ResultDTO<Integer> updateRegisterBatch(@RequestBody IotDeviceRegisterBatchDTO iotDeviceRegisterBatchDTO) {
        setDto(iotDeviceRegisterBatchDTO);
        return iotDeviceApi.updateIotDeviceRegisterBatch(iotDeviceRegisterBatchDTO);
    }


    @GetMapping(value = "/devices/counts")
    public ResultDTO<List<IotDeviceCountQueryDTO>> getDeviceCount(@RequestParam("startDatetime") Long startDatetime,
                                                                  @RequestParam("endDatetime") Long endDatetime,
                                                                  @RequestParam("type") String type,
                                                                  @RequestParam("iotProjectId") Long iotProjectId) {
        IotDosQueryDTO iotDosQueryDTO = new IotDosQueryDTO();
        iotDosQueryDTO.setStartDatetime(startDatetime);
        iotDosQueryDTO.setEndDatetime(endDatetime);
        iotDosQueryDTO.setType(type);
        iotDosQueryDTO.setIotProjectId(iotProjectId);
        setDto(iotDosQueryDTO);
        return iotDeviceApi.getDeviceCountByType(iotDosQueryDTO);
    }


    /**
     * 获取设备状态
     *
     * @param deviceCodes
     * @param iotProjectId
     * @return
     */
    @GetMapping(value = "/devices/status")
    public ResultDTO<List<IotDeviceStatusQueryDTO>> getIotDeviceStatus(@RequestParam("deviceCodes") List<String> deviceCodes,
                                                                       @RequestParam("iotProjectId") Long iotProjectId) {
        IotDeviceStatusBatchQueryDTO iotDeviceStatusBatchQueryDTO = new IotDeviceStatusBatchQueryDTO();
        iotDeviceStatusBatchQueryDTO.setDeviceCodes(deviceCodes);
        iotDeviceStatusBatchQueryDTO.setIotProjectId(iotProjectId);
        setDto(iotDeviceStatusBatchQueryDTO);
        return iotDeviceApi.getIotDeviceStatus(iotDeviceStatusBatchQueryDTO);
    }

    /**
     * 根据设备编码获取协议编码
     *
     * @param deviceCode
     * @param iotProjectId
     * @return
     */
    @GetMapping(value = "/devices/protocolcode/devicecode")
    public ResultDTO<IotIovProtocolCodeQueryDto> getProtocolByDeviceCode(@RequestParam("deviceCode") String deviceCode,
                                                                         @RequestParam("iotProjectId") Long iotProjectId) {
        IotDeviceDTO iotDeviceDTO = new IotDeviceDTO();
        iotDeviceDTO.setDeviceCode(deviceCode);
        iotDeviceDTO.setIotProjectId(iotProjectId);
        setDto(iotDeviceDTO);
        return iotDeviceApi.getProtocolByDeviceCode(iotDeviceDTO);
    }

    /**
     * 根据标签查询设备分布
     *
     * @param startDatetime
     * @param endDatetime
     * @param iotTagId
     * @return
     */
    @GetMapping(value = "/devices/tag/counts")
    public ResultDTO<QueryResult<IotDeviceCountTitleDTO>> getDeviceCountByTag(@RequestParam("startDatetime") Long startDatetime, @RequestParam("endDatetime")
            Long endDatetime, @RequestParam("iotTagId") Long iotTagId) {
        return iotDeviceApi.getDeviceCountByTag(iotTagId, startDatetime, endDatetime);
    }


    @GetMapping(value = "/devices/counts/top")
    public ResultDTO<QueryResult<IotDeviceCountTitleDTO>> getDeviceCountTop(@RequestParam("startDatetime") Long startDatetime, @RequestParam("endDatetime")
            Long endDatetime, @RequestParam("type") String type, @RequestParam("top") Integer top) {
        return iotDeviceApi.getDeviceCountTopByType(startDatetime, endDatetime, top, type);
    }

    /**
     * 查询设备分布
     *
     * @param iotTagId
     * @param iotProjectId
     * @param iotDeviceTypeId
     * @param connected
     * @param keywords
     * @return
     */
    @GetMapping(value = "/devices/location")
    public ResultDTO<List<IotDeviceQueryDTO>> getDeviceLocation(@RequestParam(value = "iotTagId", required = false) Long iotTagId,
                                                                @RequestParam(value = "iotProjectId") Long iotProjectId,
                                                                @RequestParam(value = "iotDeviceTypeId", required =
                                                                        false) Long iotDeviceTypeId,
                                                                @RequestParam(value = "connected", required = false) Boolean connected,
                                                                @RequestParam(value = "keywords", required = false) String keywords) {
        IotDeviceLocationQueryDTO iotDeviceLocationQueryDTO = new IotDeviceLocationQueryDTO();
        iotDeviceLocationQueryDTO.setIotProjectId(iotProjectId);
        iotDeviceLocationQueryDTO.setIotDeviceTypeId(iotDeviceTypeId);
        iotDeviceLocationQueryDTO.setIotTagId(iotTagId);
        iotDeviceLocationQueryDTO.setKeywords(keywords);
        iotDeviceLocationQueryDTO.setConnected(connected);
        setDto(iotDeviceLocationQueryDTO);
        return iotDeviceApi.getDeviceLocation(iotDeviceLocationQueryDTO);
    }

    /**
     * 查询历史轨迹
     *
     * @param startDatetime
     * @param endDatetime
     * @param iotProjectId
     * @param deviceCode
     * @return
     */
    @GetMapping("/devices/history")
    public ResultDTO<List<Map<String, String>>> getDeviceHistory(@RequestParam(value = "startTime") Long startDatetime,
                                                                 @RequestParam(value = "endTime") Long endDatetime,
                                                                 @RequestParam(value = "iotProjectId") Long iotProjectId,
                                                                 @RequestParam(value = "deviceCode") String deviceCode) {
        IotDeviceHistoryQueryDTO iotDeviceHistoryQueryDTO = new IotDeviceHistoryQueryDTO();
        iotDeviceHistoryQueryDTO.setIotProjectId(iotProjectId);
        iotDeviceHistoryQueryDTO.setDeviceCode(deviceCode);
        iotDeviceHistoryQueryDTO.setEndTime(endDatetime);
        iotDeviceHistoryQueryDTO.setStartTime(startDatetime);
        setDto(iotDeviceHistoryQueryDTO);
        return iotDeviceApi.history(iotDeviceHistoryQueryDTO);
    }

    @GetMapping("/device/event/list")
    public ResultDTO<PageResult<IotDesDeviceEventListQueryDto>> getDeviceEventList(@RequestParam("iotProjectId") Long iotProjectId,
                                                                                   @RequestParam("iotDeviceId") Long iotDeviceId,
                                                                                   @RequestParam("startTime") Long startTime,
                                                                                   @RequestParam("endTime") Long endTime,
                                                                                   @RequestParam("pageIndex") Integer pageIndex,
                                                                                   @RequestParam("pageSize") Integer pageSize) {
        IotDosDeviceEventQueryListDto deviceDTO = new IotDosDeviceEventQueryListDto();
        deviceDTO.setIotProjectId(iotProjectId);
        deviceDTO.setIotDeviceId(iotDeviceId);
        deviceDTO.setStartTime(startTime);
        deviceDTO.setEndTime(endTime);
        deviceDTO.setPageIndex(pageIndex);
        deviceDTO.setPageSize(pageSize);
        setDto(deviceDTO);
        return iotDeviceApi.getDeviceEventList(deviceDTO);

    }

    @GetMapping("/device/event/detail")
    public ResultDTO<IotDesEventDetailDto> getDeviceEventDetail(@RequestParam("iotProjectId") Long iotProjectId,
                                                                @RequestParam("iotDeviceId") Long iotDeviceId,
                                                                @RequestParam("iotDesDeviceEventId") Long iotDesDeviceEventId) {
        IotDeviceEventDetailQueryDto deviceEventDetailQueryDto = new IotDeviceEventDetailQueryDto();
        deviceEventDetailQueryDto.setIotProjectId(iotProjectId);
        deviceEventDetailQueryDto.setIotDeviceId(iotDeviceId);
        deviceEventDetailQueryDto.setIotDesDeviceEventId(iotDesDeviceEventId);
        setDto(deviceEventDetailQueryDto);
        return iotDeviceApi.getDeviceEventDetail(deviceEventDetailQueryDto);
    }

//    @GetMapping("/device/pass/list")
//    public ResultDTO<List<IotDevicePassDto>> getDevicePassList(@RequestParam("iotProjectId") Long iotProjectId,
//                                                               @RequestParam("iotDeviceId") Long iotDeviceId) {
//
//        IotDeviceDTO deviceDTO = new IotDeviceDTO();
//        deviceDTO.setIotDeviceId(iotDeviceId);
//        deviceDTO.setIotProjectId(iotProjectId);
//        setDto(deviceDTO);
//        return iotDevicePassApi.getDevicePassList(deviceDTO);
//    }

    @GetMapping("/device/pass/list")
    public ResultDTO<List<IotDevicePassDto>> getIotDevicePassList(@RequestParam("iotDeviceId") Long iotDeviceId,
                                                                  @RequestParam(value = "iotPassTypeId", required = false)
                                                                          Long iotPassTypeId) {
        return iotDevicePassApi.getIotDevicePassListByIotPassTypeId(iotDeviceId, iotPassTypeId);
    }

    @GetMapping("/device/logger/list")
    public ResultDTO<PageResult<IotDeviceLoggerListDto>> getDeviceLogger(@RequestParam("iotProjectId") Long iotProjectId,
                                                                         @RequestParam("iotDeviceId") Long iotDeviceId,
                                                                         @RequestParam("startTime") Long startTime,
                                                                         @RequestParam("endTime") Long endTime,
                                                                         @RequestParam("pageIndex") Integer pageIndex,
                                                                         @RequestParam("pageSize") Integer pageSize) {

        IotDeviceLoggerQueryListDto deviceLoggerQueryListDto = new IotDeviceLoggerQueryListDto();
        deviceLoggerQueryListDto.setEndTime(endTime);
        deviceLoggerQueryListDto.setIotDeviceId(iotDeviceId);
        deviceLoggerQueryListDto.setIotProjectId(iotProjectId);
        deviceLoggerQueryListDto.setLoggerTypeCode(Constants.DEVICE_LOGGER_TYPE);
        deviceLoggerQueryListDto.setStartTime(startTime);
        deviceLoggerQueryListDto.setPageIndex(pageIndex);
        deviceLoggerQueryListDto.setPageSize(pageSize);
        setDto(deviceLoggerQueryListDto);

        return iotLoggerApi.getDeviceLoggerList(deviceLoggerQueryListDto);

    }

    @GetMapping("/device/pass/live/stream")
    public ResultDTO<IotVisResultDTO> getPassLiveStream(@RequestParam("iotProjectId") Long iotProjectId,
                                                        @RequestParam("iotDevicePassId") Long iotDevicePassId) {
        IotDevicePassQueryDTO iotDevicePassQueryDTO = new IotDevicePassQueryDTO();
        iotDevicePassQueryDTO.setIotProjectId(iotProjectId);
        iotDevicePassQueryDTO.setIotDevicePassId(iotDevicePassId);
        setDto(iotDevicePassQueryDTO);
        //前端需要这样的格式
        ResultDTO<IotVisResultDTO> bizReturn = iotDevicePassApi.getPassLiveStream(iotDevicePassQueryDTO);
        if (!bizReturn.isSuccess() && bizReturn.getCode() == ErrorCode.ERROR_12005.getCode()) {
            return ResultDTO.newSuccess();
        }
        return bizReturn;
    }

    @GetMapping("/device/pass/play/back")
    public ResultDTO<IotVisResultDTO> getPassPlayBack(@RequestParam("iotProjectId") Long iotProjectId,
                                                      @RequestParam("iotDevicePassId") Long iotDevicePassId,
                                                      @RequestParam("startTime") Long startTime,
                                                      @RequestParam("endTime") Long endTime) {
        IotDevicePassQueryDTO iotDevicePassQueryDTO = new IotDevicePassQueryDTO();
        iotDevicePassQueryDTO.setIotProjectId(iotProjectId);
        iotDevicePassQueryDTO.setIotDevicePassId(iotDevicePassId);
        iotDevicePassQueryDTO.setStartTime(startTime);
        iotDevicePassQueryDTO.setEndTime(endTime);
        setDto(iotDevicePassQueryDTO);
        ResultDTO<IotVisResultDTO> bizReturn = iotDevicePassApi.getPassPlayBack(iotDevicePassQueryDTO);
        if (!bizReturn.isSuccess() && bizReturn.getCode() == ErrorCode.ERROR_12005.getCode()) {
            return ResultDTO.newSuccess();
        }
        return bizReturn;
    }

    @GetMapping("/device/pass/control")
    public ResultDTO<Integer> controlDevicePass(@RequestParam("iotProjectId") Long iotProjectId,
                                                @RequestParam("iotDevicePassId") Long iotDevicePassId,
                                                @RequestParam("command") Integer command,
                                                @RequestParam("speed") Integer speed) {
        IotDevicePassQueryDTO iotDevicePassQueryDTO = new IotDevicePassQueryDTO();
        iotDevicePassQueryDTO.setIotProjectId(iotProjectId);
        iotDevicePassQueryDTO.setIotDevicePassId(iotDevicePassId);
        iotDevicePassQueryDTO.setCommand(command);
        iotDevicePassQueryDTO.setSpeed(speed);
        setDto(iotDevicePassQueryDTO);
        return iotDevicePassApi.controlDevicePass(iotDevicePassQueryDTO);
    }

    @GetMapping("/device/pass/control/stop")
    public ResultDTO<Integer> stopControlDevicePass(@RequestParam("iotProjectId") Long iotProjectId,
                                                    @RequestParam("iotDevicePassId") Long iotDevicePassId) {
        IotDevicePassQueryDTO iotDevicePassQueryDTO = new IotDevicePassQueryDTO();
        iotDevicePassQueryDTO.setIotProjectId(iotProjectId);
        iotDevicePassQueryDTO.setIotDevicePassId(iotDevicePassId);
        setDto(iotDevicePassQueryDTO);
        return iotDevicePassApi.stopControlDevicePass(iotDevicePassQueryDTO);
    }
}

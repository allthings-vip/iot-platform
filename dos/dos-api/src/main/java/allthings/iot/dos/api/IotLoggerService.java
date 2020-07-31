package allthings.iot.dos.api;

import allthings.iot.common.dto.PageResult;
import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.dto.IotLoggerTypeDto;
import allthings.iot.dos.dto.query.IotDeviceLoggerListDto;
import allthings.iot.dos.dto.query.IotDeviceLoggerQueryListDto;
import allthings.iot.dos.dto.query.IotLogDTO;
import allthings.iot.dos.dto.query.IotSystemLoggerQueryListDto;

import java.util.List;

/**
 * @author tyf
 * @date 2019/03/12 10:47
 */
public interface IotLoggerService {

    /**
     * 保存设备日志
     *
     * @param logDTO
     */
    void saveIotDeviceLogger(IotLogDTO logDTO);

    /**
     * 查询设备日志列表
     *
     * @param iotDeviceLoggerQueryListDto
     * @return
     */
    ResultDTO<PageResult<IotDeviceLoggerListDto>> queryLoggerList(IotDeviceLoggerQueryListDto iotDeviceLoggerQueryListDto);

    /**
     * 查询系统日志列表
     *
     * @param iotSystemLoggerQueryListDto
     * @return
     */
    ResultDTO<PageResult<IotDeviceLoggerListDto>> querySystemLoggerList(IotSystemLoggerQueryListDto iotSystemLoggerQueryListDto);

    /**
     * 根据日志类型编码查询日志类型id
     *
     * @param loggerTypeCode
     * @return
     */
    ResultDTO<Long> getLoggerTypeIdByLoggerTypeCode(String loggerTypeCode);

    /**
     * 查询日志类型
     *
     * @return
     */
    ResultDTO<List<IotLoggerTypeDto>> getSystemLoggerType();

}

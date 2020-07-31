package allthings.iot.dos.dao;

import allthings.iot.dos.dto.query.IotDeviceLoggerListDto;
import allthings.iot.dos.dto.query.IotDeviceLoggerQueryListDto;
import allthings.iot.dos.dto.query.IotSystemLoggerQueryListDto;

import java.util.List;

/**
 * @author tyf
 * @date 2019/03/11 19:07
 */
public interface IotLoggerQueryDao {

    /**
     * 查询设备日志列表
     *
     * @param deviceLoggerQueryListDto
     * @return
     */
    List<IotDeviceLoggerListDto> queryDeviceLoggerList(IotDeviceLoggerQueryListDto deviceLoggerQueryListDto);

    /**
     * 查询系统日志列表
     *
     * @param iotSystemLoggerQueryListDto
     * @return
     */
    List<IotDeviceLoggerListDto> querySystemLoggerList(IotSystemLoggerQueryListDto iotSystemLoggerQueryListDto);

    /**
     * 查询设备日志数量
     *
     * @param deviceLoggerQueryListDto
     * @return
     */
    Integer queryDeviceLoggerListCount(IotDeviceLoggerQueryListDto deviceLoggerQueryListDto);

    /**
     * 查询系统日志数量
     *
     * @param iotSystemLoggerQueryListDto
     * @return
     */
    Integer querySystemLoggerListCount(IotSystemLoggerQueryListDto iotSystemLoggerQueryListDto);

}

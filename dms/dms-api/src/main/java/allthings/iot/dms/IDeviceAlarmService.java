package allthings.iot.dms;

import allthings.iot.common.dto.QueryResult;
import allthings.iot.dms.dto.DeviceAlarmDto;

import java.util.List;

/**
 * @author :  sylar
 * @FileName :  IDeviceAlarmService
 * @CreateDate :  2017/11/08
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
public interface IDeviceAlarmService {
    /**
     * 时间段内的设备报警数量
     *
     * @param beginTime
     * @param endTime
     * @return
     */
    long countOfDeviceAlarm(long beginTime, long endTime);

    /**
     * 根据设备类型查询设备报警数量
     *
     * @param deviceType
     * @param beginTime
     * @param endTime
     * @return
     */
    long countOfDeviceAlarmByDeviceType(String deviceType, long beginTime, long endTime);

    /**
     * 根据设备id查询设备报警数量
     *
     * @param deviceId
     * @param beginTime
     * @param endTime
     * @return
     */
    long countOfDeviceAlarmByDeviceId(String deviceId, long beginTime, long endTime);

    /**
     * 根据设备id查询报警信息
     *
     * @param deviceId
     * @param alarmCodes
     * @param beginTime
     * @param endTime
     * @param pageIndex
     * @param pageSize
     * @return
     */
    QueryResult<DeviceAlarmDto> getDeviceAlarmsByDeviceId(String deviceId, List<String> alarmCodes, long beginTime, long
            endTime, int pageIndex, int pageSize);
}

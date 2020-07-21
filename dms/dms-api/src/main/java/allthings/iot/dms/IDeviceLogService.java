package allthings.iot.dms;

import allthings.iot.common.dto.QueryResult;
import allthings.iot.dms.dto.DeviceLogDto;

/**
 * @author :  sylar
 * @FileName :  IDeviceLogService
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
public interface IDeviceLogService {
    /**
     * 查询设备日志
     *
     * @param deviceId
     * @param logType
     * @param beginTime
     * @param endTime
     * @param pageIndex
     * @param pageSize
     * @return
     */
    QueryResult<DeviceLogDto> getDeviceLogsByTime(String deviceId, String logType, long beginTime, long endTime, int
            pageIndex, int pageSize);

    /**
     * 查询设备日志
     *
     * @param deviceId
     * @param deviceType
     * @param logType
     * @param beginTime
     * @param endTime
     * @param pageIndex
     * @param pageSize
     * @return
     */
    QueryResult<DeviceLogDto> getDeviceLogsByTime(String deviceId, String deviceType, String logType, long beginTime,
                                               long endTime, int pageIndex, int pageSize);
}

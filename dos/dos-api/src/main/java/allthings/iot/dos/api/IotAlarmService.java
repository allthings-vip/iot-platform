package allthings.iot.dos.api;

import allthings.iot.common.dto.QueryResult;
import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.dto.query.IotDeviceAlarmDTO;

import java.util.List;

/**
 * @author :  luhao
 * @FileName :  IotAlarmBiz
 * @CreateDate :  2018-5-12
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
public interface IotAlarmService {
    /**
     * 查询报警
     *
     * @param deviceId
     * @param alarmCodes
     * @param beginTime
     * @param endTime
     * @param pageIndex
     * @param pageSize
     * @return
     */
    ResultDTO<QueryResult<IotDeviceAlarmDTO>> getDeviceAlarmsByDeviceId(String deviceId, List<String> alarmCodes, long
            beginTime, long endTime, int pageIndex, int pageSize);

}

package allthings.iot.dos.api;

import allthings.iot.common.dto.PageResult;
import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.dto.query.IotLogDTO;

/**
 * @author :  luhao
 * @FileName :  IotLogBiz
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
public interface IotLogService {
    /**
     * 查询日志信息
     *
     * @param deviceCode
     * @param msgType
     * @param beginDatetime
     * @param endDatetime
     * @param pageIndex
     * @param pageSize
     * @return
     */
    ResultDTO<PageResult<IotLogDTO>> getMsgLogs(String deviceCode, String msgType, long beginDatetime, long
            endDatetime, int pageIndex, int pageSize, Long iotProjectId, Long userId, String roleCode);
}

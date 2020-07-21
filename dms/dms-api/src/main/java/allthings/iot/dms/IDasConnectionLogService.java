package allthings.iot.dms;

import allthings.iot.common.dto.QueryResult;
import allthings.iot.dms.dto.DasConnectionLogDto;

/**
 * @author :  sylar
 * @FileName :  IDasConnectionLogService
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
public interface IDasConnectionLogService {
    /**
     * 根据das节点id获取das的连接日志
     *
     * @param nodeId
     * @param beginTime
     * @param endTime
     * @param pageIndex
     * @param pageSize
     * @return
     */
    QueryResult<DasConnectionLogDto> getDasConnectionLogsByNodeId(String nodeId, long beginTime, long endTime, int
            pageIndex, int pageSize);
}

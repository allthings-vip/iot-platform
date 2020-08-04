package allthings.iot.ktv.dao;

/**
 * @author :  luhao
 * @FileName :  IKtvDataOfflineDao
 * @CreateDate :  2018-5-21
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
public interface IKtvDataOfflineDao {

    /**
     * 查询时间段内的数据行数
     *
     * @param startDatetime
     * @param endDatetime
     * @return
     */
    Long getRowCountByTimeRange(Long startDatetime, Long endDatetime);

}

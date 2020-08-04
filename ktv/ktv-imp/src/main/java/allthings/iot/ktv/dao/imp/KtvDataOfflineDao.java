package allthings.iot.ktv.dao.imp;

import allthings.iot.ktv.common.Constant;
import allthings.iot.ktv.dao.IKtvDataOfflineDao;
import allthings.iot.util.hbase.api.HbaseTemplate;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.coprocessor.AggregationClient;
import org.apache.hadoop.hbase.client.coprocessor.LongColumnInterpreter;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author :  luhao
 * @FileName :  KtvDataOfflineDao
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
@Repository
public class KtvDataOfflineDao implements IKtvDataOfflineDao {
    @Autowired
    private HbaseTemplate hbaseTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(KtvDataOfflineDao.class);

    /**
     * 查询时间段内的数据行数
     *
     * @param startDatetime
     * @param endDatetime
     * @return
     */
    @Override
    public Long getRowCountByTimeRange(Long startDatetime, Long endDatetime) {
        AggregationClient ac = new AggregationClient(hbaseTemplate.getConfiguration());
        byte[] columnFamilyBytes = Bytes.toBytes(Constant.KTV_COL_FAMILY);
        Scan scan = new Scan();
        scan.addFamily(columnFamilyBytes);
        scan.setColumnFamilyTimeRange(columnFamilyBytes, startDatetime, endDatetime);
        long rowCount = 0;

        try {
            rowCount = ac.rowCount(TableName.valueOf(Constant.KTV_DATA_TABLE), new LongColumnInterpreter(), scan);
        } catch (Throwable e) {
            LOGGER.info(e.getMessage(), e);
        }

        return rowCount;

    }
}

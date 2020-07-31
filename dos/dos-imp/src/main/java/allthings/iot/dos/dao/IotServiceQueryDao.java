package allthings.iot.dos.dao;

import allthings.iot.common.dto.QueryResult;
import allthings.iot.dos.model.IotService;

/**
 * @author :  txw
 * @FileName :  IotServiceQueryDao
 * @CreateDate :  2019/5/13
 * @Description :  dmp
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
public interface IotServiceQueryDao {

    /**
     * 查询服务列表
     *
     * @return
     */
    QueryResult<IotService> getIotServiceLists(Boolean status, String keywords, Integer pageIndex, Integer pageSize);

    /**
     * 查询服务列表
     *
     * @return
     */
    QueryResult<IotService> getServiceInfoTopology();

}

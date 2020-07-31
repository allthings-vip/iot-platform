package allthings.iot.dos.api;

import allthings.iot.common.dto.QueryResult;
import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.dto.query.IotDataAggTypeQueryDTO;

/**
 * @author :  luhao
 * @FileName :  IotDataAggTypeBiz
 * @CreateDate :  2018-5-10
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
public interface IotDataAggTypeService {

    /**
     * 查询聚合类型列表
     *
     * @return
     */
    ResultDTO<QueryResult<IotDataAggTypeQueryDTO>> getIotDataAggTypeList();

}

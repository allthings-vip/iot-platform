package allthings.iot.dos.service;

import allthings.iot.common.dto.QueryResult;
import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.api.IotDataAggTypeService;
import allthings.iot.dos.dao.IotDataAggTypeDao;
import allthings.iot.dos.dto.query.IotDataAggTypeQueryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author :  luhao
 * @FileName :  IotDataAggTypeBizImpl
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
@Service("iotDataAggTypeService")
public class IotDataAggTypeServiceImpl implements IotDataAggTypeService {
    @Autowired
    private IotDataAggTypeDao iotDataAggTypeDao;

    /**
     * 查询聚合类型列表
     *
     * @return
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public ResultDTO<QueryResult<IotDataAggTypeQueryDTO>> getIotDataAggTypeList() {
        List<IotDataAggTypeQueryDTO> list = iotDataAggTypeDao.getAllByDeleted(false);
        QueryResult<IotDataAggTypeQueryDTO> queryResult = new QueryResult<>(list, list.size());
        return ResultDTO.newSuccess(queryResult);
    }
}

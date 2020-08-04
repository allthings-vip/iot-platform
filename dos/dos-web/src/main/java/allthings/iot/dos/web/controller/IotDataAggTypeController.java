package allthings.iot.dos.web.controller;

import allthings.iot.common.dto.QueryResult;
import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.client.api.IotDataAggTypeApi;
import allthings.iot.dos.dto.query.IotDataAggTypeQueryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author :  luhao
 * @FileName :  IotDataAggTypeController
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
@RestController
public class IotDataAggTypeController extends BaseController {
    @Autowired
    private IotDataAggTypeApi iotDataAggTypeApi;

    @GetMapping(value = "/dataaggtypes")
    public ResultDTO<QueryResult<IotDataAggTypeQueryDTO>> getIotDataAggTypeList() {
        return iotDataAggTypeApi.getIotDataAggTypeList();
    }

}

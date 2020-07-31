package allthings.iot.dos.controller;

import allthings.iot.common.dto.QueryResult;
import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.api.IotDataAggTypeService;
import allthings.iot.dos.client.api.IotDataAggTypeApi;
import allthings.iot.dos.dto.query.IotDataAggTypeQueryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tyf
 * @date 2019/07/04 14:34:12
 */
@RestController
public class IotDataAggTypeController implements IotDataAggTypeApi {

    @Autowired
    private IotDataAggTypeService dataAggTypeService;

    @Override
    public ResultDTO<QueryResult<IotDataAggTypeQueryDTO>> getIotDataAggTypeList() {
        return dataAggTypeService.getIotDataAggTypeList();
    }

}

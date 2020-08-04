package allthings.iot.dos.web.controller;

import allthings.iot.common.dto.QueryResult;
import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.client.api.IotProtocolApi;
import allthings.iot.dos.dto.query.IotProtocolDetailDTO;
import allthings.iot.dos.dto.query.IotProtocolQueryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author :  luhao
 * @FileName :  IotProtocolController
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
public class IotProtocolController extends BaseController {
    @Autowired
    private IotProtocolApi iotProtocolApi;

    @GetMapping(value = "/protocols/list")
    public ResultDTO<QueryResult<IotProtocolDetailDTO>> getIotProtocolList(@RequestParam(value = "keywords",
            required = false) String keywords,
                                                                           @RequestParam("pageIndex") Integer pageIndex,
                                                                           @RequestParam("pageSize") Integer pageSize) {
        return iotProtocolApi.getIotProtocolList(keywords, pageIndex, pageSize);
    }

    @GetMapping(value = "/protocols/{iotProtocolId}")
    public ResultDTO<IotProtocolDetailDTO> getIotProtocol(@PathVariable("iotProtocolId") Long iotProtocolId) {
        return iotProtocolApi.getIotProtocolDetail(iotProtocolId);
    }

    @GetMapping(value = "/protocols/get")
    public ResultDTO<List<IotProtocolQueryDTO>> getIotProtocols() {
        return iotProtocolApi.getIotProtocolList();
    }

}

package allthings.iot.dos.controller;

import allthings.iot.dos.api.IotProtocolBiz;
import allthings.iot.dos.client.api.IotProtocolApi;
import allthings.iot.dos.dto.IotProtocolDTO;
import allthings.iot.dos.dto.query.IotProtocolDetailDTO;
import allthings.iot.dos.dto.query.IotProtocolQueryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tf56.iot.common.dto.QueryResult;
import tf56.iot.common.dto.ResultDTO;

import java.util.List;

/**
 * @author tyf
 * @date 2019/07/04 14:51:18
 */
@RestController
public class IotProtocolController extends IotDosBaseController implements IotProtocolApi {

    @Autowired
    private IotProtocolBiz protocolBiz;

    @Override
    public ResultDTO<List<IotProtocolQueryDTO>> getIotProtocolList() {
        return getResult(protocolBiz.getIotProtocolList());
    }

    @Override
    public ResultDTO<QueryResult<IotProtocolDetailDTO>> getIotProtocolList(@RequestParam("keywords") String keywords,
                                                                           @RequestParam("pageIndex") Integer pageIndex,
                                                                           @RequestParam("pageSize") Integer pageSize) {
        return getResult(protocolBiz.getIotProtocolList(keywords, pageIndex, pageSize));
    }

    @Override
    public ResultDTO<Integer> saveIotProtocol(@RequestBody IotProtocolDTO iotProtocolDTO) {
        return getResult(protocolBiz.saveIotProtocol(iotProtocolDTO));
    }

    @Override
    public ResultDTO<Integer> updateIotProtocol(@RequestBody IotProtocolDTO iotProtocolDTO) {
        return getResult(protocolBiz.updateIotProtocol(iotProtocolDTO));
    }

    @Override
    public ResultDTO<Integer> deleteIotProtocol(@RequestParam("iotProtocolIds") Long[] iotProtocolIds,
                                                @RequestParam("operator") String operator) {
        return getResult(protocolBiz.deleteIotProtocol(iotProtocolIds, operator));
    }

    @Override
    public ResultDTO<IotProtocolDetailDTO> getIotProtocolDetail(@RequestParam("iotProtocolId") Long iotProtocolId) {
        return getResult(protocolBiz.getIotProtocolDetail(iotProtocolId));
    }

}

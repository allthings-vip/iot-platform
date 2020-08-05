package allthings.iot.dos.controller;

import allthings.iot.common.dto.QueryResult;
import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.api.IotProtocolService;
import allthings.iot.dos.client.api.IotProtocolApi;
import allthings.iot.dos.dto.IotProtocolDTO;
import allthings.iot.dos.dto.query.IotProtocolDetailDTO;
import allthings.iot.dos.dto.query.IotProtocolQueryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author tyf
 * @date 2019/07/04 14:51:18
 */
@RestController
public class IotProtocolController implements IotProtocolApi {

    @Autowired
    private IotProtocolService protocolBiz;

    @Override
    public ResultDTO<List<IotProtocolQueryDTO>> getIotProtocolList() {
        return protocolBiz.getIotProtocolList();
    }

    @Override
    public ResultDTO<QueryResult<IotProtocolDetailDTO>> getIotProtocolList(@RequestParam("keywords") String keywords,
                                                                           @RequestParam("pageIndex") Integer pageIndex,
                                                                           @RequestParam("pageSize") Integer pageSize) {
        return protocolBiz.getIotProtocolList(keywords, pageIndex, pageSize);
    }

    @Override
    public ResultDTO<Integer> saveIotProtocol(@RequestBody IotProtocolDTO iotProtocolDTO) {
        return protocolBiz.saveIotProtocol(iotProtocolDTO);
    }

    @Override
    public ResultDTO<Integer> updateIotProtocol(@RequestBody IotProtocolDTO iotProtocolDTO) {
        return protocolBiz.updateIotProtocol(iotProtocolDTO);
    }

    @Override
    public ResultDTO<Integer> deleteIotProtocol(@RequestParam("iotProtocolIds") Long[] iotProtocolIds,
                                                @RequestParam("modifyOperatorId") Long modifyOperatorId) {
        return protocolBiz.deleteIotProtocol(iotProtocolIds, modifyOperatorId);
    }

    @Override
    public ResultDTO<IotProtocolDetailDTO> getIotProtocolDetail(@RequestParam("iotProtocolId") Long iotProtocolId) {
        return protocolBiz.getIotProtocolDetail(iotProtocolId);
    }

}

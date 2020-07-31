package allthings.iot.dos.controller;

import allthings.iot.dos.api.IotKvDataBiz;
import allthings.iot.dos.client.api.IotKvDataApi;
import allthings.iot.dos.dto.query.IotFactorRangeValueQueryDTO;
import allthings.iot.dos.dto.query.IotFactorValueQueryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tf56.iot.common.dto.ResultDTO;

import java.util.List;

/**
 * @author tyf
 * @date 2019/07/04 15:03:06
 */
@RestController
public class IotKvDataController extends IotDosBaseController implements IotKvDataApi {

    @Autowired
    private IotKvDataBiz kvDataBiz;

    @Override
    public ResultDTO<List<IotFactorValueQueryDTO>> getKVLatest(@RequestParam("deviceCode") String deviceCode,
                                                               @RequestParam("partyId") String partyId,
                                                               @RequestParam("iotProjectId") Long iotProjectId,
                                                               @RequestParam("userId") Long userId,
                                                               @RequestParam("roleCode") String roleCode) {
        return getResult(kvDataBiz.getKVLatest(deviceCode, partyId, iotProjectId, userId, roleCode));
    }

    @Override
    public ResultDTO<List<IotFactorRangeValueQueryDTO>> getKVRange(@RequestParam("deviceCode") String deviceCode,
                                                                   @RequestParam("partyId") String partyId,
                                                                   @RequestParam("factorCodes") String[] factorCodes,
                                                                   @RequestParam("startDatetime") Long startDatetime,
                                                                   @RequestParam("endDatetime") Long endDatetime,
                                                                   @RequestParam("iotProjectId") Long iotProjectId,
                                                                   @RequestParam("userId") Long userId,
                                                                   @RequestParam("roleCode") String roleCode) {
        return getResult(kvDataBiz.getKVRange(deviceCode, partyId, factorCodes, startDatetime, endDatetime,
                iotProjectId, userId, roleCode));
    }

}

package allthings.iot.dos.controller;

import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.api.IotFactorService;
import allthings.iot.dos.client.api.IotFactorApi;
import allthings.iot.dos.dto.IotFactorDTO;
import allthings.iot.dos.dto.query.IotFactorQuerListDTO;
import allthings.iot.dos.dto.query.IotFactorQueryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author tyf
 * @date 2019/07/04 11:20:53
 */
@RestController
public class IotFactorController implements IotFactorApi {

    @Autowired
    private IotFactorService factorBiz;

    @Override
    public ResultDTO<Long> saveIotFactor(@RequestBody IotFactorDTO iotFactorDTO) {
        return factorBiz.saveIotFactor(iotFactorDTO);
    }

    @Override
    public ResultDTO<Long> updateIotFactor(@RequestBody IotFactorDTO iotFactorDTO) {
        return factorBiz.updateIotFactor(iotFactorDTO);
    }

    @Override
    public ResultDTO<Integer> deleteIotFactor(@RequestParam("iotFactorIds") Long[] iotFactorIds, @RequestParam(
            "modifyOperatorId") Long modifyOperatorId) {
        return factorBiz.deleteIotFactor(iotFactorIds, modifyOperatorId);
    }

    @Override
    public ResultDTO<List<IotFactorQueryDTO>> getIotFactorList(@RequestBody IotFactorQuerListDTO iotFactorQuerListDTO) {
        return factorBiz.getIotFactorList(iotFactorQuerListDTO);
    }

    @Override
    public ResultDTO<IotFactorQueryDTO> getIotFactorDetail(@RequestParam("iotFactorId") Long iotFactorId,
                                                           @RequestParam("operator") String operator) {
        return factorBiz.getIotFactorDetail(iotFactorId, operator);
    }

}

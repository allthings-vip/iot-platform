package allthings.iot.dos.web.controller;

import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.client.api.IotFactorApi;
import allthings.iot.dos.dto.IotFactorDTO;
import allthings.iot.dos.dto.query.IotFactorQuerListDTO;
import allthings.iot.dos.dto.query.IotFactorQueryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @author :  luhao
 * @FileName :  IotFactorController
 * @CreateDate :  2018-5-7
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
public class IotFactorController extends BaseController {

    @Autowired
    private IotFactorApi iotFactorApi;

    @PostMapping(value = "/factors")
    public ResultDTO<Long> saveFactor(@RequestBody IotFactorDTO iotFactorDTO) {
        iotFactorDTO.setCreateOperator(getUser().getCreateOperator());
        return iotFactorApi.saveIotFactor(iotFactorDTO);
    }

    @PutMapping(value = "/factors")
    public ResultDTO<Long> updateFactor(@ModelAttribute IotFactorDTO iotFactorDTO) {
        iotFactorDTO.setCreateOperator(getUser().getCreateOperator());
        return iotFactorApi.updateIotFactor(iotFactorDTO);
    }

    @DeleteMapping(value = "/factors")
    public ResultDTO<Integer> deleteFactor(@RequestParam("iotFactorIds") Long[] iotFactorIds) {
        return iotFactorApi.deleteIotFactor(iotFactorIds, getUser().getCreateOperator());
    }

    @GetMapping(value = "/factors/{iotFactorId}")
    public ResultDTO<IotFactorQueryDTO> getFactor(@PathVariable("iotFactorId") Long iotFactorId) {
        return iotFactorApi.getIotFactorDetail(iotFactorId, getUser().getCreateOperator());
    }

    @GetMapping(value = "/devices/factors/list")
    public ResultDTO<List<IotFactorQueryDTO>> getFactor(@RequestParam(value = "iotDeviceId") Long iotDeviceId,
                                                        @RequestParam(value = "iotProjectId") Long iotProjectId) {
        IotFactorQuerListDTO iotFactorQuerListDTO = new IotFactorQuerListDTO();
        iotFactorQuerListDTO.setIotDeviceId(iotDeviceId);
        iotFactorQuerListDTO.setIotProjectId(iotProjectId);
        setDto(iotFactorQuerListDTO);
        return iotFactorApi.getIotFactorList(iotFactorQuerListDTO);
    }
}

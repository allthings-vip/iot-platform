package allthings.iot.dos.web.controller;

import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.client.api.IotKvDataApi;
import allthings.iot.dos.dto.query.IotFactorRangeValueQueryDTO;
import allthings.iot.dos.dto.query.IotFactorValueQueryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author :  luhao
 * @FileName :  IotKvDataController
 * @CreateDate :  2018-5-18
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
public class IotKvDataController extends BaseController {
    @Autowired
    private IotKvDataApi iotKvDataApi;

    @GetMapping(value = "/points/latest")
    public ResultDTO<List<IotFactorValueQueryDTO>> getKVRealTimeData(@Param("deviceCode") String deviceCode,
                                                                     @RequestParam("iotProjectId") Long iotProjectId) {
        return iotKvDataApi.getKVLatest(deviceCode, "-100", iotProjectId, 1L, "admin");
    }

    @GetMapping(value = "/kvs/points")
    public ResultDTO<List<IotFactorRangeValueQueryDTO>> getKVRangeData(@Param("deviceCode") String deviceCode,
                                                                       @Param("factorCodes") String[] factorCodes,
                                                                       @Param("startDatetime") Long startDatetime,
                                                                       @Param("endDatetime") Long endDatetime,
                                                                       @RequestParam("iotProjectId") Long iotProjectId) {
        return iotKvDataApi.getKVRange(deviceCode, "-100", factorCodes, startDatetime, endDatetime,
                iotProjectId, 1L, "admin");
    }
}

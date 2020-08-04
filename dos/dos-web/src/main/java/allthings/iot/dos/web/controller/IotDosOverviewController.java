package allthings.iot.dos.web.controller;

import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.client.api.IotOverviewApi;
import allthings.iot.dos.client.api.IotPointCountApi;
import allthings.iot.dos.dto.query.IotDosOverviewDTO;
import allthings.iot.dos.dto.query.IotDosQueryDTO;
import allthings.iot.dos.dto.query.IotPointCountQueryDTO;
import allthings.iot.dos.dto.query.IotUserQueryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author :  luhao
 * @FileName :  IotDosOverviewController
 * @CreateDate :  2018-5-20
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
public class IotDosOverviewController extends BaseController {
    @Autowired
    private IotOverviewApi iotOverviewApi;

    @Autowired
    private IotPointCountApi iotPointCountApi;

    @GetMapping("/homepage/overview")
    public ResultDTO<IotDosOverviewDTO> overview() {
        IotUserQueryDTO iotUserQueryDTO = new IotUserQueryDTO();
        setDto(iotUserQueryDTO);
        return iotOverviewApi.overview(iotUserQueryDTO);
    }

    @GetMapping("/projects/overview")
    public ResultDTO<IotDosOverviewDTO> overviewByIotProjectId(@RequestParam("iotProjectId") Long iotProjectId) {
        IotUserQueryDTO iotUserQueryDTO = new IotUserQueryDTO();
        setDto(iotUserQueryDTO);
        iotUserQueryDTO.setIotProjectId(iotProjectId);
        return iotOverviewApi.overviewByIotProjectId(iotUserQueryDTO);

    }


    /**
     * 数据点上传趋势
     *
     * @param startDatetime
     * @param endDatetime
     * @param type
     * @return
     */
    @GetMapping(value = "/kvs/points/counts")
    public ResultDTO<List<IotPointCountQueryDTO>> getByDateRange(@RequestParam("startDatetime") Long startDatetime,
                                                                 @RequestParam("endDatetime") Long endDatetime,
                                                                 @RequestParam("type") String type,
                                                                 @RequestParam("iotProjectId") Long iotProjectId) {

        IotDosQueryDTO iotDosQueryDTO = new IotDosQueryDTO();
        iotDosQueryDTO.setStartDatetime(startDatetime);
        iotDosQueryDTO.setEndDatetime(endDatetime);
        iotDosQueryDTO.setType(type);
        iotDosQueryDTO.setIotProjectId(iotProjectId);
        setDto(iotDosQueryDTO);
        return iotPointCountApi.getByDateRange(iotDosQueryDTO);
    }

}

package allthings.iot.dos.web.controller;

import allthings.iot.common.dto.PageResult;
import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.client.api.IotDeviceTypeApi;
import allthings.iot.dos.dto.IotDeviceTypeDTO;
import allthings.iot.dos.dto.query.IotDeviceTypeDeleteDTO;
import allthings.iot.dos.dto.query.IotDeviceTypeQueryDTO;
import allthings.iot.dos.dto.query.IotProjectDeviceTypeDTO;
import allthings.iot.dos.dto.query.IotProjectSimpleDTO;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author :  luhao
 * @FileName :  IotDeviceTypeController
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
public class IotDeviceTypeController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(IotDeviceTypeController.class);

    @Autowired
    private IotDeviceTypeApi iotDeviceTypeApi;

    @PostMapping(value = "/devicetypes/save")
    public ResultDTO<Long> saveIotDeviceType(@RequestBody IotDeviceTypeDTO iotDeviceTypeDTO) {

        setDto(iotDeviceTypeDTO);
        return iotDeviceTypeApi.saveIotDeviceType(iotDeviceTypeDTO);
    }

    @PostMapping(value = "/devicetypes/update")
    public ResultDTO<Integer> updateIotDeviceType(@RequestBody IotDeviceTypeDTO iotDeviceTypeDTO) {
        LOGGER.info("更新设备类型，controller接收数据---->>" + JSON.toJSONString(iotDeviceTypeDTO));
        setDto(iotDeviceTypeDTO);
        return iotDeviceTypeApi.updateIotDeviceType(iotDeviceTypeDTO);
    }

    @PostMapping(value = "/devicetypes/delete")
    public ResultDTO<Integer> deleteIotDeviceType(@RequestBody IotDeviceTypeDeleteDTO iotDeviceTypeDeleteDTO) {

        setDto(iotDeviceTypeDeleteDTO);
        return iotDeviceTypeApi.deleteIotDeviceType(iotDeviceTypeDeleteDTO);
    }


    @GetMapping(value = "/devicetypes/list")
    public ResultDTO<PageResult<IotDeviceTypeQueryDTO>> getIotDeviceTypeList(@RequestParam(value = "keywords",
            required = false) String keywords,
                                                                             @RequestParam("pageIndex") Integer pageIndex,
                                                                             @RequestParam("pageSize") Integer pageSize,
                                                                             @RequestParam("iotProjectId") Long iotProjectId) {

        IotProjectSimpleDTO iotProjectSimpleDTO = new IotProjectSimpleDTO();
        setDto(iotProjectSimpleDTO);
        iotProjectSimpleDTO.setIotProjectId(iotProjectId);
        iotProjectSimpleDTO.setPageIndex(pageIndex);
        iotProjectSimpleDTO.setPageSize(pageSize);
        iotProjectSimpleDTO.setKeywords(keywords);
        return iotDeviceTypeApi.getIotDeviceTypeList(iotProjectSimpleDTO);
    }

    @GetMapping(value = "/devicetypes/detail")
    public ResultDTO<IotDeviceTypeDTO> getIotDeviceTypeDetail(@RequestParam("iotDeviceTypeId") Long iotDeviceTypeId,
                                                              @RequestParam("iotProjectId") Long iotProjectId) {
        IotDeviceTypeDTO iotDeviceTypeDTO = new IotDeviceTypeDTO();
        iotDeviceTypeDTO.setIotProjectId(iotProjectId);
        iotDeviceTypeDTO.setIotDeviceTypeId(iotDeviceTypeId);

        setDto(iotDeviceTypeDTO);
        return iotDeviceTypeApi.getIotDeviceTypeDetail(iotDeviceTypeDTO);
    }

    /**
     * 设备类型下拉框
     *
     * @param iotProjectId
     * @return
     */
    @GetMapping(value = "/devicetypes/down")
    public ResultDTO<List<IotProjectDeviceTypeDTO>> getDeviceTypeDown(@RequestParam("iotProjectId") Long iotProjectId) {
        IotDeviceTypeDTO iotDeviceTypeDTO = new IotDeviceTypeDTO();
        iotDeviceTypeDTO.setIotProjectId(iotProjectId);
        setDto(iotDeviceTypeDTO);
        return iotDeviceTypeApi.getIotDeviceTypeByIotProjectId(iotDeviceTypeDTO);
    }
}



package allthings.iot.dos.open.service;

import allthings.iot.common.dto.ResultDTO;
import com.alibaba.fastjson.JSON;
import allthings.iot.dos.constant.ErrorCode;
import allthings.iot.dos.dto.IotProjectDTO;
import allthings.iot.dos.dto.query.IotDeviceQueryDTO;
import allthings.iot.dos.dto.query.IotDeviceTypeSimpleDTO;
import allthings.iot.dos.dto.query.IotProjectSimpleDTO;
import allthings.iot.dos.open.api.IotProjectUtilService;
import allthings.iot.dos.api.IotDeviceService;
import allthings.iot.dos.api.IotDeviceTypeService;
import allthings.iot.dos.api.IotProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import allthings.iot.common.dto.PageResult;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :  txw
 * @FileName :  IotProjectUtil
 * @CreateDate :  2018/12/10
 * @Description :  dmp
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
@Service("iotProjectUtilBiz")
public class IotProjectUtilServiceImpl implements IotProjectUtilService {

    private static Logger LOGGER = LoggerFactory.getLogger(IotProjectUtilServiceImpl.class);
    @Autowired
    private IotProjectService iotProjectService;
    @Autowired
    private IotDeviceService iotDeviceService;
    @Autowired
    private IotDeviceTypeService iotDeviceTypeService;

    @Override
    public ResultDTO<Long> hasDevice(String clientId, List<String> deviceCodes, Boolean enabled) {
        ResultDTO<Long> biz = getIotProject(clientId);
        if (!biz.isSuccess()) {
            return biz;
        }
        Long iotProjectId = biz.getData();
        List<String> containDeviceCodes = new ArrayList<>();
        ResultDTO<PageResult<IotDeviceQueryDTO>> bizReturn = iotDeviceService.getIotOpenDeviceList(iotProjectId,
                deviceCodes, enabled);
        if (!bizReturn.isSuccess()) {
            return ResultDTO.newFail(bizReturn.getCode(), bizReturn.getMsg());
        }
        PageResult<IotDeviceQueryDTO> pageResult = bizReturn.getData();
        if (pageResult == null || CollectionUtils.isEmpty(pageResult.getData())) {
            return ResultDTO.newFail(ErrorCode.ERROR_3042.getCode(),
                    ErrorCode.ERROR_3042.getMessage());
        }

        List<IotDeviceQueryDTO> dtoList = pageResult.getData();
        for (String deviceCode : deviceCodes) {
            for (IotDeviceQueryDTO dto : dtoList) {
                if (deviceCode.equals(dto.getDeviceCode()) || deviceCode.endsWith(dto.getDeviceCode())) {
                    containDeviceCodes.add(deviceCode);
                    break;
                }
            }
        }
        if (containDeviceCodes.size() != deviceCodes.size()) {
            deviceCodes.removeAll(containDeviceCodes);
            return ResultDTO.newFail(String.format(ErrorCode.ERROR_3041.getMessage(),
                    JSON.toJSONString(deviceCodes)));
        }
        return ResultDTO.newSuccess(iotProjectId);
    }

    @Override
    public ResultDTO<Long> hasDeviceType(String clientId, List<String> deviceTypeCodes) {
        ResultDTO<Long> biz = getIotProject(clientId);
        if (!biz.isSuccess()) {
            return biz;
        }
        Long projectId = biz.getData();
        List<String> containDeviceTypeCodes = new ArrayList<>();

        IotProjectSimpleDTO iotProjectSimpleDTO = new IotProjectSimpleDTO();
        iotProjectSimpleDTO.setKeywords("");
        iotProjectSimpleDTO.setPageSize(Integer.MAX_VALUE);
        iotProjectSimpleDTO.setPageIndex(1);
        iotProjectSimpleDTO.setIotProjectId(projectId);
        ResultDTO<PageResult<IotDeviceTypeSimpleDTO>> bizReturn =
                iotDeviceTypeService.getOpenIotDeviceTypeList(iotProjectSimpleDTO);

        if (!bizReturn.isSuccess()) {
            return ResultDTO.newFail(bizReturn.getCode(), bizReturn.getMsg());
        }
        PageResult<IotDeviceTypeSimpleDTO> pageResult = bizReturn.getData();
        if (pageResult == null || CollectionUtils.isEmpty(pageResult.getData())) {
            return ResultDTO.newFail(ErrorCode.ERROR_1024.getCode(),
                    ErrorCode.ERROR_1024.getMessage());
        }
//        LOGGER.info("-------------------项目拥有设备类型列表：" + pageResult.getData());

        List<IotDeviceTypeSimpleDTO> dtoList = pageResult.getData();
        for (String deviceTypeCode : deviceTypeCodes) {
            for (IotDeviceTypeSimpleDTO dto : dtoList) {
                if (deviceTypeCode.equals(dto.getDeviceTypeCode())) {
                    containDeviceTypeCodes.add(deviceTypeCode);
                }
            }
        }
        if (containDeviceTypeCodes.size() != deviceTypeCodes.size()) {
            deviceTypeCodes.removeAll(containDeviceTypeCodes);
            return ResultDTO.newFail(String.format(ErrorCode.ERROR_1025.getMessage(),
                    JSON.toJSONString(deviceTypeCodes)));
        }
        return ResultDTO.newSuccess(projectId);
    }

    @Override
    public ResultDTO<Long> getIotProject(String clientId) {
//        LOGGER.info("-------------------获取项目信息：clientId " + clientId);
        IotProjectDTO project = iotProjectService.getProjectByClientId(clientId);
        if (project == null) {
            return ResultDTO.newFail("查询不到项目");
        }
//        LOGGER.info("-------------------项目信息：projectId " + project.getIotProjectId());
        return ResultDTO.newSuccess(project.getIotProjectId());
    }

}

package allthings.iot.dos.service;

import allthings.iot.common.dto.QueryResult;
import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.DosConstant;
import allthings.iot.dos.api.IotDeviceTypeService;
import allthings.iot.dos.api.IotPointCountService;
import allthings.iot.dos.constant.ErrorCode;
import allthings.iot.dos.dao.IotPointCountDao;
import allthings.iot.dos.dto.query.IotDosQueryDTO;
import allthings.iot.dos.dto.query.IotPointCountQueryDTO;
import allthings.iot.dos.dto.query.IotPointCountTitleDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author :  luhao
 * @FileName :  IotPointCountBizImpl
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
@Service("iotPointCountService")
public class IotPointCountServiceImpl implements IotPointCountService {

    private static Logger LOGGER = LoggerFactory.getLogger(IotPointCountServiceImpl.class);

    @Autowired
    private IotPointCountDao iotPointCountDao;

    @Autowired
    private IotDeviceTypeService iotDeviceTypeService;


    @Override
    public ResultDTO<List<IotPointCountQueryDTO>> getByDateRange(IotDosQueryDTO iotDosQueryDTO) {
        Long startDatetime = iotDosQueryDTO.getStartDatetime();
        Long endDatetime = iotDosQueryDTO.getEndDatetime();
        String type = iotDosQueryDTO.getType();
        Long iotUserId = iotDosQueryDTO.getModifyOperatorId();
        Long iotProjectId = iotDosQueryDTO.getIotProjectId();
        String roleCode = iotDosQueryDTO.getRoleCode();

        ResultDTO<Integer> result = iotDeviceTypeService.judgeProject(iotProjectId, iotUserId, roleCode);
        if (!result.isSuccess()) {
            return ResultDTO.newFail(result.getCode(), result.getMsg());
        }

        List<IotPointCountQueryDTO> dtoList;
        if (DosConstant.TOTAL.equals(type)) {
            dtoList = iotPointCountDao.getDayBeforeQuantityByDateRange(new Date(startDatetime),
                    new Date(endDatetime), iotProjectId);
        } else if (DosConstant.ADDED.equals(type)) {
            dtoList = iotPointCountDao.getDayQuantityByDateRange(new Date(startDatetime),
                    new Date(endDatetime), iotProjectId);
        } else {
            LOGGER.error("query type is error ,type={}", type);
            return ResultDTO.newFail(ErrorCode.ERROR_4018.getCode(),
                    ErrorCode.ERROR_4018.getMessage());
        }

        return ResultDTO.newSuccess(dtoList);
    }

    @Override
    public ResultDTO<QueryResult<IotPointCountTitleDTO>> getTopByDateRange(Long startDatetime, Long endDatetime,
                                                                           String type, Integer top) {
        Page<IotPointCountTitleDTO> page;
        if (DosConstant.PROJECT.equals(type)) {
            page = iotPointCountDao.getTopGroupByIotProjectId(PageRequest.of(0, top));
        } else if (DosConstant.DEVICE_TYPE.equals(type)) {
            page = iotPointCountDao.getTopGroupByIotDeviceTypeId(PageRequest.of(0, top));
        } else {
            LOGGER.error("query type is error ,type={}", type);
            return ResultDTO.newFail(ErrorCode.ERROR_4018.getCode(),
                    ErrorCode.ERROR_4018.getMessage());
        }

        QueryResult<IotPointCountTitleDTO> queryResult = new QueryResult<>(page.getContent(), page.getTotalElements());
        return ResultDTO.newSuccess(queryResult);
    }
}

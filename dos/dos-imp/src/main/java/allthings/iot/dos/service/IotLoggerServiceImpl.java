package allthings.iot.dos.service;

import allthings.iot.common.dto.PageResult;
import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.api.IotLoggerService;
import allthings.iot.dos.constant.ErrorCode;
import allthings.iot.dos.dao.IotDeviceDao;
import allthings.iot.dos.dao.IotLoggerDao;
import allthings.iot.dos.dao.IotLoggerQueryDao;
import allthings.iot.dos.dao.IotLoggerRelationDao;
import allthings.iot.dos.dao.IotLoggerTypeDao;
import allthings.iot.dos.dao.IotUserDao;
import allthings.iot.dos.dto.IotLoggerTypeDto;
import allthings.iot.dos.dto.query.IotDeviceLoggerListDto;
import allthings.iot.dos.dto.query.IotDeviceLoggerQueryListDto;
import allthings.iot.dos.dto.query.IotLogDTO;
import allthings.iot.dos.dto.query.IotSystemLoggerQueryListDto;
import allthings.iot.dos.model.IotDevice;
import allthings.iot.dos.model.IotLogger;
import allthings.iot.dos.model.IotLoggerRelation;
import allthings.iot.dos.model.IotLoggerType;
import allthings.iot.dos.model.IotUser;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * @author tyf
 * @date 2019/03/12 10:47
 */
@Service("iotLoggerService")
public class IotLoggerServiceImpl implements IotLoggerService {

    @Autowired
    private IotLoggerDao iotLoggerDao;
    @Autowired
    private IotLoggerRelationDao iotLoggerRelationDao;
    @Autowired
    private IotLoggerQueryDao iotLoggerQueryDao;
    @Autowired
    private IotLoggerTypeDao iotLoggerTypeDao;
    @Autowired
    private IotUserDao iotUserDao;
    @Autowired
    private IotDeviceDao deviceDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void saveIotDeviceLogger(IotLogDTO logDTO) {
        String associationType = logDTO.getAssociationType();
        long iotLoggerTypeId = iotLoggerTypeDao.getIotLoggerTypeIdByLoggerTypeCode(logDTO.getLogTypeCode());
        IotLogger iotLogger = new IotLogger();
        iotLogger.setLoggerContent(logDTO.getLogContent());
        iotLogger.setLoggerTime(new Date(logDTO.getLoggerTime()));
        iotLogger.setIotLoggerTypeId(iotLoggerTypeId);
        Long iotUserId = logDTO.getUserId();
        if (iotUserId == null && !StringUtils.isBlank(logDTO.getUsername())) {
            iotUserId = iotUserDao.getIotUserByUsername(logDTO.getUsername()).getIotUserId();
        }
        if (iotUserId == null) {
            iotUserId = 0L;
        }
        iotLogger.setIotUserId(iotUserId);
        iotLogger.setCreateOperatorId(iotUserId);
        iotLogger.setModifyOperatorId(iotUserId);
        iotLogger = iotLoggerDao.saveAndFlush(iotLogger);
        IotLoggerRelation loggerRelation = new IotLoggerRelation();
        loggerRelation.setIotLoggerId(iotLogger.getIotLoggerId());
        loggerRelation.setCreateOperatorId(iotUserId);
        loggerRelation.setModifyOperatorId(iotUserId);
        loggerRelation.setAssociationType(associationType);
        Long associationId = logDTO.getAssociationId();
        Long iotProjectId = logDTO.getIotProjectId();
        if (associationId == null) {
            if (IotDevice.class.getSimpleName().equals(associationType)) {
                List<IotDevice> devices = Lists.newArrayList();
                if (iotProjectId == null) {
                    devices = deviceDao.getIotDeviceByDeviceCode(logDTO.getDeviceCode());
                } else {
                    devices.add(deviceDao.getIotDeviceByDeviceCodeAndIotProjectId(logDTO.getDeviceCode(),
                            iotProjectId));
                }
                if (CollectionUtils.isEmpty(devices)) {
                    return;
                }
                for (IotDevice device : devices) {
                    associationId = device.getIotDeviceId();
                    if (iotProjectId == null) {
                        iotProjectId = device.getIotProjectId();
                    }
                    loggerRelation.setAssociationId(associationId);
                    loggerRelation.setIotProjectId(iotProjectId);
                    iotLoggerRelationDao.saveAndFlush(loggerRelation);
                }
            } else if (IotUser.class.getSimpleName().equals(associationType)) {
                associationId = iotUserId;
                iotProjectId = 0L;
                loggerRelation.setAssociationId(associationId);
                loggerRelation.setIotProjectId(iotProjectId);
                iotLoggerRelationDao.saveAndFlush(loggerRelation);
            }
        } else {
            loggerRelation.setAssociationId(associationId);
            loggerRelation.setIotProjectId(iotProjectId);
            iotLoggerRelationDao.saveAndFlush(loggerRelation);
        }

    }

    @Override
    public ResultDTO<PageResult<IotDeviceLoggerListDto>> queryLoggerList(IotDeviceLoggerQueryListDto iotDeviceLoggerQueryListDto) {
        ResultDTO<Integer> bizReturn = validateQueryParam(iotDeviceLoggerQueryListDto);
        if (!bizReturn.isSuccess()) {
            return ResultDTO.newFail(bizReturn.getCode(), bizReturn.getMsg());
        }
        List<IotDeviceLoggerListDto> deviceLoggerListDtoList =
                iotLoggerQueryDao.queryDeviceLoggerList(iotDeviceLoggerQueryListDto);

        Integer count = iotLoggerQueryDao.queryDeviceLoggerListCount(iotDeviceLoggerQueryListDto);
        PageResult<IotDeviceLoggerListDto> pageResult = new PageResult<>(count, deviceLoggerListDtoList);
        return ResultDTO.newSuccess(pageResult);
    }

    @Override
    public ResultDTO<PageResult<IotDeviceLoggerListDto>> querySystemLoggerList(IotSystemLoggerQueryListDto iotSystemLoggerQueryListDto) {
        List<IotDeviceLoggerListDto> deviceLoggerListDtoList =
                iotLoggerQueryDao.querySystemLoggerList(iotSystemLoggerQueryListDto);

        Integer count = iotLoggerQueryDao.querySystemLoggerListCount(iotSystemLoggerQueryListDto);
        PageResult<IotDeviceLoggerListDto> pageResult = new PageResult<>(count, deviceLoggerListDtoList);
        return ResultDTO.newSuccess(pageResult);
    }

    @Override
    public ResultDTO<Long> getLoggerTypeIdByLoggerTypeCode(String loggerTypeCode) {
        if (StringUtils.isBlank(loggerTypeCode)) {
            return null;
        }
        return ResultDTO.newSuccess(iotLoggerTypeDao.getIotLoggerTypeIdByLoggerTypeCode(loggerTypeCode));
    }

    @Override
    public ResultDTO<List<IotLoggerTypeDto>> getSystemLoggerType() {
        List<IotLoggerType> loggerTypeList = iotLoggerTypeDao.getSystemLoggerType();
        List<IotLoggerTypeDto> loggerTypeDtoList = Lists.newArrayList();
        for (IotLoggerType loggerType : loggerTypeList) {
            IotLoggerTypeDto loggerTypeDto = new IotLoggerTypeDto();
            BeanUtils.copyProperties(loggerType, loggerTypeDto);
            loggerTypeDtoList.add(loggerTypeDto);
        }
        return ResultDTO.newSuccess(loggerTypeDtoList);
    }

    private ResultDTO<Integer> validateQueryParam(IotDeviceLoggerQueryListDto iotDeviceLoggerQueryListDto) {
        Long iotProjectId = iotDeviceLoggerQueryListDto.getIotProjectId();
        if (iotProjectId == null || iotProjectId <= 0) {
            return ResultDTO.newFail(ErrorCode.ERROR_2004.getCode(),
                    ErrorCode.ERROR_2004.getMessage());
        }

        Long iotDeviceId = iotDeviceLoggerQueryListDto.getIotDeviceId();
        if (iotDeviceId == null || iotDeviceId <= 0) {
            return ResultDTO.newFail(ErrorCode.ERROR_3004.getCode(),
                    ErrorCode.ERROR_3004.getMessage());
        }

        Long startTime = iotDeviceLoggerQueryListDto.getStartTime();
        Long endTime = iotDeviceLoggerQueryListDto.getEndTime();
        if (startTime == null || endTime == null || startTime > endTime || startTime < 0 || endTime <= 0) {
            return ResultDTO.newFail(ErrorCode.ERROR_5052.getCode(),
                    ErrorCode.ERROR_5052.getMessage());
        }

        Integer pageIndex = iotDeviceLoggerQueryListDto.getPageIndex();
        Integer pageSize = iotDeviceLoggerQueryListDto.getPageSize();

        if (pageIndex == null || pageSize == null || pageIndex <= 0 || pageSize <= 0) {
            return ResultDTO.newFail("分页参数异常，请检查");
        }

        return ResultDTO.newSuccess();
    }

}

package allthings.iot.dos.service;

import allthings.iot.common.dto.ResultDTO;
import allthings.iot.common.usual.DeviceTypes;
import allthings.iot.dos.api.IotDeviceTypeService;
import allthings.iot.dos.api.IotFactorService;
import allthings.iot.dos.constant.ErrorCode;
import allthings.iot.dos.dao.IotFactorDao;
import allthings.iot.dos.dao.IotProtocolDao;
import allthings.iot.dos.dao.IotProtocolFactorDao;
import allthings.iot.dos.dto.IotFactorDTO;
import allthings.iot.dos.dto.query.IotFactorQuerListDTO;
import allthings.iot.dos.dto.query.IotFactorQueryDTO;
import allthings.iot.dos.dto.query.IotProtocolFactorDTO;
import allthings.iot.dos.exception.IllegalArgumentException;
import allthings.iot.dos.model.IotFactor;
import allthings.iot.dos.validate.IotFactorValidate;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author :  luhao
 * @FileName :  IotFactorBizImpl
 * @CreateDate :  2018/5/7
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
@Service("iotFactorService")
public class IotFactorServiceImpl implements IotFactorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(IotFactorServiceImpl.class);
    @Autowired
    private IotFactorDao iotFactorDao;
    @Autowired
    private IotProtocolFactorDao iotProtocolFactorDao;
    @Autowired
    private IotProtocolDao iotProtocolDao;
    @Autowired
    private IotDeviceTypeService iotDeviceTypeService;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public ResultDTO<Long> saveIotFactor(IotFactorDTO iotFactorDTO) {
        try {
            IotFactorValidate.validateSave(iotFactorDTO);

            Long iotFactorId = iotFactorDao.getIotFactorIdByFactorCode(iotFactorDTO.getFactorCode());
            if (iotFactorId != null) {
                LOGGER.error(" factor code {} duplicate ", iotFactorDTO.getFactorCode());
                return ResultDTO.newFail(ErrorCode.ERROR_4019.getCode(),
                        ErrorCode.ERROR_4019.getMessage());
            }

            IotFactor iotFactor = iotFactorDao.saveAndFlush(getIotFactor(iotFactorDTO));
            return ResultDTO.newSuccess(iotFactor.getIotFactorId());
        } catch (IllegalArgumentException ie) {
            LOGGER.error("parameters validate illegal", ie);
            return ResultDTO.newFail(ie.getErrorCode(), ie.getMessage());
        } catch (Exception ee) {
            LOGGER.error("save factor error", ee);
            return ResultDTO.newFail(ErrorCode.ERROR_4000.getCode(),
                    ErrorCode.ERROR_4000.getMessage());
        }
    }

    /**
     * 组装因子模型
     *
     * @param iotFactorDTO
     * @return
     */
    public IotFactor getIotFactor(IotFactorDTO iotFactorDTO) {
        IotFactor iotFactor = new IotFactor();
        BeanUtils.copyProperties(iotFactorDTO, iotFactor);
        return iotFactor;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public ResultDTO<Long> updateIotFactor(IotFactorDTO iotFactorDTO) {
        try {
            IotFactorValidate.validateUpdate(iotFactorDTO);

            Long iotFactorId = iotFactorDao.getIotFactorIdByFactorCode(iotFactorDTO.getFactorCode());
            if (iotFactorId != null && !iotFactorId.equals(iotFactorDTO.getIotFactorId())) {
                LOGGER.error(" factor code {} duplicate ", iotFactorDTO.getFactorCode());
                return ResultDTO.newFail(ErrorCode.ERROR_4019.getCode(),
                        ErrorCode.ERROR_4019.getMessage());
            }

            IotFactor iotFactor = iotFactorDao.saveAndFlush(getIotFactor(iotFactorDTO));
            return ResultDTO.newSuccess(iotFactor.getIotFactorId());
        } catch (IllegalArgumentException ie) {
            LOGGER.error("parameters validate illegal", ie);
            return ResultDTO.newFail(ie.getErrorCode(), ie.getMessage());
        } catch (Exception ee) {
            LOGGER.error("update factor error", ee);
            return ResultDTO.newFail(ErrorCode.ERROR_4002.getCode(),
                    ErrorCode.ERROR_4002.getMessage());
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public ResultDTO<Integer> deleteIotFactor(Long[] iotFactorIds, Long modifyOperatorId) {
        if (ArrayUtils.isEmpty(iotFactorIds)) {
            LOGGER.error(ErrorCode.ERROR_4004.getMessage());
            return ResultDTO.newFail(ErrorCode.ERROR_4004.getCode(),
                    ErrorCode.ERROR_4004.getMessage());
        }

        List<IotProtocolFactorDTO> factorQueryDTOList = iotProtocolFactorDao.getIotProtocolFactorByIotFactorId(Lists
                .newArrayList(iotFactorIds));
        if (factorQueryDTOList.size() > 0) {
            StringBuffer sb = new StringBuffer();
            for (int index = 0; index < factorQueryDTOList.size(); index++) {
                IotProtocolFactorDTO iotProtocolFactorDTO = factorQueryDTOList.get(index);
                if (index == 0) {
                    sb.append(iotProtocolFactorDTO.getFactorName());
                } else {
                    sb.append("、");
                    sb.append(iotProtocolFactorDTO.getFactorName());
                }
            }

            return ResultDTO.newFail(ErrorCode.ERROR_4017.getCode(),
                    String.format(ErrorCode.ERROR_4017.getMessage(), sb
                            .toString()));
        }

        try {
            for (Long iotFactorId : iotFactorIds) {
                iotFactorDao.deleteByIotFactorId(iotFactorId, modifyOperatorId);
            }

            return ResultDTO.newSuccess();
        } catch (Exception ee) {
            LOGGER.error("delete factor error", ee);
            return ResultDTO.newFail(ErrorCode.ERROR_4001.getCode(),
                    ErrorCode.ERROR_4001.getMessage());
        }
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public ResultDTO<List<IotFactorQueryDTO>> getIotFactorList(IotFactorQuerListDTO iotFactorQuerListDTO) {

        ResultDTO<Integer> judge = iotDeviceTypeService.judgeProject(iotFactorQuerListDTO.getIotProjectId(),
                iotFactorQuerListDTO.getModifyOperatorId(), iotFactorQuerListDTO.getRoleCode());
        if (!judge.isSuccess()) {
            return ResultDTO.newFail(ErrorCode.ERROR_8014.getCode(),
                    ErrorCode.ERROR_8014.getMessage());
        }

        Long protocolId = iotProtocolDao.getIdByIotDeviceId(iotFactorQuerListDTO.getIotDeviceId());
        if (protocolId == null) {
            protocolId = iotProtocolDao.getIdByCode(DeviceTypes.DEVICE_TYPE_PASS);
        }
        List<IotFactorQueryDTO> factorList = iotFactorDao.getAll(protocolId);

        return ResultDTO.newSuccess(factorList);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public ResultDTO<List<IotFactorQueryDTO>> getIotFactorListByDeviceCode(String deviceTypeCode, Long iotProjectId) {

        Long protocolId = iotProtocolDao.getIdByIotDeviceTypeCode(deviceTypeCode, iotProjectId);

        List<IotFactorQueryDTO> factorList = iotFactorDao.getAll(protocolId);

        return ResultDTO.newSuccess(factorList);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public ResultDTO<IotFactorQueryDTO> getIotFactorDetail(Long iotFactorId, String operator) {
        IotFactorQueryDTO iotFactor = iotFactorDao.getByIotFactorIdAndDeleted(iotFactorId, false);
        if (iotFactor == null) {
            LOGGER.error(ErrorCode.ERROR_4005.getMessage());
            return ResultDTO.newFail(ErrorCode.ERROR_4005.getCode(),
                    ErrorCode.ERROR_4005.getMessage());
        }

        return ResultDTO.newSuccess(iotFactor);
    }
}

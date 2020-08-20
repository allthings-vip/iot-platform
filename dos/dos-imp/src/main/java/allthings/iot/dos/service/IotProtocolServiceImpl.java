package allthings.iot.dos.service;

import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.constant.ErrorCode;
import allthings.iot.dos.dao.IotDeviceTypeDao;
import allthings.iot.dos.dao.IotProtocolDao;
import allthings.iot.dos.dao.IotProtocolFactorDao;
import allthings.iot.dos.dto.IotProtocolDTO;
import allthings.iot.dos.dto.query.IotProtocolDetailDTO;
import allthings.iot.dos.dto.query.IotProtocolQueryDTO;
import allthings.iot.dos.model.IotProtocol;
import allthings.iot.dos.model.IotProtocolFactor;
import allthings.iot.dos.api.IotProtocolService;
import allthings.iot.dos.validate.IotProtocolValidate;
import allthings.iot.dos.exception.IllegalArgumentException;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import allthings.iot.common.dto.QueryResult;

import javax.persistence.criteria.Predicate;
import java.util.List;

/**
 * @author :  luhao
 * @FileName :  IotProtocolBizImpl
 * @CreateDate :  2018-5-10
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
@Service("iotProtocolService")
public class IotProtocolServiceImpl implements IotProtocolService {
    private static final Logger LOGGER = LoggerFactory.getLogger(IotProtocolServiceImpl.class);
    @Autowired
    private IotProtocolDao iotProtocolDao;
    @Autowired
    private IotProtocolFactorDao iotProtocolFactorDao;
    @Autowired
    private IotDeviceTypeDao iotDeviceTypeDao;

    /**
     * 获取协议列表
     *
     * @return
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public ResultDTO<List<IotProtocolQueryDTO>> getIotProtocolList() {
        List<IotProtocolQueryDTO> list = iotProtocolDao.getAllByIsDeleted(false);
        return ResultDTO.newSuccess(list);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public ResultDTO<QueryResult<IotProtocolDetailDTO>> getIotProtocolList(String keywords, Integer pageIndex,
                                                                           Integer pageSize) {
        Page<IotProtocol> iotProtocolPage = iotProtocolDao.findAll((root, query, cb) -> {
            List<Predicate> predicateList = Lists.newArrayList();
            if (StringUtils.isNotEmpty(keywords)) {
                predicateList.add(cb.or(cb.like(root.get("protocolCode"), "%" + keywords + "%"), cb.like(root.get
                        ("protocolName"), "%" +
                        keywords + "%")));
            }

            predicateList.add(cb.equal(root.get("isDeleted"), false));

            return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
        }, PageRequest.of(pageIndex - 1, pageSize, Sort.Direction.DESC, "stampDate"));


        List<IotProtocol> protocolList = iotProtocolPage.getContent();
        QueryResult<IotProtocolDetailDTO> queryResult = new QueryResult<>(Lists.newArrayList(), 0);
        if (CollectionUtils.isEmpty(protocolList)) {
            return ResultDTO.newSuccess(queryResult);
        }

        List<IotProtocolDetailDTO> detailDTOList = Lists.newArrayList();
        for (IotProtocol iotProtocol : protocolList) {
            IotProtocolDetailDTO iotProtocolDetailDTO = new IotProtocolDetailDTO();
            BeanUtils.copyProperties(iotProtocol, iotProtocolDetailDTO);
            iotProtocolDetailDTO.setIotFactors(iotProtocolFactorDao.getIotProtocolFactorByiotProtocolId(iotProtocol
                    .getIotProtocolId()));

            detailDTOList.add(iotProtocolDetailDTO);
        }

        queryResult = new QueryResult<>(detailDTOList, iotProtocolPage.getTotalElements());

        return ResultDTO.newSuccess(queryResult);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public ResultDTO<Integer> saveIotProtocol(IotProtocolDTO iotProtocolDTO) {
        try {
            IotProtocolValidate.validateSave(iotProtocolDTO);
            IotProtocol iotProtocol = new IotProtocol();
            BeanUtils.copyProperties(iotProtocolDTO, iotProtocol);
            iotProtocol = iotProtocolDao.saveAndFlush(iotProtocol);
            iotProtocolFactorDao.saveAll(getIotProtocolFactorForSave(iotProtocol.getIotProtocolId(), iotProtocolDTO
                    .getIotFactorIds(), iotProtocolDTO.getCreateOperatorId()));

            return ResultDTO.newSuccess();
        } catch (IllegalArgumentException ie) {
            LOGGER.error("parameters validate illegal", ie);
            return ResultDTO.newFail(ie.getErrorCode(), ie.getMessage());
        } catch (Exception ee) {
            LOGGER.error("save protocol error", ee);
            return ResultDTO.newFail(ErrorCode.ERROR_7000.getCode(),
                    ErrorCode.ERROR_7000.getMessage());
        }
    }

    /**
     * 组装协议因子
     *
     * @param iotProtocolId
     * @param iotFactorIds
     * @param createOperatorId
     * @return
     */
    private List<IotProtocolFactor> getIotProtocolFactorForSave(Long iotProtocolId, Long[] iotFactorIds, Long
            createOperatorId) {
        List<IotProtocolFactor> iotProtocolFactors = Lists.newArrayList();
        for (Long iotFactorId : iotFactorIds) {
            if (iotFactorId == null) {
                continue;
            }

            IotProtocolFactor iotProtocolFactor = new IotProtocolFactor();
            iotProtocolFactor.setIotProtocolId(iotProtocolId);
            iotProtocolFactor.setIotFactorId(iotFactorId);
            iotProtocolFactor.setCreateOperatorId(createOperatorId);
            iotProtocolFactor.setModifyOperatorId(createOperatorId);

            iotProtocolFactors.add(iotProtocolFactor);
        }

        return iotProtocolFactors;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public ResultDTO<Integer> updateIotProtocol(IotProtocolDTO iotProtocolDTO) {
        try {
            IotProtocolValidate.validateUpdate(iotProtocolDTO);
            iotProtocolDao.updateIotProtocol(iotProtocolDTO.getIotProtocolId(), iotProtocolDTO.getProtocolName(),
                    iotProtocolDTO.getProtocolCode(), iotProtocolDTO.getDescription(), iotProtocolDTO.getServerDomain()
                    , iotProtocolDTO.getServerIp(), iotProtocolDTO.getServerPort(), iotProtocolDTO.getTestServerDomain()
                    , iotProtocolDTO.getTestServerIp(), iotProtocolDTO.getTestServerPort(), iotProtocolDTO
                            .getModifyOperatorId());
            iotProtocolFactorDao.saveAll(getIotProtocolFactorForUpdate(iotProtocolDTO.getIotProtocolId(), iotProtocolDTO
                    .getIotFactorIds(), iotProtocolDTO.getCreateOperatorId()));

            return ResultDTO.newSuccess();
        } catch (IllegalArgumentException ie) {
            LOGGER.error("parameters validate illegal", ie);
            return ResultDTO.newFail(ie.getErrorCode(), ie.getMessage());
        } catch (Exception ee) {
            LOGGER.error("save protocol error", ee);
            return ResultDTO.newFail(ErrorCode.ERROR_7002.getCode(),
                    ErrorCode.ERROR_7002.getMessage());
        }
    }

    /**
     * 组装协议类型因子
     *
     * @param iotProtocolId
     * @param iotFactorIds
     * @param createOperatorId
     * @return
     */
    private List<IotProtocolFactor> getIotProtocolFactorForUpdate(Long iotProtocolId, Long[] iotFactorIds, Long
            createOperatorId) {
        List<IotProtocolFactor> persistIotProtocolFactorList = iotProtocolFactorDao
                .getIotProtocolFactorByIotProtocolIdAndDeleted(iotProtocolId, false);

        List<IotProtocolFactor> iotProtocolFactorList = getIotProtocolFactorForSave(iotProtocolId, iotFactorIds,
                createOperatorId);
        //新增
        for (IotProtocolFactor iotProtocolFactor : iotProtocolFactorList) {
            if (persistIotProtocolFactorList.contains(iotProtocolFactor)) {
                continue;
            } else {
                persistIotProtocolFactorList.add(iotProtocolFactor);
            }
        }

        for (IotProtocolFactor iotProtocolFactor : persistIotProtocolFactorList) {
            if (iotProtocolFactorList.contains(iotProtocolFactor)) {
                continue;
            } else {
                iotProtocolFactor.setIsDeleted(true);
//                iotProtocolFactor.setOperator(operator);
            }
        }

        return persistIotProtocolFactorList;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public ResultDTO<Integer> deleteIotProtocol(Long[] iotProtocolIds, Long modifyOperatorId) {
        if (ArrayUtils.isEmpty(iotProtocolIds)) {
            LOGGER.error(ErrorCode.ERROR_7004.getMessage());
            return ResultDTO.newFail(ErrorCode.ERROR_7004.getCode(),
                    ErrorCode.ERROR_7004.getMessage());
        }

        List<IotProtocolQueryDTO> iotProtocolQueryList = iotDeviceTypeDao.getIotDeviceTypeByIotProtocolId(Lists
                .newArrayList(iotProtocolIds));
        if (iotProtocolQueryList.size() > 0) {
            StringBuffer sb = new StringBuffer();
            for (int index = 0; index < iotProtocolQueryList.size(); index++) {
                IotProtocolQueryDTO iotProtocolQueryDTO = iotProtocolQueryList.get(index);
                if (index == 0) {
                    sb.append(iotProtocolQueryDTO.getProtocolName());
                } else {
                    sb.append("、");
                    sb.append(iotProtocolQueryDTO.getProtocolName());
                }
            }

            return ResultDTO.newFail(ErrorCode.ERROR_7024.getCode(),
                    String.format(ErrorCode.ERROR_7024.getMessage(), sb
                    .toString()));
        }

        try {
            for (Long iotProjectId : iotProtocolIds) {
                iotProtocolDao.deleteByIotProtocolId(iotProjectId, modifyOperatorId);
                iotProtocolFactorDao.deleteByIotProtocolId(iotProjectId, modifyOperatorId);
            }

            return ResultDTO.newSuccess();
        } catch (Exception ee) {
            LOGGER.error("delete project error", ee);
            return ResultDTO.newFail(ErrorCode.ERROR_7001.getCode(),
                    ErrorCode.ERROR_7001.getMessage());
        }
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public ResultDTO<IotProtocolDetailDTO> getIotProtocolDetail(Long iotProtocolId) {
        IotProtocol iotProtocol = iotProtocolDao.getByIotProtocolId(iotProtocolId);
        if (iotProtocol.getIsDeleted()) {
            LOGGER.error("{},protocol id is {}", ErrorCode.ERROR_7005.getMessage(), iotProtocolId);
            return ResultDTO.newFail(ErrorCode.ERROR_7005.getCode(),
                    ErrorCode.ERROR_7005.getMessage());
        }

        IotProtocolDetailDTO iotProtocolDetailDTO = new IotProtocolDetailDTO();
        BeanUtils.copyProperties(iotProtocol, iotProtocolDetailDTO);
        iotProtocolDetailDTO.setIotFactors(iotProtocolFactorDao.getIotProtocolFactorByiotProtocolId(iotProtocol
                .getIotProtocolId()));

        return ResultDTO.newSuccess(iotProtocolDetailDTO);
    }

    @Override
    public Long getIdByCode(String protocolCode) {
        return iotProtocolDao.getIdByCode(protocolCode);
    }

    @Override
    public String getCodeByDeviceTypeCode(String deviceTypeCode, Long projectId) {
        return iotProtocolDao.getCodeByIotDeviceTypeCode(deviceTypeCode, projectId);
    }
}

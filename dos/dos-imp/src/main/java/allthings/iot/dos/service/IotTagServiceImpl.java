package allthings.iot.dos.service;

import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.api.IotDeviceTypeService;
import allthings.iot.dos.api.IotTagService;
import allthings.iot.dos.constant.ErrorCode;
import allthings.iot.dos.dao.IotDeviceTagDao;
import allthings.iot.dos.dao.IotTagDao;
import allthings.iot.dos.dto.IotDeviceDTO;
import allthings.iot.dos.dto.IotDeviceTagDTO;
import allthings.iot.dos.dto.IotTagDTO;
import allthings.iot.dos.dto.query.IotTagQueryDTO;
import allthings.iot.dos.model.IotDeviceTag;
import allthings.iot.dos.model.IotTag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :  luhao
 * @FileName :  IotTagBizImpl
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
@Service("iotTagService")
public class IotTagServiceImpl implements IotTagService {
    @Autowired
    private IotTagDao iotTagDao;

    @Autowired
    private IotDeviceTagDao iotDeviceTagDao;
    @Autowired
    private IotDeviceTypeService iotDeviceTypeService;

    @Override
    public ResultDTO<List<IotTagQueryDTO>> getIotTagList(IotTagDTO iotTagDTO) {

        ResultDTO<Integer> judge = iotDeviceTypeService.judgeProject(iotTagDTO.getIotProjectId(),
                iotTagDTO.getCreateOperatorId(), iotTagDTO.getRoleCode());
        if (!judge.isSuccess()) {
            return ResultDTO.newFail(ErrorCode.ERROR_8014.getMessage());
        }

        Long iotProjectId = iotTagDTO.getIotProjectId();
        List<IotTagQueryDTO> list = iotTagDao.getAllByIsDeleted(false, iotProjectId);
        return ResultDTO.newSuccess(list);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public ResultDTO<List<IotDeviceDTO>> getDeviceByIotTagIdAndIotProjectId(IotTagQueryDTO tagQueryDTO, Boolean choose) {

        ResultDTO<Integer> judge = iotDeviceTypeService.judgeProject(tagQueryDTO.getIotProjectId(),
                tagQueryDTO.getCreateOperatorId(), tagQueryDTO.getRoleCode());
        if (!judge.isSuccess()) {
            return ResultDTO.newFail(ErrorCode.ERROR_8014.getMessage());
        }

        Long iotTagId = tagQueryDTO.getIotTagId();
        Long iotProjectId = tagQueryDTO.getIotProjectId();
        String keywords = tagQueryDTO.getKeywords();
        if (StringUtils.isEmpty(keywords)) {
            keywords = "";
        }
        if (iotTagId == null) {
            return ResultDTO.newFail(ErrorCode.ERROR_9002.getCode(),
                    ErrorCode.ERROR_9002.getMessage());
        }
        List<IotDeviceDTO> list = new ArrayList<>();
        if (choose) {
            if (StringUtils.isEmpty(keywords)) {
                list = iotTagDao.getDeviceByIotTagIdAndIotProjectId(iotTagId, iotProjectId);
            } else {
                list = iotTagDao.getDeviceByIotTagIdAndIotProjectId(iotTagId, iotProjectId, "%" + keywords + "%");
            }
        } else {
            if (StringUtils.isEmpty(keywords)) {
                list = iotTagDao.getUnchooseDeviceByIotTagIdAndIotProjectId(iotTagId, iotProjectId);
            } else {
                list = iotTagDao.getUnchooseDeviceByIotTagIdAndIotProjectId(iotTagId, iotProjectId,
                        "%" + keywords + "%");
            }
        }
        return ResultDTO.newSuccess(list);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public ResultDTO<Integer> deleteTagByTagId(IotTagDTO iotTagDTO) {

        ResultDTO<Integer> judge = iotDeviceTypeService.judgeProject(iotTagDTO.getIotProjectId(),
                iotTagDTO.getCreateOperatorId(), iotTagDTO.getRoleCode());
        if (!judge.isSuccess()) {
            return ResultDTO.newFail(ErrorCode.ERROR_8014.getMessage());
        }

        Long iotTagId = iotTagDTO.getIotTagId();
        Long iotProjectId = iotTagDTO.getIotProjectId();
        Long operatorId = iotTagDTO.getCreateOperatorId();
        iotTagDao.deleteAllDeviceByTagId(iotTagId, operatorId);
        iotTagDao.deleteTagByTagId(iotTagId, iotProjectId, operatorId);
        return ResultDTO.newSuccess();
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public ResultDTO<Integer> saveTag(IotTagDTO iotTagDto) {

        ResultDTO<Integer> judge = iotDeviceTypeService.judgeProject(iotTagDto.getIotProjectId(),
                iotTagDto.getCreateOperatorId(), iotTagDto.getRoleCode());
        if (!judge.isSuccess()) {
            return ResultDTO.newFail(ErrorCode.ERROR_8014.getMessage());
        }

        int maxNameLen = 32;
        if (StringUtils.isEmpty(iotTagDto.getTagName()) || iotTagDto.getTagName().trim().length() > maxNameLen) {
            return ResultDTO.newFail(ErrorCode.ERROR_9001.getMessage());
        }
        Integer tagId = iotTagDao.getByTagName(iotTagDto.getTagName(), iotTagDto.getIotProjectId());
        if (tagId == 1) {
            return ResultDTO.newFail(ErrorCode.ERROR_9000.getMessage());
        }
        IotTag iotTag = new IotTag();
        BeanUtils.copyProperties(iotTagDto, iotTag);
        iotTag.setCreateOperatorId(iotTagDto.getCreateOperatorId());
        iotTagDao.saveAndFlush(iotTag);
        return ResultDTO.newSuccess();
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public ResultDTO<Integer> saveDeviceOfTag(IotDeviceTagDTO deviceTagDTO) {

        ResultDTO<Integer> judge = iotDeviceTypeService.judgeProject(deviceTagDTO.getIotProjectId(),
                deviceTagDTO.getCreateOperatorId(), deviceTagDTO.getRoleCode());
        if (!judge.isSuccess()) {
            return ResultDTO.newFail(ErrorCode.ERROR_8014.getMessage());
        }

        List<Long> iotDeviceIds = deviceTagDTO.getIotDeviceIds();
        if (CollectionUtils.isEmpty(iotDeviceIds)) {
            return ResultDTO.newSuccess();
        }
        List<IotDeviceTag> iotDeviceTags = new ArrayList<>();
        for (Long iotDeviceId : iotDeviceIds) {
            IotDeviceTag iotDeviceTag = new IotDeviceTag();
            iotDeviceTag.setCreateOperatorId(deviceTagDTO.getCreateOperatorId());
            iotDeviceTag.setIotDeviceId(iotDeviceId);
            iotDeviceTag.setIotTagId(deviceTagDTO.getIotTagId());
            iotDeviceTags.add(iotDeviceTag);
        }
        iotDeviceTagDao.saveAll(iotDeviceTags);
        return ResultDTO.newSuccess();
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public ResultDTO<Integer> deleteDeviceOfTag(IotDeviceTagDTO deviceTagDTO) {

        ResultDTO<Integer> judge = iotDeviceTypeService.judgeProject(deviceTagDTO.getIotProjectId(),
                deviceTagDTO.getCreateOperatorId(), deviceTagDTO.getRoleCode());
        if (!judge.isSuccess()) {
            return ResultDTO.newFail(ErrorCode.ERROR_8014.getMessage());
        }

        if (CollectionUtils.isEmpty(deviceTagDTO.getIotDeviceIds())) {
            return ResultDTO.newSuccess();
        }
        iotTagDao.deleteDeviceOfTag(deviceTagDTO.getIotDeviceIds(), deviceTagDTO.getIotTagId(), deviceTagDTO.getCreateOperatorId());
        return ResultDTO.newSuccess();
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public ResultDTO<Long> getIdByTagName(String tagName, Long iotProjectId) {
        Long id = iotTagDao.getIdByTagName(tagName, iotProjectId);
        return ResultDTO.newSuccess(id);
    }
}

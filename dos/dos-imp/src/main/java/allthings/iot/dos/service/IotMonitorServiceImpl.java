package allthings.iot.dos.service;

import allthings.iot.common.dto.PageResult;
import allthings.iot.common.dto.QueryResult;
import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.api.IotMonitorService;
import allthings.iot.dos.constant.ErrorCode;
import allthings.iot.dos.dao.IotExternalDevicePlatformDao;
import allthings.iot.dos.dao.IotServiceDao;
import allthings.iot.dos.dao.IotServiceQueryDao;
import allthings.iot.dos.dto.IotExternalDevicePlatformDTO;
import allthings.iot.dos.dto.IotServiceDTO;
import allthings.iot.dos.model.IotExternalDevicePlatform;
import allthings.iot.dos.model.IotService;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :  txw
 * @FileName :  IotMonitorServiceImpl
 * @CreateDate :  2019/5/9
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
@Service("iotMonitorService")
public class IotMonitorServiceImpl implements IotMonitorService {

    @Autowired
    private IotServiceDao iotServiceDao;

    @Autowired
    private IotServiceQueryDao iotServiceQueryDao;

    @Autowired
    private IotExternalDevicePlatformDao iotExternalDevicePlatformDao;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public ResultDTO<Integer> saveService(IotServiceDTO iotServiceDTO) {
        IotService service = iotServiceDao.getIotServiceByIPAndPort(iotServiceDTO.getIp(), iotServiceDTO.getPort());
        if (service != null && iotServiceDTO.getStatus().equals(service.getStatus())) {
            return ResultDTO.newFail(ErrorCode.ERROR_13003.getCode(),
                    ErrorCode.ERROR_13003.getMessage());
        }
        iotServiceDao.save(getModel(iotServiceDTO));
        return ResultDTO.newSuccess();
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public ResultDTO<IotServiceDTO> getIotServiceByIPAndPort(String ip, String port) {
        IotService iotService = iotServiceDao.getIotServiceByIPAndPort(ip, port);
        return ResultDTO.newSuccess(getDto(iotService));
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public ResultDTO<PageResult<IotServiceDTO>> getIotServiceLists(Boolean status, String keywords, Integer pageIndex
            , Integer pageSize) {
        QueryResult<IotService> iotServiceLists = iotServiceQueryDao.getIotServiceLists(status, keywords, pageIndex,
                pageSize);
        if (iotServiceLists.getItems() == null) {
            return ResultDTO.newSuccess(new PageResult<>((int) iotServiceLists.getRowCount(), Lists.newArrayList()));
        }
        List<IotServiceDTO> serviceDTOList = Lists.newArrayList();
        List<IotService> services = iotServiceLists.getItems();
        for (IotService service : services) {
            IotServiceDTO serviceDTO = getDto(service);
            serviceDTOList.add(serviceDTO);
        }
        PageResult<IotServiceDTO> pageResult = new PageResult<>((int) iotServiceLists.getRowCount(), serviceDTOList);
        return ResultDTO.newSuccess(pageResult);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public ResultDTO<PageResult<IotServiceDTO>> getServiceInfoTopology() {
        QueryResult<IotService> iotServiceLists = iotServiceQueryDao.getServiceInfoTopology();
        List<IotService> items = iotServiceLists.getItems();
        if (items == null) {
            return ResultDTO.newSuccess(new PageResult<>((int) iotServiceLists.getRowCount(), Lists.newArrayList()));
        }

        List<IotServiceDTO> serviceDTOList = Lists.newArrayList();
        for (IotService service : items) {
            IotServiceDTO serviceDTO = getDto(service);
            serviceDTOList.add(serviceDTO);
        }

        List<IotExternalDevicePlatform> platFormList = iotExternalDevicePlatformDao.getPlatFormList();
        for (IotExternalDevicePlatform iotExternalDevicePlatform : platFormList) {
            IotServiceDTO iotService = new IotServiceDTO();
            iotService.setServiceName(iotExternalDevicePlatform.getPlatformName());
            iotService.setStatus(iotExternalDevicePlatform.getStatus());
            iotService.setDependencyService(iotExternalDevicePlatform.getDependencyService());
            iotService.setLevels(3);
            serviceDTOList.add(iotService);
        }
        PageResult<IotServiceDTO> pageResult = new PageResult<>(
                (int) iotServiceLists.getRowCount() + platFormList.size(), serviceDTOList);
        return ResultDTO.newSuccess(pageResult);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public ResultDTO<Integer> updateService(IotServiceDTO iotServiceDTO) {
        IotService service = iotServiceDao.getIotServiceByIPAndPort(iotServiceDTO.getIp(), iotServiceDTO.getPort());
        if (service != null && !service.getIotServiceId().equals(iotServiceDTO.getIotServiceId())) {
            return ResultDTO.newFail(ErrorCode.ERROR_13004.getCode(),
                    ErrorCode.ERROR_13004.getMessage());
        }
        iotServiceDao.save(getModel(iotServiceDTO));
        return ResultDTO.newSuccess();
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public ResultDTO<Integer> deleteService(Long iotServiceId) {
        iotServiceDao.deleteByIotServiceId(iotServiceId);
        return ResultDTO.newSuccess();
    }

    @Override
    public ResultDTO<List<IotExternalDevicePlatformDTO>> getPlatFormList() {
        List<IotExternalDevicePlatformDTO> platformDTOS = Lists.newArrayList();
        List<IotExternalDevicePlatform> platforms = iotExternalDevicePlatformDao.getPlatFormList();
        for (IotExternalDevicePlatform platform : platforms) {
            IotExternalDevicePlatformDTO platformDTO = new IotExternalDevicePlatformDTO();
            BeanUtils.copyProperties(platform, platformDTO);
            platformDTOS.add(platformDTO);
        }
        return ResultDTO.newSuccess(platformDTOS);
    }

    @Override
    public ResultDTO<IotExternalDevicePlatformDTO> getPlatFormByCode(String code) {
        IotExternalDevicePlatform platform = iotExternalDevicePlatformDao.getByCode(code);
        if (platform == null) {
            return ResultDTO.newFail("");
        }
        IotExternalDevicePlatformDTO platformDTO = new IotExternalDevicePlatformDTO();
        BeanUtils.copyProperties(platform, platformDTO);
        return ResultDTO.newSuccess(platformDTO);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public ResultDTO<IotExternalDevicePlatformDTO> savePlatForm(IotExternalDevicePlatformDTO iotExternalDevicePlatformDTO) {
        IotExternalDevicePlatform iotExternalDevicePlatform = new IotExternalDevicePlatform();
        BeanUtils.copyProperties(iotExternalDevicePlatformDTO, iotExternalDevicePlatform);
        IotExternalDevicePlatform platform = iotExternalDevicePlatformDao.save(iotExternalDevicePlatform);
        IotExternalDevicePlatformDTO platformDTO = new IotExternalDevicePlatformDTO();
        BeanUtils.copyProperties(platform, platformDTO);
        return ResultDTO.newSuccess(platformDTO);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public ResultDTO<List<IotExternalDevicePlatformDTO>> getPlatFormByDependencyService(String serviceCode) {
        List<IotExternalDevicePlatform> platformList =
                iotExternalDevicePlatformDao.getPlatFormByDependencyService(serviceCode);
        List<IotExternalDevicePlatformDTO> platformDTOList = Lists.newArrayList();
        for (IotExternalDevicePlatform platform : platformList) {
            IotExternalDevicePlatformDTO platformDTO = new IotExternalDevicePlatformDTO();
            BeanUtils.copyProperties(platform, platformDTO);
            platformDTOList.add(platformDTO);

        }

        return ResultDTO.newSuccess(platformDTOList);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public ResultDTO<List<IotServiceDTO>> saveServices(List<IotServiceDTO> items) {
        List<IotService> lists = new ArrayList<>();
        for (IotServiceDTO iotServiceDTO : items) {
            lists.add(getModel(iotServiceDTO));
        }
        List<IotService> services = iotServiceDao.saveAll(lists);
        List<IotServiceDTO> serviceDTOList = Lists.newArrayList();
        for (IotService service : services) {
            IotServiceDTO serviceDTO = getDto(service);
            serviceDTOList.add(serviceDTO);
        }
        return ResultDTO.newSuccess(serviceDTOList);
    }

    private IotService getModel(IotServiceDTO iotServiceDTO) {
        if (iotServiceDTO == null) {
            return null;
        }
        IotService iotService = new IotService();
        BeanUtils.copyProperties(iotServiceDTO, iotService);
        return iotService;
    }

    private IotServiceDTO getDto(IotService iotService) {
        if (iotService == null) {
            return null;
        }
        IotServiceDTO iotServiceDTO = new IotServiceDTO();
        BeanUtils.copyProperties(iotService, iotServiceDTO);
        if (iotService.getInputDate() != null) {
            iotServiceDTO.setInputDate(iotService.getInputDate().getTime());
        }
        if (iotService.getStampDate() != null) {
            iotServiceDTO.setStampDate(iotService.getStampDate().getTime());
        }
        return iotServiceDTO;
    }
}

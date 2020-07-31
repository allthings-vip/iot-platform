package allthings.iot.dos.service;

import allthings.iot.dos.dao.IotPassTypeDao;
import allthings.iot.dos.dto.IotPassTypeDTO;
import allthings.iot.dos.model.IotPassType;
import allthings.iot.dos.api.IotPassTypeService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author luhao
 * @date 2020-2-17 10:48
 */
@Slf4j
@Service("iotPassTypeService")
public class IotPassTypeServiceImpl implements IotPassTypeService {
    private IotPassTypeDao iotPassTypeDao;

    @Autowired
    public IotPassTypeServiceImpl(IotPassTypeDao iotPassTypeDao) {
        this.iotPassTypeDao = iotPassTypeDao;
    }

    @Override
    public List<IotPassTypeDTO> getAllByIotDeviceType(Long iotDeviceTypeId) {
        List<IotPassType> iotPassTypes = iotPassTypeDao.getAllByIotDeviceType(iotDeviceTypeId, false);

        List<IotPassTypeDTO> iotPassTypeDTOS = Lists.newArrayList();
        if (CollectionUtils.isEmpty(iotPassTypes)) {
            log.warn("根据设备类型：[{}]查询的通道类型为空.", iotDeviceTypeId);
            return iotPassTypeDTOS;
        }

        for (IotPassType iotPassType : iotPassTypes) {
            IotPassTypeDTO iotPassTypeDTO = new IotPassTypeDTO();
            BeanUtils.copyProperties(iotPassType, iotPassTypeDTO);
            iotPassTypeDTOS.add(iotPassTypeDTO);
        }
        
        return iotPassTypeDTOS;
    }
}

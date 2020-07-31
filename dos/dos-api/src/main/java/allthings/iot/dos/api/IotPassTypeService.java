package allthings.iot.dos.api;

import allthings.iot.dos.dto.IotPassTypeDTO;

import java.util.List;

/**
 * @author luhao
 * @date 2020-2-17 10:21
 */
public interface IotPassTypeService {

    /**
     * 根据设备类型查询所有对应的通道类型
     *
     * @param iotDeviceTypeId
     * @return
     */
    List<IotPassTypeDTO> getAllByIotDeviceType(Long iotDeviceTypeId);


}

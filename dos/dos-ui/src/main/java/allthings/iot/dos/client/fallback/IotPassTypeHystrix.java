package allthings.iot.dos.client.fallback;

import allthings.iot.dos.client.api.IotPassTypeApi;
import allthings.iot.dos.client.constant.Constant;
import allthings.iot.dos.dto.IotPassTypeDTO;
import org.springframework.stereotype.Component;
import allthings.iot.common.dto.ResultDTO;

import java.util.List;

/**
 * @author luhao
 * @date 2020/2/17 13:22
 */
@Component
public class IotPassTypeHystrix implements IotPassTypeApi {
    @Override
    public ResultDTO<List<IotPassTypeDTO>> getAllByIotDeviceType(Long iotDeviceTypeId) {
        return Constant.FALL_CULL_BACK;
    }
}

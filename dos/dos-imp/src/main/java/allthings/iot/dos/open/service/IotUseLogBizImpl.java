package allthings.iot.dos.open.service;

import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.api.IotUseLogService;
import allthings.iot.dos.dto.IotUseLogDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: fengchangxin
 * @create: 2019-12-16 16:17
 * @description:
 **/
@Service("iotUseLogBiz")
public class IotUseLogBizImpl implements IotUseLogService {
    @Autowired
    private IotUseLogService iotUseLogService;

    @Override
    public ResultDTO saveUseLog(IotUseLogDTO iotUseLogDTO) {
        return iotUseLogService.saveUseLog(iotUseLogDTO);
    }
}

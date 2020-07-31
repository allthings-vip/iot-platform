package allthings.iot.dos.api;

import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.dto.IotUseLogDTO;

/**
 * @author: fengchangxin
 * @create: 2019-12-16 15:46
 * @description:
 **/
public interface IotUseLogService {
    /**
     * 保存接口调用记录
     *
     * @param iotUseLogDTO
     * @return
     */
    ResultDTO saveUseLog(IotUseLogDTO iotUseLogDTO);
}

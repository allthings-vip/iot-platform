package allthings.iot.dos.open.api;

import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.dto.IotUseLogDTO;

/**
 * @author: fengchangxin
 * @create: 2019-12-16 16:16
 * @description:
 **/
public interface IotUseLogBiz {
    /**
     * 保存调用记录
     *
     * @param iotUseLogDTO
     * @return
     */
    ResultDTO saveUseLog(IotUseLogDTO iotUseLogDTO);
}

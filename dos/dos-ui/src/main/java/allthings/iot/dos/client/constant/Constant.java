package allthings.iot.dos.client.constant;

import allthings.iot.common.dto.ResultDTO;

/**
 * @author tyf
 * @date 2019/05/15 15:40:38
 */
public interface Constant {
    /**
     * 熔断的错误提示
     */
    ResultDTO FALL_CULL_BACK = ResultDTO.newFail(-1, "系统出现异常，请稍后再试");
}

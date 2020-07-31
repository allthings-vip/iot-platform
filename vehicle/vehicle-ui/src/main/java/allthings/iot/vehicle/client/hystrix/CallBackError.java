package allthings.iot.vehicle.client.hystrix;

import allthings.iot.common.dto.Result;

/**
 * @description:
 * @author: fengchangxin
 * @create: 2019-05-13 14:18
 */

public class CallBackError {
    public static final Result CALLBACK_ERROR = Result.newFaild("触发熔断");
}

package allthings.iot.dos.open.controller;

import allthings.iot.dos.api.IotUseLogService;
import allthings.iot.dos.client.open.IotUseLogApi;
import allthings.iot.dos.dto.IotUseLogDTO;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import allthings.iot.common.dto.ResultDTO;

/**
 * @author: fengchangxin
 * @create: 2019-12-16 16:19
 * @description:
 **/
@RestController
public class IotUseLogController implements IotUseLogApi {
    private static final Logger LOGGER = LoggerFactory.getLogger(IotUseLogController.class);

    @Autowired
    private IotUseLogService useLogService;

    @Override
    public ResultDTO saveUseLog(@RequestBody IotUseLogDTO iotUseLogDTO) {
        try {
            return useLogService.saveUseLog(iotUseLogDTO);
        } catch (Exception e) {
            LOGGER.error("保存接口调用记录失败：参数：{}，异常：{}", JSON.toJSONString(iotUseLogDTO), e);
            return ResultDTO.newFail("保存接口调用记录失败");
        }
    }
}

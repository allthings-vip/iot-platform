package allthings.iot.dss.test;

import allthings.iot.dss.api.IotDssService;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author tyf
 * @date 2019/04/01 9:25
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IotDssServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(IotDssServiceTest.class);

    @Autowired
    private IotDssService iotDssService;

    @Test
    public void getIotDeviceIdByDeviceCodeTest() {
        Object bizReturn = iotDssService.getIotDeviceIdByDeviceCode("浙A990挂");
        LOGGER.info(JSON.toJSONString(bizReturn));
    }

    @Test
    public void getEventPushUrlByIotProjectIdTest() {
        Object bizReturn = iotDssService.getEventPushUrlByIotProjectId(307L);
        LOGGER.info(JSON.toJSONString(bizReturn));
    }

    @Test
    public void getIotProjectIdByDeviceCodeTest() {
        Object bizReturn = iotDssService.getIotProjectIdByDeviceCode("粤BFW515");
        LOGGER.info(JSON.toJSONString(bizReturn));
    }

    @Test
    public void getProtocolByDeviceCodeTest() {
        Object bizReturn = iotDssService.getProtocolByDeviceCode("粤BFW515");
        LOGGER.info(JSON.toJSONString(bizReturn));
    }

}

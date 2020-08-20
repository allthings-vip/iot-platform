package allthings.iot.dos.test.service;

import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.api.IotMessageService;
import allthings.iot.dos.dto.query.IotMessageManagerDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author tyf
 * @date 2020/08/11 14:20:47
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class IotMessageServiceTest {

    @Autowired
    private IotMessageService messageService;

    @Test
    public void sendMsgCode() {
        IotMessageManagerDTO messageDto = new IotMessageManagerDTO();
//        messageDto.setMobileNumber("13857870095");
        messageDto.setMobileNumber("19906792962");
        ResultDTO<Integer> resultDTO = messageService.sendMessageCode(messageDto);
        System.out.println(resultDTO.toString());
    }

    @Test
    public void validateIdentifyCodeTest() {
        IotMessageManagerDTO messageDto = new IotMessageManagerDTO();
        messageDto.setMobileNumber("13857870095");
        messageDto.setCode("009117");
        ResultDTO<Integer> resultDTO = messageService.validateIdentifyCode(messageDto);
        System.out.println(resultDTO.toString());
    }
}

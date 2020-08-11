package allthings.iot.dos.service;

import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.IotDosServiceConfig;
import allthings.iot.dos.api.IotMessageService;
import allthings.iot.dos.dto.query.IotMessageManagerDTO;
import allthings.iot.util.sms.ErrorCodeEnum;
import allthings.iot.util.sms.dto.SendMsgResponseDto;
import allthings.iot.util.sms.dto.ShortMessageDto;
import allthings.iot.util.sms.service.imp.AliShortMessageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author tyf
 * @date 2020/08/11 09:33:53
 */
@Service("iotMessageService")
public class IotMessageServiceImpl implements IotMessageService {

    @Autowired
    private AliShortMessageServiceImpl messageService;
    @Autowired
    IotDosServiceConfig config;

    @Override
    public ResultDTO<Integer> sendMessageCode(IotMessageManagerDTO iotMessageManagerDTO) {
        ShortMessageDto messageDto = new ShortMessageDto();
        messageDto.setSignName(config.getSmsSignName());
        messageDto.setTemplateCode(config.getSmsTemplateCode());
        messageDto.setCodeDigits(6);
        messageDto.setAction("SendSms");
        messageDto.setPhoneNumbers(iotMessageManagerDTO.getMobileNumber());
        ResultDTO<SendMsgResponseDto> resultDTO = messageService.sendMsg(messageDto);
        if (!resultDTO.isSuccess()) {
            return ResultDTO.newFail("验证码发送失败");
        }
        SendMsgResponseDto responseDto = resultDTO.getData();
        if (ErrorCodeEnum.OK.getCode().equals(responseDto.getCode())) {
            return ResultDTO.newSuccess();
        }
        return ResultDTO.newFail(responseDto.getMessage());
    }

    @Override
    public ResultDTO<Integer> validateIdentifyCode(IotMessageManagerDTO iotMessageManagerDTO) {
        ShortMessageDto messageDto = new ShortMessageDto();
        messageDto.setPhoneNumbers(iotMessageManagerDTO.getMobileNumber());
        messageDto.setAction("SendSms");
        messageDto.setTemplateCode(config.getSmsTemplateCode());
        messageDto.setSignName(config.getSmsSignName());
        messageDto.setVerificationCode(iotMessageManagerDTO.getCode());
        return messageService.validateIdentifyCode(messageDto);
    }
}

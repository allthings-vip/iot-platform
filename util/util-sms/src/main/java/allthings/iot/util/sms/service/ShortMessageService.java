package allthings.iot.util.sms.service;

import allthings.iot.common.dto.ResultDTO;
import allthings.iot.util.sms.dto.SendMsgResponseDto;
import allthings.iot.util.sms.dto.ShortMessageDto;

/**
 * @author tyf
 * @date 2020/08/10 10:26:05
 */
public interface ShortMessageService {

    ResultDTO<SendMsgResponseDto> sendMsg(ShortMessageDto shortMessageDto);

    ResultDTO validateIdentifyCode(ShortMessageDto shortMessageDto);

}

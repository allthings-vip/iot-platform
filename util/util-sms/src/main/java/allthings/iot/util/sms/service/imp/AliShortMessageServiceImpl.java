package allthings.iot.util.sms.service.imp;

import allthings.iot.common.dto.ResultDTO;
import allthings.iot.util.misc.StringUtils;
import allthings.iot.util.redis.ICentralCacheService;
import allthings.iot.util.sms.ErrorCodeEnum;
import allthings.iot.util.sms.SmsProperties;
import allthings.iot.util.sms.dto.SendMsgResponseDto;
import allthings.iot.util.sms.dto.ShortMessageDto;
import allthings.iot.util.sms.service.ShortMessageService;
import com.alibaba.fastjson.JSON;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Random;


/**
 * @author tyf
 * @date 2020/08/10 18:43:12
 */
@Service("shortMessageService")
public class AliShortMessageServiceImpl implements ShortMessageService {
    private static Logger log = LoggerFactory.getLogger(AliShortMessageServiceImpl.class);
    private static final String SMS_CACHE_KEY = "sms:%s:%s";
    @Autowired
    private SmsProperties smsProperties;
    @Autowired
    private ICentralCacheService cacheService;

    @Override
    public ResultDTO<SendMsgResponseDto> sendMsg(ShortMessageDto shortMessageDto) {
        DefaultProfile profile = DefaultProfile.getProfile(smsProperties.getRegionId(), smsProperties.getAccessKeyId(), smsProperties.getAccessSecret());
        IAcsClient client = new DefaultAcsClient(profile);
        String code = getMsgCode(shortMessageDto.getCodeDigits());
        Map<String, String> templateParam = Maps.newHashMap();
        templateParam.put("code", code);
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysVersion(smsProperties.getVersion());
        request.setSysDomain(smsProperties.getDomain());
        request.setSysAction(shortMessageDto.getAction());
        request.putQueryParameter("RegionId", smsProperties.getRegionId());
        request.putQueryParameter("PhoneNumbers", shortMessageDto.getPhoneNumbers());
        request.putQueryParameter("SignName", shortMessageDto.getSignName());
        request.putQueryParameter("TemplateCode", shortMessageDto.getTemplateCode());
        request.putQueryParameter("SmsUpExtendCode", shortMessageDto.getSmsUpExtendCode());
        request.putQueryParameter("OutId", shortMessageDto.getOutId());
        request.putQueryParameter("TemplateParam", JSON.toJSONString(templateParam));
        try {
            CommonResponse response = client.getCommonResponse(request);
            if (response.getHttpStatus() != 200) {
                log.warn("短信发送失败，请求失败，参数：{}，返回结果：{}", shortMessageDto.toString(), JSON.toJSONString(response));
                return ResultDTO.newFail("发送短信失败");
            }
            log.info("短信发送返回结果：{}", JSON.toJSONString(response));
            SendMsgResponseDto responseDto = JSON.parseObject(response.getData(), SendMsgResponseDto.class);
            if (ErrorCodeEnum.OK.getCode().equals(responseDto.getCode())) {
                cacheService.putMapValue(String.format(SMS_CACHE_KEY, shortMessageDto.getAction(),
                        shortMessageDto.getTemplateCode()), shortMessageDto.getPhoneNumbers(), code,
                        smsProperties.getValidPeriod() * 60L);
            }
            return ResultDTO.newSuccess(responseDto);
        } catch (ServerException e) {
            log.error(String.format("发送短信异常，参数：%s", shortMessageDto.toString()), e);
            return ResultDTO.newFail("发送短信失败");
        } catch (ClientException e) {
            log.error(String.format("发送短信异常，参数：%s", shortMessageDto.toString()), e);
            return ResultDTO.newFail("发送短信失败");
        }
    }

    @Override
    public ResultDTO<Integer> validateIdentifyCode(ShortMessageDto shortMessageDto) {
        String code = cacheService.getMapField(String.format(SMS_CACHE_KEY, shortMessageDto.getAction(),
                shortMessageDto.getTemplateCode()), shortMessageDto.getPhoneNumbers(), String.class);
        if (StringUtils.isBlank(code)) {
            return ResultDTO.newFail("验证码错误");
        }
        if (!code.equals(shortMessageDto.getVerificationCode())) {
            cacheService.removeMapField(String.format(SMS_CACHE_KEY, shortMessageDto.getAction(),
                    shortMessageDto.getTemplateCode()), shortMessageDto.getPhoneNumbers());
            return ResultDTO.newFail("验证码错误");
        }
        cacheService.removeMapField(String.format(SMS_CACHE_KEY, shortMessageDto.getAction(),
                shortMessageDto.getTemplateCode()), shortMessageDto.getPhoneNumbers());
        return ResultDTO.newSuccess();
    }

    private String getMsgCode(Integer dodeDigits) {
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for (int j = 0; j < dodeDigits; j++) {
            code.append(random.nextInt(10));
        }
        return code.toString();
    }
}

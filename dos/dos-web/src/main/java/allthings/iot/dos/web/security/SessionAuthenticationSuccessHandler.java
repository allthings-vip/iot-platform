package allthings.iot.dos.web.security;

import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.api.IotUserService;
import allthings.iot.dos.client.api.IotUserApi;
import allthings.iot.dos.constant.Constants;
import allthings.iot.dos.constant.ErrorCode;
import allthings.iot.dos.dto.IotUserDTO;
import allthings.iot.dos.dto.query.IotLogDTO;
import allthings.iot.dos.web.kapthcha.KaptchaService;
import allthings.iot.dos.web.message.IotDosLoggerProducer;
import allthings.iot.util.redis.ICentralCacheService;
import com.alibaba.fastjson.JSON;
import com.cuisongliu.kaptcha.autoconfigure.util.KaptchaUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author :  luhao
 * @FileName :  SessionAuthenticationSuccessHandler
 * @CreateDate :  2018-5-9
 * @Description :
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
public class SessionAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    /**
     * 登录超过5次传的参数名
     */
    private static final String CODE = "code";

    private ICentralCacheService iotRedisFactory;

    private IotUserApi iotUserApi;

    private KaptchaService kaptchaService;

    private IotDosLoggerProducer producer;

    public SessionAuthenticationSuccessHandler() {
    }

    public SessionAuthenticationSuccessHandler(ICentralCacheService iotRedisFactory, IotUserApi iotUserApi,
                                               KaptchaService kaptchaService, IotDosLoggerProducer producer) {
        this.iotRedisFactory = iotRedisFactory;
        this.iotUserApi = iotUserApi;
        this.kaptchaService = kaptchaService;
        this.producer = producer;
    }

    /**
     * Called when a user has been successfully authenticated.
     *
     * @param request        the request which caused the successful authentication
     * @param response       the response
     * @param authentication the <tt>Authentication</tt> object which was created during
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication
            authentication) throws IOException {
        String ip = SessionAuthenticationFailureHandler.getIpAddress(request);
        String value = iotRedisFactory.getObject(ip, String.class);
        ResultDTO result = null;
        if (value != null && Integer.parseInt(value) >= SessionAuthenticationFailureHandler.MAX_ERROR_NUMBER) {
            String code = request.getParameter(CODE);
            if (StringUtils.isEmpty(code)) {
                ResultDTO resultDTO = kaptchaService.dosKaptchaGenerator(request);
                result = ResultDTO.newFail(ErrorCode.ERROR_8007.getCode(), ErrorCode.ERROR_8007.getMessage());
                result.setData(resultDTO.getData());
            } else {
                Boolean validateResult = KaptchaUtil.validationKaptcha(CODE, true);
                if (!validateResult) {
                    ResultDTO resultDTO = kaptchaService.dosKaptchaGenerator(request);
                    result = ResultDTO.newFail(ErrorCode.ERROR_8025.getCode(), ErrorCode.ERROR_8025.getMessage());
                    result.setData(resultDTO.getData());
                }
            }
        }
        if (result == null) {
            iotRedisFactory.removeObject(ip);
            ResultDTO<IotUserDTO> bizReturn = iotUserApi.getUserByUsername(authentication.getName());
            IotUserDTO iotUserDTO = bizReturn.getData();

            if (iotUserDTO != null) {
                iotUserDTO.setPassword(null);
                result = ResultDTO.newSuccess(iotUserDTO);
            }
            if (iotUserDTO != null) {
                saveProjectLogger(iotUserDTO);
            }
        }


        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.getWriter().println(JSON.toJSONString(result));
        response.getWriter().flush();
        response.getWriter().close();
    }

    /**
     * 保存用户登录日志
     *
     * @param iotUser
     */
    private void saveProjectLogger(IotUserDTO iotUser) {
        IotLogDTO logDTO = new IotLogDTO();
        logDTO.setUsername(iotUser.getUsername());
        logDTO.setUserId(iotUser.getIotUserId());
        logDTO.setLogTypeCode(Constants.SYSTEM_LOGGER_TYPE);
        logDTO.setAssociationType("IotUser");
        logDTO.setCreateOperatorId(iotUser.getIotUserId());
        logDTO.setModifyOperatorId(iotUser.getIotUserId());
        logDTO.setIotProjectId(0L);
        logDTO.setAssociationId(iotUser.getIotUserId());

        logDTO.setLogContent("用户【" + iotUser.getUsername() + "】登录成功");
        producer.sendToQueue(logDTO);
    }
}

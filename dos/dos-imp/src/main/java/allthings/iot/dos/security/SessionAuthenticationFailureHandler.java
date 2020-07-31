package allthings.iot.dos.security;

import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.constant.ErrorCode;
import allthings.iot.dos.kapthcha.KaptchaService;
import allthings.iot.util.redis.ICentralCacheService;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author :  luhao
 * @FileName :  SessionAuthenticationFailureHandler
 * @CreateDate :  2018-5-10
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
public class SessionAuthenticationFailureHandler implements AuthenticationFailureHandler {
    /**
     * 登陆错误次数，超过需要验证码
     */
    public static final int MAX_ERROR_NUMBER = 5;
    private static final Logger LOGGER = LoggerFactory.getLogger(SessionAuthenticationFailureHandler.class);
    private ICentralCacheService iotRedisFactory;
    private KaptchaService kaptchaService;

    public SessionAuthenticationFailureHandler() {

    }

    public SessionAuthenticationFailureHandler(ICentralCacheService iotRedisFactory, KaptchaService kaptchaService) {
        this.iotRedisFactory = iotRedisFactory;
        this.kaptchaService = kaptchaService;
    }

    /**
     * 获取真实ip
     *
     * @param request
     * @return
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if (ip.indexOf(",") != -1) {
                ip = ip.split(",")[0];
            }
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
            LOGGER.info("Proxy-Client-IP ip: " + ip);
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
            LOGGER.info("WL-Proxy-Client-IP ip: " + ip);
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
            LOGGER.info("HTTP_CLIENT_IP ip: " + ip);
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            LOGGER.info("HTTP_X_FORWARDED_FOR ip: " + ip);
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
            LOGGER.info("X-Real-IP ip: " + ip);
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            LOGGER.info("getRemoteAddr ip: " + ip);
        }

        return ip;
    }

    /**
     * Called when an authentication attempt fails.
     *
     * @param request   the request during which the authentication attempt occurred.
     * @param response  the response.
     * @param exception the exception which was thrown to reject the authentication
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {

        String ip = getIpAddress(request);
        String value = iotRedisFactory.getObject(ip, String.class);

        int errNumber = 1;
        if (value != null) {
            errNumber = Integer.parseInt(value);
        }

        ResultDTO result;
        if (exception instanceof InternalAuthenticationServiceException) {
            result = ResultDTO.newFail(ErrorCode.ERROR_8026.getCode(), ErrorCode.ERROR_8026.getMessage());
        } else if (errNumber >= MAX_ERROR_NUMBER) {
            ResultDTO resultDTO = kaptchaService.dosKaptchaGenerator(request);
            result = ResultDTO.newFail(ErrorCode.ERROR_5054.getCode(),
                    String.format(ErrorCode.ERROR_5054.getMessage(), MAX_ERROR_NUMBER));
            result.setData(resultDTO.getData());
        } else {
            result = ResultDTO.newFail(ErrorCode.ERROR_8027.getCode(), ErrorCode.ERROR_8027.getMessage());
        }

        iotRedisFactory.putObjectWithExpireTime(ip, String.valueOf(errNumber + 1), 30 * 60);
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.getWriter().println(JSON.toJSONString(result));
        response.getWriter().flush();
        response.getWriter().close();
    }
}

package allthings.iot.dos.open.security.oauth2;

import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.constant.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.http.AccessTokenRequiredException;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author :  luhao
 * @FileName :  AuthExceptionEntryPoint
 * @CreateDate :  2018-11-16
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
public class AuthExceptionEntryPoint extends OAuth2AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException)
            throws ServletException {
        ResultDTO<String> resultDTO = ResultDTO.newFail(ErrorCode.ERROR_10004.getCode(),
                "认证失败:" + authException.getMessage());
        if (authException instanceof AccessTokenRequiredException) {
            resultDTO = ResultDTO.newFail(ErrorCode.ERROR_10000.getCode(), ErrorCode.ERROR_10000.getMessage());
        } else if (authException instanceof BadCredentialsException) {
            resultDTO = ResultDTO.newFail(ErrorCode.ERROR_10002.getCode(), ErrorCode.ERROR_10002.getMessage());
        } else if (authException instanceof AccountExpiredException) {
            resultDTO = ResultDTO.newFail(ErrorCode.ERROR_10003.getCode(), ErrorCode.ERROR_10003.getMessage());
        }
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(response.getOutputStream(), resultDTO);
        } catch (Exception e) {
            throw new ServletException();
        }
    }
}
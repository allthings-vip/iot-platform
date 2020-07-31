package allthings.iot.dos.security;

import allthings.iot.dos.constant.ErrorCode;
import com.alibaba.fastjson.JSON;
import com.tf56.web.utils.dto.ResultDTO;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author :  luhao
 * @FileName :  UnauthorizedEntryPoint
 * @CreateDate :  2018-5-24
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
public class UnauthorizedEntryPoint implements AuthenticationEntryPoint {
    /**
     * Commences an authentication scheme.
     *
     * <code>ExceptionTranslationFilter</code> will populate the <code>HttpSession</code>
     * attribute named
     * <code>AbstractAuthenticationProcessingFilter.SPRING_SECURITY_SAVED_REQUEST_KEY</code>
     * with the requested target URL before calling this method.
     * <p>
     * Implementations should modify the headers on the <code>ServletResponse</code> as
     * necessary to commence the authentication process.
     *
     * @param request       that resulted in an <code>AuthenticationException</code>
     * @param response      so that the user agent can begin authentication
     * @param authException that caused the invocation
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException
            authException) throws IOException, ServletException {
        ResultDTO<?> result = ResultDTO.buildErrorResult(ErrorCode.ERROR_5000.getCode(),
                ErrorCode.ERROR_5000.getMessage());
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.getWriter().println(JSON.toJSONString(result));
        response.getWriter().flush();
        response.getWriter().close();
    }
}

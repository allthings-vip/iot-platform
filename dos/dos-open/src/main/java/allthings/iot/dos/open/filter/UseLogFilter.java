package allthings.iot.dos.open.filter;

import allthings.iot.dos.client.open.IotUseLogApi;
import allthings.iot.dos.dto.IotUseLogDTO;
import allthings.iot.dos.open.util.HttpUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: fengchangxin
 * @create: 2019-12-18 10:14
 * @description:
 **/
@Component
public class UseLogFilter extends OncePerRequestFilter {
    private static final String AUTH_TOKEN = "/oauth/token";
    @Autowired
    private IotUseLogApi iotUseLogApi;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        String url = request.getRequestURI();
        if (StringUtils.contains(url, AUTH_TOKEN)) {
            IotUseLogDTO useLogDTO = new IotUseLogDTO();
            Object ob = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (ob instanceof String) {
                useLogDTO.setClientId((String) ob);
            }
            
            useLogDTO.setPath(url);
            useLogDTO.setUserIp(HttpUtil.getIpAddress(request));
            iotUseLogApi.saveUseLog(useLogDTO);
        }
        chain.doFilter(request, response);
    }
}

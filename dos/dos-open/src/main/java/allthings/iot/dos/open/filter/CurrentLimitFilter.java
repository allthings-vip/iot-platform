package allthings.iot.dos.open.filter;

import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.constant.ErrorCode;
import allthings.iot.dos.open.util.HttpUtil;
import allthings.iot.util.redis.ICentralCacheService;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * @author :  txw
 * @FileName :  CurrentLimitFilter
 * @CreateDate :  2019/1/4
 * @Description :  dmp
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
@Component
public class CurrentLimitFilter extends OncePerRequestFilter {

    private static final String TOKEN_DOMAIN = "oauth";
    private static final String DATA_DOMAIN = "vechile";
    private static final String MANAGER_DOMAIN = "dmp";
    private static final Long MILL = 1000L;
    private static final Long SECOND = 1L;
    private static final Long MINUTE = 60 * SECOND;
    private static final Long HOUR = 60 * MINUTE;
    private static final Long DAY = 24 * HOUR;
    @Value("${service.count.limit.manager}")
    private Integer managerLimit;
    @Value("${service.count.limit.data}")
    private Integer dataLimit;
    @Value("${service.count.limit.token}")
    private Integer tokenLimit;
    @Autowired
    private ICentralCacheService cache;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        String ip = HttpUtil.getIpAddress(httpServletRequest);
        String requestURI = httpServletRequest.getRequestURI();
        String appKey;
        if (TOKEN_DOMAIN.equals(requestURI.split("/")[2])) {
            appKey = httpServletRequest.getParameter("client_id");
        } else {
            appKey = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }
        Map<String, String> map = cache.getAll(appKey + requestURI, String.class);
        if (DATA_DOMAIN.equals(requestURI.split("/")[2])) {
            if (!CollectionUtils.isEmpty(map) && map.size() > 0) {
                for (String key : map.keySet()) {
                    if (System.currentTimeMillis() - Long.parseLong(key) >= (MINUTE * MILL)) {
                        cache.removeMapField(appKey + requestURI, key);
                    }
                }
                map = cache.getAll(appKey + requestURI, String.class);
            }
            if (map != null && map.size() > dataLimit) {
                PrintWriter pw = httpServletResponse.getWriter();
                pw.print(JSON.toJSONString(ResultDTO.newFail(ErrorCode.ERROR_5003.getCode(),
                        ErrorCode.ERROR_5003.getMessage())));
                pw.flush();
                return;
            }
            cache.putMapValue(appKey + requestURI, System.currentTimeMillis() + "", ip, 60);

        } else if (MANAGER_DOMAIN.equals(requestURI.split("/")[2])) {
            if (!CollectionUtils.isEmpty(map) && map.size() > 0) {
                for (String key : map.keySet()) {
                    if (System.currentTimeMillis() - Long.parseLong(key) >= (MINUTE * MILL)) {
                        cache.removeMapField(appKey + requestURI, key);
                    }
                }
                map = cache.getAll(appKey + requestURI, String.class);
            }
            if (map != null && map.size() > managerLimit) {
                PrintWriter pw = httpServletResponse.getWriter();
                pw.print(JSON.toJSONString(ResultDTO.newFail(ErrorCode.ERROR_5003.getCode(),
                        ErrorCode.ERROR_5003.getMessage())));
                pw.flush();
                return;
            }
            cache.putMapValue(appKey + requestURI, System.currentTimeMillis() + "", ip, 60);
        } else if (TOKEN_DOMAIN.equals(requestURI.split("/")[2])) {
            if (!CollectionUtils.isEmpty(map) && map.size() > 0) {
                for (String key : map.keySet()) {
                    if (System.currentTimeMillis() - Long.parseLong(key) >= (DAY * MILL)) {
                        cache.removeMapField(appKey + requestURI, key);
                    }
                }
                map = cache.getAll(appKey + requestURI, String.class);
            }
            if (map != null && map.size() > tokenLimit) {
                PrintWriter pw = httpServletResponse.getWriter();
                pw.print(JSON.toJSONString(ResultDTO.newFail(ErrorCode.ERROR_5003.getCode(),
                        ErrorCode.ERROR_5003.getMessage())));
                pw.flush();
                return;
            }
            cache.putMapValue(appKey + requestURI, System.currentTimeMillis() + "", ip, 24 * 60 * 60);
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}

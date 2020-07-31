package allthings.iot.dos.filter;

import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.constant.Constants;
import allthings.iot.dos.constant.ErrorCode;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description:
 * @author: fengchangxin
 * @create: 2018-11-23 09:02
 */

public class AuthorityJudgeFilter implements Filter {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorityJudgeFilter.class);
    private String contextPath;

    private String[] uriList = {"/userInfo", "/lt"};
    private Pattern pattern = Pattern.compile("\\d+$");

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String whiteUrlList = filterConfig.getInitParameter(Constants.URI_LIST_PARAM);
        if (StringUtils.isNotEmpty(whiteUrlList)) {
            uriList = whiteUrlList.split(",");
        }

        contextPath = filterConfig.getInitParameter(Constants.CONTEXT_PATH);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletResponse.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        servletRequest.setCharacterEncoding("UTF-8");

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String url = request.getRequestURI().replace(contextPath, "");
        LOGGER.error("请求URL;{}", url);
        if (ArrayUtils.contains(uriList, url)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            url = StringUtils.substringBeforeLast(url, Constants.HOME_ROUTE);
        }

        HttpSession session = request.getSession();

        String authority = (String) session.getAttribute(Constants.AUTHORITY_LIST);
        if (StringUtils.isBlank(authority)) {
            PrintWriter pw = servletResponse.getWriter();
            pw.print(JSON.toJSONString(ResultDTO.newFail(ErrorCode.ERROR_5002.getCode(),
                    ErrorCode.ERROR_5002.getMessage())));
            pw.flush();
            return;
        }

        List<String> urlList = JSON.parseArray(authority, String.class);
        for (String urlTemp : urlList) {
            if (urlTemp.endsWith("}")) {
                urlTemp = StringUtils.substringBeforeLast(urlTemp, Constants.HOME_ROUTE);
            }
            if (url.equals(urlTemp)) {
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
        }

        PrintWriter pw = servletResponse.getWriter();
        pw.print(JSON.toJSONString(ResultDTO.newFail(ErrorCode.ERROR_5002.getCode(),
                ErrorCode.ERROR_5002.getMessage())));
        pw.flush();
    }

    @Override
    public void destroy() {

    }
}

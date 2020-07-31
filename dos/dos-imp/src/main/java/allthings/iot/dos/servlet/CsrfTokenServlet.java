package allthings.iot.dos.servlet;

import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.constant.ErrorCode;
import allthings.iot.util.redis.ICentralCacheService;
import com.alibaba.fastjson.JSON;
import com.toolkit.common.Consts;
import com.toolkit.security.AbstractCsrfTokenServlet;
import com.toolkit.util.JsonUtil;
import com.toolkit.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author :  luhao
 * @FileName :  CsrfTokenServlet
 * @CreateDate :  2018-11-19
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
public class CsrfTokenServlet extends AbstractCsrfTokenServlet {

    private static final Logger logger = LoggerFactory.getLogger(CsrfTokenServlet.class);
    private ICentralCacheService redisFactory;

    public CsrfTokenServlet(ICentralCacheService redisFactory) {
        this.redisFactory = redisFactory;
    }

    @Override
    public void saveServerCsrfToken(String csrfTokenKey, String csrfTokenValue, int liveTime) {
        redisFactory.putObjectWithExpireTime(csrfTokenKey, csrfTokenValue, liveTime);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        if (logger.isDebugEnabled()) {
            logger.debug("enter csrf token filter.");
        }
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        if (!isExcludeUri(httpServletRequest) && !isNonCheck(httpServletRequest)) {

            if (logger.isDebugEnabled()) {
                logger.debug("enter csrf token filter check.");
            }

            String csrfTokenClient = request
                    .getParameter(Consts.CSRF_TOKEN_PARAMNAME);
            String csrfType = request.getParameter(Consts.CSRF_TOKEN_TYPE);

            String csrfTokenKey = getCsrfTokenKey(httpServletRequest);
            String csrfTokenServer = getServerCsrfToken(csrfTokenKey);
            if (!isSame(csrfTokenClient, csrfTokenServer)) {
                HttpServletResponse resp = (HttpServletResponse) response;
                resp.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                PrintWriter pw = resp.getWriter();
                pw.print(JSON.toJSONString(ResultDTO.newFail(ErrorCode.ERROR_5060.getCode(),
                        ErrorCode.ERROR_5060.getMessage())));
                pw.flush();
                if (chain == null) {
                    throw new RuntimeException("csrf filter error.");
                }
                if (logger.isInfoEnabled()) {
                    logger.info("invalid csrf token; csrfToken="
                            + csrfTokenClient + ", csrfType=" + csrfType
                            + ", serverCsrfToken=" + csrfTokenClient
                            + csrfTokenServer);
                }
                return;
            }
        }
        //为直接调用提供方便，for grails
        if (chain != null) {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        String csrfTokenValue = saveServerCsrfToken(request);
        ResultDTO<String> result = ResultDTO.newSuccess(csrfTokenValue);
        JsonUtil.responseJsonOrJsonp(req, res, JSON.toJSONString(result));
    }

    @Override
    public String getCsrfTokenKey(HttpServletRequest request) {
        String appTokenType = request.getParameter(Consts.CSRF_TOKEN_TYPE);
        if (StringUtil.isBlank(appTokenType)) {
            appTokenType = "NOTYPE";
        }
        StringBuilder sb = new StringBuilder("CSRF_");
        sb.append(appTokenType);
        logger.info("---------------->>>csrfTokenKey:" + sb.toString());
        return sb.toString();
    }

    /**
     * 获取服务端存储的分布式csrfToken
     *
     * @param csrfTokenKey
     * @return
     */
    @Override
    public String getServerCsrfToken(String csrfTokenKey) {
        String serverToken = redisFactory.getObject(csrfTokenKey, String.class);
        redisFactory.removeObject(csrfTokenKey);
        return serverToken;

    }

    /**
     * csrfToken是否相同
     *
     * @param csrfTokenClient
     * @param csrfTokenServer
     * @return
     */
    private boolean isSame(String csrfTokenClient, String csrfTokenServer) {
        if (csrfTokenClient == null) {
            return false;//null not equals null
        } else {
            return csrfTokenClient.equals(csrfTokenServer);
        }
    }
}

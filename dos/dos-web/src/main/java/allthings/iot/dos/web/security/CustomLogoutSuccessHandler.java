package allthings.iot.dos.web.security;

import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.constant.Constants;
import allthings.iot.dos.dto.query.IotLogDTO;
import allthings.iot.dos.web.message.IotDosLoggerProducer;
import com.alibaba.fastjson.JSON;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author :  luhao
 * @FileName :  CustomLogoutSuccessHandler
 * @CreateDate :  2018-5-25
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
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    private static ApplicationContext applicationContext;
    IotDosLoggerProducer producer;

    public static void setApplicationContext(ApplicationContext context) {
        applicationContext = context;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication
            authentication) throws IOException {
        doSomething();
        if (authentication != null) {
            User user = (User) authentication.getPrincipal();
            if (user != null) {
                saveProjectLogger(user);
            }
        }

        ResultDTO<?> result = ResultDTO.newSuccess();
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.getWriter().println(JSON.toJSONString(result));
        response.getWriter().flush();
        response.getWriter().close();
    }

    /**
     * 保存用户注销日志
     *
     * @param user
     */
    private void saveProjectLogger(User user) {
        IotLogDTO logDTO = new IotLogDTO();
        logDTO.setAssociationType("IotUser");
        logDTO.setLoggerTime(System.currentTimeMillis());
        logDTO.setLogContent("用户【" + user.getUsername() + "】注销成功");
        logDTO.setUsername(user.getUsername());
        logDTO.setLogTypeCode(Constants.SYSTEM_LOGGER_TYPE);
        logDTO.setIotProjectId(0L);
        producer.sendToQueue(logDTO);
    }

    private void doSomething() {
        producer = applicationContext.getBean(IotDosLoggerProducer.class);
    }
}

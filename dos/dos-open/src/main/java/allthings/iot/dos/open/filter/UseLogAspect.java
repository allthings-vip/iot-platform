package allthings.iot.dos.open.filter;

import allthings.iot.dos.client.open.IotUseLogApi;
import allthings.iot.dos.dto.IotUseLogDTO;
import allthings.iot.dos.open.util.HttpUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: fengchangxin
 * @create: 2019-12-16 16:45
 * @description:
 **/
@Aspect
@Component
public class UseLogAspect {
    @Autowired
    private IotUseLogApi iotUseLogApi;

    @Pointcut("execution(public * allthings.iot.dos.open.controller..*.*(..))")
    public void pointcut() {

    }

    @Before("pointcut()")
    protected void doFilterInternal(JoinPoint point) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        IotUseLogDTO useLogDTO = new IotUseLogDTO();
        Object ob = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (ob instanceof String) {
            useLogDTO.setClientId((String) ob);
        }

        useLogDTO.setPath(request.getRequestURI());
        useLogDTO.setUserIp(HttpUtil.getIpAddress(request));
        iotUseLogApi.saveUseLog(useLogDTO);
    }
}

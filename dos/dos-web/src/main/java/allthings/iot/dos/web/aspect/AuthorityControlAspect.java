package allthings.iot.dos.web.aspect;

import allthings.iot.dos.api.IotUserService;

import allthings.iot.dos.constant.ErrorCode;
import allthings.iot.dos.dto.IotUserDTO;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import allthings.iot.common.dto.ResultDTO;

/**
 * @author :  luhao
 * @FileName :  AuthorityControlAspect
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
public class AuthorityControlAspect {
    @Autowired
    private IotUserService iotUserApi;

    @Around("execution(* allthings.iot.dos.web.controller.*.save*(..)) " +
            " || execution(* allthings.iot.dos.web.controller.*.update*(..)) " +
            "|| execution(* allthings.iot.dos.web.controller.*.delete*(..)) " +
            "|| execution(* allthings.iot.dos.web.controller.*.import*(..))")
    public Object authorityServiceMethodAccess(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();

        ResultDTO<IotUserDTO> result = iotUserApi.getUserByUsername(getUsername());
        if (!result.isSuccess()) {
            return ResultDTO.newFail(ErrorCode.ERROR_5002.getCode(), ErrorCode.ERROR_5002.getMessage());
        }
        
        return joinPoint.proceed(args);
    }

    /**
     * 获取用户名
     *
     * @return
     */
    private String getUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            User user = (User) auth.getPrincipal();
            return user.getUsername();
        } else {
            return null;
        }
    }
}

package allthings.iot.dos.web.controller;

import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.constant.ErrorCode;
import allthings.iot.dos.web.kapthcha.KaptchaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author :  luhao
 * @FileName :  IotLoginController
 * @CreateDate :  2018-5-9
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
@RestController
public class IotLoginController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(IotLoginController.class);
    @Autowired
    private KaptchaService kaptchaService;

    @RequestMapping(value = {"/login", "/"})
    public ResultDTO<?> login() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (!(auth instanceof AnonymousAuthenticationToken)) {
                return ResultDTO.newSuccess();
            }
            return ResultDTO.newFail(ErrorCode.ERROR_5000.getCode(), ErrorCode.ERROR_5000.getMessage());
        } catch (Exception e) {
            LOGGER.error("login error", e);
            return ResultDTO.newFail(ErrorCode.ERROR_5001.getCode(), ErrorCode.ERROR_5001.getMessage());
        }
    }


    @GetMapping("/kaptcha/get")
    public ResultDTO<?> getKaptcha(HttpServletRequest request) {
        return kaptchaService.dosKaptchaGenerator(request);
    }

}

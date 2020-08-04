package allthings.iot.dos.web.advice;

import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.constant.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

/**
 * @author :  luhao
 * @FileName :  GlobalExceptionHandler
 * @CreateDate :  2018-6-3
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
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({SocketTimeoutException.class, ConnectException.class})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResultDTO<?> handleTimeoutException(HttpServletRequest request, Exception ex) {
        LOGGER.error("exception info ", ex);
        return ResultDTO.newFail(ErrorCode.ERROR_5020.getCode(), ErrorCode.ERROR_5020.getMessage());
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResultDTO<?> handleException(HttpServletRequest request, Exception ex) {
        LOGGER.error("exception info ", ex);
        return ResultDTO.newFail(ErrorCode.ERROR_5021.getCode(), ErrorCode.ERROR_5021.getMessage());
    }
}

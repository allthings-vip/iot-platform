package allthings.iot.dos.web.controller;

import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.constant.ErrorCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @author tyf
 * @date 2018/09/19 12:13
 */
@RestController
public class IotErrorController extends BaseController {

    @GetMapping("/400")
    public Object errorCode400(HttpServletResponse response) {
        return ResultDTO.newFail(ErrorCode.ERROR_5058.getCode(),
                ErrorCode.ERROR_5058.getMessage());
    }

    @GetMapping("/500")
    public Object errorCode500(HttpServletResponse response) {
        return ResultDTO.newFail(ErrorCode.ERROR_5021.getCode(),
                ErrorCode.ERROR_5021.getMessage());
    }

    @GetMapping("/404")
    public Object errorCode404(HttpServletResponse response) {
        return ResultDTO.newFail(ErrorCode.ERROR_5059.getCode(),
                ErrorCode.ERROR_5059.getMessage());
    }

    @GetMapping("/417")
    public Object errorCode417(HttpServletResponse response) {
        return ResultDTO.newFail(ErrorCode.ERROR_5060.getCode(),
                ErrorCode.ERROR_5060.getMessage());
    }
}

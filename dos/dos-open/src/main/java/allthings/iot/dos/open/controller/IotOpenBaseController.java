package allthings.iot.dos.open.controller;


import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author luhao
 * @date 2020/2/18 16:15
 */
public class IotOpenBaseController {
    /**
     * 获取client_id
     *
     * @return
     */
    public String getClientId() {
        return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}

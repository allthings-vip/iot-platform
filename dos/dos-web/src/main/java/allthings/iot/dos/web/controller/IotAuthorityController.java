package allthings.iot.dos.web.controller;

import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.client.api.IotUserApi;
import allthings.iot.dos.dto.IotAuthorityDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: fengchangxin
 * @create: 2018-11-23 13:46
 */
@RestController
public class IotAuthorityController extends BaseController {

    @Autowired
    private IotUserApi iotUserApi;

    /**
     * 权限判断
     *
     * @param route 页面路由
     * @return
     */
    @GetMapping("/authority/judge")
    public ResultDTO<Integer> authorityJudge(@RequestParam("route") String route) {
        IotAuthorityDTO iotAuthorityDTO = new IotAuthorityDTO();
        iotAuthorityDTO.setUrl(route);
        setDto(iotAuthorityDTO);
        return iotUserApi.getAdminAuthority(iotAuthorityDTO);
    }
}

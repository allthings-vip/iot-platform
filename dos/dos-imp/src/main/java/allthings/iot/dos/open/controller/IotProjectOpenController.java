package allthings.iot.dos.open.controller;

import allthings.iot.dos.client.open.IotProjectOpenApi;
import allthings.iot.dos.open.api.IotProjectUtilService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import allthings.iot.common.dto.ResultDTO;

/**
 * @author tyf
 * @date 2019/07/05 09:18:28
 */
@RestController
public class IotProjectOpenController implements IotProjectOpenApi {

    @Autowired
    private IotProjectUtilService projectUtilBiz;

    @Override
    public ResultDTO<Long> hasDevice(@RequestParam("clientId") String clientId,
                                     @RequestParam("deviceCodes") String[] deviceCodes,
                                     @RequestParam("enabled") Boolean enabled) {
        return projectUtilBiz.hasDevice(clientId, Lists.newArrayList(deviceCodes), enabled);
    }

    @Override
    public ResultDTO<Long> hasDeviceType(@RequestParam("clientId") String clientId,
                                         @RequestParam("deviceTypeCodes") String[] deviceTypeCodes) {
        return projectUtilBiz.hasDeviceType(clientId, Lists.newArrayList(deviceTypeCodes));
    }

    @Override
    public ResultDTO<Long> getIotProject(@RequestParam("clientId") String clientId) {
        return projectUtilBiz.getIotProject(clientId);
    }

}

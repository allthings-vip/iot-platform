package allthings.iot.dos.controller;

import allthings.iot.dos.api.IotOverviewBiz;
import allthings.iot.dos.client.api.IotOverviewApi;
import allthings.iot.dos.dto.query.IotDosOverviewDTO;
import allthings.iot.dos.dto.query.IotUserQueryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tf56.iot.common.dto.ResultDTO;

/**
 * @author tyf
 * @date 2019/07/04 16:28:27
 */
@RestController
public class IotOverviewController extends IotDosBaseController implements IotOverviewApi {

    @Autowired
    private IotOverviewBiz overviewBiz;

    @Override
    public ResultDTO<IotDosOverviewDTO> overview(@RequestBody IotUserQueryDTO iotUserQueryDTO) {
        return getResult(overviewBiz.overview(iotUserQueryDTO));
    }

    @Override
    public ResultDTO<IotDosOverviewDTO> overviewByIotProjectId(@RequestBody IotUserQueryDTO iotUserQueryDTO) {
        return getResult(overviewBiz.overviewByIotProjectId(iotUserQueryDTO));
    }

}

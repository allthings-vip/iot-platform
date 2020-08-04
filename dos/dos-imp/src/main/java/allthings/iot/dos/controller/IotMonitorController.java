package allthings.iot.dos.controller;

import allthings.iot.common.dto.PageResult;
import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.api.IotMonitorService;
import allthings.iot.dos.client.api.IotMonitorApi;
import allthings.iot.dos.dto.IotExternalDevicePlatformDTO;
import allthings.iot.dos.dto.IotServiceDTO;
import allthings.iot.dos.dto.IotServiceListDto;
import allthings.iot.dos.dto.query.IotMonitorQueryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author tyf
 * @date 2019/07/05 11:15:17
 */
@RestController
public class IotMonitorController implements IotMonitorApi {

    @Autowired
    private IotMonitorService monitorBiz;

    @Override
    public ResultDTO<Integer> saveService(@RequestBody IotServiceDTO iotServiceDTO) {
        return monitorBiz.saveService(iotServiceDTO);
    }

    @Override
    public ResultDTO<IotServiceDTO> getIotServiceByIPAndPort(@RequestParam("ip") String ip,
                                                             @RequestParam("port") String port) {
        return monitorBiz.getIotServiceByIPAndPort(ip, port);
    }

    @Override
    public ResultDTO<PageResult<IotServiceDTO>> getIotServiceLists(@RequestBody IotMonitorQueryDTO iotMonitorQueryDTO) {
        return monitorBiz.getIotServiceLists(iotMonitorQueryDTO);
    }

    @Override
    public ResultDTO<PageResult<IotServiceDTO>> getServiceInfoTopology() {
        return monitorBiz.getServiceInfoTopology();
    }

    @Override
    public ResultDTO<Integer> updateService(@RequestBody IotServiceDTO iotServiceDTO) {
        return monitorBiz.updateService(iotServiceDTO);
    }

    @Override
    public ResultDTO<Integer> deleteService(@RequestParam("iotServiceId") Long iotServiceId) {
        return monitorBiz.deleteService(iotServiceId);
    }

    @Override
    public ResultDTO<List<IotExternalDevicePlatformDTO>> getPlatFormList() {
        return monitorBiz.getPlatFormList();
    }

    @Override
    public ResultDTO<IotExternalDevicePlatformDTO> getPlatFormByCode(@RequestParam("code") String code) {
        return monitorBiz.getPlatFormByCode(code);
    }

    @Override
    public ResultDTO<List<IotServiceDTO>> saveAll(@RequestBody IotServiceListDto services) {
        return monitorBiz.saveServices(services.getServices());
    }


}

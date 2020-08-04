package allthings.iot.dos.web.controller;

import allthings.iot.common.dto.PageResult;
import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.client.api.IotMonitorApi;
import allthings.iot.dos.constant.Constants;
import allthings.iot.dos.dto.IotDosServiceInfoDto;
import allthings.iot.dos.dto.IotServiceDTO;
import allthings.iot.dos.dto.query.IotMonitorQueryDTO;
import allthings.iot.util.redis.ICentralCacheService;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author :  txw
 * @FileName :  IotServiceMonitorController
 * @CreateDate :  2019/5/8
 * @Description :  dmp
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
public class IotServiceMonitorController extends BaseController {

    @Autowired
    private IotMonitorApi iotMonitorApi;

    @Autowired
    private ICentralCacheService cache;

    @GetMapping("/serviceInfo/realtime")
    public ResultDTO<IotDosServiceInfoDto> getRealTimeServiceInfo(@RequestParam("ip") String ip,
                                                                  @RequestParam("port") String port) throws Exception {

        String str = cache.getMapField(Constants.IOT_DOS_SERVICE_INFO, ip + ":" + port, String.class);
        return ResultDTO.newSuccess(JSON.parseObject(str, IotDosServiceInfoDto.class));
    }

    @GetMapping("/serviceInfo/get")
    public ResultDTO<IotServiceDTO> getServiceInfo(@RequestParam("ip") String ip, @RequestParam("port") String port) {
        return iotMonitorApi.getIotServiceByIPAndPort(ip, port);
    }

    @GetMapping("/serviceInfo/list")
    public ResultDTO<PageResult<IotServiceDTO>> getServiceInfoList(@RequestParam(value = "status", required = false) Boolean status,
                                                                   @RequestParam(value = "keywords",
                                                                           required = false) String keywords,
                                                                   @RequestParam("pageIndex") Integer pageIndex,
                                                                   @RequestParam("pageSize") Integer pageSize) {
        IotMonitorQueryDTO iotMonitorQueryDTO = new IotMonitorQueryDTO();
        iotMonitorQueryDTO.setStatus(status);
        iotMonitorQueryDTO.setKeywords(keywords);
        iotMonitorQueryDTO.setPageIndex(pageIndex);
        iotMonitorQueryDTO.setPageSize(pageSize);
        return iotMonitorApi.getIotServiceLists(iotMonitorQueryDTO);
    }

    @GetMapping("/serviceInfo/topology")
    public ResultDTO<PageResult<IotServiceDTO>> getServiceInfoTopology() {
        return iotMonitorApi.getServiceInfoTopology();
    }

    @PostMapping("/serviceInfo/save")
    public ResultDTO<Integer> saveServiceInfo(@RequestBody IotServiceDTO iotServiceDTO) {
        iotServiceDTO.setServiceCode(iotServiceDTO.getServiceName());
        setUser(iotServiceDTO);
        return iotMonitorApi.saveService(iotServiceDTO);
    }

    @PostMapping("/serviceInfo/update")
    public ResultDTO<Integer> updateServiceInfo(@RequestBody IotServiceDTO iotServiceDTO) {
        iotServiceDTO.setServiceCode(iotServiceDTO.getServiceName());
        setUser(iotServiceDTO);
        return iotMonitorApi.updateService(iotServiceDTO);
    }

    @PostMapping("/serviceInfo/delete")
    public ResultDTO<Integer> deleteServiceInfo(@RequestBody IotServiceDTO iotServiceDTO) {
        return iotMonitorApi.deleteService(iotServiceDTO.getIotServiceId());
    }

    private void setUser(IotServiceDTO iotServiceDTO) {
        iotServiceDTO.setModifyOperatorId(1L);
        iotServiceDTO.setCreateOperatorId(1L);
    }
}

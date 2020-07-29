package allthings.iot.dss.ui;

import allthings.iot.dos.dto.IotDeviceDetailDTO;
import allthings.iot.dos.dto.IotDeviceStatusBatchQueryDTO;
import allthings.iot.dos.dto.IotEventPushUrlDto;
import allthings.iot.dos.dto.query.IotIovProtocolCodeQueryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import allthings.iot.common.dto.ResultDTO;

import java.util.List;

/**
 * @author tyf
 * @date 2019/05/06 14:58:26
 */
@FeignClient(name = "dss", fallback = IotDssFallCallback.class)
public interface IotDssApi {

    /**
     * 根据设备编码查询设备唯一编码id
     *
     * @param deviceCode
     * @return
     */
    @GetMapping(value = "/dss/getIotDeviceIdByDeviceCode")
    ResultDTO<List<Long>> getIotDeviceIdByDeviceCode(@RequestParam("deviceCode") String deviceCode);

    /**
     * 根据项目id查询项目推送地址
     *
     * @param iotProjectId
     * @return
     */
    @GetMapping(value = "/dss/getEventPushUrlByIotProjectId")
    ResultDTO<IotEventPushUrlDto> getEventPushUrlByIotProjectId(@RequestParam("iotProjectId") Long iotProjectId);

    /**
     * 根据设备编码查询设备所属项目id
     *
     * @param deviceCode
     * @return
     */
    @GetMapping("/dss/getIotProjectIdByDeviceCode")
    ResultDTO<List<Long>> getIotProjectIdByDeviceCode(@RequestParam("deviceCode") String deviceCode);

    /**
     * 根据设备编码查询协议编码
     *
     * @param deviceCode
     * @return
     */
    @GetMapping("/dss/getProtocolByDeviceCode")
    ResultDTO<IotIovProtocolCodeQueryDto> getProtocolByDeviceCode(@RequestParam("deviceCode") String deviceCode);

    /**
     * 根据设备编码查询协议编码
     *
     * @param batchQueryDTO
     * @return
     */
    @PostMapping("/dss/getProtocolByDeviceCodes")
    ResultDTO<List<IotIovProtocolCodeQueryDto>> getProtocolByDeviceCodes(@RequestBody IotDeviceStatusBatchQueryDTO batchQueryDTO);

    /**
     * 查询设备id
     *
     * @param deviceCode
     * @return
     */
    @GetMapping("/dss/getDeviceIdByDeviceCode")
    ResultDTO<String> getDeviceIdByDeviceCode(@RequestParam("deviceCode") String deviceCode);

    /**
     * 获取设备详情
     *
     * @param deviceCode
     * @return
     */
    @GetMapping("/dss/getIotDeviceDetailByDeviceCode")
    ResultDTO<IotDeviceDetailDTO> getIotDeviceDetail(@RequestParam("deviceCode") String deviceCode,
                                                     @RequestParam("iotProjectId") Long iotProjectId);

    /**
     * 查询项目ID查询项目下所有设备
     *
     * @param iotProjectId
     * @return
     */
    @GetMapping("/dss/getDeviceListByProjectId")
    ResultDTO<List<IotDeviceDetailDTO>> getDeviceByProjectId(@RequestParam("iotProjectId") Long iotProjectId);

    /**
     * 根据业务编码查询设备id
     *
     * @param bizCode
     * @return
     */
    @GetMapping("/dss/getDeviceIdByBizCode")
    ResultDTO<String> getDeviceIdByBizCode(@RequestParam("bizCode") String bizCode);

    /**
     * 根据业务编码查询设备协议
     *
     * @param bizCode
     * @return
     */
    @GetMapping("/dss/getProtocolByBizCode")
    ResultDTO<IotIovProtocolCodeQueryDto> getProtocolByBizCode(@RequestParam("bizCode") String bizCode);
}

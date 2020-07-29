package allthings.iot.dss.api;

import allthings.iot.dos.dto.IotDeviceDetailDTO;
import allthings.iot.dos.dto.IotDeviceStatusBatchQueryDTO;
import allthings.iot.dos.dto.IotEventPushUrlDto;
import allthings.iot.dos.dto.query.IotIovProtocolCodeQueryDto;
import allthings.iot.common.dto.ResultDTO;

import java.util.List;

/**
 * @author tyf
 * @date 2019/03/29 11:18
 */
public interface IotDssService {

    /**
     * 根据设备编码查询设备唯一编码id
     *
     * @param deviceCode
     * @return
     */
    ResultDTO<List<Long>> getIotDeviceIdByDeviceCode(String deviceCode);

    /**
     * 根据项目id查询项目推送地址
     *
     * @param iotProjectId
     * @return
     */
    ResultDTO<IotEventPushUrlDto> getEventPushUrlByIotProjectId(Long iotProjectId);

    /**
     * 根据设备编码查询设备所属项目id
     *
     * @param deviceCode
     * @return
     */
    ResultDTO<List<Long>> getIotProjectIdByDeviceCode(String deviceCode);

    /**
     * 根据设备编码查询协议编码
     *
     * @param deviceCode
     * @return
     */
    ResultDTO<IotIovProtocolCodeQueryDto> getProtocolByDeviceCode(String deviceCode);

    /**
     * 根据设备编码查询协议编码
     *
     * @param batchQueryDTO
     * @return
     */
    ResultDTO<List<IotIovProtocolCodeQueryDto>> getProtocolByDeviceCodes(IotDeviceStatusBatchQueryDTO batchQueryDTO);

    /**
     * 查询设备id
     *
     * @param deviceCode
     * @return
     */
    ResultDTO<String> getDeviceIdByDeviceCode(String deviceCode);

    /**
     * 获取设备详情
     *
     * @param deviceCode
     * @return
     */
    ResultDTO<IotDeviceDetailDTO> getIotDeviceDetail(String deviceCode, Long iotProjectId);

    /**
     * 查询项目ID查询项目下所有设备
     *
     * @param iotProjectId
     * @return
     */
    ResultDTO<List<IotDeviceDetailDTO>> getDeviceByProjectId(Long iotProjectId);

    /**
     * 根据业务编码查询设备id
     *
     * @param bizCode
     * @return
     */
    ResultDTO<String> getDeviceIdByBizCode(String bizCode);

    /**
     * 根据业务编码查询设备协议
     *
     * @param bizCode
     * @return
     */
    ResultDTO<IotIovProtocolCodeQueryDto> getProtocolByBizCode(String bizCode);
}

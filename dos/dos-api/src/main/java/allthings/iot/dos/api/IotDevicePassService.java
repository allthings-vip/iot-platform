package allthings.iot.dos.api;

import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.dto.IotDeviceDTO;
import allthings.iot.dos.dto.IotDevicePassDto;
import allthings.iot.dos.dto.IotVisResultDTO;
import allthings.iot.dos.dto.query.IotDevicePassQueryDTO;

import java.util.List;

/**
 * @author tyf
 * @date 2019/02/27 11:13
 */
public interface IotDevicePassService {

    /**
     * 保存设备通道信息
     *
     * @param devicePassDto
     * @return
     */
    ResultDTO<Integer> saveOrUpdateDevicePass(IotDevicePassDto devicePassDto);

    /**
     * 批量保存设备通道
     *
     * @param devicePassDtoList
     * @return
     */
    ResultDTO<Integer> batchSaveOrUpdateDevicePass(List<IotDevicePassDto> devicePassDtoList);

    /**
     * 根据设备id 查询设备通道下拉列表
     *
     * @param deviceDTO
     * @return
     */
    ResultDTO<List<IotDevicePassDto>> getDevicePassList(IotDeviceDTO deviceDTO);

    /**
     * 根据设备通道id 查询设备通道详情
     *
     * @param iotDosDevicePassId
     * @return
     */
    ResultDTO getDevicePassDetail(Long iotDosDevicePassId);

    /**
     * 查询视频设备通道直播地址
     *
     * @param iotDevicePassQueryDto
     * @return
     */
    ResultDTO<IotVisResultDTO> getPassLiveStream(IotDevicePassQueryDTO iotDevicePassQueryDto);

    /**
     * 获取回放地址
     *
     * @param iotDevicePassQueryDto
     * @return
     */
    ResultDTO<IotVisResultDTO> getPassPlayBack(IotDevicePassQueryDTO iotDevicePassQueryDto);

    /**
     * 云台控制
     *
     * @param iotDevicePassQueryDTO
     * @return
     */
    ResultDTO<Integer> controlDevicePass(IotDevicePassQueryDTO iotDevicePassQueryDTO);

    /**
     * 摄像头停止转动
     *
     * @param iotDevicePassQueryDTO
     * @return
     */
    ResultDTO<Integer> stopControlDevicePass(IotDevicePassQueryDTO iotDevicePassQueryDTO);

    /**
     * 查询设备下某类型的通道
     *
     * @param iotDeviceId
     * @param iotPassTypeId
     * @return
     */
    ResultDTO<List<IotDevicePassDto>> getIotDevicePassList(Long iotDeviceId, Long iotPassTypeId);
}

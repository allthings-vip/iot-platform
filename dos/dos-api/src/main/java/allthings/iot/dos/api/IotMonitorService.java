package allthings.iot.dos.api;

import allthings.iot.common.dto.PageResult;
import allthings.iot.common.dto.ResultDTO;
import allthings.iot.dos.dto.IotExternalDevicePlatformDTO;
import allthings.iot.dos.dto.IotServiceDTO;
import allthings.iot.dos.dto.query.IotMonitorQueryDTO;

import java.util.List;

/**
 * @author :  txw
 * @FileName :  IotMonitorService
 * @CreateDate :  2019/5/9
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
public interface IotMonitorService {

    /**
     * 保存服务信息
     *
     * @param iotServiceDTO
     * @return
     */
    ResultDTO<Integer> saveService(IotServiceDTO iotServiceDTO);

    /**
     * 通过IP和端口获取服务信息
     *
     * @param ip
     * @param port
     * @return
     */
    ResultDTO<IotServiceDTO> getIotServiceByIPAndPort(String ip, String port);

    /**
     * 获取服务列表
     *
     * @param queryDTO
     * @return
     */
    ResultDTO<PageResult<IotServiceDTO>> getIotServiceLists(IotMonitorQueryDTO queryDTO);

    /**
     * 获取服务列表
     *
     * @return
     */
    ResultDTO<PageResult<IotServiceDTO>> getServiceInfoTopology();

    /**
     * 更新服务信息
     *
     * @param iotServiceDTO
     * @return
     */
    ResultDTO<Integer> updateService(IotServiceDTO iotServiceDTO);

    /**
     * 删除服务信息
     *
     * @param iotServiceId
     * @return
     */
    ResultDTO<Integer> deleteService(Long iotServiceId);

    /**
     * 获取第三方平台信息
     *
     * @return
     */
    ResultDTO<List<IotExternalDevicePlatformDTO>> getPlatFormList();

    /**
     * 根据平台码获取平台信息
     *
     * @param code
     * @return
     */
    ResultDTO<IotExternalDevicePlatformDTO> getPlatFormByCode(String code);

    /**
     * 保存平台信息
     *
     * @param iotExternalDevicePlatform
     * @return
     */
    ResultDTO<IotExternalDevicePlatformDTO> savePlatForm(IotExternalDevicePlatformDTO iotExternalDevicePlatform);

    /**
     * 通过服务名获取第三方平台信息
     *
     * @param serviceCode
     * @return
     */
    ResultDTO<List<IotExternalDevicePlatformDTO>> getPlatFormByDependencyService(String serviceCode);

    /**
     * 批量保存服务信息
     *
     * @param items
     * @return
     */
    ResultDTO<List<IotServiceDTO>> saveServices(List<IotServiceDTO> items);
}

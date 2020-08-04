package allthings.iot.dos.open.api;

import allthings.iot.common.dto.ResultDTO;

import java.util.List;

/**
 * @author :  txw
 * @FileName :  IotProjectUtilBiz
 * @CreateDate :  2018/12/10
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
public interface IotProjectUtilService {

    /**
     * 查询是否拥有设备权限
     *
     * @param clientId
     * @param deviceCodes
     * @param enabled
     * @return
     */
    ResultDTO<Long> hasDevice(String clientId, List<String> deviceCodes, Boolean enabled);

    /**
     * 查询是否拥有设备类型权限
     *
     * @param clientId
     * @param deviceTypeCodes
     * @return
     */
    ResultDTO<Long> hasDeviceType(String clientId, List<String> deviceTypeCodes);

    /**
     * 获取项目信息
     *
     * @param clientId
     * @return
     */
    ResultDTO<Long> getIotProject(String clientId);

}

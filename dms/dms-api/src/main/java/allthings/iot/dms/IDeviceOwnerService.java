package allthings.iot.dms;

import java.util.List;

/**
 * @author :  luhao
 * @FileName :  IDeviceOwnerService
 * @CreateDate :  2016/6/28
 * @Description :
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) www.XXXXX.com   All Rights Reserved
 * *******************************************************************************************
 */
public interface IDeviceOwnerService {

    /**
     * 设备与拥有者进行绑定
     *
     * @param ownerId  拥有者id
     * @param deviceId 设备id
     */
    void bindDevice(String ownerId, String deviceId);

    /**
     * 设备与拥有者进行绑定
     *
     * @param ownerId    拥有者id
     * @param deviceList 设备列表
     */
    void bindDevice(String ownerId, List<String> deviceList);


    /**
     * 解除绑定拥有者的所有设备
     *
     * @param ownerId 拥有者id
     */
    void unBindDevice(String ownerId);

    /**
     * 解除绑定指定的设备
     *
     * @param ownerId    拥有者id
     * @param deviceList 设备列表
     */
    void unBindDevice(String ownerId, List<String> deviceList);

    /**
     * 根据deviceId 所属拥有者
     *
     * @param deviceId 设备id
     * @return 拥有者信息
     */
    String getOwnerIdByDeviceId(String deviceId);

    /**
     * 设备批量升级
     *
     * @param deviceCodes 升级设备列表
     * @return 升级是否成功
     */
    String deviceUpdate(List<String> deviceCodes);
}

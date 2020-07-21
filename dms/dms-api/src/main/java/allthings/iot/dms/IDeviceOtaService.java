package allthings.iot.dms;

import allthings.iot.common.dto.QueryResult;
import allthings.iot.dms.dto.DeviceOtaFileDto;

/**
 * @author :  sylar
 * @FileName :  IDeviceOtaService
 * @CreateDate :  2017/11/08
 * @Description :
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
public interface IDeviceOtaService {
    /**
     * 根据设备类型查询设备的在线升级文件
     *
     * @param deviceType
     * @param pageIndex
     * @param pageSize
     * @return
     */
    QueryResult<DeviceOtaFileDto> getDeviceOtaFilesByDeviceType(String deviceType, int pageIndex, int pageSize);

    /**
     * 上传在线升级文件
     *
     * @param otaFullName
     * @param deviceType
     * @param versionCode
     * @param versionName
     * @param description
     * @param content
     */
    void uploadOtaFile(String otaFullName, String deviceType, int versionCode, String versionName,
                       String description, String content);

    /**
     * 根据参数查询设备升级文档信息
     *
     * @param deviceType   设备类型
     * @param connected    设备状态
     * @param deviceCode   设备编码
     * @param beginVersion 开始版本号
     * @param endVersion   结束版本号
     * @param pageIndex
     * @param pageSize
     * @return 设备升级文档信息
     */
    QueryResult<?> findUpDocument(String deviceType, boolean connected, String deviceCode, int beginVersion, int
            endVersion, int pageIndex, int pageSize);

}

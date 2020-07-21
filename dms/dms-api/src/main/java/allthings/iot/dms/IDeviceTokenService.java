package allthings.iot.dms;

import allthings.iot.common.dto.QueryResult;
import allthings.iot.dms.dto.DeviceTokenDto;

/**
 * @author :  sylar
 * @FileName :  IDeviceTokenService
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
public interface IDeviceTokenService {

    /**
     * 设备token数量
     *
     * @return
     */
    long countOfDeviceToken();

    /**
     * 产生设备id
     *
     * @param deviceType
     * @param token
     * @return
     */
    String generateDeviceId(String deviceType, String token);

    /**
     * 根据设备id获取设备token
     *
     * @param deviceId
     * @return
     */
    DeviceTokenDto getDeviceTokenByDeviceId(String deviceId);

    /**
     * 暂时不明白
     *
     * @param token
     * @return
     */
    DeviceTokenDto getDeviceTokenByToken(String token);

    /**
     * 根据设备类型获取设备token
     *
     * @param deviceType
     * @param pageIndex
     * @param pageSize
     * @return
     */
    QueryResult<DeviceTokenDto> getDeviceTokensByDeviceType(String deviceType, int pageIndex, int pageSize);

}

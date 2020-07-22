package allthings.iot.dms.dto;

/**
 * @author :  sylar
 * @FileName :  DeviceTokenDto
 * @CreateDate :  2017/11/08
 * @Description :
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) allthings-vip All Rights Reserved
 * *******************************************************************************************
 */
public class DeviceTokenDto extends AbstractDeviceDto {

    private Long iotDeviceTokenId;

    private String token;

    public Long getIotDeviceTokenId() {
        return iotDeviceTokenId;
    }

    public void setIotDeviceTokenId(Long iotDeviceTokenId) {
        this.iotDeviceTokenId = iotDeviceTokenId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

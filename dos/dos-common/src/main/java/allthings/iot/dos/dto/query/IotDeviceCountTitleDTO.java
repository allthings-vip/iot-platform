package allthings.iot.dos.dto.query;

import java.io.Serializable;

/**
 * @author :  luhao
 * @FileName :  IotDeviceCountTitleDTO
 * @CreateDate :  2018-5-17
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
public class IotDeviceCountTitleDTO implements Serializable {

    /**
     * 设备数量
     */
    private Long deviceCounts;
    /**
     * 标题
     */
    private String title;

    /**
     * 标题ID
     */
    private Long iotTitleId;

    public IotDeviceCountTitleDTO() {
    }

    public IotDeviceCountTitleDTO(Long deviceCounts, Long iotTitleId, String title) {
        this.deviceCounts = deviceCounts;
        this.title = title;
        this.iotTitleId = iotTitleId;
    }

    public Long getDeviceCounts() {
        return deviceCounts;
    }

    public void setDeviceCounts(Long deviceCounts) {
        this.deviceCounts = deviceCounts;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getIotTitleId() {
        return iotTitleId;
    }

    public void setIotTitleId(Long iotTitleId) {
        this.iotTitleId = iotTitleId;
    }
}

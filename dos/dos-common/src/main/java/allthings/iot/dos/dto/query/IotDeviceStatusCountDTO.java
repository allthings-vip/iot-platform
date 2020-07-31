package allthings.iot.dos.dto.query;

import java.io.Serializable;

/**
 * @author :  luhao
 * @FileName :  IotDeviceStatusCountDTO
 * @CreateDate :  2018/5/7
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
public class IotDeviceStatusCountDTO implements Serializable {

    /**
     * 设备总数
     */
    private Long deviceTotal;
    /**
     * 离线数据量
     */
    private Long offlineTotal;
    /**
     * 在线数量
     */
    private Long onlineTotal;

    public Long getDeviceTotal() {
        return deviceTotal;
    }

    public void setDeviceTotal(Long deviceTotal) {
        this.deviceTotal = deviceTotal;
    }

    public Long getOfflineTotal() {
        return offlineTotal;
    }

    public void setOfflineTotal(Long offlineTotal) {
        this.offlineTotal = offlineTotal;
    }

    public Long getOnlineTotal() {
        return onlineTotal;
    }

    public void setOnlineTotal(Long onlineTotal) {
        this.onlineTotal = onlineTotal;
    }
}

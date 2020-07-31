package allthings.iot.dos.dto.query;

/**
 * @author :  luhao
 * @FileName :  IotProjectDetailDTO
 * @CreateDate :  2018/5/4
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
public class IotProjectDetailDTO extends IotProjectQueryDTO {
    /**
     * 离线数据量
     */
    private Long offlineTotal;
    /**
     * 在线数量
     */
    private Long onlineTotal;

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

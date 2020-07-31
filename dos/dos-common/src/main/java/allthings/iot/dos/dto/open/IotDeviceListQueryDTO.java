package allthings.iot.dos.dto.open;

import java.io.Serializable;

/**
 * @author :  txw
 * @FileName :  IotDeviceListQueryDTO
 * @CreateDate :  2018/11/15
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
public class IotDeviceListQueryDTO implements Serializable {

    /**
     * 在线状态
     */
    private Boolean connected;

    /**
     * 关键字
     */
    private String keywords;

    /**
     * 页面大小
     */
    private Integer pageSize;

    /**
     * 页码
     */
    private Integer pageIndex;

    /**
     * 项目编码
     */
    private Long iotProjectId;

    public IotDeviceListQueryDTO() {
    }

    public Boolean getConnected() {
        return connected;
    }

    public void setConnected(Boolean connected) {
        this.connected = connected;
    }

    public void setConnectStatus(Integer connectStatus) {
        if (connectStatus == 0) {
            connected = false;
        }
        if (connectStatus == 1) {
            connected = true;
        }
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Long getIotProjectId() {
        return iotProjectId;
    }

    public void setIotProjectId(Long iotProjectId) {
        this.iotProjectId = iotProjectId;
    }
}

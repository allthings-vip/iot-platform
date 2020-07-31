package allthings.iot.dos.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @author :  txw
 * @FileName :  IotDeviceTagDTO
 * @CreateDate :  2018/10/30
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
public class IotDeviceTagDTO extends AbstractIotDosDTO implements Serializable {

    /**
     * 标签唯一标识
     */
    private Long iotTagId;
    /**
     * 标签名称
     */
    private String tagName;

    private Long iotProjectId;

    private List<Long> iotDeviceIds;

    public IotDeviceTagDTO() {
    }

    public IotDeviceTagDTO(Long iotTagId, String tagName) {
        this.iotTagId = iotTagId;
        this.tagName = tagName;
    }

    public Long getIotTagId() {
        return iotTagId;
    }

    public void setIotTagId(Long iotTagId) {
        this.iotTagId = iotTagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public Long getIotProjectId() {
        return iotProjectId;
    }

    public void setIotProjectId(Long iotProjectId) {
        this.iotProjectId = iotProjectId;
    }

    public List<Long> getIotDeviceIds() {
        return iotDeviceIds;
    }

    public void setIotDeviceIds(List<Long> iotDeviceIds) {
        this.iotDeviceIds = iotDeviceIds;
    }
}

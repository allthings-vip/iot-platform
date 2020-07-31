package allthings.iot.dos.dto.query;

import java.io.Serializable;

/**
 * @author :  luhao
 * @FileName :  IotDeviceTypeTagDTO
 * @CreateDate :  2018/5/3
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
public class IotDeviceTypeTagDTO implements Serializable {
    /**
     * 标签唯一标识
     */
    private Long iotTagId;
    /**
     * 标签名称
     */
    private String tagName;

    public IotDeviceTypeTagDTO() {
    }

    public IotDeviceTypeTagDTO(Long iotTagId, String tagName) {
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
}

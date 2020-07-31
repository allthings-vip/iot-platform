package allthings.iot.dos.dto.query;

import allthings.iot.dos.dto.IotDeviceTagDTO;

/**
 * @author :  luhao
 * @FileName :  IotTagQueryDTO
 * @CreateDate :  2018-5-10
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
public class IotTagQueryDTO extends IotDeviceTagDTO {

    private String keywords;

    public IotTagQueryDTO() {
        super();
    }

    public IotTagQueryDTO(Long iotTagId, String tagName) {
        super(iotTagId, tagName);
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }
}

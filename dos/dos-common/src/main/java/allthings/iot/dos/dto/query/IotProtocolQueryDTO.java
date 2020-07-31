package allthings.iot.dos.dto.query;

import java.io.Serializable;

/**
 * @author :  luhao
 * @FileName :  IotProtocolQueryDTO
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
public class IotProtocolQueryDTO implements Serializable {
    /**
     * 协议ID
     */
    private Long iotProtocolId;
    /**
     * 协议名称
     */
    private String protocolName;
    /**
     * 协议代码
     */
    private String protocolCode;

    public IotProtocolQueryDTO() {
    }

    public IotProtocolQueryDTO(Long iotProtocolId, String protocolName, String protocolCode) {
        this.iotProtocolId = iotProtocolId;
        this.protocolName = protocolName;
        this.protocolCode = protocolCode;
    }

    public Long getIotProtocolId() {
        return iotProtocolId;
    }

    public void setIotProtocolId(Long iotProtocolId) {
        this.iotProtocolId = iotProtocolId;
    }

    public String getProtocolName() {
        return protocolName;
    }

    public void setProtocolName(String protocolName) {
        this.protocolName = protocolName;
    }

    public String getProtocolCode() {
        return protocolCode;
    }

    public void setProtocolCode(String protocolCode) {
        this.protocolCode = protocolCode;
    }
}

package allthings.iot.dos.dto.open;

import java.io.Serializable;
import java.util.List;

/**
 * @author :  txw
 * @FileName :  IotSaveDeviceDTO
 * @CreateDate :  2018/11/12
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
public class IotSaveDeviceDTO implements Serializable {

    /**
     * 设备号
     */
    private String deviceCode;

    /**
     * gps数据列表
     */
    private List<IotGpsListDTO> gpsList;

    /**
     * 因子数据
     */
    private List<IotKtvFactorDTO> kvsList;

    private String clientId;

    public IotSaveDeviceDTO() {
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public List<IotGpsListDTO> getGpsList() {
        return gpsList;
    }

    public void setGpsList(List<IotGpsListDTO> gpsList) {
        this.gpsList = gpsList;
    }

    public List<IotKtvFactorDTO> getKvsList() {
        return kvsList;
    }

    public void setKvsList(List<IotKtvFactorDTO> kvsList) {
        this.kvsList = kvsList;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}

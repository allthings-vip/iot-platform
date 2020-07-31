package allthings.iot.dos.dto.open;

import java.io.Serializable;

/**
 * @author :  txw
 * @FileName :  IotKtvFactorDTO
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
public class IotKtvFactorDTO implements Serializable {

    /**
     * 因子编码
     */
    private String code;

    /**
     * 采集时间
     */
    private Long realtime;

    /**
     * 因子值
     */
    private String value;

    public IotKtvFactorDTO() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getRealtime() {
        return realtime;
    }

    public void setRealtime(Long realtime) {
        this.realtime = realtime;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

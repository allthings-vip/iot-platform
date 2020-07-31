package allthings.iot.dos.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

/**
 * @author :  luhao
 * @FileName :  IotProtocolFactor
 * @CreateDate :  2018/4/29
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
@Entity
@Table(name = "iot_dos_protocol_factor")
public class IotProtocolFactor extends AbstractIotDosModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "int(20) comment '协议类型因子唯一编码' ", name = "iot_dos_protocol_factor_id")
    private Long iotProtocolFactorId;

    @Column(columnDefinition = " int(20) comment '协议唯一编码' ", name = "iot_dos_protocol_id", nullable = false)
    private Long iotProtocolId;

    @Column(columnDefinition = " int(20) comment '因子唯一编码' ", name = "iot_dos_factor_id", nullable = false)
    private Long iotFactorId;

    public Long getIotProtocolFactorId() {
        return iotProtocolFactorId;
    }

    public void setIotProtocolFactorId(Long iotProtocolFactorId) {
        this.iotProtocolFactorId = iotProtocolFactorId;
    }

    public Long getIotProtocolId() {
        return iotProtocolId;
    }

    public void setIotProtocolId(Long iotProtocolId) {
        this.iotProtocolId = iotProtocolId;
    }

    public Long getIotFactorId() {
        return iotFactorId;
    }

    public void setIotFactorId(Long iotFactorId) {
        this.iotFactorId = iotFactorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IotProtocolFactor)) {
            return false;
        }
        IotProtocolFactor that = (IotProtocolFactor) o;
        return Objects.equals(iotProtocolId, that.iotProtocolId) &&
                Objects.equals(iotFactorId, that.iotFactorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(iotProtocolId, iotFactorId);
    }
}

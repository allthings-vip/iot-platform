package allthings.iot.dms.entity;

import org.hibernate.annotations.Table;

import javax.persistence.*;

/**
 * @author :  sylar
 * @FileName :  IotDeviceToken
 * @CreateDate :  2017/11/08
 * @Description :
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) allthings-vip All Rights Reserved
 * *******************************************************************************************
 */
@Entity
@Table(appliesTo = "IotDeviceToken", comment = "设备令牌表")
public class IotDeviceToken extends AbstractDeviceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "int(20) comment 'iotDeviceTokenId' ")
    private Long iotDeviceTokenId;

    @Column(nullable = false, unique = true, columnDefinition = " varchar(255) comment '令牌' ")
    private String token;

    public Long getIotDeviceTokenId() {
        return iotDeviceTokenId;
    }

    public void setIotDeviceTokenId(Long iotDeviceTokenId) {
        this.iotDeviceTokenId = iotDeviceTokenId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

package allthings.iot.dos.model.oauth2;

import allthings.iot.dos.model.AbstractIotDosModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * @author :  luhao
 * @FileName :  IotOauthCode
 * @CreateDate :  2018/04/25
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
@Table(name = "iot_oauth_code")
public class IotOauthCode extends AbstractIotDosModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "int(20) comment 'iot_oauth_code_id' ", name = "iot_oauth_code_id")
    private Long iotOauthCodeId;

    @Column(columnDefinition = "varchar(500) comment 'code' ", nullable = false, name = "code")
    private String code;

    @Column(columnDefinition = "varchar(4096) comment 'authentication' ", nullable = false, name = "authentication")
    private String authentication;

    public Long getIotOauthCodeId() {
        return iotOauthCodeId;
    }

    public void setIotOauthCodeId(Long iotOauthCodeId) {
        this.iotOauthCodeId = iotOauthCodeId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAuthentication() {
        return authentication;
    }

    public void setAuthentication(String authentication) {
        this.authentication = authentication;
    }
}

package allthings.iot.dos.model.oauth2;


import allthings.iot.dos.model.AbstractIotDosModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author :  luhao
 * @FileName :  IotOauthApprovals
 * @CreateDate :  2018-11-14
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
@Table(name = "iot_oauth_approvals")
public class IotOauthApprovals extends AbstractIotDosModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "int(20) comment 'iot_oauth_approvals_id' ", name = "iot_oauth_approvals_id")
    private Long iotOauthApprovalsId;
    @Column(columnDefinition = "varchar(500) comment 'user_id' ", nullable = false, name =
            "user_id")
    private String userId;
    @Column(columnDefinition = "varchar(500) comment 'client_id' ", nullable = false, name = "client_id")
    private String clientId;
    @Column(columnDefinition = "varchar(500) comment 'scope' ", nullable = false, name = "scope")
    private String scope;
    @Column(columnDefinition = "varchar(500) comment 'status' ", nullable = false, name = "status")
    private String status;
    @Column(columnDefinition = " datetime  COMMENT 'expires_at' ", name = "expires_at")
    private Date expiresAt;
    @Column(columnDefinition = " datetime  COMMENT 'lastmodified_at' ", name = "lastmodified_at")
    private Date lastModifiedAt;

    public Long getIotOauthApprovalsId() {
        return iotOauthApprovalsId;
    }

    public void setIotOauthApprovalsId(Long iotOauthApprovalsId) {
        this.iotOauthApprovalsId = iotOauthApprovalsId;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }


    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Date expiresAt) {
        this.expiresAt = expiresAt;
    }

    public Date getLastModifiedAt() {
        return lastModifiedAt;
    }

    public void setLastModifiedAt(Date lastModifiedAt) {
        this.lastModifiedAt = lastModifiedAt;
    }
}

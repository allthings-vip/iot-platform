package allthings.iot.dos.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author :  luhao
 * @FileName :  IotUser
 * @CreateDate :  2018-6-5
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
@Table(name = "iot_dos_user")
public class IotUser extends AbstractIotDosModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "int(20) comment 'iotUserId' ", name = "iot_dos_user_id")
    private Long iotUserId;

    @Column(columnDefinition = " varchar(500) comment '用户名' ", name = "username", nullable = false)
    private String username;

    @Column(columnDefinition = " varchar(500) comment '密码' ", name = "password", nullable = false)
    private String password;

    @Column(columnDefinition = " varchar(500) comment '角色' ", name = "role_code", nullable = false)
    private String roleCode;

    @Column(columnDefinition = "varchar(20) comment '手机号' ", name = "mobile")
    private String mobile;

    @Column(columnDefinition = "varchar(500) comment '邮箱' ", name = "email")
    private String email;

    @Column(columnDefinition = "varchar(500) comment '公司名称' ", name = "company_name")
    private String companyName;

    @Column(columnDefinition = "varchar(50) comment '姓名' ", name = "real_name")
    private String realName;

    @Column(columnDefinition = "tinyint(1) comment '是否启用' ", name = "is_enabled")
    private Boolean isEnabled;

    @Column(columnDefinition = "varchar(500) comment '权限路由' ", name = "authority")
    private String authority;

    @Column(columnDefinition = "varchar(500) comment '密码随机数' ", name = "salt")
    private String salt;

    public Long getIotUserId() {
        return iotUserId;
    }

    public void setIotUserId(Long iotUserId) {
        this.iotUserId = iotUserId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Boolean getEnabled() {
        return isEnabled;
    }

    public void setEnabled(Boolean enabled) {
        isEnabled = enabled;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}

package allthings.iot.dos.dto;

import allthings.iot.dos.dto.query.IotProjectSimpleDTO;

import java.util.List;

/**
 * @author :  luhao
 * @FileName :  IotUserDTO
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
public class IotUserDTO extends AbstractIotDosDTO {
    /**
     * 自增主键
     */
    private Long iotUserId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 新密码
     */
    private String newPassword;

    /**
     * 确认密码
     */
    private String password2;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 姓名
     */
    private String realName;

    /**
     * 是否启用
     */
    private Boolean isEnabled;

    /**
     * 验证码
     */
    private String code;

    /**
     * 拥有项目
     */
    private List<IotProjectSimpleDTO> iotProjects;

    /**
     * 权限路由
     */
    private List<IotAuthorityDTO> authority;

    /**
     * 密码随机数
     */
    private String salt;

    public IotUserDTO() {
    }

    public IotUserDTO(Long iotUserId, String username) {
        this.iotUserId = iotUserId;
        this.username = username;
    }

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

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<IotProjectSimpleDTO> getIotProjects() {
        return iotProjects;
    }

    public void setIotProjects(List<IotProjectSimpleDTO> iotProjects) {
        this.iotProjects = iotProjects;
    }

    public List<IotAuthorityDTO> getAuthority() {
        return authority;
    }

    public void setAuthority(List<IotAuthorityDTO> authority) {
        this.authority = authority;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}

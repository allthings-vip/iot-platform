package allthings.iot.dos.dto;

/**
 * @description:
 * @author: fengchangxin
 * @create: 2018-11-01 09:47
 */

public class IotUserProjectDTO extends AbstractIotDosDTO {
    /**
     * 主键ID
     */
    private Long iotUserProjectId;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 项目id
     */
    private Long iotProjectId;

    /**
     * 用户ID
     */
    private Long iotUserId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 是否项目拥有者
     */
    private Boolean isOwner;

    /**
     * 项目审核状态
     */
    private Boolean isReview;

    public IotUserProjectDTO() {
    }

    public IotUserProjectDTO(String projectName, Long iotProjectId, Long iotUserId, String username, Boolean isReview) {
        this.projectName = projectName;
        this.iotProjectId = iotProjectId;
        this.iotUserId = iotUserId;
        this.username = username;
        this.isReview = isReview;
    }

    public Long getIotUserProjectId() {
        return iotUserProjectId;
    }

    public void setIotUserProjectId(Long iotUserProjectId) {
        this.iotUserProjectId = iotUserProjectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Long getIotProjectId() {
        return iotProjectId;
    }

    public void setIotProjectId(Long iotProjectId) {
        this.iotProjectId = iotProjectId;
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

    public Boolean getOwner() {
        return isOwner;
    }

    public void setOwner(Boolean owner) {
        isOwner = owner;
    }

    public Boolean getReview() {
        return isReview;
    }

    public void setReview(Boolean review) {
        isReview = review;
    }
}

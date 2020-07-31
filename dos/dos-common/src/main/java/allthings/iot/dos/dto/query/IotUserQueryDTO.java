package allthings.iot.dos.dto.query;

/**
 * @description:
 * @author: fengchangxin
 * @create: 2018-10-31 11:20
 */

public class IotUserQueryDTO extends AbstractIotDosQueryListDto {
    /**
     * 用户ID
     */
    private Long iotUserId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 项目ID
     */
    private Long iotProjectId;

    public IotUserQueryDTO() {
    }

    public IotUserQueryDTO(Long iotUserId, String username) {
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

    public Long getIotProjectId() {
        return iotProjectId;
    }

    public void setIotProjectId(Long iotProjectId) {
        this.iotProjectId = iotProjectId;
    }
}

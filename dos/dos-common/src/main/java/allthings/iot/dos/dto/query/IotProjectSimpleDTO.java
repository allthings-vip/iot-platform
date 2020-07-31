package allthings.iot.dos.dto.query;

/**
 * @author :  luhao
 * @FileName :  IotProjectSimpleDTO
 * @CreateDate :  2018/5/4
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
public class IotProjectSimpleDTO extends AbstractIotDosQueryListDto {
    /**
     * 项目唯一编码
     */
    private Long iotProjectId;

    /**
     * 项目名称
     */
    private String projectName;

    public IotProjectSimpleDTO() {
    }

    public IotProjectSimpleDTO(Long iotProjectId) {
        this.iotProjectId = iotProjectId;
    }

    public IotProjectSimpleDTO(Long iotProjectId, String projectName) {
        this.iotProjectId = iotProjectId;
        this.projectName = projectName;
    }

    public Long getIotProjectId() {
        return iotProjectId;
    }

    public void setIotProjectId(Long iotProjectId) {
        this.iotProjectId = iotProjectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}

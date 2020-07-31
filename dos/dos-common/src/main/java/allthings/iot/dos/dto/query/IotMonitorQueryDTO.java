package allthings.iot.dos.dto.query;

/**
 * @author :  txw
 * @FileName :  IotMonitorQueryDTO
 * @CreateDate :  2019/5/14
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
public class IotMonitorQueryDTO extends AbstractIotDosQueryListDto {

    private Boolean status;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}

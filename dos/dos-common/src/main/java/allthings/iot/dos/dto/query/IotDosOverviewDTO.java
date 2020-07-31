package allthings.iot.dos.dto.query;

import java.io.Serializable;

/**
 * @author :  luhao
 * @FileName :  IotDosOverviewDTO
 * @CreateDate :  2018-5-20
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
public class IotDosOverviewDTO implements Serializable {

    /**
     * 今日新增数据点数
     */
    private Long addedTodayPointCounts;

    /**
     * 接入设备总数
     */
    private Long deviceCounts;
    /**
     * 设备类型数
     */
    private Long deviceTypeCounts;
    /**
     * 在线设备数量
     */
    private Long onlineDeviceCounts;
    /**
     * 接入项目数
     */
    private Long projectCounts;
    /**
     * 总数据点数
     */
    private Long totalPointCounts;


    public Long getAddedTodayPointCounts() {
        return addedTodayPointCounts;
    }

    public void setAddedTodayPointCounts(Long addedTodayPointCounts) {
        this.addedTodayPointCounts = addedTodayPointCounts;
    }

    public Long getDeviceCounts() {
        return deviceCounts;
    }

    public void setDeviceCounts(Long deviceCounts) {
        this.deviceCounts = deviceCounts;
    }

    public Long getDeviceTypeCounts() {
        return deviceTypeCounts;
    }

    public void setDeviceTypeCounts(Long deviceTypeCounts) {
        this.deviceTypeCounts = deviceTypeCounts;
    }

    public Long getOnlineDeviceCounts() {
        return onlineDeviceCounts;
    }

    public void setOnlineDeviceCounts(Long onlineDeviceCounts) {
        this.onlineDeviceCounts = onlineDeviceCounts;
    }

    public Long getProjectCounts() {
        return projectCounts;
    }

    public void setProjectCounts(Long projectCounts) {
        this.projectCounts = projectCounts;
    }

    public Long getTotalPointCounts() {
        return totalPointCounts;
    }

    public void setTotalPointCounts(Long totalPointCounts) {
        this.totalPointCounts = totalPointCounts;
    }
}

package allthings.iot.dms.dto;

import com.google.common.collect.Sets;

import java.io.Serializable;
import java.util.Set;

/**
 * @author :  sylar
 * @FileName :  DasStatusDto
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
public class DasStatusDto implements Serializable {
    private String nodeId;
    /**
     * 在线设备编码列表
     */
    private Set<String> onlineDeviceIdSet = Sets.newConcurrentHashSet();

    /**
     * 创建时间(DAS上线时间)
     */
    private long createTime = System.currentTimeMillis();


    public DasStatusDto() {
    }

    public DasStatusDto(String nodeId) {
        this.nodeId = nodeId;
    }

    public void addDeviceId(String deviceId) {
        if (!onlineDeviceIdSet.contains(deviceId)) {
            onlineDeviceIdSet.add(deviceId);
        }
    }

    public void removeDeviceId(String deviceId) {
        if (onlineDeviceIdSet.contains(deviceId)) {
            onlineDeviceIdSet.remove(deviceId);
        }
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public Set<String> getOnlineDeviceIdSet() {
        return onlineDeviceIdSet;
    }

    public void setOnlineDeviceIdSet(Set<String> onlineDeviceIdSet) {
        this.onlineDeviceIdSet = onlineDeviceIdSet;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}

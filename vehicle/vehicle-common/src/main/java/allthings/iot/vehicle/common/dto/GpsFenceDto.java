package allthings.iot.vehicle.common.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @author :  luhao
 * @FileName :  GpsFenceDto
 * @CreateDate :  2018/1/16
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
public class GpsFenceDto implements Serializable {
    /**
     * 围栏的id
     */
    private String entityId;
    /**
     * 围栏名称
     */
    private String name;
    /**
     * 操作人
     */
    private String owner;
    /**
     * app名称
     */
    private String appId;
    /**
     * 区域类型
     */
    private String type;
    /**
     * 所属业务区域
     */
    private String businessDomain;
    /**
     * 描述
     */
    private String description;
    /**
     * 坐标点列表二维数组
     */
    private List<List<GpsPointDto>> area;
    /**
     * 最小经度
     */
    private double minX;
    /**
     * 最小纬度
     */
    private double minY;
    /**
     * 最大经度
     */
    private double maxX;
    /**
     * 最大纬度
     */
    private double maxY;

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBusinessDomain() {
        return businessDomain;
    }

    public void setBusinessDomain(String businessDomain) {
        this.businessDomain = businessDomain;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<List<GpsPointDto>> getArea() {
        return area;
    }

    public void setArea(List<List<GpsPointDto>> area) {
        this.area = area;
    }

    public double getMinX() {
        return minX;
    }

    public void setMinX(double minX) {
        this.minX = minX;
    }

    public double getMinY() {
        return minY;
    }

    public void setMinY(double minY) {
        this.minY = minY;
    }

    public double getMaxX() {
        return maxX;
    }

    public void setMaxX(double maxX) {
        this.maxX = maxX;
    }

    public double getMaxY() {
        return maxY;
    }

    public void setMaxY(double maxY) {
        this.maxY = maxY;
    }
}

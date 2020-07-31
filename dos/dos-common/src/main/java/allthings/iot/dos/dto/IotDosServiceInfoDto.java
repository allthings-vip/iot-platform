package allthings.iot.dos.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @author :  txw
 * @FileName :  ServiceInfoDto
 * @CreateDate :  2019/5/7
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
public class IotDosServiceInfoDto implements Serializable {

    /**
     * IP
     */
    private String ip;
    /**
     * 堆内存使用（MB）
     */
    private Double heap;
    /**
     * 最大堆内存（MB）
     */
    private Double heapMax;
    /**
     * 使用比例（%）
     */
    private Double heapRatio;
    /**
     * 非堆内存使用（MB）
     */
    private Double nonHeap;
    /**
     * 最大非堆内存（MB）
     */
    private Double nonHeapMax;
    /**
     * 使用比例（%）
     */
    private Double nonHeapRatio;
    /**
     * 磁盘总容量（GB）
     */
    private Double totalDiskSpace;
    /**
     * 可用磁盘总容量（GB）
     */
    private Double usableDiskSpace;
    /**
     * 内存总容量（MB）
     */
    private Long totalRAM;
    /**
     * 可用内存容量（MB）
     */
    private Long avaliableRAM;
    /**
     * 网络速率（Mbps）
     */
    private Double netSpeed;
    /**
     * 启动时间 (2019-05-06 08:53:16)
     */
    private String runTime;
    /**
     * 运行时间 (0天0小时30分钟)
     */
    private String diffTime;
    /**
     * 包名
     */
    private String title;
    /**
     * 版本
     */
    private String version;
    /**
     * 构建时间 (2019-05-06 08:53:16)
     */
    private String buildTime;
    /**
     * cpu使用率（%）
     */
    private Double cpu;
    /**
     * 采集时间
     */
    private Long reportTime;
    /**
     * 层级
     */
    private Integer levels;
    /**
     * 端口
     */
    private String port;
    /**
     * 服务编码
     */
    private String serviceCode;
    /**
     * 第三方平台状态
     */
    private List<String> platform;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Double getHeap() {
        return heap;
    }

    public void setHeap(Double heap) {
        this.heap = heap;
    }

    public Double getHeapMax() {
        return heapMax;
    }

    public void setHeapMax(Double heapMax) {
        this.heapMax = heapMax;
    }

    public Double getHeapRatio() {
        return heapRatio;
    }

    public void setHeapRatio(Double heapRatio) {
        this.heapRatio = heapRatio;
    }

    public Double getNonHeap() {
        return nonHeap;
    }

    public void setNonHeap(Double nonHeap) {
        this.nonHeap = nonHeap;
    }

    public Double getNonHeapMax() {
        return nonHeapMax;
    }

    public void setNonHeapMax(Double nonHeapMax) {
        this.nonHeapMax = nonHeapMax;
    }

    public Double getNonHeapRatio() {
        return nonHeapRatio;
    }

    public void setNonHeapRatio(Double nonHeapRatio) {
        this.nonHeapRatio = nonHeapRatio;
    }

    public Double getTotalDiskSpace() {
        return totalDiskSpace;
    }

    public void setTotalDiskSpace(Double totalDiskSpace) {
        this.totalDiskSpace = totalDiskSpace;
    }

    public Double getUsableDiskSpace() {
        return usableDiskSpace;
    }

    public void setUsableDiskSpace(Double usableDiskSpace) {
        this.usableDiskSpace = usableDiskSpace;
    }

    public Long getTotalRAM() {
        return totalRAM;
    }

    public void setTotalRAM(Long totalRAM) {
        this.totalRAM = totalRAM;
    }

    public Long getAvaliableRAM() {
        return avaliableRAM;
    }

    public void setAvaliableRAM(Long avaliableRAM) {
        this.avaliableRAM = avaliableRAM;
    }

    public Double getNetSpeed() {
        return netSpeed;
    }

    public void setNetSpeed(Double netSpeed) {
        this.netSpeed = netSpeed;
    }

    public String getRunTime() {
        return runTime;
    }

    public void setRunTime(String runTime) {
        this.runTime = runTime;
    }

    public String getDiffTime() {
        return diffTime;
    }

    public void setDiffTime(String diffTime) {
        this.diffTime = diffTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getBuildTime() {
        return buildTime;
    }

    public void setBuildTime(String buildTime) {
        this.buildTime = buildTime;
    }

    public Double getCpu() {
        return cpu;
    }

    public void setCpu(Double cpu) {
        this.cpu = cpu;
    }

    public Long getReportTime() {
        return reportTime;
    }

    public void setReportTime(Long reportTime) {
        this.reportTime = reportTime;
    }

    public Integer getLevels() {
        return levels;
    }

    public void setLevels(Integer levels) {
        this.levels = levels;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public List<String> getPlatform() {
        return platform;
    }

    public void setPlatform(List<String> platform) {
        this.platform = platform;
    }
}

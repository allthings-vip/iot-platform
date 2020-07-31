package allthings.iot.dos.dto;

import java.io.Serializable;

/**
 * @description:
 * @author: fengchangxin
 * @create: 2019-04-17 11:20
 */

public class IotVisResultDTO implements Serializable {
    /**
     * 低清hls流
     */
    private String hlsLive;
    /**
     * 高清hls流
     */
    private String hlsHD;

    /**
     * RTMP流(流畅)
     */
    private String rtmpLive;
    /**
     * RTMP流(高清)
     */
    private String rtmpHD;

    /**
     * 回放地址
     */
    private String address;

    /**
     * 回放流类型rmtp,rtsp,hls,ezopen,flv
     */
    private String type;

    /**
     * 标识
     */
    private String sequence;

    public String getHlsLive() {
        return hlsLive;
    }

    public void setHlsLive(String hlsLive) {
        this.hlsLive = hlsLive;
    }

    public String getHlsHD() {
        return hlsHD;
    }

    public void setHlsHD(String hlsHD) {
        this.hlsHD = hlsHD;
    }

    public String getRtmpLive() {
        return rtmpLive;
    }

    public void setRtmpLive(String rtmpLive) {
        this.rtmpLive = rtmpLive;
    }

    public String getRtmpHD() {
        return rtmpHD;
    }

    public void setRtmpHD(String rtmpHD) {
        this.rtmpHD = rtmpHD;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }
}

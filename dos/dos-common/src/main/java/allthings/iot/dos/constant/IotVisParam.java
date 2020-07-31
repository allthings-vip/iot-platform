package allthings.iot.dos.constant;

/**
 * @description:
 * @author: fengchangxin
 * @create: 2019-04-17 10:10
 */

public interface IotVisParam {
    /**
     * 获取直播地址方法名
     */
    String GET_LIVE_STREAM = "getLiveStream";

    /**
     * 获取回放地址方法名
     */
    String GET_PLAY_BACK = "getPlayBack";

    /**
     * 低清hls流
     */
    String HLS_LIVE = "hlsLive";
    /**
     * 高清hls流
     */
    String HLS_HD = "hlsHD";

    /**
     * RTMP流地址
     */
    String RTMP_LIVE = "RTMPSD";
}

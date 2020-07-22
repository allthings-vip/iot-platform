package allthings.iot.common.msg;

import java.io.Serializable;
import java.util.Map;

/**
 * @author :  sylar
 * @FileName :  IMsg
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
public interface IMsg extends Serializable {

    /**
     * 消息类型
     *
     * @return
     */
    MsgType getMsgType();

    /**
     * 消息code
     *
     * @return
     */
    String getMsgCode();

    /**
     * 源设备类型
     *
     * @return
     */
    String getSourceDeviceType();

    /**
     * 源设备id
     *
     * @return
     */
    String getSourceDeviceId();

    /**
     * 目标设备类型
     *
     * @return
     */
    String getTargetDeviceType();

    /**
     * 目标设备id
     *
     * @return
     */
    String getTargetDeviceId();

    /**
     * 发生时间
     *
     * @return
     */
    long getOccurTime();

    /**
     * 标签
     *
     * @return
     */
    Object getTag();

    /**
     * 其他参数
     *
     * @return
     */
    Map<String, Object> getParams();

    /**
     * 根据key获取内容
     *
     * @param paramKey
     * @param <T>
     * @return
     */
    <T> T get(String paramKey);

    /**
     * 获取msdId
     *
     * @return
     */
    String getMsgId();

    /**
     * 获取平台编码
     *
     * @return
     */
    String getPlatformCode();
}

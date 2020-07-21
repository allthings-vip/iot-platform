package allthings.iot.das.mqtt.core;

import allthings.iot.common.msg.IMsg;
import allthings.iot.das.mqtt.pojo.MqttPacketWrap;

import java.util.List;

/**
 * @author :  sylar
 * @FileName :  IMqttMsgResolver
 * @CreateDate :  2017/11/08
 * @Description :
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @@CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
public interface IMqttMsgResolver {

    /**
     * 将ByteBuffer转换成IMsg
     *
     * @param wrap ByteBuffer数据
     * @return IMsg列表
     */
    List<IMsg> wrapToMsg(MqttPacketWrap wrap);

    /**
     * 将IMsg转换成 ByteBuffer
     *
     * @param msg IMsg信息
     * @return ByteBuffer封装信息
     */
    MqttPacketWrap msgToWrap(IMsg msg);
}

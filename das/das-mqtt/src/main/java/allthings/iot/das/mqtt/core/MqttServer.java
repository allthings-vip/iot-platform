package allthings.iot.das.mqtt.core;

import allthings.iot.das.mqtt.protocol.codec.MqttDecoder;
import io.netty.channel.ChannelPipeline;
import allthings.iot.das.common.NettyConst;
import allthings.iot.das.common.core.AbstractTcpServer;
import allthings.iot.das.mqtt.protocol.codec.MqttEncoder;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author :  sylar
 * @fileName :  MqttServer
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
public class MqttServer extends AbstractTcpServer {

    public static final String MQTT_ENCODER_HANDLER_NAME = "mqttEncoderHandler";
    public static final String MQTT_DECODER_HANDLER_NAME = "mqttDecoderHandler";

    public static final String M2M_ENCODER_HANDLER_NAME = "m2mEncoderHandler";
    public static final String M2M_DECODER_HANDLER_NAME = "m2mDecoderHandler";

    @Autowired
    protected DeviceMsgToMqttMsgEncoder deviceMsgToMqttMsgEncoder;

    @Autowired
    protected MqttMsgToDeviceMsgDecoder mqttMsgToDeviceMsgDecoder;

    @Override
    protected void buildChannelHandler(ChannelPipeline pipeline) {
        //in
        pipeline.addLast(MQTT_DECODER_HANDLER_NAME, new MqttDecoder());
        pipeline.addLast(M2M_DECODER_HANDLER_NAME, mqttMsgToDeviceMsgDecoder);
        pipeline.addLast(NettyConst.INBOUND_MSG_HANDLER_NAME, inboundMsgHandler);

        //out
        pipeline.addLast(MQTT_ENCODER_HANDLER_NAME, new MqttEncoder());
        pipeline.addLast(M2M_ENCODER_HANDLER_NAME, deviceMsgToMqttMsgEncoder);

    }

}

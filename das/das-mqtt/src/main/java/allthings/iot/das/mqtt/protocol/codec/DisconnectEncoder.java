package allthings.iot.das.mqtt.protocol.codec;

import allthings.iot.das.mqtt.protocol.message.AbstractMessage;
import allthings.iot.das.mqtt.protocol.message.DisconnectMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author :  sylar
 * @FileName :  MqttConst
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
public class DisconnectEncoder extends AbstractDemuxEncoder<DisconnectMessage> {

    @Override
    protected void encode(ChannelHandlerContext chc, DisconnectMessage msg, ByteBuf out) {
        out.writeByte(AbstractMessage.DISCONNECT << 4).writeByte(0);
    }

}

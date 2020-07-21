package allthings.iot.das.mqtt.protocol.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.CorruptedFrameException;
import io.netty.handler.codec.MessageToByteEncoder;
import allthings.iot.das.mqtt.protocol.message.AbstractMessage;

import java.util.HashMap;
import java.util.Map;

/**
 * @author :  sylar
 * @FileName :  MqttEncoder
 * @CreateDate :  2017/11/08
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
public class MqttEncoder extends MessageToByteEncoder<AbstractMessage> {

    @SuppressWarnings("rawtypes")
    private Map<Byte, AbstractDemuxEncoder> mEncoderMap = new HashMap<Byte, AbstractDemuxEncoder>();

    public MqttEncoder() {
        mEncoderMap.put(AbstractMessage.CONNECT, new ConnectEncoder());
        mEncoderMap.put(AbstractMessage.CONNACK, new ConnAckEncoder());
        mEncoderMap.put(AbstractMessage.PUBLISH, new PublishEncoder());
        mEncoderMap.put(AbstractMessage.PUBACK, new PubAckEncoder());
        mEncoderMap.put(AbstractMessage.SUBSCRIBE, new SubscribeEncoder());
        mEncoderMap.put(AbstractMessage.SUBACK, new SubAckEncoder());
        mEncoderMap.put(AbstractMessage.UNSUBSCRIBE, new UnsubscribeEncoder());
        mEncoderMap.put(AbstractMessage.DISCONNECT, new DisconnectEncoder());
        mEncoderMap.put(AbstractMessage.PINGREQ, new PingReqEncoder());
        mEncoderMap.put(AbstractMessage.PINGRESP, new PingRespEncoder());
        mEncoderMap.put(AbstractMessage.UNSUBACK, new UnsubAckEncoder());
        mEncoderMap.put(AbstractMessage.PUBCOMP, new PubCompEncoder());
        mEncoderMap.put(AbstractMessage.PUBREC, new PubRecEncoder());
        mEncoderMap.put(AbstractMessage.PUBREL, new PubRelEncoder());
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    protected void encode(ChannelHandlerContext chc, AbstractMessage msg, ByteBuf bb) throws Exception {
        AbstractDemuxEncoder encoder = mEncoderMap.get(msg.getMessageType());
        if (encoder == null) {
            throw new CorruptedFrameException("Can't find any suitable decoder for message type: " + msg
                    .getMessageType());
        }
        encoder.encode(chc, msg, bb);
    }
}

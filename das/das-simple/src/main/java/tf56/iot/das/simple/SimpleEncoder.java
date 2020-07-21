package allthings.iot.das.simple;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import allthings.iot.common.msg.IMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.ByteBuffer;
import java.util.List;

/**
 * @author :  sylar
 * @FileName :  SimpleEncoder
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
@Component
@ChannelHandler.Sharable
public class SimpleEncoder extends MessageToMessageEncoder<IMsg> {

    @Autowired
    protected ISimpleMsgResolver simpleMsgResolver;

    @Override
    protected void encode(ChannelHandlerContext ctx, IMsg msg, List<Object> out) throws Exception {
        if (msg == null || simpleMsgResolver == null) {
            return;
        }

        try {
            ByteBuffer buf = simpleMsgResolver.msgToBuf(msg);
            buf.flip();
            out.add(Unpooled.copiedBuffer(buf));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

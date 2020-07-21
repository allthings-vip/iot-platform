package allthings.iot.das.simple.udp;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import allthings.iot.common.msg.IMsg;
import allthings.iot.das.common.bean.TerminalCache;
import allthings.iot.das.simple.SimpleEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * @author :  sylar
 * @FileName :  UdpEncoder
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
public class UdpEncoder extends SimpleEncoder {

    @Autowired
    TerminalCache terminalCache;

    @Override
    protected void encode(ChannelHandlerContext ctx, IMsg msg, List<Object> out) throws Exception {
        if (msg == null) {
            return;
        }

        String targetDeviceId = msg.getTargetDeviceId();
        if (Strings.isNullOrEmpty(targetDeviceId)) {
            return;
        }

        if (!terminalCache.containsKey(targetDeviceId)) {
            return;
        }

        InetSocketAddress address = terminalCache.get(msg.getTargetDeviceId());
        if (address == null) {
            return;
        }

        super.encode(ctx, msg, out);

        //build DatagramPacket
        List<Object> raw = Lists.newArrayList(out);
        out.clear();

        for (Object obj : raw) {
            if (obj instanceof ByteBuf) {
                out.add(new DatagramPacket((ByteBuf) obj, address));
            }
        }
    }
}

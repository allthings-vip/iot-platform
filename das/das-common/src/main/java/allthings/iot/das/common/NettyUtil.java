package allthings.iot.das.common;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.DatagramChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.AttributeKey;

import java.net.InetSocketAddress;

/**
 * @author :  sylar
 * @FileName :  NettyUtil
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
public class NettyUtil {
    public static final AttributeKey<String> ATTR_CLIENTID = AttributeKey.valueOf("clientId");
    public static final AttributeKey<String> ATTR_DEVICE_TYPE = AttributeKey.valueOf("deviceType");
    public static final AttributeKey<Boolean> ATTR_CLEAN_SESSION = AttributeKey.valueOf("cleanSession");
    public static final AttributeKey<Integer> ATTR_KEEP_ALIVE = AttributeKey.valueOf("keepAlive");

    public static boolean isTcpChannel(Channel channel) {
        return channel instanceof SocketChannel;
    }

    public static boolean isUdpChannel(Channel channel) {
        return channel instanceof DatagramChannel;
    }

    public static String getClientId(Channel channel) {
        return channel.attr(ATTR_CLIENTID).get();
    }

    public static void setClientId(Channel channel, String clientId) {
        channel.attr(ATTR_CLIENTID).set(clientId);
    }

    public static String getDeviceType(Channel channel) {
        return channel.attr(ATTR_DEVICE_TYPE).get();
    }

    public static void setDeviceType(Channel channel, String deviceType) {
        channel.attr(ATTR_DEVICE_TYPE).set(deviceType);
    }

    public static boolean isCleanSession(Channel channel) {
        return channel.attr(ATTR_CLEAN_SESSION).get();
    }

    public static void setCleanSession(Channel channel, boolean cleanSession) {
        channel.attr(ATTR_CLEAN_SESSION).set(cleanSession);
    }

    public static int getKeepAlive(Channel channel) {
        return channel.attr(ATTR_KEEP_ALIVE).get();
    }

    public static void setKeepAlive(Channel channel, int keepAlive) {
        channel.attr(ATTR_KEEP_ALIVE).set(keepAlive);
    }

    public static void setChannelIdleTime(Channel channel, int idleTime) {
        if (idleTime < NettyConst.MIN_IDLETIME) {
            idleTime = NettyConst.MIN_IDLETIME;
        } else if (idleTime > NettyConst.MAX_IDLETIME) {
            idleTime = NettyConst.MAX_IDLETIME;
        }
        ChannelPipeline pipeline = channel.pipeline();
        if (pipeline.get(NettyConst.IDLE_STATE_HANDLER_NAME) != null) {
            channel.pipeline().remove(NettyConst.IDLE_STATE_HANDLER_NAME);
        }
        channel.pipeline().addFirst(NettyConst.IDLE_STATE_HANDLER_NAME, new IdleStateHandler(idleTime, 0, 0));
    }

    public static void closeChannel(Channel channel, boolean immediately) {
        if (!channel.isActive()) {
            return;
        }
        if (immediately) {
            channel.close();
        } else {
            channel.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
        }
    }

    public static void writeData(Channel channel, Object value) {
        if (channel != null && channel.isActive()) {
            channel.writeAndFlush(value);
        }
    }

    public static String getChannelRemoteIP(Channel channel) {
        return ((InetSocketAddress) channel.remoteAddress()).getHostString();
    }

    public static int getChannelRemotePort(Channel channel) {
        return ((InetSocketAddress) channel.remoteAddress()).getPort();
    }

    public static void writeDataThenClose(Channel channel, Object value) {
        if (channel.isActive()) {
            channel.writeAndFlush(value).addListener(ChannelFutureListener.CLOSE);
        }
    }
}

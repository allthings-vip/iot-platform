package allthings.iot.das.common.core;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.internal.StringUtil;
import allthings.iot.util.misc.ByteUtils;

/**
 * @author :  luhao
 * @FileName :  ExLoggingHandler
 * @CreateDate :  2017/7/28
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
@ChannelHandler.Sharable
public class ExLoggingHandler extends LoggingHandler {
    /**
     * Creates a new instance whose logger name is the fully qualified class
     * name of the instance with hex dump enabled.
     */
    public ExLoggingHandler() {
        super();
    }

    public ExLoggingHandler(LogLevel level) {
        super(level);
    }


    /**
     * Formats an event and returns the formatted message.
     *
     * @param eventName the name of the event
     * @param arg       the argument of the event
     */
    @Override
    protected String format(ChannelHandlerContext ctx, String eventName, Object arg) {
        if (arg instanceof ByteBuf) {
            return formatByteBuf(ctx, eventName, (ByteBuf) arg);
        } else if (arg instanceof ByteBufHolder) {
            return formatByteBufHolder(ctx, eventName, (ByteBufHolder) arg);
        } else {
            return formatSimple(ctx, eventName, arg);
        }
    }

    /**
     * Generates the default log message of the specified event whose argument is a {@link ByteBuf}.
     */
    private static String formatByteBuf(ChannelHandlerContext ctx, String eventName, ByteBuf msg) {
        String chStr = getChStr(ctx);
        int length = msg.readableBytes();
        if (length == 0) {
            StringBuilder buf = new StringBuilder(chStr.length() + 1 + eventName.length() + 4);
            buf.append(chStr).append(' ').append(eventName).append(": 0B");
            return buf.toString();
        } else {
            int rows = length / 16 + (length % 15 == 0 ? 0 : 1) + 4;
            StringBuilder buf = new StringBuilder(chStr.length() + 1 + eventName.length() + 2 + 10 + 1 + 2 + rows * 80);

            buf.append(chStr).append(' ').append(eventName).append(": ").append(length).append('B').append(StringUtil.NEWLINE);
            appendPrettyHexDump(buf, msg);
            return buf.toString();
        }
    }

    /**
     * 压缩日志格式
     *
     * @param buf
     * @param msg
     */
    private static void appendPrettyHexDump(StringBuilder buf, ByteBuf msg) {
        byte[] content = new byte[msg.readableBytes()];
        msg.getBytes(0, content);
        buf.append("通讯报文：").append(ByteUtils.bytesToHexString(content));
    }

    /**
     * Generates the default log message of the specified event whose argument is a {@link ByteBufHolder}.
     */
    private static String formatByteBufHolder(ChannelHandlerContext ctx, String eventName, ByteBufHolder msg) {
        String chStr = getChStr(ctx);
        String msgStr = msg.toString();
        ByteBuf content = msg.content();
        int length = content.readableBytes();
        if (length == 0) {
            StringBuilder buf = new StringBuilder(chStr.length() + 1 + eventName.length() + 2 + msgStr.length() + 4);
            buf.append(chStr).append(' ').append(eventName).append(", ").append(msgStr).append(", 0B");
            return buf.toString();
        } else {
            int rows = length / 16 + (length % 15 == 0 ? 0 : 1) + 4;
            StringBuilder buf = new StringBuilder(
                    chStr.length() + 1 + eventName.length() + 2 + msgStr.length() + 2 + 10 + 1 + 2 + rows * 80);

            buf.append(chStr).append(' ').append(eventName).append(": ")
                    .append(msgStr).append(", ").append(length).append('B').append(StringUtil.NEWLINE);

            appendPrettyHexDump(buf, content);

            return buf.toString();
        }
    }

    /**
     * Generates the default log message of the specified event whose argument is an arbitrary object.
     */
    private static String formatSimple(ChannelHandlerContext ctx, String eventName, Object msg) {
        String chStr = getChStr(ctx);
        String msgStr = String.valueOf(msg);
        StringBuilder buf = new StringBuilder(chStr.length() + 1 + eventName.length() + 2 + msgStr.length());
        return buf.append(chStr).append(' ').append(eventName).append(": ").append(msgStr).toString();
    }

    private static String getChStr(ChannelHandlerContext ctx) {
        String chStr = ctx.channel().toString();
        return chStr;
    }
}

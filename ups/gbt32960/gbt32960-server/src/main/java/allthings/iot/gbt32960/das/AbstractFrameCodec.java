package allthings.iot.gbt32960.das;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import allthings.iot.common.msg.IMsg;
import allthings.iot.gbt32960.das.packet.AbstractPacket;
import allthings.iot.util.misc.ByteUtils;
import allthings.iot.util.misc.Crc16Utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.List;

/**
 * @author :  luhao
 * @FileName :  AbstractFrameCodec
 * @CreateDate :  2017/12/29
 * @Description : 帧解码器
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) allthings-vip All Rights Reserved
 * *******************************************************************************************
 */
public abstract class AbstractFrameCodec {
    /**
     * 包头标识长度
     */
    private static final int HEAD_LEN = AbstractPacket.HEAD.length;
    /**
     * 命令标识长度
     */
    private static final int MSG_CODE_LEN = 1;
    /**
     * 应答标志长度
     */
    private static final int MSG_RESPONSE_CODE_LEN = 1;
    /**
     * 设备编码长度（唯一标识码）
     */
    private static final int DEVICE_ID_LEN = 17;
    /**
     * 数据单元加密方式长度
     */
    private static final int MSG_ENCRYPT_TYPE_LEN = 1;
    /**
     * 数据单元长度所占长度
     */
    private static final int MSG_LENGTH_LEN = 2;
    /**
     * crc校验码长度
     */
    private static final int CRC_LEN = 1;

    private static final int MIN_FRAME_LEN = HEAD_LEN + MSG_CODE_LEN + MSG_RESPONSE_CODE_LEN + DEVICE_ID_LEN +
            MSG_ENCRYPT_TYPE_LEN + MSG_LENGTH_LEN + 0 + CRC_LEN;

    /**
     * 报文解码
     *
     * @param ctx
     * @param buf
     * @return
     * @throws Exception
     */
    public List<IMsg> decode(ChannelHandlerContext ctx, ByteBuffer buf) throws Exception {
        MsgWrap wrap = this.matchedFrame(buf);
        if (wrap == null) {
            return null;
        }

        return onDecodeMsg(ctx, wrap);
    }

    /**
     * 帧解码
     * <p>
     * 结构
     * +--------+------------------------------------------------+
     * | 2B |  1B   |  1B   | 17B    | 1B    | 2B    | NB | 1B  |
     * +---------------------------------------------------------+
     * |包头|命令标识|应答标志|唯一标识码|加密方式|数据长度|内容|校验码|
     * +--------+------------------------------------------------+
     *
     * @param buf
     * @return
     */
    private MsgWrap matchedFrame(ByteBuffer buf) {
        ByteBuf byteBuf = Unpooled.wrappedBuffer(buf);
        if (byteBuf.capacity() < MIN_FRAME_LEN) {
            return null;
        }

        byte[] head = new byte[HEAD_LEN];
        byteBuf.readBytes(head);

        //比对包头是否匹配
        if (!Arrays.equals(AbstractPacket.HEAD, head)) {
            return null;
        }

        //读取crc校验内容
        byte[] crcBytes = new byte[byteBuf.capacity() - 1];
        byteBuf.getBytes(0, crcBytes);

        //读取命令标识
        byte[] msgCodeByte = new byte[1];
        byteBuf.readBytes(msgCodeByte);

        //读取应答标识
        byte responseCode = byteBuf.readByte();

        //读取设备标识（唯一标识码）
        byte[] deviceCodeBytes = new byte[17];
        byteBuf.readBytes(deviceCodeBytes);

        //加密方式
        byte encryptTypeBytes = byteBuf.readByte();

        //读取包长
        int contentLength = byteBuf.readUnsignedShort();

        //读取内容
        byte[] contentBytes = new byte[contentLength];
        byteBuf.readBytes(contentBytes);

        //读取crc,并校验crc
        short crc = byteBuf.readUnsignedByte();
        if (crc != Crc16Utils.calcCrc(crcBytes)) {
            return null;
        }

        //读取包尾
        byte[] tail = new byte[1];
        byteBuf.readBytes(tail);
        if (!Arrays.equals(tail, AbstractPacket.TAIL)) {
            return null;
        }

        ByteBuffer byteBuffer = ByteBuffer.allocate(contentLength).order(ByteOrder.BIG_ENDIAN);
        byteBuffer.put(contentBytes);

        MsgWrap msgWrap = new MsgWrap();
        msgWrap.setCrc((byte) crc);
        msgWrap.setEncryptType(encryptTypeBytes);
        msgWrap.setResponseCode(responseCode);
        msgWrap.setDeviceId(ByteUtils.toString(deviceCodeBytes, 0, deviceCodeBytes.length));
        msgWrap.setMsgCode(ByteUtils.bytesToHexString(msgCodeByte));
        msgWrap.setContent(byteBuffer);
        msgWrap.getContent().flip();

        return msgWrap;
    }

    /**
     * 根据主信令解码内容部分
     *
     * @param ctx
     * @param wrap
     * @return
     * @throws Exception
     */
    public abstract List<IMsg> onDecodeMsg(ChannelHandlerContext ctx, MsgWrap wrap) throws Exception;
}

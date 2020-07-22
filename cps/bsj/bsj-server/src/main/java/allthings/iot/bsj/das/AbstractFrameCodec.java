package allthings.iot.bsj.das;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import allthings.iot.bsj.das.packet.AbstractPacket;
import allthings.iot.common.msg.IMsg;
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

    private static final int HEAD_LEN = AbstractPacket.HEAD.length;
    private static final int MSG_CODE_LEN = 1;
    private static final int MSG_LENGTH_LEN = 2;
    private static final int PSEUDO_IP_LEN = 4;
    private static final int CRC_LEN = 1;
    private static final int TAIL_LEN = AbstractPacket.TAIL.length;

    private static final int MIN_FRAME_LEN = HEAD_LEN + MSG_CODE_LEN + MSG_LENGTH_LEN + PSEUDO_IP_LEN + 0 +
            CRC_LEN + TAIL_LEN;

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
     * +--------+------------------------+
     * | 2B |  1B | 2B| 4B | NB| 1B | 1B |
     * +---------------------------------+
     * |包头|主信令|包长|伪ip|内容|校验|包尾|
     * +--------+------------------------+
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
        byte[] crcBytes = new byte[byteBuf.capacity() - 2];
        byteBuf.getBytes(0, crcBytes);

        //读取指令
        byte[] msgCodeByte = new byte[1];
        byteBuf.readBytes(msgCodeByte);

        //读取包长
        int contentLength = byteBuf.readUnsignedShort();

        //读取伪ip
        byte[] ipBytes = new byte[4];
        byteBuf.readBytes(ipBytes);

        //读取内容
        byte[] contentBytes = new byte[contentLength - PSEUDO_IP_LEN - CRC_LEN - TAIL_LEN];
        byteBuf.readBytes(contentBytes);

        //读取crc
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
        msgWrap.setChildMsgCode(contentBytes[0]);
        msgWrap.setDeviceId(ByteUtils.bytesToHexString(ipBytes));
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

package allthings.iot.bb808.das;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import allthings.iot.bb808.common.MsgParam;
import allthings.iot.bb808.das.packet.PacketHeader;
import allthings.iot.common.msg.IMsg;
import allthings.iot.util.misc.RSAUtil;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Arrays;
import java.util.List;

/**
 * @author :  luhao
 * @FileName :  FrameCodec
 * @CreateDate :  2017/12/21
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
public abstract class FrameCodec {

    private static final Logger LOGGER = LoggerFactory.getLogger(FrameCodec.class);

    @Value("${rsa.n}")
    private String rsaN;

    @Value("${rsa.d}")
    private String rsaD;

    private RSAPrivateKey rsaPrivateKey;

    @PostConstruct
    public void init() {
        rsaPrivateKey = RSAUtil.getPrivateKey(rsaN, rsaD);
    }

    /**
     * 协议帧结构:
     * { 标识位  消息头 消息体 校验码 标识位 }
     * <p>
     * 标识位  采用Ox7e表示
     * 消息头  { 1WORD 消息ID | 1WORD 消息体属性 | BCD[6] 终端手机号 | 1WORD 消息流水号 | 消息包封装项 }
     * 校验码  指从消息头开始，同后一字节异或，直到校验码前一个字节，占用一个字节。
     * <p>
     * 协议采用大端模式(big-endian)的网络字节序来传递字和双字
     */

    final ByteOrder ORDER = ByteOrder.BIG_ENDIAN;
    final int MaxBufferSize = 1024 * 2;
    final byte[] HEAD = new byte[]{0x7E};
    final byte[] TAIL = new byte[]{0x7E};

    final int HEAD_LEN = HEAD.length;
    final int MSG_ID_LEN = 2;
    final int MSG_ATTR_LEN = 2;
    final int MOBILE_PHONE_NO_LEN = 6;
    final int MSG_SEQ_NUM_LEN = 2;
    final int CRC_LEN = 1;
    final int TAIL_LEN = TAIL.length;

    final int minFrameLen = HEAD_LEN + MSG_ID_LEN + MSG_ATTR_LEN + MOBILE_PHONE_NO_LEN + MSG_SEQ_NUM_LEN + 0 +
            CRC_LEN + TAIL_LEN;

    private short crc = 0;

    protected List<IMsg> decode(ChannelHandlerContext ctx, ByteBuffer buf) throws IOException {
        buf.order(ORDER);
        MsgWrap wrap = this.matchedFrame(buf);
        if (wrap == null) {
            return null;
        }

        return onDecodeMsg(ctx, wrap);
    }

    private MsgWrap matchedFrame(ByteBuffer byteBuffer) throws IOException {
        ByteBuf buf = Unpooled.wrappedBuffer(byteBuffer);
        if (buf.capacity() < minFrameLen) {
            return null;
        }

        byte[] tmp = new byte[HEAD_LEN];
        buf.readBytes(tmp);

        if (!Arrays.equals(HEAD, tmp)) {
            return null;
        }

        //crcBuf
        byte[] crcBytes = new byte[buf.readableBytes() - 2];
        buf.getBytes(buf.readerIndex(), crcBytes);

        // 解析消息头
        tmp = new byte[MSG_ID_LEN + MSG_ATTR_LEN + MOBILE_PHONE_NO_LEN + MSG_SEQ_NUM_LEN];
        buf.readBytes(tmp);

        MsgWrap wrap = new MsgWrap();
        wrap.packetHeader.setMessageBody(tmp);
        wrap.packetHeader.unpack(tmp);

        // 如果存在消息包封装项，则解析 分包总包数、包序号
        Integer msgAttr = wrap.getPacketHeader().get(MsgParam.MSG_ATTR);
        if ((msgAttr & 0x2000) > 0) {
            wrap.packetHeader.put(MsgParam.MSG_TOTAL_CNT, buf.readUnsignedShort());
            wrap.packetHeader.put(MsgParam.MSG_INDEX, buf.readUnsignedShort());
        } else {
            wrap.packetHeader.put(MsgParam.MSG_TOTAL_CNT, 1);
            wrap.packetHeader.put(MsgParam.MSG_INDEX, 1);
        }

        // 数据内容
        int messageBodyLength = wrap.packetHeader.get(MsgParam.MSG_BODY_LENGTH);
        tmp = new byte[messageBodyLength];
        buf.readBytes(tmp);
        //如果数据加密，则进行解密
        if ((msgAttr & 0x0400) > 0) {
            try {
                tmp = RSAUtil.decryptByPrivateKey(tmp, rsaPrivateKey);
            } catch (Exception e) {
                LOGGER.error(e.toString(), e);
                LOGGER.error("rsa decrypt fail");
                return null;
            }
        }

        wrap.content = ByteBuffer.allocate(messageBodyLength).order(ORDER);
        wrap.content.put(tmp);
        wrap.content.flip();

        // crc
        short crc = buf.readUnsignedByte();
        if (calcCrc(crcBytes) != crc) {
            return null;
        }

        setCrc(crc);

        // 包尾
        tmp = new byte[TAIL_LEN];
        buf.readBytes(tmp);
        if (!Arrays.equals(TAIL, tmp)) {
            return null;
        }

        return wrap;
    }

    /**
     * 数据包内容解码
     *
     * @param ctx
     * @param wrap
     * @return
     */
    abstract protected List<IMsg> onDecodeMsg(ChannelHandlerContext ctx, MsgWrap wrap);

    protected ByteBuffer encode(IMsg msg) {
        // encode content of msg
        ByteBuffer buffer = ByteBuffer.allocate(MaxBufferSize).order(ORDER);
        onEncodeMsg(buffer, msg);

        // msg content
        int contentLen = buffer.position();
        byte[] content = new byte[contentLen];
        System.arraycopy(buffer.array(), 0, content, 0, contentLen);

        //如果需要加密
        if (msg.getParams().get(MsgParam.DEV_RSA_N) != null && msg.getParams().get(MsgParam.DEV_RSA_E) !=
                null) {
            try {
                String n = (String) msg.getParams().get(MsgParam.DEV_RSA_N);
                String e = (String) msg.getParams().get(MsgParam.DEV_RSA_E);
                RSAPublicKey rsaPublicKey = RSAUtil.getPublicKey(n, e);
                content = RSAUtil.encryptByPublicKey(content, rsaPublicKey);
                contentLen = content.length;
                contentLen = contentLen | 0x0400;
            } catch (Exception e) {
                LOGGER.error(e.toString(), e);
                LOGGER.error("rsa encrypt fail");
            }
        }

        // begin to build frame
        int frameLen = minFrameLen + contentLen;
        buffer = ByteBuffer.allocate(frameLen).order(ORDER);
        // 用于临时存储计算校验码的属性
        ByteBuf crcHeaderBuf = Unpooled.buffer(12);

        // 标识位
        buffer.put(HEAD);

        PacketHeader ph = new PacketHeader();
        ph.put(MsgParam.MSG_TAG, msg.getMsgCode());
        ph.put(MsgParam.MSG_ATTR, contentLen);
        ph.put(MsgParam.PHONE_NO, msg.getTargetDeviceId());
        ph.put(MsgParam.CMD_RUNNING_NO, msg.getParams().get(MsgParam.CMD_RUNNING_NO));
        ph.pack();

        buffer.put(ph.getMessageBody());
        crcHeaderBuf.writeBytes(ph.getMessageBody());

        // 数据体
        buffer.put(content);

        // 校验码
        byte[] byteMessageHeader = new byte[12];
        crcHeaderBuf.getBytes(0, byteMessageHeader, 0, byteMessageHeader.length);
        int calcCrc = calcCrc(byteMessageHeader, content);
        buffer.put((byte) calcCrc);

        // 标识位
        buffer.put(TAIL);

        return buffer;
    }

    public int calcCrc(byte[] byteMessageHeader, byte[] byteMessageBody) {
        if (byteMessageHeader.length > 0) {
            int tmpCrc = byteMessageHeader[0] & 0xff;
            for (int i = 1; i < byteMessageHeader.length; i++) {
                tmpCrc = tmpCrc ^ (byteMessageHeader[i] & 0xff);
            }
            for (int i = 0; i < byteMessageBody.length; i++) {
                tmpCrc = tmpCrc ^ (byteMessageBody[i] & 0xff);
            }
            return tmpCrc;
        }
        return 0;
    }

    public int calcCrc(byte[] crcBytes) {
        if (crcBytes.length > 0) {
            int tmpCrc = crcBytes[0] & 0xff;
            for (int i = 1; i < crcBytes.length; i++) {
                tmpCrc = tmpCrc ^ (crcBytes[i] & 0xff);
            }
            return tmpCrc;
        }
        return 0;
    }

    /**
     * 发送消息给设备，进行编码
     *
     * @param buf
     * @param msg
     */
    abstract protected void onEncodeMsg(ByteBuffer buf, IMsg msg);

    public class MsgWrap {
        private PacketHeader packetHeader = new PacketHeader();
        private ByteBuffer content;

        public PacketHeader getPacketHeader() {
            return packetHeader;
        }

        public void setPacketHeader(PacketHeader packetHeader) {
            this.packetHeader = packetHeader;
        }

        public ByteBuffer getContent() {
            return content;
        }

        public void setContent(ByteBuffer content) {
            this.content = content;
        }
    }

    public int getCrc() {
        return crc;
    }

    public void setCrc(short crc) {
        this.crc = crc;
    }
}

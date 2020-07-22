package tf56.cloud.iot.store.dustbin.das;

import allthings.iot.common.msg.IMsg;
import allthings.iot.das.util.CcittCrc16Util;
import allthings.iot.das.util.DateUtil;
import allthings.iot.util.misc.ByteUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.Calendar;

/**
 * @author :  sylar
 * @FileName :  AbstractFrameCodec
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
public abstract class AbstractFrameCodec {

    /**
     * 协议帧结构:
     * {HEAD 2B | PacketLen 2B | CmdId 1B | DeviceTypeLen 1B | DeviceType N | DeviceIdLen 1B | DeviceId N | RtcTime
     * 7B | Content N | CRC 2B | TAIL 2B}
     * <p>
     * 字节序:小端模式
     * 时间戳说明: 请求指令的时间戳使用请求方的时间, 回应指令的时间戳须等于对应的请求指令的时间戳
     * CRC说明:因设备端数据流会经由串口传输,使用CRC防止串码
     */

    final ByteOrder ORDER = ByteOrder.LITTLE_ENDIAN;
    final int MaxBufferSize = 1024 * 2;
    final byte[] HEAD = new byte[]{0x2A, 0x2A};
    final byte[] TAIL = new byte[]{0x23, 0x23};
    final int HEAD_SIZE = HEAD.length;
    final int TAIL_SIZE = TAIL.length;
    final int CMD_SIZE = 1;
    final int CRC_SIZE = 2;
    final int PACKET_LEN_SIZE = 2;
    final int DevType_LEN_SIZE = 1;
    final int DevId_LEN_SIZE = 1;
    final int TIMESTAMP_SIZE = 7;
    final int minFrameLen = HEAD_SIZE + PACKET_LEN_SIZE + CMD_SIZE + DevType_LEN_SIZE + 0 + DevId_LEN_SIZE + 0 +
            TIMESTAMP_SIZE + 0 + CRC_SIZE + TAIL_SIZE;

    int devTypeLen, devIdLen, contentLen, dataPacketLen, frameLen;

    protected ByteBuffer encode(IMsg msg) {
        ByteBuffer buf = ByteBuffer.allocate(MaxBufferSize).order(ORDER);

        int cmdId = Integer.parseInt(msg.getMsgCode());
        String deviceType = msg.getSourceDeviceType();
        String deviceId = msg.getSourceDeviceId();
        byte[] timestamp = DateUtil.millisecond2Bytes(Calendar.getInstance().getTimeInMillis());

        //encode content of msg
        onEncodeMsg(buf, msg);

        //msg content
        byte[] content = new byte[buf.position()];
        System.arraycopy(buf.array(), 0, content, 0, content.length);

        devTypeLen = deviceType.length();
        devIdLen = deviceId.length();
        contentLen = content.length;
        dataPacketLen = CMD_SIZE + DevType_LEN_SIZE + DevId_LEN_SIZE + devTypeLen + devIdLen + TIMESTAMP_SIZE +
                contentLen;
        frameLen = HEAD_SIZE + PACKET_LEN_SIZE + dataPacketLen + CRC_SIZE + TAIL_SIZE;

        //packet content
        buf = ByteBuffer.allocate(dataPacketLen).order(ORDER);
        buf.put((byte) cmdId);
        buf.put((byte) devTypeLen);
        buf.put(deviceType.getBytes());
        buf.put((byte) devIdLen);
        buf.put(deviceId.getBytes());
        buf.put(timestamp);
        buf.put(content);
        buf.flip();

        //data packet
        byte[] dataPacket = new byte[buf.capacity()];
        System.arraycopy(buf.array(), 0, dataPacket, 0, dataPacket.length);

        //CRC
        short crc = CcittCrc16Util.calcCrc16(dataPacket);

        //build frame
        buf = ByteBuffer.allocate(frameLen).order(ORDER);
        //HEAD
        buf.put(HEAD);
        //数据包长度
        buf.putShort((short) dataPacketLen);
        //数据包
        buf.put(dataPacket);
        //crc
        buf.putShort(crc);
        //TAIL
        buf.put(TAIL);
        return buf;
    }

    protected IMsg decode(ByteBuffer buf) {
        buf.order(ORDER);
        MsgWrap wrap = matchedFrame(buf);
        if (wrap == null) {
            return null;
        }

        return onDecodeMsg(wrap);
    }

    public MsgWrap matchedFrame(ByteBuffer buf) {
        if (buf.capacity() < minFrameLen) {
            return null;
        }

        byte[] tmp = new byte[2];
        buf.get(tmp);

        if (!Arrays.equals(HEAD, tmp)) {
            return null;
        }

        dataPacketLen = buf.getShort();
        frameLen = HEAD_SIZE + PACKET_LEN_SIZE + dataPacketLen;
        if (buf.capacity() < frameLen) {
            return null;
        }

        //dataPacket
        dataPacketLen = dataPacketLen - CRC_SIZE - TAIL_SIZE;
        tmp = new byte[dataPacketLen];
        buf.get(tmp);

        //crc
        short crcRaw = buf.getShort();
        short crc1 = CcittCrc16Util.calcCrc16(tmp);

        //crc 校验不通过
        if (crcRaw != crc1) {
            return null;
        }

        //msg content
        buf = ByteBuffer.allocate(tmp.length).order(ORDER);
        buf.put(tmp);
        buf.flip();

        MsgWrap wrap = new MsgWrap();
        //cmdId
        wrap.cmdId = ByteUtils.toInt(buf.get());

        //deviceType
        devTypeLen = ByteUtils.toInt(buf.get());
        tmp = new byte[devTypeLen];
        buf.get(tmp);
        wrap.deviceType = new String(tmp);

        //deviceId
        devIdLen = ByteUtils.toInt(buf.get());
        tmp = new byte[devIdLen];
        buf.get(tmp);
        wrap.deviceId = new String(tmp);

        //timestamp
        wrap.timestamp = DateUtil.readDate(buf).getTime();

        //content
        contentLen = dataPacketLen - CMD_SIZE - DevType_LEN_SIZE - DevId_LEN_SIZE - devTypeLen - devIdLen -
                TIMESTAMP_SIZE;
        tmp = new byte[contentLen];
        buf.get(tmp);
        wrap.content = ByteBuffer.allocate(contentLen).order(ORDER);
        wrap.content.put(tmp);
        wrap.content.flip();

        return wrap;
    }

    /**
     * 消息编码
     *
     * @param buf
     * @param msg
     */
    abstract protected void onEncodeMsg(ByteBuffer buf, IMsg msg);

    /**
     * 消息解码
     *
     * @param wrap
     * @return
     */
    abstract protected IMsg onDecodeMsg(MsgWrap wrap);

    class MsgWrap {
        int cmdId;
        String deviceType, deviceId;
        long timestamp;
        ByteBuffer content;
    }

}

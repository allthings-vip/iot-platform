package allthings.iot.bsj.das.packet;

import allthings.iot.common.Constant;
import allthings.iot.util.misc.ByteUtils;
import allthings.iot.util.misc.Crc16Utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * @author :  luhao
 * @FileName :  PacketSetting
 * @CreateDate :  2018/1/12
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
public abstract class PacketSetting extends AbstractPacket {
    public PacketSetting(String packetId, int contentLength) {
        super(packetId);
        this.contentLength = contentLength;
    }

    /**
     * 包长度
     */
    private static final int PACKET_LENGTH = 11;
    /**
     * 包长（2B） + 伪ip(4b) + 校验（1B） + 包尾（1B）
     */
    private static final int BASE_LENGTH = 6;

    private int contentLength;

    @Override
    public byte[] pack() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(PACKET_LENGTH + contentLength).order(ByteOrder.BIG_ENDIAN);
        byteBuffer.put(HEAD);

        byte[] msgCodeBytes = ByteUtils.hexStringToBytes(getPacketId());
        byteBuffer.put(msgCodeBytes[0]);

        short packetLength = (short) (BASE_LENGTH + contentLength);
        byteBuffer.put(ByteUtils.getBytes(packetLength, ByteOrder.BIG_ENDIAN));

        byte[] deviceIdBytes = ByteUtils.hexStringToBytes(get(Constant.ATTR_DEVICE_ID));
        byteBuffer.put(deviceIdBytes);

        pack(byteBuffer);

        byteBuffer.put((byte) Crc16Utils.calcCrc(byteBuffer.array()));
        byteBuffer.put(TAIL);

        return byteBuffer.array();
    }

    /**
     * 设置的内容
     *
     * @param byteBuffer
     */
    public abstract void pack(ByteBuffer byteBuffer);
}

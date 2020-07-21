package allthings.iot.bsj.das.packet;

import allthings.iot.common.Constant;
import allthings.iot.util.misc.ByteUtils;
import allthings.iot.util.misc.Crc16Utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * @author :  luhao
 * @FileName :  PacketQuery
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
public class PacketQuery extends AbstractPacket {
    public PacketQuery(String packetId) {
        super(packetId);
    }

    /**
     * 包长度
     */
    private static final int PACKET_LENGTH = 11;

    @Override
    public byte[] pack() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(PACKET_LENGTH).order(ByteOrder.BIG_ENDIAN);
        byteBuffer.put(HEAD);

        byte[] msgCodeBytes = ByteUtils.hexStringToBytes(getPacketId());
        byteBuffer.put(msgCodeBytes[0]);
        byteBuffer.put(new byte[]{0x00, 0x06});

        byte[] deviceIdBytes = ByteUtils.hexStringToBytes(get(Constant.ATTR_DEVICE_ID));
        byteBuffer.put(deviceIdBytes);
        byteBuffer.put((byte) Crc16Utils.calcCrc(byteBuffer.array()));
        byteBuffer.put(TAIL);

        return byteBuffer.array();
    }
}

package allthings.iot.bb808.das.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import allthings.iot.bb808.common.MsgParam;
import allthings.iot.util.misc.ByteUtils;

/**
 * @author :  luhao
 * @FileName :  Packet0x0107
 * @CreateDate :  2017/12/21
 * @Description : 查询终端属性应答
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
public class Packet0x0107 extends AbstractPacket {

    public Packet0x0107() {
        super("0107");
    }

    @Override
    public void unpack(byte[] bytes) {
        ByteBuf buf = Unpooled.wrappedBuffer(bytes);
        super.put(MsgParam.DEV_GENRE, buf.readUnsignedShort());

        byte[] tmpBytes = null;

        tmpBytes = new byte[5];
        buf.readBytes(tmpBytes);
        super.put(MsgParam.MAKER_ID, ByteUtils.toString(tmpBytes, 0, tmpBytes.length));

        tmpBytes = new byte[20];
        buf.readBytes(tmpBytes);
        super.put(MsgParam.DEV_TYPE, ByteUtils.toString(tmpBytes, 0, tmpBytes.length));

        tmpBytes = new byte[7];
        buf.readBytes(tmpBytes);
        super.put(MsgParam.DEV_CODE, ByteUtils.toString(tmpBytes, 0, tmpBytes.length));

        tmpBytes = new byte[10];
        buf.readBytes(tmpBytes);
        super.put(MsgParam.DEV_SIM_ICCID, ByteUtils.bytesToHexString(tmpBytes).trim());

        short byteLength = buf.readUnsignedByte();
        tmpBytes = new byte[byteLength];
        buf.readBytes(tmpBytes);
        super.put(MsgParam.DEV_HARDWARE_VERSION, ByteUtils.toString(tmpBytes, 0, tmpBytes.length));

        byteLength = buf.readUnsignedByte();
        tmpBytes = new byte[byteLength];
        buf.readBytes(tmpBytes);
        super.put(MsgParam.DEV_FIRMWARE_VERSION, ByteUtils.toString(tmpBytes, 0, tmpBytes.length));

        super.put(MsgParam.GNSS_MODULE_ATTRIBUTES, buf.readUnsignedByte());
        super.put(MsgParam.COMMUNICATION_MODULE_ATTRIBUTES, buf.readUnsignedByte());
    }
}

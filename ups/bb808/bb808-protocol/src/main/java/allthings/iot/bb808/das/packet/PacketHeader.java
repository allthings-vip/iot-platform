package allthings.iot.bb808.das.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import allthings.iot.bb808.common.MsgParam;
import allthings.iot.bb808.common.RunningNumberFactory;
import allthings.iot.util.misc.ByteUtils;

/**
 * @author :  luhao
 * @FileName :  PacketHeader
 * @CreateDate :  2017/12/21
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
public class PacketHeader extends AbstractPacket {

    public PacketHeader() {
        super("Header");
    }

    @Override
    public void unpack(byte[] bytes) {
        ByteBuf buf = Unpooled.wrappedBuffer(bytes);

        byte[] tmpBytes = new byte[2];
        buf.readBytes(tmpBytes);
        super.put(MsgParam.MSG_TAG, ByteUtils.bytesToHexString(tmpBytes));

        int msgAttr = buf.readUnsignedShort();
        super.put(MsgParam.MSG_ATTR, msgAttr);

        super.put(MsgParam.MSG_BODY_LENGTH, msgAttr & 0x03FF);

        byte[] bytePhoneNo = new byte[6];
        buf.readBytes(bytePhoneNo);
        String phoneNo = ByteUtils.bytesToHexString(bytePhoneNo);
        super.put(MsgParam.PHONE_NO, phoneNo);

        super.put(MsgParam.RUNNING_NO, buf.readUnsignedShort());
    }

    @Override
    public byte[] pack() {
        byte[] content = new byte[12];
        ByteBuf buf = Unpooled.wrappedBuffer(content);
        buf.resetWriterIndex();

        buf.writeBytes(ByteUtils.hexStringToBytes(super.get(MsgParam.MSG_TAG)));

        Integer msgAttr = (Integer) super.get(MsgParam.MSG_ATTR);
        buf.writeShort(msgAttr.shortValue());

        buf.writeBytes(ByteUtils.hexStringToBytes(super.get(MsgParam.PHONE_NO)));
        if (super.get(MsgParam.CMD_RUNNING_NO) != null) {
            buf.writeShort(super.get(MsgParam.CMD_RUNNING_NO));
        } else {
            buf.writeShort((short) RunningNumberFactory.getInstance().getRunningNumber());
        }

        super.setMessageBody(content);
        return content;
    }
}

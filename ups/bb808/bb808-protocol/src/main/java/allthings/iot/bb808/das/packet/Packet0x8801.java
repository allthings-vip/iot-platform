package allthings.iot.bb808.das.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import allthings.iot.bb808.common.MsgParam;

/**
 * @author :  luhao
 * @FileName :  Packet0x8801
 * @CreateDate :  2017/12/21
 * @Description : 摄像头立即拍摄命令
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
public class Packet0x8801 extends AbstractPacket {

    public Packet0x8801() {
        super("8801");
    }

    @Override
    public byte[] pack() {
        byte[] content = new byte[1 + 2 + 2 + 1 + 1 + 1 + 1 + 1 + 1 + 1];
        ByteBuf buf = Unpooled.wrappedBuffer(content);
        buf.resetWriterIndex();

        buf.writeByte(super.get(MsgParam.CHANNEL_ID));
        buf.writeShort(super.get(MsgParam.SNAP_CMD));
        buf.writeShort(super.get(MsgParam.SNAP_INTERVAL));
        buf.writeByte(super.get(MsgParam.STORE_WAY));
        buf.writeByte(super.get(MsgParam.RESOLUTION));
        buf.writeByte(super.get(MsgParam.COMPRESSION_RATIO));
        buf.writeByte(super.get(MsgParam.BRIGHTNESS));
        buf.writeByte(super.get(MsgParam.CONTRAST_RATIO));
        buf.writeByte(super.get(MsgParam.SATURATION));
        buf.writeByte(super.get(MsgParam.CHROMA));

        super.setMessageBody(content);
        return content;
    }
}

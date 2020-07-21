package allthings.iot.bb808.das.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import allthings.iot.bb808.common.MsgParam;

/**
 * @author :  luhao
 * @FileName :  Packet0x8805
 * @CreateDate :  2017/12/21
 * @Description :  单条存储多媒体数据检索上传命令
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
public class Packet0x8805 extends AbstractPacket {
    public Packet0x8805() {
        super("8805");
    }

    @Override
    public byte[] pack() {
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(((Number)super.get(MsgParam.MULTIMEDIA_DATA_ID)).intValue());
        buf.writeByte(super.get(MsgParam.DELETE_FLAG));

        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        setMessageBody(bytes);
        return bytes;
    }


}

package allthings.iot.bb808.das.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import allthings.iot.bb808.common.MsgParam;
import allthings.iot.util.misc.ByteUtils;

/**
 * @author :  luhao
 * @FileName :  Packet0x8304
 * @CreateDate :  2017/12/21
 * @Description : 信息服务
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
public class Packet0x8304 extends AbstractPacket {
    public Packet0x8304() {
        super("8304");
    }

    @Override
    public byte[] pack() {
        ByteBuf buf = Unpooled.buffer();

        buf.writeByte(super.get(MsgParam.INFO_TYPE));
        String infoContentStr = super.get(MsgParam.INFO_CONTENT);
        byte[] infoContent = infoContentStr.getBytes(ByteUtils.CHARSET_NAME_GBK);
        buf.writeShort(infoContent.length);
        buf.writeBytes(infoContent);

        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        setMessageBody(bytes);
        return bytes;
    }
}

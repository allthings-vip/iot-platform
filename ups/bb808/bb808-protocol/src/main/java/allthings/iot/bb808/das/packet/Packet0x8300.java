package allthings.iot.bb808.das.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import allthings.iot.bb808.common.MsgParam;
import allthings.iot.util.misc.ByteUtils;

/**
 * @author :  luhao
 * @FileName :  Packet0x8300
 * @CreateDate :  2017/12/21
 * @Description : 文本信息下发
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
public class Packet0x8300 extends AbstractPacket {

    public Packet0x8300() {
        super("8300");
    }

    @Override
    public byte[] pack() {
        ByteBuf buf = Unpooled.buffer();

        buf.writeByte(super.get(MsgParam.TEXT_SEND_FLAG));

        String msg = super.get(MsgParam.TEXT_SEND_MSG);
        byte[] msgBytes = msg.getBytes(ByteUtils.CHARSET_UTF8);
        buf.writeBytes(msgBytes);

        // 取出消息体
        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        super.setMessageBody(bytes);
        return bytes;
    }
}

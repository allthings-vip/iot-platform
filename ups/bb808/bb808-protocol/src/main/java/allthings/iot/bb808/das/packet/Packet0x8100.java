package allthings.iot.bb808.das.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import allthings.iot.bb808.common.MsgParam;

import java.io.IOException;

/**
 * @author :  luhao
 * @FileName :  Packet0x8100
 * @CreateDate :  2017/12/21
 * @Description : 终端注册应答
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) allthings-vip All Rights Reserved
 * *******************************************************************************************
 */
public class Packet0x8100 extends AbstractPacket {

    public Packet0x8100() {
        super("8100");
    }

    public void unpack() throws IOException {

    }

    @Override
    public byte[] pack() {
        byte[] content = new byte[15];
        ByteBuf buf = Unpooled.wrappedBuffer(content);
        buf.resetWriterIndex();

        buf.writeShort(super.get(MsgParam.ACK_RUNNING_NO));
        buf.writeByte(super.get(MsgParam.ACK_RESULT));
        //buf.writeBytes(ByteUtils.getBytes(super.get(MsgParam.JIAN_QUAN)));

        super.setMessageBody(content);
        return content;
    }
}

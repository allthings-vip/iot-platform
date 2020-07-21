package allthings.iot.bb808.das.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import allthings.iot.bb808.common.MsgParam;
import allthings.iot.util.misc.ByteUtils;

/**
 * @author :  luhao
 * @FileName :  Packet0x8001
 * @CreateDate :  2017/12/21
 * @Description : 平台通用应答
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
public class Packet0x8001 extends AbstractPacket {

    public Packet0x8001() {
        super("8001");
    }

    @Override
    public byte[] pack() {
        byte[] content = new byte[5];
        ByteBuf buf = Unpooled.wrappedBuffer(content);
        buf.resetWriterIndex();

        buf.writeShort(super.get(MsgParam.ACK_RUNNING_NO));
        buf.writeBytes(ByteUtils.hexStringToBytes(super.get(MsgParam.ACK_ID)));
        buf.writeByte(super.get(MsgParam.ACK_RESULT));

        super.setMessageBody(content);
        return content;
    }
}

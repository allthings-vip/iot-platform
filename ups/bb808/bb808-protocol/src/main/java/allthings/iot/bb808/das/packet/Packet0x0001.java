package allthings.iot.bb808.das.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import allthings.iot.bb808.common.MsgParam;


/**
 * @author :  luhao
 * @FileName :  Packet0x0001
 * @CreateDate :  2017/12/21
 * @Description : 终端通用应答
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) allthings-vip All Rights Reserved
 * *******************************************************************************************
 */
public class Packet0x0001 extends AbstractPacket {

    public Packet0x0001() {
        super("0001");
    }

    @Override
    public void unpack(byte[] bytes) {
        ByteBuf buf = Unpooled.wrappedBuffer(bytes);

        super.put(MsgParam.ACK_RUNNING_NO, buf.readUnsignedShort());

        super.put(MsgParam.ACK_ID, Integer.toHexString(buf.readUnsignedShort()));

        super.put(MsgParam.ACK_RESULT, buf.readUnsignedByte());
    }
}

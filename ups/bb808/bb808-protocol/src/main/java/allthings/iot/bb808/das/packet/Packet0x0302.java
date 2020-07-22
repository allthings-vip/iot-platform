package allthings.iot.bb808.das.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import allthings.iot.bb808.common.MsgParam;

/**
 * @author :  luhao
 * @FileName :  Packet0x0302
 * @CreateDate :  2017/12/21
 * @Description : 提问应答
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) allthings-vip All Rights Reserved
 * *******************************************************************************************
 */
public class Packet0x0302 extends AbstractPacket {
    public Packet0x0302(){
        super("0302");
    }

    @Override
    public void unpack(byte[] bytes) {
        ByteBuf buf = Unpooled.wrappedBuffer(bytes);

        super.getParamMap().put(MsgParam.ACK_RUNNING_NO,buf.readUnsignedShort());
        super.getParamMap().put(MsgParam.ANSWER_ID,buf.readUnsignedByte());
    }
}

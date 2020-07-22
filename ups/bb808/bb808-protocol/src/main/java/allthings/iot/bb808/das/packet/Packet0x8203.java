package allthings.iot.bb808.das.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import allthings.iot.bb808.common.MsgParam;

/**
 * @author :  luhao
 * @FileName :  Packet0x8203
 * @CreateDate :  2017/12/21
 * @Description : 人工报警信息确认
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) allthings-vip All Rights Reserved
 * *******************************************************************************************
 */
public class Packet0x8203 extends AbstractPacket {

    public Packet0x8203(){
        super("8203");
    }

    @Override
    public byte[] pack() {
        ByteBuf buf = Unpooled.buffer();
        buf.writeShort(super.get(MsgParam.ALARM_RUNNING_NO));
        buf.writeInt(super.get(MsgParam.ALARM_TYPE));

        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        super.setMessageBody(bytes);
        return bytes;
    }
}

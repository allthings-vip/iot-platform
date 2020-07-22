package allthings.iot.bb808.das.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import allthings.iot.bb808.common.MsgParam;

/**
 * @author :  luhao
 * @FileName :  Packet0x0201
 * @CreateDate :  2017/12/21
 * @Description : 位置信息查询应答
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) allthings-vip All Rights Reserved
 * *******************************************************************************************
 */
public class Packet0x0201 extends AbstractPacket {
    public Packet0x0201(){
        super("0201");
    }

    @Override
    public void unpack(byte[] bytes) {
        ByteBuf buf = Unpooled.wrappedBuffer(super.getMessageBody());

        super.put(MsgParam.ACK_RUNNING_NO,buf.readUnsignedShort());

        byte[] tempByte = new byte[buf.readableBytes()];
        buf.readBytes(tempByte);

        //位置信息
        Packet0x0200 packet0x0200 = new Packet0x0200();
        packet0x0200.setMessageBody(tempByte);
        packet0x0200.unpack(tempByte);
        super.getParamMap().putAll(packet0x0200.getParamMap());
    }
}

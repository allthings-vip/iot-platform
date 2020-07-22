package allthings.iot.bb808.das.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import allthings.iot.bb808.common.MsgParam;

/**
 * @author :  luhao
 * @FileName :  Packet0x8202
 * @CreateDate :  2017/12/21
 * @Description : 临时位置跟踪
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) allthings-vip All Rights Reserved
 * *******************************************************************************************
 */
public class Packet0x8202 extends AbstractPacket {

    public Packet0x8202(){
        super("8202");
    }

    @Override
    public byte[] pack() {
        ByteBuf buf = Unpooled.buffer();

        //todo 异常处理 interval<0时
        int interval = super.get(MsgParam.TIME_INTERVAL);
        buf.writeShort(interval);
        if(interval > 0){
            buf.writeInt(((Number)super.get(MsgParam.EFFECTIVE_TIME)).intValue());
        }

        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        super.setMessageBody(bytes);
        return bytes;
    }
}

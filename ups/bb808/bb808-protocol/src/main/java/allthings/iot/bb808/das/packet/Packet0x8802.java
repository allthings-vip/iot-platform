package allthings.iot.bb808.das.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import allthings.iot.bb808.common.MsgParam;
import allthings.iot.das.util.DateUtil;

/**
 * @author :  luhao
 * @FileName :  Packet0x8802
 * @CreateDate :  2017/12/21
 * @Description : 存储多媒体数据检索
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) allthings-vip All Rights Reserved
 * *******************************************************************************************
 */
public class Packet0x8802 extends AbstractPacket {
    public Packet0x8802() {
        super("8802");
    }

    @Override
    public byte[] pack() {
        ByteBuf buf = Unpooled.buffer();
        buf.writeByte(super.get(MsgParam.MULTIMEDIA_TYPE));
        buf.writeByte(super.get(MsgParam.CHANNEL_ID));
        buf.writeByte(super.get(MsgParam.MULTIMEDIA_EVENT));
        long startTime = Long.parseLong(super.get(MsgParam.START_TIME).toString());
        if (startTime == 0) {
            buf.writeBytes(new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00});
        } else {
            buf.writeBytes(DateUtil.millisecond2BytesSix(startTime));
        }

        long endTime = Long.parseLong(super.get(MsgParam.END_TIME).toString());
        if (endTime == 0) {
            buf.writeBytes(new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00});
        } else {
            buf.writeBytes(DateUtil.millisecond2BytesSix(endTime));
        }
        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        setMessageBody(bytes);
        return bytes;
    }


}

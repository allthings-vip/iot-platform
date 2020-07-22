package allthings.iot.bb808.das.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import allthings.iot.bb808.common.MsgParam;
import allthings.iot.das.util.DateUtil;

/**
 * @author :  luhao
 * @FileName :  Packet0x8803
 * @CreateDate :  2017/12/21
 * @Description : 存储多媒体数据上传命令
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) allthings-vip All Rights Reserved
 * *******************************************************************************************
 */
public class Packet0x8803 extends AbstractPacket {
    public Packet0x8803() {
        super("8803");
    }

    @Override
    public byte[] pack() {
        ByteBuf buf = Unpooled.buffer();
        buf.writeByte(super.get(MsgParam.MULTIMEDIA_TYPE));
        buf.writeByte(super.get(MsgParam.CHANNEL_ID));
        buf.writeByte(super.get(MsgParam.MULTIMEDIA_EVENT));
        long startTime = super.get(MsgParam.START_TIME);
        buf.writeBytes(DateUtil.millisecond2BytesSix(startTime));
        long endTime = super.get(MsgParam.END_TIME);
        buf.writeBytes(DateUtil.millisecond2BytesSix(endTime));

        buf.writeByte(super.get(MsgParam.DELETE_FLAG));
        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        setMessageBody(bytes);
        return bytes;
    }


}

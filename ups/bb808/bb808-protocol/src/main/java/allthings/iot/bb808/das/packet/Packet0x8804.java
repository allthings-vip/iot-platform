package allthings.iot.bb808.das.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import allthings.iot.bb808.common.MsgParam;

/**
 * @author :  luhao
 * @FileName :  Packet0x8804
 * @CreateDate :  2017/12/21
 * @Description :  录音开始命令
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) allthings-vip All Rights Reserved
 * *******************************************************************************************
 */
public class Packet0x8804 extends AbstractPacket {
    public Packet0x8804() {
        super("8804");
    }

    @Override
    public byte[] pack() {
        ByteBuf buf = Unpooled.buffer();
        buf.writeByte(super.get(MsgParam.RECODING_COMMAND));
        buf.writeShort(super.get(MsgParam.RECODING_TIME));
        buf.writeByte(super.get(MsgParam.RECODING_SAVE_FLAG));
        buf.writeByte(super.get(MsgParam.RECODING_AUDIO_SAMPLING_RATE));

        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        setMessageBody(bytes);
        return bytes;
    }


}

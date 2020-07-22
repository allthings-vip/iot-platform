package allthings.iot.bb808.das.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import allthings.iot.bb808.common.MsgParam;

/**
 * @author :  luhao
 * @FileName :  Packet0x0800
 * @CreateDate :  2017/12/21
 * @Description : 多媒体事件信息上传
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) allthings-vip All Rights Reserved
 * *******************************************************************************************
 */
public class Packet0x0800 extends AbstractPacket {
    public Packet0x0800() {
        super("0800");
    }

    @Override
    public void unpack(byte[] bytes) {
        ByteBuf buf = Unpooled.wrappedBuffer(bytes);

        super.put(MsgParam.MULTIMEDIA_DATA_ID, buf.readUnsignedInt());
        super.put(MsgParam.MULTIMEDIA_TYPE, buf.readByte());
        super.put(MsgParam.MULTIMEDIA_FORMAT, buf.readByte());
        super.put(MsgParam.MULTIMEDIA_EVENT,buf.readByte());
        super.put(MsgParam.CHANNEL_ID,buf.readByte());

    }
}

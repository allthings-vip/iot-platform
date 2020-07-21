package allthings.iot.bb808.das.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import allthings.iot.bb808.common.MsgParam;

import java.util.Base64;

/**
 * @author :  luhao
 * @FileName :  Packet0x0801
 * @CreateDate :  2017/12/21
 * @Description : 多媒体数据上传
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
public class Packet0x0801 extends AbstractPacket {

    public Packet0x0801() {
        super("0801");
    }

    @Override
    public void unpack(byte[] bytes) {
        ByteBuf buf = Unpooled.wrappedBuffer(bytes);

        super.put(MsgParam.MULTIMEDIA_DATA_ID, buf.readUnsignedInt());
        super.put(MsgParam.MULTIMEDIA_TYPE, buf.readUnsignedByte());
        super.put(MsgParam.MULTIMEDIA_FORMAT, buf.readUnsignedByte());
        super.put(MsgParam.MULTIMEDIA_EVENT, buf.readUnsignedByte());
        super.put(MsgParam.CHANNEL_ID, buf.readUnsignedByte());

        byte[] tmpBytes = new byte[Packet0x0200.DATA_LENGTH];
        buf.readBytes(tmpBytes);
        Packet0x0200 packet0x0200 = new Packet0x0200();
        packet0x0200.setMessageBody(tmpBytes);
        packet0x0200.unpack(tmpBytes);
        super.getParamMap().putAll(packet0x0200.getParamMap());

        // 所有分包整合后的完整多媒体数据
        tmpBytes = new byte[buf.readableBytes()];
        buf.readBytes(tmpBytes);
        String multimediaData = Base64.getEncoder().encodeToString(tmpBytes);
        super.put(MsgParam.MULTIMEDIA_DATA, multimediaData);

        // 设置数据类型，这里有多个：多媒体、GPS TODO 参考人员的做法
    }
}

package allthings.iot.bb808.das.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import allthings.iot.bb808.common.MsgParam;
import allthings.iot.util.misc.ByteUtils;

/**
 * @author :  luhao
 * @FileName :  Packet0x8108
 * @CreateDate :  2017/12/21
 * @Description : 下发终端升级包
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) allthings-vip All Rights Reserved
 * *******************************************************************************************
 */
public class Packet0x8108 extends AbstractPacket {

    public Packet0x8108() {
        super("8108");
    }

    @Override
    public byte[] pack() {
        ByteBuf buf = Unpooled.buffer();
        buf.writeByte(super.get(MsgParam.UPGRADE_TYPE));
        String markId = super.get(MsgParam.MAKER_ID);
        buf.writeBytes(markId.getBytes(ByteUtils.CHARSET_UTF8));
        String versionNo = super.get(MsgParam.VERSION_NO);
        byte[] version = versionNo.getBytes(ByteUtils.CHARSET_UTF8);
        buf.writeByte(version.length);
        buf.writeBytes(version);
        String upgradeData = super.get(MsgParam.UPGRADE_DATA);
        byte[] data = upgradeData.getBytes(ByteUtils.CHARSET_UTF8);
        buf.writeInt(data.length);
        buf.writeBytes(data);

        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        setMessageBody(bytes);
        return bytes;
    }
}

package allthings.iot.bb808.das.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import allthings.iot.bb808.common.MsgParam;
import allthings.iot.util.misc.ByteUtils;

/**
 * @author :  luhao
 * @FileName :  Packet0x8400
 * @CreateDate :  2017/12/21
 * @Description : 电话回拨。平台要求设备拨打指定电话号码。
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
public class Packet0x8400 extends AbstractPacket {

    public Packet0x8400() {
        super("8400");
    }

    @Override
    public byte[] pack() {
        ByteBuf buf = Unpooled.buffer();

        buf.writeByte(super.get(MsgParam.CALL_MODE));

        String cellPhoneNo = super.get(MsgParam.CALL_PHONE_NO);
        byte[] phoneBytes = cellPhoneNo.getBytes(ByteUtils.CHARSET_UTF8);
        buf.writeBytes(phoneBytes);

        // 取出消息体
        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        super.setMessageBody(bytes);
        return bytes;
    }
}

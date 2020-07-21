package allthings.iot.bb808.das.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import allthings.iot.bb808.common.MsgParam;
import allthings.iot.util.misc.ByteUtils;

import java.util.List;
import java.util.Map;

/**
 * @author :  luhao
 * @FileName :  Packet0x8401
 * @CreateDate :  2017/12/21
 * @Description : 设置电话本
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
public class Packet0x8401 extends AbstractPacket {
    public Packet0x8401() {
        super("8401");
    }

    @Override
    public byte[] pack() {
        ByteBuf buf = Unpooled.buffer();
        int setType = super.get(MsgParam.CONTANT_SET_TYPE);
        buf.writeByte(setType);
        if (setType != 0) {
            List<Map<String, Object>> contants = super.get(MsgParam.CONTANT_LIST);
            buf.writeByte(contants.size());
            for (Map<String, Object> contant : contants) {
                buf.writeByte((int) contant.get(MsgParam.CONTANT_FLAG));
                String contactPhone = (String) contant.get(MsgParam.CONTANT_PHONE);
                byte[] phone = contactPhone.getBytes(ByteUtils.CHARSET_UTF8);
                buf.writeByte(phone.length);
                buf.writeBytes(phone);
                String contentName = (String) contant.get(MsgParam.CONTENT_NAME);
                byte[] name = contentName.getBytes(ByteUtils.CHARSET_NAME_GBK);
                buf.writeByte(name.length);
                buf.writeBytes(name);
            }
        }

        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        setMessageBody(bytes);
        return bytes;
    }
}

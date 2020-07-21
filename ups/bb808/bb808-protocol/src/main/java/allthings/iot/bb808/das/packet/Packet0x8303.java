package allthings.iot.bb808.das.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import allthings.iot.bb808.common.MsgParam;
import allthings.iot.util.misc.ByteUtils;

import java.util.List;
import java.util.Map;

/**
 * @author :  luhao
 * @FileName :  Packet0x8303
 * @CreateDate :  2017/12/21
 * @Description : 信息点播菜单设置
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
public class Packet0x8303 extends AbstractPacket {

    public Packet0x8303() {
        super("8303");
    }

    @Override
    public byte[] pack() {
        ByteBuf buf = Unpooled.buffer();
        int infoSetType = super.get(MsgParam.INFO_SET_TYPE);
        buf.writeByte(infoSetType);
        if (infoSetType != 0) {
            List<Map<String, Object>> infoList = (List<Map<String, Object>>) super.getParamMap().get(MsgParam
                    .INFO_LIST);
            buf.writeByte(infoList.size());
            for (Map<String, Object> info : infoList) {
                buf.writeByte((int) info.get(MsgParam.INFO_TYPE));
                String infoName = (String) info.get(MsgParam.INFO_NAME);
                byte[] infoContent = infoName.getBytes(ByteUtils.CHARSET_NAME_GBK);
                buf.writeShort(infoContent.length);
                buf.writeBytes(infoContent);
            }
        }

        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        super.setMessageBody(bytes);
        return bytes;
    }
}

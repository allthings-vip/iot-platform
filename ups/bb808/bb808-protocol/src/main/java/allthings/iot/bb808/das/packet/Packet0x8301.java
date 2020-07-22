package allthings.iot.bb808.das.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import allthings.iot.bb808.common.MsgParam;
import allthings.iot.util.misc.ByteUtils;

import java.util.List;
import java.util.Map;

/**
 * @author :  luhao
 * @FileName :  Packet0x8301
 * @CreateDate :  2017/12/21
 * @Description : 事件设置
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) allthings-vip All Rights Reserved
 * *******************************************************************************************
 */
public class Packet0x8301 extends AbstractPacket {

    public Packet0x8301() {
        super("8301");
    }

    @Override
    public byte[] pack() {
        ByteBuf buf = Unpooled.buffer();

        int eventSetType = super.get(MsgParam.EVENT_SET_TYPE);
        buf.writeByte(eventSetType);

        if (eventSetType != 0) {
            List<Map<String, Object>> events = (List<Map<String, Object>>) super.getParamMap().get(MsgParam
                    .EVENT_LIST);
            buf.writeByte(events.size());
            for (Map<String, Object> event : events) {
                buf.writeByte((int) event.get(MsgParam.EVENT_ID));
                if (eventSetType != 4) {
                   String eventContent = (String) event.get(MsgParam.EVENT_CONTENT);
                    byte[] tmpBytes = eventContent.getBytes(ByteUtils.CHARSET_NAME_GBK);
                    buf.writeByte(tmpBytes.length);
                    buf.writeBytes(tmpBytes);
                }
            }
        }

        // 取出消息体
        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        super.setMessageBody(bytes);
        return bytes;
    }
}

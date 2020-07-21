package allthings.iot.bb808.das.packet;

import com.google.common.base.Splitter;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import allthings.iot.bb808.common.MsgParam;
import allthings.iot.util.misc.StringUtils;

import java.util.List;

/**
 * @author :  luhao
 * @FileName :  Packet0x8800
 * @CreateDate :  2017/12/21
 * @Description : 多媒体数据上传应答
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
public class Packet0x8800 extends AbstractPacket {

    public Packet0x8800() {
        super("8800");
    }

    @Override
    public byte[] pack() {
        ByteBuf buf = Unpooled.buffer(4);

        buf.writeInt(((Number) super.get(MsgParam.MULTIMEDIA_DATA_ID)).intValue());
        buf.writeByte(super.get(MsgParam.RETRANS_PACKET_COUNT));

        String ids = super.get(MsgParam.RETRANS_PACKET_ID_LIST);
        if (StringUtils.isNotBlank(ids)) {
            List<String> idList = Splitter.on(";").splitToList(ids);
            for (String id : idList) {
                buf.writeShort(Integer.parseInt(id));
            }
        }

        byte[] content = new byte[buf.readableBytes()];
        buf.readBytes(content);
        super.setMessageBody(content);
        return content;
    }
}

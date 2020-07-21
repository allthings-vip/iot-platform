package allthings.iot.bb808.das.packet;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import allthings.iot.bb808.common.MsgParam;

import java.util.List;
import java.util.Map;

/**
 * @author :  luhao
 * @FileName :  Packet0x0802
 * @CreateDate :  2017/12/21
 * @Description : 存储多媒体数据检索应答
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
public class Packet0x0802 extends AbstractPacket {
    public Packet0x0802() {
        super("0802");
    }

    @Override
    public void unpack(byte[] bytes) {
        ByteBuf buf = Unpooled.wrappedBuffer(bytes);
        super.put(MsgParam.ACK_RUNNING_NO,buf.readUnsignedShort());
        int count = buf.readUnsignedShort();
        List<Map<String, Object>> searchItems = Lists.newArrayList();
        while(buf.readableBytes() > 34){
            Map<String,Object> searchItem = Maps.newHashMap();
            searchItem.put(MsgParam.MULTIMEDIA_DATA_ID,buf.readUnsignedInt());
            searchItem.put(MsgParam.MULTIMEDIA_TYPE, buf.readByte());
            searchItem.put(MsgParam.CHANNEL_ID,buf.readUnsignedByte());
            searchItem.put(MsgParam.MULTIMEDIA_EVENT,buf.readByte());

            byte[] tmpBytes = new byte[28];
            buf.readBytes(tmpBytes);

            Packet0x0200 packet0x0200 = new Packet0x0200();
            packet0x0200.setMessageBody(tmpBytes);
            packet0x0200.unpack(tmpBytes);
            searchItem.putAll(packet0x0200.getParamMap());
            searchItems.add(searchItem);
        }
        super.put(MsgParam.MULTIMEDIA_SEARCH_ITEMS, searchItems);
    }
}

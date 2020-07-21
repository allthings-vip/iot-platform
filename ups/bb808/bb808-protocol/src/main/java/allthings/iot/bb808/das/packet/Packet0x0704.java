package allthings.iot.bb808.das.packet;

import com.google.common.collect.Lists;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import allthings.iot.bb808.common.MsgParam;
import allthings.iot.constant.gps.GpsMsgParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author :  luhao
 * @FileName :  Packet0x0704
 * @CreateDate :  2017/12/21
 * @Description : 定位数据批量上传
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
public class Packet0x0704 extends AbstractPacket {


    public Packet0x0704() {
        super("0704");
    }

    @Override
    public void unpack(byte[] bytes) {
        ByteBuf buf = Unpooled.wrappedBuffer(bytes);
        // 数据项个数 WORD 包含的位置汇报数据项个数，>0
        int itemNum = buf.readUnsignedShort();
        // 位置数据类型 BYTE 0：正常位置批量汇报，1：盲区补报
        short dataType = buf.readUnsignedByte();
        // 位置汇报数据项
        List<Packet0x0704Item> itemList = new ArrayList<Packet0x0704Item>();

        Packet0x0704Item item = null;
        int length = 0;
        byte[] p0x0200Bytes = null;
        Packet0x0200 p0x0200 = null;

        while (buf.readableBytes() > 0) {
            length = buf.readUnsignedShort();
            p0x0200Bytes = new byte[length];
            buf.readBytes(p0x0200Bytes, 0, length);

            item = new Packet0x0704Item();
            item.setLength(length);

            p0x0200 = new Packet0x0200();
            p0x0200.setMessageBody(p0x0200Bytes);
            p0x0200.unpack(p0x0200Bytes);
            item.setData(p0x0200);

            itemList.add(item);
        }

        super.put(MsgParam.POSITION_UPLOAD_BATCH_ITEM_CNT, itemNum);
        super.put(MsgParam.POSITION_UPLOAD_BATCH_TYPE, dataType);

        List<Map<String, Object>> gpsParamMapList = Lists.newArrayList();

        Map<String, Object> itemParamMap = null;
        List<Map<String, Object>> itemGpsParamMapList = null;
        for (Packet0x0704Item packet0x0704Item : itemList) {

            itemParamMap = packet0x0704Item.getData().getParamMap();
            itemGpsParamMapList = (List<Map<String, Object>>) itemParamMap.get(GpsMsgParam.DATA_CONTENT);

            gpsParamMapList.addAll(itemGpsParamMapList);
        }
        super.put(GpsMsgParam.DATA_CONTENT, gpsParamMapList);
    }

    private class Packet0x0704Item {
        /**位置汇报数据体长度 WORD 位置汇报数据体长度，n */
        private int length;
        /** 位置汇报数据体 BYTE[n] 定义见8.12 位置信息汇报*/
        private Packet0x0200 data;

        public int getLength() {
            return length;
        }

        public void setLength(int length) {
            this.length = length;
        }

        public Packet0x0200 getData() {
            return data;
        }

        public void setData(Packet0x0200 data) {
            this.data = data;
        }
    }
}

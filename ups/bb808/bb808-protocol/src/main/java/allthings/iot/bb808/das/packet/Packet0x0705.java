package allthings.iot.bb808.das.packet;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import allthings.iot.bb808.common.MsgParam;
import allthings.iot.util.misc.ByteUtils;

import java.util.List;
import java.util.Map;

/**
 * @author :  luhao
 * @FileName :  Packet0x0705
 * @CreateDate :  2017/12/21
 * @Description : CAN总线数据上传
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
public class Packet0x0705 extends AbstractPacket {
    public Packet0x0705() {
        super("0705");
    }

    @Override
    public void unpack(byte[] bytes) {
        ByteBuf buf = Unpooled.wrappedBuffer(bytes);

        int count = buf.readUnsignedShort();

        byte[] tmpByte;
        tmpByte = new byte[5];
        buf.readBytes(tmpByte);

        super.put(MsgParam.CAN_TIME, ByteUtils.bytesToHexString(tmpByte));
        List<Map<String, Object>> canDatas = Lists.newArrayList();
        while (buf.readableBytes() > 0) {
            Map<String, Object> canData = Maps.newHashMap();
            long tmp = buf.readUnsignedInt();
            int bit = 0;
            bit = (tmp & 0x80000000) > 0 ? 1 : 0; //CAN 通道号
            canData.put(MsgParam.CAN_CHANNEL, bit);
            bit = (tmp & 0x40000000) > 0 ? 1 : 0; //CAN 帧类型
            canData.put(MsgParam.CAN_FRAME_TYPE, bit);
            bit = (tmp & 0x20000000) > 0 ? 1 : 0; //数据采集方式
            canData.put(MsgParam.CAN_COLLECT_TYPE, bit);
            tmp &= 0x1fffffff;
            canData.put(MsgParam.CAN_ID, tmp);
            tmp = buf.readLong();
            canData.put(MsgParam.CAN_DATA, tmp);
            canDatas.add(canData);
        }
        super.put(MsgParam.CAN_DATA_LIST, canDatas);
    }
}

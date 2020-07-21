package allthings.iot.bb808.das.packet;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import allthings.iot.bb808.common.JBT19506MsgParam;
import allthings.iot.util.misc.ByteUtils;

import java.util.List;
import java.util.Map;

/**
 * @author :  luhao
 * @FileName :  Recorder0x09
 * @CreateDate :  2017/12/21
 * @Description : 采集指定的位置信息记录
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
public class Recorder0x09 extends JBT19506Packet {

    public Recorder0x09() {
        super("09");
    }

    @Override
    public void unpack(byte[] bytes) {
        ByteBuf buf = Unpooled.wrappedBuffer(bytes);

        List<Map<String, Object>> hourDatas = Lists.newArrayList();
        //每小时的记录
        while (buf.readableBytes() > 0) {
            byte[] tmpByte = new byte[6];
            buf.readBytes(tmpByte);
            Map<String, Object> hourData = Maps.newHashMap();
            hourData.put(JBT19506MsgParam.HOUR_TIME, ByteUtils.bytesToHexString(tmpByte));
            List<Map<String, Object>> minuteDatas = Lists.newArrayList();
            //每分钟的记录
            for (int i = 0; i < 60; i++) {
                Map<String, Object> minuteData = Maps.newHashMap();
                long lng = buf.readUnsignedInt();
                if (lng == 0xFFFFFFFF || lng == 0x7FFFFFFF) {
                    minuteData.put(JBT19506MsgParam.RECODER_LNG, 0);
                } else {
                    minuteData.put(JBT19506MsgParam.RECODER_LNG, lng);
                }
                long lat = buf.readUnsignedInt();
                if (lng == 0xFFFFFFFF || lng == 0x7FFFFFFF) {
                    minuteData.put(JBT19506MsgParam.RECODER_LAT, 0);
                } else {
                    minuteData.put(JBT19506MsgParam.RECODER_LAT, lat);
                }
                int altitude = buf.readUnsignedShort();
                if (altitude == 0xFFFF) {
                    minuteData.put(JBT19506MsgParam.RECODER_ALTITUDE, 0);
                } else {
                    minuteData.put(JBT19506MsgParam.RECODER_ALTITUDE, altitude);
                }
                short avgSpeed = buf.readUnsignedByte();
                if (avgSpeed == 0xFF) {
                    minuteData.put(JBT19506MsgParam.RECODER_AVG_SPEED, -1);
                } else {
                    minuteData.put(JBT19506MsgParam.RECODER_AVG_SPEED, avgSpeed);
                }
                minuteDatas.add(minuteData);
            }
            hourData.put(JBT19506MsgParam.RECODER_MIN_LIST, minuteDatas);
            hourDatas.add(hourData);
        }
        super.put(JBT19506MsgParam.RECODER_HOUR_LIST, hourDatas);


    }
}

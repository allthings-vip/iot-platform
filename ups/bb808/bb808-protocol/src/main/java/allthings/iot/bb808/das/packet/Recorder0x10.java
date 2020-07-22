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
 * @FileName :  Recorder0x10
 * @CreateDate :  2017/12/21
 * @Description : 采集指定的事故疑点记录
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) allthings-vip All Rights Reserved
 * *******************************************************************************************
 */
public class Recorder0x10 extends JBT19506Packet {
    public Recorder0x10() {
        super("10");
    }

    @Override
    public void unpack(byte[] bytes) {
        ByteBuf buf = Unpooled.wrappedBuffer(bytes);
        byte[] tmpBytes = null;

        List<Map<String, Object>> accidents = Lists.newArrayList();
        while (buf.readableBytes() > 0) {
            Map<String, Object> accident = Maps.newHashMap();
            tmpBytes = new byte[6];
            buf.readBytes(tmpBytes);
            accident.put(JBT19506MsgParam.RECODER_TIME, ByteUtils.bytesToHexString(tmpBytes));

            tmpBytes = new byte[18];
            buf.readBytes(tmpBytes);
            accident.put(JBT19506MsgParam.DRIVER_LICENSE_NUMBER, ByteUtils.toString(tmpBytes, 0, tmpBytes.length));

            accident.put(JBT19506MsgParam.RECODER_SPEED, buf.readUnsignedByte());
            accident.put(JBT19506MsgParam.STATUS_SIGNAL, buf.readUnsignedByte());

            List<Map<String, Object>> secendDatas = Lists.newArrayList();
            //每0.2秒的记录
            for (int i = 0; i < 100; i++) {
                Map<String, Object> data = Maps.newHashMap();
                short speed = buf.readUnsignedByte();
                if (speed == 0xFF) {
                    data.put(JBT19506MsgParam.RECODER_SPEED, -1);
                } else {
                    data.put(JBT19506MsgParam.RECODER_SPEED, speed);
                }
                data.put(JBT19506MsgParam.RECODER_SIGNAL, buf.readUnsignedByte());
                secendDatas.add(data);
            }

            long lng = buf.readUnsignedInt();
            if (lng == 0xFFFFFFFF || lng == 0x7FFFFFFF) {
                accident.put(JBT19506MsgParam.RECODER_LNG, 0);
            } else {
                accident.put(JBT19506MsgParam.RECODER_LNG, lng);
            }
            long lat = buf.readUnsignedInt();
            if (lng == 0xFFFFFFFF || lng == 0x7FFFFFFF) {
                accident.put(JBT19506MsgParam.RECODER_LAT, 0);
            } else {
                accident.put(JBT19506MsgParam.RECODER_LAT, lat);
            }
            int altitude = buf.readUnsignedShort();
            if (altitude == 0xFFFF) {
                accident.put(JBT19506MsgParam.RECODER_ALTITUDE, 0);
            } else {
                accident.put(JBT19506MsgParam.RECODER_ALTITUDE, altitude);
            }
            accidents.add(accident);
        }
        super.put(JBT19506MsgParam.RECODER_ACCIDENT, accidents);
    }
}

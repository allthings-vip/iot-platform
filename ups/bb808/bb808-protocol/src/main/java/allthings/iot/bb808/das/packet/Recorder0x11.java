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
 * @FileName :  Recorder0x11
 * @CreateDate :  2017/12/21
 * @Description : 采集指定的超时驾驶记录
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
public class Recorder0x11 extends JBT19506Packet {

    public Recorder0x11() {
        super("11");
    }

    @Override
    public void unpack(byte[] bytes) {
        ByteBuf buf = Unpooled.wrappedBuffer(bytes);
        byte[] tmpBytes = null;

        List<Map<String, Object>> overtimeDatas = Lists.newArrayList();
        while (buf.readableBytes() > 0) {
            Map<String, Object> overtimeData = Maps.newHashMap();
            tmpBytes = new byte[18];
            buf.readBytes(tmpBytes);
            super.put(JBT19506MsgParam.DRIVER_LICENSE_NUMBER, ByteUtils.toString(tmpBytes, 0, tmpBytes.length));

            tmpBytes = new byte[6];
            buf.readBytes(tmpBytes);
            super.put(JBT19506MsgParam.START_TIME, ByteUtils.bytesToHexString(tmpBytes));
            tmpBytes = new byte[6];
            buf.readBytes(tmpBytes);
            super.put(JBT19506MsgParam.END_TIME, ByteUtils.bytesToHexString(tmpBytes));

            List<Map<String, Object>> locationList = Lists.newArrayList();
            for (int i = 0; i < 2; i++) {
                Map<String, Object> locationData = Maps.newHashMap();
                long lng = buf.readUnsignedInt();
                if (lng == 0xFFFFFFFF || lng == 0x7FFFFFFF) {
                    locationData.put(JBT19506MsgParam.RECODER_LNG, 0);
                } else {
                    locationData.put(JBT19506MsgParam.RECODER_LNG, lng);
                }
                long lat = buf.readUnsignedInt();
                if (lng == 0xFFFFFFFF || lng == 0x7FFFFFFF) {
                    locationData.put(JBT19506MsgParam.RECODER_LAT, 0);
                } else {
                    locationData.put(JBT19506MsgParam.RECODER_LAT, lat);
                }
                int altitude = buf.readUnsignedShort();
                if (altitude == 0xFFFF) {
                    locationData.put(JBT19506MsgParam.RECODER_ALTITUDE, 0);
                } else {
                    locationData.put(JBT19506MsgParam.RECODER_ALTITUDE, altitude);
                }
                locationList.add(locationData);
            }
            super.put(JBT19506MsgParam.RECODER_LOCATIONS, locationList);
        }
    }
}

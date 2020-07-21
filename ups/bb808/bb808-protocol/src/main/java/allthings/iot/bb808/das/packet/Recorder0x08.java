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
 * @FileName :  Recorder0x08
 * @CreateDate :  2017/12/21
 * @Description : 采集指定的行驶速度记录
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
public class Recorder0x08 extends JBT19506Packet {

    public Recorder0x08() {
        super("08");
    }

    @Override
    public void unpack(byte[] bytes) {
        ByteBuf buf = Unpooled.wrappedBuffer(bytes);

        List<Map<String, Object>> minuteDatas = Lists.newArrayList();
        //每分钟的记录
        while (buf.readableBytes() > 0) {
            byte[] tmpByte = new byte[6];
            buf.readBytes(tmpByte);
            Map<String, Object> minuteData = Maps.newHashMap();
            minuteData.put(JBT19506MsgParam.MINUTE_TIME, ByteUtils.bytesToHexString(tmpByte));
            List<Map<String, Object>> secendDatas = Lists.newArrayList();
            //每秒钟的记录
            for (int i = 0; i < 60; i++) {
                Map<String, Object> secondData = Maps.newHashMap();
                short avgSpeed = buf.readUnsignedByte();
                if (avgSpeed == 0xFF) {
                    secondData.put(JBT19506MsgParam.RECODER_AVG_SPEED, -1);
                } else {
                    secondData.put(JBT19506MsgParam.RECODER_AVG_SPEED, avgSpeed);
                }
                secondData.put(JBT19506MsgParam.RECODER_SIGNAL, buf.readUnsignedByte());
                secendDatas.add(secondData);
            }
            minuteData.put(JBT19506MsgParam.RECODER_SEC_LIST, secendDatas);
            minuteDatas.add(minuteData);
        }
        super.put(JBT19506MsgParam.RECODER_MIN_LIST, minuteDatas);
    }
}

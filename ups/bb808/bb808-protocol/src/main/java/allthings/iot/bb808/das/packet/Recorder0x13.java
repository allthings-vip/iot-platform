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
 * @FileName :  Recorder0x13
 * @CreateDate :  2017/12/21
 * @Description : 采集指定的记录仪外部供电记录
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
public class Recorder0x13 extends JBT19506Packet {
    public Recorder0x13() {
        super("13");
    }

    @Override
    public void unpack(byte[] bytes) {
        ByteBuf buf = Unpooled.wrappedBuffer(bytes);

        byte[] tmpByte = null;
        List<Map<String, Object>> powerRecoders = Lists.newArrayList();
        while (buf.readableBytes() > 0) {
            Map<String, Object> powerRecoder = Maps.newHashMap();
            tmpByte = new byte[6];
            buf.readBytes(tmpByte);
            powerRecoder.put(JBT19506MsgParam.RECODER_TIME, ByteUtils.bytesToHexString(tmpByte));

            powerRecoder.put(JBT19506MsgParam.RECODER_EVENT_TYPE, buf.readByte());
            powerRecoders.add(powerRecoder);
        }
        super.put(JBT19506MsgParam.RECODER_POWER, powerRecoders);
    }
}

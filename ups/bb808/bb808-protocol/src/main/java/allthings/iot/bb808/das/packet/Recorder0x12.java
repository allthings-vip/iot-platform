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
 * @FileName :  Recorder0x12
 * @CreateDate :  2017/12/21
 * @Description : 采集指定的驾驶人身份记录
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) allthings-vip All Rights Reserved
 * *******************************************************************************************
 */
public class Recorder0x12 extends JBT19506Packet {
    public Recorder0x12() {
        super("12");
    }

    @Override
    public void unpack(byte[] bytes) {
        ByteBuf buf = Unpooled.wrappedBuffer(bytes);

        byte[] tmpByte = null;
        List<Map<String, Object>> driverRecoders = Lists.newArrayList();
        while (buf.readableBytes() > 0) {
            Map<String, Object> driverRecoder = Maps.newHashMap();
            tmpByte = new byte[6];
            buf.readBytes(tmpByte);
            driverRecoder.put(JBT19506MsgParam.RECODER_TIME, ByteUtils.bytesToHexString(tmpByte));

            tmpByte = new byte[18];
            buf.readBytes(tmpByte);
            driverRecoder.put(JBT19506MsgParam.DRIVER_LICENSE_NUMBER, ByteUtils.toString(tmpByte, 0, tmpByte.length));

            driverRecoder.put(JBT19506MsgParam.RECODER_EVENT_TYPE, buf.readByte());
            driverRecoders.add(driverRecoder);
        }
        super.put(JBT19506MsgParam.RECODER_DRIVER, driverRecoders);
    }
}

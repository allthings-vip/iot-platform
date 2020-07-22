package allthings.iot.bb808.das.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import allthings.iot.bb808.common.JBT19506MsgParam;
import allthings.iot.util.misc.ByteUtils;

/**
 * @author :  luhao
 * @FileName :  Recorder0x03
 * @CreateDate :  2017/12/21
 * @Description : 采集累计行驶里程
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) allthings-vip All Rights Reserved
 * *******************************************************************************************
 */
public class Recorder0x03 extends AbstractPacket {
    public Recorder0x03() {
        super("03");
    }

    @Override
    public void unpack(byte[] bytes) {
        ByteBuf buf = Unpooled.wrappedBuffer(bytes);

        byte[] tmpByte = null;
        tmpByte = new byte[6];
        buf.readBytes(tmpByte);
        super.put(JBT19506MsgParam.RECODER_TIME, ByteUtils.bytesToHexString(tmpByte));

        tmpByte = new byte[6];
        buf.readBytes(tmpByte);
        super.put(JBT19506MsgParam.INITIAL_INSTALLATION_TIME, ByteUtils.bytesToHexString(tmpByte));

        tmpByte = new byte[4];
        buf.readBytes(tmpByte);
        String initialMileage = ByteUtils.bytesToHexString(tmpByte);
        super.put(JBT19506MsgParam.INITIAL_MILEAGE, Integer.parseInt(initialMileage) * 0.1);

        tmpByte = new byte[4];
        buf.readBytes(tmpByte);
        String totalMileage = ByteUtils.bytesToHexString(tmpByte);
        super.put(JBT19506MsgParam.TOTAL_MILEAGE, Integer.parseInt(totalMileage) * 0.1);

    }
}

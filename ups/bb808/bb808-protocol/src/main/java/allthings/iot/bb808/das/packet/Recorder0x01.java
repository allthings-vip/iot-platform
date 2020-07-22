package allthings.iot.bb808.das.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import allthings.iot.bb808.common.JBT19506MsgParam;
import allthings.iot.util.misc.ByteUtils;

/**
 * @author :  luhao
 * @FileName :  Recorder0x01
 * @CreateDate :  2017/12/21
 * @Description : 采集当前驾驶人信息
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) allthings-vip All Rights Reserved
 * *******************************************************************************************
 */
public class Recorder0x01 extends AbstractPacket {
    public Recorder0x01() {
        super("01");
    }

    @Override
    public void unpack(byte[] bytes) {
        ByteBuf buf = Unpooled.wrappedBuffer(bytes);

        byte[] tmpByte = new byte[18];
        buf.readBytes(tmpByte);
        super.put(JBT19506MsgParam.DRIVER_LICENSE_NUMBER, ByteUtils.toString(tmpByte, 0, tmpByte.length));
    }
}

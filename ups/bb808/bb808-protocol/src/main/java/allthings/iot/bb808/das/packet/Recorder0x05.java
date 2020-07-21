package allthings.iot.bb808.das.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import allthings.iot.bb808.common.JBT19506MsgParam;
import allthings.iot.util.misc.ByteUtils;

/**
 * @author :  luhao
 * @FileName :  Recorder0x05
 * @CreateDate :  2017/12/21
 * @Description : 采集车辆信息
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
public class Recorder0x05 extends AbstractPacket {
    public Recorder0x05() {
        super("05");
    }

    @Override
    public void unpack(byte[] bytes) {
        ByteBuf buf = Unpooled.wrappedBuffer(bytes);

        byte[] tmpByte = null;

        tmpByte = new byte[17];
        buf.readBytes(tmpByte);
        super.put(JBT19506MsgParam.DEV_VIN, ByteUtils.toString(tmpByte, 0, tmpByte.length));

        tmpByte = new byte[9];
        buf.readBytes(tmpByte);
        super.put(JBT19506MsgParam.CAR_CODE, ByteUtils.toString(tmpByte, 0, tmpByte.length));
        //备用
        tmpByte = new byte[3];
        buf.readBytes(tmpByte);

        tmpByte = new byte[8];
        super.put(JBT19506MsgParam.CAR_CODE_TYPE, ByteUtils.toString(tmpByte, 0, tmpByte.length));


    }
}

package allthings.iot.bb808.das.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import allthings.iot.bb808.common.JBT19506MsgParam;
import allthings.iot.util.misc.ByteUtils;

/**
 * @author :  luhao
 * @FileName :  Recorder0x07
 * @CreateDate :  2017/12/21
 * @Description : 采集记录仪唯一性编号
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
public class Recorder0x07 extends AbstractPacket {
    public Recorder0x07() {
        super("07");
    }

    @Override
    public void unpack(byte[] bytes) {
        ByteBuf buf = Unpooled.wrappedBuffer(bytes);

        byte[] tmpByte = null;
        tmpByte = new byte[7];
        buf.readBytes(tmpByte);
        super.put(JBT19506MsgParam.CCC_AUTH_CODE, ByteUtils.toString(tmpByte, 0, tmpByte.length));

        tmpByte = new byte[16];
        buf.readBytes(tmpByte);
        super.put(JBT19506MsgParam.RECODER_MODEL, ByteUtils.toString(tmpByte, 0, tmpByte.length));

        tmpByte = new byte[3];
        buf.readBytes(tmpByte);
        super.put(JBT19506MsgParam.RECODER_PRODUCTION_DATE, ByteUtils.bytesToHexString(tmpByte));

        super.put(JBT19506MsgParam.RECODER_SERIAL_NO, buf.readUnsignedInt());
    }
}

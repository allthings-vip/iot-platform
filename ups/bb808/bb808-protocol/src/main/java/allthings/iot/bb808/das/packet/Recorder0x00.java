package allthings.iot.bb808.das.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import allthings.iot.bb808.common.JBT19506MsgParam;
import allthings.iot.util.misc.ByteUtils;

/**
 * @author :  luhao
 * @FileName :  Recorder0x00
 * @CreateDate :  2017/12/21
 * @Description : 采集记录仪执行标准版本
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
public class Recorder0x00 extends AbstractPacket {

    public Recorder0x00() {
        super("00");
    }

    @Override
    public void unpack(byte[] bytes) {
        ByteBuf buf = Unpooled.wrappedBuffer(bytes);

        super.getParamMap().put(JBT19506MsgParam.RECODER_STANDARD_YEAR, ByteUtils.bytesToHexString(new byte[]{buf
                .readByte()}));
        super.getParamMap().put(JBT19506MsgParam.RECODER_MODIFY_NUMBER, buf.readByte());
    }
}

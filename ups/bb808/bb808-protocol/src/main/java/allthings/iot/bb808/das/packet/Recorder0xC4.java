package allthings.iot.bb808.das.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import allthings.iot.bb808.common.JBT19506MsgParam;
import allthings.iot.util.misc.ByteUtils;

/**
 * @author :  luhao
 * @FileName :  Recorder0xC4
 * @CreateDate :  2017/12/21
 * @Description : 设置起始里程
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) allthings-vip All Rights Reserved
 * *******************************************************************************************
 */
public class Recorder0xC4 extends AbstractPacket {
    public Recorder0xC4() {
        super("C4");
    }

    @Override
    public byte[] pack() {
        ByteBuf buf = Unpooled.buffer();

        double millage = Double.valueOf(String.valueOf(super.get(JBT19506MsgParam.INITIAL_MILEAGE)));
        byte[] tmpByte = ByteUtils.hexStringToBytes(String.format("%08d", (int) (millage * 10)));
        buf.writeBytes(tmpByte);

        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        setMessageBody(bytes);
        return bytes;
    }


}

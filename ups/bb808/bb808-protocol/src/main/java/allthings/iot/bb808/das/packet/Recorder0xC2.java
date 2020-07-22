package allthings.iot.bb808.das.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import allthings.iot.bb808.common.JBT19506MsgParam;
import allthings.iot.das.util.DateUtil;

/**
 * @author :  luhao
 * @FileName :  Recorder0xC2
 * @CreateDate :  2017/12/21
 * @Description : 设置记录仪时间
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) allthings-vip All Rights Reserved
 * *******************************************************************************************
 */
public class Recorder0xC2 extends AbstractPacket {
    public Recorder0xC2() {
        super("C2");
    }

    @Override
    public byte[] pack() {
        ByteBuf buf = Unpooled.buffer();

        long time = super.get(JBT19506MsgParam.RECODER_TIME);
        buf.writeBytes(DateUtil.millisecond2BytesSix(time));

        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        setMessageBody(bytes);
        return bytes;
    }


}

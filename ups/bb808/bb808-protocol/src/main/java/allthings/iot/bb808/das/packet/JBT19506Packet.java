package allthings.iot.bb808.das.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import allthings.iot.bb808.common.JBT19506MsgParam;
import allthings.iot.das.util.DateUtil;

/**
 * @author :  luhao
 * @FileName :  JBT19506Packet
 * @CreateDate :  2017/12/21
 * @Description :
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
public abstract class JBT19506Packet extends AbstractPacket {
    public JBT19506Packet(String packetId) {
        super(packetId);
    }

    @Override
    public byte[] pack() {
        ByteBuf buf = Unpooled.buffer();

        long startTime = super.get(JBT19506MsgParam.START_TIME);
        buf.writeBytes(DateUtil.millisecond2BytesSix(startTime));

        long endTime = super.get(JBT19506MsgParam.END_TIME);
        buf.writeBytes(DateUtil.millisecond2BytesSix(endTime));

        buf.writeShort(super.get(JBT19506MsgParam.MAX_FRAME_COUNT));

        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        setMessageBody(bytes);
        return bytes;
    }
}

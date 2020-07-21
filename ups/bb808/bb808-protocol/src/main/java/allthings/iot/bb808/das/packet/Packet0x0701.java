package allthings.iot.bb808.das.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import allthings.iot.bb808.common.MsgParam;
import allthings.iot.util.misc.ByteUtils;

/**
 * @author :  luhao
 * @FileName :  Packet0x0701
 * @CreateDate :  2017/12/21
 * @Description : 电子运单上报
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
public class Packet0x0701 extends AbstractPacket {

    public Packet0x0701() {
        super("0701");
    }

    @Override
    public void unpack(byte[] bytes) {
        ByteBuf buf = Unpooled.wrappedBuffer(bytes);

        int length = buf.readInt();
        byte[] tmpByte = new byte[length];
        buf.readBytes(tmpByte);

        super.put(MsgParam.WAYBILL_CONTENT, ByteUtils.toString(tmpByte, 0, tmpByte.length));
    }
}

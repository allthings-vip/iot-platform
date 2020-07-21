package allthings.iot.bb808.das.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import allthings.iot.bb808.common.MsgParam;

/**
 * @author :  luhao
 * @FileName :  Packet0x0900
 * @CreateDate :  2017/12/21
 * @Description : 数据压缩上报
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
public class Packet0x0901 extends AbstractPacket {
    public Packet0x0901() {
        super("0901");
    }

    @Override
    public void unpack(byte[] bytes) {
        ByteBuf buf = Unpooled.wrappedBuffer(bytes);

        int length = buf.readInt();
        byte[] tmpBytes = new byte[length];
        buf.readBytes(tmpBytes);
        super.put(MsgParam.GZIP_DATA, tmpBytes);
    }
}

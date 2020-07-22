package allthings.iot.bb808.das.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import allthings.iot.bb808.common.MsgParam;
import allthings.iot.util.misc.ByteUtils;

/**
 * @author :  luhao
 * @FileName :  Packet0x0A00
 * @CreateDate :  2017/12/21
 * @Description :
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) allthings-vip All Rights Reserved
 * *******************************************************************************************
 */
public class Packet0x0A00 extends AbstractPacket {

    public Packet0x0A00(){
        super("0A00");
    }

    @Override
    public void unpack(byte[] bytes) {
        ByteBuf buf = Unpooled.wrappedBuffer(bytes);

        super.put(MsgParam.DEV_RSA_E, buf.readUnsignedInt());
        byte[] tmpBytes = new byte[128];
        buf.readBytes(tmpBytes);
        super.put(MsgParam.DEV_RSA_N, ByteUtils.bytesToHexString(tmpBytes));
    }
}

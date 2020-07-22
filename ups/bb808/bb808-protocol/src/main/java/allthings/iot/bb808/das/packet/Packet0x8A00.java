package allthings.iot.bb808.das.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import allthings.iot.bb808.common.MsgParam;

import java.math.BigInteger;

/**
 * @author :  luhao
 * @FileName :  Packet0x8A00
 * @CreateDate :  2017/12/21
 * @Description : 平台RSA 公钥
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) allthings-vip All Rights Reserved
 * *******************************************************************************************
 */
public class Packet0x8A00 extends AbstractPacket {
    public Packet0x8A00() {
        super("8A00");
    }

    @Override
    public byte[] pack() {
        ByteBuf buf = Unpooled.buffer();

        buf.writeInt(super.get(MsgParam.PLAT_RSA_E));
        String platRsaN = super.get(MsgParam.PLAT_RSA_N);
        BigInteger n = new BigInteger(platRsaN);
        byte[] tmpBytes = n.toByteArray();
        byte[] copyBytes = new byte[tmpBytes.length - 1];
        System.arraycopy(tmpBytes, 1, copyBytes, 0, copyBytes.length);
        buf.writeBytes(copyBytes);

        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        setMessageBody(bytes);
        return bytes;
    }
}

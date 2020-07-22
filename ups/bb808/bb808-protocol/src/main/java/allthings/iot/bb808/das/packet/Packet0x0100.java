package allthings.iot.bb808.das.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import allthings.iot.bb808.common.MsgParam;
import allthings.iot.util.misc.ByteUtils;

/**
 * @author :  luhao
 * @FileName :  Packet0x0100
 * @CreateDate :  2017/12/21
 * @Description : 终端注册
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) allthings-vip All Rights Reserved
 * *******************************************************************************************
 */
public class Packet0x0100 extends AbstractPacket {

    public Packet0x0100() {
        super("0100");
    }

    @Override
    public void unpack(byte[] bytes) {
        ByteBuf buf = Unpooled.wrappedBuffer(bytes);

        super.put(MsgParam.PROVINCE_ID, buf.readUnsignedShort());
        super.put(MsgParam.CITY_ID, buf.readUnsignedShort());

        byte[] tmpBytes = null;
        tmpBytes = new byte[5];
        buf.readBytes(tmpBytes, 0, tmpBytes.length);
        super.put(MsgParam.MAKER_ID, ByteUtils.toString(tmpBytes, 0, tmpBytes.length));
        //11版
        if (buf.readableBytes() < 29) {
            tmpBytes = new byte[8];
            buf.readBytes(tmpBytes, 0, tmpBytes.length);
            super.put(MsgParam.DEV_TYPE, ByteUtils.toString(tmpBytes, 0, tmpBytes.length));
        } else {      //11版补充协议以及13版
            tmpBytes = new byte[20];
            buf.readBytes(tmpBytes, 0, tmpBytes.length);
            super.put(MsgParam.DEV_TYPE, ByteUtils.toString(tmpBytes, 0, tmpBytes.length));
        }
        tmpBytes = new byte[7];
        buf.readBytes(tmpBytes, 0, tmpBytes.length);
        super.put(MsgParam.DEV_CODE, ByteUtils.toString(tmpBytes, 0, tmpBytes.length));

        short carCodeColor = buf.readUnsignedByte();
        super.put(MsgParam.CAR_CODE_COLOR, carCodeColor);
        tmpBytes = new byte[buf.readableBytes()];
        buf.readBytes(tmpBytes, 0, tmpBytes.length);
        if (carCodeColor == 0) {
            super.put(MsgParam.VIN_CODE, ByteUtils.toString(tmpBytes, 0, tmpBytes.length));
        } else {
            super.put(MsgParam.CAR_CODE, ByteUtils.toString(tmpBytes, 0, tmpBytes.length));
        }
    }
}

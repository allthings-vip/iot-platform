package allthings.iot.bb808.das.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import allthings.iot.bb808.common.JBT19506MsgParam;
import allthings.iot.util.misc.ByteUtils;

/**
 * @author :  luhao
 * @FileName :  Recorder0x82
 * @CreateDate :  2017/12/21
 * @Description : 设置车辆信息
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
public class Recorder0x82 extends AbstractPacket {

    public Recorder0x82() {
        super("82");
    }

    @Override
    public byte[] pack() {
        ByteBuf buf = Unpooled.buffer();
        String devVin = super.get(JBT19506MsgParam.DEV_VIN);
        byte[] vin = devVin.getBytes(ByteUtils.CHARSET_UTF8);
        buf.writeBytes(vin);

        String carCode = super.get(JBT19506MsgParam.CAR_CODE);
        if (carCode != null && carCode.length() > 0) {
            byte[] tmpBytes = carCode.getBytes(ByteUtils.CHARSET_UTF8);
            if (tmpBytes.length > 9) {
                byte[] bytes = new byte[9];
                System.arraycopy(tmpBytes, 0, bytes, 0, 9);
                buf.writeBytes(bytes);
            } else {
                buf.writeBytes(tmpBytes);
                int i = 9 - tmpBytes.length;
                while (i > 0) {
                    buf.writeByte(0x00);
                    i--;
                }
            }
        }

        byte[] tmp;
        tmp = new byte[]{0x00, 0x00, 0x00};
        buf.writeBytes(tmp);

        String carCodeType = super.get(JBT19506MsgParam.CAR_CODE_TYPE);
        if (carCodeType != null && carCodeType.length() > 0) {
            byte[] tmpBytes = carCodeType.getBytes(ByteUtils.CHARSET_UTF8);
            if (tmpBytes.length > 8) {
                byte[] bytes = new byte[8];
                System.arraycopy(tmpBytes, 0, bytes, 0, 8);
                buf.writeBytes(bytes);
            } else {
                buf.writeBytes(tmpBytes);
                int i = 8 - tmpBytes.length;
                while (i > 0) {
                    buf.writeByte(0x00);
                    i--;
                }
            }
        }
        tmp = new byte[]{0x00, 0x00, 0x00, 0x00};
        buf.writeBytes(tmp);

        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        setMessageBody(bytes);
        return bytes;
    }

}

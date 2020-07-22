package allthings.iot.bb808.das.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import allthings.iot.bb808.common.JBT19506MsgParam;
import allthings.iot.util.misc.ByteUtils;

import java.util.List;

/**
 * @author :  luhao
 * @FileName :  Recorder0x84
 * @CreateDate :  2017/12/21
 * @Description : 设置状态量配置信息
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) allthings-vip All Rights Reserved
 * *******************************************************************************************
 */
public class Recorder0x84 extends AbstractPacket {
    public Recorder0x84() {
        super("84");
    }

    @Override
    public byte[] pack() {
        ByteBuf buf = Unpooled.buffer();

        List<String> signals = super.get(JBT19506MsgParam.STATUS_SIGNAL_LIST);
        byte[] tmpByte = null;
        for (int i = 0; i < 8; i++) {
            if (i < signals.size()) {
                String signal = signals.get(i);
                tmpByte = signal.getBytes(ByteUtils.CHARSET_UTF8);
                if (tmpByte.length > 10) {
                    byte[] bytes = new byte[9];
                    System.arraycopy(tmpByte, 0, bytes, 0, 9);
                    buf.writeBytes(bytes);
                } else {
                    buf.writeBytes(tmpByte);
                    int j = 10 - tmpByte.length;
                    while (j > 0) {
                        buf.writeByte(0x00);
                        j--;
                    }
                }
            } else {
                tmpByte = new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
                buf.writeBytes(tmpByte);
            }
        }

        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        setMessageBody(bytes);
        return bytes;
    }


}

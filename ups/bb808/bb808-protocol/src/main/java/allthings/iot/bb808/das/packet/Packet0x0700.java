package allthings.iot.bb808.das.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import allthings.iot.bb808.common.MsgParam;
import allthings.iot.bb808.das.util.CRCUtil;
import allthings.iot.util.misc.ByteUtils;

import java.util.Arrays;

/**
 * @author :  luhao
 * @FileName :  Packet0x0700
 * @CreateDate :  2017/12/21
 * @Description : 行驶记录仪数据上传
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) allthings-vip All Rights Reserved
 * *******************************************************************************************
 */
public class Packet0x0700 extends AbstractPacket {

    private static final Logger LOGGER = LoggerFactory.getLogger(Packet0x0700.class);
    private static final byte[] RECODER_START = new byte[]{0x55, 0x7A};

    public Packet0x0700() {
        super("0700");
    }

    @Override
    public void unpack(byte[] bytes) {
        ByteBuf buf = Unpooled.wrappedBuffer(bytes);

        super.put(MsgParam.ACK_RUNNING_NO, buf.readUnsignedShort());

        byte commandWord = buf.readByte();
        super.put(MsgParam.COMMAND_WORD, ByteUtils.bytesToHexString(new byte[]{commandWord}));

        String msgCode = ByteUtils.bytesToHexString(new byte[]{commandWord});

        AbstractPacket bPacket = null;
        String packageName = AbstractPacket.class.getPackage().getName();
        String className = packageName + ".Recoder0x" + msgCode;
        try {
            bPacket = (AbstractPacket) Class.forName(className).newInstance();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        if (bPacket != null) {
            byte[] crcBytes = new byte[buf.readableBytes() - 1];
            buf.getBytes(buf.readerIndex(), crcBytes);

            byte[] tmpByte = null;
            tmpByte = new byte[2];

            buf.readBytes(tmpByte);
            if (Arrays.equals(tmpByte, RECODER_START)) {
                byte command = buf.readByte();
                if (command == 0xFA || command == 0xFB) {
                    super.put(MsgParam.RECODER_FLAG, ByteUtils.bytesToHexString(new byte[]{command}));
                } else if (command == commandWord) {
                    super.put(MsgParam.RECODER_FLAG, "00");
                    int dataLength = buf.readUnsignedShort();
                    buf.readByte(); //保留字段
                    tmpByte = new byte[dataLength];
                    buf.readBytes(tmpByte);
                    bPacket.setMessageBody(tmpByte);
                    byte crc = buf.readByte();
                    if (crc == CRCUtil.calGBT19056CRC(crcBytes)) {  //CRC校验
                        try {
                            bPacket.unpack(tmpByte);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        super.getParamMap().putAll(bPacket.getParamMap());
                    } else {
                        super.put(MsgParam.RECODER_FLAG, "03"); //CRC校验失败
                        LOGGER.error("recoder crc wrong");
                    }
                } else {
                    super.put(MsgParam.RECODER_FLAG, "01"); //命令
                    LOGGER.error("recoder commandWord not match");
                }
            } else {
                super.put(MsgParam.RECODER_FLAG, "02"); //包头不匹配
                LOGGER.error("recoder start not match");
            }
        }
    }
}

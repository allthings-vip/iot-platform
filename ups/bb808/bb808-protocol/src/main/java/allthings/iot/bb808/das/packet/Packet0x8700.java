package allthings.iot.bb808.das.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import allthings.iot.bb808.common.MsgParam;
import allthings.iot.util.misc.ByteUtils;

/**
 * @author :  luhao
 * @FileName :  Packet0x8700
 * @CreateDate :  2017/12/21
 * @Description : 行驶记录仪数据采集命令
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) allthings-vip All Rights Reserved
 * *******************************************************************************************
 */
public class Packet0x8700 extends AbstractPacket {
    private static final Logger logger = LoggerFactory.getLogger(Packet0x8700.class);
    public Packet0x8700() {
        super("8700");
    }

    @Override
    public byte[] pack() {
        ByteBuf buf = Unpooled.buffer();

        String commandWord = super.get(MsgParam.COMMAND_WORD);
        buf.writeBytes(ByteUtils.hexStringToBytes(commandWord));

        AbstractPacket bPacket = null;
        String packageName = Recorder0x01.class.getPackage().getName();
        String className = packageName + ".Recorder0x" + commandWord;
        try {
            bPacket = (AbstractPacket) Class.forName(className).newInstance();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        if(bPacket != null) {
            bPacket.setParamMap(getParamMap());
            try {
                bPacket.pack();
            } catch (Exception e) {
                e.printStackTrace();
                logger.error(e.getMessage(), e);
            }
            if(bPacket.getMessageBody() != null && bPacket.getMessageBody().length > 0){
                buf.writeBytes(bPacket.getMessageBody());
            }
        }

        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        setMessageBody(bytes);
        return bytes;
    }

}

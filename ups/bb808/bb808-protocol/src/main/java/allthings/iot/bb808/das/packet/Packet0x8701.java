package allthings.iot.bb808.das.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import allthings.iot.bb808.common.MsgParam;
import allthings.iot.util.misc.ByteUtils;

/**
 * @author :  luhao
 * @FileName :  Packet0x8701
 * @CreateDate :  2017/12/21
 * @Description : 行驶记录参数下传命令
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) allthings-vip All Rights Reserved
 * *******************************************************************************************
 */
public class Packet0x8701 extends AbstractPacket {
    public Packet0x8701() {
        super("8701");
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
        }

        if(bPacket != null) {
            bPacket.setParamMap(getParamMap());
            try {
                bPacket.pack();
            } catch (Exception e) {
                e.printStackTrace();
            }
            buf.writeBytes(bPacket.getMessageBody());
        }

        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        setMessageBody(bytes);
        return bytes;
    }


}

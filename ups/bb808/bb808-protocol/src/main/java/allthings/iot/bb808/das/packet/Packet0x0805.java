package allthings.iot.bb808.das.packet;

import com.google.common.collect.Lists;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import allthings.iot.bb808.common.MsgParam;

import java.util.List;

/**
 * @author :  luhao
 * @FileName :  Packet0x0805
 * @CreateDate :  2017/12/21
 * @Description : 摄像头立即拍摄命令应答
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) allthings-vip All Rights Reserved
 * *******************************************************************************************
 */
public class Packet0x0805 extends AbstractPacket {
    public Packet0x0805() {
        super("0805");
    }

    @Override
    public void unpack(byte[] bytes) {
        ByteBuf buf = Unpooled.wrappedBuffer(bytes);

        super.put(MsgParam.ACK_RUNNING_NO, buf.readUnsignedShort());

        byte result = buf.readByte();

        super.put(MsgParam.TAKE_PHONE_RESULT, result);
        if (result == 0) {
            int count = buf.readUnsignedShort();
            List<String> multimediaIdList = Lists.newArrayList();
            while (count > 0) {
                long id = buf.readUnsignedInt();
                multimediaIdList.add(String.valueOf(id));
                count--;
            }
            super.put(MsgParam.MULTIMEDIA_ID_LIST, multimediaIdList);
        }
    }
}

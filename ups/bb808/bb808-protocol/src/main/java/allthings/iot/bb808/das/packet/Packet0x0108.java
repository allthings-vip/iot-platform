package allthings.iot.bb808.das.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import allthings.iot.bb808.common.MsgParam;

/**
 * @author :  luhao
 * @FileName :  Packet0x0108
 * @CreateDate :  2017/12/21
 * @Description : 终端升级结果通知
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
public class Packet0x0108 extends AbstractPacket {
    public Packet0x0108() {
        super("0108");
    }

    @Override
    public void unpack(byte[] bytes) {
        ByteBuf buf = Unpooled.wrappedBuffer(bytes);

        super.put(MsgParam.UPGRADE_TYPE,buf.readByte());
        super.put(MsgParam.UPGRADE_RESULT,buf.readByte());
    }
}

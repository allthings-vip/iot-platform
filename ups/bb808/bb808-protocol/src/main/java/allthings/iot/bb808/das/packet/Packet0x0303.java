package allthings.iot.bb808.das.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import allthings.iot.bb808.common.MsgParam;

/**
 * @author :  luhao
 * @FileName :  Packet0x0303
 * @CreateDate :  2017/12/21
 * @Description : 信息点播、取消
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
public class Packet0x0303 extends AbstractPacket {

    public Packet0x0303(){
        super("8303");
    }

    @Override
    public void unpack(byte[] bytes) {
        ByteBuf buf = Unpooled.wrappedBuffer(bytes);
        super.put(MsgParam.INFO_TYPE,buf.readUnsignedByte());
        super.put(MsgParam.INFO_ON_OFF,buf.readUnsignedByte());
    }
}

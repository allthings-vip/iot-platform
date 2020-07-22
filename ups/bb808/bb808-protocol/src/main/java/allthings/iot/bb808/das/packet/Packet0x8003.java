package allthings.iot.bb808.das.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import allthings.iot.bb808.common.MsgParam;

import java.util.List;

/**
 * @author :  luhao
 * @FileName :  Packet0x8003
 * @CreateDate :  2017/12/21
 * @Description : 补传分包请求
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) allthings-vip All Rights Reserved
 * *******************************************************************************************
 */
public class Packet0x8003 extends AbstractPacket {
    public Packet0x8003() {
        super("0x8003");
    }

    @Override
    public byte[] pack() {
        ByteBuf buf = Unpooled.buffer();

        buf.writeInt(super.get(MsgParam.ORIGIN_RUNNING_NO));
        List<Integer> idLists = super.get(MsgParam.RESEND_ID_LIST);
        buf.writeByte(idLists.size());
        for(Integer id : idLists){
            buf.writeShort(id);
        }

        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        return bytes;
    }
}

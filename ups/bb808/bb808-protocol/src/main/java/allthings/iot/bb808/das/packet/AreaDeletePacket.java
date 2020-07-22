package allthings.iot.bb808.das.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import allthings.iot.bb808.common.MsgParam;

import java.util.List;

/**
 * @author :  luhao
 * @FileName :  AreaDeletePacket
 * @CreateDate :  2017/12/21
 * @Description :
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) allthings-vip All Rights Reserved
 * *******************************************************************************************
 */
public abstract class AreaDeletePacket extends AbstractPacket {

    public AreaDeletePacket(String packetId) {
        super(packetId);
    }

    @Override
    public byte[] pack() {
        ByteBuf buf = Unpooled.buffer();

        List<Number> ids = super.get(MsgParam.DELETE_AREA_LIST);
        if(ids == null){
            buf.writeByte(0);
        }else{
            buf.writeByte(ids.size());
            for(Number id : ids){
                buf.writeInt(id.intValue());
            }
        }

        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        setMessageBody(bytes);
        return bytes;
    }
}

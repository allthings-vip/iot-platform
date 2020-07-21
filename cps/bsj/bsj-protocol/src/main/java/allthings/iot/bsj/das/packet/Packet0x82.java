package allthings.iot.bsj.das.packet;

import io.netty.buffer.ByteBuf;

/**
 * @author :  luhao
 * @FileName :  Packet0x82
 * @CreateDate :  2018/1/8
 * @Description :  (0x82)特殊报警数据
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
public class Packet0x82 extends PacketPosition2 {
    public Packet0x82() {
        super("82");
    }

    @Override
    public void unpack(ByteBuf buf) throws Exception {
        
    }
}

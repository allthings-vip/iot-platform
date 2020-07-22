package allthings.iot.bsj.das.packet;

import io.netty.buffer.ByteBuf;

/**
 * @author :  luhao
 * @FileName :  Packet0x8E
 * @CreateDate :  2018/1/8
 * @Description : (0x8E)盲区补传数据
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) allthings-vip All Rights Reserved
 * *******************************************************************************************
 */
public class Packet0x8E extends PacketPosition2 {
    public Packet0x8E() {
        super("8E");
    }

    @Override
    public void unpack(ByteBuf buf) throws Exception {

    }
}

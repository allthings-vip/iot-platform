package allthings.iot.bsj.das.packet;

import allthings.iot.bsj.common.PoMsgParam;
import allthings.iot.util.misc.ByteUtils;

import java.nio.ByteBuffer;

/**
 * @author :  luhao
 * @FileName :  Packet0x7A
 * @CreateDate :  2018/1/8
 * @Description : (0x7A)TCP心跳时间隔
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
public class Packet0x7A extends PacketSetting {
    public Packet0x7A() {
        super("7A", 1);
    }


    @Override
    public void pack(ByteBuffer byteBuffer) {
        byteBuffer.put(ByteUtils.toByte(get(PoMsgParam.ATTR_SETTING_INTERVAL)));
    }
}

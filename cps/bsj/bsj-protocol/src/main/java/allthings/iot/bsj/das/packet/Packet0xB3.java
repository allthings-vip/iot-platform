package allthings.iot.bsj.das.packet;

import allthings.iot.bsj.common.ExMsgParam;
import allthings.iot.util.misc.ByteUtils;

/**
 * @author :  luhao
 * @FileName :  Packet0xB3
 * @CreateDate :  2018/1/8
 * @Description : (0xB3）上发版本或者状态信息
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) allthings-vip All Rights Reserved
 * *******************************************************************************************
 */
public class Packet0xB3 extends AbstractPacket {
    public Packet0xB3() {
        super("B3");
    }

    @Override
    public void unpack(byte[] content) throws Exception {
        String contentStr = new String(content, ByteUtils.CHARSET_NAME);

        put(ExMsgParam.BBZT, contentStr);
    }
}

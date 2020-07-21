package allthings.iot.bsj.das.packet;

import allthings.iot.bsj.common.ExMsgParam;
import allthings.iot.util.misc.ByteUtils;

/**
 * @author :  luhao
 * @FileName :  Packet0x84
 * @CreateDate :  2018/1/8
 * @Description : (0x84)上发文本或调度信息
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
public class Packet0x84 extends AbstractPacket {
    public Packet0x84() {
        super("84");
    }

    @Override
    public void unpack(byte[] content) throws Exception {
        String contentStr = new String(content, ByteUtils.CHARSET_NAME);

        put(ExMsgParam.WB, contentStr);
    }
}

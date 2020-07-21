package allthings.iot.bb808.das.packet;

import allthings.iot.bb808.common.MsgParam;
import allthings.iot.util.misc.ByteUtils;

/**
 * @author :  luhao
 * @FileName :  Packet0x0102
 * @CreateDate :  2017/12/21
 * @Description : 终端鉴权
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
public class Packet0x0102 extends AbstractPacket {

    public Packet0x0102() {
        super("0102");
    }

    @Override
    public void unpack(byte[] bytes) {
        String jianQuan = ByteUtils.toString(bytes, 0, bytes.length);
        super.put(MsgParam.JIAN_QUAN, jianQuan);
    }
}

package allthings.iot.bsj.das.record;

import allthings.iot.bsj.common.ExMsgParam;
import allthings.iot.util.misc.ByteUtils;

/**
 * @author :  luhao
 * @FileName :  Record0x0022
 * @CreateDate :  2018/1/5
 * @Description : IC卡数据
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
public class Record0x0022 extends AbstractRecord {
    public Record0x0022() {
        super("0x0022");
    }

    @Override
    public void unpack(byte[] content) throws Exception {
        put(ExMsgParam.ICCARDNO, ByteUtils.toString(content, 0, content.length));
    }
}

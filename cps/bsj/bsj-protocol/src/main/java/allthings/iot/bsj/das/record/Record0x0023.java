package allthings.iot.bsj.das.record;

import allthings.iot.bsj.common.ExMsgParam;
import allthings.iot.util.misc.ByteUtils;

/**
 * @author :  luhao
 * @FileName :  Record0x0023
 * @CreateDate :  2018/1/5
 * @Description : 带通信的油位传感器
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) allthings-vip All Rights Reserved
 * *******************************************************************************************
 */
public class Record0x0023 extends AbstractRecord {
    public Record0x0023() {
        super("0x0023");
    }

    @Override
    public void unpack(byte[] content) throws Exception {
        String yw = ByteUtils.toString(content, 0, content.length);
        put(ExMsgParam.YW, Double.parseDouble(yw));
    }
}

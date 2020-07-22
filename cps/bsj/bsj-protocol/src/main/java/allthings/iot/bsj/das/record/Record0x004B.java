package allthings.iot.bsj.das.record;

import allthings.iot.constant.gps.GpsMsgParam;
import allthings.iot.constant.gps.OilType;
import allthings.iot.util.misc.ByteUtils;

/**
 * @author :  luhao
 * @FileName :  Record0x004B
 * @CreateDate :  2018/1/10
 * @Description : 久通油感数据
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) allthings-vip All Rights Reserved
 * *******************************************************************************************
 */
public class Record0x004B extends AbstractRecord {
    public Record0x004B() {
        super("0x004B");
    }

    @Override
    public void unpack(byte[] content) throws Exception {
        String yls = ByteUtils.toString(content, 9, 6);

        put(GpsMsgParam.ATTR_OIL_TYPE, OilType.ZSL.name());
        put(GpsMsgParam.ATTR_OIL, Double.parseDouble(yls) / 100);
        put(GpsMsgParam.ATTR_OIL_UNIT, "L");
    }
}

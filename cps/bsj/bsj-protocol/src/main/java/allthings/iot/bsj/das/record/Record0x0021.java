package allthings.iot.bsj.das.record;

/**
 * @author :  luhao
 * @FileName :  Record0x0021
 * @CreateDate :  2018/1/5
 * @Description : CAN总线数据段
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) allthings-vip All Rights Reserved
 * *******************************************************************************************
 */
public class Record0x0021 extends AbstractRecord {
    public Record0x0021() {
        super("0x0021");
    }

    @Override
    public void unpack(byte[] content) throws Exception {
        //01到08是节点181数据[3020] ，09到10是节点281的数据[5020] 11到18是381的数据[7020]，19到20是节点481的数据[9020]
        //只有在80和80指令中带有
    }
}

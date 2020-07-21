package allthings.iot.bsj.das.record;

/**
 * @author :  luhao
 * @FileName :  Record0x0024
 * @CreateDate :  2018/1/5
 * @Description : GSM小区信息
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
public class Record0x0024 extends AbstractRecord {
    public Record0x0024() {
        super("0x0024");
    }

    @Override
    public void unpack(byte[] content) throws Exception {
        //String gsmContent = ByteUtils.toString(content, 0, content.length);
    }
}

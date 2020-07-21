package allthings.iot.bsj.das.record;

import com.google.common.base.Joiner;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import allthings.iot.bsj.common.ExMsgParam;

/**
 * @author :  luhao
 * @FileName :  Record0x0001
 * @CreateDate :  2018/1/4
 * @Description : 油耗数据段
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
public class Record0x0001 extends AbstractRecord {
    public Record0x0001() {
        super("0x0001");
    }

    @Override
    public void unpack(byte[] content) {
        ByteBuf buf = Unpooled.wrappedBuffer(content);
        int oilInt = buf.readUnsignedShort();
        short oilDecimal = buf.readUnsignedByte();

        String oil = Joiner.on(".").join(oilInt, oilDecimal);

        put(ExMsgParam.YH, oil);
    }
}

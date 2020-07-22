package allthings.iot.bsj.das.record;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import allthings.iot.bsj.common.ExMsgParam;
import allthings.iot.util.misc.ByteUtils;

/**
 * @author :  luhao
 * @FileName :  Record0x0003
 * @CreateDate :  2018/1/4
 * @Description : 温控数据段
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) allthings-vip All Rights Reserved
 * *******************************************************************************************
 */
public class Record0x0003 extends AbstractRecord {

    public Record0x0003() {
        super("0x0003");
    }

    @Override
    public void unpack(byte[] content) {
        ByteBuf buf = Unpooled.wrappedBuffer(content);

        //第1路
        byte[] temperature1 = new byte[2];
        buf.readBytes(temperature1);
        put(ExMsgParam.WD1, ByteUtils.toInt(temperature1[0]));

        //第2路
        byte[] temperature2 = new byte[2];
        buf.readBytes(temperature2);
        put(ExMsgParam.WD2, ByteUtils.toInt(temperature2[0]));

        //第3路
        byte[] temperature3 = new byte[2];
        buf.readBytes(temperature3);
        put(ExMsgParam.WD3, ByteUtils.toInt(temperature3[0]));

        //第4路
        byte[] temperature4 = new byte[2];
        buf.readBytes(temperature4);
        put(ExMsgParam.WD4, ByteUtils.toInt(temperature4[0]));
    }
}

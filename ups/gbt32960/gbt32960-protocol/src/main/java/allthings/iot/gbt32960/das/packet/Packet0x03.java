package allthings.iot.gbt32960.das.packet;

import java.util.Map;

import io.netty.buffer.ByteBuf;
import allthings.iot.gbt32960.das.record.AbstractRecord;
import allthings.iot.gbt32960.das.record.RecordCustom;
import allthings.iot.util.misc.ByteUtils;
import allthings.iot.util.misc.ReflectUtils;

/**
 * @author : luhao
 * @FileName : Packet0x03
 * @CreateDate : 2018/1/31
 * @Description :补发信息上报
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) allthings-vip All Rights Reserved
 * *******************************************************************************************
 */
public class Packet0x03 extends AbstractPacket {
    public Packet0x03() {
        super("0x03");
    }


    @Override
    public void unpack(ByteBuf byteBuf) throws Exception {
        while (byteBuf.readableBytes() > 0) {
            // 信息类型
            short infoType = byteBuf.readUnsignedByte();
            AbstractRecord record = null;
            if (infoType >= (short) 0x80 && infoType <= (short) 0xFE) {
                record = new RecordCustom();
            } else {
                record = ReflectUtils.getInstance(ByteUtils.bytesToHexString(new byte[] { ByteUtils.toByte(infoType) }),
                                                  "Record0x", AbstractRecord.class);
            }
            // 解码
            record.unpack(byteBuf);
            // 解码后的参数
            this.getParamMap().putAll(record.getParamMap());
        }
    }

    /*
     * (non-Javadoc)
     * @see allthings.iot.gbt32960.das.packet.AbstractPacket#packBody(java.util.Map)
     */
    @Override
    protected byte[] packBody(Map<String, Object> params) {
        // TODO Auto-generated method stub
        return new byte[0];
    }
}

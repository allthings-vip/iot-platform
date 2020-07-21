package allthings.iot.bb808.das.packet;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import allthings.iot.bb808.common.JBT19506MsgParam;
import allthings.iot.util.misc.ByteUtils;

import java.util.List;
import java.util.Map;


/**
 * @author :  luhao
 * @FileName :  Recorder0x06
 * @CreateDate :  2017/12/21
 * @Description : 采集记录仪状态信号配置信息
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
public class Recorder0x06 extends AbstractPacket {
    public Recorder0x06() {
        super("06");
    }

    @Override
    public void unpack(byte[] bytes) {
        ByteBuf buf = Unpooled.wrappedBuffer(bytes);

        byte[] tmpByte = new byte[6];
        buf.readBytes(tmpByte);
        super.put(JBT19506MsgParam.RECODER_TIME, ByteUtils.bytesToHexString(tmpByte));

        short count = buf.readUnsignedByte();

        List<Map<String, Object>> list = Lists.newArrayList();
        while (buf.readableBytes() > 0) {
            Map<String, Object> map = Maps.newHashMap();
            for (int i = 0; i < 8; i++) {
                tmpByte = new byte[10];
                buf.readBytes(tmpByte);
                map.put(JBT19506MsgParam.STATUS_SIGNAL + i, ByteUtils.toString(tmpByte, 0, tmpByte.length));
            }
            list.add(map);
        }
        super.put(JBT19506MsgParam.CONFIG_LIST, list);
    }
}

package allthings.iot.bb808.das.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import allthings.iot.bb808.common.MsgParam;
import allthings.iot.util.misc.ByteUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author :  luhao
 * @FileName :  Packet0x8600
 * @CreateDate :  2017/12/21
 * @Description : 设置圆形区域
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) allthings-vip All Rights Reserved
 * *******************************************************************************************
 */
public class Packet0x8600 extends AbstractPacket {

    private static final int ONE_MILLION = 1000000;

    public Packet0x8600() {
        super("8600");
    }

    @Override
    public byte[] pack() {
        ByteBuf buf = Unpooled.buffer();

        buf.writeByte(super.get(MsgParam.SET_ATTR));
        List<Map<String, Object>> areas = super.get(MsgParam.AREA_LIST);
        buf.writeByte(areas.size());

        for (Map<String, Object> area : areas) {
            buf.writeInt(((Number) area.get(MsgParam.AREA_ID)).intValue());
            int areaAttr = (int) area.get(MsgParam.AREA_ATTR);
            buf.writeShort(areaAttr);

            BigDecimal lat = new BigDecimal(area.get(MsgParam.CENTER_LAT).toString());
            BigDecimal lng = new BigDecimal(area.get(MsgParam.CENTER_LNG).toString());

            buf.writeInt(lat.multiply(new BigDecimal(Double.toString(ONE_MILLION))).intValue());
            buf.writeInt(lng.multiply(new BigDecimal(Double.toString(ONE_MILLION))).intValue());

            buf.writeInt((int) area.get(MsgParam.RADIUS));
            if ((areaAttr & 0x01) > 0) {
                buf.writeBytes(ByteUtils.hexStringToBytes((String) area.get(MsgParam.START_TIME)));
                buf.writeBytes(ByteUtils.hexStringToBytes((String) area.get(MsgParam.END_TIME)));
            }

            if ((areaAttr & 0x02) > 0) {
                buf.writeShort((int) area.get(MsgParam.MAX_SPEED));
                buf.writeByte((int) area.get(MsgParam.OVERSPEED_TIME));
            }
        }

        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        setMessageBody(bytes);
        return bytes;
    }


}

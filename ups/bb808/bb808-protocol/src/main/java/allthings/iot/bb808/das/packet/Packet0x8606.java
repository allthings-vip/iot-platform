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
 * @FileName :  Packet0x8606
 * @CreateDate :  2017/12/21
 * @Description : 设置路线
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) allthings-vip All Rights Reserved
 * *******************************************************************************************
 */
public class Packet0x8606 extends AbstractPacket {

    private static final int ONE_MILLION = 1000000;

    public Packet0x8606() {
        super("8606");
    }

    @Override
    public byte[] pack() {
        ByteBuf buf = Unpooled.buffer();

        buf.writeInt(((Number) super.get(MsgParam.ROUTE_ID)).intValue());
        int areaAttr = super.get(MsgParam.ROUTE_ATTR);
        buf.writeShort(areaAttr);

        if ((areaAttr & 0x01) > 0) {
            buf.writeBytes(ByteUtils.hexStringToBytes(super.get(MsgParam.START_TIME)));
            buf.writeBytes(ByteUtils.hexStringToBytes(super.get(MsgParam.END_TIME)));
        }

        List<Map<String, Object>> points = super.get(MsgParam.POINT_LIST);
        buf.writeShort(points.size());
        for (Map<String, Object> point : points) {
            buf.writeInt(((Number) point.get(MsgParam.POINT_ID)).intValue());
            buf.writeInt(((Number) point.get(MsgParam.ROAD_ID)).intValue());

            BigDecimal lat = new BigDecimal(point.get(MsgParam.POINT_LAT).toString());
            BigDecimal lng = new BigDecimal(point.get(MsgParam.POINT_LNG).toString());

            buf.writeInt(lat.multiply(new BigDecimal(Double.toString(ONE_MILLION))).intValue());
            buf.writeInt(lng.multiply(new BigDecimal(Double.toString(ONE_MILLION))).intValue());

            buf.writeByte((int) point.get(MsgParam.ROAD_WIDTH));
            int roadAttr = (int) point.get(MsgParam.ROAD_ATTR);
            buf.writeByte(roadAttr);
            if ((roadAttr & 0x01) > 0) {
                buf.writeShort((int) point.get(MsgParam.ROAD_MAX_TIME));
                buf.writeShort((int) point.get(MsgParam.ROAD_MIN_TIME));
            }

            if ((roadAttr & 0x02) > 0) {
                buf.writeShort((int) point.get(MsgParam.MAX_SPEED));
                buf.writeByte((int) point.get(MsgParam.OVERSPEED_TIME));
            }
        }
        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        setMessageBody(bytes);
        return bytes;
    }


}

package allthings.iot.bsj.das.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import allthings.iot.constant.gps.GpsMsgParam;
import allthings.iot.das.util.DateUtil;
import allthings.iot.util.gps.util.GpsUtil;
import allthings.iot.util.misc.ByteUtils;

/**
 * @author :  luhao
 * @FileName :  PacketPosition2
 * @CreateDate :  2018/1/8
 * @Description : 位置数据二解析
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) allthings-vip All Rights Reserved
 * *******************************************************************************************
 */
public abstract class PacketPosition2 extends AbstractPacket {
    public PacketPosition2(String packetId) {
        super(packetId);
    }

    @Override
    public void unpack(byte[] content) throws Exception {
        ByteBuf buf = Unpooled.wrappedBuffer(content);

        byte[] gpsDatetime = new byte[6];
        buf.readBytes(gpsDatetime);
        put(allthings.iot.constant.gps.GpsMsgParam.ATTR_GPS_DATETIME, DateUtil.parse(ByteUtils.cbcd2string(gpsDatetime),
                "yyMMddHHmmss"));

        byte[] latitudeBytes = new byte[4];
        buf.readBytes(latitudeBytes);
        put(allthings.iot.constant.gps.GpsMsgParam.ATTR_GPS_LATITUDE, GpsUtil.transformDegree2Decimal(ByteUtils
                .cbcd2string(latitudeBytes)));

        byte[] longitudeBytes = new byte[4];
        buf.readBytes(longitudeBytes);
        put(allthings.iot.constant.gps.GpsMsgParam.ATTR_GPS_LONGITUDE, GpsUtil.transformDegree2Decimal(ByteUtils
                .cbcd2string(longitudeBytes)));

        byte[] speedBytes = new byte[2];
        buf.readBytes(speedBytes);
        put(allthings.iot.constant.gps.GpsMsgParam.ATTR_GPS_SPEED, Integer.parseInt(ByteUtils.cbcd2string(speedBytes)));

        byte[] directionBytes = new byte[2];
        buf.readBytes(directionBytes);
        put(allthings.iot.constant.gps.GpsMsgParam.ATTR_GPS_DIRECTION, Integer.parseInt(ByteUtils.cbcd2string
                (directionBytes)));

        //状态
        short status = buf.readUnsignedByte();
        //定位标志，0为未定位，1为已定位
        put(GpsMsgParam.GPS_VALID, (status & 0x80) >> 7);
        //差分定位标志
        //put(MsgParam.TD6, (status & 0x40) >> 6);
        //解析位置数据外的其他字段

        unpack(buf);
    }

    /**
     * 解析除位置数据外的其他数据
     *
     * @param buf
     */
    public abstract void unpack(ByteBuf buf) throws Exception;
}

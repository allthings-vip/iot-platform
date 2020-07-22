package allthings.iot.bsj.das.packet;

import com.google.common.collect.Maps;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import allthings.iot.common.Constant;
import allthings.iot.bsj.das.record.AbstractRecord;
import allthings.iot.constant.gps.GpsMsgParam;
import allthings.iot.das.util.DateUtil;
import allthings.iot.util.gps.util.GpsUtil;
import allthings.iot.util.misc.ByteUtils;
import allthings.iot.util.misc.ReflectUtils;

import java.util.Map;

/**
 * @author :  luhao
 * @FileName :  PacketPosition
 * @CreateDate :  2018/1/8
 * @Description : 位置数据解析
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) allthings-vip All Rights Reserved
 * *******************************************************************************************
 */
public class PacketPosition extends AbstractPacket {

    private static final Logger LOGGER = LoggerFactory.getLogger(PacketPosition.class);

    public PacketPosition(String packetId) {
        super(packetId);
    }

    @Override
    public void unpack(byte[] content) throws Exception {
        ByteBuf buf = Unpooled.wrappedBuffer(content);

        byte[] gpsDatetime = new byte[6];
        buf.readBytes(gpsDatetime);
        put(GpsMsgParam.ATTR_GPS_DATETIME, DateUtil.parse(ByteUtils.cbcd2string(gpsDatetime), "yyMMddHHmmss"));

        byte[] latitudeBytes = new byte[4];
        buf.readBytes(latitudeBytes);
        put(GpsMsgParam.ATTR_GPS_LATITUDE, GpsUtil.transformDegree2Decimal(ByteUtils.cbcd2string(latitudeBytes)));

        byte[] longitudeBytes = new byte[4];
        buf.readBytes(longitudeBytes);
        put(GpsMsgParam.ATTR_GPS_LONGITUDE, GpsUtil.transformDegree2Decimal(ByteUtils.cbcd2string(longitudeBytes)));

        byte[] speedBytes = new byte[2];
        buf.readBytes(speedBytes);
        put(GpsMsgParam.ATTR_GPS_SPEED, Integer.parseInt(ByteUtils.cbcd2string(speedBytes)));

        byte[] directionBytes = new byte[2];
        buf.readBytes(directionBytes);
        put(GpsMsgParam.ATTR_GPS_DIRECTION, Integer.parseInt(ByteUtils.cbcd2string(directionBytes)));

        //状态
        short status = buf.readUnsignedByte();
        //定位标志，0为未定位，1为已定位
        put(GpsMsgParam.GPS_VALID, (status & 0x80) >> 7);
        //gps天线状态，左移5位之后需要计算两位
        put(GpsMsgParam.ATTR_ANTENNA_STATUS, (status & 0x60) >> 5);
        //电源状态
        put(GpsMsgParam.ATTR_POWER_STATUS, status & 0x18 >> 3);
        //登签状态1
        put(GpsMsgParam.ATTR_SIGN_UP_ID_1, status & 0x07);

        //gps里程
        byte[] gpsMileageByte = new byte[3];
        buf.readBytes(gpsMileageByte);
        put(GpsMsgParam.ATTR_GPS_MILEAGE, ByteUtils.toUnsignedInt(gpsMileageByte));

        //车辆状态1
        short vehicleStatusOne = buf.readUnsignedByte();
        put(GpsMsgParam.ATTR_IGNITION, (vehicleStatusOne & 0x80) >> 7);
        put(GpsMsgParam.ATTR_OIL_STATUS, (vehicleStatusOne & 0x04) >> 2);
        put(GpsMsgParam.ATTR_SIGN_UP_STATUS, (vehicleStatusOne & 0x02) >> 1);

        //车辆状态2
        short vehicleStatusTwo = buf.readUnsignedByte();
        put(GpsMsgParam.ATTR_ALARM_ROBBERY, (vehicleStatusTwo & 0x80) >> 7);
        put(GpsMsgParam.ATTR_ALARM_SPEEDING, (vehicleStatusTwo & 0x40) >> 6);
        put(GpsMsgParam.ATTR_ALARM_PARKING, (vehicleStatusTwo & 0x20) >> 5);
        put(GpsMsgParam.ATTR_GPRS_ONLINE_STATUS, (vehicleStatusTwo & 0x02) >> 1);

        //车辆状态3
        short vehicleStatusThree = buf.readUnsignedByte();
        put(GpsMsgParam.ATTR_GPRS_REGISTER_STATUS, (vehicleStatusThree & 0x80) >> 7);
        put(GpsMsgParam.ATTR_CODE_DOWN_STATUS, (vehicleStatusThree & 0x40) >> 6);
        put(GpsMsgParam.ATTR_COMMUNICATION_METHOD, (vehicleStatusThree & 0x20) >> 5);
        put(GpsMsgParam.ATTR_CSQ_STATUS, (vehicleStatusThree & 0x1f));

        //车辆状态4
        buf.readUnsignedByte();

        //ACC开时定时发送时间间隔 2个字节 单位秒,如30秒，表示为0x001E
        put(GpsMsgParam.ATTR_ACC_INTERVAL, buf.readUnsignedShort());
        put(GpsMsgParam.ATTR_PARKING_TIMEOUT, buf.readUnsignedByte());
        put(GpsMsgParam.ATTR_OVERSPEED_VALVE, buf.readUnsignedByte());
        put(GpsMsgParam.ATTR_FENCE_NUM, buf.readUnsignedByte());
        put(GpsMsgParam.ATTR_SIGN_UP_ID_2, buf.readUnsignedByte());
        //U: 定时发送图片的时间 1个字节
        buf.readUnsignedByte();
        //I: 中心下发的主命令  1个字节
        buf.readUnsignedByte();

        //读取扩展数据
        while (buf.readableBytes() > 0) {
            int len = buf.readUnsignedShort();
            Map<String, Object> exMap = Maps.newHashMap();
            exMap.put(Constant.ATTR_EX_LEN, len);

            byte[] orderBytes = new byte[2];
            buf.readBytes(orderBytes);

            byte[] contentBytes = new byte[len - 2];
            buf.readBytes(contentBytes);

            unpackEx(ByteUtils.bytesToHexString(orderBytes), contentBytes);
        }
    }

    /**
     * 解码扩展数据
     *
     * @param order
     * @param contentBytes
     */
    private void unpackEx(String order, byte[] contentBytes) {
        try {
            AbstractRecord record = ReflectUtils.getInstance(order, "Record0x", AbstractRecord.class);
            if (record != null) {
                record.unpack(contentBytes);
                //所有的内容都放入包里面
                this.getParamMap().putAll(record.getParamMap());
            }
        } catch (Exception e) {
            LOGGER.error("{}", e.getMessage(), e);
        }
    }
}

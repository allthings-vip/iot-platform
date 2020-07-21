package allthings.iot.bb808.das.packet;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import allthings.iot.constant.gps.GpsMsgParam;
import allthings.iot.constant.gps.OilType;
import allthings.iot.das.util.DateUtil;
import allthings.iot.util.misc.ByteUtils;

import java.nio.ByteOrder;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author :  luhao
 * @FileName :  Packet0x0200
 * @CreateDate :  2017/12/21
 * @Description : 位置信息汇报
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
public class Packet0x0200 extends AbstractPacket {
    private final Logger logger = LoggerFactory.getLogger(Packet0x0200.class);

    /**
     * 数据内容长度，字节
     */
    public static final int DATA_LENGTH = 28;

    private boolean emergencyAlarm;

    /**
     * 状态
     */
    long status = 0;
    long alarmTag = 0;
    boolean stateIgnition = false;
    boolean gpsValid = false;
    boolean isNorth = true;
    boolean isEast = true;
    boolean doorLock = false;
    private boolean shengJiang = false;
    private boolean dingGai = false;


    private double gpsLatitude = 0;
    private double gpsLongitude = 0;
    private double gpsSpeed = 0;
    private double gpsDirection = 0;
    private double gpsAltitude = 0;
    private Date gpsTime;
    private int gpsNum = 0x00;
    private double gpsMileage = 0;

    private double currentSpeed = 0;

    private final double ONE_MILLION = 10000 * 100;

    /**
     * 0x0200 - 位置信息汇报  位置附加信息项  附加信息ID
     */
    private static final String ADDITIONAL_ITEM_ID = "additionalItemId";

    /**
     * 0x0200 - 位置信息汇报  位置附加信息项  附加信息长度
     */
    private static final String ADDITIONAL_ITEM_LENGTH = "additionalItemLength";

    /**
     * 0x0200 - 位置信息汇报  位置附加信息项  附加信息
     */
    private static final String ADDITIONAL_ITEM_DATA = "additionalItemData";

    /**
     * 扩展协议油耗协议的类型
     */
    private OilType oilType;
    /**
     * 模拟量类型，字符串"0"打头
     */
    private static final String TYPE_PREFIX_ANALOG = "0";
    /**
     * 超声波类型，字符串"1"打头
     */
    private static final String TYPE_PREFIX_ULTRASOUND = "1";

    private float oilRealData;
    private String oilUnit;
    private float oilTemp;
    private int calcCountOil;

    private boolean jgdState = false;
    private boolean yzxState = false;
    private boolean zzxState = false;
    private boolean ygdState = false;

    private int analog1 = 0x00;
    private int analog2 = 0x00;

    private boolean isDY = false;

    public Packet0x0200() {
        super("0200");
    }

    @Override
    public void unpack(byte[] bytes) {
        ByteBuf buf = Unpooled.wrappedBuffer(bytes);

        alarmTag = buf.readUnsignedInt();
        //紧急报警
        emergencyAlarm = (alarmTag & 0x00000001) == 0 ? false : true;

        status = buf.readUnsignedInt();
        this.unpackTerminalStatus(status);

        gpsLatitude = buf.readUnsignedInt() / ONE_MILLION;
        gpsLongitude = buf.readUnsignedInt() / ONE_MILLION;
        gpsAltitude = buf.readUnsignedShort();
        gpsSpeed = (double) buf.readUnsignedShort() / 10;
        gpsDirection = buf.readUnsignedShort();

        // gps time
        byte[] tmpBytes = new byte[6];
        buf.readBytes(tmpBytes, 0, tmpBytes.length);
        String gpsTimeString = ByteUtils.bytesToHexString(tmpBytes);
        try {
            gpsTime = DateUtil.parse(gpsTimeString, "yyMMddHHmmss");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("格式化GPS时间时异常", e);
        }

        // 位置附加信息项列表
        List<Map<String, Object>> additionalItemList = Lists.newArrayList();
        Map<String, Object> additionalItem = null;
        short additionalItemLength = 0;
        try {
            while (buf.readableBytes() > 0) {
                additionalItem = Maps.newHashMap();
                additionalItem.put(ADDITIONAL_ITEM_ID, buf.readUnsignedByte());

                additionalItemLength = buf.readUnsignedByte();
                additionalItem.put(ADDITIONAL_ITEM_LENGTH, additionalItemLength);

                tmpBytes = new byte[additionalItemLength];
                buf.readBytes(tmpBytes, 0, tmpBytes.length);
                additionalItem.put(ADDITIONAL_ITEM_DATA, tmpBytes);

                additionalItemList.add(additionalItem);
            }
        } catch (Exception e) {
            logger.error(e.toString());
        }
        this.unpackAdditionalItemList(additionalItemList);

        this.setParams();
    }

    /**
     * 解析终端的状态
     *
     * @param status
     */
    private void unpackTerminalStatus(long status) {

        // 0 0：ACC 关；1： ACC 开
        if ((status & 0x01) > 0) {
            stateIgnition = true;
        }

        // 1 0：未定位；1：定位
        if ((status & 0x02) > 0) {
            gpsValid = true;
        }

        // 2 0：北纬；1：南纬
        // 纬度（正：北纬； 负：南纬）
        if ((status & 0x04) > 0) {
            isNorth = false;
        }

        // 3 0：东经；1：西经
        // 经度（正：东经； 负：西经）
        if ((status & 0x08) > 0) {
            isEast = false;
        }

        // 12 0车门解锁 1 车门加锁
        if ((status & 0x1000) > 0) {
            doorLock = true;
        }

        // 24 0无效 1有效
        if ((status & 0x1000000) > 0) {
            shengJiang = true;
        }
        // 25 0无效 1有效
        if ((status & 0x2000000) > 0) {
            dingGai = true;
        }
    }

    private void unpackAdditionalItemList(List<Map<String, Object>> additionalItemList) {
        short additionalItemId;
        byte[] additionalItemData;
        for (Map<String, Object> map : additionalItemList) {
            additionalItemId = (short) map.get(ADDITIONAL_ITEM_ID);
            additionalItemData = (byte[]) map.get(ADDITIONAL_ITEM_DATA);

            switch (additionalItemId) {
                case 0x01:
                    this.unpack0x01(additionalItemData);
                    break;
                case 0x02:
                    this.unpack0x02(additionalItemData);
                    break;
                case 0x03:
                    this.unpack0x03(additionalItemData);
                    break;
                case 0x25:
                    this.unpack0x25(additionalItemData);
                    break;

                case 0x2B:
                    this.unpack0x2B(additionalItemData);
                    break;

                case 0x31:
                    this.unpack0x31(additionalItemData);
                    break;

                case 0xE9:
                    this.unpack0xE9(additionalItemData);
                    break;

                case 0xEB:
                    this.unpack0xEB(additionalItemData);
                    break;

                default:
                    logger.warn("unsupported ExternalId[{}]", additionalItemId);
            }
        }
    }

    /**
     * 里程，DWORD，1/10km，对应车上里程表读数
     *
     * @param data
     */
    private void unpack0x03(byte[] data) {
        gpsMileage = ByteUtils.toUnsignedInt(data) * 0.1;
    }

    /**
     * 行驶记录功能获取的速度，WORD,1/10km/h
     *
     * @param data
     */
    private void unpack0x01(byte[] data) {
        currentSpeed = ByteUtils.toInt16(data, 0, ByteOrder.BIG_ENDIAN) * 0.1;
    }

    private void unpack0x02(byte[] data) {
        ByteBuf buf = Unpooled.wrappedBuffer(data);
        oilRealData = (float) (buf.readUnsignedShort() * 0.1);
    }

    private void unpack0x25(byte[] data) {
        ByteBuf buf = Unpooled.wrappedBuffer(data);

        int statusInt = buf.readInt();
        // 近光灯信号
        jgdState = (statusInt & 0x00000001) == 0 ? false : true;
        // 远光灯信号
        ygdState = (statusInt & 0x00000002) == 0 ? false : true;
        // 右转向灯信号
        yzxState = (statusInt & 0x00000004) == 0 ? false : true;
        // 左转向灯信号
        zzxState = (statusInt & 0x00000008) == 0 ? false : true;
    }

    private void unpack0x2B(byte[] data) {
        ByteBuf buf = Unpooled.wrappedBuffer(data);

        analog1 = buf.readUnsignedShort();
        analog2 = buf.readUnsignedShort();
    }

    private void unpack0x31(byte[] data) {
        ByteBuf buf = Unpooled.wrappedBuffer(data);
        gpsNum = buf.readByte();
    }

    private void unpack0xE9(byte[] data) {
        oilType = OilType.CSB;

        ByteBuf buf = Unpooled.wrappedBuffer(data);
        oilRealData = buf.readUnsignedShort() * 0.1f;
        oilUnit = "mm";
    }

    /**
     * 博实结BSJ扩展数据格式，兼容2929扩展协议
     * BSJ扩展指令格式：
     * 长度 2Bytes + 指令 2Bytes + 数据；
     * 其中，长度 = 指令长度 + 数据长度
     *
     * @param data
     */
    private void unpack0xEB(byte[] data) {
        ByteBuf buf = Unpooled.wrappedBuffer(data);

        int length;
        int dataType;
        int dataLength;
        while (buf.readableBytes() > 0) {
            length = buf.readUnsignedShort();
            dataType = buf.readUnsignedShort();
            dataLength = length - 2;
            if (dataType == 0x35) {
                this.unpack0xEB35(buf, dataLength);
            } else if (dataType == 0x36) {
                this.unpack0xEB36(buf, dataLength);
            } else if (dataType == 0x4B) {
                this.unpack0xEB4B(buf, dataLength);
            } else if (dataType == 0xBB) {
                this.unpack0xEBBB(buf, dataLength);
            } else if (dataType == 0x23) {
                this.unpack0xEB23(buf, dataLength);
            }
        }
    }

    /**
     * 油位比
     *
     * @param buf
     * @param dataLength
     */
    private void unpack0xEB23(ByteBuf buf, int dataLength) {
        byte[] oilBytes = new byte[dataLength];
        buf.readBytes(oilBytes);

        oilType = OilType.BL;
        oilRealData = Float.parseFloat(ByteUtils.toString(oilBytes, 0, dataLength));
        oilUnit = "%";
    }

    private void unpack0xEB35(ByteBuf buf, int dataLength) {
        isDY = true;
        oilType = OilType.CSB;
        oilRealData = buf.readUnsignedShort();
        oilUnit = "mm";
    }

    private void unpack0xEB36(ByteBuf buf, int dataLength) {
        isDY = true;
        oilType = OilType.MNL;
        oilRealData = (int) buf.readUnsignedInt();
    }

    /**
     * 固定15字节：A(5字节) + B(4字节) + C(6字节)。
     * C的前2位为98表示富从模拟量测油耗
     * C的前2位为99表示富从超声波测油耗
     *
     * @param buf
     * @param dataLength
     */
    private void unpack0xEB4B(ByteBuf buf, int dataLength) {
        byte[] btsA = new byte[5];
        buf.readBytes(btsA);

        byte[] btsB = new byte[4];
        buf.readBytes(btsB);

        byte[] btsC = new byte[6];
        buf.readBytes(btsC);
        String strC = ByteUtils.toString(btsC, 0, btsC.length);

        if (strC.startsWith("98")) {
            this.unpack0xEB4B98(btsA, btsB, btsC);
        } else if (strC.startsWith("99")) {
            this.unpack0xEB4B99(btsA, btsB, btsC);
        } else {
            this.unpack0xEB4BOther(btsA, btsB, btsC);
        }
    }

    /**
     * 原有富从模拟量测油耗数据格式如下：
     * VT,实际值,0,-1,临时值,类型
     *
     * @param btsA A表示模拟量实际值,
     * @param btsB B表示模拟量临时值
     * @param btsC C的前2位为98表示富从模拟量测油耗，后面4个字节无效
     */
    private void unpack0xEB4B98(byte[] btsA, byte[] btsB, byte[] btsC) {
        oilType = OilType.MNL;

        oilRealData = Integer.parseInt(ByteUtils.toString(btsA, 0, btsA.length));
        oilTemp = Integer.parseInt(ByteUtils.toString(btsB, 0, btsB.length));
    }

    /**
     * 原有富从超声波测油耗数据格式如下：
     * VT,实际值(0.1mm),温度(摄氏度),测试次数,临时值(0.1mm),类型
     *
     * @param btsA A表示超声波实际值
     * @param btsB B表示超声波临时值
     * @param btsC C的前2位为99表示富从超声波测油耗，后面4个字节表示超声波的超声波测试次数
     */
    private void unpack0xEB4B99(byte[] btsA, byte[] btsB, byte[] btsC) {
        oilType = OilType.CSB;

        oilRealData = Integer.parseInt(ByteUtils.toString(btsA, 0, btsA.length)) * 0.1f;
        oilTemp = Integer.parseInt(ByteUtils.toString(btsB, 0, btsB.length)) * 0.1f;
        calcCountOil = Integer.parseInt(ByteUtils.toString(btsC, 0, btsC.length).substring(2));
        oilUnit = "mm";
    }

    /**
     * 久通油感（单路）
     *
     * @param btsA 当前液位高度万分比，最大值为10000
     * @param btsB 当前经过调整后的油位值，0-4095
     * @param btsC 除以100为当前的油位升数，如384.09L
     */
    private void unpack0xEB4BOther(byte[] btsA, byte[] btsB, byte[] btsC) {
        oilType = OilType.ZSL;

        oilRealData = Integer.parseInt(ByteUtils.toString(btsC, 0, btsC.length)) / 100;
        oilUnit = "L";
    }

    private void unpack0xEBBB(ByteBuf buf, int dataLength) {
        byte[] bts = new byte[dataLength];
        buf.readBytes(bts);

        String ss = ByteUtils.toString(bts, 0, bts.length);
        String[] arr = ss.split(",");

        String type = arr[5];
        if (type.startsWith(TYPE_PREFIX_ULTRASOUND)) {
            unpackOfUltrasound(arr);
        } else if (type.startsWith(TYPE_PREFIX_ANALOG)) {
            unpackOfAnalog(arr);
        } else {
            logger.warn("unsupported type [{}]", type);
        }
    }

    /**
     * 超声波格式如下：
     * VT,实际值(0.1mm),温度(摄氏度),测试次数,临时值(0.1mm),类型
     * VT,541,16,11,531,1
     *
     * @param paramArr
     */
    private void unpackOfUltrasound(String[] paramArr) {
        oilType = OilType.CSB;
        oilRealData = Integer.parseInt(paramArr[1]) * 0.1f;
        calcCountOil = Integer.parseInt(paramArr[3]);
        oilTemp = Float.parseFloat(paramArr[4]) * 0.1f;
        oilUnit = "mm";
    }

    /**
     * 模拟量格式如下：
     * VT,实际值,0,-1,临时值,类型
     * VT,2193,0,-1,2194,0
     *
     * @param paramArr
     */
    private void unpackOfAnalog(String[] paramArr) {
        oilType = OilType.MNL;
        oilRealData = Integer.parseInt(paramArr[1]);
        calcCountOil = Integer.parseInt(paramArr[3]);
        oilTemp = Integer.parseInt(paramArr[4]);
    }

    private void setParams() {
        Map<String, Object> gpsParamMap = Maps.newHashMap();

        gpsParamMap.put(GpsMsgParam.ATTR_GPS_LATITUDE, isNorth ? gpsLatitude : 0 - gpsLatitude);
        gpsParamMap.put(GpsMsgParam.ATTR_GPS_LONGITUDE, isEast ? gpsLongitude : 0 - gpsLongitude);
        gpsParamMap.put(GpsMsgParam.ATTR_GPS_STATUS, status);
        gpsParamMap.put(GpsMsgParam.ATTR_GPS_ALARM, alarmTag);
        gpsParamMap.put(GpsMsgParam.ATTR_GPS_ALTITUDE, gpsAltitude);
        gpsParamMap.put(GpsMsgParam.ATTR_GPS_SPEED, gpsSpeed);
        gpsParamMap.put(GpsMsgParam.ATTR_CURRENT_SPEED, currentSpeed);
        gpsParamMap.put(GpsMsgParam.ATTR_GPS_DIRECTION, gpsDirection);
        gpsParamMap.put(GpsMsgParam.ATTR_GPS_DATETIME, gpsTime);
        gpsParamMap.put(GpsMsgParam.ATTR_GPS_NUM, gpsNum);
        gpsParamMap.put(GpsMsgParam.ATTR_GPS_MILEAGE, gpsMileage);

        gpsParamMap.put(GpsMsgParam.GPS_VALID, gpsValid);
        gpsParamMap.put(GpsMsgParam.ATTR_IGNITION, stateIgnition);
        gpsParamMap.put(GpsMsgParam.DOOR_LOCK, doorLock);

        gpsParamMap.put(GpsMsgParam.ATTR_SWITCH0, jgdState);
        gpsParamMap.put(GpsMsgParam.ATTR_SWITCH1, ygdState);
        gpsParamMap.put(GpsMsgParam.ATTR_SWITCH2, yzxState);
        gpsParamMap.put(GpsMsgParam.ATTR_SWITCH3, zzxState);

        if (isDY) {
            gpsParamMap.put(GpsMsgParam.ATTR_SWITCH0, shengJiang);
            gpsParamMap.put(GpsMsgParam.ATTR_SWITCH1, dingGai);
        }

        gpsParamMap.put(GpsMsgParam.ATTR_ANALOG1, analog1);
        gpsParamMap.put(GpsMsgParam.ATTR_ANALOG2, analog2);
        gpsParamMap.put(GpsMsgParam.ATTR_COM4, emergencyAlarm);

        if (oilType != null) {
            gpsParamMap.put(GpsMsgParam.ATTR_OIL_TYPE, oilType.name());
        }
        gpsParamMap.put(GpsMsgParam.ATTR_OIL, oilRealData);
        gpsParamMap.put(GpsMsgParam.ATTR_OIL_UNIT, oilUnit);
        gpsParamMap.put(GpsMsgParam.ATTR_OIL_CALC_TIME, calcCountOil);
        gpsParamMap.put(GpsMsgParam.ATTR_TEMP_OIL, oilTemp);
        gpsParamMap.put(GpsMsgParam.ATTR_OIL_NEW, true);

        List<Map<String, Object>> gpsParamMapList = Lists.newArrayList();
        gpsParamMapList.add(gpsParamMap);

        super.put(GpsMsgParam.DATA_CONTENT, gpsParamMapList);
    }
}

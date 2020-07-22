package allthings.iot.gbt32960.das.packet;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import allthings.iot.common.msg.IMsg;
import allthings.iot.constant.vehicle.VehicleMsgParam;
import allthings.iot.gbt32960.das.ContextConfig;
import allthings.iot.util.misc.ByteUtils;
import allthings.iot.util.misc.Crc16Utils;

/**
 * @author :  luhao
 * @FileName :  AbstractPacket
 * @CreateDate :  2018/01/29
 * @Description :
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) allthings-vip All Rights Reserved
 * *******************************************************************************************
 */
public abstract class AbstractPacket {
    public static final byte[] HEAD = {0x23, 0x23};
    public static final byte[] TAIL = {};
    public static final byte    RESPONSE_SUCCESS = 0x01;
    public static final byte    RESPONSE_ERROR   = 0x02;
    public static final byte    RESPONSE_VIN     = 0x03;
    public static final short   RESPONSE_COMMAND = (short) 0xFE;
    public static final String  BODY_BYTES_ATTR  = "BODY_BYTES_ATTR";

    /**
     * 消息编码
     */
    private String packetId;

    /**
     * 消息数据体
     */
    private byte[] messageBody = new byte[0];

    /**
     * 参数集
     */
    private Map<String, Object> paramMap = new HashMap<>();

    /**
     * 数据采集的时间
     */
    private Long occurDatetime;


    public AbstractPacket(String packetId) {
        super();
        this.packetId = packetId;
    }

    public String getPacketId() {
        return packetId;
    }

    public void setPacketId(String packetId) {
        this.packetId = packetId;
    }

    public byte[] getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(byte[] messageBody) {
        this.messageBody = messageBody;
    }

    public Map<String, Object> getParamMap() {
        return paramMap;
    }

    public void setParamMap(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }

    public void put(String paramKey, Object paramValue) {
        paramMap.put(paramKey, paramValue);
    }

    public <T> T get(String paramKey) {
        if (!paramMap.containsKey(paramKey)) {
            return null;
        }

        return (T) paramMap.get(paramKey);
    }

    public Long getOccurDatetime() {
        return occurDatetime;
    }

    public void setOccurDatetime(Long occurDatetime) {
        this.occurDatetime = occurDatetime;
    }

    /**
     * 解包
     */
    public void unpack(byte[] content) throws Exception {
        // 心跳和终端校时没有数据单元
        if (this.packetId.equals("0x07") || this.packetId.equals("0x08")) {
            return;
        }
        //每个报文中都存在时间
        ByteBuf byteBuf = Unpooled.wrappedBuffer(content);
        int year = ByteUtils.toInt(byteBuf.readByte());
        int month = ByteUtils.toInt(byteBuf.readByte());
        int day = ByteUtils.toInt(byteBuf.readByte());
        int hour = ByteUtils.toInt(byteBuf.readByte());
        int minute = ByteUtils.toInt(byteBuf.readByte());
        int second = ByteUtils.toInt(byteBuf.readByte());

        StringBuilder strTime = new StringBuilder("20");
        strTime.append(year).append("-").append(month).append("-").append(day).append(" ").append(hour).append(":")
                .append(minute).append(":").append(second);
        //数据采集时间
        DateTime time = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").parseDateTime(strTime.toString());
        //转换成时间戳
        occurDatetime = time.getMillis();
        this.put(VehicleMsgParam.OCCUR_DATE_TIME, occurDatetime);
        this.put(BODY_BYTES_ATTR, content);
        unpack(byteBuf);
    }

    /**
     * 处理时间戳外的其他数据
     *
     * @param byteBuf
     * @throws Exception
     */
    public abstract void unpack(ByteBuf byteBuf) throws Exception;

    /**
     * 组包
     */
    public byte[] pack(IMsg msg) {
        int bufferSize = 22;
        short bodyLength = 0;
        byte[] occurDateTime = new byte[0];
        byte[] bodyBytes = new byte[0];
        if (ContextConfig.IS_DIRECT_CLIENT) {
            if (this.packetId.equals("0x07")) {// 心跳
                occurDateTime = new byte[0];
                bodyBytes = new byte[0];
                bodyLength = 0;
                bufferSize += 2;
            } else if (this.packetId.equals("0x08")) {// 终端校时
                occurDateTime = this.buildOccurDateTime(new Date().getTime());
                bodyBytes = new byte[0];
                bodyLength = 6;
                bufferSize += bodyLength + 2;
            } else {// 其他
                byte[] srcBodyBytes = msg.get(AbstractPacket.BODY_BYTES_ATTR);
                ByteBuf buf = Unpooled.wrappedBuffer(srcBodyBytes);
                bodyBytes = new byte[srcBodyBytes.length - 6];
                buf.getBytes(6, bodyBytes);
                // bodyBytes = this.packBody(msg.getParams());
                bodyLength = (short) srcBodyBytes.length;// (short) bodyBytes.length;
                occurDateTime = this.buildOccurDateTime(new Date().getTime());
                bufferSize += bodyBytes.length + 8;
            }
        } else {
            bodyLength = 6;
            long occurDatetime = msg.get(VehicleMsgParam.OCCUR_DATE_TIME);
            occurDateTime = this.buildOccurDateTime(occurDatetime);
            bufferSize += 8;
        }
        ByteBuffer buffer = ByteBuffer.allocate(bufferSize + 1).order(ByteOrder.BIG_ENDIAN);
        buffer.put(HEAD);
        buffer.put(ByteUtils.hexStringToBytes(msg.getMsgCode()));
        buffer.put(RESPONSE_SUCCESS);

        ByteBuf buf = Unpooled.buffer(17);
        if (ContextConfig.IS_DIRECT_CLIENT) {
            buf.writeBytes(msg.getTargetDeviceId().getBytes());
        } else {
            buf.writeBytes(ByteUtils.hexStringToBytes(msg.getTargetDeviceId()));
        }
        buffer.put(buf.array());

        byte encryptionRule = msg.get(VehicleMsgParam.ENCRYPTION_RULE);
        buffer.put(encryptionRule);
        buffer.putShort(bodyLength);
        buffer.put(occurDateTime);
        buffer.put(bodyBytes);
        buffer.put((byte) 0);

        buffer.flip();
        ByteBuf crcBuf = Unpooled.wrappedBuffer(buffer);
        byte[] crcBody = new byte[buffer.capacity() - 3];
        crcBuf.getBytes(2, crcBody);
        int crc = Crc16Utils.calcCrc(crcBody);
        buffer.put(buffer.capacity() - 1, ByteUtils.toByte(crc));
        return buffer.array();
    }

    /**
     * 构建时间字节流
     * 
     * @param millis
     * @return
     * @author mingyuan.miao 2018年4月19日 下午5:49:25
     */
    private byte[] buildOccurDateTime(long millis) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(millis);
        int year = cal.get(Calendar.YEAR);
        year = year % 2000;
        byte[] occurTimes = new byte[6];
        occurTimes[0] = ByteUtils.toByte(year);
        occurTimes[1] = ByteUtils.toByte(cal.get(Calendar.MONTH) + 1);
        occurTimes[2] = ByteUtils.toByte(cal.get(Calendar.DAY_OF_MONTH));
        occurTimes[3] = ByteUtils.toByte(cal.get(Calendar.HOUR_OF_DAY));
        occurTimes[4] = ByteUtils.toByte(cal.get(Calendar.MINUTE));
        occurTimes[5] = ByteUtils.toByte(cal.get(Calendar.SECOND));
        return occurTimes;
    }

    /**
     * 数据单元打包
     * 
     * @param params
     * @return
     * @author mingyuan.miao 2018年4月19日 下午4:55:52
     */
    protected abstract byte[] packBody(Map<String, Object> params);
}

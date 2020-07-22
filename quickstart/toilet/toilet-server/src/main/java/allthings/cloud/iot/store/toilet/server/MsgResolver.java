package tf56.cloud.iot.store.toilet.server;

import com.google.common.collect.Lists;
import tf56.cloud.iot.store.toilet.common.dic.SensorDataWrap;
import tf56.cloud.iot.store.toilet.common.dic.SensorInfoManager;
import tf56.cloud.iot.store.toilet.common.protolcol.MsgCodes;
import tf56.cloud.iot.store.toilet.common.protolcol.MsgParams;
import tf56.cloud.iot.store.toilet.common.protolcol.ProtocolConst;
import allthings.iot.common.msg.*;
import allthings.iot.common.pojo.DeviceGuid;
import allthings.iot.das.mqtt.core.IMqttMsgResolver;
import allthings.iot.das.mqtt.pojo.MqttPacketWrap;
import allthings.iot.das.util.DateUtil;
import allthings.iot.util.misc.ByteUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author :  sylar
 * @FileName :  MsgResolver
 * @CreateDate :  2017/11/08
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
@Component
public class MsgResolver implements IMqttMsgResolver {

    public static final Charset CHARSET = Charset.forName("utf-8");
    private static final Logger LOG = LoggerFactory.getLogger(MsgResolver.class);
    private static final ByteOrder ORDER = ByteOrder.LITTLE_ENDIAN;

    static List<SensorDataWrap> readSensons(ByteBuffer payload) {
        String sensorGuid;
        int sensorCfg;
        boolean online;
        List<SensorDataWrap> sensons = Lists.newArrayList();

        int sensonCount = payload.get();
        for (int i = 0; i < sensonCount; i++) {
            sensorGuid = ByteUtils.toString(payload, DeviceGuid.GUID_LENGTH);
            sensorCfg = payload.get();
            online = payload.get() == 1;
            sensons.add(new SensorDataWrap(sensorGuid, sensorCfg, online));
        }

        return sensons;
    }

    static Map<String, Object> readSensonsData(ByteBuffer payload) {
        int sensonCount = payload.get();

        String sensorGuid;
        int sensorCfg;
        boolean online;
        int dataLength;
        byte[] sensorData;

        List<SensorDataWrap> sensons = Lists.newArrayList();
        for (int i = 0; i < sensonCount; i++) {
            sensorGuid = ByteUtils.toString(payload, DeviceGuid.GUID_LENGTH);
            sensorCfg = payload.get();
            online = payload.get() == 1;
            dataLength = payload.get();
            sensorData = new byte[dataLength];
            payload.get(sensorData);

            sensons.add(new SensorDataWrap(sensorGuid, sensorCfg, online, sensorData));
        }

        return SensorInfoManager.getInstance().buildSensorDataMap(sensons);
    }

    @Override
    public List<IMsg> wrapToMsg(MqttPacketWrap wrap) {

        int cmdCode = wrap.getCmdCode();
        DeviceGuid srcGuid = wrap.getSourceGuid();
        DeviceGuid dstGuid = wrap.getTargetGuid();

        ByteBuffer payload = wrap.getPayload();

        try {
            List<IMsg> msgList = Lists.newArrayList();
            IMsg msg;

            switch (cmdCode) {
                case MsgCodes.ONLINE_NOTIFY:
                    msg = onOnlineNotify(cmdCode, srcGuid, dstGuid, payload);
                    break;
                case MsgCodes.SENSORS_CHANGED_NOTIFY:
                    msg = onSensorsChangeNotify(cmdCode, srcGuid, dstGuid, payload);
                    break;
                case MsgCodes.OTA_SEND_RES:
                    msg = onOtaSendRes(cmdCode, srcGuid, dstGuid, payload);
                    break;
                case MsgCodes.REPORT_SENSOR_DATA:
                    msg = onReportSensorData(cmdCode, srcGuid, dstGuid, payload);
                    break;
                case MsgCodes.REPORT_RFID_DATA:
                    msg = onReportRfidData(cmdCode, srcGuid, dstGuid, payload);
                    break;
                case MsgCodes.EVENT_NOTIFY:
                    msg = onEventNotify(cmdCode, srcGuid, dstGuid, payload);
                    break;

                case MsgCodes.SET_ROUTE_RES:
                case MsgCodes.DELETE_SENSOR_RES:
                case MsgCodes.SET_REPORT_INTERVAL_RES:
                case MsgCodes.SET_DEVICE_NUM_RES:
                case MsgCodes.SET_TOILET_PARAM_RES:
                case MsgCodes.GET_ROUTE_RES:
                case MsgCodes.GET_SENSORS_RES:
                case MsgCodes.GET_SENSOR_DATA_RES:
                case MsgCodes.GET_REPORT_INTERVAL_RES:
                    msg = onRcMsg(cmdCode, srcGuid, dstGuid, payload);
                    break;

                case MsgCodes.SYNC_TIME:
                case MsgCodes.OTA_SUCCESS_NOTIFY:
                default:
                    DeviceMsg deviceMsg = new DeviceMsg();
                    deviceMsg.setMsgCode(String.valueOf(cmdCode));
                    deviceMsg.setDevice(srcGuid, dstGuid);

                    msg = deviceMsg;
                    break;
            }

            if (msg != null) {
                msgList.add(msg);
            }
            return msgList;

        } catch (Exception e) {
            LOG.error("wrapToMsg error. cmdCode:{} , error:{}", cmdCode, e.getMessage());
            throw e;
        }
    }

    @Override
    public MqttPacketWrap msgToWrap(IMsg msg) {
        int msgCode = Integer.parseInt(msg.getMsgCode());
        MqttPacketWrap wrap = new MqttPacketWrap();
        wrap.setCmdCode(msgCode);
        wrap.setSourceDevice(msg.getSourceDeviceType(), msg.getSourceDeviceId());
        wrap.setTargetDevice(msg.getTargetDeviceType(), msg.getTargetDeviceId());

        ByteBuffer payload = null;

        try {
            switch (msgCode) {
                case MsgCodes.SYNC_TIME_RES:
                case MsgCodes.REPORT_SENSOR_DATA_RES:
                case MsgCodes.REPORT_RFID_DATA_RES:
                    payload = onReturnTime(msg);
                    break;
                case MsgCodes.OTA_SEND:
                    payload = onOtaSend(msg);
                    break;
                case MsgCodes.CLOUD_PUSH:
                    payload = onCloudPush(msg);
                    break;
                case MsgCodes.SET_ROUTE:
                    payload = onSetRoute(msg);
                    break;
                case MsgCodes.DELETE_SENSOR:
                    payload = onDeleteSensor(msg);
                    break;
                case MsgCodes.SET_REPORT_INTERVAL:
                    payload = onSetReportInterval(msg);
                    break;
                case MsgCodes.SET_DEVICE_NUM:
                    payload = onSetDeviceNum(msg);
                    break;
                case MsgCodes.SET_TOILET_PARAM:
                    payload = onSetToiletParam(msg);
                    break;


                case MsgCodes.GET_ROUTE:
                case MsgCodes.GET_SENSORS:
                case MsgCodes.GET_SENSOR_DATA:
                case MsgCodes.GET_REPORT_INTERVAL:
                default:
                    break;
            }

            if (payload != null) {
                wrap.setPayload(payload);
            }

            return wrap;
        } catch (Exception e) {
            LOG.error("msgToWrap error. cmdCode:{} , error:{}", msgCode, e.getMessage());
            throw e;
        }
    }

    IMsg onOnlineNotify(int cmdCode, DeviceGuid srcGuid, DeviceGuid dstGuid, ByteBuffer payload) {
        String mac = ByteUtils.toString(payload, ProtocolConst.LEN_MAC);
        String guid = ByteUtils.toString(payload, DeviceGuid.GUID_LENGTH);
        int version = payload.get();

        DeviceInfoMsg msg = new DeviceInfoMsg();
        msg.setMsgCode(String.valueOf(cmdCode));
        msg.setDevice(srcGuid, dstGuid);
        msg.setMac(mac);
        msg.setVersion(version);
        return msg;
    }

    IMsg onReportSensorData(int cmdCode, DeviceGuid srcGuid, DeviceGuid dstGuid, ByteBuffer payload) {
        Date time = DateUtil.readDate(payload);
        Map<String, Object> map = readSensonsData(payload);

        DeviceDataMsg msg = new DeviceDataMsg();
        msg.setMsgCode(String.valueOf(cmdCode));
        msg.setDevice(srcGuid, dstGuid);
        msg.setTimestamp(time.getTime());
        msg.setParams(map);
        return msg;
    }

    IMsg onSensorsChangeNotify(int cmdCode, DeviceGuid srcGuid, DeviceGuid dstGuid, ByteBuffer payload) {
        DeviceMsg msg = new DeviceMsg();
        msg.setMsgCode(String.valueOf(cmdCode));
        msg.setDevice(srcGuid, dstGuid);

        List<SensorDataWrap> sensons = readSensons(payload);
        msg.put(MsgParams.SENSONS, sensons);
        return msg;
    }

    IMsg onReportRfidData(int cmdCode, DeviceGuid srcGuid, DeviceGuid dstGuid, ByteBuffer payload) {
        Date time = DateUtil.readDate(payload);
        String rfid = ByteUtils.toString(payload, ProtocolConst.LEN_RFID);
        rfid = rfid.substring(2);
        rfid = String.valueOf(Long.parseLong(rfid, 16));

        DeviceMsg msg = new DeviceMsg();
        msg.setMsgCode(String.valueOf(cmdCode));
        msg.setDevice(srcGuid, dstGuid);

        msg.put(MsgParams.TIME, time.getTime());
        msg.put(MsgParams.RFID, rfid);
        return msg;
    }

    IMsg onEventNotify(int cmdCode, DeviceGuid srcGuid, DeviceGuid dstGuid, ByteBuffer payload) {
        int eventCode = payload.get();
        int dataLen = payload.get();
        byte[] eventData = new byte[dataLen];
        payload.get(eventData);

        DeviceEventMsg msg = new DeviceEventMsg();
        msg.setMsgCode(String.valueOf(cmdCode));
        msg.setDevice(srcGuid, dstGuid);

        msg.setEventCode(String.valueOf(eventCode));
        return msg;
    }

    IMsg onOtaSendRes(int cmdCode, DeviceGuid srcGuid, DeviceGuid dstGuid, ByteBuffer payload) {
        int rc = payload.get();
        short currentPackageIndex = payload.getShort();

        DeviceMsg msg = new DeviceMsg();
        msg.setMsgCode(String.valueOf(cmdCode));
        msg.setDevice(srcGuid, dstGuid);

        msg.put(MsgParams.RC, rc);
        msg.put(MsgParams.OTACURRENTPACKETINDEX, currentPackageIndex);
        return msg;
    }

    IMsg onRcMsg(int cmdCode, DeviceGuid srcGuid, DeviceGuid dstGuid, ByteBuffer payload) {
        DeviceMsg rcMsg = new DeviceMsg();
        rcMsg.setMsgCode(String.valueOf(cmdCode));
        rcMsg.setDevice(srcGuid, dstGuid);

        int rc = payload.get();
        rcMsg.put(MsgParams.RC, rc);
        switch (cmdCode) {
            case MsgCodes.GET_ROUTE_RES:
                int ssidLen = payload.get();
                String ssid = ByteUtils.toString(payload, ssidLen);
                int pwdLen = payload.get();
                String pwd = ByteUtils.toString(payload, pwdLen);
                rcMsg.put(MsgParams.WIFISSID, ssid);
                rcMsg.put(MsgParams.WIFIPWD, pwd);
                break;
            case MsgCodes.GET_REPORT_INTERVAL_RES:
                short interval = payload.getShort();
                rcMsg.put(MsgParams.REPORTINTERVAL, interval);
                break;
            case MsgCodes.GET_SENSORS_RES:
                List<SensorDataWrap> sensons = readSensons(payload);
                rcMsg.put(MsgParams.SENSONS, sensons);
                break;
            case MsgCodes.GET_SENSOR_DATA_RES:
                rcMsg.setParams(readSensonsData(payload));
                break;
            default:
                break;
        }
        return rcMsg;
    }

    ByteBuffer onOtaSend(IMsg msg) {
        short packetCount = msg.get(MsgParams.OTAPACKETCOUNT);
        short curPacketIndex = msg.get(MsgParams.OTACURRENTPACKETINDEX);
        byte[] packetData = msg.get(MsgParams.OTAPACKETDATA);

        ByteBuffer payload = ByteUtils.allocate(2 + 2 + packetData.length, ORDER);
        payload.putShort(packetCount);
        payload.putShort(curPacketIndex);
        payload.put(packetData);
        return payload;
    }

    ByteBuffer onCloudPush(IMsg msg) {
        String pushContent = msg.get(MsgParams.PUSHCONTENT);
        ByteBuffer payload = ByteUtils.allocate(pushContent.length(), ORDER);
        payload.put(pushContent.getBytes(CHARSET));
        return payload;
    }

    ByteBuffer onSetRoute(IMsg msg) {
        String ssid = msg.get(MsgParams.WIFISSID);
        String pwd = msg.get(MsgParams.WIFIPWD);

        ByteBuffer payload = ByteUtils.allocate(ssid.length() + pwd.length() + 1 + 1, ORDER);
        payload.put((byte) ssid.length());
        payload.put(ssid.getBytes(CHARSET));
        payload.put((byte) pwd.length());
        payload.put(pwd.getBytes(CHARSET));
        return payload;
    }

    ByteBuffer onDeleteSensor(IMsg msg) {
        String guid = msg.get(MsgParams.GUID);
        ByteBuffer payload = ByteUtils.allocate(guid.length(), ORDER);
        payload.put(guid.getBytes(CHARSET));
        return payload;
    }

    ByteBuffer onSetReportInterval(IMsg msg) {
        short interval = msg.get(MsgParams.REPORTINTERVAL);
        ByteBuffer payload = ByteUtils.allocate(2, ORDER);
        payload.putShort(interval);
        return payload;
    }

    ByteBuffer onSetDeviceNum(IMsg msg) {
        String deviceNum = msg.get(MsgParams.DEVICENUM);
        ByteBuffer payload = ByteUtils.allocate(DeviceGuid.DEVICE_NUMBER_LENGTH, ORDER);
        payload.put(deviceNum.getBytes(CHARSET));
        return payload;
    }

    ByteBuffer onSetToiletParam(IMsg msg) {
        short p1 = msg.get(MsgParams.TOILETPARAMFAN);
        short p2 = msg.get(MsgParams.TOILETPARAMDEODORANT);
        ByteBuffer payload = ByteUtils.allocate(4, ORDER);
        payload.putShort(p1);
        payload.putShort(p2);
        return payload;
    }

    ByteBuffer onReturnTime(IMsg msg) {
        byte rc = Byte.valueOf(msg.get(MsgParams.RC).toString());
        long millis = msg.get(MsgParams.TIME);
        byte[] time = DateUtil.millisecond2Bytes(millis);

        ByteBuffer payload = ByteUtils.allocate(time.length + 1, ORDER);
        payload.put(rc);
        payload.put(time);
        return payload;
    }

}

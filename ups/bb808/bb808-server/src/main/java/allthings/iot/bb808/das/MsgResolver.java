package allthings.iot.bb808.das;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import allthings.iot.bb808.common.*;
import allthings.iot.bb808.das.packet.AbstractPacket;
import allthings.iot.bb808.das.packet.PacketHeader;
import allthings.iot.common.msg.DeviceConnectionMsg;
import allthings.iot.common.msg.DeviceDataMsg;
import allthings.iot.common.msg.DeviceMsg;
import allthings.iot.common.msg.IMsg;
import allthings.iot.common.pojo.DeviceGuid;
import allthings.iot.common.usual.DeviceTypes;
import allthings.iot.das.common.DasConfig;
import allthings.iot.das.common.NettyUtil;
import allthings.iot.das.simple.ISimpleMsgResolver;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;

/**
 * @author :  luhao
 * @FileName :  MsgResolver
 * @CreateDate :  2017/12/21
 * @Description :
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
@Component
public class MsgResolver extends FrameCodec implements ISimpleMsgResolver {
    private final Logger logger = LoggerFactory.getLogger(MsgResolver.class);

    @Autowired
    private DasConfig dasConfig;


    @Value("${authentication.enable}")
    private Boolean authEnable;


    @Override
    public List<IMsg> bufToMsg(ChannelHandlerContext ctx, ByteBuffer buf) {
        List<IMsg> list = null;
        try {
            list = super.decode(ctx, buf);
        } catch (IOException e) {
            logger.error(e.toString(), e);
        }

        return list;
    }

    @Override
    protected List<IMsg> onDecodeMsg(ChannelHandlerContext ctx, MsgWrap wrap) {
        List<IMsg> msgList = Lists.newArrayList();

        // 获取消息头关键参数
        Map<String, Object> headerParamMap = wrap.getPacketHeader().getParamMap();
        String sourceDeviceId = (String) headerParamMap.get(MsgParam.PHONE_NO);
        // 格式为2B 十六进制，如 0100
        String msgCode = (String) headerParamMap.get(MsgParam.MSG_TAG);
        Integer total = (Integer) headerParamMap.get(MsgParam.MSG_TOTAL_CNT);
        Integer packetIndex = (Integer) headerParamMap.get(MsgParam.MSG_INDEX);

        ByteBuffer body = wrap.getContent();
        byte[] bodyBytes = new byte[body.remaining()];
        body.get(bodyBytes, 0, bodyBytes.length);

        // 分包数据缓存
        cacheSubPacket(sourceDeviceId, msgCode, wrap.getPacketHeader(), bodyBytes);

        // 获取消息体对应的参数map
        Map<String, Object> bodyParamMap = Maps.newHashMap();
        AbstractPacket bodyPacket = this.unPackByMsgCode(sourceDeviceId, msgCode, total);
        if (bodyPacket != null) {
            bodyParamMap = bodyPacket.getParamMap();
        } else {
            return msgList;
        }

        switch (msgCode) {
            case MsgCode.MSG_REGISTER:
                // 当设备连接上平台时,需要构造 DeviceConnectionMsg
                this.addDeviceConnectionMsg(ctx, msgList, sourceDeviceId, true);

                // DeviceConnectionMsg放在DeviceMsg前
                this.addDeviceMsg(msgList, sourceDeviceId, msgCode, headerParamMap, bodyParamMap, null);
                break;

            case MsgCode.MSG_LOGIN:
                /*
                 当设备连接上平台时,需要构造 DeviceConnectionMsg
                 如果当前连接发起过注册，则无需再构造Connection Msg，否则应构造
                  */
                String clientId = NettyUtil.getClientId(ctx.channel());
                if (StringUtils.isBlank(clientId)) {
                    this.addDeviceConnectionMsg(ctx, msgList, sourceDeviceId, true);
                }

                // DeviceConnectionMsg放在DeviceMsg前
                this.addDeviceMsg(msgList, sourceDeviceId, msgCode, headerParamMap, bodyParamMap, null);
                break;

            case MsgCode.MSG_DEVICE_GENERAL_RES:
                this.addDeviceMsg(msgList, sourceDeviceId, msgCode, headerParamMap, bodyParamMap, null);
                break;

            case MsgCode.MSG_HEARTBEAT:
                this.addDeviceMsg(msgList, sourceDeviceId, msgCode, headerParamMap, bodyParamMap, null);
                break;

            case MsgCode.MSG_POSITION_UPLOAD:
                this.addDeviceMsg(msgList, sourceDeviceId, msgCode, headerParamMap, bodyParamMap, DataEnum
                        .VEHICLE_GPS);
                break;

            case MsgCode.MSG_DRIVER_IC:
                this.addDeviceMsg(msgList, sourceDeviceId, msgCode, headerParamMap, bodyParamMap, DataEnum
                        .VEHICLE_DRIVER_IC);
                break;

            case MsgCode.MSG_POSITION_BATCH_UPLOAD:
                this.addDeviceMsg(msgList, sourceDeviceId, msgCode, headerParamMap, bodyParamMap, DataEnum
                        .VEHICLE_GPS);
                break;

            case MsgCode.MSG_MULTIMEDIA_UPLOAD:
                DataEnum e;
                if (bodyPacket == null) {
                    e = null;
                } else {
                    e = DataEnum.VEHICLE_MULTIMEDIA;
                }
                this.addDeviceMsg(msgList, sourceDeviceId, msgCode, headerParamMap, bodyParamMap, e);
                break;
            case MsgCode.MSG_TRANSPARENT_TRANSMISSION_UP:
                this.constructMsgFor0x0900(msgList, sourceDeviceId, msgCode, headerParamMap, bodyParamMap);
                break;

            case MsgCode.MSG_SEARCH_TERMINAL_PARAMETER_RES:
                this.addDeviceMsg(msgList, sourceDeviceId, msgCode, headerParamMap, bodyParamMap, DataEnum
                        .VEHICLE_SETTING);
                break;

            case MsgCode.MSG_SEARCH_TERMINAL_ATTRIBUTE_RES:
                this.addDeviceMsg(msgList, sourceDeviceId, msgCode, headerParamMap, bodyParamMap, DataEnum
                        .VEHICLE_ATTR);
                break;

            case MsgCode.MSG_POSITION_RES:
                this.addDeviceMsg(msgList, sourceDeviceId, msgCode, headerParamMap, bodyParamMap, DataEnum
                        .VEHICLE_GPS);
                this.addDeviceMsg(msgList, sourceDeviceId, msgCode, headerParamMap, bodyParamMap, DataEnum
                        .VEHICLE_GPS_RES);
                break;

            case MsgCode.MSG_EVENT_UPLOAD:
                this.addDeviceMsg(msgList, sourceDeviceId, msgCode, headerParamMap, bodyParamMap, DataEnum
                        .VEHICLE_EVENT_UPLOAD);
                break;

            case MsgCode.MSG_QES_ANSWER:
                this.addDeviceMsg(msgList, sourceDeviceId, msgCode, headerParamMap, bodyParamMap, DataEnum
                        .VEHICLE_QES_ANSWER);
                break;

            case MsgCode.MSG_INFO_SUB:
                this.addDeviceMsg(msgList, sourceDeviceId, msgCode, headerParamMap, bodyParamMap, DataEnum
                        .VEHICLE_INFO_SUB);
                break;

            case MsgCode.MSG_CONTROL_RES:
                this.addDeviceMsg(msgList, sourceDeviceId, msgCode, headerParamMap, bodyParamMap, DataEnum
                        .VEHICLE_CONTROL_RES);
                break;

            case MsgCode.MSG_CAN_DATA_UPLOAD:
                this.addDeviceMsg(msgList, sourceDeviceId, msgCode, headerParamMap, bodyParamMap, DataEnum
                        .VEHICLE_CAN_DATA);
                break;

            case MsgCode.MSG_RECORDER_DATA_UPLOAD:
                this.addDeviceMsg(msgList, sourceDeviceId, msgCode, headerParamMap, bodyParamMap, DataEnum
                        .VEHICLE_RECODER_DATA);
                break;

            case MsgCode.MSG_WAYBILL_UPLOAD:
                this.addDeviceMsg(msgList, sourceDeviceId, msgCode, headerParamMap, bodyParamMap, DataEnum
                        .VEHICLE_WAYBILL_DATA);
                break;

            case MsgCode.MSG_MULTIMEDIA_SEARCH_RES:
                this.addDeviceMsg(msgList, sourceDeviceId, msgCode, headerParamMap, bodyParamMap, DataEnum
                        .VEHICLE_MULTIMEDIA_SEARCH_RES);
                break;

            case MsgCode.MSG_GZIP_DATA_UPLOAD:
                this.addDeviceMsg(msgList, sourceDeviceId, msgCode, headerParamMap, bodyParamMap, DataEnum
                        .VEHICLE_GZIP_DATA);
                break;

            case MsgCode.MSG_MULTIMEDIA_EVENT_UPLOAD:
                this.addDeviceMsg(msgList, sourceDeviceId, msgCode, headerParamMap, bodyParamMap, DataEnum
                        .VEHICLE_MULTIMEDIA_EVENT);
                break;

            case MsgCode.MSG_UPGRADE_RESULT:
                this.addDeviceMsg(msgList, sourceDeviceId, msgCode, headerParamMap, bodyParamMap, DataEnum
                        .VEHICLE_UPGRADE_RESULT);
                break;

            case MsgCode.MSG_DEV_RSA:
                this.addDeviceMsg(msgList, sourceDeviceId, msgCode, headerParamMap, bodyParamMap, DataEnum
                        .VEHICLE_RSA_DATA);
                break;

            case MsgCode.MSG_DEV_CANCEL:
                this.addDeviceMsg(msgList, sourceDeviceId, msgCode, headerParamMap, bodyParamMap, null);
                break;

            case MsgCode.MSG_TAKE_PHOTO_RES:
                this.addDeviceMsg(msgList, sourceDeviceId, msgCode, headerParamMap, bodyParamMap, null);
                break;

            default:
                break;
        }

        return msgList;
    }

    /**
     * 分包处理，将所有分包缓存起来
     *
     * @param deviceCode
     * @param packetHeader
     */
    private void cacheSubPacket(String deviceCode, String packetId, PacketHeader packetHeader, byte[] messageBody) {
        Integer messageTotalCount = packetHeader.get(MsgParam.MSG_TOTAL_CNT);
        Integer messageIndex = packetHeader.get(MsgParam.MSG_INDEX);
        FragSubPacketCache.cacheSubPacket(deviceCode, packetId, messageTotalCount, messageIndex, messageBody);
    }

    /**
     * 组包，解包
     *
     * @param deviceCode
     * @param msgCode
     * @param total
     * @return
     */
    private AbstractPacket unPackByMsgCode(String deviceCode, String msgCode, Integer total) {
        boolean isAllReceived = FragSubPacketCache.isAllReceived(deviceCode, msgCode, total);
        if (!isAllReceived) {
            return null;
        }

        AbstractPacket bPacket = this.getPacketInstance(msgCode);
        if (bPacket == null) {
            return null;
        }

        // 从缓存中获取设备相应指令的所有分包，组装成消息体
        byte[] bodyBytes = FragSubPacketCache.getWholeMessageBody(deviceCode, msgCode, total);
        bPacket.setMessageBody(bodyBytes);
        bPacket.unpack(bodyBytes);

        // 清空分包缓存
        FragSubPacketCache.clearSubPacketCache(deviceCode, msgCode);

        return bPacket;
    }

    /**
     * 反射实例化对应的数据包类
     *
     * @param msgCode
     * @return
     */
    private AbstractPacket getPacketInstance(String msgCode) {
        AbstractPacket bPacket = null;
        String packageName = "allthings.iot.bb808.das.packet";
        String className = packageName + ".Packet0x" + msgCode;
        try {
            bPacket = (AbstractPacket) Class.forName(className).newInstance();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        if (bPacket == null) {
            return null;
        }

        return bPacket;
    }

    /**
     * 所有Device msg的都必须有5 个基本属性，不可为空，无论是进还是出
     *
     * @param ctx
     * @param msgList
     * @param sourceDeviceId
     */
    private void addDeviceConnectionMsg(ChannelHandlerContext ctx, List<IMsg> msgList, String sourceDeviceId, boolean
            flag) {
        DeviceConnectionMsg dcMsg = new DeviceConnectionMsg();
        dcMsg.setSourceDevice(DeviceTypes.DEVICE_TYPE_BB808, sourceDeviceId);
        // 云平台的deviceType,deviceId 是约定的，使用内置实现
        dcMsg.setTargetDevice(DeviceGuid.getCloudType(), DeviceGuid.getCloudNum());

        dcMsg.setConnected(true);
        dcMsg.setDasNodeId(dasConfig.getDasNodeId());
        dcMsg.setTerminalIp(NettyUtil.getChannelRemoteIP(ctx.channel()));

        msgList.add(dcMsg);
    }

    /**
     * 所有Device msg的都必须有5 个基本属性，不可为空，无论是进还是出
     *
     * @param msgList
     * @param sourceDeviceId
     * @param msgCode
     * @param headerParamMap
     * @param bodyParamMap
     * @param e
     */
    private void addDeviceMsg(List<IMsg> msgList, String sourceDeviceId, String msgCode, Map<String, Object>
            headerParamMap, Map<String, Object> bodyParamMap, DataEnum e) {
        DeviceMsg deviceMsg = DeviceMsg.newMsgToCloud(msgCode);
        deviceMsg.setSourceDevice(DeviceTypes.DEVICE_TYPE_BB808, sourceDeviceId);

        deviceMsg.getParams().putAll(headerParamMap);
        deviceMsg.getParams().putAll(bodyParamMap);
        if (e != null) {
            deviceMsg.setTag(e.name());
        }
        msgList.add(deviceMsg);
    }


    @Override
    public ByteBuffer msgToBuf(IMsg msg) {
        return super.encode(msg);
    }

    @Override
    protected void onEncodeMsg(ByteBuffer buf, IMsg msg) {
        AbstractPacket bPacket = this.getPacketInstance(msg.getMsgCode());
        if (bPacket == null) {
            return;
        }

        //调用pack()方法，将param map封装成帧中的数据体
        bPacket.setParamMap(msg.getParams());
        bPacket.pack();
        if (bPacket.getMessageBody() != null) {
            buf.put(bPacket.getMessageBody());
        }
    }

    /**
     * 处理数据上行透传
     *
     * @param msgList
     * @param sourceDeviceId
     * @param msgCode
     * @param headerParamMap
     * @param bodyParamMap
     */
    private void constructMsgFor0x0900(List<IMsg> msgList, String sourceDeviceId, String msgCode, Map<String, Object>
            headerParamMap, Map<String, Object> bodyParamMap) {
        String packetCode = (String) bodyParamMap.get(MsgParamsSubProtocol.SUB_PROTOCOL_CODE);

        if (StringUtils.isBlank(packetCode)) {
            logger.warn("sub packet code is blank");
            this.addDeviceMsg(msgList, sourceDeviceId, msgCode, headerParamMap, bodyParamMap, null);
            return;
        }

        /**
         * 油量、水量、刷卡、称重、水电，交由上层自定义数据服务处理；
         */
        switch (packetCode) {
            // 由自定义数据服务处理
            case SubProtocolCode.OIL_OR_WATER:
                // 由自定义数据服务处理
            case SubProtocolCode.LIQUID_NAN_CE:
                this.addDeviceMsg(msgList, sourceDeviceId, msgCode, headerParamMap, bodyParamMap, DataEnum
                        .VEHICLE_OIL_WATER);
                break;

            case SubProtocolCode.RFID:
                // 由自定义数据服务处理
                this.addDeviceMsg(msgList, sourceDeviceId, msgCode, headerParamMap, bodyParamMap, DataEnum
                        .VEHICLE_RFID);
                break;

            case SubProtocolCode.WEIGHT:
                // 由自定义数据服务处理
                Boolean passCrc = (Boolean) bodyParamMap.get(MsgParamsWeight.ATTR_CS_WEIGHT_PASS_CRC_CHECK);
                DataEnum e;
                if (passCrc) {
                    e = DataEnum.VEHICLE_WEIGHT;
                } else {
                    e = null;
                }
                this.addDeviceMsg(msgList, sourceDeviceId, msgCode, headerParamMap, bodyParamMap, e);
                break;

            case SubProtocolCode.WEIGHT_HAND:
                // 由自定义数据服务处理
                this.addDeviceMsg(msgList, sourceDeviceId, msgCode, headerParamMap, bodyParamMap, DataEnum
                        .VEHICLE_WEIGHT);
                break;

            case SubProtocolCode.WATER_AND_ELECTRICITY:
                // 构造Device Data Msg
                DeviceDataMsg dataMsg = new DeviceDataMsg();
                dataMsg.setMsgCode(msgCode);
                dataMsg.setSourceDevice(DeviceTypes.DEVICE_TYPE_BB808, sourceDeviceId);
                dataMsg.setTargetDevice(DeviceGuid.getCloudType(), DeviceGuid.getCloudNum());
                dataMsg.setTimestamp(System.currentTimeMillis());
                dataMsg.getParams().putAll(headerParamMap);
                dataMsg.getParams().putAll(bodyParamMap);

                msgList.add(dataMsg);

                break;
            default:
                break;
        }
    }
}

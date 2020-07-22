package allthings.iot.bb809.das;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import allthings.iot.bb809.das.common.RunningNumberFactory;
import allthings.iot.bb809.das.protocol.MsgHeader;
import allthings.iot.bb809.das.protocol.MsgPackage;
import allthings.iot.bb809.das.protocol.MsgPackageBuilder;
import allthings.iot.bb809.das.protocol.MsgType;
import allthings.iot.bb809.das.protocol.UpConnectRsp;
import allthings.iot.common.msg.DeviceConnectionMsg;
import allthings.iot.common.msg.DeviceDataMsg;
import allthings.iot.common.msg.DeviceMsg;
import allthings.iot.common.msg.IMsg;
import allthings.iot.common.pojo.DeviceGuid;
import allthings.iot.common.usual.DeviceTypes;
import allthings.iot.das.common.DasConfig;
import allthings.iot.das.common.NettyUtil;
import allthings.iot.das.simple.ISimpleMsgResolver;
import allthings.iot.das.util.CcittCrc16Util;
import allthings.iot.util.misc.ByteUtils;

/**
 * @author : luhao
 * @FileName : MsgResolver
 * @CreateDate : 2018/3/13
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
public class MsgResolver implements ISimpleMsgResolver {

    @Autowired
    private DasConfig dasConfig;

    private static final int CRC_LENGTH = 2;
    private static final byte[] HEAD = new byte[]{0x5b};
    private static final byte[] END = new byte[]{0x5d};
    private static final int MIN_MESSAGE_LEN = 1 + 4 + 4 + 2 + 4 + 3 + 1 + 4 + 2 + 1;
    private static final int MAX_BUFFER_SIZE = 1024 * 2;


    @Override
    public List<IMsg> bufToMsg(ChannelHandlerContext ctx, ByteBuffer buffer) {
        buffer.order(ByteOrder.BIG_ENDIAN);
        ByteBuf buf = Unpooled.wrappedBuffer(buffer);
        if (buf.capacity() < MIN_MESSAGE_LEN) {
            return null;
        }

        MsgPackage msgPackage = MsgPackageBuilder.build(buffer);
        if (msgPackage == null) {
            return null;
        }

        if (!Arrays.equals(HEAD, msgPackage.getHeadFlag())) {
            return null;
        }

        if (!Arrays.equals(END, msgPackage.getEndFlag())) {
            return null;
        }

        byte[] crcBytes = msgPackage.getCrcBytes();
        if (CcittCrc16Util.calculateCrc16CcittFFFF(crcBytes, crcBytes.length) != msgPackage.getCrcCode()) {
            return null;
        }

        List<IMsg> msgList = Lists.newArrayList();

        MsgHeader msgHeader = msgPackage.getMsgHeader();
        switch (msgHeader.getMsgId()) {
            case MsgType.UP_CONNECT_REQ:
                //构建连接通道，否则通道不通
                addDeviceConnectionMsg(ctx, msgList,
                        String.valueOf(msgPackage.getMsgHeader().getMsgGnssCenterId()), String.valueOf(msgHeader
                                .getMsgId()));

                //保存日志，为了后续响应
                DeviceDataMsg loginDataMsg = DeviceDataMsg.newMsgToCloud(String.valueOf(msgHeader.getMsgId()));
                loginDataMsg.setSourceDevice(DeviceTypes.DEVICE_TYPE_BB809, String.valueOf(msgPackage.getMsgHeader()
                        .getMsgGnssCenterId()));

                loginDataMsg.setParams(msgPackage.getMsgBody());
                loginDataMsg.getParams().putAll(msgHeader.toMap());

                msgList.add(loginDataMsg);

                break;
            case MsgType.UP_DISCONNECT_REQ:
                DeviceDataMsg logoutDataMsg = DeviceDataMsg.newMsgToCloud(String.valueOf(msgHeader.getMsgId()));
                logoutDataMsg.setSourceDevice(DeviceTypes.DEVICE_TYPE_BB809,
                                              String.valueOf(msgPackage.getMsgHeader().getMsgGnssCenterId()));

                logoutDataMsg.setParams(msgHeader.toMap());
                logoutDataMsg.getParams().putAll(msgPackage.getMsgBody());

                msgList.add(logoutDataMsg);
                break;
            case MsgType.UP_EXG_MSG:
                DeviceMsg deviceDataMsg = DeviceMsg.newMsgToCloud(String.valueOf(msgHeader.getMsgId()));
                deviceDataMsg.setSourceDevice(DeviceTypes.DEVICE_TYPE_BB809, msgPackage.getDeviceId());
                deviceDataMsg.getParams().putAll(msgHeader.toMap());
                deviceDataMsg.getParams().putAll(msgPackage.getMsgBody());
                msgList.add(deviceDataMsg);
                break;
            default:
                break;
        }

        return msgList;
    }


    /**
     * 所有Device msg的都必须有5 个基本属性，不可为空，无论是进还是出
     *
     * @param ctx
     * @param msgList
     * @param sourceDeviceId
     */
    private void addDeviceConnectionMsg(ChannelHandlerContext ctx, List<IMsg> msgList, String sourceDeviceId, String
            msgCode) {
        DeviceConnectionMsg dcMsg = new DeviceConnectionMsg();
        dcMsg.setSourceDevice(DeviceTypes.DEVICE_TYPE_BB809, sourceDeviceId);
        // 云平台的deviceType,deviceId 是约定的，使用内置实现
        dcMsg.setTargetDevice(DeviceGuid.getCloudType(), DeviceGuid.getCloudNum());
        dcMsg.setMsgCode(msgCode);
        dcMsg.setConnected(true);
        dcMsg.setDasNodeId(dasConfig.getDasNodeId());
        dcMsg.setTerminalIp(NettyUtil.getChannelRemoteIP(ctx.channel()));

        msgList.add(dcMsg);
    }


    @Override
    public ByteBuffer msgToBuf(IMsg msg) {
        byte[] bodyBytes = getContent(msg.getMsgCode(), msg.get("msgGnssCenterId"));
        bodyBytes = this.encodeBuf(bodyBytes);// 转义
        //帧长
        int frameLen = MIN_MESSAGE_LEN + bodyBytes.length;

        //返回的buffer数据
        ByteBuffer buffer = ByteBuffer.allocate(frameLen).order(ByteOrder.BIG_ENDIAN);
        //crc校验的数据
        ByteBuffer crcBuffer = ByteBuffer.allocate(frameLen - HEAD.length - CRC_LENGTH
                                                   - END.length).order(ByteOrder.BIG_ENDIAN);

        // 消息头
        buffer.put(HEAD);

        //header的buffer
        ByteBuffer header = ByteBuffer.allocate(frameLen - HEAD.length - CRC_LENGTH - END.length
                                                - bodyBytes.length).order(ByteOrder.BIG_ENDIAN);
        // 标识位
        header.put(ByteUtils.getBytes(frameLen, ByteOrder.BIG_ENDIAN));
        header.put(ByteUtils.getBytes(RunningNumberFactory.getInstance().getRunningNumber(), ByteOrder.BIG_ENDIAN));
        header.put(ByteUtils.hexStringToBytes(msg.getMsgCode()));
        header.put(ByteUtils.getBytes((Integer) msg.get("msgGnssCenterId"), ByteOrder.BIG_ENDIAN));
        String versionFlag = msg.get("versionFlag");
        String[] versionFlags = versionFlag.split("[.]");
        header.put(Byte.parseByte(versionFlags[0]));
        header.put(Byte.parseByte(versionFlags[1]));
        header.put(Byte.parseByte(versionFlags[2]));
        // header.put((byte[]) msg.get("versionFlag"));
        header.put((byte) msg.get("encryptFlag"));
        header.put(ByteUtils.getBytes((Integer) msg.get("encryptKey"), ByteOrder.BIG_ENDIAN));

        buffer.put(header.array());
        crcBuffer.put(header.array());

        // 数据体
        buffer.put(bodyBytes);
        crcBuffer.put(bodyBytes);

        crcBuffer.flip();

        byte[] crcBytes = crcBuffer.array();

        // 校验码
        short crcCode = CcittCrc16Util.calculateCrc16CcittFFFF(crcBytes, crcBytes.length);
        buffer.put(ByteUtils.getBytes(crcCode, ByteOrder.BIG_ENDIAN));

        // 标识位
        buffer.put(END);

        return buffer;
    }

    /**
     * 返回给下级平台的消息内容
     *
     * @param msgCode
     * @return
     */
    private byte[] getContent(String msgCode, int verifyCode) {
        ByteBuffer buffer = ByteBuffer.allocate(0);
        switch (msgCode) {
            case MsgType.UP_CONNECT_RSP:
                buffer = ByteBuffer.allocate(5).order(ByteOrder.BIG_ENDIAN);
                UpConnectRsp upConnectRsp = new UpConnectRsp();
                upConnectRsp.setResult(UpConnectRsp.RESULT_SUCCESS);
                upConnectRsp.setVerifyCode(verifyCode);
                buffer.put(upConnectRsp.pack());
                break;
            case MsgType.UP_DISCONNECT_RSP:
                break;
            case MsgType.UP_EXG_MSG:
                break;
            default:
                break;
        }

        return buffer.array();
    }

    private byte[] encodeBuf(byte[] sourceBytes) {
        ByteBuf resultBuf = Unpooled.buffer(sourceBytes.length * 2);
        for (int i = 0; i < sourceBytes.length; i++) {
            if (sourceBytes[i] == (byte) 0x5b) {
                resultBuf.writeByte((byte) 0x5a);
                resultBuf.writeByte((byte) 0x01);
                continue;
            }
            if (sourceBytes[i] == (byte) 0x5a) {
                resultBuf.writeByte((byte) 0x5a);
                resultBuf.writeByte((byte) 0x02);
                continue;
            }
            if (sourceBytes[i] == (byte) 0x5d) {
                resultBuf.writeByte((byte) 0x5e);
                resultBuf.writeByte((byte) 0x01);
                continue;
            }
            if (sourceBytes[i] == (byte) 0x5e) {
                resultBuf.writeByte((byte) 0x5e);
                resultBuf.writeByte((byte) 0x02);
                continue;
            }
            resultBuf.writeByte(sourceBytes[i]);
        }
        byte[] resultBytes = new byte[resultBuf.readableBytes()];
        resultBuf.readBytes(resultBytes);
        return resultBytes;
    }

}

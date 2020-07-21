package tf56.cloud.iot.store.dustbin.das;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import io.netty.channel.ChannelHandlerContext;
import tf56.cloud.iot.store.dustbin.common.MsgCodes;
import tf56.cloud.iot.store.dustbin.common.MsgParams;
import allthings.iot.common.msg.*;
import allthings.iot.common.pojo.DeviceGuid;
import allthings.iot.das.common.DasConfig;
import allthings.iot.das.simple.ISimpleMsgResolver;
import allthings.iot.das.util.DateUtil;
import allthings.iot.util.misc.ByteUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.ByteBuffer;
import java.util.List;

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
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
@Component
public class MsgResolver extends AbstractFrameCodec implements ISimpleMsgResolver {

    @Autowired
    private DasConfig dasConfig;

    @Override
    public List<IMsg> bufToMsg(ChannelHandlerContext ctx, ByteBuffer buf) {

        IMsg msg = decode(buf);
        if (msg == null || msg.getSourceDeviceId() == null) {
            return null;
        }

        DeviceConnectionMsg dcMsg = new DeviceConnectionMsg();
        dcMsg.setSourceDevice(msg.getSourceDeviceType(), msg.getSourceDeviceId());
        // 云平台的deviceType,deviceId 是约定的，使用内置实现
        dcMsg.setTargetDevice(DeviceGuid.getCloudType(), DeviceGuid.getCloudNum());
        dcMsg.setTag(msg.getMsgCode());
        dcMsg.setConnected(true);
        dcMsg.setDasNodeId(dasConfig.getDasNodeId());

        List<IMsg> msgList = Lists.newArrayList();
        msgList.add(dcMsg);
        msgList.add(msg);

        return msgList;
    }

    @Override
    public ByteBuffer msgToBuf(IMsg msg) {
        return encode(msg);
    }

    @Override
    protected void onEncodeMsg(ByteBuffer buf, IMsg msg) {
        byte rc = msg.get(MsgParams.RC) == null ? 0 : Byte.valueOf(msg.get(MsgParams.RC).toString());
        buf.put(rc);

        int cmdId = Integer.parseInt(msg.getMsgCode());
        switch (cmdId) {
            case MsgCodes.SYNC_TIME_RES:
                long mills = msg.get(MsgParams.RTCTIME) == null ? 0 : Long.parseLong(msg.get(MsgParams.RTCTIME)
                        .toString());
                buf.put(DateUtil.millisecond2Bytes(mills));
                break;
            case MsgCodes.REPORT_STATUS_RES:
                break;
            case MsgCodes.REPORT_PARAMS_RES:

                int limit1 = msg.get(MsgParams.LIMIT1) == null ? 0 : Integer.parseInt(msg.get(MsgParams.LIMIT1)
                        .toString());
                int limit2 = msg.get(MsgParams.LIMIT2) == null ? 0 : Integer.parseInt(msg.get(MsgParams.LIMIT2)
                        .toString());
                int reportIndterval = msg.get(MsgParams.REPORTINTERVAL) == null ? 0 : Integer.parseInt(msg.get
                        (MsgParams.REPORTINTERVAL).toString());
                String connectStr = msg.get(MsgParams.CONNECTSTRING) == null ? null : msg.get(MsgParams
                        .CONNECTSTRING).toString();
                int connectStrLen = Strings.isNullOrEmpty(connectStr) ? 0 : connectStr.length();

                buf.put((byte) limit1);
                buf.put((byte) limit2);
                buf.put((byte) reportIndterval);
                buf.put((byte) connectStrLen);
                if (connectStrLen > 0) {
                    buf.put(connectStr.getBytes());
                }

                break;
            case MsgCodes.REPORT_ALARM_RES:
                break;
            default:
                break;
        }

    }

    @Override
    protected IMsg onDecodeMsg(MsgWrap wrap) {
        int cmdId = wrap.cmdId;
        String msgCode = String.valueOf(cmdId);
        AbstractDeviceMsg msg = new DeviceMsg();

        byte[] tmp;
        ByteBuffer buf = wrap.content;

        switch (cmdId) {
            case MsgCodes.SYNC_TIME:
                break;
            case MsgCodes.REPORT_STATUS:
                DeviceDataMsg dataMsg = new DeviceDataMsg();
                dataMsg.setTimestamp(wrap.timestamp);

                dataMsg.put(MsgParams.REMAIN1, ByteUtils.toInt(buf.get()));
                dataMsg.put(MsgParams.REMAIN2, ByteUtils.toInt(buf.get()));
                dataMsg.put(MsgParams.REMAIN3, ByteUtils.toInt(buf.get()));
                dataMsg.put(MsgParams.TILT, ByteUtils.toInt(buf.get()));
                dataMsg.put(MsgParams.BATTERY, buf.getShort());
                dataMsg.put(MsgParams.TEMPERATUE, buf.getShort() / 10F);

                msg = dataMsg;
                break;
            case MsgCodes.REPORT_PARAMS:
                msg.put(MsgParams.LIMIT1, ByteUtils.toInt(buf.get()));
                msg.put(MsgParams.LIMIT2, ByteUtils.toInt(buf.get()));
                msg.put(MsgParams.REPORTINTERVAL, ByteUtils.toInt(buf.get()));
                int connectStringLen = ByteUtils.toInt(buf.get());
                tmp = new byte[connectStringLen];
                buf.get(tmp);
                msg.put(MsgParams.CONNECTSTRING, new String(tmp));
                break;
            case MsgCodes.REPORT_ALARM:
                msg.put(MsgParams.ALARMMOVE, buf.get() != 0x00);
                msg.put(MsgParams.ALARMERROR1, buf.get() != 0x00);
                msg.put(MsgParams.ALARMERROR2, buf.get() != 0x00);
                msg.put(MsgParams.ALARMERROR3, buf.get() != 0x00);
                break;
            default:
                break;
        }

        msg.setMsgCode(msgCode);
        msg.setOccurTime(wrap.timestamp);
        msg.setSourceDeviceType(wrap.deviceType);
        msg.setSourceDeviceId(wrap.deviceId);
        msg.setTargetDeviceType(DeviceGuid.getCloudType());
        msg.setTargetDeviceId(DeviceGuid.getCloudNum());

        return msg;
    }
}

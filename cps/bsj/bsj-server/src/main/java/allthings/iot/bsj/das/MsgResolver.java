package allthings.iot.bsj.das;

import com.google.common.collect.Lists;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import allthings.iot.common.Constant;
import allthings.iot.bsj.common.MsgCode;
import allthings.iot.bsj.das.packet.AbstractPacket;
import allthings.iot.common.msg.AbstractDeviceMsg;
import allthings.iot.common.msg.DeviceConnectionMsg;
import allthings.iot.common.msg.DeviceMsg;
import allthings.iot.common.msg.IMsg;
import allthings.iot.common.pojo.DeviceGuid;
import allthings.iot.common.usual.DeviceTypes;
import allthings.iot.das.common.DasConfig;
import allthings.iot.das.common.NettyUtil;
import allthings.iot.das.common.bean.ChannelCache;
import allthings.iot.das.simple.ISimpleMsgResolver;
import allthings.iot.util.misc.ReflectUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;

/**
 * @author :  luhao
 * @FileName :  MsgResolver
 * @CreateDate :  2017/12/29
 * @Description : 消息内容编解码
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

    private static final Logger LOGGER = LoggerFactory.getLogger(MsgResolver.class);

    @Autowired
    private DasConfig dasConfig;

    @Autowired
    private ChannelCache channelCache;

    @Override
    public List<IMsg> bufToMsg(ChannelHandlerContext ctx, ByteBuffer buf) {
        try {
            return decode(ctx, buf);
        } catch (Exception e) {
            LOGGER.error("解码异常，{}", e);
        }
        return null;
    }

    @Override
    public ByteBuffer msgToBuf(IMsg msg) {
        try {
            AbstractPacket packet = ReflectUtils.getInstance(msg.getMsgCode(), "Packet0x", AbstractPacket.class);
            packet.put(Constant.ATTR_DEVICE_ID, msg.getTargetDeviceId());
            packet.getParamMap().putAll(msg.getParams());

            byte[] respBytes = packet.pack();
            ByteBuffer buffer = ByteBuffer.allocate(respBytes.length).order(ByteOrder.BIG_ENDIAN);
            buffer.put(respBytes);

            return buffer;
        } catch (Exception e) {
            LOGGER.error("编码异常，{}", e);
        }

        return null;
    }

    @Override
    public List<IMsg> onDecodeMsg(ChannelHandlerContext ctx, MsgWrap wrap) throws Exception {
        ByteBuffer body = wrap.getContent();
        byte[] bodyBytes = new byte[body.remaining()];
        body.get(bodyBytes, 0, bodyBytes.length);
        AbstractPacket packet;
        try {
            packet = ReflectUtils.getInstance(wrap.getMsgCode(), "Packet0x", AbstractPacket.class);
            //解码
            packet.unpack(bodyBytes);
            return unPackByMsgCode(ctx, packet, wrap);
        } catch (Exception ee) {
            LOGGER.error("指令异常，{}", ee);
            return Lists.newArrayList();
        }
    }


    /**
     * 按msgCode组织Imsg
     *
     * @param ctx
     * @param packet
     * @param wrap
     * @return
     */
    private List<IMsg> unPackByMsgCode(ChannelHandlerContext ctx, AbstractPacket packet, MsgWrap wrap) {

        List<IMsg> msgList = Lists.newArrayList();

        AbstractDeviceMsg msg = DeviceMsg.newMsgToCloud(wrap.getMsgCode());
        msg.setSourceDeviceId(wrap.getDeviceId());
        msg.setSourceDeviceType(DeviceTypes.DEVICE_TYPE_BSJ);
        msg.setParams(packet.getParamMap());
        msg.put(Constant.ATTR_REP_CRC, wrap.getCrc());
        msg.put(Constant.ATTR_CHILD_MSG_CODE, wrap.getChildMsgCode());
        msg.setTag(wrap.getMsgCode());

        if (MsgCode.MSG_CODE_POSITION.equals(wrap.getMsgCode())) {
            //如果需要特殊处理，则做相关处理
        }

        //加连接信息
        Channel channel = channelCache.get(wrap.getDeviceId());
        if (channel == null) {
            channel = channelCache.get(DeviceTypes.DEVICE_TYPE_BSJ + wrap.getDeviceId());
        }

        //通道关闭了
        if (channel == null || !channel.isActive()) {
            msgList.add(addDeviceConnectionMsg(ctx, wrap.getDeviceId()));
        }

        msgList.add(msg);

        return msgList;
    }

    /**
     * 增加 device connect msg
     *
     * @param ctx
     * @param deviceId
     * @return
     */
    private AbstractDeviceMsg addDeviceConnectionMsg(ChannelHandlerContext ctx, String deviceId) {
        DeviceConnectionMsg dcMsg = new DeviceConnectionMsg();
        dcMsg.setSourceDevice(DeviceTypes.DEVICE_TYPE_BSJ, deviceId);
        // 云平台的deviceType,deviceId 是约定的，使用内置实现
        dcMsg.setTargetDevice(DeviceGuid.getCloudType(), DeviceGuid.getCloudNum());

        dcMsg.setConnected(true);
        dcMsg.setDasNodeId(dasConfig.getDasNodeId());
        try {
            dcMsg.setTerminalIp(NettyUtil.getChannelRemoteIP(ctx.channel()));
        } catch (Exception e) {
            LOGGER.error("终端ip获取失败，{}", e.getMessage());
        }

        return dcMsg;
    }
}

package allthings.iot.gbt32960.das;

import com.google.common.collect.Lists;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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
import allthings.iot.gbt32960.das.common.Gbt32960EncryptType;
import allthings.iot.gbt32960.das.packet.AbstractPacket;
import allthings.iot.util.misc.Aes128Util;
import allthings.iot.util.misc.RSAUtil;
import allthings.iot.util.misc.ReflectUtils;

import javax.annotation.PostConstruct;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.interfaces.RSAPrivateKey;
import java.util.List;

/**
 * @author :  luhao
 * @FileName :  MsgResolver
 * @CreateDate :  2018/01/29
 * @Description : 消息内容编解码
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
public class MsgResolver extends AbstractFrameCodec implements ISimpleMsgResolver {

    private static final Logger LOGGER = LoggerFactory.getLogger(MsgResolver.class);

    @Autowired
    private DasConfig dasConfig;

    @Autowired
    private ChannelCache channelCache;
    /**
     * RSA 私钥
     */
    private RSAPrivateKey rsaPrivateKey;

    @PostConstruct
    public void init() {
        //rsa私钥
        rsaPrivateKey = RSAUtil.getPrivateKey("", "");
    }

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
            //组织返回包
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
        byte[] decryptBodyBytes = decryptData(wrap);

        try {
            //根据msgCode获取实例
            AbstractPacket packet = ReflectUtils.getInstance(wrap.getMsgCode(), "Packet0x", AbstractPacket.class);
            //解码
            packet.unpack(decryptBodyBytes);
            //需要根据msgCode进行处理
            return unPackByMsgCode(ctx, packet, wrap);
        } catch (Exception ee) {
            LOGGER.error("指令异常，{}", ee);
            return Lists.newArrayList();
        }
    }

    /**
     * 报文解密
     *
     * @param wrap
     * @return
     * @throws Exception
     */
    private byte[] decryptData(MsgWrap wrap) throws Exception {
        byte encryptType = wrap.getEncryptType();

        byte[] contentBytes = wrap.getContent().array();
        if (encryptType == Gbt32960EncryptType.RSA_ENCRYPTION) {
            //ras加密，对数据进行解密
            return RSAUtil.decryptByPrivateKey(contentBytes, rsaPrivateKey);
        } else if (encryptType == Gbt32960EncryptType.AES128_ENCRYPTION) {
            //AES128加密，对数据进行解密
            return Aes128Util.decrypt(contentBytes, "");
        } else if (encryptType == Gbt32960EncryptType.NOT_ENCRYPTION) {
            //没有加密
            return contentBytes;
        } else if (encryptType == Gbt32960EncryptType.EXCEPTION_ENCRYPTION) {
            //加密异常
            throw new RuntimeException("加密异常");
        } else if (encryptType == Gbt32960EncryptType.INVALID_ENCRYPTION) {
            //加密无效
            throw new RuntimeException("加密无效");
        }

        return null;
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
        msg.setSourceDeviceType(DeviceTypes.DEVICE_TYPE_GBT32960);
        msg.setParams(packet.getParamMap());
        msg.setTag(wrap.getMsgCode());

        //加连接信息
        Channel channel = channelCache.get(wrap.getDeviceId());
        if (channel == null) {
            channel = channelCache.get(DeviceTypes.DEVICE_TYPE_GBT32960 + wrap.getDeviceId());
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
        dcMsg.setSourceDevice(DeviceTypes.DEVICE_TYPE_GBT32960, deviceId);
        // 云平台的deviceType,deviceId 是约定的，使用内置实现
        dcMsg.setTargetDevice(DeviceGuid.getCloudType(), DeviceGuid.getCloudNum());

        dcMsg.setConnected(true);
        dcMsg.setDasNodeId(dasConfig.getDasNodeId());

        try {
            //模拟终端可能无法获取到ip
            dcMsg.setTerminalIp(NettyUtil.getChannelRemoteIP(ctx.channel()));
        } catch (Exception e) {
            LOGGER.error("终端ip获取失败，{}", e.getMessage());
        }

        return dcMsg;
    }
}

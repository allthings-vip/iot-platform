package allthings.iot.bb808.das.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import allthings.iot.bb808.common.MsgParam;
import allthings.iot.bb808.common.MsgParamsSubProtocol;

/**
 * @author :  luhao
 * @FileName :  Packet0x0900
 * @CreateDate :  2017/12/21
 * @Description : 数据上行透传
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) allthings-vip All Rights Reserved
 * *******************************************************************************************
 */
public class Packet0x0900 extends AbstractPacket {

    private static final Logger logger = LoggerFactory.getLogger(Packet0x0900.class);
    private AbstractPacket packet;
    public Packet0x0900() {
        super("0900");
    }

    @Override
    public void unpack(byte[] bytes) {
        ByteBuf buf = Unpooled.wrappedBuffer(bytes);

        super.put(MsgParamsSubProtocol.INTERFACE_ID, buf.readUnsignedByte());

        byte[] content = new byte[buf.readableBytes()];
        buf.readBytes(content);
        super.put(MsgParam.TRANSPARENT_TRANSMISSION_CONTENT, content);

        // 透传数据的二次解析
        this.unpackOneByOne(content);
    }

    /**
     * 尝试匹配子协议
     *
     * @param bytes
     */
    private void unpackOneByOne(byte[] bytes) {
        String packetCode = super.get(MsgParamsSubProtocol.SUB_PROTOCOL_CODE);
        unpackForSubProtocol(packetCode, bytes);
    }

    private void unpackForSubProtocol(String subProtocolCode, byte[] bytes) {
        String packageName = getPackageName(subProtocolCode);
        if(StringUtils.isBlank(packageName)) {
            return;
        }

        String className = packageName + ".Packet" + subProtocolCode;
        try {
            packet = (AbstractPacket) Class.forName(className).newInstance();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return;
        }

        packet.unpack(bytes);
        super.getParamMap().putAll(packet.getParamMap());
    }

    private String getPackageName(String subPacketCode) {
        String packageName = null;
        return packageName;
    }
}

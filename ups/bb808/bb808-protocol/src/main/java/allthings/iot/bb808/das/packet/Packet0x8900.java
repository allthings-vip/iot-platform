package allthings.iot.bb808.das.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import allthings.iot.bb808.common.MsgParam;
import allthings.iot.bb808.common.MsgParamsSubProtocol;
import allthings.iot.util.misc.ByteUtils;
import allthings.iot.util.misc.StringUtils;

import java.io.IOException;

/**
 * @author :  luhao
 * @FileName :  Packet0x8900
 * @CreateDate :  2017/12/21
 * @Description :  数据下行透传
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
public class Packet0x8900 extends AbstractPacket {
    private static final Logger logger = LoggerFactory.getLogger(Packet0x8900.class);

    public Packet0x8900() {
        super("8900");
    }

    public void unpack() throws IOException {
    }

    @Override
    public byte[] pack() {
        ByteBuf buf = Unpooled.buffer();

        Number type = super.get(MsgParamsSubProtocol.INTERFACE_ID);
        buf.writeByte(type.intValue());

        byte[] dataBytes = new byte[0];
        Object data = super.get(MsgParam.TRANSPARENT_TRANSMISSION_CONTENT);
        if (data != null) {
            dataBytes = data.toString().getBytes(ByteUtils.CHARSET_UTF8);
        } else {
            dataBytes = getDataByPacketCode();
        }
        buf.writeBytes(dataBytes);

        // 取出消息体
        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        super.setMessageBody(bytes);
        return bytes;
    }

    private byte[] getDataByPacketCode() {
        /*
         数据部分。
         服务器下发数据到某个串口。只修改波特率不发数据时，此字段为空（字节数为0）
          */
        String packetCode = super.get(MsgParamsSubProtocol.SUB_PROTOCOL_CODE);
        if (StringUtils.isBlank(packetCode)) {
            return ByteUtils.EMPTY_BYTE;
        }

        //@TODO
        String packageName = "";//getPackageName(packetCode);
        if (StringUtils.isBlank(packageName)) {
            return ByteUtils.EMPTY_BYTE;
        }

        String className = packageName + ".Packet" + packetCode;
        AbstractPacket packet = null;
        try {
            packet = (AbstractPacket) Class.forName(className).newInstance();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ByteUtils.EMPTY_BYTE;
        }

        packet.setParamMap(super.getParamMap());
        return packet.pack();
    }

  /*  private String getPackageName(String subPacketCode) {
        String packageName = null;
        switch (subPacketCode) {
            case SubProtocolCode.WEIGHT:
            case SubProtocolCode.WEIGHT_HAND:
            case SubProtocolCode.RFID:
            case SubProtocolCode.WATER_AND_ELECTRICITY:
            case SubProtocolCode.OIL_OR_WATER:
            case SubProtocolCode.LIQUID_NAN_CE:
                packageName = "com.vortex.vehicle.das.packet";
                break;
            default:
                packageName = null;
        }

        return packageName;
    }*/
}

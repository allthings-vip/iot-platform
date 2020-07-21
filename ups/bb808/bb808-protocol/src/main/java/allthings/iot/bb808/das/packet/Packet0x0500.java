package allthings.iot.bb808.das.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import allthings.iot.bb808.common.MsgParam;
import allthings.iot.constant.gps.GpsMsgParam;

import java.util.List;
import java.util.Map;

/**
 * @author :  luhao
 * @FileName :  Packet0x0500
 * @CreateDate :  2017/12/21
 * @Description : 车辆控制应答
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
public class Packet0x0500 extends AbstractPacket {
    public Packet0x0500() {
        super("0500");
    }

    @Override
    public void unpack(byte[] bytes) {
        ByteBuf buf = Unpooled.wrappedBuffer(bytes);

        super.put(MsgParam.ACK_RUNNING_NO, buf.readUnsignedShort());

        byte[] tempByte = new byte[buf.readableBytes()];
        buf.readBytes(tempByte);

        //位置信息
        Packet0x0200 packet0x0200 = new Packet0x0200();
        packet0x0200.setMessageBody(tempByte);
        packet0x0200.unpack(tempByte);
        List<Map<String, Object>> gpsParamMapList = (List<Map<String, Object>>) (packet0x0200.getParamMap().get
                (GpsMsgParam.DATA_CONTENT));
        super.put(MsgParam.DOOR_LOCK, gpsParamMapList.get(0).get(GpsMsgParam.DOOR_LOCK));
    }
}

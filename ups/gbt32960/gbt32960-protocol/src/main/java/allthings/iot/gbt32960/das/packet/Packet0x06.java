package allthings.iot.gbt32960.das.packet;

import java.nio.ByteOrder;
import java.util.Map;

import io.netty.buffer.ByteBuf;
import allthings.iot.constant.vehicle.VehicleMsgParam;
import allthings.iot.util.misc.ByteUtils;

/**
 * @author : luhao
 * @FileName : Packet0x06
 * @CreateDate : 2018/1/31
 * @Description :平台登出
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) allthings-vip All Rights Reserved
 * *******************************************************************************************
 */
public class Packet0x06 extends AbstractPacket {
    public Packet0x06() {
        super("0x06");
    }

    @Override
    public void unpack(ByteBuf byteBuf) throws Exception {
        byte[] tempBytes = new byte[2];
        byteBuf.readBytes(tempBytes);
        // 登出流水号
        int serialumber = ByteUtils.toInt16(tempBytes, 0, ByteOrder.BIG_ENDIAN);
        this.put(VehicleMsgParam.SERIAL_NUMBER, serialumber);
    }

    /*
     * (non-Javadoc)
     * @see allthings.iot.gbt32960.das.packet.AbstractPacket#packBody(java.util.Map)
     */
    @Override
    protected byte[] packBody(Map<String, Object> params) {
        return new byte[0];
    }
}

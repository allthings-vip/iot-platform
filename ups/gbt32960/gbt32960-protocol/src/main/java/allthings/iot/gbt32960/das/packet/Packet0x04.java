package allthings.iot.gbt32960.das.packet;

import java.nio.ByteOrder;
import java.util.Map;

import io.netty.buffer.ByteBuf;
import allthings.iot.constant.vehicle.VehicleMsgParam;
import allthings.iot.util.misc.ByteUtils;

/**
 * @author : luhao
 * @FileName : Packet0x04
 * @CreateDate : 2018/1/31
 * @Description :车辆登出
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) allthings-vip All Rights Reserved
 * *******************************************************************************************
 */
public class Packet0x04 extends AbstractPacket {
    public Packet0x04() {
        super("0x04");
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
        // TODO Auto-generated method stub
        return new byte[0];
    }
}

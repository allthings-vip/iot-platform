package allthings.iot.gbt32960.das.packet;

import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.Map;

import io.netty.buffer.ByteBuf;
import allthings.iot.constant.vehicle.VehicleMsgParam;
import allthings.iot.util.misc.ByteUtils;

/**
 * @author : luhao
 * @FileName : Packet0x05
 * @CreateDate : 2018/1/31
 * @Description :平台登入
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) allthings-vip All Rights Reserved
 * *******************************************************************************************
 */
public class Packet0x05 extends AbstractPacket {
    public Packet0x05() {
        super("0x05");
    }

    @Override
    public void unpack(ByteBuf byteBuf) throws Exception {
        byte[] tempBytes = new byte[2];
        byteBuf.readBytes(tempBytes);
        // 登录流水号
        int serialumber = ByteUtils.toInt16(tempBytes, 0, ByteOrder.BIG_ENDIAN);

        tempBytes = new byte[12];
        byteBuf.readBytes(tempBytes);
        // 平台用户名
        String username = new String(tempBytes, Charset.forName("UTF-8")).trim();
        tempBytes = new byte[20];
        byteBuf.readBytes(tempBytes);
        // 平台用户名
        String password = new String(tempBytes, Charset.forName("UTF-8")).trim();

        // 加密规则
        byte encryptionRule = byteBuf.readByte();

        this.put(VehicleMsgParam.SERIAL_NUMBER, serialumber);
        this.put(VehicleMsgParam.USERNAME, username);
        this.put(VehicleMsgParam.PASSWORD, password);
        this.put(VehicleMsgParam.ENCRYPTION_RULE, encryptionRule);
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

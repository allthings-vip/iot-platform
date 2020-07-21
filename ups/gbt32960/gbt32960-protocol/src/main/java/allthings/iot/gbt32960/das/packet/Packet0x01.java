package allthings.iot.gbt32960.das.packet;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.Map;

import io.netty.buffer.ByteBuf;
import allthings.iot.constant.vehicle.VehicleMsgParam;
import allthings.iot.util.misc.ByteUtils;

/**
 * @author : luhao
 * @FileName : Packet0x01
 * @CreateDate : 2018/1/31
 * @Description :车辆登入
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
public class Packet0x01 extends AbstractPacket {

    public Packet0x01(){
        super("0x01");
    }

    @Override
    public void unpack(ByteBuf byteBuf) throws Exception {
        byte[] tempBytes = new byte[2];
        byteBuf.readBytes(tempBytes);
        // 登录流水号
        int serialumber = ByteUtils.toInt16(tempBytes, 0, ByteOrder.BIG_ENDIAN);

        tempBytes = new byte[20];
        byteBuf.readBytes(tempBytes);
        // ICCID
        String iccid = new String(tempBytes, Charset.forName("UTF-8"));
        // 可充电储能子系统数
        short electricContainerSubSysCount = byteBuf.readUnsignedByte();
        // 可充电储能系统编码长度
        short electricContainerSysCodeLength = byteBuf.readUnsignedByte();

        // 可充电储能系统编码
        tempBytes = new byte[electricContainerSubSysCount * electricContainerSysCodeLength];
        byteBuf.readBytes(tempBytes);
        String electricContainerSysCode = new String(tempBytes, Charset.forName("UTF-8"));

        this.put(VehicleMsgParam.SERIAL_NUMBER, serialumber);
        this.put(VehicleMsgParam.ICCID, iccid);
        this.put(VehicleMsgParam.ELECTRIC_CONTAINER_SUB_SYS_COUNT, electricContainerSubSysCount);
        this.put(VehicleMsgParam.ELECTRIC_CONTAINER_SYS_CODE_LENGTH, electricContainerSysCodeLength);
        this.put(VehicleMsgParam.ELECTRIC_CONTAINER_SYS_CODE, electricContainerSysCode);
    }

    /*
     * (non-Javadoc)
     * @see allthings.iot.gbt32960.das.packet.AbstractPacket#packBody(java.util.Map)
     */
    @Override
    protected byte[] packBody(Map<String, Object> params) {
        ByteBuffer buffer = ByteBuffer.allocate(1024).order(ByteOrder.BIG_ENDIAN);
        // int serialumber = ByteUtils.getBytes(value, ByteOrder.BIG_ENDIAN);
        // buffer.put(ByteUtils.toInt16(serialumber, 0, ByteOrder.BIG_ENDIAN));
        return new byte[0];
    }
}

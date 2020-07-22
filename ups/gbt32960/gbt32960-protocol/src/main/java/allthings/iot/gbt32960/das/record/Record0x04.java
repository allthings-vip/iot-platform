package allthings.iot.gbt32960.das.record;

import java.math.RoundingMode;

import io.netty.buffer.ByteBuf;
import allthings.iot.constant.vehicle.VehicleMsgParam;
import allthings.iot.gbt32960.das.common.BigDecimalUtils;

/**
 * @author :  luhao
 * @FileName :  Record0x04
 * @CreateDate :  2018/2/9
 * @Description : 发动机数据
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) allthings-vip All Rights Reserved
 * *******************************************************************************************
 */
public class Record0x04 extends AbstractRecord {
    public Record0x04() {
        super("0x04");
    }

    @Override
    public void unpack(ByteBuf byteBuf) throws Exception {
        //发动机状态
        short engineStatus = byteBuf.readUnsignedByte();
        //曲轴转速
        int crankshaftSpeed = byteBuf.readUnsignedShort();
        //燃料消耗率
        double fuelConsumptionRate = byteBuf.readUnsignedShort();
        if (fuelConsumptionRate <= 60000) {
            fuelConsumptionRate = BigDecimalUtils.divide(fuelConsumptionRate, 100D, 2, RoundingMode.DOWN);
        }

        this.put(VehicleMsgParam.ENGINE_STATUS, engineStatus);
        this.put(VehicleMsgParam.CRANKSHAFT_SPEED, crankshaftSpeed);
        this.put(VehicleMsgParam.FUEL_CONSUMPTION_RATE, fuelConsumptionRate);
    }
}

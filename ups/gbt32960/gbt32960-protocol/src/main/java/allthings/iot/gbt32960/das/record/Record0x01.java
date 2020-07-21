package allthings.iot.gbt32960.das.record;

import java.math.RoundingMode;

import io.netty.buffer.ByteBuf;
import allthings.iot.constant.vehicle.VehicleMsgParam;
import allthings.iot.gbt32960.das.ContextConfig;
import allthings.iot.gbt32960.das.common.BigDecimalUtils;

/**
 * @author :  luhao
 * @FileName :  Record0x01
 * @CreateDate :  2018/2/9
 * @Description : 整车数据
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
public class Record0x01 extends AbstractRecord {
    public Record0x01() {
        super("0x01");
    }

    @Override
    public void unpack(ByteBuf byteBuf) throws Exception {
        //车辆状态
        short vehicleStatus = byteBuf.readUnsignedByte();
        //充电状态
        short chargeStatus = byteBuf.readUnsignedByte();
        //运行模式
        short runMode = byteBuf.readUnsignedByte();
        // 车速
        double vehicleSpeed = byteBuf.readUnsignedShort();
        if (vehicleSpeed <= 2200) {
            vehicleSpeed = BigDecimalUtils.divide(vehicleSpeed, 10D, 1, RoundingMode.DOWN);
        }

        //累计里程
        double totalMileage = byteBuf.readUnsignedInt();
        if (totalMileage <= 9999999) {
            totalMileage = BigDecimalUtils.divide(totalMileage, 10D, 1, RoundingMode.DOWN);
        }

        //总电压
        double totalVoltage = byteBuf.readUnsignedShort();
        if (totalVoltage <= 10000) {
            totalVoltage = BigDecimalUtils.divide(totalVoltage, 10D, 1, RoundingMode.DOWN);
        }

        //总电流
        double totalCurrent = byteBuf.readUnsignedShort();
        if (totalCurrent <= 20000) {
            totalCurrent = BigDecimalUtils.divide(totalCurrent, 10D, 1, RoundingMode.DOWN);
            totalCurrent = BigDecimalUtils.subtract(totalCurrent, 1000D, 1, RoundingMode.DOWN);// 减去偏移量1000
        }

        //soc
        double soc = byteBuf.readUnsignedByte();
        if (soc <= 100) {
            soc = BigDecimalUtils.divide(soc, 100D, 2, RoundingMode.DOWN);
        }
        //DC-DC状态(DC-DC是指开关电源中的一种，指直流直流转换。)
        short dcDcStatus = byteBuf.readUnsignedByte();

        //档位
        short gear = byteBuf.readUnsignedByte();
        //绝缘电阻
        int insulationResistance = byteBuf.readUnsignedShort();

        if (ContextConfig.IS_DIRECT_CLIENT) {
            // 加速踏板行程值
            double acceleratorPedalMileage = byteBuf.readUnsignedByte();
            if (acceleratorPedalMileage <= 100) {
                acceleratorPedalMileage = BigDecimalUtils.divide(acceleratorPedalMileage, 100D, 2, RoundingMode.DOWN);
            }
            // 制动踏板状态
            double brakePedalStatus = byteBuf.readUnsignedByte();
            if (brakePedalStatus <= 100) {
                brakePedalStatus = BigDecimalUtils.divide(brakePedalStatus, 100D, 2, RoundingMode.DOWN);
            }
            this.put(VehicleMsgParam.ACCELERATOR_PEDAL_MILEAGE, acceleratorPedalMileage);
            this.put(VehicleMsgParam.BRAKE_PEDAL_STATUS, brakePedalStatus);
        } else {
            // 预留
            byteBuf.skipBytes(2);
        }
        this.put(VehicleMsgParam.VEHICLE_STATUS, vehicleStatus);
        this.put(VehicleMsgParam.CHARGE_STATUS, chargeStatus);
        this.put(VehicleMsgParam.RUN_MODE, runMode);
        this.put(VehicleMsgParam.VEHICLE_SPEED, vehicleSpeed);
        this.put(VehicleMsgParam.TOTAL_MILEAGE, totalMileage);
        this.put(VehicleMsgParam.TOTAL_VOLTAGE, totalVoltage);
        this.put(VehicleMsgParam.TOTAL_CURRENT, totalCurrent);
        this.put(VehicleMsgParam.SOC, soc);
        this.put(VehicleMsgParam.DC_DC_STATUS, dcDcStatus);
        this.put(VehicleMsgParam.GEAR, gear);
        this.put(VehicleMsgParam.INSULATION_RESISTANCE, insulationResistance);
    }
}

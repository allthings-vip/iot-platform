package allthings.iot.gbt32960.das.record;

import java.math.RoundingMode;

import io.netty.buffer.ByteBuf;
import allthings.iot.constant.vehicle.VehicleMsgParam;
import allthings.iot.gbt32960.das.common.BigDecimalUtils;

/**
 * @author :  luhao
 * @FileName :  Record0x03
 * @CreateDate :  2018/2/9
 * @Description : 燃料电池数据
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
public class Record0x03 extends AbstractRecord {
    public Record0x03() {
        super("0x03");
    }

    @Override
    public void unpack(ByteBuf byteBuf) throws Exception {
        //燃料电池电压
        double fuelBatteryVoltage = byteBuf.readUnsignedShort();
        if (fuelBatteryVoltage <= 20000) {
            fuelBatteryVoltage = BigDecimalUtils.divide(fuelBatteryVoltage, 10D, 1, RoundingMode.DOWN);
        }
        //燃料电池电流
        double fuelBatteryCurrent = byteBuf.readUnsignedShort();
        if (fuelBatteryCurrent <= 20000) {
            fuelBatteryCurrent = BigDecimalUtils.divide(fuelBatteryCurrent, 10D, 1, RoundingMode.DOWN);
        }
        //燃料消耗率
        double fuelConsumptionRate = byteBuf.readUnsignedShort();
        if (fuelConsumptionRate <= 60000) {
            fuelConsumptionRate = BigDecimalUtils.divide(fuelConsumptionRate, 100D, 2, RoundingMode.DOWN);
        }
        // 燃料电池温度探针总数
        int fuelBatteryTemperatureProbeCount = byteBuf.readUnsignedShort();

        int index = 0;
        while (index < fuelBatteryTemperatureProbeCount) {
            index++;
            //探针温度值
            short probeTemperature = byteBuf.readUnsignedByte();
            if (probeTemperature <= 240) {
                probeTemperature -= 40;
            }
            this.put(VehicleMsgParam.PROBE_TEMPERATURE_PREFIX + index, probeTemperature);
        }

        //氢系统中最高温度
        double hydrogenSysMaxTemperature = byteBuf.readUnsignedShort();
        if (hydrogenSysMaxTemperature <= 2400) {
            hydrogenSysMaxTemperature = BigDecimalUtils.divide(hydrogenSysMaxTemperature, 10D, 1, RoundingMode.DOWN);
            hydrogenSysMaxTemperature = BigDecimalUtils.subtract(hydrogenSysMaxTemperature, 40, 1, RoundingMode.DOWN);// 减去偏移量40
        }
        //氢系统中最高温度探针代号
        short hydrogenSysMaxTemperatureProbeCode = byteBuf.readUnsignedByte();

        // 氢气最高浓度
        int hydrogenMaxConcentration = byteBuf.readUnsignedShort();
        // 氢气最高浓度传感器代号
        int hydrogenMaxConcentrationSensorCode = byteBuf.readUnsignedByte();
        // 氢气最高压力
        double hydrogenMaxPressure = byteBuf.readUnsignedShort();
        if (hydrogenMaxPressure <= 1000) {
            hydrogenMaxPressure = BigDecimalUtils.divide(hydrogenMaxPressure, 10D, 1, RoundingMode.DOWN);
        }
        // 氢气最高压力传感器代号
        int hydrogenMaxPressureSensorCode = byteBuf.readUnsignedByte();
        // 高压DC/DC状态
        int highPressureDcdcStatus = byteBuf.readUnsignedByte();

        this.put(VehicleMsgParam.FUEL_BATTERY_VOLTAGE, fuelBatteryVoltage);
        this.put(VehicleMsgParam.FUEL_BATTERY_CURRENT, fuelBatteryCurrent);
        // this.put(VehicleMsgParam.FUEL_CONSUMPTION_RATE, fuelConsumptionRate);
        this.put(VehicleMsgParam.FUEL_BATTERY_TEMPERATURE_PROBE_COUNT, fuelBatteryTemperatureProbeCount);
        this.put(VehicleMsgParam.HYDROGEN_SYS_MAX_TEMPERATURE, hydrogenSysMaxTemperature);
        this.put(VehicleMsgParam.HYDROGEN_SYS_MAX_TEMPERATURE_PROBE_CODE, hydrogenSysMaxTemperatureProbeCode);
        this.put(VehicleMsgParam.HYDROGEN_MAX_CONCENTRATION, hydrogenMaxConcentration);
        this.put(VehicleMsgParam.HYDROGEN_MAX_CONCENTRATION_SENSOR_CODE, hydrogenMaxConcentrationSensorCode);
        this.put(VehicleMsgParam.HYDROGEN_MAX_PRESSURE, hydrogenMaxPressure);
        this.put(VehicleMsgParam.HYDROGEN_MAX_PRESSURE_SENSOR_CODE, hydrogenMaxPressureSensorCode);
        this.put(VehicleMsgParam.HIGH_PRESSURE_DC_DC_STATUS, highPressureDcdcStatus);
    }
}

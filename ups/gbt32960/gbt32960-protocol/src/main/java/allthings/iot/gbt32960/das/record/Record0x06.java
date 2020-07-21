package allthings.iot.gbt32960.das.record;

import java.math.RoundingMode;

import io.netty.buffer.ByteBuf;
import allthings.iot.constant.vehicle.VehicleMsgParam;
import allthings.iot.gbt32960.das.common.BigDecimalUtils;

/**
 * @author :  luhao
 * @FileName :  Record0x06
 * @CreateDate :  2018/2/9
 * @Description : 极值数据
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
public class Record0x06 extends AbstractRecord {
    public Record0x06() {
        super("0x06");
    }

    @Override
    public void unpack(ByteBuf byteBuf) throws Exception {
        // 最高电压电池子系统号
        short maxVoltageBatterySubSysNo = byteBuf.readUnsignedByte();
        // 最高电压电池单体代号
        short maxVoltageBatteryCode = byteBuf.readUnsignedByte();
        // 电池单体电压最高值
        double maxVoltageBattery = byteBuf.readUnsignedShort();
        if (maxVoltageBattery <= 15000) {
            maxVoltageBattery = BigDecimalUtils.divide(maxVoltageBattery, 1000D, 3, RoundingMode.DOWN);
        }
        // 最低电压电池子系统号
        short minVoltageBatterySubSysNo = byteBuf.readUnsignedByte();
        // 最低电压电池单体代号
        short minVoltageBatteryCode = byteBuf.readUnsignedByte();
        // 电池单体电压最低值
        double minVoltageBattery = byteBuf.readUnsignedShort();
        if (minVoltageBattery <= 15000) {
            minVoltageBattery = BigDecimalUtils.divide(minVoltageBattery, 1000D, 3, RoundingMode.DOWN);
        }
        // 最高温度子系统号
        short maxTemperatureSubSysNo = byteBuf.readUnsignedByte();
        // 最高温度探针序号
        short maxTemperatureProbeCode = byteBuf.readUnsignedByte();
        // 最高温度值
        short maxTemperature = byteBuf.readUnsignedByte();
        if (maxTemperature <= 250) {
            maxTemperature -= 40;
        }
        // 最低温度子系统号
        short minTemperatureSubSysNo = byteBuf.readUnsignedByte();
        // 最低温度探针序号
        short minTemperatureProbeCode = byteBuf.readUnsignedByte();
        // 最低温度值
        short minTemperature = byteBuf.readUnsignedByte();
        if (minTemperature <= 250) {
            minTemperature -= 40;
        }

        this.put(VehicleMsgParam.MAX_VOLTAGE_BATTERY_SUB_SYS_NO, maxVoltageBatterySubSysNo);
        this.put(VehicleMsgParam.MAX_VOLTAGE_BATTERY_CODE, maxVoltageBatteryCode);
        this.put(VehicleMsgParam.MAX_VOLTAGE_BATTERY, maxVoltageBattery);
        this.put(VehicleMsgParam.MIN_VOLTAGE_BATTERY_SUB_SYS_NO, minVoltageBatterySubSysNo);
        this.put(VehicleMsgParam.MIN_VOLTAGE_BATTERY_CODE, minVoltageBatteryCode);
        this.put(VehicleMsgParam.MIN_VOLTAGE_BATTERY, minVoltageBattery);
        this.put(VehicleMsgParam.MAX_TEMPERATURE_SUB_SYS_NO, maxTemperatureSubSysNo);
        this.put(VehicleMsgParam.MAX_TEMPERATURE_PROBE_CODE, maxTemperatureProbeCode);
        this.put(VehicleMsgParam.MAX_TEMPERATURE, maxTemperature);
        this.put(VehicleMsgParam.MIN_TEMPERATURE_SUB_SYS_NO, minTemperatureSubSysNo);
        this.put(VehicleMsgParam.MIN_TEMPERATURE_PROBE_CODE, minTemperatureProbeCode);
        this.put(VehicleMsgParam.MIN_TEMPERATURE, minTemperature);
    }
}

package allthings.iot.gbt32960.das.record;

import io.netty.buffer.ByteBuf;
import allthings.iot.constant.vehicle.VehicleMsgParam;

/**
 * @author :  luhao
 * @FileName :  Record0x07
 * @CreateDate :  2018/2/9
 * @Description : 报警数据
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) allthings-vip All Rights Reserved
 * *******************************************************************************************
 */
public class Record0x07 extends AbstractRecord {
    public Record0x07() {
        super("0x07");
    }

    @Override
    public void unpack(ByteBuf byteBuf) throws Exception {
        // 最高报警等级1
        short highestAlarmLevel = byteBuf.readUnsignedByte();
        this.put(VehicleMsgParam.HIGHEST_ALARM_LEVEL, highestAlarmLevel);
        // 通用报警标志4
        long commonAlarmSign = byteBuf.readUnsignedInt();
        this.put(VehicleMsgParam.COMMON_ALARM_SIGN, commonAlarmSign);

        // 温度差异报警0
        byte alarmTemperatureDifference = (byte) ((commonAlarmSign & 0x00000001) > 0L ? 1 : 0);
        // 电池高温报警1
        byte alarmBatteryHighTemperature = (byte) ((commonAlarmSign & 0x00000002) > 0L ? 1 : 0);
        // 车载储能装置类型过压报警2
        byte alarmVehicleOvervoltage = (byte) ((commonAlarmSign & 0x00000004) > 0L ? 1 : 0);
        // 车载储能装置类型欠压报警3
        byte alarmVehicleUndervoltage = (byte) ((commonAlarmSign & 0x00000008) > 0L ? 1 : 0);
        // SOC低报警4
        byte alarmSocLow = (byte) ((commonAlarmSign & 0x00000010) > 0L ? 1 : 0);
        // 单体电池过压报警5
        byte alarmBatteryOvervoltage = (byte) ((commonAlarmSign & 0x00000020) > 0L ? 1 : 0);
        // 单体电池欠压报警6
        byte alarmBatteryUndervoltage = (byte) ((commonAlarmSign & 0x00000040) > 0L ? 1 : 0);
        // SOC过高报警7
        byte alarmSocHigh = (byte) ((commonAlarmSign & 0x00000080) > 0L ? 1 : 0);
        // SOC跳变报警8
        byte alarmSocJump = (byte) ((commonAlarmSign & 0x00000100) > 0L ? 1 : 0);
        // 可充电储能系统不匹配报警9
        byte alarmElectricContainerMismatch = (byte) ((commonAlarmSign & 0x00000200) > 0L ? 1 : 0);
        // 电池单体一致性差报警10
        byte alarmBatteryConsistencyBad = (byte) ((commonAlarmSign & 0x00000400) > 0L ? 1 : 0);
        // 绝缘报警11
        byte alarmInsulation = (byte) ((commonAlarmSign & 0x00000800) > 0L ? 1 : 0);
        // DC-DC温度报警12
        byte alarmDcDcTemperature = (byte) ((commonAlarmSign & 0x00001000) > 0L ? 1 : 0);
        // 制动系统报警13
        byte alarmBrakingSystem = (byte) ((commonAlarmSign & 0x00002000) > 0L ? 1 : 0);
        // DC-DC状态报警14
        byte alarmDcDcStatus = (byte) ((commonAlarmSign & 0x00004000) > 0L ? 1 : 0);
        // 驱动电机控制器温度报警15
        byte alarmDriveMotorControllerTemperature = (byte) ((commonAlarmSign & 0x00008000) > 0L ? 1 : 0);
        // 高压互锁状态报警16
        byte alarmHvilStatus = (byte) ((commonAlarmSign & 0x00010000) > 0L ? 1 : 0);
        // 驱动电机温度报警17
        byte alarmDriveMotorTemperature = (byte) ((commonAlarmSign & 0x00020000) > 0L ? 1 : 0);
        // 车载储能装置类型过充18
        byte alarmVehicleTypeOvercharge = (byte) ((commonAlarmSign & 0x00040000) > 0L ? 1 : 0);

        this.put(VehicleMsgParam.ALARM_TEMPERATURE_DIFFERENCE, alarmTemperatureDifference);
        this.put(VehicleMsgParam.ALARM_BATTERY_HIGH_TEMPERATURE, alarmBatteryHighTemperature);
        this.put(VehicleMsgParam.ALARM_VEHICLE_OVERVOLTAGE, alarmVehicleOvervoltage);
        this.put(VehicleMsgParam.ALARM_VEHICLE_UNDERVOLTAGE, alarmVehicleUndervoltage);
        this.put(VehicleMsgParam.ALARM_SOC_LOW, alarmSocLow);
        this.put(VehicleMsgParam.ALARM_BATTERY_OVERVOLTAGE, alarmBatteryOvervoltage);
        this.put(VehicleMsgParam.ALARM_BATTERY_UNDERVOLTAGE, alarmBatteryUndervoltage);
        this.put(VehicleMsgParam.ALARM_SOC_HIGH, alarmSocHigh);
        this.put(VehicleMsgParam.ALARM_SOC_JUMP, alarmSocJump);
        this.put(VehicleMsgParam.ALARM_ELECTRIC_CONTAINER_MISMATCH, alarmElectricContainerMismatch);
        this.put(VehicleMsgParam.ALARM_BATTERY_CONSISTENCY_BAD, alarmBatteryConsistencyBad);
        this.put(VehicleMsgParam.ALARM_INSULATION, alarmInsulation);
        this.put(VehicleMsgParam.ALARM_DC_DC_TEMPERATURE, alarmDcDcTemperature);
        this.put(VehicleMsgParam.ALARM_BRAKING_SYSTEM, alarmBrakingSystem);
        this.put(VehicleMsgParam.ALARM_DC_DC_STATUS, alarmDcDcStatus);
        this.put(VehicleMsgParam.ALARM_DRIVE_MOTOR_CONTROLLER_TEMPERATURE, alarmDriveMotorControllerTemperature);
        this.put(VehicleMsgParam.ALARM_HVIL_STATUS, alarmHvilStatus);
        this.put(VehicleMsgParam.ALARM_DRIVE_MOTOR_TEMPERATURE, alarmDriveMotorTemperature);
        this.put(VehicleMsgParam.ALARM_VEHICLE_TYPE_OVERCHARGE, alarmVehicleTypeOvercharge);

        // 可充电储能装置故障总数1
        short electricContainerFaultCount = byteBuf.readUnsignedByte();
        this.put(VehicleMsgParam.ELECTRIC_CONTAINER_FAULT_COUNT, electricContainerFaultCount);
        if (electricContainerFaultCount == 0xFE || electricContainerFaultCount == 0xFF) {
            return;
        }
        // 可充电储能装置故障代码列表4*N
        for (int i = 1; i <= electricContainerFaultCount; i++) {
            long electricContainerFaultCode = byteBuf.readUnsignedInt();
            this.put(VehicleMsgParam.ELECTRIC_CONTAINER_FAULT_CODE_PREFIX + i, electricContainerFaultCode);
        }
        // 驱动电机故障总数
        short driveMotorFaultCount = byteBuf.readUnsignedByte();
        this.put(VehicleMsgParam.DRIVE_MOTOR_FAULT_COUNT, driveMotorFaultCount);
        if (driveMotorFaultCount == 0xFE || driveMotorFaultCount == 0xFF) {
            return;
        }
        // 驱动电机故障代码列表
        for (int i = 1; i <= driveMotorFaultCount; i++) {
            long driveMotorFaultCode = byteBuf.readUnsignedInt();
            this.put(VehicleMsgParam.DRIVE_MOTOR_FAULT_CODE_PREFIX + i, driveMotorFaultCode);
        }
        // 发动机故障总数
        short engineFaultCount = byteBuf.readUnsignedByte();
        this.put(VehicleMsgParam.ENGINE_FAULT_COUNT, engineFaultCount);
        if (engineFaultCount == 0xFE || engineFaultCount == 0xFF) {
            return;
        }
        // 发动机故障代码列表
        for (int i = 1; i <= engineFaultCount; i++) {
            long engineFaultCode = byteBuf.readUnsignedInt();
            this.put(VehicleMsgParam.ENGINE_FAULT_CODE_PREFIX + i, engineFaultCode);
        }
        // 其他故障总数
        short otherFaultCount = byteBuf.readUnsignedByte();
        this.put(VehicleMsgParam.OTHER_FAULT_COUNT, otherFaultCount);
        if (otherFaultCount == 0xFE || otherFaultCount == 0xFF) {
            return;
        }
        // 其他故障代码列表
        for (int i = 1; i <= otherFaultCount; i++) {
            long otherFaultCode = byteBuf.readUnsignedInt();
            this.put(VehicleMsgParam.OTHER_FAULT_CODE_PREFIX + i, otherFaultCode);
        }
    }
}

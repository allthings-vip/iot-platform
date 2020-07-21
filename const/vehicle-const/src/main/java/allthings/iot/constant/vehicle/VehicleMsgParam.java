package allthings.iot.constant.vehicle;

/**
 * @author :  luhao
 * @FileName :  GpsMsgParam
 * @CreateDate :  2018/1/16
 * @Description :
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
public interface VehicleMsgParam {

    /**
     * 流水号
     */
    public static final String SERIAL_NUMBER = "serialNumber";
    /**
     * ICCID
     */
    public static final String ICCID = "iccid";
    /**
     * 数据采集时间
     */
    public static final String OCCUR_DATE_TIME = "gps_datetime";
    /**
     * 登入用户名
     */
    public static final String USERNAME = "username";
    /**
     * 登入密码
     */
    public static final String PASSWORD = "password";
    /**
     * 数据加密规则
     */
    public static final String ENCRYPTION_RULE = "encryptionRule";
    /**
     * 报文应答
     */
    public static final String RESPONSE_CODE = "responseCode";
    /**
     * 可充电储能子系统数
     */
    public static final String ELECTRIC_CONTAINER_SUB_SYS_COUNT = "electricContainerSubSysCount";
    /**
     * 可充电储能系统编码长度
     */
    public static final String ELECTRIC_CONTAINER_SYS_CODE_LENGTH = "electricContainerSysCodeLength";
    /**
     * 可充电储能系统编码
     */
    public static final String ELECTRIC_CONTAINER_SYS_CODE = "electricContainerSysCode";

    /**
     * 整车数据start
     */
    // 车辆状态
    public static final String VEHICLE_STATUS = "vehicleStatus";
    // 充电状态
    public static final String CHARGE_STATUS = "chargeStatus";
    // 运行模式
    public static final String RUN_MODE = "runMode";
    // 车速
    public static final String VEHICLE_SPEED = "gps_speed";
    // 累计里程
    public static final String TOTAL_MILEAGE = "gps_mileage";
    // 总电压
    public static final String TOTAL_VOLTAGE = "totalVoltage";
    // 总电流
    public static final String TOTAL_CURRENT = "totalCurrent";
    // soc 剩余电量比
    public static final String SOC = "soc";
    // DC-DC状态(DC-DC是指开关电源中的一种，指直流直流转换。)
    public static final String DC_DC_STATUS = "dcDcStatus";
    // 档位
    public static final String GEAR = "gear";
    // 绝缘电阻
    public static final String INSULATION_RESISTANCE = "insulationResistance";
    // 加速踏板行程值
    public static final String ACCELERATOR_PEDAL_MILEAGE = "acceleratorPedalMileage";
    // 制动踏板状态
    public static final String BRAKE_PEDAL_STATUS = "brakePedalStatus";
    /** 整车数据end */

    /** 驱动电机数据start */
    // 驱动电机个数
    public static final String DRIVE_MOTOR_COUNT = "driveMotorCount";
    // 驱动电机序号
    public static final String DRIVE_MOTOR_NO_PREFIX = "driveMotorNo_";
    // 驱动电机状态
    public static final String DRIVE_MOTOR_STATUS_PREFIX = "driveMotorStatus_";
    // 驱动电机控制器温度(0-250摄氏度,数据偏移量40摄氏度，表示的温度为-40-210摄氏度)
    public static final String DRIVE_MOTOR_CONTROLLER_TEMPERATURE_PREFIX = "driveMotorControllerTemperature_";
    // 驱动电机转速
    public static final String DRIVE_MOTOR_ROTATING_SPEED_PREFIX = "driveMotorRotatingSpeed_";
    // 驱动电机转矩
    public static final String DRIVE_MOTOR_TORQUE_PREFIX = "driveMotorTorque_";
    // 驱动电机温度(0-250摄氏度,数据偏移量40摄氏度，表示的温度为-40-210摄氏度)
    public static final String DRIVE_MOTOR_TEMPERATURE_PREFIX = "driveMotorTemperature_";
    // 电机控制器输入电压
    public static final String MOTOR_CONTROLLER_INPUT_VOLTAGE_PREFIX = "motorControllerInputVoltage_";
    // 电机控制器直流母线电流
    public static final String MOTOR_CONTROLLER_DC_BUS_CURRENT_PREFIX = "motorControllerDcBusCurrent_";
    /** 驱动电机数据end */

    /**
     * 燃料电池数据start
     */
    // 燃料电池电压
    public static final String FUEL_BATTERY_VOLTAGE = "fuelBatteryVoltage";
    // 燃料电池电流
    public static final String FUEL_BATTERY_CURRENT = "fuelBatteryCurrent";
    // 燃料消耗率(暂不解析)
    // public static final String fuelConsumptionRate = "fuelConsumptionRate";
    // 燃料电池温度探针总数
    public static final String FUEL_BATTERY_TEMPERATURE_PROBE_COUNT = "fuelBatteryTemperatureProbeCount";

    // 探针温度值（前缀）
    public static final String PROBE_TEMPERATURE_PREFIX = "probeTemperature_";
    // 氢系统中最高温度
    public static final String HYDROGEN_SYS_MAX_TEMPERATURE = "hydrogenSysMaxTemperature";
    // 氢系统中最高温度探针代号
    public static final String HYDROGEN_SYS_MAX_TEMPERATURE_PROBE_CODE = "hydrogenSysMaxTemperatureProbeCode";
    // 氢气最高浓度
    public static final String HYDROGEN_MAX_CONCENTRATION = "hydrogenMaxConcentration";
    // 氢气最高浓度传感器代号
    public static final String HYDROGEN_MAX_CONCENTRATION_SENSOR_CODE = "hydrogenMaxConcentrationSensorCode";
    // 氢气最高压力
    public static final String HYDROGEN_MAX_PRESSURE = "hydrogenMaxPressure";
    // 氢气最高压力传感器代号
    public static final String HYDROGEN_MAX_PRESSURE_SENSOR_CODE = "hydrogenMaxPressureSensorCode";
    // 高压DC/DC状态
    public static final String HIGH_PRESSURE_DC_DC_STATUS = "highPressureDcdcStatus";
    /** 燃料电池数据end */

    /**
     * 发动机数据start
     */
    // 发动机状态
    public static final String ENGINE_STATUS = "engineStatus";
    // 曲轴转速
    public static final String CRANKSHAFT_SPEED = "crankshaftSpeed";
    // 燃料消耗率
    public static final String FUEL_CONSUMPTION_RATE = "fuelConsumptionRate";
    /** 发动机数据end */

    /**
     * 车辆位置数据start
     */
    // 是否为有效定位
    public static final String GPS_VALID = "gps_valid";
    // 经度
    public static final String GPS_LONGITUDE = "gps_longitude";
    // 纬度
    public static final String GPS_LATITUDE = "gps_latitude";
    /** 车辆位置数据end */

    /**
     * 极值数据start
     */
    // 最高电压电池子系统号
    public static final String MAX_VOLTAGE_BATTERY_SUB_SYS_NO = "maxVoltageBatterySubSysNo";
    // 最高电压电池单体代号
    public static final String MAX_VOLTAGE_BATTERY_CODE = "maxVoltageBatteryCode";
    // 电池单体电压最高值
    public static final String MAX_VOLTAGE_BATTERY = "maxVoltageBattery";
    // 最低电压电池子系统号
    public static final String MIN_VOLTAGE_BATTERY_SUB_SYS_NO = "minVoltageBatterySubSysNo";
    // 最低电压电池单体代号
    public static final String MIN_VOLTAGE_BATTERY_CODE = "minVoltageBatteryCode";
    // 电池单体电压最低值
    public static final String MIN_VOLTAGE_BATTERY = "minVoltageBattery";
    // 最高温度子系统号
    public static final String MAX_TEMPERATURE_SUB_SYS_NO = "maxTemperatureSubSysNo";
    // 最高温度探针序号
    public static final String MAX_TEMPERATURE_PROBE_CODE = "maxTemperatureProbeCode";
    // 最高温度值
    public static final String MAX_TEMPERATURE = "maxTemperature";
    // 最低温度子系统号
    public static final String MIN_TEMPERATURE_SUB_SYS_NO = "minTemperatureSubSysNo";
    // 最低温度探针序号
    public static final String MIN_TEMPERATURE_PROBE_CODE = "minTemperatureProbeCode";
    // 最低温度值
    public static final String MIN_TEMPERATURE = "minTemperature";
    /** 极值数据end */

    /**
     * 报警数据start
     */
    // 最高报警等级1
    public static final String HIGHEST_ALARM_LEVEL = "highestAlarmLevel";
    // 通用报警标志4
    public static final String COMMON_ALARM_SIGN = "commonAlarmSign";

    // 温度差异报警0
    public static final String ALARM_TEMPERATURE_DIFFERENCE = "alarmTemperatureDifference";
    // 电池高温报警1
    public static final String ALARM_BATTERY_HIGH_TEMPERATURE = "alarmBatteryHighTemperature";
    // 车载储能装置类型过压报警2
    public static final String ALARM_VEHICLE_OVERVOLTAGE = "alarmVehicleOvervoltage";
    // 车载储能装置类型欠压报警3
    public static final String ALARM_VEHICLE_UNDERVOLTAGE = "alarmVehicleUndervoltage";
    // SOC低报警4
    public static final String ALARM_SOC_LOW = "alarmSocLow";
    // 单体电池过压报警5
    public static final String ALARM_BATTERY_OVERVOLTAGE = "alarmBatteryOvervoltage";
    // 单体电池欠压报警6
    public static final String ALARM_BATTERY_UNDERVOLTAGE = "alarmBatteryUndervoltage";
    // SOC过高报警7
    public static final String ALARM_SOC_HIGH = "alarmSocHigh";
    // SOC跳变报警8
    public static final String ALARM_SOC_JUMP = "alarmSocJump";
    // 可充电储能系统不匹配报警9
    public static final String ALARM_ELECTRIC_CONTAINER_MISMATCH = "alarmElectricContainerMismatch";
    // 电池单体一致性差报警10
    public static final String ALARM_BATTERY_CONSISTENCY_BAD = "alarmBatteryConsistencyBad";
    // 绝缘报警11
    public static final String ALARM_INSULATION = "alarmInsulation";
    // DC-DC温度报警12
    public static final String ALARM_DC_DC_TEMPERATURE = "alarmDcDcTemperature";
    // 制动系统报警13
    public static final String ALARM_BRAKING_SYSTEM = "alarmBrakingSystem";
    // DC-DC状态报警14
    public static final String ALARM_DC_DC_STATUS = "alarmDcDcStatus";
    // 驱动电机控制器温度报警15
    public static final String ALARM_DRIVE_MOTOR_CONTROLLER_TEMPERATURE = "alarmDriveMotorControllerTemperature";
    // 高压互锁状态报警16
    public static final String ALARM_HVIL_STATUS = "alarmHvilStatus";
    // 驱动电机温度报警17
    public static final String ALARM_DRIVE_MOTOR_TEMPERATURE = "alarmDriveMotorTemperature";
    // 车载储能装置类型过充18
    public static final String ALARM_VEHICLE_TYPE_OVERCHARGE = "alarmVehicleTypeOvercharge";

    // 可充电储能装置故障总数1
    public static final String ELECTRIC_CONTAINER_FAULT_COUNT = "electricContainerFaultCount";
    // 可充电储能装置故障代码列表4*N
    public static final String ELECTRIC_CONTAINER_FAULT_CODE_PREFIX = "electricContainerFaultCode_";
    // 驱动电机故障总数
    public static final String DRIVE_MOTOR_FAULT_COUNT = "driveMotorFaultCount";
    // 驱动电机故障代码列表
    public static final String DRIVE_MOTOR_FAULT_CODE_PREFIX = "driveMotorFaultCode_";
    // 发动机故障总数
    public static final String ENGINE_FAULT_COUNT = "engineFaultCount";
    // 发动机故障代码列表
    public static final String ENGINE_FAULT_CODE_PREFIX = "engineFaultCode_";
    // 其他故障总数
    public static final String OTHER_FAULT_COUNT = "otherFaultCount";
    // 其他故障代码列表
    public static final String OTHER_FAULT_CODE_PREFIX = "otherFaultCode_";
    /** 报警数据end */

    /**
     * 可充电储能装置电压数据start
     */
    // 可充电储能子系统号
    public static final String ELECTRIC_CONTAINER_SUB_SYS_NO_PREFIX = "electricContainerSubSysNo_";
    // 可充电储能装置电压
    public static final String ELECTRIC_CONTAINER_DEVICE_VOLTAGE_PREFIX = "electricContainerDeviceVoltage_";
    // 可充电储能装置电流
    public static final String ELECTRIC_CONTAINER_DEVICE_CURRENT_PREFIX = "electricContainerDeviceCurrent_";
    // 单体电池总数
    public static final String SIMPLE_BATTERY_COUNT_PREFIX = "simpleBatteryCount_";
    // 本帧起始电池序号
    public static final String FRAME_START_BATTERY_NO_PREFIX = "frameStartBatteryNo_";
    // 本帧单体电池总数
    public static final String FRAME_SIMPLE_BATTERY_COUNT_PREFIX = "frameSimpleBatteryCount_";
    // 单体电池电压前缀
    public static final String SIMPLE_BATTERY_VOLTAGE_PREFIX_PREFIX = "simpleBatteryVoltage_";
    /** 可充电储能装置电压数据end */

    /**
     * 可充电储能装置电压数据start
     */
    // 可充电储能子系统号
    // public static final String ELECTRIC_CONTAINER_SUB_SYS_NO = "electricContainerSubSysNo";
    // 可充电储能温度探针个数
    public static final String ELECTRIC_CONTAINER_TEMPERATURE_PROBE_COUNT_PREFIX = "electricContainerTemperatureProbeCount_";
    // 可充电储能子系统各温度探针检测到的温度值前缀
    public static final String ELECTRIC_CONTAINER_PROBE_TEMPERATURE_PREFIX_PREFIX = "electricContainerProbeTemperature_";
    /** 可充电储能装置电压数据end */

    /**
     * 传化慧联自定义因子start
     */
    // 版本切换标志(0：切换为非锁车版本；1：切换为锁车版本)
    public static final String NEED_SWITCH_FLAG = "needSwitchFlag";
    // 锁车标志(0：不需要锁车；1：需要锁车)
    public static final String NEED_LOCK_FLAG = "needLockFlag";
    // 终端绑定状态(1：已绑定；0：未绑定)
    public static final String NEED_BIND_FLAG = "needBindFlag";
    // 车辆控制器版本状态(1：锁车版本；0：非锁车版本)
    public static final String VCU_VERSION_STATUS = "vcuVersionStatus";
    // 锁车状态 (1：已锁车；0：未锁车)
    public static final String LOCK_STATUS = "lockStatus";
    /** 传化慧联自定义因子end */

    /**
     * 车牌号
     */
    String VEHICLE_LICENSE_PLATE = "license_plate";

    /**
     * 车辆设备号
     */
    String VEHICLE_DEVICE_NO = "device_no";

    /** 无人车因子 start */

    /**
     * 电池可用电量
     */
    String USABLE_CAPACITY = "usableCapacity";

    /**
     * 电池标称电量
     */
    String NOMINAL_CAPACITY = "nominalCapacity";

    /**
     * 剩余续航里程
     */
    String ENDURANCE = "endurance";

    /**
     * 刹车力度（0-100%）
     */
    String BRAKE = "brake";

    /**
     * 剩余可用时间
     */
    String AUTONOMY = "autonomy";

    /**
     * 车头朝向（正东逆时针）
     */
    String AZIMUTH = "azimuth";

    /**
     * 放电电流（负数为充电）
     */
    String AMMETER = "ammeter";

    /** 无人车因子 end */


}

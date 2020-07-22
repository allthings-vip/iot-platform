package allthings.iot.gbt32960.das.record;

import java.math.RoundingMode;

import io.netty.buffer.ByteBuf;
import allthings.iot.constant.vehicle.VehicleMsgParam;
import allthings.iot.gbt32960.das.common.BigDecimalUtils;

/**
 * @author :  luhao
 * @FileName :  Record0x02
 * @CreateDate :  2018/2/9
 * @Description : 驱动电机数据
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) allthings-vip All Rights Reserved
 * *******************************************************************************************
 */
public class Record0x02 extends AbstractRecord {
    public Record0x02() {
        super("0x02");
    }

    @Override
    public void unpack(ByteBuf byteBuf) throws Exception {
        short emNums = byteBuf.readUnsignedByte();
        this.put(VehicleMsgParam.DRIVE_MOTOR_COUNT, emNums);
        for (int i = 1; i <= emNums; i++) {
            unpackDetail(byteBuf, i);
        }
    }

    /**
     * @param byteBuf
     */
    private void unpackDetail(ByteBuf byteBuf, int index) {
        //驱动电机序号
        short driveMotorNo = byteBuf.readUnsignedByte();

        //驱动电机状态
        short driveMotorStatus = byteBuf.readUnsignedByte();

        //驱动电机控制器温度(0-250摄氏度,数据偏移量40摄氏度，表示的温度为-40-210摄氏度)
        short driveMotorControllerTemperature = byteBuf.readUnsignedByte();
        if (driveMotorControllerTemperature <= 250) {
            driveMotorControllerTemperature -= 40;
        }

        //驱动电机转速
        int driveMotorRotatingSpeed = byteBuf.readUnsignedShort();
        if (driveMotorRotatingSpeed <= 65531) {
            driveMotorRotatingSpeed -= 20000;
        }

        //驱动电机转矩
        double driveMotorTorque = byteBuf.readUnsignedShort();
        if (driveMotorTorque <= 65531) {
            driveMotorTorque = BigDecimalUtils.subtract(driveMotorTorque, 20000D, 1, RoundingMode.DOWN);// 减去偏移量20000
            driveMotorTorque = BigDecimalUtils.divide(driveMotorTorque, 10D, 1, RoundingMode.DOWN);
        }

        //驱动电机温度(0-250摄氏度,数据偏移量40摄氏度，表示的温度为-40-210摄氏度)
        short driveMotorTemperature = byteBuf.readUnsignedByte();
        if (driveMotorTemperature <= 250) {
            driveMotorTemperature -= 40;
        }

        //电机控制器输入电压
        double motorControllerInputVoltage = byteBuf.readUnsignedShort();
        if (motorControllerInputVoltage <= 60000) {
            motorControllerInputVoltage = BigDecimalUtils.divide(motorControllerInputVoltage, 10D, 1,
                                                                 RoundingMode.DOWN);
        }

        //电机控制器直流母线电流
        double motorControllerDcBusCurrent = byteBuf.readUnsignedShort();
        if (motorControllerDcBusCurrent <= 20000) {
            motorControllerDcBusCurrent = BigDecimalUtils.divide(motorControllerDcBusCurrent, 10D, 1,
                                                                 RoundingMode.DOWN);
            motorControllerDcBusCurrent = BigDecimalUtils.subtract(motorControllerDcBusCurrent, 1000D, 1,
                                                                   RoundingMode.DOWN);// 减去偏移量1000
        }

        this.put(VehicleMsgParam.DRIVE_MOTOR_NO_PREFIX + index, driveMotorNo);
        this.put(VehicleMsgParam.DRIVE_MOTOR_STATUS_PREFIX + index, driveMotorStatus);
        this.put(VehicleMsgParam.DRIVE_MOTOR_CONTROLLER_TEMPERATURE_PREFIX + index, driveMotorControllerTemperature);
        this.put(VehicleMsgParam.DRIVE_MOTOR_ROTATING_SPEED_PREFIX + index, driveMotorRotatingSpeed);
        this.put(VehicleMsgParam.DRIVE_MOTOR_TORQUE_PREFIX + index, driveMotorTorque);
        this.put(VehicleMsgParam.DRIVE_MOTOR_TEMPERATURE_PREFIX + index, driveMotorTemperature);
        this.put(VehicleMsgParam.MOTOR_CONTROLLER_INPUT_VOLTAGE_PREFIX + index, motorControllerInputVoltage);
        this.put(VehicleMsgParam.MOTOR_CONTROLLER_DC_BUS_CURRENT_PREFIX + index, motorControllerDcBusCurrent);
    }
}

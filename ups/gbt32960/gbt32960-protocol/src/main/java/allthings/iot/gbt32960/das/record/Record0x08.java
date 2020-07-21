/*
 * Copyright 2018 Transfar56.com All right reserved. This software is the
 * confidential and proprietary information of Transfar56.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Transfar56.com.
 */
package allthings.iot.gbt32960.das.record;

import java.math.RoundingMode;

import io.netty.buffer.ByteBuf;
import allthings.iot.constant.vehicle.VehicleMsgParam;
import allthings.iot.gbt32960.das.common.BigDecimalUtils;


/**
 * 类Record0x08.java的实现描述：可充电储能装置电压数据
 * 
 * @author mingyuan.miao 2018年4月18日 下午3:47:55
 */
public class Record0x08 extends AbstractRecord {

    public Record0x08(){
        super("0x08");
    }

    @Override
    public void unpack(ByteBuf byteBuf) throws Exception {
        short emNums = byteBuf.readUnsignedByte();
        this.put(VehicleMsgParam.ELECTRIC_CONTAINER_SUB_SYS_COUNT, emNums);
        for (int i = 1; i <= emNums; i++) {
            unpackDetail(byteBuf, i);
        }
    }

    private void unpackDetail(ByteBuf byteBuf, int index) {
        // 可充电储能子系统号
        short electricContainerSubSysNo = byteBuf.readUnsignedByte();

        // 可充电储能装置电压
        double electricContainerDeviceVoltage = byteBuf.readUnsignedShort();
        if (electricContainerDeviceVoltage <= 10000) {
            electricContainerDeviceVoltage = BigDecimalUtils.divide(electricContainerDeviceVoltage, 10D, 1,
                                                                    RoundingMode.DOWN);
        }

        // 可充电储能装置电流
        double electricContainerDeviceCurrent = byteBuf.readUnsignedShort();
        if (electricContainerDeviceCurrent <= 20000) {
            electricContainerDeviceCurrent = BigDecimalUtils.divide(electricContainerDeviceCurrent, 10D, 1,
                                                                    RoundingMode.DOWN);
            electricContainerDeviceCurrent = BigDecimalUtils.subtract(electricContainerDeviceCurrent, 1000D, 1,
                                                                      RoundingMode.DOWN);
        }
        // 单体电池总数
        int simpleBatteryCount = byteBuf.readUnsignedShort();
        // 本帧起始电池序号
        int frameStartBatteryNo = byteBuf.readUnsignedShort();
        // 本帧单体电池总数
        short frameSimpleBatteryCount = byteBuf.readUnsignedByte();
        // 单体电池电压
        for (short i = 1; i <= frameSimpleBatteryCount; i++) {
            double simpleBatteryVoltage = byteBuf.readUnsignedShort();
            if (simpleBatteryVoltage <= 60000) {
                simpleBatteryVoltage = BigDecimalUtils.divide(simpleBatteryVoltage, 1000D, 3, RoundingMode.DOWN);
            }
            this.put(VehicleMsgParam.SIMPLE_BATTERY_VOLTAGE_PREFIX_PREFIX + index + "_" + i, simpleBatteryVoltage);
        }

        this.put(VehicleMsgParam.ELECTRIC_CONTAINER_SUB_SYS_NO_PREFIX + index, electricContainerSubSysNo);
        this.put(VehicleMsgParam.ELECTRIC_CONTAINER_DEVICE_VOLTAGE_PREFIX + index, electricContainerDeviceVoltage);
        this.put(VehicleMsgParam.ELECTRIC_CONTAINER_DEVICE_CURRENT_PREFIX + index, electricContainerDeviceCurrent);
        this.put(VehicleMsgParam.SIMPLE_BATTERY_COUNT_PREFIX + index, simpleBatteryCount);
        this.put(VehicleMsgParam.FRAME_START_BATTERY_NO_PREFIX + index, frameStartBatteryNo);
        this.put(VehicleMsgParam.FRAME_SIMPLE_BATTERY_COUNT_PREFIX + index, frameSimpleBatteryCount);
    }
}

/*
 * Copyright 2018 Transfar56.com All right reserved. This software is the
 * confidential and proprietary information of Transfar56.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Transfar56.com.
 */
package allthings.iot.gbt32960.das.record;

import io.netty.buffer.ByteBuf;
import allthings.iot.constant.vehicle.VehicleMsgParam;


/**
 * 类Record0x09.java的实现描述：可充电储能装置温度数据
 * 
 * @author mingyuan.miao 2018年4月18日 下午3:48:14
 */
public class Record0x09 extends AbstractRecord {

    public Record0x09(){
        super("0x09");
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

        // 可充电储能温度探针个数
        int electricContainerTemperatureProbeCount = byteBuf.readUnsignedShort();

        // 可充电储能子系统各温度探针检测到的温度值
        for (short i = 1; i <= electricContainerTemperatureProbeCount; i++) {
            short electricContainerProbeTemperature = byteBuf.readUnsignedByte();
            if (electricContainerProbeTemperature <= 250) {
                electricContainerProbeTemperature -= 40F;
            }
            this.put(VehicleMsgParam.ELECTRIC_CONTAINER_PROBE_TEMPERATURE_PREFIX_PREFIX + index + "_" + i,
                     electricContainerProbeTemperature);
        }

        this.put(VehicleMsgParam.ELECTRIC_CONTAINER_SUB_SYS_NO_PREFIX + index, electricContainerSubSysNo);
        this.put(VehicleMsgParam.ELECTRIC_CONTAINER_TEMPERATURE_PROBE_COUNT_PREFIX + index,
                 electricContainerTemperatureProbeCount);
    }

}

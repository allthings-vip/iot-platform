package allthings.iot.gbt32960.das.record;

import java.math.BigDecimal;

import io.netty.buffer.ByteBuf;
import allthings.iot.constant.vehicle.VehicleMsgParam;

/**
 * @author :  luhao
 * @FileName :  Record0x05
 * @CreateDate :  2018/2/9
 * @Description : 车辆位置数据
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
public class Record0x05 extends AbstractRecord {
    public Record0x05() {
        super("0x05");
    }

    @Override
    public void unpack(ByteBuf byteBuf) throws Exception {
        //定位状态
        byte positionStatus = byteBuf.readByte();
        //经度
        BigDecimal longitudeBD = new BigDecimal(byteBuf.readUnsignedInt());
        longitudeBD = longitudeBD.setScale(6, BigDecimal.ROUND_DOWN);
        longitudeBD = longitudeBD.divide(new BigDecimal(1000000));
        double longitude = longitudeBD.doubleValue();
        //纬度
        BigDecimal latitudeBD = new BigDecimal(byteBuf.readUnsignedInt());
        latitudeBD = latitudeBD.setScale(6, BigDecimal.ROUND_DOWN);
        latitudeBD = latitudeBD.divide(new BigDecimal(1000000));
        double latitude = latitudeBD.doubleValue();
        //是否为有效定位
        boolean gpsValid = true;
        int gpsValidFlag = positionStatus & 0x01;
        if (gpsValidFlag == 1) {
            gpsValid = false;
        }
        //南纬还是北纬
        int nsFlag = positionStatus & 0x02;
        if (nsFlag > 0) {
            latitude = -latitude;
        }
        //东经还是西经
        int weFlag = positionStatus & 0x04;
        if (weFlag > 0) {
            longitude = -longitude;
        }

        this.put(VehicleMsgParam.GPS_VALID, gpsValid);
        this.put(VehicleMsgParam.GPS_LONGITUDE, longitude);
        this.put(VehicleMsgParam.GPS_LATITUDE, latitude);
    }
}

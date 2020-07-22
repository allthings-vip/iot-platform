package allthings.iot.dms.service;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import allthings.iot.dms.IDeviceLocationService;
import allthings.iot.dms.dao.DeviceLocationDao;
import allthings.iot.dms.dto.DeviceLocationDto;
import allthings.iot.dms.entity.IotDeviceLocation;
import allthings.iot.util.gps.enums.CoorType;
import allthings.iot.util.gps.util.GpsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author :  sylar
 * @FileName :  DeviceLocationServiceImpl
 * @CreateDate :  2017/11/08
 * @Description :
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) allthings-vip All Rights Reserved
 * *******************************************************************************************
 */
@Service
public class DeviceLocationServiceImpl implements IDeviceLocationService {

    private final static String COOR_FORMAT = "%s,%s";

    @Autowired
    DeviceLocationDao deviceLocationDao;

    @Override
    public long bindLocation(String userId, String deviceType, String deviceId, int coorType, double lon, double lat,
                             String locationDesc) {
        String wgsCoor, gcjCoor, bdCoor;
        double[] coor;

        CoorType ct = CoorType.valueOf(coorType);
        switch (ct) {
            case WGS84:
                wgsCoor = String.format(COOR_FORMAT, lon, lat);

                coor = GpsUtil.gpsConvertor(lon, lat, CoorType.WGS84, CoorType.GCJ02);
                gcjCoor = String.format(COOR_FORMAT, coor[0], coor[1]);

                coor = GpsUtil.gpsConvertor(lon, lat, CoorType.WGS84, CoorType.BD09LL);
                bdCoor = String.format(COOR_FORMAT, coor[0], coor[1]);

                break;
            case GCJ02:
                gcjCoor = String.format(COOR_FORMAT, lon, lat);

                coor = GpsUtil.gpsConvertor(lon, lat, CoorType.GCJ02, CoorType.WGS84);
                wgsCoor = String.format(COOR_FORMAT, coor[0], coor[1]);

                coor = GpsUtil.gpsConvertor(lon, lat, CoorType.GCJ02, CoorType.BD09LL);
                bdCoor = String.format(COOR_FORMAT, coor[0], coor[1]);

                break;
            case BD09LL:
                bdCoor = String.format(COOR_FORMAT, lon, lat);

                coor = GpsUtil.gpsConvertor(lon, lat, CoorType.BD09LL, CoorType.GCJ02);
                gcjCoor = String.format(COOR_FORMAT, coor[0], coor[1]);

                coor = GpsUtil.gpsConvertor(lon, lat, CoorType.BD09LL, CoorType.WGS84);
                wgsCoor = String.format(COOR_FORMAT, coor[0], coor[1]);

                break;
            default:
                wgsCoor = gcjCoor = bdCoor = null;
                break;
        }

        IotDeviceLocation pojo = deviceLocationDao.getByDeviceId(deviceId);
        if (pojo == null) {
            pojo = new IotDeviceLocation();
        }

        pojo.setUserId(userId);
        pojo.setDeviceType(deviceType);
        pojo.setDeviceId(deviceId);

        pojo.setLocationDesc(locationDesc);
        pojo.setWgsCoor(wgsCoor);
        pojo.setGcjCoor(gcjCoor);
        pojo.setBdCoor(bdCoor);

        deviceLocationDao.saveAndFlush(pojo);
        return pojo.getIotDeviceLocationId();
    }

    @Override
    public DeviceLocationDto getLocation(String deviceId, int coorType) {
        IotDeviceLocation pojo = deviceLocationDao.getByDeviceId(deviceId);
        if (pojo == null) {
            return null;
        }


        DeviceLocationDto dto = new DeviceLocationDto();
        dto.setUserId(pojo.getUserId());
        dto.setDeviceType(pojo.getDeviceType());
        dto.setDeviceId(pojo.getDeviceId());
        dto.setLocationDesc(pojo.getLocationDesc());
        dto.setCoorType(coorType);

        try {
            String coor;
            CoorType ct = CoorType.valueOf(coorType);
            switch (ct) {
                case WGS84:
                    coor = pojo.getWgsCoor();
                    break;
                case GCJ02:
                    coor = pojo.getGcjCoor();
                    break;
                case BD09LL:
                    coor = pojo.getBdCoor();
                    break;
                default:
                    coor = null;
                    break;
            }

            if (!Strings.isNullOrEmpty(coor)) {
                List<String> strList = Splitter.on(",").splitToList(coor);
                dto.setLon(Double.parseDouble(strList.get(0)));
                dto.setLat(Double.parseDouble(strList.get(1)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return dto;
    }
}

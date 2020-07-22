package allthings.iot.common.pojo;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

import java.io.Serializable;
import java.security.SecureRandom;

/**
 * @author :  sylar
 * @FileName :  MqttConst
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
public class DeviceGuid implements Serializable {

    public final static int VENDOR_LENGTH = 0;
    public final static int DEVICE_TYPE_LENGTH = 5;
    public final static int DEVICE_NUMBER_LENGTH = 12;
    public final static int GUID_LENGTH = VENDOR_LENGTH + DEVICE_TYPE_LENGTH + DEVICE_NUMBER_LENGTH;


    public final static String CLOUD_TYPE = "CLOUD";
    public final static String CLOUD_NUM = "TF56__PLAT";

    /**
     * 设备guid
     */
    private String guid;
    /**
     * 厂商
     */
    private String vendor;
    /**
     * 设备类型编码
     */
    private String devTypeId;
    /**
     * 设备编码
     */
    private String devNum;

    private DeviceGuid(String guid) {
        this.guid = guid;
        this.vendor = getVendor(guid);
        this.devTypeId = getDeviceTypeId(guid);
        this.devNum = getDeviceNumber(guid);
    }

    public static String getCloudType() {
        return CLOUD_TYPE;
    }

    public static String getCloudNum() {
        return CLOUD_NUM;
    }

    public static String getCloudGuid() {
        return CLOUD_TYPE + CLOUD_NUM;
    }

    public static DeviceGuid fromString(String guid) {
        if (checkValid(guid)) {
            return new DeviceGuid(guid);
        } else {
            return null;
        }
    }

    public static boolean checkValid(String guid) {
        if (Strings.isNullOrEmpty(guid)) {
            return false;
        }

        if (guid.length() != GUID_LENGTH) {
            return false;
        }

        return true;
    }

    public static String generateGuid(String deviceTypeId) {
        Preconditions.checkNotNull(deviceTypeId, "invalid deviceTypeId");
        Preconditions.checkState(deviceTypeId.length() == DEVICE_TYPE_LENGTH,
                "invalid deviceTypeId length:" + deviceTypeId);

        StringBuilder sb = new StringBuilder();
        //随机用以下三个随机生成器
        SecureRandom rand = new SecureRandom();
        SecureRandom randdata = new SecureRandom();
        int data = 0;
        for (int i = 0; i < DEVICE_NUMBER_LENGTH; i++) {
            int index = rand.nextInt(3);
            //目的是随机选择生成数字，大小写字母
            switch (index) {
                //0~9的ASCII为48~57
                case 0:
                    //仅仅会生成0~9
                    data = randdata.nextInt(10);
                    sb.append(data);
                    break;
                //A~Z的ASCII为65~90
                case 1:
                    //保证只会产生65~90之间的整数
                    data = randdata.nextInt(26) + 65;
                    sb.append((char) data);
                    break;
                //a~z的ASCII为97~122
                case 2:
                    //保证只会产生97~122之间的整数
                    data = randdata.nextInt(26) + 97;
                    sb.append((char) data);
                    break;
                default:
                    break;
            }
        }
        return deviceTypeId + sb.toString();
    }

    static String getVendor(String guid) {
        return guid.substring(0, VENDOR_LENGTH);
    }

    static String getDeviceTypeId(String guid) {
        return guid.substring(VENDOR_LENGTH, VENDOR_LENGTH + DEVICE_TYPE_LENGTH);
    }

    static String getDeviceNumber(String guid) {
        return guid.substring(VENDOR_LENGTH + DEVICE_TYPE_LENGTH);
    }

    /**
     * 获取厂商编码
     *
     * @return 厂商编码
     */
    public String getVendor() {
        return vendor;
    }

    /**
     * 获取设备类型
     *
     * @return 设备类型
     */
    public String getDeviceTypeId() {
        return devTypeId;
    }

    /**
     * 获取设备序号
     *
     * @return 设备序号
     */
    public String getDeviceNumber() {
        return devNum;
    }

    /**
     * 获取设备唯一编码
     *
     * @return 设备唯一编码
     */
    public String getGuid() {
        return guid;
    }


}
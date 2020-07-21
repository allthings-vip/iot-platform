package allthings.iot.bsj.data.util;

import allthings.iot.util.misc.ByteUtils;

/**
 * @author :  luhao
 * @FileName :  VipUtil
 * @CreateDate :  2018/1/12
 * @Description :  将设备号转换成虚拟ip
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
public class VipUtil {

    /**
     * 将终端手机号转成VIP
     *
     * @param tNo
     * @return
     */
    public static String deviceId2Vip(String tNo) {
        if (tNo == null || "".equals(tNo)) {
            return null;
        }

        if (tNo.length() > 11) {
            tNo = getLastCharacter(tNo, 11);
        }

        try {
            int[] sTemp = new int[4];
            int[] sIp = new int[4];
            int iHigt;
            if (tNo.length() == 11) {
                sTemp[0] = Integer.parseInt(tNo.substring(3, 5));
                sTemp[1] = Integer.parseInt(tNo.substring(5, 7));
                sTemp[2] = Integer.parseInt(tNo.substring(7, 9));
                sTemp[3] = Integer.parseInt(tNo.substring(9, 11));
                iHigt = Integer.parseInt(tNo.substring(1, 3)) - 30;
            } else if (tNo.length() == 10) {
                sTemp[0] = Integer.parseInt(tNo.substring(2, 4));
                sTemp[1] = Integer.parseInt(tNo.substring(4, 6));
                sTemp[2] = Integer.parseInt(tNo.substring(6, 8));
                sTemp[3] = Integer.parseInt(tNo.substring(8, 10));
                iHigt = Integer.parseInt(tNo.substring(0, 2)) - 30;
            } else if (tNo.length() == 9) {
                sTemp[0] = Integer.parseInt(tNo.substring(1, 3));
                sTemp[1] = Integer.parseInt(tNo.substring(3, 5));
                sTemp[2] = Integer.parseInt(tNo.substring(5, 7));
                sTemp[3] = Integer.parseInt(tNo.substring(7, 9));
                iHigt = Integer.parseInt(tNo.substring(0, 1));
            } else if (tNo.length() < 9) {
                switch (tNo.length()) {
                    case 8:
                        tNo = "140" + tNo;
                        break;
                    case 7:
                        tNo = "1400" + tNo;
                        break;
                    case 6:
                        tNo = "14000" + tNo;
                        break;
                    case 5:
                        tNo = "140000" + tNo;
                        break;
                    case 4:
                        tNo = "1400000" + tNo;
                        break;
                    case 3:
                        tNo = "14000000" + tNo;
                        break;
                    case 2:
                        tNo = "140000000" + tNo;
                        break;
                    case 1:
                        tNo = "1400000000" + tNo;
                        break;
                }
                sTemp[0] = Integer.parseInt(tNo.substring(3, 5));
                sTemp[1] = Integer.parseInt(tNo.substring(5, 7));
                sTemp[2] = Integer.parseInt(tNo.substring(7, 9));
                sTemp[3] = Integer.parseInt(tNo.substring(9, 11));
                iHigt = Integer.parseInt(tNo.substring(1, 3)) - 30;
            } else {
                return null;
            }

            if ((iHigt & 0x8) != 0) {
                sIp[0] = (sTemp[0] | 128);
            } else {
                sIp[0] = sTemp[0];
            }

            if ((iHigt & 0x4) != 0) {
                sIp[1] = sTemp[1] | 128;
            } else {
                sIp[1] = sTemp[1];
            }

            if ((iHigt & 0x2) != 0) {
                sIp[2] = sTemp[2] | 128;
            } else {
                sIp[2] = sTemp[2];
            }

            if ((iHigt & 0x1) != 0) {
                sIp[3] = sTemp[3] | 128;
            } else {
                sIp[3] = sTemp[3];
            }

            return Integer.toHexString(sIp[0])
                    + Integer.toHexString(sIp[1])
                    + Integer.toHexString(sIp[2])
                    + Integer.toHexString(sIp[3]);
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * 获取一个字符串最后 n 位字符
     *
     * @param str 字符串
     * @param n   多少位
     * @return
     */
    public static String getLastCharacter(String str, int n) {
        if (str == null || "".equals(str)) {
            return null;
        }

        int len = str.length();
        String deviceId;
        if (len >= n) {
            deviceId = str.substring(len - n);
        } else {
            StringBuffer sb = new StringBuffer(str);
            int index = n - len;
            for (int i = 0; i < index; i++) {
                sb.insert(0, "0");
            }

            deviceId = sb.toString();
        }

        return deviceId;
    }

    public static void main(String[] args) {
        byte[] ipBytes = ByteUtils.hexStringToBytes("a811ddcb");
        System.out.println();
    }

}

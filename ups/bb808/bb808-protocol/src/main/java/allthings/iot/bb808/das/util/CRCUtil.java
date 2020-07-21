package allthings.iot.bb808.das.util;

/**
 * @author :  luhao
 * @FileName :  CRCUtil
 * @CreateDate :  2017/12/21
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
public class CRCUtil {
    public static byte calGBT19056CRC(byte[] bytes){
        if(bytes != null && bytes.length > 0){
            byte crc = bytes[0];
            for(int i = 1; i < bytes.length; i++){
                crc ^= bytes[i];
            }
            return crc;
        }
        return 0x00;
    }
}

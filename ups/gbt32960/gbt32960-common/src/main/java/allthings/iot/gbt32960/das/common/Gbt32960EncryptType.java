package allthings.iot.gbt32960.das.common;

/**
 * @author :  luhao
 * @FileName :  Gbt32960EncryptType
 * @CreateDate :  2018/2/2
 * @Description : 报文加密方式
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) allthings-vip All Rights Reserved
 * *******************************************************************************************
 */
public interface Gbt32960EncryptType {
    /**
     * 不加密
     */
    int NOT_ENCRYPTION = 0x01;
    /**
     * rsa加密
     */
    int RSA_ENCRYPTION = 0x02;

    /**
     * aes128 加密
     */
    int AES128_ENCRYPTION = 0x03;
    /**
     * 加密异常
     */
    int EXCEPTION_ENCRYPTION = 0xFE;
    /**
     * 无效
     */
    int INVALID_ENCRYPTION = 0xFF;
}

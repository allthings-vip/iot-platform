package tf56.cloud.iot.store.toilet.common.dic;

import java.io.Serializable;

/**
 * @author :  sylar
 * @FileName :  DataTypeEnum
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
public enum DataTypeEnum implements Serializable {

    /**
     * 1byte
     */
    BYTE,

    /**
     * 1byte
     * true    - 0x01
     * false   - 0x00
     */
    BOOL,

    /**
     * 1byte 或 2byte
     */
    SHORT,

    /**
     * 4byte
     */
    INT,

    /**
     * 4byte
     */
    FLOAT,

    /**
     * 8byte
     */
    LONG,

    /**
     * 8byte
     */
    DOUBLE,

    /**
     * n byte
     */
    BYTES,

    /**
     * n byte
     */
    STRING,

    /**
     * nested param
     */
    LIST,
}

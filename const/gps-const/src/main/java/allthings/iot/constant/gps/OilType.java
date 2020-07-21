package allthings.iot.constant.gps;

/**
 * @author :  luhao
 * @FileName :  OilType
 * @CreateDate :  2017/12/21
 * @Description : 油量类型
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
public enum OilType {
    /**
     * 超声波
     */
    CSB,

    /**
     * 模拟量-油浮子
     */
    MNL,

    /**
     * 真实量，如升
     */
    ZSL,
    /**
     * 油位比例
     */
    BL
}
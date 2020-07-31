package allthings.iot.dos.dto.open;

import java.io.Serializable;

/**
 * @author :  txw
 * @FileName :  IotGpsFenceTaskDTO
 * @CreateDate :  2018/11/12
 * @Description :  dmp
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
public class IotGpsFenceTaskDTO implements Serializable {

    /**
     * 围栏编码
     */
    private String entityId;

    /**
     * 会员id
     */
    private String partyId;

    /**
     * 设备标识
     */
    private String deviceId;

    /**
     * 自定义/点围栏（圆/方）
     */
    private String fenceType;

    /**
     * 业务号
     */
    private String businessNumber;

    /**
     * 附加参数
     */
    private String callbackParams;

    /**
     * 回调地址
     */
    private String callbackUrl;

    /**
     * 触发类型
     */
    private Integer triggerType;

    /**
     * 触发次数
     */
    private Integer triggerCount;

    /**
     * 生效时间
     */
    private String validDate;

    /**
     * 失效时间
     */
    private String invalidDate;

    /**
     * 描述
     */
    private String description;

    /**
     * app名称
     */
    private String appId;

}

package allthings.iot.bb808.common;

/**
 * @author :  luhao
 * @FileName :  MsgCode
 * @CreateDate :  2017/12/21
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
public interface MsgCode {
    /**
     * 0x0001   终端通用应答
     */
    String MSG_DEVICE_GENERAL_RES = "0001";

    /**
     * 0x0002   终端心跳
     */
    String MSG_HEARTBEAT = "0002";

    /**
     *  0x0003  终端注销
     */
    String MSG_DEV_CANCEL = "0003";

    /**
     * 0x0100   终端注册
     */
    String MSG_REGISTER = "0100";

    /**
     * 0x0102   终端鉴权
     */
    String MSG_LOGIN = "0102";

    /**
     * 0x0104    查询终端参数应答
     */
    String MSG_SEARCH_TERMINAL_PARAMETER_RES = "0104";

    /**
     *  0X0107 查询终端属性应答
     */
    String MSG_SEARCH_TERMINAL_ATTRIBUTE_RES = "0107";

    /**
     *  0X0108 终端升级结果通知
     */
    String MSG_UPGRADE_RESULT = "0108";

    /**
     * 0x0200   位置信息汇报
     */
    String MSG_POSITION_UPLOAD = "0200";

    /**
     * 0x0201   位置信息查询应答
     */
    String MSG_POSITION_RES = "0201";

    /**
     * 0x0301   事件上报
     */
    String MSG_EVENT_UPLOAD = "0301";

    /**
     * 0x0302   提问应答
     */
    String MSG_QES_ANSWER = "0302";

    /**
     * 0x0303   信息点播/取消
     */
    String MSG_INFO_SUB = "0303";

    /**
     * 0x0500   终端控制应答
     */
    String MSG_CONTROL_RES = "0500";

    /**
     * 0x0500   行驶记录仪数据上传
     */
    String MSG_RECORDER_DATA_UPLOAD = "0700";

    /**
     * 0x0701   电子运单上传
     */
    String MSG_WAYBILL_UPLOAD = "0701";


    /**
     * 0x0702   驾驶员身份信息采集上报
     */
    String MSG_DRIVER_IC = "0702";

    /**
     * 0x0704   定位数据批量上传
     */
    String MSG_POSITION_BATCH_UPLOAD = "0704";

    /**
     * 0x0704   CAN总线数据上传
     */
    String MSG_CAN_DATA_UPLOAD = "0705";

    /**
     * 0x0800  多媒体事件上传
     */
    String MSG_MULTIMEDIA_EVENT_UPLOAD = "0800";

    /**
     * 0x0801  多媒体数据上传
     */
    String MSG_MULTIMEDIA_UPLOAD = "0801";

    /**
     * 0x0802   存储多媒体数据检索应答
     */
    String MSG_MULTIMEDIA_SEARCH_RES = "0802";

    /**
     * 0x0805  摄像头立即拍摄命令应答
     */
    String MSG_TAKE_PHOTO_RES = "0805";

    /**
     * 0x8001   平台通用应答
     */
    String MSG_PLAT_GENERAL_RES = "8001";

    /**
     *  0x8003 补传分包
     */
    String MSG_RESEND_PACKET = "8003";

    /**
     * 0x8100   终端注册应答
     */
    String MSG_REGISTER_RES = "8100";

    /**
     * 0x8104  查询终端参数
     */
    String MSG_SEARCH_DEVICE_PARAM = "8104";

    /**
     * 0x8106   查询指定终端参数
     */
    String MSG_SEARCH_SPECIFY_DEVICE_PARAM = "8106";

    /**
     * 0x8107   查询终端属性
     */
    String MSG_SEARCH_DEVICE_ATTR = "8107";

    /**
     * 0x8800   多媒体数据上传应答   服务器发起
     */
    String MSG_MULTIMEDIA_UPLOAD_RESPONSE = "8800";

    /**
     * 0x8900   数据下行透传
     */
    String MSG_TRANSPARENT_TRANSMISSION_DOWN = "8900";

    /**
     * 0x0900   数据上行透传
     */
    String MSG_TRANSPARENT_TRANSMISSION_UP = "0900";

    /**
     *  0x0901 数据压缩上报
     */
    String MSG_GZIP_DATA_UPLOAD = "0901";

    /**
     *  0X0A00 终端RSA公钥
     */
    String MSG_DEV_RSA = "0A00";
}
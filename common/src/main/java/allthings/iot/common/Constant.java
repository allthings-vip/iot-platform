package allthings.iot.common;

/**
 * @author :  luhao
 * @FileName :  Constant
 * @CreateDate :  2018/1/4
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
public interface Constant {
    /**
     * 设备id
     */
    String ATTR_DEVICE_ID = "device_id";

    /**
     * 扩展内容长度
     */
    String ATTR_EX_LEN = "ex_len";

    /**
     * 扩展内容指令
     */
    String ATTR_EX_ORDER = "ex_order";

    /**
     * 扩展内容
     */
    String ATTR_EX_CONTENT = "ex_content";

    /**
     * crc内容
     */
    String ATTR_REP_CRC = "rep_crc";

    /**
     * 指令码
     */
    String ATTR_MSG_CODE = "msg_code";
    /**
     * 子指令码
     */
    String ATTR_CHILD_MSG_CODE = "child_msg_code";

    /**
     * 设备缓存key
     */
    String DEVICE_CACHE_KEY = "DEVICE_CACHE_KEY";

    /**
     * 超时时间
     */
    String ATTR_TIMEOUT = "timeout";
}

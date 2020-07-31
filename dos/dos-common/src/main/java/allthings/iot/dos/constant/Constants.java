package allthings.iot.dos.constant;

/**
 * @author :  luhao
 * @FileName :  Constants
 * @CreateDate :  2018-5-11
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
public interface Constants {

    /**
     * 设备类型key
     */
    String DEVICE_TYPE_KEY = "DEVICE_TYPE_KEY";

    /**
     * 权限过滤白名单列表
     */
    String URI_LIST_PARAM = "whiteUrlList";

    /**
     * context-path
     */
    String CONTEXT_PATH = "contextPath";

    /**
     *
     */
    String HOME_ROUTE = "/";

    /**
     * 权限列表
     */
    String AUTHORITY_LIST = "authority";

    /**
     * 空ID
     */
    Long LONG_OF_NULL = -9999L;

    /**
     * Redis数据点数Key
     */
    String DOS_PROJECT_POINT_TOTAL_COUNT_KEY = "dos_project_point_total_count_key";

    String DOS_PROJECT_POINT_TODAY_COUNT_KEY = "dos_project_point_today_count_key";

    String DOS_POINT_COUNT_KEY = "dos_point_count_key";

    String POINT_TOTAL_COUNT = "point_total_count";

    String POINT_TODAY_COUNT = "point_today_count";

    /**
     * 设备新增发消息task group
     */
    String DOS_SERVICE_TO_TASK_GROUP = "iot-dosservice-to-dostask-group";

    /**
     * 设备新增发消息task topic
     */
    String DOS_SERVICE_TO_TASK_TOPIC = "IOT-DosServiceToDosTask";

    /**
     * 设备日志类型编码
     */
    String DEVICE_LOGGER_TYPE = "deviceLogger";

    /**
     * 系统日志类型编码
     */
    String SYSTEM_LOGGER_TYPE = "systemLogger";

    /**
     * 删除操作
     */
    int DELETED_OPERATE = -1;
    /**
     * 创建操作
     */
    int CREATE_OPERATE = 1;
    /**
     * 更新操作
     */
    int UPDATE_OPERATE = 0;

    /**
     * 一小时的秒数
     */
    int HOUR = 3600;

    /**
     * mq存储结果保存时间（秒）
     */
    int VIS_RESULT_LIVE = 30;

    /**
     * 循环获取VIS结果超时时间（毫秒）
     */
    int GET_VIS_TIMEOUT = 3000;

    /**
     * 日志消息主题
     */
    String IOT_DOS_LOGGER_TOPIC = "iot-dos-logger-topic";

    /**
     * 日志消息分组
     */
    String IOT_DOS_LOGGER_GROUP = "iot-dos-logger-group";

    /**
     * vis到dos的消息主题
     */
    String IOT_AEP_TO_DOS_TOPIC = "IOT-AepToAps";

    /**
     * vis到dos的消息分组
     */
    String IOT_AEP_TO_DOS_GROUP = "iot-aep-to-aps-group";

    /**
     * dos到vis消息主题
     */
    String IOT_DOS_TO_AEP_TOPIC = "IOT-ApsToAep";

    /**
     * dos到vis消息分组
     */
    String IOT_DOS_TO_AEP_GROUP = "iot-aps-to-aep-group";

    /*********************监控包使用常量******************/

    /**
     * 监控信息缓存key
     */
    String IOT_DOS_SERVICE_INFO = "iot_dos_service_info";

    String IOT_DOS_MONITOR_GROUP = "iot-dos-monitor-group";

    String IOT_DOS_MONITOR_GROUP_CONSUMER = "iot-dos-monitor-group-consumer";

    String IOT_DOS_MONITOR_TOPIC = "iot-dos-monitor-topic";

    String IOT_DOS_SERVICE_INFO_ALL = "all";

    String IOT_DOS_SERVICE_INFO_SIMPLE = "simple";

    String IOT_DOS_PLATFORM_STATUS = "platform";

    /***************************************************/
}

package allthings.iot.bb808.common;

/**
 * @author :  luhao
 * @FileName :  MsgParam
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
public interface MsgParam {
    /**
     * 消息头 - 消息ID
     */
    String MSG_TAG = "messageTag";

    /**
     * 消息头 - 消息体属性
     */
    String MSG_ATTR = "messageAttr";

    /**
     * 消息头 - 终端手机号
     */
    String PHONE_NO = "phoneNo";

    /**
     * 消息头 - 消息流水号
     */
    String RUNNING_NO = "runningNo";

    /**
     * 消息头 - 消息体属性：消息体长度
     */
    String MSG_BODY_LENGTH = "messagebodyLength";

    /**
     * 消息头 - 消息体属性：分包之消息总包数
     */
    String MSG_TOTAL_CNT = "messageTotalCount";

    /**
     * 消息头 - 消息体属性：分包之包序号，取值范围：[1, 分包总包数]
     */
    String MSG_INDEX = "messageIndex";

    /**
     * 0x0001/0x8001/0X0104/0X0302/0X0201 - 终端/平台通用应答/查询终端参数应答/提问应答/位置信息查询应答  应答流水号
     */
    String ACK_RUNNING_NO = "ackRunningNo";

    /**
     * 0x0001/0x8001 - 终端/平台通用应答  应答ID    对应终端/平台消息的ID
     */
    String ACK_ID = "ackId";

    /**
     * 0x0001/0x8001 - 终端/平台通用应答  结果
     */
    String ACK_RESULT = "result";

    /**
     * 0x0100 - 终端注册    省域ID  0 保留，由平台去默认值
     */
    String PROVINCE_ID = "provinceId";

    /**
     * 0x0100 - 终端注册    市县域ID  0 保留，由平台去默认值
     */
    String CITY_ID = "cityId";

    /**
     * 0x0100/0X0107/0X8105/0x8108 - 终端注册/查询终端属性应答/终端控制/下发终端升级包    制造商ID 5位
     */
    String MAKER_ID = "makerId";

    /**
     * 0x0100/0107 - 终端注册/查询终端属性应答    终端型号
     */
    String DEV_TYPE = "devType";

    /**
     * 0x0100/0107 - 终端注册/查询终端属性应答    终端ID
     */
    String DEV_CODE = "devCode";

    /**
     * 0x0100 - 终端注册    车牌颜色  未上牌时，取值为0
     */
    String CAR_CODE_COLOR = "carCodeColor";

    /**
     * 0x0100 - 终端注册    车牌
     */
    String CAR_CODE = "carCode";

    /**
     * 0x0100 - 终端注册    VIN
     */
    String VIN_CODE = "vinCode";

    /**
     * 0x0702 - 驾驶员身份信息采集上报 状态
     * 0x01  IC 卡插入（驾驶员上班）
     * 0x02  IC 卡拔出（驾驶员下班）
     */
    String IC_STATE = "icState";

    /**
     * 0x0702 - 驾驶员身份信息采集上报 插卡/拔卡时间 YY-MM-DD-hh-mm-ss
     */
    String IC_OPERATE_DATE = "icOperateDate";

    /**
     * 0x0702 - 驾驶员身份信息采集上报 IC 卡读取结果
     */
    String IC_READ_RESULT = "icReadResult";

    /**
     * 0x0702 - 驾驶员身份信息采集上报 驾驶员姓名长度
     */
    String IC_DRIVER_NAME_LENGTH = "icDriverNameLength";

    /**
     * 0x0702 - 驾驶员身份信息采集上报 驾驶员姓名
     */
    String IC_DRIVER_NAME = "icDriverName";

    /**
     * 0x0702 - 驾驶员身份信息采集上报 从业资格证编码
     */
    String IC_CODE = "icCode";

    /**
     * 0x0702 - 驾驶员身份信息采集上报 发证机构名称长度
     */
    String IC_CA_NAME_LENGTH = "icCaNameLength";

    /**
     * 0x0702 - 驾驶员身份信息采集上报 发证机构名称
     */
    String IC_CA_NAME = "icCaName";

    /**
     * 0x0702 - 驾驶员身份信息采集上报 证件有效期
     */
    String IC_EXPIRY_DATE = "icExpiryDate";

    /**
     * 0x0704 - 定位数据批量上传    位置汇报数据项个数
     */
    String POSITION_UPLOAD_BATCH_ITEM_CNT = "itemCnt";

    /**
     * 0x0704 - 定位数据批量上传    位置数据类型  0：正常位置批量汇报，1：盲区补报
     */
    String POSITION_UPLOAD_BATCH_TYPE = "batchType";

    /**
     * 0x8100 - 终端注册应答  鉴权码
     */
    String JIAN_QUAN = "jianQuan";

    /**
     * 0x8300 - 文本信息下发  标志
     */
    String TEXT_SEND_FLAG = "textSendFlag";

    /**
     * 0x8300 - 文本信息下发  文本信息
     */
    String TEXT_SEND_MSG = "textSendMsg";

    /**
     * 0x8400 - 电话回拨  标志    0:普通通话；1:监听
     */
    String CALL_MODE = "callMode";

    /**
     * 0x8400 - 电话回拨  电话号码
     */
    String CALL_PHONE_NO = "callPhoneNo";

    /**
     * 0x8900/0x0900 - 数据下行/上行透传  透传消息内容
     */
    String TRANSPARENT_TRANSMISSION_CONTENT = "transparentTransmissionContent";

    /**
     * 0x8801/0x8803/0x8802/0x0802/0x0800 - 摄像头立即拍摄命令/存储多媒体数据上传命令/存储多媒体数据检索/存储多媒体数据检索应答/多媒体事件信息上传  通道 ID
     */
    String CHANNEL_ID = "channelId";

    /**
     * 0x8801 - 摄像头立即拍摄命令  拍摄命令 0 表示停止拍摄；0xFFFF 表示录像；其它表示拍照张数
     */
    String SNAP_CMD = "snapCmd";

    /**
     * 0x8801 - 摄像头立即拍摄命令  拍照间隔/录像时间    秒，0 表示按最小间隔拍照或一直录像
     */
    String SNAP_INTERVAL = "snapInterval";

    /**
     * 0x8801 - 摄像头立即拍摄命令  保存标志     1：保存；0：实时上传
     */
    String STORE_WAY = "storeWay";

    /**
     * 0x8801 - 摄像头立即拍摄命令  分辨率
     */
    String RESOLUTION = "resolution";

    /**
     * 0x8801 - 摄像头立即拍摄命令  图像/视频质量  1-10，1 代表质量损失最小，10 表示压缩比最大
     */
    String COMPRESSION_RATIO = "compressionRatio";

    /**
     * 0x8801 - 摄像头立即拍摄命令  亮度   0-255
     */
    String BRIGHTNESS = "brightness";

    /**
     * 0x8801 - 摄像头立即拍摄命令  对比度   0-127
     */
    String CONTRAST_RATIO = "contrastRatio";

    /**
     * 0x8801 - 摄像头立即拍摄命令  饱和度   0-127
     */
    String SATURATION = "saturation";

    /**
     * 0x8801 - 摄像头立即拍摄命令  色度   0-255
     */
    String CHROMA = "chroma";

    /**
     * 0x0801/0x8805/0x0802/0x0800 - 多媒体数据上传/单条存储多媒体数据上传命令/存储多媒体数据检索应答/多媒体事件信息上传  多媒体ID
     */
    String MULTIMEDIA_DATA_ID = "multimediaDataId";

    /**
     * 0x0801/0x8803/0x0802/0x0800 - 多媒体数据上传/存储多媒体数据上传命令/存储多媒体数据检索应答/多媒体事件信息上传 多媒体类型  0：图像；1：音频；2：视频；
     */
    String MULTIMEDIA_TYPE = "multimediaType";

    /**
     * 0x0801/0x0800 - 多媒体数据上传/多媒体事件信息上传  多媒体格式编码    0：JPEG；1：TIF；2：MP3；3：WAV；4：WMV；
     */
    String MULTIMEDIA_FORMAT = "multimediaFormat";

    /**
     * 0x0801/0x8803/0x8802/0x0802/0x0800  - 多媒体数据上传/存储多媒体数据上传命令/存储多媒体数据检索/存储多媒体数据检索应答/多媒体事件信息上传  事件项编码    0：平台下发指令；1：定时动作；2：抢劫报警触发；3：碰撞侧翻报警触发；其他保留
     */
    String MULTIMEDIA_EVENT = "multimediaEvent";

    /**
     * 0x0801 - 多媒体数据上传  多媒体数据包
     */
    String MULTIMEDIA_DATA = "multimediaData";

    /**
     * 0X8800 - 多媒体数据上传应答  重传包总数
     */
    String RETRANS_PACKET_COUNT = "retransPacketCount";

    /**
     * 0X8800 - 多媒体数据上传应答  重传包ID 列表
     */
    String RETRANS_PACKET_ID_LIST = "retransPacketIdList";

    /**
     * 0X8106 - 查询指定终端参数  参数总数
     */
    String TERMINAL_PARAMETERS_COUNT = "terminalParametersCount";

    /**
     * 0X8106 - 查询指定终端参数  参数ID列表
     */
    String TERMINAL_PARAMETERS_ID_LIST = "terminalParametersIdList";

    /**
     * 0X0104 - 查询终端参数应答 应答参数个数
     */
    String ANSWER_PARAMETER_COUNT = "answerParameterCount";

    /**
     * 0X0104 - 查询终端参数应答 应答参数列表
     */
    String ANSWER_PARAMETER_LIST = "answerParameterList";

    /**
     * 0X8103 - 设置指定终端参数 参数项键值对
     */
    String TERMINAL_PARAMETERS_MAP = "terminalParametersMap";

    /**
     * 0X0107 -  查询终端属性应答 终端类型
     */
    String DEV_GENRE = "devGenre";


    /**
     * 0X0107 - 查询终端属性应答 终端SIM 卡ICCID
     */
    String DEV_SIM_ICCID = "devSimIccid";

    /**
     * 0X0107/0X8105 - 查询终端属性应答/终端控制 终端硬件版本
     */
    String DEV_HARDWARE_VERSION = "devHardwareVersion";

    /**
     * 0X0107/0X8105 - 查询终端属性应答/终端控制  终端固件版本
     */
    String DEV_FIRMWARE_VERSION = "devFirmwareVersion";

    /**
     * 0X0107 - 查询终端属性应答 GNSS 模块属性
     */
    String GNSS_MODULE_ATTRIBUTES = "gnssModuleAttributes";

    /**
     * 0X0107 - 查询终端属性应答 通信模块属性
     */
    String COMMUNICATION_MODULE_ATTRIBUTES = "communicationModuleAttributes";

    /**
     * 0X8105/0X8700  - 终端控制/行驶记录数据采集命令 命令字
     */
    String COMMAND_WORD = "commandWord";

    /**
     * 0X8106 - 终端控制 命令参数
     */
    String COMMAND_PARAM = "commandParam";

    /**
     * 0X8106 - 终端控制 URL地址
     */
    String URL = "url";

    /**
     * 0X8106 - 终端控制 拨号点名称
     */
    String DIAL_POINT_NAME = "dialPointName";

    /**
     * 0X8106 - 终端控制 拨号用户名
     */
    String DIAL_POINT_USERNAME = "dialPointUserame";

    /**
     * 0X8106 - 终端控制 拨号密码
     */
    String DIAL_POINT_PWD = "dialPointPwd";

    /**
     * 0X8106 - 终端控制 地址
     */
    String ADDR = "addr";

    /**
     * 0X8106 - 终端控制 TCP 端口
     */
    String TCP_PORT = "tcpPort";

    /**
     * 0X8106 - 终端控制 UDP 端口
     */
    String UDP_PORT = "udpPort";


    /**
     * 0X8106 - 终端控制 连接到指定服务器时限
     */
    String CONNECT_TIME_LIMIT = "connectTimeLimit";

    /**
     * 0X8106 - 终端控制 连接控制
     */
    String CONNECT_CONTROL = "connectControl";

    /**
     * 0X8106 - 终端控制 监管平台鉴权码
     */
    String PLAT_JIANQUAN = "platJianquan";

    /**
     * 消息id
     */
    String MSG_ID = "msgId";

    /**
     * 反控指令流水号
     */
    String CMD_RUNNING_NO = "cmdRunningNo";

    /**
     * 0X8301 事件设置 设置类型 0：删除终端现有所有事件 1：更新事件 2：追加事件 3：修改事件 4：删除特定几项事件
     */
    String EVENT_SET_TYPE = "eventSetType";

    /**
     * 0X8301 事件设置 事件项列表
     */
    String EVENT_LIST = "eventList";

    /**
     * 0X8301/0X0301 事件设置/事件上报 事件ID
     */
    String EVENT_ID = "eventId";

    /**
     * 0X8301 事件设置 事件内容
     */
    String EVENT_CONTENT = "eventContent";

    /**
     * 0X8203 人工报警确认 报警类型
     */
    String ALARM_TYPE = "alarmType";

    /**
     * 0X8203 人工报警确认 报警流水号
     */
    String ALARM_RUNNING_NO = "alarmRunningNo";

    /**
     * 0X8202 临时位置跟踪 事件间隔 0为停止跟踪
     */
    String TIME_INTERVAL = "timeInterval";

    /**
     * 0X8202 临时位置跟踪 位置跟踪有效期
     */
    String EFFECTIVE_TIME = "effectiveTime";

    /**
     * 0X8302 提问下发 标志 位0：紧急 位3：终端TTS 播读 位4：广告屏显示
     */
    String QES_FLAG = "qesFlag";


    /**
     * 0X8302 提问下发 问题
     */
    String QES_CONTENT = "qesContent";

    /**
     * 0X8302 提问下发 候选答案列表
     */
    String ANSWER_LIST = "answerList";

    /**
     * 0X8302 提问下发 答案Id
     */
    String ANSWER_ID = "answerId";

    /**
     * 0X8302 提问下发 答案内容
     */
    String ANSWER_CONTENT = "answerContent";

    /**
     * 0X8303 信息点播菜单设置 -- 设置类型 0：删除终端全部信息项；     1：更新菜单；     2：追加菜单；     3：修改菜单
     */
    String INFO_SET_TYPE = "informationSetType";

    /**
     * 0X8303 信息点播菜单设置 -- 信息项列表
     */
    String INFO_LIST = "informationList";

    /**
     * 0X8303/0X0303/0X8304 信息点播菜单设置/信息点播/取消/信息服务 -- 信息类型
     */
    String INFO_TYPE = "informationType";

    /**
     * 0X8303 信息点播菜单设置 -- 信息名称
     */
    String INFO_NAME = "informationName";

    /**
     * 0X0303 信息点播/取消 点播/取消 标志 0：取消；1：点播
     */
    String INFO_ON_OFF = "onformationOnOff";

    /**
     * 0X8304 信息服务 信息内容
     */
    String INFO_CONTENT = "informationContent";

    /**
     * 0X8401 电话本设置 -- 设置类型
     * 0：删除终端上所有存储的联系人；
     * 1：表示更新电话本（删除终端中已有全部联系人
     * 并追加消息中的联系人）；
     * 2：表示追加电话本；
     * 3：表示修改电话本（以联系人为索引）
     */
    String CONTANT_SET_TYPE = "contantSetType";

    /**
     * 0X8401 电话本设置 -- 联系人列表
     */
    String CONTANT_LIST = "contantList";

    /**
     * 0X8401 电话本设置 -- 联系人标志 1：呼入；2：呼出；3：呼入/呼出
     */
    String CONTANT_FLAG = "contantFlag";

    /**
     * 0X8401 电话本设置 -- 联系人电话号码
     */
    String CONTANT_PHONE = "contantPhone";

    /**
     * 0X8401 电话本设置 -- 联系人
     */
    String CONTENT_NAME = "contantName";

    /**
     * 0X8500 终端控制 --控制标志  位0：0：车门解锁；1：车门加锁
     */
    String CONTROL_FLAG = "controlFlag";

    /**
     * 0X0500 终端控制 --车门加锁与否
     */
    String DOOR_LOCK = "doorLock";

    /**
     * 0X0705 CAN总线数据上传 -- CAN 总线数据接收时间
     */
    String CAN_TIME = "canTime";

    /**
     * 0X0705 CAN总线数据上传 -- CAN 通道 0：CAN1，1：CAN2；
     */
    String CAN_CHANNEL = "canChannel";

    /**
     * 0X0705 CAN总线数据上传 -- CAN 数据采集方式
     */
    String CAN_COLLECT_TYPE = "canCollectType";

    /**
     * 0X0705 CAN总线数据上传 -- CAN ID
     */
    String CAN_ID = "canId";

    /**
     * 0X0705 CAN总线数据上传 -- 帧类型 0：标准帧，1：扩展帧；
     */
    String CAN_FRAME_TYPE = "canFrameType";

    /**
     * 0X0705 CAN总线数据上传 -- CAN 数据；
     */
    String CAN_DATA = "canData";

    /**
     * 0X0705 CAN总线数据上传 -- CAN DATA LIST
     */
    String CAN_DATA_LIST = "canDataList";

    /**
     * 0X8600/0X8602/0X8604/0X8606 设置圆形区域/设置矩形区域/设置多边形区域/设置路段 -- 设置属性 0 ：更新 1 ：追加； 2 ：修改
     */
    String SET_ATTR = "settingAttribute";

    /**
     * 0X8600/0X8602/0X8604 设置圆形区域/设置矩形区域/设置多边形区域 -- 区域列表
     */
    String AREA_LIST = "areaList";

    /**
     * 0X8600/0X8602/0X8604 设置圆形区域/设置矩形区域/设置多边形区域 -- 区域ID
     */
    String AREA_ID = "areaId";

    /**
     * 0X8600/0X8602/0X8604  设置圆形区域/设置矩形区域/设置多边形区域 -- 区域属性
     */
    String AREA_ATTR = "areaAttribute";

    /**
     * 0X8600 设置圆形区域 -- 中心点经度
     */
    String CENTER_LNG = "centerLng";

    /**
     * 0X8600 设置圆形区域 -- 中心点纬度
     */
    String CENTER_LAT = "centerLat";

    /**
     * 0X8600/ 设置圆形区域 -- 半径
     */
    String RADIUS = "radius";

    /**
     * 0X8600/0X8602/0X8604/0X8606/0x8803/0x8802 设置圆形区域/设置矩形区域/设置多边形区域/设置路段/存储多媒体数据上传命令/存储多媒体数据检索 -- 起始时间
     */
    String START_TIME = "startTime";

    /**
     * 0X8600/0X8602/0X8604/0X8606/0x8803/0x8802 设置圆形区域/设置矩形区域/设置多边形区域/设置路段/存储多媒体数据上传命令/存储多媒体数据检索 -- 结束时间
     */
    String END_TIME = "endTime";

    /**
     * 0X8600/0X8602/0X8604/0X8606 设置圆形区域/设置矩形区域/设置多边形区域/设置路段 -- 最高速度
     */
    String MAX_SPEED = "maxSpeed";

    /**
     * 0X8600/0X8602/0X8604/0X8606 设置圆形区域/设置矩形区域/设置多边形区域/设置路段 -- 超速持续时间
     */
    String OVERSPEED_TIME = "overSpeedTime";

    /**
     * 0X8601/0X8603/0X8605/0X8607 删除圆形区域/删除矩形区域/删除多边形区域/删除路段 -- 删除区域ID表
     */
    String DELETE_AREA_LIST = "deleteAreaList";

    /**
     * 0X8602/0X8604/0X8606 设置矩形区域/设置多边形区域/设置路段 顶点列表
     */
    String POINT_LIST = "pointList";

    /**
     * 0X8602/0X8604/0X8606 设置矩形区域/设置多边形区域/设置路段 顶点经度
     */
    String POINT_LNG = "pointLng";

    /**
     * 0X8602/0X8604/0X8606 设置矩形区域/设置多边形区域/设置路段 顶点纬度
     */
    String POINT_LAT = "pointLat";

    /**
     * 0X8606 设置路线 路线ID
     */
    String ROUTE_ID = "routeId";

    /**
     * 0X8606 设置路线 路线属性
     */
    String ROUTE_ATTR = "routeAttribute";

    /**
     * 0X8606 设置路线 路线列表
     */
    String ROUTE_LIST = "routeList";

    /**
     * 0X8606 设置路线 拐点ID
     */
    String POINT_ID = "pointId";

    /**
     * 0X8606 设置路线 路段ID
     */
    String ROAD_ID = "roadId";

    /**
     * 0X8606 设置路线 路段宽度
     */
    String ROAD_WIDTH = "roadWidth";

    /**
     * 0X8606 设置路线 路段属性
     */
    String ROAD_ATTR = "roadAttribute";

    /**
     * 0X8606 设置路线 路段行驶过长阈值
     */
    String ROAD_MAX_TIME = "roadMaxTime";

    /**
     * 0X8606 设置路线 路段行驶不足阈值
     */
    String ROAD_MIN_TIME = "roadMinTime";

    /**
     * 0X0700 行驶记录数据上传 记录仪状态 00：正常 01:命令不匹配 02：包头不匹配 03：CRC校验失败
     */
    String RECODER_FLAG = "recoderFlag";

    /**
     * 0X0701 电子运单上报 电子运单内容
     */
    String WAYBILL_CONTENT = "waybillContent";

    /**
     * 0X8804 录音开始命令 录音命令 0：停止录音；0x01：开始录音；
     */
    String RECODING_COMMAND = "recodingCommand";

    /**
     * 0X8804 录音开始命令 录音时间 单位为秒（s），0 表示一直录音
     */
    String RECODING_TIME = "recodingTime";

    /**
     * 0X8804 录音开始命令 保存标志 0：实时上传；1：保存
     */
    String RECODING_SAVE_FLAG = "recodingSaveFlag";

    /**
     * 0X8804 录音开始命令 音频采样率 0：8K；1：11K；2：23K；3：32K；其他保留
     */
    String RECODING_AUDIO_SAMPLING_RATE = "recodingAudioSamplingRate";

    /**
     * 0X8803/0X8805 存储多媒体数据上传命令/单条存储多媒体数据上传命令 删除标志 0：保留；1：删除；
     */
    String DELETE_FLAG = "deleteFlag";

    /**
     * 0x0802 存储多媒体数据检索应答 检索项列表
     */
    String MULTIMEDIA_SEARCH_ITEMS = "multimediaSearchItems";

    /**
     * 0X0901 数据压缩上报 压缩消息体
     */
    String GZIP_DATA = "gzipData";


    /**
     * 0x8A00 平台RSA 公钥 平台RSA 公钥{e,n}中的e
     */
    String PLAT_RSA_E = "platRsaE";

    /**
     * 0x8A00 平台RSA 公钥 平台RSA 公钥{e,n}中的n
     */
    String PLAT_RSA_N = "platRsaN";

    /**
     * 0x0A00 终端RSA 公钥 终端RSA 公钥{e,n}中的e
     */
    String DEV_RSA_E = "devRsaE";

    /**
     * 0x0A00 终端RSA 公钥 终端RSA 公钥{e,n}中的n
     */
    String DEV_RSA_N = "devRsaN";

    /**
     * 0x8108/0x0108 下发终端升级包/终端升级结果通知 升级类型 0：终端，12：道路运输证IC 卡读卡器，52：北斗卫星定位模块
     */
    String UPGRADE_TYPE = "upgradeType";

    /**
     * 0x8108 下发终端升级包 版本号
     */
    String VERSION_NO = "versionNo";

    /**
     * 0x8108 下发终端升级包 升级数据包
     */
    String UPGRADE_DATA = "upgradeData";

    /**
     * 0x0108 终端升级结果通知 升级结果 0：成功，1：失败，2：取消
     */
    String UPGRADE_RESULT = "upgradeResult";

    /**
     * 0x8003 补传分包 原始消息流水号
     */
    String ORIGIN_RUNNING_NO = "originRunningNo";

    /**
     * 0x8003 补传分包 重传包id列表
     */
    String RESEND_ID_LIST = "resendIdList";

    /**
     * 0x0805 摄像头立即拍摄应答命令 结果 0：成功；1：失败；2：通道不支持。
     */
    String TAKE_PHONE_RESULT = "takePhotoResult";

    /**
     * 0x0805 摄像头立即拍摄应答命令 多媒体id列表
     */
    String MULTIMEDIA_ID_LIST = "multimediaIdList";

    /**
     * guid 设备类型+设备号
     */
    String GUID = "guid";

    /**
     * 回应的消息Id
     */
    String ACK_MSG_ID = "ackMsgId";

}

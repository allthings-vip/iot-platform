package allthings.iot.dms.service;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import allthings.iot.common.dto.QueryResult;
import allthings.iot.dms.IDeviceLocationService;
import allthings.iot.dms.IDeviceManageService;
import allthings.iot.dms.IDeviceOwnerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import allthings.iot.common.msg.*;
import allthings.iot.dms.dto.*;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author :  sylar
 * @FileName :  DeviceManageServiceImpl
 * @CreateDate :  2017/11/08
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

@Component("dms")
public class DeviceManageServiceImpl implements IDmsMsgProcessor<IMsg>, IDeviceManageService {

    private static final Logger LOG = LoggerFactory.getLogger(DeviceManageServiceImpl.class);

    @Autowired
    private MsgLogServiceImpl msgLogServiceImpl;

    @Autowired
    private DasConnectionLogServiceImpl dasConnectionLogServiceImpl;

    @Autowired
    private DasStatusServiceImpl dasStatusServiceImpl;

    @Autowired
    private DeviceAlarmServiceImpl deviceAlarmServiceImpl;

    @Autowired
    private DeviceConnectionLogServiceImpl deviceConnectionLogServiceImpl;

    @Autowired
    private DeviceDataService deviceDataService;

    @Autowired
    private DeviceEventServiceImpl deviceEventServiceImpl;

    @Autowired
    private DeviceInfoServiceImpl deviceInfoServiceImpl;

    @Autowired
    private DeviceLogServiceImpl deviceLogServiceImpl;

    @Autowired
    private DeviceMessageServiceImpl deviceMessageServiceImpl;

    @Autowired
    private DeviceOtaServiceImpl deviceOtaServiceImpl;

    @Autowired
    private DeviceStatusServiceImpl deviceStatusServiceImpl;

    @Autowired
    private DeviceTokenServiceImpl deviceTokenServiceImpl;

    @Autowired
    private IDeviceOwnerService deviceOwnerService;

    @Autowired
    private IDeviceLocationService deviceLocationService;

    private Executor executor = new ThreadPoolExecutor(1, Runtime.getRuntime().availableProcessors() * 2,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(), new ThreadFactoryBuilder()
            .setNameFormat("dms-save-msg-log-%d").build());

    @Override
    public void processMsg(IMsg msg) throws Exception {
        LOG.info("DMS process msg\n{}", msg);
        executor.execute(new Runnable() {
            @Override
            public void run() {
                msgLogServiceImpl.processMsg(msg);
            }
        });

        switch (msg.getMsgType()) {
            case Undefine:
                break;
            case DasConnection:
                dasConnectionLogServiceImpl.processMsg((DasConnectionMsg) msg);
                break;
            case DeviceConnection:
                deviceConnectionLogServiceImpl.processMsg((DeviceConnectionMsg) msg);
                break;
            case DeviceAlarm:
                deviceAlarmServiceImpl.processMsg((DeviceAlarmMsg) msg);
                break;
            case DeviceData:
                deviceDataService.processMsg((DeviceDataMsg) msg);
                break;
            case DeviceEvent:
                deviceEventServiceImpl.processMsg((DeviceEventMsg) msg);
                break;
            case DeviceInfo:
                deviceInfoServiceImpl.processMsg((DeviceInfoMsg) msg);
                break;
            case DeviceLog:
                deviceLogServiceImpl.processMsg((DeviceLogMsg) msg);
                break;
            case DeviceOta:
                //DeviceOtaMsg 是下发的,不用处理
                break;
            default:
                break;
        }

        deviceMessageServiceImpl.processMsg(msg);
    }

    @Override
    public QueryResult<DasConnectionLogDto> getDasConnectionLogsByNodeId(String nodeId, long beginTime, long endTime,
                                                                         int pageIndex, int pageSize) {
        return null;
    }

    @Override
    public DasStatusDto getDasStatus(String nodeId) {
        return dasStatusServiceImpl.getDasStatus(nodeId);
    }

    @Override
    public long countOfDeviceAlarm(long beginTime, long endTime) {
        return deviceAlarmServiceImpl.countOfDeviceAlarm(beginTime, endTime);
    }

    @Override
    public long countOfDeviceAlarmByDeviceType(String deviceType, long beginTime, long endTime) {
        return deviceAlarmServiceImpl.countOfDeviceAlarmByDeviceType(deviceType, beginTime, endTime);
    }

    @Override
    public long countOfDeviceAlarmByDeviceId(String deviceId, long beginTime, long endTime) {
        return deviceAlarmServiceImpl.countOfDeviceAlarmByDeviceId(deviceId, beginTime, endTime);
    }

    @Override
    public QueryResult<DeviceAlarmDto> getDeviceAlarmsByDeviceId(String deviceId, List<String> alarmCodes, long
            beginTime, long endTime, int pageIndex, int pageSize) {
        return deviceAlarmServiceImpl.getDeviceAlarmsByDeviceId(deviceId, alarmCodes, beginTime, endTime, pageIndex,
                pageSize);
    }

    @Override
    public QueryResult<DeviceConnectionLogDto> getDeviceConnectionLogsByDeviceId(String deviceId, long beginTime, long
            endTime, int pageIndex, int pageSize) {
        return deviceConnectionLogServiceImpl.getDeviceConnectionLogsByDeviceId(deviceId, beginTime, endTime,
                pageIndex, pageSize);
    }

    @Override
    public long countOfDeviceEvent(long beginTime, long endTime) {
        return deviceEventServiceImpl.countOfDeviceEvent(beginTime, endTime);
    }

    @Override
    public long countOfDeviceEventByDeviceType(String deviceType, long beginTime, long endTime) {
        return deviceEventServiceImpl.countOfDeviceEventByDeviceType(deviceType, beginTime, endTime);
    }

    @Override
    public long countOfDeviceEventByDeviceId(String deviceId, long beginTime, long endTime) {
        return deviceEventServiceImpl.countOfDeviceEventByDeviceId(deviceId, beginTime, endTime);
    }

    @Override
    public QueryResult<DeviceEventDto> getDeviceEventsByDeviceId(String deviceId, List<String> eventCodes, long
            beginTime, long endTime, int pageIndex, int pageSize) {
        return deviceEventServiceImpl.getDeviceEventsByDeviceId(deviceId, eventCodes, beginTime, endTime, pageIndex,
                pageSize);
    }

    @Override
    public long countOfDeviceInfo() {
        return deviceInfoServiceImpl.countOfDeviceInfo();
    }

    @Override
    public long countOfDeviceInfoByDeviceType(String deviceType) {
        return deviceInfoServiceImpl.countOfDeviceInfoByDeviceType(deviceType);
    }

    @Override
    public long countOfDeviceInfoByDeviceTypeAndVersionCode(String deviceType, int versionCode) {
        return deviceInfoServiceImpl.countOfDeviceInfoByDeviceTypeAndVersionCode(deviceType, versionCode);
    }

    @Override
    public DeviceInfoDto getDeviceInfoById(long id) {
        return deviceInfoServiceImpl.getDeviceInfoById(id);
    }

    @Override
    public DeviceInfoDto getDeviceInfoByDeviceId(String deviceId) {
        return deviceInfoServiceImpl.getDeviceInfoByDeviceId(deviceId);
    }

    @Override
    public DeviceInfoDto getDeviceInfoByMac(String mac) {
        return deviceInfoServiceImpl.getDeviceInfoByMac(mac);
    }

    @Override
    public QueryResult<DeviceInfoDto> getDeviceInfosByDeviceType(String deviceType, int pageIndex, int pageSize) {
        return deviceInfoServiceImpl.getDeviceInfosByDeviceType(deviceType, pageIndex, pageSize);
    }

    @Override
    public QueryResult<DeviceInfoDto> getDeviceInfosByDeviceTypeAndVersion(String deviceType, int versionCode, int
            pageIndex, int pageSize) {
        return deviceInfoServiceImpl.getDeviceInfosByDeviceTypeAndVersion(deviceType, versionCode, pageIndex, pageSize);
    }

    @Override
    public QueryResult<DeviceLogDto> getDeviceLogsByTime(String deviceId, String logType, long beginTime, long endTime,
                                                         int pageIndex, int pageSize) {
        return deviceLogServiceImpl.getDeviceLogsByTime(deviceId, logType, beginTime, endTime, pageIndex, pageSize);
    }

    @Override
    public QueryResult<DeviceLogDto> getDeviceLogsByTime(String deviceId, String deviceType, String logType, long
            beginTime, long endTime, int pageIndex, int pageSize) {
        return deviceLogServiceImpl.getDeviceLogsByTime(deviceId, deviceType, logType, beginTime, endTime, pageIndex,
                pageSize);
    }

    @Override
    public void sendMsg(IMsg msg) throws Exception {
        deviceMessageServiceImpl.sendMsg(msg);
    }

    @Override
    public QueryResult<DeviceOtaFileDto> getDeviceOtaFilesByDeviceType(String deviceType, int pageIndex, int pageSize) {
        return deviceOtaServiceImpl.getDeviceOtaFilesByDeviceType(deviceType, pageIndex, pageSize);
    }

    @Override
    public void uploadOtaFile(String otaFullName, String deviceType, int versionCode, String versionName, String
            description, String content) {
        deviceOtaServiceImpl.uploadOtaFile(otaFullName, deviceType, versionCode, versionName, description, content);
    }

    /**
     * 根据参数查询设备升级文档信息
     *
     * @param deviceType   设备类型
     * @param connected    设备状态
     * @param deviceCode   设备编码
     * @param beginVersion 开始版本号
     * @param endVersion   结束版本号
     * @param pageIndex
     * @param pageSize     @return 设备升级文档信息
     */
    @Override
    public QueryResult<?> findUpDocument(String deviceType, boolean connected, String deviceCode, int beginVersion,
                                         int endVersion, int pageIndex, int pageSize) {
        return deviceOtaServiceImpl.findUpDocument(deviceType, connected, deviceCode, beginVersion, endVersion,
                pageIndex, pageSize);
    }

    @Override
    public DeviceStatusDto getDeviceStatus(String deviceId) {
        return deviceStatusServiceImpl.getDeviceStatus(deviceId);
    }

    @Override
    public List<DeviceStatusDto> getDeviceStatusBatch(String[] deviceIds) {
        return deviceStatusServiceImpl.getDeviceStatusBatch(deviceIds);
    }

    @Override
    public long countOfDeviceToken() {
        return deviceTokenServiceImpl.countOfDeviceToken();
    }

    @Override
    public String generateDeviceId(String deviceType, String token) {
        return deviceTokenServiceImpl.generateDeviceId(deviceType, token);
    }

    @Override
    public DeviceTokenDto getDeviceTokenByDeviceId(String deviceId) {
        return deviceTokenServiceImpl.getDeviceTokenByDeviceId(deviceId);
    }

    @Override
    public DeviceTokenDto getDeviceTokenByToken(String token) {
        return deviceTokenServiceImpl.getDeviceTokenByToken(token);
    }

    @Override
    public QueryResult<DeviceTokenDto> getDeviceTokensByDeviceType(String deviceType, int pageIndex, int pageSize) {
        return deviceTokenServiceImpl.getDeviceTokensByDeviceType(deviceType, pageIndex, pageSize);
    }

    @Override
    public void bindDevice(String ownerId, String deviceId) {
        deviceOwnerService.bindDevice(ownerId, deviceId);
    }

    @Override
    public void bindDevice(String ownerId, List<String> deviceList) {
        deviceOwnerService.bindDevice(ownerId, deviceList);
    }

    @Override
    public void unBindDevice(String ownerId) {
        deviceOwnerService.unBindDevice(ownerId);
    }

    @Override
    public void unBindDevice(String ownerId, List<String> deviceList) {
        deviceOwnerService.unBindDevice(ownerId, deviceList);
    }

    @Override
    public QueryResult<DeviceInfoDto> getDeviceInfoByOwnerId(String ownerId, int pageIndex, int pageSize) {
        return deviceInfoServiceImpl.getDeviceInfoByOwnerId(ownerId, pageIndex, pageSize);
    }

    @Override
    public String getOwnerIdByDeviceId(String deviceId) {
        return deviceOwnerService.getOwnerIdByDeviceId(deviceId);
    }

    @Override
    public QueryResult<DeviceInfoDto> findDeviceByParams(String[] ownerIds, String deviceType, boolean state, int
            pageIndex, int pageSize) {
        return deviceInfoServiceImpl.findDeviceByParams(ownerIds, deviceType, state, pageIndex, pageSize);
    }

    @Override
    public String deviceUpdate(List list) {
        return deviceOwnerService.deviceUpdate(list);
    }

    @Override
    public QueryResult<MsgLogDto> getMsgLogs(String deviceType, String deviceId, String msgType, long beginTime, long
            endTime, int pageIndex, int pageSize) {
        return msgLogServiceImpl.getMsgLogs(deviceType, deviceId, msgType, beginTime, endTime, pageIndex, pageSize);
    }

    @Override
    public long bindLocation(String userId, String deviceType, String deviceId, int coorType, double lon, double lat,
                             String locationDesc) {
        return deviceLocationService.bindLocation(userId, deviceType, deviceId, coorType, lon, lat, locationDesc);
    }

    @Override
    public DeviceLocationDto getLocation(String deviceId, int coorType) {
        return deviceLocationService.getLocation(deviceId, coorType);
    }
}

package allthings.iot.dms.ui.service;

import org.springframework.stereotype.Component;
import allthings.iot.common.dto.QueryResult;
import allthings.iot.common.dto.Result;
import allthings.iot.dms.dto.*;

import java.util.List;
import java.util.Map;

/**
 * @author :  sylar
 * @FileName :  MqttConst
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
@Component
public class DmsFallCallback implements IDmsFeignClient {
    @Override
    public Result<QueryResult<DasConnectionLogDto>> getDasConnectionLogsByNodeId(String nodeId, long beginTime, long endTime, int pageIndex, int
            pageSize) {
        return null;
    }

    @Override
    public Result<DasStatusDto> getDasStatus(String nodeId) {
        return null;
    }

    @Override
    public Result<Long> countOfDeviceAlarm(long beginTime, long endTime) {
        return null;
    }

    @Override
    public Result<Long> countOfDeviceAlarmByDeviceType(String deviceType, long beginTime, long endTime) {
        return null;
    }

    @Override
    public Result<Long> countOfDeviceAlarmByDeviceId(String deviceId, long beginTime, long endTime) {
        return null;
    }

    @Override
    public Result<QueryResult<DeviceAlarmDto>> getDeviceAlarmsByDeviceId(String deviceId, String[] alarmCodes, long beginTime, long
            endTime, int pageIndex, int pageSize) {
        return null;
    }

    @Override
    public Result<QueryResult<DeviceConnectionLogDto>> getDeviceConnectionLogsByDeviceId(String deviceId, long beginTime, long endTime, int pageIndex,
                                                                                         int pageSize) {
        return null;
    }

    @Override
    public Result<Long> countOfDeviceEvent(long beginTime, long endTime) {
        return null;
    }

    @Override
    public Result<Long> countOfDeviceEventByDeviceType(String deviceType, long beginTime, long endTime) {
        return null;
    }

    @Override
    public Result<Long> countOfDeviceEventByDeviceId(String deviceId, long beginTime, long endTime) {
        return null;
    }

    @Override
    public Result<QueryResult<DeviceEventDto>> getDeviceEventsByDeviceId(String deviceId, String[] eventCodes, long beginTime, long
            endTime, int pageIndex, int pageSize) {
        return null;
    }

    @Override
    public Result<Long> countOfDeviceInfo() {
        return null;
    }

    @Override
    public Result<Long> countOfDeviceInfoByDeviceType(String deviceType) {
        return null;
    }

    @Override
    public Result<Long> countOfDeviceInfoByDeviceTypeAndVersionCode(String deviceType, int versionCode) {
        return null;
    }

    @Override
    public Result<DeviceInfoDto> getDeviceInfoById(long id) {
        return Result.newFaild();
    }

    @Override
    public Result<DeviceInfoDto> getDeviceInfoByDeviceId(String deviceId) {
        return null;
    }

    @Override
    public Result<DeviceInfoDto> getDeviceInfoByMac(String mac) {
        return null;
    }

    @Override
    public Result<QueryResult<DeviceInfoDto>> getDeviceInfosByDeviceType(String deviceType, int pageIndex, int pageSize) {
        return null;
    }

    @Override
    public Result<QueryResult<DeviceInfoDto>> getDeviceInfosByDeviceTypeAndVersion(String deviceType, int versionCode, int pageIndex, int
            pageSize) {
        return null;
    }

    @Override
    public Result<DeviceLocationDto> getLocation(String deviceId, int coorType) {
        return null;
    }

    @Override
    public Result<?> bindLocation(BindLocationParamsDto params) {
        return null;
    }

    @Override
    public Result<QueryResult<DeviceLogDto>> getDeviceLogsByTime(String deviceId, String deviceType, String logType, long beginTime, long
            endTime, int pageIndex, int pageSize) {
        return null;
    }

    @Override
    public Result<QueryResult<DeviceOtaFileDto>> getDeviceOtaFilesByDeviceType(String deviceType, int pageIndex, int pageSize) {
        return null;
    }

    @Override
    public Result<?> uploadOtaFile(Map<String, Object> params) {
        return null;
    }

    @Override
    public Result<QueryResult<DeviceOtaFileDto>> findUpDocument(String deviceType, boolean connected, String deviceCode, int
            beginVersion, int endVersion, int pageIdnex, int pageSize) {
        return null;
    }

    @Override
    public Result<?> bindDevice(String ownerId, String deviceId) {
        return null;
    }

    @Override
    public Result<?> bindDeviceList(Map<String, Object> paramMap) {
        return null;
    }

    @Override
    public Result<?> unbindDevice(String ownerId, String[] deviceArray) {
        return null;
    }

    @Override
    public Result<QueryResult<DeviceInfoDto>> getDeviceInfoByOwnerId(String ownerId, int pageIndex, int pageSize) {
        return null;
    }

    @Override
    public Result<QueryResult<DeviceInfoDto>> findDeviceByParams(String[] ownerIds, String deviceType, boolean connected, int pageIdnex, int
            pageSize) {
        return null;
    }

    @Override
    public Result<String> deviceUpdate(String[] list) {
        return null;
    }

    @Override
    public Result<DeviceStatusDto> getDeviceStatus(String deviceId) {
        return null;
    }

    @Override
    public Result<List<DeviceStatusDto>> getDeviceStatusBatch(String[] deviceIds) {
        return null;
    }

    @Override
    public Result<Long> countOfDeviceToken() {
        return null;
    }

    @Override
    public Result<String> generateDeviceId(String deviceType, String token) {
        return null;
    }

    @Override
    public Result<DeviceTokenDto> getDeviceTokenByDeviceId(String deviceId) {
        return null;
    }

    @Override
    public Result<DeviceTokenDto> getDeviceTokenByToken(String token) {
        return null;
    }

    @Override
    public Result<QueryResult<DeviceTokenDto>> getDeviceTokensByDeviceType(String deviceType, int pageIndex, int pageSize) {
        return null;
    }

    @Override
    public Result<QueryResult<MsgLogDto>> getMsgLogs(String deviceType, String deviceId, String msgType, long beginTime, long endTime, int pageIndex, int pageSize) {
        return Result.newFaild(-1, "触发熔断");
    }
}

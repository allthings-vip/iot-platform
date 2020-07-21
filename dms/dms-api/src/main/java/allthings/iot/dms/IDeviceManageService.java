package allthings.iot.dms;

/**
 * @author :  sylar
 * @FileName :  IDeviceManageService
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
public interface IDeviceManageService extends
        IMsgLogService,
        IDasStatusService,
        IDasConnectionLogService,
        IDeviceStatusService,
        IDeviceConnectionLogService,
        IDeviceTokenService,
        IDeviceMessageService,
        IDeviceInfoService,
        IDeviceAlarmService,
        IDeviceEventService,
        IDeviceLogService,
        IDeviceOtaService,
        IDeviceOwnerService,
        IDeviceLocationService {
}

package allthings.iot.dms;

import allthings.iot.util.dubbo.DubboUtils;

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
 * @CopyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
public class Dms {
    final static String APPNAME = "dms-sdk";

    public static IDeviceManageService getService(String zkConnectString) {
        return DubboUtils.getServiceReference(APPNAME, zkConnectString, IDeviceManageService.class);
    }
}

package allthings.iot.das.common.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author :  sylar
 * @FileName :  OtaEvent
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
public class OtaEvent extends ApplicationEvent {
    public OtaEvent(Object source) {
        super(source);
    }
}

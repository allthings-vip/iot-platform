package allthings.iot.util.disruptor;

import com.lmax.disruptor.EventFactory;

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
public class ValueEventFactory implements EventFactory<ValueEvent> {
    @Override
    public ValueEvent newInstance() {
        return new ValueEvent();
    }
}

package allthings.iot.common;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Strings;
import com.lmax.disruptor.EventHandler;
import allthings.iot.common.msg.IMsg;
import allthings.iot.common.pojo.CacheMsgWrap;
import allthings.iot.util.disruptor.IMessaging;
import allthings.iot.util.disruptor.LmaxDiscuptorMessaging;
import allthings.iot.util.disruptor.ValueEvent;

import javax.annotation.PreDestroy;

/**
 * @author :  sylar
 * @FileName :  AbstractDeviceMessagePipe
 * @CreateDate :  2017/11/08
 * @Description : 抽象的设备消息管道，管道上游是消息来源或生产者， 下游是消息消费方
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) allthings-vip All Rights Reserved
 * *******************************************************************************************
 */
public abstract class AbstractDeviceMessagePipe {

    /**
     * 以回调的方式完成消息的导入，例如从 rocketmq or kafka
     *
     * @param callback 消息回调方法
     */
    public abstract void input(Callback<IMsg> callback);

    /**
     * 输出消息，管道下游自行处理
     *
     * @param msg 设备消息
     */
    public abstract void output(IMsg msg) throws Exception;

    private final IMessaging messagingService = new LmaxDiscuptorMessaging(new EventProcessor());

    protected void init() {
        onInit();
    }

    @PreDestroy
    private void destroy() {
        onDestroy();
    }

    protected void onInit() {
        input(new Callback<IMsg>() {
            @Override
            public void onSuccess(IMsg msg) {
                if (msg != null) {
                    toDisruptor(msg);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
    }

    protected void onDestroy() {
        messagingService.stop();
    }

    protected void toDisruptor(IMsg msg) {
        messagingService.publish(msg);
    }

    protected IMsg convert(String json) {
        if (Strings.isNullOrEmpty(json)) {
            return null;
        }

        CacheMsgWrap wrap = JSON.parseObject(json, CacheMsgWrap.class);
        if (wrap != null) {
            return wrap.getMsg();
        }

        return null;
    }


    class EventProcessor implements EventHandler<ValueEvent> {

        @Override
        public void onEvent(ValueEvent event, long sequence, boolean endOfBatch) throws Exception {

            try {
                Object value = event.getValue();
                if (value == null) {
                    return;
                }

                IMsg msg = null;
                if (value instanceof IMsg) {
                    msg = (IMsg) value;
                } else if (value instanceof String) {
                    msg = convert(value.toString());
                }

                if (msg == null) {
                    return;
                }

                output(msg);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}

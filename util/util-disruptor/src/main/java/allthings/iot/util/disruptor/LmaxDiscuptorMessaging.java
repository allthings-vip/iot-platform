package allthings.iot.util.disruptor;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;

import java.util.concurrent.Executors;

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
public class LmaxDiscuptorMessaging implements IMessaging {

    final static private int RING_BUFFER_SIZE = 1024 * 16;
    private Disruptor<ValueEvent> disruptor;
    private RingBuffer<ValueEvent> ringBuffer;

    @SuppressWarnings("unchecked")
    public LmaxDiscuptorMessaging(EventHandler<ValueEvent>... msgEventHandlers) {
        disruptor = new Disruptor<>(new ValueEventFactory(), RING_BUFFER_SIZE, Executors.defaultThreadFactory());
        disruptor.setDefaultExceptionHandler(new ValueEventExceptionHandler());
        disruptor.handleEventsWith(msgEventHandlers);
        disruptor.start();
        ringBuffer = disruptor.getRingBuffer();
    }

    @Override
    public void stop() {
        disruptor.shutdown();
    }

    @Override
    public void publish(Object value) {
        long sequence = ringBuffer.next();
        try {
            ValueEvent event = ringBuffer.get(sequence);
            event.setValue(value);
        } finally {
            ringBuffer.publish(sequence);
        }
    }

    @Override
    public long getRemainBufferSize() {
        return ringBuffer.remainingCapacity();
    }

}

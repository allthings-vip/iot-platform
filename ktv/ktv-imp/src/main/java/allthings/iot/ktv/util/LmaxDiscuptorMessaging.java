package allthings.iot.ktv.util;

/**
 * @author tyf
 * @date 2018/12/18 9:46
 */
//public class LmaxDiscuptorMessaging implements IMessaging {
//    final static private int RING_BUFFER_SIZE = 1024 * 1024;
//    private Disruptor<ValueEvent> disruptor;
//    private RingBuffer<ValueEvent> ringBuffer;
//    private static ExecutorService executorService = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(),
//            Runtime.getRuntime().availableProcessors() * 2, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(512),
//            new ThreadFactoryBuilder().setNameFormat("lmax-thread-%d").build());

//    @SuppressWarnings("unchecked")
//    public LmaxDiscuptorMessaging(EventHandler<ValueEvent>... msgEventHandlers) {
//        disruptor = new Disruptor<>(new ValueEventFactory(), RING_BUFFER_SIZE, executorService);
//        disruptor.setDefaultExceptionHandler(new ValueEventExceptionHandler());
//        disruptor.handleEventsWith(msgEventHandlers);
//        disruptor.start();
//        ringBuffer = disruptor.getRingBuffer();
//    }
//
//    @Override
//    public void stop() {
//        disruptor.shutdown();
//    }
//
//    @Override
//    public void publish(Object value) {
//        long sequence = ringBuffer.next();
//        try {
//            ValueEvent event = ringBuffer.get(sequence);
//            event.setValue(value);
//        } finally {
//            ringBuffer.publish(sequence);
//        }
//    }
//
//    @Override
//    public long getRemainBufferSize() {
//        return ringBuffer.remainingCapacity();
//    }
//}

package allthings.iot.util.kafka;

/**
 * Created by sylar on 2017/3/9.
 */
public abstract class AbsService implements IService {

    protected boolean isRunning;

    protected abstract void onStart();

    protected abstract void onStop();

    @Override
    synchronized public void start() throws Exception {
        if (!isRunning) {
            onStart();
            isRunning = true;
        }
    }

    @Override
    synchronized public void stop() throws Exception {

        if (isRunning) {
            onStop();
            isRunning = false;
        }
    }
}

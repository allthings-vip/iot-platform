package allthings.iot.bb808.common;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author :  luhao
 * @FileName :  RunningNumberFactory
 * @CreateDate :  2017/12/21
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
public class RunningNumberFactory {
    private static AtomicInteger runningNumberAtomic = new AtomicInteger(0);
    private static RunningNumberFactory instance;

    public synchronized static RunningNumberFactory getInstance() {
        if (instance == null) {
            instance = new RunningNumberFactory();
        }
        return instance;
    }

    public synchronized int getRunningNumber() {
        int runningNumber = runningNumberAtomic.incrementAndGet();
        if (runningNumber >= Integer.MAX_VALUE) {
            runningNumber = 1;
            runningNumberAtomic.set(runningNumber);
        }
        return runningNumber;
    }
}

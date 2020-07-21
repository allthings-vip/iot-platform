package allthings.iot.util.kafka.model;

/**
 * Created by sylar on 2017/3/9.
 */
public class Num {

    int index;
    long time = System.currentTimeMillis();

    public Num() {
    }

    public Num(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}

package allthings.iot.util.kafka.model;

/**
 * Created by sylar on 2017/3/9.
 */
public class Num2 {

    String clientId;
    int index;
    long time = System.currentTimeMillis();

    public Num2() {
    }

    public Num2(String clientId, int index) {
        this.clientId = clientId;
        this.index = index;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
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

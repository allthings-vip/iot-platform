package allthings.iot.util.jedis;

/**
 * @author tyf
 * @date 2020/07/27 14:19:53
 */
public enum RedisTypeEnum {

    STANDALONE("standalone"),
    SENTINEL("sentinel"),
    CLUSTER("cluster");

    private String enName;

    public String getEnName() {
        return this.enName;
    }

    private RedisTypeEnum(String enName) {
        this.enName = enName;
    }

}

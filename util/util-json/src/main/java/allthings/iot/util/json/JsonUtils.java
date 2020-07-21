package allthings.iot.util.json;

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
public class JsonUtils {

    private JsonUtils() {
    }

    /**
     * 用于校验一个字符串是否是合法的JSON格式
     *
     * @param input
     * @return
     */
    static public boolean isValid(String input) {
        return new JsonValidator().validate(input);
    }
}

package allthings.iot.das.mqtt.bean;

import com.google.common.collect.Maps;
import allthings.iot.das.common.DasConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Objects;

/**
 * @author :  sylar
 * @FileName :  MqttAuthenticator
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
@Component
public class MqttAuthenticator {
    private final static String PARAMKEY_MQTTUSER = "mqttUser";
    private final static String PARAMKEY_MQTTPASSWORD = "mqttPassword";

    @Autowired
    private DasConfig dasConfig;
    private Map<String, String> mapUsers = Maps.newHashMap();

    @PostConstruct
    public void init() {
        String user = dasConfig.getDasProperties().getParam(PARAMKEY_MQTTUSER);
        String pwd = dasConfig.getDasProperties().getParam(PARAMKEY_MQTTPASSWORD);
        if (user != null && pwd != null) {
            mapUsers.put(user, pwd);
        }
    }


    public boolean checkValid(String username, String password) {
        if (mapUsers.isEmpty()) {
            return true;
        } else if (username == null || password == null) {
            return false;
        } else {
            return Objects.equals(password, mapUsers.get(username));
        }
    }

}

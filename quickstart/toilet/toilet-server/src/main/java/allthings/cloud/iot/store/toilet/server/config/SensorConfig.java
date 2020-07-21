package tf56.cloud.iot.store.toilet.server.config;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Charsets;
import tf56.cloud.iot.store.toilet.common.dic.SensorInfoManager;
import tf56.cloud.iot.store.toilet.common.dic.SensorInfos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author :  sylar
 * @FileName :  SensorConfig
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
@Component
public class SensorConfig {

    @Autowired
    ApplicationContext ctx;

    @PostConstruct
    private void init() {
        Resource resource = new ClassPathResource("toilet_sensor_config.json");
        if (!resource.exists()) {
            return;
        }

        InputStream is = null;
        try {
            byte[] buffer = new byte[(int) resource.contentLength()];
            is = resource.getInputStream();
            is.read(buffer, 0, buffer.length);
            String json = new String(buffer, Charsets.UTF_8);
            SensorInfos sensorInfos = JSON.parseObject(json, SensorInfos.class);
            if (sensorInfos != null) {
                SensorInfoManager.getInstance().loadSensonInfos(sensorInfos);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
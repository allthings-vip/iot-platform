package allthings.iot.util.jedis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * @author :  luhao
 * @FileName :  PropUtil
 * @CreateDate :  2018-5-22
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
public class PropUtil {

    private static final Logger log = LoggerFactory.getLogger(PropUtil.class);

    public static String getPropValue(String propFilepath, String propKey, String defaultValue) throws Exception {
        try {
            if (propFilepath == null || propFilepath.trim().length() == 0 || propKey == null || propKey.trim().length
                    () == 0) {
                String msg = "The prop filepath[" + propFilepath + "] or key[" + propKey + "] you give is blank !";
                log.warn(msg);
                throw new Exception(msg);
            }

            Properties properties = new Properties();
            properties.load(PropUtil.class.getClassLoader().getResourceAsStream(propFilepath));

            return properties.getProperty(propKey, defaultValue);
        } catch (Exception e) {
            String msg = "Trying ERROR! With params:[propFilepath:" + propFilepath + ",propKey:" + propKey;
            log.error(msg);
            throw new Exception(msg);
        }
    }
}

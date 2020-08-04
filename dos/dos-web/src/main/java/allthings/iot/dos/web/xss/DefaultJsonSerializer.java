package allthings.iot.dos.web.xss;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.web.util.HtmlUtils;

import java.io.IOException;

/**
 * @author :  luhao
 * @FileName :  DefaultJsonSerializer
 * @CreateDate :  2018-9-25
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
public class DefaultJsonSerializer extends StdSerializer<String> {
    public DefaultJsonSerializer() {
        this(null);
    }

    public DefaultJsonSerializer(Class<String> t) {
        super(t);
    }

    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        // xss策略再此执行
        String safe = HtmlUtils.htmlEscape(value, "utf-8");
        gen.writeString(safe);
    }
}

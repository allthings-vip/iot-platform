package allthings.iot.dos.open.security.oauth2.handler;

import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.security.oauth2.provider.error.DefaultOAuth2ExceptionRenderer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :  luhao
 * @FileName :  CustomOauthExceptionRenderer
 * @CreateDate :  2018-11-16
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
public class CustomOauthExceptionRenderer extends DefaultOAuth2ExceptionRenderer {

    public CustomOauthExceptionRenderer() {
        setMessageConverters(getMessageConverters());
    }

    private List<HttpMessageConverter<?>> getMessageConverters() {
        List<HttpMessageConverter<?>> result = new ArrayList<>();
        result.add(new CustomOauthMessageConverter());
        return result;
    }
}

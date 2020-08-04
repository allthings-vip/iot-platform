package allthings.iot.dos.open.security.oauth2.handler;

import allthings.iot.common.dto.ResultDTO;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

import java.io.IOException;

/**
 * @author :  luhao
 * @FileName :  CustomOauthMessageConverter
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
public class CustomOauthMessageConverter extends MappingJackson2HttpMessageConverter {

    @Override
    protected void writeInternal(Object object, HttpOutputMessage outputMessage) throws IOException,
            HttpMessageNotWritableException {
        super.writeInternal(transformObject(object), outputMessage);
    }

    protected Object transformObject(Object object) {
        //ApiResponse is just my own class, replace it with anything you wanna return
        ResultDTO<String> response = ResultDTO.newFail(444, "认证失败");
        if (object instanceof OAuth2Exception) {
        }

        return response;
    }
}

package allthings.iot.dos.open;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author :  luhao
 * @fileName :  CustomPasswordEncoder
 * @createDate :  2019-2-27
 * @description :
 * @reviewedBy :
 * @reviewedOn :
 * @versionHistory :
 * @modifiedBy :
 * @modifiedDate :
 * @comments :
 * @copyRight : COPYRIGHT(c) iot.tf56.com All Rights Reserved
 * *******************************************************************************************
 */
public class CustomPasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence charSequence) {
        return charSequence.toString();
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        return s.equals(charSequence.toString());
    }
}

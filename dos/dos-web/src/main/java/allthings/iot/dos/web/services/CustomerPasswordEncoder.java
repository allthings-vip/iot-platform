package allthings.iot.dos.web.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.DigestUtils;

/**
 * @description:
 * @author: fengchangxin
 * @create: 2018-12-20 18:59
 */

public class CustomerPasswordEncoder implements PasswordEncoder {
//    @Override
//    public boolean isPasswordValid(String encPass, String rawPass, Object salt) {
//        String pass1 = encPass + "";
//        String pass2 = mergePasswordSalt(rawPass, salt);
//        return pass1.equals(pass2);
//    }

    private String mergePasswordSalt(String password, Object salt) {
        if (password == null) {
            password = "";
        }

        if (salt != null) {
            if ((salt.toString().lastIndexOf("{") != -1)
                    || (salt.toString().lastIndexOf("}") != -1)) {
                throw new IllegalArgumentException("Cannot use { or } in salt.toString()");
            }
        }

        if ((salt == null) || "".equals(salt)) {
            return DigestUtils.md5DigestAsHex(password.getBytes());
        } else {
            return DigestUtils.md5DigestAsHex(password.concat(salt.toString()).getBytes());
        }
    }

    @Override
    public String encode(CharSequence rawPassword) {
        return null;
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        String salt = encodedPassword.split("@")[1];
        return mergePasswordSalt(rawPassword.toString(), salt).equals(encodedPassword.split("@")[0]);
    }
}

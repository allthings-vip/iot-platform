package allthings.iot.util.misc;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

/**
 * @author :  luhao
 * @FileName :  Aes128Util
 * @CreateDate :  2018/2/2
 * @Description : Aes128加解密
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) allthings-vip All Rights Reserved
 * *******************************************************************************************
 */
public class Aes128Util {

    /**
     * AES加密字符串
     *
     * @param byteContent 需要被加密的字节数组
     * @param password    加密需要的密码
     * @return 密文
     */
    public static byte[] encrypt(byte[] byteContent, String password) throws Exception {
        //创建AES的Key生产者
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        //利用用户密码作为随机数初始化出
        //128位的key生产者
        //加密没关系，SecureRandom是生成安全随机数序列，password.getBytes()是种子，只要种子相同，序列就一样，所以解密只要有password就行
        kgen.init(128, new SecureRandom(password.getBytes()));
        //根据用户密码，生成一个密钥
        SecretKey secretKey = kgen.generateKey();

        //返回基本编码格式的密钥，如果此密钥不支持编码，则返回null
        byte[] enCodeFormat = secretKey.getEncoded();

        //转换为AES专用密钥
        SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
        //创建密码器
        Cipher cipher = Cipher.getInstance("AES");

        //初始化为加密模式的密码器
        cipher.init(Cipher.ENCRYPT_MODE, key);
        //加密
        return cipher.doFinal(byteContent);
    }

    /**
     * 解密AES加密过的字符串
     *
     * @param content  AES加密过过的内容
     * @param password 加密时的密码
     * @return 明文
     */
    public static byte[] decrypt(byte[] content, String password) throws Exception {
        //创建AES的Key生产者
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128, new SecureRandom(password.getBytes()));
        //根据用户密码，生成一个密钥
        SecretKey secretKey = kgen.generateKey();
        //返回基本编码格式的密钥
        byte[] enCodeFormat = secretKey.getEncoded();
        //转换为AES专用密钥
        SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
        //创建密码器
        Cipher cipher = Cipher.getInstance("AES");
        //初始化为解密模式的密码器
        cipher.init(Cipher.DECRYPT_MODE, key);

        return cipher.doFinal(content);

    }
}

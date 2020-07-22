package allthings.iot.util.misc;


import javax.crypto.Cipher;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

/**
 * @author :  luhao
 * @FileName :  RSAUtil
 * @CreateDate :  2017/12/21
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
public class RSAUtil {

    private static final int KEY_LEN = 128;

    /**
     * 使用模和指数生成RSA公钥
     * 用了默认补位方式，为RSA/None/PKCS1Padding，不同JDK默认的补位方式可能不同，如Android默认是RSA/None/NoPadding
     *
     * @param modulus  模/N
     * @param exponent 公钥指数/E
     * @return
     */
    public static RSAPublicKey getPublicKey(String modulus, String exponent) {
        try {
            BigInteger n = new BigInteger(modulus);
            BigInteger e = new BigInteger(exponent);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            RSAPublicKeySpec keySpec = new RSAPublicKeySpec(n, e);
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 使用模和指数生成RSA私钥
     * 用了默认补位方式，为RSA/None/PKCS1Padding，不同JDK默认的补位方式可能不同，如Android默认是RSA/None/NoPadding
     *
     * @param modulus  模/N
     * @param exponent 私钥指数/E
     * @return
     */
    public static RSAPrivateKey getPrivateKey(String modulus, String exponent) {
        try {
            BigInteger n = new BigInteger(modulus);
            BigInteger d = new BigInteger(exponent);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(n, d);
            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 公钥加密
     *
     * @param data
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(byte[] data, RSAPublicKey publicKey)
            throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        int dataLength = data.length;
        int remainLength = dataLength;
        //如果明文长度大于模长-11则要分组加密
        ByteBuffer buf = ByteBuffer.allocate(dataLength);
        int index = 0;
        byte[] tmpBytes = null;
        while (remainLength > 0) {
            if (remainLength > KEY_LEN - 11) {
                tmpBytes = new byte[KEY_LEN - 11];
                System.arraycopy(data, index, tmpBytes, 0, KEY_LEN - 11);
                buf.put(cipher.doFinal(tmpBytes));
                index += (KEY_LEN - 11);
                remainLength -= (KEY_LEN - 11);
            } else {
                tmpBytes = new byte[remainLength];
                System.arraycopy(data, index, tmpBytes, 0, remainLength);
                buf.put(cipher.doFinal(tmpBytes));
                remainLength = 0;
            }
        }

        return buf.array();
    }

    /**
     * 私钥解密
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(byte[] data, RSAPrivateKey rsaPrivateKey)
            throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, rsaPrivateKey);
        //如果密文长度大于模长则要分组解密
        int dataLength = data.length;
        int remainLength = dataLength;
        int index = 0;
        ByteBuffer buf = ByteBuffer.allocate(dataLength);
        byte[] tmpBytes = null;
        while (remainLength > 0) {
            if (remainLength > KEY_LEN) {
                tmpBytes = new byte[KEY_LEN];
                System.arraycopy(data, index, tmpBytes, 0, KEY_LEN);
                buf.put(cipher.doFinal(tmpBytes));
                index += KEY_LEN;
                remainLength -= KEY_LEN;
            } else {
                tmpBytes = new byte[remainLength];
                System.arraycopy(data, index, tmpBytes, 0, remainLength);
                buf.put(cipher.doFinal(tmpBytes));
                remainLength = 0;
            }
        }

        return buf.array();
    }
}

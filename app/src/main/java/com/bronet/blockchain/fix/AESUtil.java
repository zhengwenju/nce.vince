package com.bronet.blockchain.fix;


import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

//用于支付密码和助记词,密钥登录加密
public class AESUtil {
    private static String key = "daodaoxiong0601Xdaodaoxiong0601X";

    // 加密
    public static String encry(String content) {
        try {
            String IV = key;
            if (key.length() > 16) {
                // IV为商户MD5密钥后16位
                IV = key.substring(key.length() - 16);
                // RES的KEY 为商户MD5密钥的前16位
                key = key.substring(0, 16);
            }
            return encryptData(content, key, IV);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;

    }

    // 加密
    public static String desEncry(String content) {
        try {
            String IV = key;
            if (key.length() > 16) {
                // IV为商户MD5密钥后16位
                IV = key.substring(key.length() - 16);
                // RES的KEY 为商户MD5密钥的前16位
                key = key.substring(0, 16);
            }
            return decryptData(content, key, IV);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * aes 加密
     *
     * @param data
     * @return
     */
    public static String encryptData(String data, String key, String IV) throws Exception {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            byte[] dataBytes = data.getBytes("UTF-8");
            int plaintextLength = dataBytes.length;
            // if (plaintextLength % blockSize != 0) {
            // plaintextLength = plaintextLength + (blockSize - (plaintextLength
            // % blockSize));
            // }
            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(IV.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
            byte[] encrypted = cipher.doFinal(plaintext);
            return new String(Base64.encodeToString(encrypted, 0));
        } catch (Exception e) {
            throw e;
        }

    }

    /**
     * aes 解密
     *
     * @param data 密文
     * @return
     */
    public static String decryptData(String data, String key, String IV) throws Exception {
        try {
            byte[] encrypted1 = Base64.decode(data.getBytes("UTF-8"), 0);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(IV.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original, "UTF-8");
            return originalString;
        } catch (Exception e) {
            throw e;
        }
    }

}

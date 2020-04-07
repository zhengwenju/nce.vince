package com.bronet.blockchain.fix;



import android.util.Base64;

import com.bronet.blockchain.fix.ConstantUtil;
//import com.zzhoujay.richtext.ext.Base64;

import org.json.JSONException;
import org.json.JSONObject;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

//用于扫码结果加密和用户ID加密

public class AES {
    public static String APPOINT="qrcode";
    private static String key="daodaoxiong0601X";
    //    private static final String CipherMode = "AES/ECB/PKCS5Padding";使用ECB加密，不需要设置IV，但是不安全
    private static final String CipherMode = "AES/CFB/NoPadding";//使用CFB加密，需要设置IV
    public static final String
            QR_TIP=" ";

    /**
     * 对字符串加密
     *
     * @param data 源字符串
     * @return 加密后的字符串
     */
    public static String encrypt(String data)  {
        try {
            Cipher cipher = Cipher.getInstance(CipherMode);
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            cipher.init(Cipher.ENCRYPT_MODE, keyspec, new IvParameterSpec(
                    new byte[cipher.getBlockSize()]));
            byte[] encrypted = cipher.doFinal(data.getBytes());
            return Base64.encodeToString(encrypted, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 对字符串解密
     *
     * @param data 已被加密的字符串
     * @return 解密得到的字符串
     */
    public static String decrypt( String data)  {
        try {
            byte[] encrypted1 = Base64.decode(data.getBytes(), Base64.DEFAULT);
            Cipher cipher = Cipher.getInstance(CipherMode);
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            cipher.init(Cipher.DECRYPT_MODE, keyspec, new IvParameterSpec(
                    new byte[cipher.getBlockSize()]));
            byte[] original = cipher.doFinal(encrypted1);
            return new String(original, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String TAG="tag";
    public static String UID="uid";
    public static String PID="pid";
    public static String TIME="time";
    public static String USDT="usdt";
    public static String NCE="nce";
    public static String USERNAME="username";
    public static String qrCodeEncrypt(String userid,double usdt,double nce,int pid){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(TAG, APPOINT);
            jsonObject.put(UID, encrypt(userid));
            jsonObject.put(USDT, usdt);
            jsonObject.put(NCE, nce);
            jsonObject.put(PID, encrypt(String.valueOf(pid)));
            jsonObject.put(USERNAME, encrypt(ConstantUtil.USERNAME));
            jsonObject.put(TIME,System.currentTimeMillis()/1000);
        }catch (JSONException ex){
            ex.printStackTrace();
        }
        return jsonObject.toString();
    }

    public static String qrCodeEncrypt2(String userid,double usdt,double nce,int pid){
//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put(TAG, APPOINT);
//            jsonObject.put(UID, encrypt(userid));
//            jsonObject.put(USDT, usdt);
//            jsonObject.put(NCE, nce);
//            jsonObject.put(PID, encrypt(String.valueOf(pid)));
//            jsonObject.put(USERNAME, encrypt(ConstantUtil.USERNAME));
//            jsonObject.put(TIME,System.currentTimeMillis()/1000);
//        }catch (JSONException ex){
//            ex.printStackTrace();
//        }
//        return jsonObject.toString();

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(APPOINT)
                .append(QR_TIP).append(encrypt(userid))
                .append(QR_TIP).append(usdt)
                .append(QR_TIP).append(nce)
                .append(QR_TIP).append(encrypt(String.valueOf(pid)))
                .append(QR_TIP).append(encrypt(ConstantUtil.USERNAME))
                .append(QR_TIP).append(System.currentTimeMillis()/1000)
        ;
        return stringBuffer.toString();
    }
}

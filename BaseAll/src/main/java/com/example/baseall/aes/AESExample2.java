package com.example.baseall.aes;

import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * <h1>AES 加密和解密示例代码</h1>
 * Created by woniu
 */
public class AESExample2 {

    /**
     * 加密模式之 ECB，算法/模式/补码方式
     */
    private static final String AES_ECB = "AES/ECB/PKCS5Padding";

    /**
     * 加密模式之 CBC，算法/模式/补码方式
     */
    private static final String AES_CBC = "AES/CBC/PKCS5Padding";

    /**
     * 加密模式之 CFB，算法/模式/补码方式
     */
    private static final String AES_CFB = "AES/CFB/PKCS5Padding";

    /**
     * AES 中的 IV 必须是 16 字节（128位）长
     */
    private static final Integer IV_LENGTH = 16;

    /***
     * <h2>空校验</h2>
     * @param str 需要判断的值
     */
    public static boolean isEmpty(Object str) {
        return null == str || "".equals(str);
    }

    /***
     * <h2>String 转 byte</h2>
     * @param str 需要转换的字符串
     */
    public static byte[] getBytes(String str) {
        if (isEmpty(str)) {
            return null;
        }

        try {
            return str.getBytes(StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /***
     * <h2>初始化向量（IV），它是一个随机生成的字节数组，用于增加加密和解密的安全性</h2>
     */
    public static String getIV() {
//        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
//        Random random = new Random();
//        StringBuffer sb = new StringBuffer();
//        for(int i = 0 ; i < IV_LENGTH ; i++){
//            int number = random.nextInt(str.length());
//            sb.append(str.charAt(number));
//        }
//        return sb.toString();
        return "1234567876543210";
    }

    /***
     * <h2>获取一个 AES 密钥规范</h2>
     */
    public static SecretKeySpec getSecretKeySpec(String key) {
        SecretKeySpec secretKeySpec = new SecretKeySpec(getBytes(key), "AES");
        return secretKeySpec;
    }

    /**
     * <h2>加密 - 模式 ECB</h2>
     *
     * @param text 需要加密的文本内容
     * @param key  加密的密钥 key
     */
    public static String encrypt(String text, String key) {
        if (isEmpty(text) || isEmpty(key)) {
            return null;
        }

        try {
            // 创建AES加密器
            Cipher cipher = Cipher.getInstance(AES_ECB);

            SecretKeySpec secretKeySpec = getSecretKeySpec(key);

            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

            // 加密字节数组
            byte[] encryptedBytes = cipher.doFinal(getBytes(text));

            // 将密文转换为 Base64 编码字符串
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * <h2>解密 - 模式 ECB</h2>
     *
     * @param text 需要解密的文本内容
     * @param key  解密的密钥 key
     */
    public static String decrypt(String text, String key) {
        if (isEmpty(text) || isEmpty(key)) {
            return null;
        }

        // 将密文转换为16字节的字节数组
        byte[] textBytes = Base64.getDecoder().decode(text);

        try {
            // 创建AES加密器
            Cipher cipher = Cipher.getInstance(AES_ECB);

            SecretKeySpec secretKeySpec = getSecretKeySpec(key);

            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);

            // 解密字节数组
            byte[] decryptedBytes = cipher.doFinal(textBytes);

            // 将明文转换为字符串
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * <h2>加密 - 自定义加密模式</h2>
     *
     * @param text 需要加密的文本内容
     * @param key  加密的密钥 key
     * @param iv   初始化向量
     * @param mode 加密模式
     */
    public static String encrypt(String text, String key, String iv, String mode) {
        if (isEmpty(text) || isEmpty(key) || isEmpty(iv)) {
            return null;
        }

        try {
            // 创建AES加密器
            Cipher cipher = Cipher.getInstance(mode);

            SecretKeySpec secretKeySpec = getSecretKeySpec(key);

            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, new IvParameterSpec(getBytes(iv)));

            // 加密字节数组
            byte[] encryptedBytes = cipher.doFinal(getBytes(text));

            // 将密文转换为 Base64 编码字符串
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * <h2>解密 - 自定义加密模式</h2>
     *
     * @param text 需要解密的文本内容
     * @param key  解密的密钥 key
     * @param iv   初始化向量
     * @param mode 加密模式
     */
    public static String decrypt(String text, String key, String iv, String mode) {
        if (isEmpty(text) || isEmpty(key) || isEmpty(iv)) {
            return null;
        }

        // 将密文转换为16字节的字节数组
        byte[] textBytes = Base64.getDecoder().decode(text);

        try {
            // 创建AES加密器
            Cipher cipher = Cipher.getInstance(mode);

            SecretKeySpec secretKeySpec = getSecretKeySpec(key);

            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, new IvParameterSpec(getBytes(iv)));

            // 解密字节数组
            byte[] decryptedBytes = cipher.doFinal(textBytes);

            // 将明文转换为字符串
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }




    public static String decryptAES(String encode) {
        String text = "";
        try {
            if (StringUtils.isEmpty(encode)) return "";
            text = encode.substring(4);

            String mode = "AES/CBC/PKCS5Padding";
            String iv = "1234567876543210";

            String key = "rHx3pZwRTAb33psX"; // 16字节的密钥
            // 将密文转换为16字节的字节数组

            byte[] textBytes = new byte[0];


            textBytes = Base64.getDecoder().decode(text);

            // 创建AES加密器
            Cipher cipher = Cipher.getInstance(mode);

            SecretKeySpec secretKeySpec = getSecretKeySpec(key);

            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, new IvParameterSpec(getBytes(iv)));

            // 解密字节数组
            byte[] decryptedBytes = cipher.doFinal(textBytes);

            // 将明文转换为字符串
            text = new String(decryptedBytes, StandardCharsets.UTF_8);
            return text;

        } catch (Exception e) {

            //解密异常 直接返回原内容
            return encode;
        }

    }

}


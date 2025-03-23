package com.bj.demo.util;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import javax.crypto.Cipher;

public class RsaUtils {
    private static final String ALGORITHM = "RSA";
    private static final int KEY_SIZE = 4096;

    /**
     * 生成RSA密钥对
     * @return 生成的密钥对
     * @throws NoSuchAlgorithmException 如果RSA算法不可用
     */
    public static KeyPair generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
        keyPairGenerator.initialize(KEY_SIZE);
        return keyPairGenerator.generateKeyPair();
    }

    /**
     * 使用公钥加密明文
     * @param plainText 明文
     * @param publicKey 公钥
     * @return 加密后的Base64编码字符串
     * @throws Exception 如果加密过程中出现异常
     */
    public static String encrypt(String plainText, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    /**
     * 使用私钥解密密文
     * @param encryptedText 加密后的Base64编码字符串
     * @param privateKey 私钥
     * @return 解密后的明文
     * @throws Exception 如果解密过程中出现异常
     */
    public static String decrypt(String encryptedText, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
        return new String(decryptedBytes);
    }

    /**
     * 从Base64编码的字符串中获取公钥
     * @param publicKeyStr Base64编码的公钥字符串
     * @return 公钥对象
     * @throws Exception 如果解析过程中出现异常
     */
    public static PublicKey getPublicKey(String publicKeyStr) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(publicKeyStr);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        return keyFactory.generatePublic(keySpec);
    }

    /**
     * 从Base64编码的字符串中获取私钥
     * @param privateKeyStr Base64编码的私钥字符串
     * @return 私钥对象
     * @throws Exception 如果解析过程中出现异常
     */
    public static PrivateKey getPrivateKey(String privateKeyStr) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(privateKeyStr);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        return keyFactory.generatePrivate(keySpec);
    }

    /**
     * 获取公钥和私钥的字符串表示
     * @param keyPair 密钥对
     * @return 包含公钥和私钥字符串的数组，第一个元素为公钥，第二个元素为私钥
     */
    public static String[] getKeyStrings(KeyPair keyPair) {
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();
        String publicKeyStr = Base64.getEncoder().encodeToString(publicKey.getEncoded());
        String privateKeyStr = Base64.getEncoder().encodeToString(privateKey.getEncoded());
        return new String[]{publicKeyStr, privateKeyStr};
    }

    //////////////以下是使用公钥字符串和私钥字符串进行加密和解密的方法/////////////
    //////////////以下是使用公钥字符串和私钥字符串进行加密和解密的方法/////////////
    //////////////以下是使用公钥字符串和私钥字符串进行加密和解密的方法/////////////
    //////////////以下是使用公钥字符串和私钥字符串进行加密和解密的方法/////////////
    //////////////以下是使用公钥字符串和私钥字符串进行加密和解密的方法/////////////
    //////////////以下是使用公钥字符串和私钥字符串进行加密和解密的方法/////////////

    /**
     * 使用公钥字符串加密明文
     * @param plainText 明文
     * @param publicKeyStr Base64编码的公钥字符串
     * @return 加密后的Base64编码字符串
     * @throws Exception 如果加密过程中出现异常
     */
    public static String encryptByPublicKeyStr(String plainText, String publicKeyStr) throws Exception {
        PublicKey publicKey = getPublicKey(publicKeyStr);
        return encrypt(plainText, publicKey);
    }

    /**
     * 使用私钥字符串解密密文
     * @param encryptedText 加密后的Base64编码字符串
     * @param privateKeyStr Base64编码的私钥字符串
     * @return 解密后的明文
     * @throws Exception 如果解密过程中出现异常
     */
    public static String decryptByPrivateKeyStr(String encryptedText, String privateKeyStr) throws Exception {
        PrivateKey privateKey = getPrivateKey(privateKeyStr);
        return decrypt(encryptedText, privateKey);
    }
    
    public static void main(String[] args) {
        try {
            KeyPair keyPair = generateKeyPair();
            String[] keyStrings = getKeyStrings(keyPair);
            String publicKeyStr = keyStrings[0];
            String privateKeyStr = keyStrings[1];
            String plainText = "Test text for encryption and decryption.";
            String encryptedText = encryptByPublicKeyStr(plainText, publicKeyStr);
            String decryptedText = decryptByPrivateKeyStr(encryptedText, privateKeyStr);
            System.out.println("原始报文: " + plainText);
            System.out.println("加密报名: " + encryptedText);
            System.out.println("解密报文: " + decryptedText);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
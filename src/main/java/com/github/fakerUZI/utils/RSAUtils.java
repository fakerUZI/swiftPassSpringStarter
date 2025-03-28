package com.github.fakerUZI.utils;

import org.apache.commons.codec.binary.Base64;

import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author 曹聪
 * @create 2025/2/24-16:32
 * @description RSA工具类
 **/
public class RSAUtils {

    private static KeyFactory getKeyFactory() {
        try {
            return KeyFactory.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            // 应该不会出现
            throw new RuntimeException("初始化RSA KeyFactory失败");
        }
    }

    //private final static Logger logger = LoggerFactory.getLogger(RSAUtil.class);

    public static byte[] sign(SignatureSuite suite, byte[] msgBuf, String privateKeyStr) {
        Signature signature = null;
        try {
            signature = Signature.getInstance(suite.val());
        } catch (Exception e) {
            // 上线运行时套件一定存在
            // 异常不往外抛
        }

        try {
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKeyStr));
            PrivateKey privateKey = getKeyFactory().generatePrivate(keySpec);
            signature.initSign(privateKey);
        } catch (Exception e) {
            throw new RuntimeException("INVALID_PRIKEY");
        }
        try {
            signature.update(msgBuf);
            return signature.sign();
        } catch (SignatureException e) {
            // 一般不会出现
            throw new RuntimeException(e.getMessage());
        }
    }

    public static boolean verifySign(SignatureSuite suite, byte[] msgBuf, byte[] sign, String publicKeyStr) {
        Signature signature = null;
        try {
            signature = Signature.getInstance(suite.val());
        } catch (Exception e) {
            // 上线运行时套件一定存在
            // 异常不往外抛
        }

        try {
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKeyStr));
            PublicKey publicKey = getKeyFactory().generatePublic(keySpec);
            signature.initVerify(publicKey);
        } catch (Exception e) {

            throw new RuntimeException("INVALID_PUBKEY");
        }
        try {
            signature.update(msgBuf);
            return signature.verify(sign);
        } catch (SignatureException e) {
            // 一般不会出现

            throw new RuntimeException("签名格式不合法");
        }
    }

    public static enum SignatureSuite {

        SHA1("SHA1WithRSA"), SHA256("SHA256WithRSA");
        private String suite;

        SignatureSuite(String suite) {
            this.suite = suite;
        }

        public String val() {
            return suite;
        }
    }
}

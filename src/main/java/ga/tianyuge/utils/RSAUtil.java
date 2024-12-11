package ga.tianyuge.utils;

import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

public class RSAUtil {
    public static final String PUK = "PUK";
    public static final String PVK = "PVK";
    private static final String RSA = "RSA";

    @Test
    public void unitTest() throws Exception {
        //生成公钥和私钥
        Map<String, String> genKeyPair = genKeyPair();
        //加密字符串
        String message = "hello123";
        System.out.println("随机生成的公钥为:" + genKeyPair.get(PUK));
        System.out.println("随机生成的私钥为:" + genKeyPair.get(PVK));
        String messageEn = encrypt(message,genKeyPair.get(PUK));
        System.out.println(message + "\t加密后的字符串为:" + messageEn);
        String messageDe = decrypt(messageEn,genKeyPair.get(PVK));
        System.out.println("还原后的字符串为:" + messageDe);
    }

    /**
     * 随机生成密钥对
     * @return 密钥对
     * @throws NoSuchAlgorithmException
     */
    public static Map<String, String>  genKeyPair() throws NoSuchAlgorithmException {
        Map<String, String> rsaKeyMap = new HashMap<>();
        // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(RSA);
        // 初始化密钥对生成器，密钥大小为96-1024位
        keyPairGen.initialize(1024,new SecureRandom());
        // 生成一个密钥对，保存在keyPair中
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        String publicKeyString = new String(Base64.encodeBase64(publicKey.getEncoded()));
        // 得到私钥字符串
        String privateKeyString = new String(Base64.encodeBase64((privateKey.getEncoded())));
        // 将公钥和私钥保存到Map
        rsaKeyMap.put(PUK,publicKeyString);
        rsaKeyMap.put(PVK,privateKeyString);
        return rsaKeyMap;
    }

    /**
     * RSA公钥加密
     *
     * @param data 加密字符串
     * @param puk 公钥
     * @return 密文
     * @throws Exception 加密过程中的异常信息
     */
    public static String encrypt(String data, String puk) throws Exception{
        //base64编码的公钥
        byte[] decoded = Base64.decodeBase64(puk);
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance(RSA).generatePublic(new X509EncodedKeySpec(decoded));
        //RSA加密
        Cipher cipher = Cipher.getInstance(RSA);
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        return Base64.encodeBase64String(cipher.doFinal(data.getBytes(StandardCharsets.UTF_8)));
    }

    /**
     * RSA私钥解密
     *
     * @param data 加密字符串
     * @param pvk 私钥
     * @return 铭文
     * @throws Exception 解密过程中的异常信息
     */
    public static String decrypt(String data, String pvk) throws Exception{
        //64位解码加密后的字符串
        byte[] inputByte = Base64.decodeBase64(data.getBytes(StandardCharsets.UTF_8));
        //base64编码的私钥
        byte[] decoded = Base64.decodeBase64(pvk);
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance(RSA).generatePrivate(new PKCS8EncodedKeySpec(decoded));
        //RSA解密
        Cipher cipher = Cipher.getInstance(RSA);
        cipher.init(Cipher.DECRYPT_MODE, priKey);
        return new String(cipher.doFinal(inputByte));
    }

}
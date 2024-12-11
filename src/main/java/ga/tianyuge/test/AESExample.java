package ga.tianyuge.test;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.SecureRandom;
import java.util.Base64;

public class AESExample {
    public static void main(String[] args) throws Exception {
        // 生成随机密钥
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256); // 使用256位密钥
        SecretKey secretKey = keyGenerator.generateKey();

        // 获取密钥的字节数组
        byte[] raw = secretKey.getEncoded();
        String base64EncodedKey = Base64.getEncoder().encodeToString(raw);
        System.out.println("密钥（Base64编码）：" + base64EncodedKey);

        // 加密
        String data = "Hello, AES!";
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encrypted = cipher.doFinal(data.getBytes());
        String base64Encrypted = Base64.getEncoder().encodeToString(encrypted);
        System.out.println("加密后的数据（Base64编码）：" + base64Encrypted);

        // 解密
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(base64Encrypted));
        System.out.println("解密后的数据：" + new String(decrypted));
    }
}
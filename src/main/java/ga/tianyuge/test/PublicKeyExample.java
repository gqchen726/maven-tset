package ga.tianyuge.test;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class PublicKeyExample {
    public static void main(String[] args) throws Exception {
        // 1. 创建一个 RSA 密钥对生成器
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);  // 2048 位 RSA 密钥
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        // 2. 获取公钥
        PublicKey publicKey = keyPair.getPublic();

        // 3. 获取公钥的编码（X.509 格式）
        byte[] encodedPublicKey = publicKey.getEncoded();

        // 4. 打印 Base64 编码的公钥
        String encodedBase64 = Base64.getEncoder().encodeToString(encodedPublicKey);
        System.out.println("Encoded Public Key: " + encodedBase64);

        String data = "hello123";

        byte[] decodes = Base64.getDecoder().decode(encodedBase64);
        KeyFactory rsa = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(decodes);
        PublicKey publicKey1 = rsa.generatePublic(x509EncodedKeySpec);
        System.out.println(Base64.getEncoder().encodeToString(publicKey1.getEncoded()));
        Cipher rsa1 = Cipher.getInstance("RSA");
        rsa1.init(Cipher.ENCRYPT_MODE, publicKey1);
        byte[] bytes = rsa1.doFinal(data.getBytes(StandardCharsets.UTF_8));
        System.out.println(Base64.getEncoder().encodeToString(bytes));
    }
}

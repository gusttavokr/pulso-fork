package web.pulso.infra.config;

import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.pkcs.RSAPrivateKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateCrtKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Configuration
public class RsaKeyConfig {

    @Value("${jwt.private.key}")
    private Resource privateKeyResource;

    @Value("${jwt.public.key}")
    private Resource publicKeyResource;

    @Bean
    public java.security.interfaces.RSAPrivateKey privateKey() throws Exception {

        String keyContent = new String(
                privateKeyResource.getInputStream().readAllBytes(),
                StandardCharsets.UTF_8
        )
                .replaceAll("-----BEGIN (.*)PRIVATE KEY-----", "")
                .replaceAll("-----END (.*)PRIVATE KEY-----", "")
                .replaceAll("\\s+", "");

        byte[] keyBytes = Base64.getDecoder().decode(keyContent);

        try {

            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);

            return (java.security.interfaces.RSAPrivateKey)
                    KeyFactory.getInstance("RSA").generatePrivate(keySpec);

        } catch (Exception e) {

            RSAPrivateKey bcKey = RSAPrivateKey.getInstance(
                    ASN1Primitive.fromByteArray(keyBytes)
            );

            RSAPrivateCrtKeySpec keySpec = new RSAPrivateCrtKeySpec(
                    bcKey.getModulus(),
                    bcKey.getPublicExponent(),
                    bcKey.getPrivateExponent(),
                    bcKey.getPrime1(),
                    bcKey.getPrime2(),
                    bcKey.getExponent1(),
                    bcKey.getExponent2(),
                    bcKey.getCoefficient()
            );

            return (java.security.interfaces.RSAPrivateKey)
                    KeyFactory.getInstance("RSA").generatePrivate(keySpec);
        }
    }

    @Bean
    public RSAPublicKey publicKey() throws Exception {

        String keyContent = new String(
                publicKeyResource.getInputStream().readAllBytes(),
                StandardCharsets.UTF_8
        )
                .replaceAll("-----BEGIN (.*)PUBLIC KEY-----", "")
                .replaceAll("-----END (.*)PUBLIC KEY-----", "")
                .replaceAll("\\s+", "");

        byte[] keyBytes = Base64.getDecoder().decode(keyContent);

        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);

        return (RSAPublicKey)
                KeyFactory.getInstance("RSA").generatePublic(keySpec);
    }
}
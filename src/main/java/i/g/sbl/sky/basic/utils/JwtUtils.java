package i.g.sbl.sky.basic.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;

@Component
public class JwtUtils {


    private RSAPrivateKey privateKey;
    private RSAPublicKey publicKey;

    @Value("${auth.token.expire_minutes:480}")
    private int expireMinutes = 480;

    @PostConstruct
    public void init() {
        try {
            ClassPathResource privateKeyResource = new ClassPathResource("auth.private.key");
            String privateKeyStr = privateKeyResource.getContentAsString(StandardCharsets.UTF_8).replaceAll("[\\n\\r]", "").replace("-----BEGIN PRIVATE KEY-----", "").replace("-----END PRIVATE KEY-----", "");

            ClassPathResource publicKeyResource = new ClassPathResource("auth.public.key");
            String publicKeyStr = publicKeyResource.getContentAsString(StandardCharsets.UTF_8).replaceAll("[\\n\\r]", "").replace("-----BEGIN PUBLIC KEY-----", "").replace("-----END PUBLIC KEY-----", "");

            privateKey = getPrivateKey(privateKeyStr);
            publicKey = getPublicKey(publicKeyStr);
        } catch (IOException e) {
            throw new RuntimeException("Can not load jwt auth key files", e);
        }
    }

    public String decode(String token) {
        Algorithm algorithm = Algorithm.RSA256(publicKey, privateKey);
        JWTVerifier verifier = JWT.require(algorithm)
                .build();

        DecodedJWT decodedJWT = verifier.verify(token);
        return decodedJWT.getSubject();
    }

    public String encode(String username) {
        Algorithm algorithm = Algorithm.RSA256(publicKey, privateKey);
        String token = JWT.create()
                .withSubject(username)
                .withExpiresAt(Instant.now().plus(this.expireMinutes, ChronoUnit.MINUTES))
                .sign(algorithm);
        return token;
    }

    @SneakyThrows
    private static RSAPublicKey getPublicKey(String content) {
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(content));
        KeyFactory rsa = KeyFactory.getInstance("RSA");
        return (RSAPublicKey) rsa.generatePublic(x509EncodedKeySpec);
    }


    @SneakyThrows
    private static RSAPrivateKey getPrivateKey(String content) {
        PKCS8EncodedKeySpec x509EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(content));
        KeyFactory rsa = KeyFactory.getInstance("RSA");
        return (RSAPrivateKey) rsa.generatePrivate(x509EncodedKeySpec);
    }

}

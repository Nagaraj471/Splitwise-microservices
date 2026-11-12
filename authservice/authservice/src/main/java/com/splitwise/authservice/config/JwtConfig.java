package com.splitwise.authservice.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Configuration
public class JwtConfig {

    @Bean
    public JwtEncoder jwtEncoder() throws Exception{
        String privatePem= Files.readString(Path.of("keys/private.pem"));
        String publicPem=Files.readString(Path.of("keys/public.pem"));
        byte[] privateBytes=decodePem(privatePem,"PRIVATE KEY");
        byte[] publicBytes=decodePem(publicPem,"PUBLIC KEY");
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        RSAPrivateKey privateKey = (RSAPrivateKey)
                keyFactory.generatePrivate(
                        new PKCS8EncodedKeySpec(privateBytes)
                );

        RSAPublicKey publicKey = (RSAPublicKey)
                keyFactory.generatePublic(
                        new X509EncodedKeySpec(publicBytes)
                );

        RSAKey rsaKey = new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .build();

        return new NimbusJwtEncoder(
                new ImmutableJWKSet<>(new JWKSet(rsaKey))
        );

    }
    private byte[] decodePem(String pem, String type) {

        String base64 = pem
                .replace("-----BEGIN " + type + "-----", "")
                .replace("-----END " + type + "-----", "")
                .replaceAll("\\s", "");

        return Base64.getDecoder().decode(base64);
    }
}

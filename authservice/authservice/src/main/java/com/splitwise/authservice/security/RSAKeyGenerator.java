package com.splitwise.authservice.security;

import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.Base64;

public class RSAKeyGenerator {
    public static void main(String[] args) throws Exception{
        KeyPairGenerator generator=KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048);
        KeyPair keyPair=generator.generateKeyPair();

        String privateKey=convertToPEM(keyPair.getPrivate().getEncoded(), "PRIVATE KEY");

        String publicKey=convertToPEM(keyPair.getPublic().getEncoded(), "PUBLIC KEY");

        Path directory=Path.of("keys");
        Files.createDirectories(directory);
        Files.writeString(directory.resolve("private.pem"),privateKey);
        Files.writeString(directory.resolve("public.pem"),publicKey);

        System.out.println("RSA key pair generated successfully!");
    }


    private static String convertToPEM(byte[] key, String type) {

        String encoded = Base64.getMimeEncoder(64, new byte[]{'\n'})
                .encodeToString(key);

        return "-----BEGIN " + type + "-----\n"
                + encoded
                + "\n-----END " + type + "-----";
    }
}

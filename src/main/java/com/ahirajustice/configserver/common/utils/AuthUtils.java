package com.ahirajustice.configserver.common.utils;

import com.ahirajustice.configserver.common.exceptions.ConfigurationException;
import com.ahirajustice.configserver.common.exceptions.ValidationException;
import com.google.common.hash.Hashing;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthUtils {

    public static PublicKey getPublicKey(String publicKeyString) {
        X509EncodedKeySpec keySpec;
        KeyFactory kf;
        PublicKey publicKey;

        try {
            byte [] publicKeyBytes = Base64.getDecoder().decode(publicKeyString);
            keySpec = new X509EncodedKeySpec(publicKeyBytes);
            kf = KeyFactory.getInstance("RSA");
            publicKey = kf.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            throw new ConfigurationException(ex.getMessage());
        }

        return publicKey;
    }

    public static PrivateKey getPrivateKey(String privateKeyString) {
        PKCS8EncodedKeySpec keySpec;
        KeyFactory kf;
        PrivateKey privateKey;

        try {
            byte [] privateKeyBytes = Base64.getDecoder().decode(privateKeyString);
            keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            kf = KeyFactory.getInstance("RSA");
            privateKey = kf.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            throw new ConfigurationException(ex.getMessage());
        }

        return privateKey;
    }

    public static String encryptString(String value, String publicKeyString) {
        String encryptedMessage;

        try {
            PublicKey publicKey = getPublicKey(publicKeyString);

            Cipher encryptCipher = Cipher.getInstance("RSA");
            encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);

            byte[] secretMessageBytes = value.getBytes(StandardCharsets.UTF_8);
            byte[] encryptedMessageBytes = encryptCipher.doFinal(secretMessageBytes);
            encryptedMessage = Base64.getEncoder().encodeToString(encryptedMessageBytes);
        }
        catch (IllegalBlockSizeException | BadPaddingException | NoSuchPaddingException | NoSuchAlgorithmException ex) {
            throw new ConfigurationException(ex.getMessage());
        }
        catch (ConfigurationException | InvalidKeyException ex) {
            throw new ValidationException("Configured public key is invalid. Update configured public key");
        }

        return encryptedMessage;
    }

    public static String decryptString(String value, String privateKeyString) {
        String decryptedMessage;

        try {
            PrivateKey privateKey = getPrivateKey(privateKeyString);

            Cipher decryptCipher = Cipher.getInstance("RSA");
            decryptCipher.init(Cipher.DECRYPT_MODE, privateKey);

            byte[] secretMessageBytes = Base64.getDecoder().decode(value);
            byte[] decryptedMessageBytes = decryptCipher.doFinal(secretMessageBytes);
            decryptedMessage = new String(decryptedMessageBytes, StandardCharsets.UTF_8);
        }
        catch (IllegalBlockSizeException | BadPaddingException | NoSuchPaddingException | NoSuchAlgorithmException ex) {
            throw new ConfigurationException(ex.getMessage());
        }
        catch (ConfigurationException | InvalidKeyException ex) {
            throw new ValidationException("Configured private key is invalid. Update configured private key");
        }

        return decryptedMessage;
    }

    public static String getSha256Hash(String plaintext) {
        return Hashing.sha256()
                .hashString(plaintext, StandardCharsets.UTF_8)
                .toString();
    }

}

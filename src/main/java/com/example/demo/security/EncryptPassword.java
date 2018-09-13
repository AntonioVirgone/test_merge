package com.example.demo.security;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

public class EncryptPassword {

//    public static void main(String[] args) throws InvalidKeySpecException, NoSuchAlgorithmException {
//        forceTest();
//    }
//
//    private static void forceTest() throws InvalidKeySpecException, NoSuchAlgorithmException {
//        for (int i = 0; i < 10; i++) {
//            String originalPassword = "password";
//            String generatedSecuredPasswordHash = generateStorngPasswordHash(originalPassword);
//            System.out.println(generatedSecuredPasswordHash);
//        }
//    }


    public static String generateStorngPasswordHash(String password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        int iterations = 1000;
        char[] chars = password.toCharArray();
//        byte[] salt = getSalt();

        System.out.println(salt);

        PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 32 * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = skf.generateSecret(spec).getEncoded();
        return iterations + ":" + toHex(salt) + ":" + toHex(hash);
    }

    public static byte[] getSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }

    public static String toHex(byte[] array) {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if (paddingLength > 0) {
            return String.format("%0" + paddingLength + "d", 0) + hex;
        } else {
            return hex;
        }
    }
}
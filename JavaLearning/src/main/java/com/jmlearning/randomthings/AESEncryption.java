//package com.jmlearning.randomthings;
//
//import javax.crypto.*;
//import javax.xml.bind.DatatypeConverter;
//import java.security.InvalidKeyException;
//import java.security.NoSuchAlgorithmException;
//
//public class AESEncryption {
//
//    public static void main(String[] args) throws Exception {
//
//        String plainText = "Hello World";
//        SecretKey secKey = getSecretEncryptionKey();
//        byte[] cipherText = encryptText(plainText, secKey);
//        String decryptedText = decryptText(cipherText, secKey);
//
//        System.out.println("Original Text: " + plainText);
//        System.out.println("AES Key (Hex): " + bytesToHex(secKey.getEncoded()));
//        System.out.println("Encrypted Text (Hex): " + bytesToHex(cipherText));
//        System.out.println("Decrypted Text: " + decryptedText);
//    }
//
//    public static SecretKey getSecretEncryptionKey() throws NoSuchAlgorithmException {
//
//        KeyGenerator aesKeyGenerator = KeyGenerator.getInstance("AES");
//        aesKeyGenerator.init(128);
//        SecretKey secretKey = aesKeyGenerator.generateKey();
//        return secretKey;
//    }
//
//    public static byte[] encryptText(String plainText, SecretKey secKey) throws NoSuchAlgorithmException,
//            NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
//
//        Cipher aesCipher = Cipher.getInstance("AES");
//        aesCipher.init(Cipher.ENCRYPT_MODE, secKey);
//        byte[] byteCipherText = aesCipher.doFinal(plainText.getBytes());
//        return byteCipherText;
//    }
//
//    public static String decryptText(byte[] byteCipherText, SecretKey secKey) throws NoSuchAlgorithmException,
//            NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
//
//        Cipher aesCipher = Cipher.getInstance("AES");
//        aesCipher.init(Cipher.DECRYPT_MODE, secKey);
//        byte[] bytePlainText = aesCipher.doFinal(byteCipherText);
//        return new String(bytePlainText);
//    }
//
//    private static String bytesToHex(byte[] hash) {
//
//        return DatatypeConverter.printHexBinary(hash);
//    }
//}
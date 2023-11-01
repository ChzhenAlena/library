package controller;

import javax.crypto.*;
import java.io.*;
import java.security.*;
import java.security.cert.CertificateException;


public class Encryptor {
    static private int keyBitSize = 256;
    static private KeyStore keyStore = null;
    static private KeyStore.ProtectionParameter entryPassword;
    static private SecretKey key;
    static final char[] keyStorePassword = "123abc".toCharArray();
    static final char[] keyPassword = "789xyz".toCharArray();
    static final String alias = "keyAlias";
    static final String keyStoreDirectory = "src/files/keystore.ks";
    public static void initEncryptor(){
        try {
            keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        } catch (KeyStoreException e) {
            throw new RuntimeException(e);
        }
        entryPassword = new KeyStore.PasswordProtection(keyPassword);
        File keystoreFile = new File(keyStoreDirectory);
        if (keystoreFile.length() == 0L)
            key = generateKey();
        else
            key = loadKey();
    }

    public static String encrypt(String pass){
        byte[] password;
        Cipher cipher = null;
        byte[] cipherPass;
        String encryptedPass;
        try {
            cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            password = pass.getBytes("UTF-8");
            cipherPass = cipher.doFinal(password);
            encryptedPass = new String(cipherPass, "UTF-8");
        } catch (IllegalBlockSizeException | UnsupportedEncodingException | BadPaddingException |
                 NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
        return encryptedPass;
    }


    public static SecretKey generateKey() {
        KeyGenerator keyGenerator;
        try {
            keyGenerator = KeyGenerator.getInstance("AES");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        keyGenerator.init(keyBitSize, new SecureRandom());
        SecretKey key = keyGenerator.generateKey();
        try(InputStream keyStoreInputStream = new FileInputStream(keyStoreDirectory)){
            keyStore.load(null, keyStorePassword);
        } catch (IOException | CertificateException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        KeyStore.SecretKeyEntry secretKeyEntry = new KeyStore.SecretKeyEntry(key);
        try {
            keyStore.setEntry(alias, secretKeyEntry, entryPassword);
        } catch (KeyStoreException e) {
            throw new RuntimeException(e);
        }
        try (FileOutputStream keyStoreOutputStream = new FileOutputStream(keyStoreDirectory)) {
            keyStore.store(keyStoreOutputStream, keyStorePassword);
        } catch (NoSuchAlgorithmException | KeyStoreException | CertificateException | IOException e) {
            throw new RuntimeException(e);
        }
        return key;
    }

    public static SecretKey loadKey(){
        try(InputStream keyStoreData = new FileInputStream(keyStoreDirectory)){
            keyStore.load(keyStoreData, keyStorePassword);
        } catch (IOException | NoSuchAlgorithmException | CertificateException e) {
            throw new RuntimeException(e);
        }
        KeyStore.SecretKeyEntry keyEntry = null;
        try {
            keyEntry = (KeyStore.SecretKeyEntry) keyStore.getEntry(alias, entryPassword);
        } catch (NoSuchAlgorithmException | KeyStoreException | UnrecoverableEntryException e) {
            throw new RuntimeException(e);
        }
        return keyEntry.getSecretKey();
    }
}

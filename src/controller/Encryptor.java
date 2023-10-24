package controller;

import javax.crypto.*;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;


public class Encryptor {
    static byte[] password;
    static Cipher cipher = null;
    static KeyGenerator keyGenerator = null;
    static byte[] cipherPass;
    static int keyBitSize = 256;
    static String encryptedPass = "";
    public static void initEncryptor(){
        try {
            cipher = Cipher.getInstance("AES");
            keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(keyBitSize, new SecureRandom());
            SecretKey key = keyGenerator.generateKey();
            //System.out.println(key);
            cipher.init(Cipher.ENCRYPT_MODE, key);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }
    public static String encrypt(String pass){
        try {
            password = pass.getBytes("UTF-8");//
            cipherPass = cipher.doFinal(password);
            encryptedPass = new String(cipherPass, "UTF-8");//
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        } catch (BadPaddingException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return encryptedPass;
    }
}

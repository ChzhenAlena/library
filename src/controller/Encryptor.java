package controller;
import javax.crypto.*;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;


public class Encryptor {

    public static String encrypt(String pass){
        byte[] password;
        Cipher cipher = null;
        KeyGenerator keyGenerator = null;
        byte[] cipherPass;
        int keyBitSize = 256;
        String encryptedPass = "";
        try {
            password = pass.getBytes("UTF-8");//
            cipher = Cipher.getInstance("AES");
            keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(keyBitSize, new SecureRandom());
            SecretKey key = keyGenerator.generateKey();
            cipher.init(Cipher.ENCRYPT_MODE, key);
            cipherPass = cipher.doFinal(password);
            encryptedPass = new String(cipherPass, "UTF-8");//
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        } catch (BadPaddingException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        System.out.println(encryptedPass);
        return encryptedPass;
    }
}

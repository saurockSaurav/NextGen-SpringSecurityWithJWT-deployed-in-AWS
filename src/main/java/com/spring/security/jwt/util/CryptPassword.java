package com.spring.security.jwt.util;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Objects;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES-GCM inputs - 12 bytes IV, need the same IV and secret keys for encryption
 * and decryption.
 * <p>
 * The output consist of iv, encrypted content, and auth tag in the following
 * format: output = byte[] {i i i c c c c c c ...}
 * <p>
 * i = IV bytes c = content bytes (encrypted content, auth tag)
 */
public class CryptPassword {

	 public static final int AES_KEY_SIZE = 256;
     public static final int GCM_IV_LENGTH = 12;
     public static final int GCM_TAG_LENGTH = 16;
     
	public static String conversion(String str, boolean isRequestToEncrypt) throws Exception {
		
		EncryptorAesGcm.testEnDeCrypt(str);
		
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(AES_KEY_SIZE);

        // Generate Key
        SecretKey key = keyGenerator.generateKey();

        byte[] IV = new byte[GCM_IV_LENGTH];
        SecureRandom random = new SecureRandom();
        random.nextBytes(IV);
        byte[] cipherText = encrypt(str.getBytes(), key, IV);
        
        if(Objects.nonNull(str) && isRequestToEncrypt) {
        	return Base64.getEncoder().encodeToString(cipherText);
        }
        else {
        	return decrypt(str.getBytes(), key, IV);
        }
		
    }

	public static byte[] encrypt(byte[] plaintext, SecretKey key, byte[] IV) throws Exception
    {
        // Get Cipher Instance for selected algorithm
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");

        // Create SecretKeySpec for key
        SecretKeySpec keySpec = new SecretKeySpec(key.getEncoded(), "AES");

        // Create GCMParameterSpec for key
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, IV);

        // Initialize Cipher for ENCRYPT_MODE for encrypt plaintext
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, gcmParameterSpec);

        // Perform Encryption
        byte[] cipherText = cipher.doFinal(plaintext);

        return cipherText;
    }

    public static String decrypt(byte[] cipherText, SecretKey key, byte[] IV) throws Exception
    {
        // Get Cipher Instance based on selective AES algorithm
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");

        // Create SecretKeySpec for key
        SecretKeySpec keySpec = new SecretKeySpec(key.getEncoded(), "AES");

        // Create GCMParameterSpec for key
        IV = new byte[GCM_IV_LENGTH]; //here is issue

        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, IV);

        // Initialize Cipher for DECRYPT_MODE to in plain text 
        cipher.init(Cipher.DECRYPT_MODE, keySpec, gcmParameterSpec);

        // Perform Decryption on encrypted text
        byte[] decryptedText = cipher.doFinal(cipherText);

        return new String(decryptedText);
    }
}

package net.indrix.arara.tools.crypt;

import java.io.*;

public class XORCipherImpl implements Crypt {

  private static final String DEFAULT_KEY = "XORCipher";
  
  public static final String CHAR_ENCODING = "US-ASCII";

  private String secretKey = null;

  private static byte[] crypt(byte[] M, byte[] K){ 
    byte[] D = null; 
    if (M.length != K.length){
      D = new byte[M.length]; 
      for (int i = 0; i < M.length; i++){ 
	D[i] = (byte)(M[i] ^ K[i % K.length]); 
      } 
    } 
    return D; 
  }

  public void setKey(String secretKey){ this.secretKey = secretKey;}

  public void clearKey(){ this.secretKey = null; }

  public String encrypt(String cleartext) { 
    String encrypted = null;
    try {
      byte[] cipher, key, clear = cleartext.getBytes(CHAR_ENCODING);
      if (this.secretKey != null) key = secretKey.getBytes(CHAR_ENCODING);
      else                        key = DEFAULT_KEY.getBytes(CHAR_ENCODING);
      cipher = XORCipherImpl.crypt(clear,key);
      encrypted =new String(cipher,CHAR_ENCODING);
    } catch(UnsupportedEncodingException e) { encrypted = null; }
    return encrypted;
  }

  public String decrypt(String ciphertext) {
    String decrypted = null;
    try {
      byte[] cipher, key, clear  = ciphertext.getBytes(CHAR_ENCODING);
      if (this.secretKey != null) key = secretKey.getBytes(CHAR_ENCODING);
      else                        key = DEFAULT_KEY.getBytes(CHAR_ENCODING);
      cipher = XORCipherImpl.crypt(clear,key);
      decrypted = new String(cipher,CHAR_ENCODING);
    }catch(UnsupportedEncodingException e) { decrypted = null;}
    return decrypted;
  }

}













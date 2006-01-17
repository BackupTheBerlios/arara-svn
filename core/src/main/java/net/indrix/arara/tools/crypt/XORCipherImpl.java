package net.indrix.tools.crypt;

import java.io.*;

/**
  Esta classe implementa o metodo de criptografia baseado na operaçao logica XOR.
  As operaçoes logicas atuam sobre bits, logo o resultado e' quaqler padrao de bits. 
  Uma vez que o resultado do XOR pode nao ser imprimivel, nao e' recomendavel usar o 
  texto criptografado em impressoes (tela, papel, arquivos texto, streams de texto, etc.)
  Tambem nao e' recomendado armazenar o texto cifrado em variaveris de caracteres.
  Recomenda-se arrays de bytes.
  java.lang.String pode ser usada se o valor nao for impresso. 
  
  Linguagens sem o operador xor podem usar a expressao abaixo, 
  baseada em !(not), or e and.

  A xor B = (!A and B) or (A and !B)

  Tabela verdade do XOR 

     0 xor 0 = 0
     1 xor 0 = 1
     0 xor 1 = 1
     1 xor 1 = 0
  
  Demonstracao do algoritmo criptografico:

    chave = 01001001 (chave secreta)
            xor
    texto = 00011110 (mensagem original)
    ----------------
    cifra = 01010111 (mensagem criptografada) 
            xor 
    chave = 01001001 (chave secreta)
    ----------------
    texto = 00011110 (mensagem original descriptografa)

**/
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


  public static void main(String args[]){  
    
    XORCipherImpl c = new XORCipherImpl(); 
  
    String key = "\\]^_`a|}~!\"%&'(";  
    String msg = "\\]@l&x@ndr&_M&10_8r@g@."; 

    if (args.length > 0) msg = args[0];  
    else                 c.setKey(key);  

    System.out.println("[[[["+c.encrypt(msg)+"]]]]");  
    System.out.println("[[[["+c.decrypt(c.encrypt(msg))+"]]]]");  
  }

}













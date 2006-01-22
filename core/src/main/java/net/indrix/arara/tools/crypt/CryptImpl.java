package net.indrix.arara.tools.crypt;



public class CryptImpl implements Crypt {

  // variaçoes de alfabetos
  public static final int LONG_ALPHA  = 1;
  public static final int SHORT_ALPHA = 0;
  public static final int ALPHA_NUM = SHORT_ALPHA;

  // tipos de algoritmos criptograficos
  public static final int XOR_CIPHER      = 1;
  public static final int VIGENERE_CIPHER = 2;

  /*pck*/ static final String sequence       = "N7Rx6bWFdL5GfhaMXj0Sk2iBHmElInD8pAwy3JcT1osQvUzCKe4quVgrt9POYZ";
  private static final String key            = "pu2AjoG7LieW";
  private static final int    sequenceLength = sequence.length();
  private static final int    keyLength      = key.length();

  public static Crypt getInstance(int cipherCode) {
    Crypt cipher = null;
    switch(cipherCode){
    case(XOR_CIPHER):
      cipher = new XORCipherImpl();
      break;
    case(VIGENERE_CIPHER):
      cipher = new VigenereCipherImpl();
      break;
    default:
      cipher = new CryptImpl();
      break;
    }
    return cipher;
  }

  protected VigenereCipherImpl vigCipher;

  /*pack*/ CryptImpl(){ vigCipher = new VigenereCipherImpl();}

  public String encrypt(String clearText){
    if (vigCipher.keyIsNotSet() || vigCipher.keyIsDefault() ) vigCipher.setKey(key);

    for(int i = 0; i < clearText.length(); i++){
      char aux = clearText.charAt(i);
      if (((aux < '0') || (aux > '9')) &&
	  ((aux < 'A') || (aux > 'Z')) &&
	  ((aux < 'a') || (aux > 'z'))) {
	return null;
      }
    }
    return vigCipher.encryptGeneric(clearText,SHORT_ALPHA);
  }

  public String decrypt(String cipherText){

    if (vigCipher.keyIsNotSet() || vigCipher.keyIsDefault()) vigCipher.setKey(key);

    for(int i = 0; i < cipherText.length(); i++){
      char aux = cipherText.charAt(i);
      if (((aux < '0') || (aux > '9')) &&
	  ((aux < 'A') || (aux > 'Z')) &&
	  ((aux < 'a') || (aux > 'z'))) {
	return null;
      }
    }
    return vigCipher.decryptGeneric(cipherText,SHORT_ALPHA);
  }

  public void setKey(String key){ vigCipher.setKey(key);}

  }





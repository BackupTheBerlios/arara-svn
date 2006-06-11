package net.indrix.arara.tools.crypt;



public class CryptImpl implements Crypt {

  // variaçoes de alfabetos
  public static final int LONG_ALPHA  = 1;
  public static final int SHORT_ALPHA = 0;
  public static final int ALPHA_NUM = SHORT_ALPHA;

  // tipos de algoritmos criptograficos
  public static final int SAGRE_CIPHER    = 0;
  public static final int XOR_CIPHER      = 1;
  public static final int VIGENERE_CIPHER = 2;

  /*pck*/ static final String sequence       = "N7Rx6bWFdL5GfhaMXj0Sk2iBHmElInD8pAwy3JcT1osQvUzCKe4quVgrt9POYZ";
  private static final String key            = "pu2AjoG7LieW";
  private static final int    sequenceLength = sequence.length();
  private static final int    keyLength      = key.length();

  public static Crypt getInstance(int cipherCode) {
    Crypt cipher = null;
    switch(cipherCode){
    case(SAGRE_CIPHER):
      cipher = new CryptImpl();
      break;
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

  /**
     Implementaçao antiga. Ainda eh matida por razoes historicas.
   **/
  public String encryptOld(String clearText){
    char[] alphabet_key = new char[sequenceLength +1];
    int char_pos = 0, line_pos = 0, pos_aux_f, pos_aux_s, char_counter = 0;
    char char_aux;
    StringBuffer result = new StringBuffer(64);

    if ((clearText == null) || clearText.equals("")) return null;

    int clearLength = clearText.length();
    for(int i = 0; i < clearLength; i++){
      char_aux = clearText.charAt(i);
      if (((char_aux < '0') || (char_aux > '9')) &&
	  ((char_aux < 'A') || (char_aux > 'Z')) &&
	  ((char_aux < 'a') || (char_aux > 'z'))) {
	return null;
      }
    }

    int pos = 0;
    for (int i = 0; i < clearLength; i++) {
      for (int j = 0; j < keyLength; j++) {
	if ((pos == (j + (keyLength * i))) && (pos < clearLength)) {
	  for (int count = 0; count < sequenceLength; count++)
	    if (sequence.charAt(count) == key.charAt(j))
	      line_pos = count;
	  pos_aux_f = line_pos;
	  pos_aux_s = 0;
	  for (int count = 0; count < sequenceLength; count++)
	    if (line_pos < sequenceLength) {
	      alphabet_key[count] = sequence.charAt(pos_aux_f);
	      pos_aux_f++;
	      line_pos++;
	    }
	    else {
	      alphabet_key[count] = sequence.charAt(pos_aux_s);
	      pos_aux_s++;
	    }
	  for (int count = 0; count < sequenceLength; count++)
	    if (sequence.charAt(count) == clearText.charAt(pos)) {
	      char_pos = count;
	      break;
	    };
	  result.append(alphabet_key[char_pos]);
	  pos++;
	}
      }
    }
    return result.toString();
  }

  /**
     Implementaçao antiga. Ainda eh matida por razoes historicas.
  **/
  public String decryptOld(String cipherText){
    char[] alphabet_key = new char[sequenceLength +1];
    int cipherTextLength;
    StringBuffer result = new StringBuffer(64);
    int i = 0;

    if ((cipherText  == null) || cipherText.equals("")){
      return null;
    }
    cipherTextLength = cipherText.length();
    for (int pos = 0 ; pos < cipherText.length(); i++) {
      for (int j = 0; j < keyLength; j++) {
	if ((pos == ( j + (keyLength * i))) && (pos < cipherTextLength)) {
	  if (" `\"~^\\".indexOf(cipherText.charAt(pos))!=-1) {
	    return null;
	  }
	  int line_pos = 0;
	  int pos_aux_f;
	  int pos_aux_s;
	  for (int count = 0; count < sequenceLength; count++)
	    if (sequence.charAt(count) == key.charAt(j))
	      line_pos = count;
	  pos_aux_f = line_pos;
	  pos_aux_s = 0;
	  for (int count = 0; count < sequenceLength; count++)
	    if (line_pos < sequenceLength) {
	      alphabet_key[count]= sequence.charAt(pos_aux_f);
	      pos_aux_f++;
	      line_pos++;
	    } else {
	      alphabet_key[count] = sequence.charAt(pos_aux_s);
	      pos_aux_s++;
	    }
	  int char_pos = 0;
	  for (int count = 0; count < sequenceLength; count++)
	    if (alphabet_key[count] == cipherText.charAt(pos))
	      char_pos = count;
	  result.append(sequence.charAt(char_pos));
	  pos++;
	}
      }
    }
    return result.toString();
  }

  private static void testCipher(String clearText){
    CryptImpl sc = new CryptImpl();
    String cipherText = sc.encrypt(clearText);
    if (!cipherText.equals(clearText)) System.out.println(sc.decrypt(cipherText));
    //Mensagem:Erro no ciframento
    else System.out.println("Erro");
  }


  private static String encryptCommandLine(String line){
    CryptImpl sc = new CryptImpl();
    return sc.encrypt(line);
  }

  private static String decryptCommandLine(String line){
    CryptImpl sc = new CryptImpl();
    return sc.decrypt(line);
  }

  /*
  public static void main(String args[]){
    if (args.length > 1){
      if (args[0].equals("-test")) testCipher(args[1]);
      if (args[0].equals("-encrypt")) System.out.println(encryptCommandLine(args[1]));
      if (args[0].equals("-decrypt")) System.out.println(decryptCommandLine(args[1]));
    }
  }
  */

}





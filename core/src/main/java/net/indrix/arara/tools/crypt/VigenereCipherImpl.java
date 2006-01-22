package net.indrix.arara.tools.crypt;

public class VigenereCipherImpl implements Crypt {

  private static final String DEFAULT_KEY = "\\]^_`a|}~!\"%&'(";

  private String secretKey = null;

  public VigenereCipherImpl(){
    this.secretKey = DEFAULT_KEY;
  }

  public void setKey(String secretKey){ this.secretKey = secretKey;}

  public boolean keyIsNotSet(){ return (this.secretKey == null); }

  public boolean keyIsDefault(){ return (this.secretKey.equals(DEFAULT_KEY)); }

  public void clearKey(){ this.secretKey = null; }

  public String encrypt(String cleartext) {
    return this.encryptGeneric(cleartext,CryptImpl.SHORT_ALPHA);
  }

  /*pack*/ String encryptGeneric(String cleartext, int alphaType) {
     String alphabet = alphabetGenerator(alphaType);
     int perms[] = permutations(secretKey,alphabet,true);
     return transform(cleartext,alphabet, perms);
  }

  public String decrypt(String ciphertext){
    return this.decryptGeneric(ciphertext,CryptImpl.SHORT_ALPHA);
  }

  /*pack*/ String decryptGeneric(String ciphertext, int alphaType){
    String alphabet = alphabetGenerator(alphaType);
    int perms[] = permutations(secretKey,alphabet,false);
    return transform(ciphertext, alphabet, perms);
  }

  private String transform(String cleartext, String alphabet, int[] perms) {
    String key, msg = cleartext;
    StringBuffer crypt = new StringBuffer();

    if (this.secretKey == null || this.secretKey.equals("")) key = DEFAULT_KEY;
    else                                                     key = this.secretKey;

    MatrixArray ma = new MatrixArray(msg,key.length());
    int shift, lines = ma.lines();
    char c;
    for(int i = 0; i < lines; i++){
      for(int j = 0; j < ma.cols; j++){
	c = ma.getChar(i,j);
	if (c != 0x00) {
	  shift = (alphabet.indexOf(c) + perms[j]) % alphabet.length();
	  crypt.append(alphabet.charAt(shift));
	}
      }
    }
    return crypt.toString();
  }

  private String alphabetGenerator(int alphaType){
    String output =  null;
    switch(alphaType){
    case CryptImpl.LONG_ALPHA:
      StringBuffer buffy = new StringBuffer();
      for(int i = 0x20; i < 0x7F; i++){
	char c = (char) i;
	buffy.append(c);
      }
      output = buffy.toString();
      break;
    case CryptImpl.SHORT_ALPHA:
      output = CryptImpl.sequence;
      break;
    }
    return output;
  }

  private int[] permutations(String key,String alphabet, boolean crypt){
    int perms[] = new int[key.length()];
    char c;
    for (int i = 0; i < key.length(); i++) {
      c = key.charAt(i);
      if (crypt) perms[i] = alphabet.indexOf(c);
      else       perms[i] = alphabet.length() - alphabet.indexOf(c);
    }
    return perms;
  }



}

class MatrixArray {

  private String array;
  public final int cols;

  public MatrixArray(String array, int cols){
    this.array = array;
    this.cols = cols;
  }

  public char getChar(int i, int j) {
    int pos = i*cols + j;
    char c;
    if (pos < array.length()) c = array.charAt(pos);
    else                      c = (char) 0x00;
    return c;
  }

  public int lines(){
    int lines = array.length()/cols;
    if (array.length() % cols != 0) lines++;
    return lines;
  }

}







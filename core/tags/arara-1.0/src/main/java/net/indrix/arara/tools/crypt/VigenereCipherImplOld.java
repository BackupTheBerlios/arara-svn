package net.indrix.arara.tools.crypt;

/**

 * Esta classe implementa o metodo de criptografia conhecido como Vigenere Cipher.
   Ele e' uma cifrador por substituicao polialfabetica.
   Esta e' uma implementacao de referencia.
   O texto abaixo descreve o algoritmo (Menesez at all, Hand book of applied cryptograpty, 1999):

   " Polyalphabetic substitution ciphers

   Definition: A polyalphabetic substitution cipher is a block cipher with block length t over
   an alphabet A having the following properties:
     (i) the key space K consists of all ordered sets of t permutations (p1 , p2 , ... , pt),where
         each permutation pi is defined on the set A;
    (ii) encryption of the message m = (m1, m2, ..., mt) under the key e = (p1 , p2 , ... , pt)
         is given by Ee(m) = ( p1(m1) p2(m2)...pt(mt)); and
   (iii) the decryption key associated with e = (p1 , p2 , ... , pt) is d = ((p1)^(-1) , (p2)^(-1) ,
         ... (pt)^(-1)).

   Example: LetA = { A, B, C, ... ,X, Y, Z } and t = 3. Choose e = (p1 , p2 , p3),where p1 maps each
   letter to the letter three positions to its right in the alphabet, p2 to the one seven positions
   to its right, and p3 ten positions to its right.

   If m = THI SCI PHE RIS CER TAI NLY NOT SEC URE

   then

   c = Ee(m) = WOS VJS SOO UPC FLB WHS QSI QVD VLM XYO

   Polyalphabetic ciphers have the advantage over simple substitution ciphers that symbol
   frequencies are not preserved. In the example above, the letter E is encrypted to both O and
   L. However, polyalphabetic ciphers are not significantly more difficult to cryptanalyze, the
   approach being similar to the simple substitution cipher. In fact, once the block length t is
   determined, the ciphertext letters can be divided into t groups (where group i, 1 <= i <= t,
   consists of those ciphertext letters derived using permutation pi), and a frequency analysis
   can be done on each group. "

 * A implementaçao abaixo considera todos os caracteres ascii imprimiveis: 0x20 ate' 0x7F (exadecimal).
   Eles sao:

       !"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\]^_`abcdefghijklmnopqrstuvwxyz{|}~


   Ela ainda nao trata caracteres iso (ascii expandido) como letras acentuadas e cedilha.
   Implementacoes em linguagens diferentes devem considerar a representacao de caracteres especiais
   da linguagem de programacao. Por exemplo, em Java, a string "\]" contem um unico caracter (scape), mas
   a string "\\]" contem dois caracteres '\' e ']'.

 * A passagem de argumentos pela linha de comando deve considerar as caracteristicas do interpretador
   de comandos. No unix, um unico comando com varias palavras e' indicado por aspas duplas ("palavra1
   palavra2 ... etc."). Ainda no unix, deve-se ter cuidado com a expansao de '*' e '!', entre outros.

 * Esta implementacao usara' uma chave default se nenhuma outra for atribuida.

 * Este algoritmo deve ser relativamente facil de implementar em C, VB e GML. Considera-se ate' a
   traducao da implementacao.

 * O metodo main descreve o uso da classe. Ele deve ser considerado apenas uma demonstracao.

**/
public class VigenereCipherImplOld implements Crypt {

  private static final String DEFAULT_KEY = "\\]^_`a|}~!\"%&'(";

  private String secretKey = null;

  public VigenereCipherImplOld(){
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

    MatrixArrayOld ma = new MatrixArrayOld(msg,key.length());
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


/*
  public static void main(String args[]){

    VigenereCipherImpl c = new VigenereCipherImpl();
//    String alphabet = c.alphabetGenerator();
//    System.out.println(alphabet);
//    System.out.println(alphabet.length());

    String key = "\\]^_`a|}~!\"%&'(";
    String msg = "\\]@l&x@ndr&_M&10_8r@g@.";

    if (args.length > 0) msg = args[0];
    else                 c.setKey(key);

    System.out.println("[[[["+msg+"]]]]");
    System.out.println("[[[["+c.encrypt(msg)+"]]]]");
    System.out.println("[[[["+c.decrypt(c.encrypt(msg))+"]]]]");
  }
*/
  /*
  public static void main(String args[]){
    SagreCipherImpl c = (SagreCipherImpl) SagreCipherImpl.getInstance(SagreCipherImpl.SAGRE_CIPHER);
    String msg, t1,t2, alpha = SagreCipherImpl.sequence;
    int max = 0;
    long range = alpha.length();
    range = range * range * range * range * range * range * range * range * range * range;
    boolean ok_enc =  true, ok_dec = true, wrote = false;
    for (int i = 0; i < alpha.length(); i++)
      for (int ii = 0; ii < alpha.length(); ii++)
	for (int iii = 0; iii < alpha.length(); iii++)
	  for (int j = 0; j < alpha.length(); j++)
	    for (int jj = 0; jj < alpha.length(); jj++)
	      for (int jjj = 0; jjj < alpha.length(); jjj++)
		for (int k = 0; k < alpha.length(); k++)
		  for (int kk = 0; kk < alpha.length(); kk++)
		    for (int kkk = 0; kkk < alpha.length(); kkk++)
		      for (int o = 0; o < alpha.length(); o++)
    {
      msg =  ""+ alpha.charAt(i)+ alpha.charAt(ii)+ alpha.charAt(iii);
      msg = msg+ alpha.charAt(j)+ alpha.charAt(jj)+ alpha.charAt(jjj);
      msg = msg+ alpha.charAt(k)+ alpha.charAt(kk)+ alpha.charAt(kkk)+ alpha.charAt(o);

      t1 = c.encrypt(msg);
      t2 = c.encryptOld(msg);
      if (!t1.equals(t2)) ok_enc = false;

      max++;
      if (((max/range*100) % 2) == 0) wrote =  false;
      if (!wrote) {System.out.print(" " +(max/range)*100 + "%:"+msg+":"+t1+":"+t2+":"+max);wrote =  true;}

      t1 = c.decrypt(t1);
      t2 = c.decryptOld(t2);
      if (!(t1.equals(t2)&& t1.equals(msg))) ok_dec = false;

      if (! (ok_dec && ok_enc)) {
	System.out.println("ok enc = "+ok_enc);
	System.out.println("ok dec = "+ok_dec);
	System.out.println("max = "+max);
	System.exit(0);
      }

    }
    System.out.println("ok enc = "+ok_enc);
    System.out.println("ok dec = "+ok_dec);
    System.out.println("max = "+max);
  }

  */

  /*
  public static void main(String args[]){
    SagreCipher c = SagreCipherImpl.getInstance(SagreCipherImpl.SAGRE_CIPHER);
    System.out.println("[[[["+c.decrypt(args[0])+"]]]]");
    System.out.println("[[[["+c.encrypt(c.decrypt(args[0]))+"]]]]");
  }
  */

}

class MatrixArrayOld {

  private String array;
  public final int cols;

  public MatrixArrayOld(String array, int cols){
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







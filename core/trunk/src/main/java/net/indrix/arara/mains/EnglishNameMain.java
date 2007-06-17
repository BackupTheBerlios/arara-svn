package net.indrix.arara.mains;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JOptionPane;

public class EnglishNameMain {
    private static final String PONTO_VIRGULA = ";";

    public static void main(String a[]){
       BufferedReader in = null;
        
        try {
            String filename = JOptionPane.showInputDialog("Arquivo:");
            in = new BufferedReader(new FileReader(filename));
            String line = null;
            int count = 0;
            while ((line = in.readLine()) != null) {
                String tokens[] = line.split(PONTO_VIRGULA);
                if (tokens.length > 0) {
                    String specieName = tokens[0];
                    if (specieName.trim().length() == 0){
                        System.out.println("ERROR SPECIE");
                        System.exit(0);
                    }
                    String englishName = tokens[1];
                    if (englishName.trim().length() == 0){
                        System.out.println("ERROR ENGLISH NAME");
                        System.exit(0);
                    }
                    count++;
                    System.out.println(specieName + " | " + englishName);
                }
            }
            System.out.println("\n");
            System.out.println("Total: " + count);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (in != null) {
            try {
                in.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }       
    }
}

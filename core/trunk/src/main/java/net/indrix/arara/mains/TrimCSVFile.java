package net.indrix.arara.mains;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;

public class TrimCSVFile {
    private static final String PONTO_VIRGULA = ";";

    /**
     * @param args
     */
    public static void main(String[] args) {
        BufferedReader in = null;
        BufferedWriter out = null;
        try {
            String filename = JOptionPane.showInputDialog("Arquivo Entrada:");
            String filename2 = JOptionPane.showInputDialog("Arquivo Saída:");
            in = new BufferedReader(new FileReader(filename));
            out = new BufferedWriter(new FileWriter(filename2));
            String line = null;
            while ((line = in.readLine()) != null) {
                String tokens[] = line.split(PONTO_VIRGULA);
                for (int i = 0; i < tokens.length; i++) {
                    tokens[i] = tokens[i].trim();
                    out.write(tokens[i]+";");
                }
                out.write("\n");
            }
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
        if (out != null) {
            try {
                out.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        System.exit(0);
    }

}

package net.indrix.arara.tools.crypt;

class MatrixArray {

    private String array;

    public final int cols;

    public MatrixArray(String array, int cols) {
        this.array = array;
        this.cols = cols;
    }

    public char getChar(int i, int j) {
        int pos = i * cols + j;
        char c;
        if (pos < array.length())
            c = array.charAt(pos);
        else
            c = (char) 0x00;
        return c;
    }

    public int lines() {
        int lines = array.length() / cols;
        if (array.length() % cols != 0)
            lines++;
        return lines;
    }

}

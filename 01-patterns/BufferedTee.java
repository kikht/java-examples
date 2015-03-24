import java.io.*;

public class BufferedTee {
    public static void main(String[] argv) {
        try {
            OutputStream fout = new BufferedOutputStream(
                    new FileOutputStream(argv[0]));
            int c;
            while ((c = System.in.read()) != -1) {
                System.out.write(c);
                fout.write(c);
            }
            fout.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } 
    }
}

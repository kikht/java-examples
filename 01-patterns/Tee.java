import java.io.*;

public class Tee {
    public static void main(String[] argv) {
        try {
            System.out.write(-1);
            OutputStream fout = new FileOutputStream(argv[0]);
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

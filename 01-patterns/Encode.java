import java.io.*;

public class Encode {
    public static void main(String[] argv) {
        try {
            Reader input = new InputStreamReader(System.in, "UTF-8");
            Writer output = new OutputStreamWriter(System.out, "UTF-16");
            int c;
            while ((c = input.read()) != -1) {
                output.write(c);
            }
            output.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } 
    }
}

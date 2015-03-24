import java.io.*;

public class Template extends InputStream {
    public int read() {
        return 'a';
    }

//    public int read(byte[] buf) {
 //       for (int i = 0; i < buf.length; i++)
  //          buf[i] = 'b';
   //     return buf.length;
    //}

    public static void main(String[] argv) {
        try {
            InputStream input = new Template();
            byte[] data = new byte[10];
            input.read(data);
            System.out.write(data);
            System.out.println();
            System.out.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } 
    }
}

import java.io.*;

public class Flyweight {
    public static void main(String[] argv) {
        Integer a, b;
        
        a = new Integer(1);
        b = new Integer(1);
        if (a == b)
            System.out.println("a == b");
        else
            System.out.println("a != b");

        a = Integer.valueOf(1);
        b = Integer.valueOf(1);
        if (a == b)
            System.out.println("a == b");
        else
            System.out.println("a != b");

        for (int i = 0 ; i < 1000; i++) {
            a = Integer.valueOf(i);
            b = Integer.valueOf(i);
            if (a != b) {
                System.out.println(i);
                break;
            }
        }
    }
}

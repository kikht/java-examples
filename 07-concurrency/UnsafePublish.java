import java.util.concurrent.CountDownLatch;

//TODO: separate singleton from testcase
public class UnsafePublish implements Runnable {

    public static Singleton dummy;
    private static int i;

    public static void main(String[] args) {
        //warmup
        for (i = 0; i < 10000000; i++) {
            dummy = (new UnsafePublish()).getInstance();
        }

        i = 0;
        while (true) {
            UnsafePublish race = new UnsafePublish();
            race.test();
            i++;
        }
    }

    public void test() {
        flag = new CountDownLatch(1);
        for (int i = 0; i < 4; i++) {
            (new Thread(this)).start();
        }
        flag.countDown();
    }

    public void run() {
        try {
            flag.await();
        } catch (InterruptedException ex) { }

        Singleton s = getInstance();
        if (s.value != 42 || s.obj1 == null || s.obj2 == null 
                || s.obj3 == null || s.obj4 == null)
            System.out.println("error! iteration: " + i);
    }

    public static class Singleton {
        public Singleton() {
            value = 42;
            obj1 = new Object();
            obj2 = new Object();
            obj3 = new Object();
            obj4 = new Object();
        }
        
        public int value;
        public Object obj1, obj2, obj3, obj4;
    }

    public Singleton getInstance() {
        if (instance == null) {
            synchronized (this) {
                if (instance == null)
                    instance = new Singleton();
            }
        }
        return instance;
    }

    private Singleton instance = null;
    private CountDownLatch flag = new CountDownLatch(1);
}

public class SyncPublish implements Runnable {

    public static void main(String[] args) {
        SyncPublish race = new SyncPublish();
        race.test();
    }

    public void test() {
        for (int i = 0; i < 100; i++) {
            (new Thread(this)).start();
        }
    }

    public void run() {
        Singleton s = getInstance();
        System.out.println(s.value);
    }

    public static class Singleton {
        public Singleton() {
            value = 42;
        }

        public int value;
    }

    public Singleton getInstance() {
        synchronized (this) {
            if (instance == null)
                instance = new Singleton();
        }
        return instance;
    }

    private Singleton instance = null;

}

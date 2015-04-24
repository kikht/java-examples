public class SyncCounter implements Runnable {

    public static void main(String[] args) {
        SyncCounter race = new SyncCounter();
        race.test();
    }

    public void test() {
        try {
            while (true) {
                counter = 0;
                Thread t1 = new Thread(this);
                Thread t2 = new Thread(this);
                t1.start();
                t2.start();
                t1.join();
                t2.join();
                System.out.println(counter);
            }
        } catch (InterruptedException ex) { }
    }

    public void run() {
        for (int i = 0; i < 1000000; i++) {
            synchronized(this) {
                counter++;
            }
        }
    }

    private int counter;
}

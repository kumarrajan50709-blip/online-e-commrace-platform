
public class MultiUserOrderTest {

    public static void main(String[] args) {

        Thread t1 = new Thread(new OrderTask(1, 2, 101));
        Thread t2 = new Thread(new OrderTask(1, 1, 102));
        Thread t3 = new Thread(new OrderTask(1, 3, 103));

        t1.start();
        t2.start();
        t3.start();
    }
}

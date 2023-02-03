package ga.tianyuge.bean;

/**
 * description
 *
 * @author guoqing.chen01@hand-china.com 2022/11/24 11:22
 */
public class StaticClassA {
    public static int i;
    static {
        i = 1/0;
    }

    public static void A() throws InterruptedException {
        Thread.sleep(2000);
        System.out.println("static method A");
    }

    public static void B() {
        System.out.println("static method B");
        throw new RuntimeException("B");
    }
}

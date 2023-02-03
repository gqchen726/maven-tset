package ga.tianyuge.bean;

/**
 * description
 *
 * @author guoqing.chen01@hand-china.com 2022/11/24 11:22
 */
public class StaticClass {
//    public final static int i = 1/0;
    public final static StaticClassA staticClassA = new StaticClassA();
    static {
        System.out.println("static code block");
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

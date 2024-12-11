package ga.tianyuge.test;

import org.junit.Test;

public class TryCacheTest {

    @Test
    public void forTryCacheTest() {
        int[] a = {1,2,3,4,5};
        for (int i = 0; i < a.length; i++) {
            try {
                if (i == 3) continue;
                System.out.println("try" + a[i]);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                System.out.println("finally" + a[i]);
            }
        }
    }
}

package ga.tianyuge.test;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class SplitTest {
    private static Map<String, String> G6_NUMBER_MAP = new HashMap<>();
    private static String num = "DV182937140";

    static {
        G6_NUMBER_MAP.put("DV", "ADV");
        G6_NUMBER_MAP.put("AY", "PAY");
    }

    @Test
    public void test() {
        System.out.println(num.substring(0, 2));
        System.out.println(G6_NUMBER_MAP.get(num.substring(0, 2))+num.substring(2));
    }
}

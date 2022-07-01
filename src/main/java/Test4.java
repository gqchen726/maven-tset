/**
 * @author: guoqing.chen01@hand-china.com 2022-02-10 16:52
 **/

public class Test4 {
    public static void main(String[] args) {
        String text = "{\"content\" : \"test\"}";
        StringBuilder stringBuilder = new StringBuilder("{\"content\" : \"test\"}");
        String text1 = "{\"content\":\"111\"}";
        System.out.println(text);
        System.out.println(stringBuilder.toString());
        System.out.println(text1);
    }
}

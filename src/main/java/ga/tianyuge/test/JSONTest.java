package ga.tianyuge.test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.io.*;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * description
 *
 * @author guoqing.chen01@hand-china.com 2023/01/05 9:28
 */
public class JSONTest {

    @Test
    public void test1() throws IOException {
        File fileOfIn = new File("C:\\Users\\GuoqingChen01\\Desktop\\test-in.txt");
//        File fileOfOut = new File("C:\\Users\\GuoqingChen01\\Desktop\\test-out.txt");
        FileReader fileReader = new FileReader(fileOfIn);
//        FileWriter fileWriter = new FileWriter(fileOfOut);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
//        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
//        StringBuilder stringBuilder = new StringBuilder();
//        Set<String> stringSet = new HashSet<>();
        while (true) {
            String s = bufferedReader.readLine();
            if (StringUtils.isBlank(s)) {
                break;
            }
//            s = s.trim();
            JSONArray objects = JSONObject.parseArray(s);
            objects.forEach(t -> {
                JSONObject body = JSONObject.parseObject(JSONObject.toJSONString(t));
                Object suppliers_name = body.get("SUPPLIERS_NAME");
                System.out.println(suppliers_name);
            });
        }
    }
}

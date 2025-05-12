package ga.tianyuge.test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.io.*;
import java.util.List;

/**
 * description
 *
 * @author guoqing.chen01@hand-china.com 2023/01/05 10:17
 */
public class JSONMappingTest {

    @Test
    public void test1() throws IOException {
        File fileOfIn = new File("C:\\Users\\GuoqingChen01\\Desktop\\test-in.txt");
        File fileOfOut = new File("C:\\Users\\GuoqingChen01\\Desktop\\test-out.txt");
        FileReader fileReader = new FileReader(fileOfIn);
        FileWriter fileWriter = new FileWriter(fileOfOut);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        StringBuilder stringBuilder = new StringBuilder();
//        Set<String> stringSet = new HashSet<>();
        while (true) {
            String s = bufferedReader.readLine();
            if (StringUtils.isBlank(s)) {
                break;
            }
//            s = s.trim();
            List<Module> modules = ObjectMapper.findModules();
            for (Module module : modules) {
                String moduleName = module.getModuleName();
                System.out.println(moduleName);
            }
        }
    }
}

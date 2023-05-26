package ga.tianyuge.test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import ga.tianyuge.bean.Employee;
import ga.tianyuge.dto.Word2PdfDTO;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.entity.StringEntity;
import org.junit.Test;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

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

    @Test
    public void test2() {
        String str = "SHELL CATALYSTS & TECHNOLOGIES PTE.LTD";
        str = str.replace("&", "&amp;");
        str = str.replace("&", "&amp;");
        str = str.replace("&amp;","&");
        Employee employee = new Employee();
        employee.setName(str);
        String s = JSONObject.toJSONString(employee);
        System.out.println(s);
    }

    @Test
    public void toJsonStringTest() throws UnsupportedEncodingException {
        Word2PdfDTO word2PdfDTO = new Word2PdfDTO();
        word2PdfDTO.setKey(UUID.randomUUID().toString());
        word2PdfDTO.setUrl("http://scptest.shenghongpec.com:9000/dev-private-bucket/purchase-contract/3/5054b131b15447698f6ecc0abb72cf56@测试.docx");
        word2PdfDTO.setTitle("CON52002023001143.docx");
        System.out.println(JSONObject.toJSONString(word2PdfDTO));
        StringEntity stringEntity = new StringEntity(JSONObject.toJSONString(word2PdfDTO), StandardCharsets.UTF_8);
        stringEntity.setContentType("application/json");
        System.out.println(stringEntity);
    }

    public StringEntity getJsonStringTest() throws UnsupportedEncodingException {
        Word2PdfDTO word2PdfDTO = new Word2PdfDTO();
        word2PdfDTO.setKey(UUID.randomUUID().toString());
        word2PdfDTO.setUrl("http://scptest.shenghongpec.com:9000/dev-private-bucket/purchase-contract/3/5054b131b15447698f6ecc0abb72cf56@测试.docx");
        word2PdfDTO.setTitle("CON52002023001143.docx");
        System.out.println(JSONObject.toJSONString(word2PdfDTO));
        StringEntity stringEntity = new StringEntity(JSONObject.toJSONString(word2PdfDTO), StandardCharsets.UTF_8);
        stringEntity.setContentType("application/json");
        System.out.println(stringEntity);
        return stringEntity;
    }
}

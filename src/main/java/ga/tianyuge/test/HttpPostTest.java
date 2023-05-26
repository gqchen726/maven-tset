package ga.tianyuge.test;

import com.alibaba.fastjson.JSONObject;
import ga.tianyuge.dto.Word2PdfDTO;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.util.UUID;

/**
 * @Description: description
 * @author: GuoqingChen
 * @Time: 2023/05/26 18:00
 * @Email: guoqing.chen01@hand-china.com
 */
public class HttpPostTest {
    @Test
    public void test1() throws Exception {
        Word2PdfDTO word2PdfDTO = new Word2PdfDTO();
        word2PdfDTO.setKey(UUID.randomUUID().toString());
        word2PdfDTO.setUrl("http://scptest.shenghongpec.com:9000/dev-private-bucket/purchase-contract/3/5054b131b15447698f6ecc0abb72cf56@测试.docx");
        word2PdfDTO.setTitle("CON52002023001143.docx");
        System.out.println(JSONObject.toJSONString(word2PdfDTO));
        this.sendHttpPost("http://10.10.101.71:8000/ConvertService.ashx", JSONObject.toJSONString(word2PdfDTO));
    }

    public String sendHttpPost(String url, String body) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Content-Type", "application/json");
        httpPost.setEntity(new StringEntity(body));
        CloseableHttpResponse response = httpClient.execute(httpPost);
        System.out.println(response.getStatusLine().getStatusCode() + "\n");
        HttpEntity entity = response.getEntity();
        String responseContent = EntityUtils.toString(entity, "UTF-8");
        System.out.println(responseContent);
        response.close();
        httpClient.close();
        return responseContent;
    }
}

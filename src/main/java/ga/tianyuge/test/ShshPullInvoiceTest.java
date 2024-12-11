package ga.tianyuge.test;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.springframework.util.Base64Utils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class ShshPullInvoiceTest {


    @Test
    public void test() {
        String interface_code = "INCOME.INVALLCHECK";
        String appId = "9o2g3l8k673u";
        String appSecret = "b9ce68de085440fabf81d668cc6ca823";
        String nsrsbh = "913207005668923863";
        Map<String, Object> data = new HashMap<>();
        data.put("nsrsbh", nsrsbh);
        data.put("fpdm", "");
        data.put("fphm", "24922000000026871733");
        data.put("kprq", "2024-05-30");
        data.put("sjlx", "1");
        String datastr = JSON.toJSONString(data);
        String data_b64str = Base64Utils.encodeToString(datastr.getBytes(StandardCharsets.UTF_8));
        String rdata = "{"+"\"global_info\":{"+"\"application_code\":\""+interface_code+"\","+"\"interface_code\":\""+interface_code+"\","+"\"version\":\"V1.0\","+"\"app_abbr\":\"\","+"\"app_id\":\""+appId+"\","+"\"data_id\":\"\""+"},"+"\"data\":\""+data_b64str+"\","+"\"data_sign\":\"\""+"}";
        byte[] byteKey = appSecret.getBytes(StandardCharsets.UTF_8);
        SecretKeySpec secretKeySpec = new SecretKeySpec(byteKey,
                "HmacSHA512");
        byte[] byteData = null;
        try {
            Mac mac = Mac.getInstance("HmacSHA512");
            mac.init(secretKeySpec);
            byteData = mac.doFinal(rdata.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
        }
        String dataSign = Base64Utils.encodeToString(byteData);
        String pdata = rdata.replaceFirst("\"data_sign\":\"\"", "\"data_sign\":\"" + dataSign + "\"");
        System.out.println(pdata);
    }
}

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.*;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 1.通过curl请求构造代码
 * 2.cookie如何自动获取替换
 * 3.获取返回的json数据进行解析
 * 4.存库、获取文件-返回解析后可使用的邮件地址
 * 5.触发邮件发送
 *
 * @author chunlin.qi@hand-china.com
 * @version 1.0
 * @description
 * @date 2021/12/27
 */
@Slf4j
public class HandResultProTest {


    public static void main(String[] args) {

        try {
            //请求地址
            String loginUrl = "https://login.hand-china.com/sso/login";
            //模拟登录
            Map<String, String> resulteLoginInfoMap = simulationResultLogin(loginUrl);

            //openDocument.faces

            //开始获取数据
            String webiDHTMLUrl = "http://bo.hand-china.com:8080/BOE/OpenDocument/2005112237/AnalyticalReporting/webiDHTML/viewer/report.jsp?iViewerID=1&sEntry=we000100004d69ae79aa96&iReport=0&iReportID=10&sPageMode=QuickDisplay&sReportMode=Viewing&iPage=1&zoom=100&isInteractive=false&isStructure=false&appKind=OpenDocument&sSetPrompts=true&sUndoEnabled=false";
            //模拟登录
            queryResultDataAndTransferHtml(webiDHTMLUrl, resulteLoginInfoMap.get("JSESSIONID"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询业绩系统数据并转化为html文件
     *
     * @param resultSystemUrl
     * @param JSESSIONID
     * @return
     * @throws Exception
     */
    private static void queryResultDataAndTransferHtml(String resultSystemUrl, String JSESSIONID) throws Exception {

        CloseableHttpClient httpClient2 = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(resultSystemUrl);
        httpGet.setHeader("Connection", "keep-alive");
        httpGet.setHeader("Upgrade-Insecure-Requests", "1");
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/97.0.4692.99 Safari/537.36");
        httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        httpGet.setHeader("Referer", "http://bo.hand-china.com:8080/BOE/OpenDocument/2005112237/AnalyticalReporting/webiDHTML/viewer/processDocWithPrompts.jsp?iViewerID=1&sEntry=we00000000fc4179ce952a&iReport=0&iReportID=10&sPageMode=QuickDisplay&sReportMode=Viewing&iPage=1&zoom=100&isInteractive=false&isStructure=false&appKind=OpenDocument");
        httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.9");
        httpGet.setHeader("Cookie", "JSESSIONID=" + JSESSIONID + "; GUEST_LANGUAGE_ID=zh_CN");
        CloseableHttpResponse response = httpClient2.execute(httpGet);

        HttpEntity entity = response.getEntity();
        if (entity != null) {
            String content = EntityUtils.toString(entity, "utf-8");
            log.info("查询接口：{}", content);

            //写成html
            File file = new File("D:/test.html");
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(content.getBytes());
            fos.flush();
            fos.close();

            String regex1 = "<td.*?>(.*?)</td>";
            Pattern pattern = Pattern.compile(regex1);
            Matcher matcher = pattern.matcher(content);
            //声明一个list对象
            List<String> list = new ArrayList<>();
            int count = 0;
            while (matcher.find()) {
                list.add(matcher.group(1));
            }
            log.info("提取数据结果：{}", JSONObject.toJSONString(list));

            EntityUtils.consume(entity);
            response.close();


        }

    }


    /**
     * 模拟新业绩系统登陆
     *
     * @param loginUrl
     * @return
     * @throws Exception
     */
    private static Map<String, String> simulationResultLogin(String loginUrl) throws Exception {
        //登陆信息结果集
        Map<String, String> loginInfoMap = new HashMap<>(5);

        // 1、创建一个httpClient客户端对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 2、创建一个HttpPost请求
        HttpPost httpPost = new HttpPost(loginUrl);
        httpPost.setHeader("Connection", "keep-alive");
        httpPost.setHeader("Cache-Control", "max-age=0");
        httpPost.setHeader("sec-ch-ua", "\" Not A;Brand\";v=\"99\", \"Chromium\";v=\"96\", \"Google Chrome\";v=\"96\"");
        httpPost.setHeader("sec-ch-ua-mobile", "?0");
        httpPost.setHeader("sec-ch-ua-platform", "\"Windows\"");
        httpPost.setHeader("Upgrade-Insecure-Requests", "1");
        httpPost.setHeader("Origin", "https://login.hand-china.com");
        // 设置传输的数据格式
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
        httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.110 Safari/537.36");
        //设置请求头
        httpPost.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        httpPost.setHeader("Sec-Fetch-Site", "same-origin");
        httpPost.setHeader("Sec-Fetch-Mode", "navigate");
        httpPost.setHeader("Sec-Fetch-User", "?1");
        httpPost.setHeader("Sec-Fetch-Dest", "document");
        httpPost.setHeader("Referer", "https://login.hand-china.com/sso/login?service=http://bi.hand-china.com/core/");
        httpPost.setHeader("Accept-Language", "zh-CN,zh;q=0.9");

        //登陆信息
        String reqData = "username=14225&password=Hand2022&execution=332fc23a-5b81-4d7b-bc81-38df2ba164f2_ZXlKaGJHY2lPaUpJVXpVeE1pSjkuZ0xSeCs5N0tvaWppU3hJL2hreGZ3NUtZb2Q4QXFqWkcvcWFzTVllQjRlNU5QVjYzaGtnNUIvWUVrS2hhT3pSVXkyYkR5RmNyNkp0NXBidU1JSFNibGg4UXBqVWI3L3BMajdRV0lGN2ZrNGRncm5tc0l6TGhGLzBCbHkrSCthV0VXRmpmWEFhdC9tRElGbmROTUNLdWhITmxDd1M5aGk1ek8zbjlTeXYyUFZXbnVidmZFeXZSa1hFU1NoQTdJWWFFaldaVDliVGlWUXUzVmpzK1ZGV0JZSXp6T3ZnbnNWN2oxS0ZSZmsyajNUUXR2YmZldlgyMGw5YW9qUXQwM0xTR0Y1WVNwMVh6SU5nZEQ0aTFOUjdJM3YzTDN3YXFDZG9CNWlzcTZHM1o0ZWFYVDhLNnM3MXg0VVVmeC9aSFZCV0hHUmVRbjJwcFYzWTg1VFM4S0l3K0RQbDl3SVlYMnI5WG8wTWd5aGJkWWoxWGZoTHhSTzl1R1NxU2tNNmppU0FpbGVJcE1jSWYxZTlYNitPRmpiOTRtM01qSmJQN3JtYXBXRHp2SkM5d0ZyZkRtZjI5YU9VK2ZXS0lmeW1CRm1IUkhzMzMxVkxpZS9NRm0yTmFqUnVHL1JVVTltY3pPTG12ZVYxb3FjdHZUNUttbXBsRUpvZHdDSXpTTkl5amJxaFNWbnNHdmx2dklnSDNtTTluMzFDOERxMkJEOFRqU0F5L2R3a1hxSUhyamJ3OFp3WU5Db1JNN2U1UnRZUjl3aCtYMnJUMUUwMWRib1FQWkVZdnZJeHFsOWRQTFBvWjZVUWluMkVxb3hDbWRFNXh1RFEyaVdoZk0ydmtFckVseHQ1ZU05RFBPNjFtakMvajI2aCsxM1ZnNmFKQlNITkFvQnkrUno4VjdSMjhPOU9jYVZoRGhjazlNNk45WC9mSTRoUU9pamxaTlFvRjlyQlBob1RYK1luekVMWXdNM05NaktrdWpxUk93SlBiSi9JMHI5S1p0cGpQbCtOTGVqaExZWVZzbDRvWnNFNzZOT3NuZXFhYXZVU2ozeGdDbkt2OXRpZU5EK0svSzZlZ05lWWd6YllhNG5BcjdjZVBiOWNTZU1KQ01BZEgxOERYb1NPanZYbzVESnpzUkVoZ2N0VUZRSWhFR2RIcnJFZ2VKaVc4VnJObHNWZFRvL2tYSFAyUjRCZVRmSTFiWUVOZitqRWhjUEtWV1d5OStmdmhpVitLb3F2eU13SDNvUDNUL1J4dVcxc1NSZGxnN0tFbFpPT0lZRDVmbktaZTUrak5PR2lJdGRvMDRMQUNPOHhQV0owOUlQUWpkbEFNT1h1MURqMEdvOG9kTDlQMkFuUkt6QWpIS2RLZDlqdVM3aUxvVVJFTlA3dzZadEVLTzRCaFhtYmh2L1FJelBRL0pRRnk2SllMajFDYjZ3S1lhdVZ6SUorY2FUQVJYSE5sSk5FOUpSS0kyWThWclFNc0lFckVPUUtLZEdPbVNYZ2p5THB5cTVpM0xLbTJPNHhtazYyTlhuRFZNNFJydm1QSnJ4b1ZGY2hlcEZGcFRBRzJ6eTZsY3g5ekdMamZpdndsR0kyODNDdG40S2tzWlUwZHFSNDFCUkVYc2lPMGdpTWtSVEFWUG9ZYWJYNnRCYURaZk0zQ2Z6ekZjc3pUUFhnMDFYTENFWHNTd0I0a01ST3VFK1dUK3ppbVhZazJrempXZjVWUVVFVEhlQkRJRDJ6WkJQaExETkkyVnUyRGhpcTY0QkZ3alRnaUVuYkZuWE44bFdhdXZzS3NvYWtPV1k4RkxMbGp0TXVrS3k3WS9mbGtnY0c0dTFPK245eExKVS9PeHRSZnE3MDhKa2JWQ052QlY3UUs2M0FyYmUvNlpSR1BVREJzdU1pWUR6N2FGMCs2Sklzb1IzU2x1NXljWFltYThReTd5THIrbWNES01IQnBlblFLWWV1cngwb1lmbVRhOG0zMGNsMk82ODNCQlQrK2FLMzE2VVlSODJmaUhxYkVyK2gvVEZvSC9CKzhwcmNLaytMUFM0c0x2RW5KZS9EZ0hEU2NJTDhaaEZrZzNzS01IdExPZHZDbURHeFhNR3ZrUTVOSzBDVTMybjZlYkp5SHYycEVVRDBXRjQrK1ltY0gwcnEyMjlYZGY0YWk4am1nN1NjQlYvemdiQmMreFpkV2JFY29TZUdSVnFCTWpFcTVialN2TE44NitKV3FYbmZPNnZvOVE4aXY2S2dFNmxqTk9BenREZWtHbFdaSGowdWU2M0p1K0tLTFBWN1dZd1R5dW5oTW95NzgxQnVabC9MQThWUXNUd0l0WFhucnhxVGtCcnpiU2pUcDhIOS9aUFhnYjdaMUdidjJsRkhobUhPNDEyUG1mSXFoMEhpMFhuZllhdDcrbXVjdXdOMXk3WGhmSU5sRFJpRisyNEFtbHlxblUvSkZWRU8yK05nM2ZsRGFXWHpDRGdib3ppSDk5RzhCR1FZZGMxTDVaY2piZ0JZK3F0T3V6c2c5bDRleUFycjVJQTgva2V2VGZaelQrdnp2NzdKbjFIYkFOZ2RMQks0Sy9FVE4reldWTkZYU0hSUTNRQytiazVOVk9KOWpuWVhDdkZaRTl2Tk90S1g3Rllhck10ZzhGR0hPbGhiVlI0eXljNUpJTzByaWhCbkJIbjJHWVE3N1JqUnNoTFUvTjVnVkFGdXB5M3o0QTBxZkRuNEpOT0dneEduTC9EK3RrRzEyMEpzeTdZY2hzclBCSG44RnowRkhqckxhU1dBM3BwYWM1eW55azVvYWtyNS92QWhrME93QTNPV3o3MlI3dXpTMG1vMW9vczRGTVlydGFDaXdLSHFVcTgwYjBUWGpyR2orVFlOc0lhNjFWc09aS1h2MngyNjNWVE1lYzFSTjBiZ0VDbTQwMTBZTnFSKzJXTE8vRUZsM2hhTEtuMWhBUDBqTnFWZ0Y0SW1OaWZjT1NydTN2MFI5KzMwSHlDcnJ1L0JHUmdQMnQ0NnFHT0E2eHZ4akI3cFJzTzdqRGppU2hFQUNRWUhuTTdWRWxENGlsdXZYa3pCMk1ENDc5elFvOFZsSU1kSERzL2l4c1RKRVNVWkVVeHB4WHRPOUtMaFZxUjFyWFUrbUpkbnRkVlFVcHVsNWh5UjF6WlpzUnZLbUkraUhQbUNOUEx3OENCTllKSmtnK1lWNW1DdmRlTDlnN2I1Witua2l2SEV5VmladW9CNUFRWWh1NmdVTUplRitOSmYzTUFSdTgxQ1BPeTZQWjdyTDNKVnRvbktoeFRTbEhXSTlQL253TnMvYW5JUDNkQkdsaXVXSjFLVTlGcmxlQ0NJT0w1R1lIbjE0Y2FtVGo5aGx0d3dVcG5tREFVaXg2RnFCQUtSN1I0RHF5ZVRmRTNGZ3B6Mm92OUFHTWFZMmw0bGNlcGFwSElJNmFkUDFMV1pZUlR1bG15bDJCTjBGTHE3MWFjc1E5TGZST0JlRTNmeE5UckNoRE0wVExEcmhNNkdBTS9yLzlQY3BCRnpDTGI2S2JuQXhBVS96TXUyVjhCWnpCSWRpd0FlVnZsRVhSNWw3MG5ON3NqN3ZrWVZtdVlBUUFUYVpjczVIMVZCazNPTDRrREJOYnF2MzZUZWNKaVgyOXJBSTFQZ2xKVGhmL1JGTHJSSm1uL1RZaWxrZ2doWi83UEhhWWdmTTNGK2M3cDJEWUh1MVd2R0E1cFN3Y0JIL1E3UkUwREhnR2JJMVhxL2V6QmRLNzFmWStrWmVjbk5tditXZ0V6ZHp4T3JaS1N6c2ZITWVkaXpsZ1ZUenBXOUdlMUlMZmdkT3FnQnY1RDhnUGJnM0VFT1JnajNsaWI1bGQ2b1o0YW1QbEp1d2FyZDdIR2VYS2RvT2JPQTR5T3ZENW5ENlFmaGNDaUZMMGFvZmk0ZTBjVDRicEhERFFPVzExdGxXQ3ZoZWlKak1XWmYrQTcrWkRaVDZWd0xwMWszMnZ5MGZ4L1ArM0RIQUZYdUpMNTJBak5Obk1sVTQ1WTgvQWFtYTliWDMrQUtxbTZ6UUdSZC9qbkxWVGY4UEtuei82SWJLcWQxanI1MjdRbjk5K0YwcE9xdGNIc3lwQnFnT0wvcmtreW1KVmhvRTZocEZOZz09Lk5RU0hDQy1fcjlaTW1VYVV2M1hfM3h1MjFKeGNYSXhsNW5wRWVFbWxSWDlOc1hfbFNCc3VmVVRMOThBNWxuOFBjMWdtTUNncF8yNndXNWN1Q1ZfWHpB&_eventId=submit&geolocation=&submit=%E7%99%BB%E5%BD%95";
        //放参数进post请求里面 raw类型
        httpPost.setEntity(new StringEntity(reqData));

        // 7、执行post请求操作，并拿到结果
        CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
        int statusCode = httpResponse.getStatusLine().getStatusCode();

        //获取返回码中头部中location重定向的地址
        String redirectUrl = httpResponse.getHeaders("Location")[0].getValue();
        //第一次重定向后的 ticket=ST-2758788-L7zgbniWJ1gDqZtXFgmm7RzTiVg192-168-16-100
        String ticket = redirectUrl.split("\\?")[1];
        log.info("login登录新业绩系统接口获取的ticket：{}", ticket);
        log.info("login登录新业绩系统接口当前状态码：{},第一次获取重定向地址：{}", statusCode, redirectUrl);

        String cookieTGCValue = httpResponse.getHeaders("Set-Cookie")[0].getValue();
        log.info("login登录新业绩系统接口获取的Set-Cookie-TGC：{}", cookieTGCValue);
        cookieTGCValue = cookieTGCValue.split(";")[0];

        //2.执行第一次重定向
        CookieStore cookieStore = new BasicCookieStore();
        CloseableHttpClient httpClient2 = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
        HttpGet httpGet = new HttpGet(redirectUrl);
        httpGet.setHeader("Connection", "keep-alive");
        httpGet.setHeader("Cache-Control", "max-age=0");
        httpGet.setHeader("Upgrade-Insecure-Requests", "1");
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.110 Safari/537.36");
        httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8,en-US;q=0.7");
        httpGet.setHeader("Cookie", "GUEST_LANGUAGE_ID=zh_CN; Hm_lvt_afc5bec53effc77c4ec7e2a702b8f1f4=1625485460; _ga=GA1.2.374340343.1632619247; _ga_QFWN0KYHTY=GS1.1.1632619246.1.1.1632619323.0");
        CloseableHttpResponse response = httpClient2.execute(httpGet);

        HttpEntity entity = response.getEntity();

        //全局参数SESSIONID_HAP
        String SESSIONID_HAP = "";
        if (entity != null) {
            String result = EntityUtils.toString(entity, "utf-8");

            // 获得重定向后的 Cookie
            List<Cookie> cookies = cookieStore.getCookies();
            for (int i = 0; i < cookies.size(); i++) {
                if ("SESSIONID_HAP".equals(cookies.get(i).getName())) {
                    SESSIONID_HAP = cookies.get(i).getValue();
                }
            }
        }
        EntityUtils.consume(entity);
        response.close();

        log.info("authorize的SESSIONID_HAP：{}", SESSIONID_HAP);

        //3.执行第二次
        CloseableHttpClient httpClient3 = HttpClients.createDefault();
        String redirectUrl2 = "https://login.hand-china.com/sso/login?service=http%3A%2F%2Fbi.hand-china.com%2Fcore%2Flogin%2Fcas";
        log.info("第二次请求：{}", redirectUrl2);
        HttpGet httpGet2 = new HttpGet(redirectUrl2);
        httpGet2.setConfig(createConfig(5000, false));
        httpGet2.setHeader("Connection", "keep-alive");
        httpGet2.setHeader("Cache-Control", "max-age=0");
        httpGet2.setHeader("Upgrade-Insecure-Requests", "1");
        httpGet2.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.110 Safari/537.36");
        httpGet2.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        httpGet2.setHeader("Sec-Fetch-Site", "same-site");
        httpGet2.setHeader("Sec-Fetch-Mode", "navigate");
        httpGet2.setHeader("Sec-Fetch-User", "?1");
        httpGet2.setHeader("Sec-Fetch-Dest", "document");
        httpGet2.setHeader("sec-ch-ua", "\" Not A;Brand\";v=\"99\", \"Chromium\";v=\"96\", \"Google Chrome\";v=\"96\"");
        httpGet2.setHeader("sec-ch-ua-mobile", "?0");
        httpGet2.setHeader("sec-ch-ua-platform", "\"Windows\"");
        httpGet2.setHeader("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8,en-US;q=0.7");
        httpGet2.setHeader("Cookie", cookieTGCValue + "; GUEST_LANGUAGE_ID=zh_CN; Hm_lvt_afc5bec53effc77c4ec7e2a702b8f1f4=1625485460; _ga=GA1.2.374340343.1632619247; _ga_QFWN0KYHTY=GS1.1.1632619246.1.1.1632619323.0");
        CloseableHttpResponse httpResponse3 = httpClient3.execute(httpGet2);

        HttpEntity entity2 = httpResponse3.getEntity();
        String redirectUrl3 = httpResponse3.getHeaders("Location")[0].getValue();

        EntityUtils.consume(entity2);
        httpResponse3.close();
        log.info("redirectUrl3:{}", redirectUrl3);


        //3.执行第三次
        CloseableHttpClient httpClient4 = HttpClients.createDefault();
        HttpGet httpGet3 = new HttpGet(redirectUrl3);
        httpGet3.setConfig(createConfig(5000, false));
        httpGet3.setHeader("Connection", "keep-alive");
        httpGet3.setHeader("Cache-Control", "max-age=0");
        httpGet3.setHeader("Upgrade-Insecure-Requests", "1");
        httpGet3.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.110 Safari/537.36");
        httpGet3.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        httpGet3.setHeader("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8,en-US;q=0.7");
        httpGet3.setHeader("Cookie", "SESSIONID_HAP=" + SESSIONID_HAP + "; GUEST_LANGUAGE_ID=zh_CN; Hm_lvt_afc5bec53effc77c4ec7e2a702b8f1f4=1625485460; _ga=GA1.2.374340343.1632619247; _ga_QFWN0KYHTY=GS1.1.1632619246.1.1.1632619323.0");
        CloseableHttpResponse httpResponse4 = httpClient4.execute(httpGet3);

        HttpEntity entity3 = httpResponse4.getEntity();
        String redirectUrl4 = httpResponse4.getHeaders("Location")[0].getValue();
        EntityUtils.consume(entity3);
        httpResponse4.close();

        log.info("redirectUrl4:{}", redirectUrl4);

        CloseableHttpClient httpClient5 = HttpClients.createDefault();
        HttpGet httpGet4 = new HttpGet(redirectUrl4);
        httpGet4.setConfig(createConfig(5000, false));
        httpGet4.setHeader("Connection", "keep-alive");
        httpGet4.setHeader("Cache-Control", "max-age=0");
        httpGet4.setHeader("Upgrade-Insecure-Requests", "1");
        httpGet4.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.110 Safari/537.36");
        httpGet4.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        httpGet4.setHeader("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8,en-US;q=0.7");
        httpGet4.setHeader("Cookie", "SESSIONID_HAP=" + SESSIONID_HAP + "; GUEST_LANGUAGE_ID=zh_CN; Hm_lvt_afc5bec53effc77c4ec7e2a702b8f1f4=1625485460; _ga=GA1.2.374340343.1632619247; _ga_QFWN0KYHTY=GS1.1.1632619246.1.1.1632619323.0");
        CloseableHttpResponse httpResponse5 = httpClient5.execute(httpGet4);

        HttpEntity entity4 = httpResponse5.getEntity();
        if (entity4 != null) {
            String result4 = EntityUtils.toString(entity4, "utf-8");
            if (result4.contains("//推出BO账号")) {
                log.info("登陆成功....");
            }
            log.info("最终结果result4：{}", result4);
        }
        //登陆完成
        loginInfoMap.put("SESSIONID_HAP", SESSIONID_HAP);

        //3.执行请求
        HttpContext ctx = new BasicHttpContext();
        CloseableHttpClient httpClient6 = HttpClients.createDefault();
        String userHandelLogUrl = "http://bi.hand-china.com/core/hdm/changeDataLog/userHandelLog?url=http://bi.hand-china.com/core/home.html?theBoReportFormsCode=hom";
        HttpGet httpGet5 = new HttpGet(userHandelLogUrl);
        httpGet5.setHeader("Connection", "keep-alive");
        httpGet5.setHeader("Accept", "application/json, text/javascript, */*; q=0.01");
        httpGet5.setHeader("X-Requested-With", "XMLHttpRequest");
        httpGet5.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/97.0.4692.99 Safari/537.36");
        httpGet5.setHeader("Content-Type", "application/json");
        httpGet5.setHeader("Referer", "http://bi.hand-china.com/core/home.html?theBoReportFormsCode=home");
        httpGet5.setHeader("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8,en-US;q=0.7");
        httpGet5.setHeader("Cookie", "SESSIONID_HAP=" + SESSIONID_HAP + "; GUEST_LANGUAGE_ID=zh_CN; Hm_lvt_afc5bec53effc77c4ec7e2a702b8f1f4=1625485460; _ga=GA1.2.374340343.1632619247; _ga_QFWN0KYHTY=GS1.1.1632619246.1.1.1632619323.0");
        CloseableHttpResponse httpResponse2 = httpClient6.execute(httpGet5, ctx);

        HttpEntity entity5 = httpResponse2.getEntity();
        String result5 = "";
        if (entity5 != null) {
            result5 = EntityUtils.toString(entity5, "utf-8");

            log.info("result5:{}", result5);
        }


        //获取请求的BI链接
        CloseableHttpClient httpClient7 = HttpClients.createDefault();
        String queryForRecoverBoTokenUrl = "http://bi.hand-china.com/core/portal/SysProperties/queryForRecoverBoToken";
        HttpGet httpGet6 = new HttpGet(queryForRecoverBoTokenUrl);
        httpGet6.setHeader("Connection", "keep-alive");
        httpGet6.setHeader("Accept", "application/json, text/javascript, */*; q=0.01");
        httpGet6.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/97.0.4692.99 Safari/537.36");
        httpGet6.setHeader("X-Requested-With", "XMLHttpRequest");
        //最后一个重定向地址
        httpGet6.setHeader("Referer", redirectUrl4);
        httpGet6.setHeader("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8,en-US;q=0.7");
        httpGet6.setHeader("Cookie", "SESSIONID_HAP=" + SESSIONID_HAP + "; GUEST_LANGUAGE_ID=zh_CN; Hm_lvt_afc5bec53effc77c4ec7e2a702b8f1f4=1625485460; _ga=GA1.2.374340343.1632619247; _ga_QFWN0KYHTY=GS1.1.1632619246.1.1.1632619323.0");
        CloseableHttpResponse httpResponse6 = httpClient7.execute(httpGet6);

        HttpEntity entity6 = httpResponse6.getEntity();

        //{"message":"http://bo.hand-china.com:8080/BOE/OpenDocument/opendoc/openDocument.jsp?sType=win&sIDType=CUID&iDocID=Acg3FdkbpJRJomfo6efeQt4&token=bodev001.hand-china.com:6400@1359600J2RaoWLKFLt5uxJNPmOCej9wpKj7e5e6H1359598JO7uOj0VKYcnb2AN4P8S2JSj1qHKNx2dP","success":true}
        String openDocumentUrl = "";
        if (entity6 != null) {
            String result6 = EntityUtils.toString(entity6, "utf-8");

            log.info("result6:{}", result6);

            openDocumentUrl = (String) JSONObject.parseObject(result6).get("message");
        }


        SimpleDateFormat curDateSdf = new SimpleDateFormat("yyyyMMdd");
        String queryParamDate = curDateSdf.format(new Date());
        String finalOpenDocumentUrl = openDocumentUrl + "&lsSIP_USERID=14225&lsSIP_YMONTH_S=" + queryParamDate + "&lsSIP_YMONTH_E=" + queryParamDate + "&lsSEMPLOYEE_CODE=null&lsSIP_BMBM=null&lsSPROJECT_CODE=null&lsSDISTANCE=-1&lsSIP_ZXMLX=null";
        log.info("openDocumentUrl：{}，finalOpenDocumentUrl：{}", openDocumentUrl, finalOpenDocumentUrl);


        //打开文档接口获取JSESSIONID
        CookieStore cookieStore2 = new BasicCookieStore();
        CloseableHttpClient httpClient8 = HttpClients.custom().setDefaultCookieStore(cookieStore2).build();
        HttpGet httpGet7 = new HttpGet(finalOpenDocumentUrl);
        httpGet7.setHeader("Connection", "keep-alive");
        httpGet7.setHeader("Upgrade-Insecure-Requests", "1");
        httpGet7.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/97.0.4692.99 Safari/537.36");
        httpGet7.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        httpGet7.setHeader("Referer", "http://bi.hand-china.com/");
        httpGet7.setHeader("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8,en-US;q=0.7");
        httpGet7.setHeader("Cookie", "GUEST_LANGUAGE_ID=zh_CN; Hm_lvt_afc5bec53effc77c4ec7e2a702b8f1f4=1625485460; _ga=GA1.2.374340343.1632619247; _ga_QFWN0KYHTY=GS1.1.1632619246.1.1.1632619323.0");
        CloseableHttpResponse httpResponse7 = httpClient8.execute(httpGet7);
        HttpEntity entity7 = httpResponse7.getEntity();
        //全局参数JSESSIONID
        String JSESSIONID = "";
        if (entity != null) {
            String result3 = EntityUtils.toString(entity7, "utf-8");

            // 获得重定向后的 Cookie
            List<Cookie> cookies = cookieStore2.getCookies();
            for (int i = 0; i < cookies.size(); i++) {
                if ("JSESSIONID".equals(cookies.get(i).getName())) {
                    JSESSIONID = cookies.get(i).getValue();
                }
            }
        }
        log.info("JSESSIONID:{}", JSESSIONID);
        loginInfoMap.put("JSESSIONID", JSESSIONID);
        loginInfoMap.put("openDocumentUrl", finalOpenDocumentUrl);
        return loginInfoMap;

    }


    private static RequestConfig createConfig(int timeout, boolean redirectsEnabled) {
        return RequestConfig.custom()
                .setSocketTimeout(timeout)
                .setConnectTimeout(timeout)
                .setConnectionRequestTimeout(timeout)
                .setRedirectsEnabled(redirectsEnabled)
                .build();
    }

}

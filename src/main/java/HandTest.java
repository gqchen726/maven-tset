
import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

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
public class HandTest {


    public static void main(String[] args) {

        try {
            //模拟登录
            //请求地址
            String loginUrl = "https://login.hand-china.com/sso/login";
            String jSessionId = simulationLogin(loginUrl);
            /*log.info("jSessionId:{}", jSessionId);

            Map<String, Object> excelMap = readExcel("D://工号手机.xlsx");
            log.info("11读取到数据:{}", JSONObject.toJSONString(excelMap));

            Map<String, Object> absentMap = new HashMap<>(1);
            absentMap.put("22486", "齐春林");
            Set<String> keySet = absentMap.keySet();

            //通过工号获取手机号
            String mobileListStr = translatePhoneByCode(excelMap, keySet);
            log.info("转化后：{}", mobileListStr);
            if (StringUtils.isEmpty(mobileListStr)) {
                log.info("为空。。。。。");
            }
            sendEnterpriseWeChat(mobileListStr);

            int i = 10 / 0;


            //查询没填TS的人员数据
            String url = "http://app.hand-china.com/hrms/modules/hr/prj/PRJ106/PRP_ResourceQuery.svc?pagesize=10&pagenum=1&_fetchall=false&_autocount=true";
            String curDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

            TsInfoVo tsInfoVo = (TsInfoVo) sendPostMethodForTS(url, curDate);

            //员工工号和姓名
            Map<Object, Object> resultMap = new HashMap<>(16);
            if (tsInfoVo != null) {
                List<Map> absentDetailList = tsInfoVo.getAbsentDetailList();
                if (!CollectionUtils.isEmpty(absentDetailList) && absentDetailList.size() > 0) {
                    absentDetailList.forEach(l -> {
                        resultMap.put(l.get("employee_code"), l.get("name"));
                    });
                }
            } else {
                log.error("暂无数据......");
                resultMap.put("暂无数据", null);
            }
            log.info("处理后的resultMap数据：{}", JSONObject.toJSONString(resultMap));

            //TODO 根据员工名字查询员工信息获取邮件信息(TOKEN问题未解决)
            //String queryUrl = "http://asc.hand-china.com/hrms/staffquery/queryStaff";
            //Map<String, Object> map = new LinkedHashMap<>(2);
            //map.put("orgName", null);
            //map.put("name", "齐春林");
            //String emailInfo = sendPostMethodForQueryStaff(queryUrl, map);

            //TODO 定时任务-发送邮件
            //sendMail();*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送企业微信通知
     * 参考链接：https://work.weixin.qq.com/api/doc/90000/90136/91770
     *
     * @param mobileListStr 需要通知的人的手机号码
     * @return
     */
    private static void sendEnterpriseWeChat(String mobileListStr) {
        try {
            // 1、创建一个httpClient客户端对象
            CloseableHttpClient httpClient = HttpClients.createDefault();

            //某个群组添加机器人之后，创建者可以在机器人详情页看的该机器人特有的webhookurl
            String webHookUrl = "https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=4a52c5fd-68b2-4328-b900-ee93060bae53";
            // 2、创建一个HttpPost请求
            HttpPost httpPost = new HttpPost(webHookUrl);
            httpPost.setHeader("Content-Type", "application/json; charset=UTF-8");

            //data数据格式：{"msgtype":"text","text":{"content":"广州今日天气：29度，大部分多云，降雨概率：60%","mentioned_list":["wangqing","@all"],"mentioned_mobile_list":["13800001111","@all"]}}
            Map<String, Object> dataMap = new HashMap<>(2);
            dataMap.put("msgtype", "text");

            Map<String, String> textMap = new HashMap<>(2);
            textMap.put("content", "请以下同事及时填写今天(" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " )的TS,谢谢,手机号列表：" + mobileListStr);
            textMap.put("mentioned_mobile_list", mobileListStr);

            dataMap.put("text", textMap);
            log.info("发送企业微信通知参数：{}", JSONObject.toJSONString(dataMap));

            //放参数进post请求里面 json类型/UTF-8
            httpPost.setEntity(new StringEntity(JSONObject.toJSONString(dataMap), Charset.forName("UTF-8")));

            // 3、执行post请求操作，并拿到结果
            CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            log.info("机器人推送企业微信事件执行结果：{}", statusCode);
        } catch (Exception e) {
            log.error("机器人推送企业微信执行失败,具体原因：{}", e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 加载读取excel文件数据
     *
     * @param fileName
     */
    private static Map<String, Object> readExcel(String fileName) {
        //fileName="D://工号手机.xlsx"
        List<ExcelUserInfo> list = EasyExcel.read(fileName).head(ExcelUserInfo.class).sheet().doReadSync();
        if (CollectionUtils.isEmpty(list)) {
            log.error("excel数据为空,请检查");
            return null;
        }

        //工号和手机号
        Map<String, Object> codeAndPhoneMap = list.stream().distinct().collect(Collectors.toMap(ExcelUserInfo::getUserCode, ExcelUserInfo::getUserPhone));
        return codeAndPhoneMap;
    }

    private static String translatePhoneByCode(Map<String, Object> resultMap, Set<String> absentSet) {
        List<String> phoneList = new ArrayList<>();
        absentSet.forEach(s -> {
            boolean key = resultMap.containsKey(s);
            if (key) {
                String phone = (String) resultMap.get(s);
                phoneList.add(phone);
            }
        });
        return phoneList.stream().collect(Collectors.joining(","));
    }


    /**
     * 模拟登陆
     */
    private static String simulationLogin(String loginUrl) throws Exception {
        // 1、创建一个httpClient客户端对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 2、创建一个HttpPost请求
        HttpPost httpPost = new HttpPost(loginUrl);
        httpPost.setHeader("Connection", "keep-alive");
        httpPost.setHeader("Cache-Control", "max-age=0");
        httpPost.setHeader("sec-ch-ua", "\" Not A;Brand\";v=\"99\", \"Chromium\";v=\"96\", \"Google Chrome\";v=\"96\"");
        httpPost.setHeader("sec-ch-ua-mobile", "?0");
        httpPost.setHeader("sec-ch-ua-platform", "Windows");
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
        httpPost.setHeader("Referer", "https://login.hand-china.com/sso/login?service=http://app.hand-china.com/hrms/main.screen");
        httpPost.setHeader("Accept-Language", "zh-CN,zh;q=0.9");

        //登陆信息
        String reqData = "username=31738&password=DHSQJ444.&execution=98a7bfb4-9583-40ed-91dd-d517afddabf7_ZXlKaGJHY2lPaUpJVXpVeE1pSjkuZ0xSeCs5N0tvaWppU3hJL2hreGZ3OGhUM085MFhaQjRoWXhiK2ZTdkhHbWkzNlBkRXp1UmpwRXJ2c2ZWN1Z2WXJDbVg5c1FPRldON1I1dFdlbUlkbWltSFFWNjVEYTVLYyt5b0xZVXI0SVJzeXhlUnhVZ0N0cXBPd0N4R1JsZEVMdkZ3NWdYOGVtcWI0WElHakJjdHhuQVliNTZGUlhXMkdpcTU3c3k1OHB6OVBKMjVOcnB2S2ZKb3hKYyttOVNJWExNQ1lpWnBrU1pIc0pRSzhySE90VTVIZURQTzJiQmp4UG9wK3ExR3JBc1Z5MGZmcUVtcjhkTDVSeC9RZzZ1UUtXQXU0RTVJSm81S0pXM1FpeEFXa2U3N25PTVdGcmRZajVLN3ZIektlY2RUQUxvV3BGbGJTckswMnA2REhhdzFoK2dPT2tldHRkMVRUVkw3eUxiU0tFQjMxRmpjamxYcVk1U1h3cVVJL2IxRHpnMU1zb09RWVJ1ZUtBcDBFYW5EUzhrekxDb1JtY0hDYlNYdENKMmhvczJXRU1uWG13TnorNnNJQnZGSE53di9QV1BTeXlRVE5uM05UcW1ySjViUjFsc3dyUXFiZmExajIwcUZ0S2JUQ0JJWGJLSk50ZTNDeENQVWVKRHI5c3RjOWhlY3h0dEhCME5FYVpFV2kwNzYzUG9PdzRqc1NWN3RydmJENHdBb3BLOFA5K0I1TGRqdzZETlRYZUZ2SHN0UzRiMjIzNXhYdGhvOGtRbWJMMm5tcDNOVitjNjNaWGwraGl5Vko5T3I0OGdYcFo3TTNRMDY4SlFpeWZrQjh6MDZlRUFUcEtYMFdUOFFndVlGZmJBWUVNbVNodVpWaDc4U3poRFlRVzR2c21wYjZXVXFFeFE2NlQrOW1HKzg4YjJwMDdITnY2cjk3cUxvQmN5MzFDc2N3QURFbGkzODl0TUdmVzUreXZjNnl6OGR0TVVtTkFrOGdEYmF4UVZmRFlzTVd5cnZTQkk2QmtXL2ozYlBodUdFUGY2ZGcwRDVySnJDUW1sRUhLMTk0SzBySVNVZWdtdzNqdThnaDkycGozZmJ5cWw5RnVyOHV5bTYvcGpPeXQwbDNrRTF1bnV5b0w5WFlaeG9xN3ROR3ptYzE0RkZVSGRBbElVSVc5Y1ljWHV0UTM4Y0JWTTZRL0pXRFRWeEhrRGVPWUNzUWRNaFJocTZPQmM5RldxQTh2UlNhMnp3a21yWFg1S1JUZjVRbjlvdm9Tak10blIwQkh1eDVZL0p5VHdZek1pOHZEZWVDdCt4ZzFXVXRFdmkrQzRuV0Fnb012Z1FxSVNqMVJRQWF1Y2lDTnJzQ3BvZitCQ0NlNkp6OHdsSkdwWVJCT3ZmL3l2b0RqTWtHdHM4cndzTlRLUFpIZHN3eWE4dldScHVPNW1KZ2tnREpRVkhpRjd5ZWpPQzZoWE1ML0M5S0FKZTM5VzJ1YkcvRkFTQzJBSlUxTmduR0RBTVFNTXhkNzNTNXRQNVZLZlcrM0J0eVNmTVNZajZwM0NvM3o4bXlEeDdob0pjMUZjMVBoZkMvVFVyVzRNY3JxUnJ5cE91NktNUkhMUW9QN3RqeTBXMTdGVndzb2hkd2F6ZWliRXlIM1Y3aytwaThxb0I1L056S2VLMTFsOGo5Nm5HVXcwamFtaENUa0tBekJIM0dubzNjY0IvcE9OVVJiYXpJMmxYeVAyOGtKWlE0aFBHbnRhdzlLbmcwNVNaK3lqZVdyTXRhZWRGcVlMVE50dG9CU3Yrb0h5OTNpaFJJN0lXUEYzNGpqL0RMSVR6MkdOZGNWNWhzTEZ2aTFZRzRVdm05THhrMkhFcFRzSE9JbnZJM1BBLzNwcEdoWWFtRkVFeU91K0taSlFEYzVNWjYvMmFUS3l3ODVRZjFWYUdqYjdtMW5ZbEFmczRrM0N3RnE0RjcvdWhzeGlqUFNaUmh5UWlmc0ZMa2RmQzM4Y295QnZvZ2x3M1E2WDN0M0RSWUsvRkRPbjZqMlo4aTZiK3dwT3BzaHJRWmRlcktISUVSbzdQbkN0VXlHRGJzbmd4RDhmRk1OSGJWMkpncldnaEUxLzZJM3MwWm9UanpQbnNjT3NSMWIxZFhRWWQ1UG1tZm04a0YwT0hhQVVQcXlEcDJTN1gwOHJieDhBQ3dkVVkvVUlua3R1MHNSbHB6aHZ3QVIzRVZFK05nSllrL1B5dlJmUzFEbHVuakdHb01saWg5OVRPTndOUUtpdWFQSlNHaVpJbmhZT0cxMFp1Z0cxbDIycVhMRVVIdUExTktQSW5TTDUxblNrQ0wwd01PajM1azk5aTdDZjg5bko2QlRhZG83UEhDTmVESjNZbXQrdVdhMmlLbGgvbkh0VlhxQTV4WDZqQ09LQTZKWEpNNnc5MmtwWFBDTTMycXZFUHRqUi9pYXF4b0ZkaTJiUGkxSUd0Rks3LzVianpOc0s3Uk1tNUxnZE1KY0l2bjYyTHgxd3NRckJkUWRqUVA5RHpyaG1nc2Q4aWVEb0IySG1LVnFsWVoxZkZ2c0RoSVZ3NkRnS0hpR2lnVkpSNVJlbGdIWUtMR0lnODJUUVRKdG1RKzZKUllQTzBCM2lkWUx4M3RHWmUrREhPMUJZZ1k3VXIydzM4eTZaOVd3NHJFRmNSVlZibmcwWThNRTF1WTVIT0llU1UwMHE2RE5nWmtLeWl2UGRObnkvWHlHVXRKUTdNOGlYMlI3Wk1kMXBVWE9IU0FwQU9SRmhJZy9rT1BWbkVFRjRUb2g3NUFlUWg1NVhFTTJ1SmVLVllxc2lORU5sUWowR0pXb2w4U2ZVNjJNOVEyS0VNMm85U3JxZm11S1plR1dsbE5xbExuS3V0L0JxQkl6eXNJVlU3ZU5yL25RSFkyanZaVFRMTlZuaUlsSlgxQ0ZJbGhmRzNrSlZUOHBnY3NYWHdjZE1lUVRzaEIvMUczakxNaDU1Slo0VzcrbmZjRFJqeHhSUHROZ1E1ZWxKeXJUY2tDZy9EQzdBbG5NMFdLc1I5bjdwNS9kMXBNVFdvdjNsQ05hODRUaXY2TlUvVUZRMFV2UldNOXcydFNLUWY0RjZLbkVFSHVNWGJwQVcxOCttRVU1Rk96L3NlQ1FhZGc3end2UmkvVGhramZMYVZReHNnNWczS2dRN0dvVkZpdTJhSmFNMFppWDJDWFVXNm1pVzBFZkJYV2VXMnlTYU94cmttVVFFNFYxZFRpcDh4VFBOUVJyNnd1NWNpWUZzV2FtdFpsQlVScStFY0Uyd21IeVExaWd2Z2xWb0IyWXVqZTZNZEhxMmIwNGRHR004SkVITnA5TDgxNU12WEN5U1JTbVI2SWIvdWlxRml4ZnhCM2Rmdm1MWWZybkQyb000TlVNU0JrYWJLSEtEZVlqMjlJQ2ZzdmdOaFVpU2hNMTQ4blorMXYrL2xqMFErSmJVMmxFSnVqYzFDNEtSUDhtRjAyTUhpMFFRV0o3bWVMdEdhbGRBMmdqT3BKeGNXZmtrUmtWNmdYaHhyejdUcjFETEc4Y1hLUUo4eVY5aWlIdHhWdHdBYjVzb3kvdVZ5Rzk1WUV2RFJNSEZVcXpTMllhV1d5aDhRVXZIUm9BdlJha2E4TU16WFljRXRNM3I1bTczM0dMaEJJR1FhUCtzNUgzeDFVWUVWSk1VdkJIS2ltR3hTRVgxcDJxQlJMdVplcEhFSkoxRGJsUzJnUDdXVytUOHdXeEF0NjgrSjZUVGJkZThlcHV4WkpJTWlkVExtQ0haWTVyRUt1RjM5b1NubGc2cU9hQ1hIaS81Q0MrTWk5Z1U2dnd1MDFMTUdoME0xbC9VZFVQQmdEMkZtdXdsWi9tbVBKY2hwaFN6MkNwRmt1ZHZPUWJXVFRmandqTS9PTm14T0ZaWWVyZ25aV0xVVkVEcThVWmxMS3FQSndTcFVqakQybElYQmVjMXJLMHYyVHZ4RElQZC9FQklQSTB2czc5UksrNU9RZWRjYlZhSURHUFI1elhJNG1WT1FXTWsrclBjcEZXU29mSHlrcm11c1pXNHk5K0xIK1VZU0Rab3YrMHJtam5vRlZMbUE3c21DMWZmMUR0UHExRys0ZzFUM1hNS0k3ZkxaRjA2V2FTdTIzakNzRGw0d3ZpQzVOYU1haFJUWTgvbz0uX2MxZWlKYlVtb0cyRTF4RkxsczFSQVVUN1Nha0VWU1M1ek95WWdYNFVsUG4zMU1PTVpsVVh1LTctYTFWa0VPNW1xOWpJZzh0ZFVtMktPcGJ2TEQ1Smc=&_eventId=submit&geolocation=&submit=%E7%99%BB%E5%BD%95";
        //放参数进post请求里面 raw类型
        httpPost.setEntity(new StringEntity(reqData));

        // 7、执行post请求操作，并拿到结果
        CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
        int statusCode = httpResponse.getStatusLine().getStatusCode();

        //获取返回码中头部中location重定向的地址
        String redirectUrl = httpResponse.getHeaders("Location")[0].getValue();
        //第一次重定向后的ticket
        String ticket = redirectUrl.split("\\?")[1];
        log.info("login登录接口获取的ticket：{}", ticket);
        log.info("login登录接口当前状态码：{},第一次获取重定向地址：{}", statusCode, redirectUrl);


        String cookieTGCValue = httpResponse.getHeaders("Set-Cookie")[0].getValue();
        log.info("login登陆接口获取的Set-Cookie-TGC：{},{}", cookieTGCValue, httpResponse.getHeaders("Set-Cookie")[0].getElements());
        cookieTGCValue =  cookieTGCValue.split(";")[0];


        //2.执行第一次重定向
        CookieStore cookieStore = new BasicCookieStore();
        //此方式不带cookie
        //CloseableHttpClient httpClient2 = HttpClients.createDefault();

        CloseableHttpClient httpClient2 = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
        HttpGet httpGet = new HttpGet(redirectUrl);
        httpGet.setHeader("Proxy-Connection", "keep-alive");
        httpGet.setHeader("Cache-Control", "max-age=0");
        httpGet.setHeader("Upgrade-Insecure-Requests", "1");
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.110 Safari/537.36");
        httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8,en-US;q=0.7");
        httpGet.setHeader("Cookie", cookieTGCValue);
        CloseableHttpResponse response = httpClient2.execute(httpGet);

        HttpEntity entity = response.getEntity();
        String result = null;

        //全局参数JSESSIONID
        String JSESSIONID ="";
        String SERVICE_PARA ="";
        String SESSION_HAP ="";
        if (entity != null) {
            result = EntityUtils.toString(entity, "utf-8");

            // 获得重定向后的 Cookie
            List<Cookie> cookies = cookieStore.getCookies();
            for (int i = 0; i < cookies.size(); i++) {
                if ("JSESSIONID".equals(cookies.get(i).getName())) {
                    JSESSIONID = cookies.get(i).getValue();
                }

                 if ("SERVICE_PARA".equals(cookies.get(i).getName())) {
                    SERVICE_PARA = cookies.get(i).getValue();
                }
                 if ("SESSIONID_HAP".equals(cookies.get(i).getName())) {
                    SERVICE_PARA = cookies.get(i).getValue();
                }

            }
        }
        log.info("JSESSIONID：{},SERVICE_PARA：{}", JSESSIONID, SERVICE_PARA, SESSION_HAP);

        //3.执行第二次重定向
        CloseableHttpClient httpClient3 = HttpClients.createDefault();
        String redirectUrl2 = "http://app.hand-china.com/hrms/error_session_expired.screen?targetUrl=main.screen?" + ticket;
        log.info("第二次重定向：{}", redirectUrl2);
        HttpGet httpGet2 = new HttpGet(redirectUrl2);
        httpGet2.setHeader("Proxy-Connection", "keep-alive");
        httpGet2.setHeader("Cache-Control", "max-age=0");
        httpGet2.setHeader("Upgrade-Insecure-Requests", "1");
        httpGet2.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.110 Safari/537.36");
        //设置请求头
        httpGet2.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        httpGet2.setHeader("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8,en-US;q=0.7");
        httpGet2.setHeader("Cookie", "JSESSIONID=" + JSESSIONID + "; SERVICE_URL=main.screen; SERVICE_PARA=?" + ticket + "; " + cookieTGCValue);
        CloseableHttpResponse httpResponse2 = httpClient3.execute(httpGet2);

        HttpEntity entity2 = httpResponse2.getEntity();
        String result2 = null;
        if (entity2 != null) {
            result2 = EntityUtils.toString(entity2, "utf-8");
        }
        EntityUtils.consume(entity2);
        httpResponse2.close();


        //4.执行第三次
        CloseableHttpClient httpClient4 = HttpClients.createDefault();
        String redirectUrl3 = "http://app.hand-china.com/hrms/login.screen?targetUrl=main.screen?" + ticket;
        log.info("第三次重定向：{}", redirectUrl3);
        HttpGet httpGet3 = new HttpGet(redirectUrl3);
        httpGet3.setHeader("Proxy-Connection", "keep-alive");
        httpGet3.setHeader("Cache-Control", "max-age=0");
        httpGet3.setHeader("Upgrade-Insecure-Requests", "1");
        httpGet3.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.110 Safari/537.36");
        //设置请求头
        httpGet3.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        httpGet3.setHeader("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8,en-US;q=0.7");
        httpGet3.setHeader("Cookie", "JSESSIONID=" + JSESSIONID + "; SERVICE_URL=main.screen; SERVICE_PARA=?" + ticket + "; " + cookieTGCValue);
        CloseableHttpResponse httpResponse3 = httpClient4.execute(httpGet3);

        HttpEntity entity3 = httpResponse3.getEntity();
        String result3 = null;
        if (entity3 != null) {
            result3 = EntityUtils.toString(entity3, "utf-8");
            log.info("查询：{}", result3);

        }
        String redirectUrl4 = httpResponse3.getHeaders("Location")[0].getValue();
        log.info("第四次重定向：{}", redirectUrl4);

        EntityUtils.consume(entity3);
        httpResponse3.close();


        //5.执行第四次
        CloseableHttpClient httpClient5 = HttpClients.createDefault();
        HttpGet httpGet4 = new HttpGet(redirectUrl4);
        httpGet4.setHeader("Connection", "keep-alive");
        httpGet4.setHeader("Cache-Control", "max-age=0");
        httpGet4.setHeader("Upgrade-Insecure-Requests", "1");
        httpGet4.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.110 Safari/537.36");
        //设置请求头
        httpGet4.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        httpGet4.setHeader("Sec-Fetch-Site", "cross-site");
        httpGet4.setHeader("Sec-Fetch-Mode", "navigate");
        httpGet4.setHeader("Sec-Fetch-Dest", "document");
        httpGet4.setHeader("sec-ch-ua", " Not A;Brand\";v=\"99\", \"Chromium\";v=\"96\", \"Google Chrome\";v=\"96\"");
        httpGet4.setHeader("sec-ch-ua-mobile", "?0");
        httpGet4.setHeader("sec-ch-ua-platform", "\"Windows\"");
        httpGet4.setHeader("Referer", "http://app.hand-china.com/");
        httpGet4.setHeader("Accept-Language", "zh-CN,zh;q=0.9");
        httpGet4.setHeader("Cookie", cookieTGCValue);
        CloseableHttpResponse httpResponse4 = httpClient5.execute(httpGet4);

        HttpEntity entity4 = httpResponse4.getEntity();
        String result4 = null;
        if (entity4 != null) {
            result4 = EntityUtils.toString(entity4, "utf-8");
        }
        log.info("kkkkk");

        EntityUtils.consume(entity4);
        httpResponse4.close();

        return JSESSIONID;

    }


    /**
     * 根据姓名参数查询员工的邮箱
     *
     * @param queryUrl
     * @param paramMap
     * @return
     * @throws Exception
     */
    private static String sendPostMethodForQueryStaff(String queryUrl, Map<String, Object> paramMap) throws Exception {
        // 1、创建一个httpClient客户端对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 2、创建一个HttpPost请求
        HttpPost httpPost = new HttpPost(queryUrl);
        httpPost.addHeader("Content-Type", "application/json;charset=UTF-8");
        //设置请求头
        httpPost.setHeader("Connection", "keep-alive");
        httpPost.setHeader("Accept", "application/json, text/plain, */*");
        httpPost.setHeader("X-CSRF-TOKEN", "4f88bde1-de43-49dc-9d9e-ab37ef6bc421");
        httpPost.setHeader("X-Requested-With", "XMLHttpRequest");
        httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.110 Safari/537.36");
        // 设置传输的数据格式
        httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");
        httpPost.setHeader("Origin", "http://asc.hand-china.com");
        httpPost.setHeader("Referer", "http://asc.hand-china.com/hrms/staffquery/staffquery.html");
        httpPost.setHeader("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8,en-US;q=0.7");
        httpPost.setHeader("Cookie", "SESSIONID_HAP=e1df65fb-8a3b-4bb7-91a7-5ee1e339d9a7; GUEST_LANGUAGE_ID=zh_CN; Hm_lvt_afc5bec53effc77c4ec7e2a702b8f1f4=1625485460; _ga=GA1.2.374340343.1632619247; _ga_QFWN0KYHTY=GS1.1.1632619246.1.1.1632619323.0");

        //String data = "{\"orgName\":null,\"name\":\"齐春林\"}";

        //放参数进post请求里面 json类型
        StringEntity s = new StringEntity(JSONObject.toJSONString(paramMap, SerializerFeature.WriteMapNullValue), Charset.forName("UTF-8"));
        s.setContentEncoding("UTF-8");
        //发送json数据需要设置contentType
        s.setContentType("application/json");
        httpPost.setEntity(s);

        // 7、执行post请求操作，并拿到结果
        CloseableHttpResponse httpResponse = httpClient.execute(httpPost);

        // 获取结果实体
        HttpEntity entity = httpResponse.getEntity();
        if (entity != null) {
            //进行输出操作 这里就简单的使用EntityUtils工具类的toString()方法
            String responseBody = EntityUtils.toString(entity, "UTF-8");
            log.info("获取的响应结果：{}", responseBody);

            JSONObject jsonObject = JSONObject.parseObject(responseBody);
            String rows = jsonObject.getString("rows");
            log.info("获取结果rows：{}", rows);

            JSONArray jsonArray = JSONObject.parseArray(rows);
            String email = jsonArray.getJSONObject(0).getString("email");
            log.info("获取结果email：{}", email);


            return email;
        }
        return "";
    }

    /**
     * 发送post请求 携带非json数据
     *
     * @param url
     * @param queryDate 查询日期
     * @throws Exception
     */
    public static Object sendPostMethodForTS(String url, String queryDate) throws Exception {
        TsInfoVo tsInfoVo = new TsInfoVo();
        try {
            // 1、创建一个httpClient客户端对象
            CloseableHttpClient httpClient = HttpClients.createDefault();
            // 2、创建一个HttpPost请求
            HttpPost httpPost = new HttpPost(url);
            //设置请求头
            httpPost.setHeader("Connection", "keep-alive");
            httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.110 Safari/537.36");
            httpPost.setHeader("X-Requested-With", "XMLHttpRequest");
            // 设置传输的数据格式
            httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            httpPost.setHeader("Accept", "*/*");
            httpPost.setHeader("Origin", "http://app.hand-china.com");
            httpPost.setHeader("Referer", "http://app.hand-china.com/hrms/modules/hr/prj/PRJ106/PRP_ResourceQuery.screen");
            httpPost.setHeader("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8,en-US;q=0.7");
            //JSESSIONID=6AA98F195D9F5F9F9E413BB46C29BB46; JSID=9983DDC661ED7B28
            httpPost.setHeader("Cookie", "ISTIMEOUT=false; TARGETURL=%0A%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20modules/hr/prj/PRJ106/PRP_ResourceQuery.screen%0A%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20%20; FUNCTION_CODE=PRJ106; SERVICE_URL=main.screen; SERVICE_PARA=; IS_NTLM=N; vw=1920; JSESSIONID=6AA98F195D9F5F9F9E413BB46C29BB46; JSID=9983DDC661ED7B28; vh=421; GUEST_LANGUAGE_ID=zh_CN; Hm_lvt_afc5bec53effc77c4ec7e2a702b8f1f4=1625485460; _ga=GA1.2.374340343.1632619247; _ga_QFWN0KYHTY=GS1.1.1632619246.1.1.1632619323.0");

            //携带普通的参数params的方式
            //List<NameValuePair> params = new ArrayList<>();
            //String reqData = "%7B%22parameter%22%3A%7B%22date_to%22%3A%222021-12-27%22%2C%22date_from%22%3A%222021-12-27%22%2C%22unit_id%22%3A%22201136%22%2C%22cur_state%22%3A%22N%22%2C%22unit_name%22%3A%22%E4%B8%AD%E5%8F%B0%E6%8A%80%E6%9C%AF%E4%B8%AD%E5%BF%83(%E5%91%A8%E4%B8%80%E6%96%B0)%22%7D%7D";
            //params.add(new BasicNameValuePair("_request_data", reqData));

            //String reqDataPro = "_request_data=%7B%22parameter%22%3A%7B%22date_to%22%3A%222021-12-27%22%2C%22date_from%22%3A%222021-12-27%22%2C%22unit_id%22%3A%22201136%22%2C%22cur_state%22%3A%22N%22%2C%22unit_name%22%3A%22%E4%B8%AD%E5%8F%B0%E6%8A%80%E6%9C%AF%E4%B8%AD%E5%BF%83(%E5%91%A8%E4%B8%80%E6%96%B0)%22%7D%7D";
            //String reqDataPro = "_request_data={\"parameter\":{\"date_to\":\"2021-12-27\",\"date_from\":\"2021-12-27\",\"unit_id\":\"201136\",\"cur_state\":\"N\",\"unit_name\":\"中台技术中心(周一新)\"}}";
            String reqDataPro = "_request_data={\"parameter\":{\"date_to\":\"" + queryDate + "\",\"date_from\":\"" + queryDate + "\",\"unit_id\":\"201136\",\"cur_state\":\"N\",\"unit_name\":\"中台技术中心(周一新)\"}}";
            //放参数进post请求里面 raw类型
            httpPost.setEntity(new StringEntity(reqDataPro));

            // 7、执行post请求操作，并拿到结果
            CloseableHttpResponse httpResponse = httpClient.execute(httpPost);

            // 获取结果实体
            HttpEntity entity = httpResponse.getEntity();
            if (entity != null) {
                //进行输出操作 这里就简单的使用EntityUtils工具类的toString()方法
                String responseBody = EntityUtils.toString(entity, "UTF-8");
                log.info("获取的响应结果：{}", responseBody);

                JSONObject jsonObject = JSONObject.parseObject(responseBody);
                String result = jsonObject.getString("result");
                log.info("获取结果result：{}", result);

                JSONObject jsonObject2 = JSONObject.parseObject(result);
                String record = jsonObject2.getString("record");
                List<Map> tsDomainList = JSONObject.parseArray(record, Map.class);
                tsInfoVo.setTotalEmployee(tsDomainList.size());
                log.info("总记录数：{}", tsDomainList.size());

                //过滤掉已打卡的数据
                String curDate = new SimpleDateFormat("MM-dd").format(new Date());
                log.info("当前日期：{}", curDate);

                //12-27: ""、12-27flag: "0"、12-27project:""
                //(tsDomain.get(curDate) == null && tsDomain.get(curDate + "project") == null && tsDomain.get(curDate + "flag") == null) &&
                List<Map> list = tsDomainList.stream().filter(tsDomain -> (tsDomain.get("record_project") == null)).collect(Collectors.toList());
                tsInfoVo.setAbsentEmployee(list.size());
                tsInfoVo.setAbsentDetailList(list);
                log.info("未打卡记录数：{},缺勤人员数据：{}", list.size(), JSONObject.toJSONString(list));
                return tsInfoVo;
            } else {

            }
            //最后释放资源之类的
            EntityUtils.consume(entity);
            httpResponse.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
        return null;
    }


}

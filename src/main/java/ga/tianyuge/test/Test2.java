package ga.tianyuge.test;import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * 模拟登陆BI系统
 * @author: guoqing.chen01@hand-china.com 2022-01-28 10:37
 **/

public class Test2 {
    private static String cookie = "";
    private static CloseableHttpClient httpClient = null;
    private static final Logger logger = LoggerFactory.getLogger(Test2.class);
    public static void main(String[] args) {

        try {
            if (httpClient == null){
                httpClient = HttpClientBuilder.create().build();
            }

            StringBuilder urlStr = new StringBuilder("https://login.hand-china.com/sso/login");
            HashMap<String, String> params = new HashMap<>();
            params.put("username", "31738");
            params.put("password", "DHSQJ444.");
            params.put("execution", "98a7bfb4-9583-40ed-91dd-d517afddabf7_ZXlKaGJHY2lPaUpJVXpVeE1pSjkuZ0xSeCs5N0tvaWppU3hJL2hreGZ3OGhUM085MFhaQjRoWXhiK2ZTdkhHbWkzNlBkRXp1UmpwRXJ2c2ZWN1Z2WXJDbVg5c1FPRldON1I1dFdlbUlkbWltSFFWNjVEYTVLYyt5b0xZVXI0SVJzeXhlUnhVZ0N0cXBPd0N4R1JsZEVMdkZ3NWdYOGVtcWI0WElHakJjdHhuQVliNTZGUlhXMkdpcTU3c3k1OHB6OVBKMjVOcnB2S2ZKb3hKYyttOVNJWExNQ1lpWnBrU1pIc0pRSzhySE90VTVIZURQTzJiQmp4UG9wK3ExR3JBc1Z5MGZmcUVtcjhkTDVSeC9RZzZ1UUtXQXU0RTVJSm81S0pXM1FpeEFXa2U3N25PTVdGcmRZajVLN3ZIektlY2RUQUxvV3BGbGJTckswMnA2REhhdzFoK2dPT2tldHRkMVRUVkw3eUxiU0tFQjMxRmpjamxYcVk1U1h3cVVJL2IxRHpnMU1zb09RWVJ1ZUtBcDBFYW5EUzhrekxDb1JtY0hDYlNYdENKMmhvczJXRU1uWG13TnorNnNJQnZGSE53di9QV1BTeXlRVE5uM05UcW1ySjViUjFsc3dyUXFiZmExajIwcUZ0S2JUQ0JJWGJLSk50ZTNDeENQVWVKRHI5c3RjOWhlY3h0dEhCME5FYVpFV2kwNzYzUG9PdzRqc1NWN3RydmJENHdBb3BLOFA5K0I1TGRqdzZETlRYZUZ2SHN0UzRiMjIzNXhYdGhvOGtRbWJMMm5tcDNOVitjNjNaWGwraGl5Vko5T3I0OGdYcFo3TTNRMDY4SlFpeWZrQjh6MDZlRUFUcEtYMFdUOFFndVlGZmJBWUVNbVNodVpWaDc4U3poRFlRVzR2c21wYjZXVXFFeFE2NlQrOW1HKzg4YjJwMDdITnY2cjk3cUxvQmN5MzFDc2N3QURFbGkzODl0TUdmVzUreXZjNnl6OGR0TVVtTkFrOGdEYmF4UVZmRFlzTVd5cnZTQkk2QmtXL2ozYlBodUdFUGY2ZGcwRDVySnJDUW1sRUhLMTk0SzBySVNVZWdtdzNqdThnaDkycGozZmJ5cWw5RnVyOHV5bTYvcGpPeXQwbDNrRTF1bnV5b0w5WFlaeG9xN3ROR3ptYzE0RkZVSGRBbElVSVc5Y1ljWHV0UTM4Y0JWTTZRL0pXRFRWeEhrRGVPWUNzUWRNaFJocTZPQmM5RldxQTh2UlNhMnp3a21yWFg1S1JUZjVRbjlvdm9Tak10blIwQkh1eDVZL0p5VHdZek1pOHZEZWVDdCt4ZzFXVXRFdmkrQzRuV0Fnb012Z1FxSVNqMVJRQWF1Y2lDTnJzQ3BvZitCQ0NlNkp6OHdsSkdwWVJCT3ZmL3l2b0RqTWtHdHM4cndzTlRLUFpIZHN3eWE4dldScHVPNW1KZ2tnREpRVkhpRjd5ZWpPQzZoWE1ML0M5S0FKZTM5VzJ1YkcvRkFTQzJBSlUxTmduR0RBTVFNTXhkNzNTNXRQNVZLZlcrM0J0eVNmTVNZajZwM0NvM3o4bXlEeDdob0pjMUZjMVBoZkMvVFVyVzRNY3JxUnJ5cE91NktNUkhMUW9QN3RqeTBXMTdGVndzb2hkd2F6ZWliRXlIM1Y3aytwaThxb0I1L056S2VLMTFsOGo5Nm5HVXcwamFtaENUa0tBekJIM0dubzNjY0IvcE9OVVJiYXpJMmxYeVAyOGtKWlE0aFBHbnRhdzlLbmcwNVNaK3lqZVdyTXRhZWRGcVlMVE50dG9CU3Yrb0h5OTNpaFJJN0lXUEYzNGpqL0RMSVR6MkdOZGNWNWhzTEZ2aTFZRzRVdm05THhrMkhFcFRzSE9JbnZJM1BBLzNwcEdoWWFtRkVFeU91K0taSlFEYzVNWjYvMmFUS3l3ODVRZjFWYUdqYjdtMW5ZbEFmczRrM0N3RnE0RjcvdWhzeGlqUFNaUmh5UWlmc0ZMa2RmQzM4Y295QnZvZ2x3M1E2WDN0M0RSWUsvRkRPbjZqMlo4aTZiK3dwT3BzaHJRWmRlcktISUVSbzdQbkN0VXlHRGJzbmd4RDhmRk1OSGJWMkpncldnaEUxLzZJM3MwWm9UanpQbnNjT3NSMWIxZFhRWWQ1UG1tZm04a0YwT0hhQVVQcXlEcDJTN1gwOHJieDhBQ3dkVVkvVUlua3R1MHNSbHB6aHZ3QVIzRVZFK05nSllrL1B5dlJmUzFEbHVuakdHb01saWg5OVRPTndOUUtpdWFQSlNHaVpJbmhZT0cxMFp1Z0cxbDIycVhMRVVIdUExTktQSW5TTDUxblNrQ0wwd01PajM1azk5aTdDZjg5bko2QlRhZG83UEhDTmVESjNZbXQrdVdhMmlLbGgvbkh0VlhxQTV4WDZqQ09LQTZKWEpNNnc5MmtwWFBDTTMycXZFUHRqUi9pYXF4b0ZkaTJiUGkxSUd0Rks3LzVianpOc0s3Uk1tNUxnZE1KY0l2bjYyTHgxd3NRckJkUWRqUVA5RHpyaG1nc2Q4aWVEb0IySG1LVnFsWVoxZkZ2c0RoSVZ3NkRnS0hpR2lnVkpSNVJlbGdIWUtMR0lnODJUUVRKdG1RKzZKUllQTzBCM2lkWUx4M3RHWmUrREhPMUJZZ1k3VXIydzM4eTZaOVd3NHJFRmNSVlZibmcwWThNRTF1WTVIT0llU1UwMHE2RE5nWmtLeWl2UGRObnkvWHlHVXRKUTdNOGlYMlI3Wk1kMXBVWE9IU0FwQU9SRmhJZy9rT1BWbkVFRjRUb2g3NUFlUWg1NVhFTTJ1SmVLVllxc2lORU5sUWowR0pXb2w4U2ZVNjJNOVEyS0VNMm85U3JxZm11S1plR1dsbE5xbExuS3V0L0JxQkl6eXNJVlU3ZU5yL25RSFkyanZaVFRMTlZuaUlsSlgxQ0ZJbGhmRzNrSlZUOHBnY3NYWHdjZE1lUVRzaEIvMUczakxNaDU1Slo0VzcrbmZjRFJqeHhSUHROZ1E1ZWxKeXJUY2tDZy9EQzdBbG5NMFdLc1I5bjdwNS9kMXBNVFdvdjNsQ05hODRUaXY2TlUvVUZRMFV2UldNOXcydFNLUWY0RjZLbkVFSHVNWGJwQVcxOCttRVU1Rk96L3NlQ1FhZGc3end2UmkvVGhramZMYVZReHNnNWczS2dRN0dvVkZpdTJhSmFNMFppWDJDWFVXNm1pVzBFZkJYV2VXMnlTYU94cmttVVFFNFYxZFRpcDh4VFBOUVJyNnd1NWNpWUZzV2FtdFpsQlVScStFY0Uyd21IeVExaWd2Z2xWb0IyWXVqZTZNZEhxMmIwNGRHR004SkVITnA5TDgxNU12WEN5U1JTbVI2SWIvdWlxRml4ZnhCM2Rmdm1MWWZybkQyb000TlVNU0JrYWJLSEtEZVlqMjlJQ2ZzdmdOaFVpU2hNMTQ4blorMXYrL2xqMFErSmJVMmxFSnVqYzFDNEtSUDhtRjAyTUhpMFFRV0o3bWVMdEdhbGRBMmdqT3BKeGNXZmtrUmtWNmdYaHhyejdUcjFETEc4Y1hLUUo4eVY5aWlIdHhWdHdBYjVzb3kvdVZ5Rzk1WUV2RFJNSEZVcXpTMllhV1d5aDhRVXZIUm9BdlJha2E4TU16WFljRXRNM3I1bTczM0dMaEJJR1FhUCtzNUgzeDFVWUVWSk1VdkJIS2ltR3hTRVgxcDJxQlJMdVplcEhFSkoxRGJsUzJnUDdXVytUOHdXeEF0NjgrSjZUVGJkZThlcHV4WkpJTWlkVExtQ0haWTVyRUt1RjM5b1NubGc2cU9hQ1hIaS81Q0MrTWk5Z1U2dnd1MDFMTUdoME0xbC9VZFVQQmdEMkZtdXdsWi9tbVBKY2hwaFN6MkNwRmt1ZHZPUWJXVFRmandqTS9PTm14T0ZaWWVyZ25aV0xVVkVEcThVWmxMS3FQSndTcFVqakQybElYQmVjMXJLMHYyVHZ4RElQZC9FQklQSTB2czc5UksrNU9RZWRjYlZhSURHUFI1elhJNG1WT1FXTWsrclBjcEZXU29mSHlrcm11c1pXNHk5K0xIK1VZU0Rab3YrMHJtam5vRlZMbUE3c21DMWZmMUR0UHExRys0ZzFUM1hNS0k3ZkxaRjA2V2FTdTIzakNzRGw0d3ZpQzVOYU1haFJUWTgvbz0uX2MxZWlKYlVtb0cyRTF4RkxsczFSQVVUN1Nha0VWU1M1ek95WWdYNFVsUG4zMU1PTVpsVVh1LTctYTFWa0VPNW1xOWpJZzh0ZFVtMktPcGJ2TEQ1Smc=");
            params.put("_eventId", "submit");
            params.put("geolocation", "");
            params.put("submit", "登录");

            urlStr.append("?");


            Set<String> keys = params.keySet();

            Iterator<String> iterator = keys.iterator();

            while (iterator.hasNext()) {
                String key = iterator.next();
                urlStr.append(key);
                String value = params.get(key);
                if (StringUtils.isNotBlank(value)) {
                    urlStr
                            .append("=")
                            .append(value);
                }
                if (iterator.hasNext()) {
                    urlStr.append("&");
                }
            }

            /*for (String key : keys) {
                urlStr.append(key);
                String value = params.get(key);
                if (StringUtils.isNotBlank(value)) {
                    urlStr
                            .append("=")
                            .append(value);
                }
                urlStr.append("&");
            }*/

            String url = urlStr.toString();

            HttpPost post = new HttpPost(urlStr.toString());
            post.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.81 Safari/537.36");

            HttpResponse response = httpClient.execute(post);

            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                //返回json格式
                String res = EntityUtils.toString(response.getEntity());

            }

            Header[] allHeaders = response.getAllHeaders();
            for (Header header : allHeaders) {
                if ("Set-Cookie".equals(header.getName())) {
                    cookie = header.getValue();
                }
            }
            logger.debug("headers"+allHeaders.toString());
            response.getHeaders("11");



        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (httpClient != null){
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

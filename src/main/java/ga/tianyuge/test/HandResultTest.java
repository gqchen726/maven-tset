package ga.tianyuge.test;

import com.alibaba.fastjson.JSONObject;
import ga.tianyuge.bean.Employee;
import ga.tianyuge.bean.ResultInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.*;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
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
public class HandResultTest {
    private static final Logger log = LoggerFactory.getLogger(HandResultTest.class);



    public static void main(String[] args) {

       /* try {
            //请求地址
            String loginUrl = "http://bo.hand-china.com:8080/BOE/OpenDocument/2005112237/AnalyticalReporting/webiDHTML/viewer/report.jsp?iViewerID=1&sEntry=we000100004d69ae79aa96&iReport=0&iReportID=10&sPageMode=QuickDisplay&sReportMode=Viewing&iPage=1&zoom=100&isInteractive=false&isStructure=false&appKind=OpenDocument&sSetPrompts=true&sUndoEnabled=false";
            //模拟登录
            Map<String, String> resourceLoginInfoMap = simulationResourceLogin(loginUrl);

        } catch (Exception e) {
            e.printStackTrace();
        }*/
        test();
    }

    private static void test() {
        try {
            //写成html
            File file = new File("C:/temp/test.html");
            /*FileInputStream fos = new FileInputStream(file);
            while((s = fos.read()) != -1) {
                System.out.print((char)s);
            }*/

            FileReader fileReader = null;

            fileReader = new FileReader(file);

            StringBuilder content = new StringBuilder();
            String alineString = null;
            BufferedReader bufferedReader = null;
            bufferedReader = new BufferedReader(fileReader);
            while((alineString = bufferedReader.readLine()) != null) {
                content.append(alineString);
            }

            /*String[] split = content.toString().split("JSONDATA.AddOutLine\\(");
            String data = split[1];*/

            //解析一行
            String regex1 = "<tr.*?>(.*?)</tr>";
            Pattern pattern = Pattern.compile(regex1);
            Matcher matcher = pattern.matcher(content);
            //声明一个list对象
            List<String> list = new ArrayList<>();
            int count = 0;
            while (matcher.find()) {
                list.add(matcher.group(1));
            }

            List<List<String>> list1 = new ArrayList<>();
            String regex2 = "<span.*?>(.*?)</span>";
            Pattern pattern2 = Pattern.compile(regex2);
            for (String s1 : list) {
                ArrayList<String> values = new ArrayList<>();
                Matcher matcher2 = pattern2.matcher(s1);
                while (matcher2.find()) {
                    values.add(matcher2.group(1));
                }
                list1.add(values);
            }

            System.out.println();
            //提取结果为list1,其中包含的每一个list是表格中的一行数据
            int index = 0;
            ArrayList<Employee> employees = new ArrayList<>();
            for (List<String> strings : list1) {
                if (index <= 2) {
                    index++;
                    continue;
                }
                if (strings.size()<=1) {
                    index++;
                    continue;
                }

                Employee employee = new Employee();
                employee.setEmployeeCode(strings.get(0));
                employee.setName(strings.get(1));
                employee.setApprovalStatus(strings.get(19));
                employee.setApproverCode(strings.get(20));
                employee.setApproverName(strings.get(21));
                employees.add(employee);

                index++;
            }

            System.out.println();

            //统计项目经理
            Map<String, String> PMs = new HashMap();

            for (Employee employee : employees) {
                PMs.put(employee.getApproverCode(), employee.getApproverName());
            }

            HashMap<Integer, ResultInfo> result = new HashMap<>();
            int id = 0;
            //分类汇总
            for (String pmCode : PMs.keySet()) {
                Integer NoApproveDays = 0;
                String PMName = PMs.get(pmCode);
                for (Employee employee : employees) {
                    if (pmCode.equals(employee.getApproverCode()) && "未审批".equals(employee.getApprovalStatus())) {
                        NoApproveDays++;
                    }
                }
                if (NoApproveDays != 0) {
                    id++;
                    ResultInfo resultInfo = new ResultInfo();
                    resultInfo.setId(id);
                    resultInfo.setPMId(pmCode);
                    if (StringUtils.isNotBlank(PMName)) {
                        resultInfo.setPMName(PMName);
                    }
                    resultInfo.setNoApprovalDays(NoApproveDays);
                    result.put(id, resultInfo);
                }
            }

            System.out.println();

            System.out.println("序号\t项目经理工号\t项目经理名字\t未审批人数");
            for (ResultInfo value : result.values()) {
                System.out.println(value.toString());
            }

            System.out.println();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 模拟资源管理系统登陆
     *
     * @param loginUrl
     * @return
     * @throws Exception
     */
    private static Map<String, String> simulationResourceLogin(String loginUrl) throws Exception {

        CloseableHttpClient httpClient2 = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(loginUrl);
        httpGet.setHeader("Connection", "keep-alive");
        httpGet.setHeader("Upgrade-Insecure-Requests", "1");
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/97.0.4692.99 Safari/537.36");
        httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        httpGet.setHeader("Referer", "http://bo.hand-china.com:8080/BOE/OpenDocument/2005112237/AnalyticalReporting/webiDHTML/viewer/processDocWithPrompts.jsp?iViewerID=1&sEntry=we00000000fc4179ce952a&iReport=0&iReportID=10&sPageMode=QuickDisplay&sReportMode=Viewing&iPage=1&zoom=100&isInteractive=false&isStructure=false&appKind=OpenDocument");
        httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.9");
        httpGet.setHeader("Cookie", "JSESSIONID=1483B1CCB6BE3D67E9924B4209E9DD5F; GUEST_LANGUAGE_ID=zh_CN");
        CloseableHttpResponse response = httpClient2.execute(httpGet);

        HttpEntity entity = response.getEntity();
        if (entity != null) {
            String content = EntityUtils.toString(entity, "utf-8");
            log.warn("查询接口：{}" + content);

            //写成html
            File file = new File("C:/temp/test.html");
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
            log.warn("提取数据结果：{}" + JSONObject.toJSONString(list));


        }
        EntityUtils.consume(entity);
        response.close();
        return null;

    }

}

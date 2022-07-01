import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * timesheet报表解析与统计
 * @author: guoqing.chen01@hand-china.com 2022-01-28 17:14
 **/

public class TimeSheetApprovalStatistics {

    //调用示例
    /*public static void main(String[] args) {
        //需先读取完毕数据后进行统计，如果此报表有分页，请把每一分页进行数据提取后再进行分析，否则会出现统计不准确的情况
        //先提取数据
        //假设该数据有两页
        List<String> filePaths = new ArrayList<>();
        filePaths.add("C:/temp/test.html");
        List<Employee> employees = new ArrayList<>();
        TimeSheetApprovalStatistics tsApprovalStatistics = new TimeSheetApprovalStatistics();
        for (String filePath : filePaths) {
            employees.addAll(tsApprovalStatistics.readReportFormFile(filePath));
        }

        //后统计数据
        Collection<ResultInfo> resultInfos = tsApprovalStatistics.dataAnalysis(employees);

        String result = tsApprovalStatistics.returnResult(resultInfos);
        System.out.println(result);
    }*/

    /**
     * 读取报表文件，获取每一行的数据
     * @param filePath 报表文件的路径
     * @return 返回一个包含每一行的数据集合，第一行为报表名称，第二行为表头
     */
    private List<Employee> readReportFormFile(String filePath) {

        List<List<String>> lines = new ArrayList<>();
        ArrayList<Employee> employees = new ArrayList<>();
        FileReader fileReader = null;
        try {
            //写成html
            File file = new File(filePath);


            fileReader = new FileReader(file);

            StringBuilder content = new StringBuilder();
            String alineString = null;
            BufferedReader bufferedReader = null;
            bufferedReader = new BufferedReader(fileReader);
            while ((alineString = bufferedReader.readLine()) != null) {
                content.append(alineString);
            }



            //解析一行
            String regex1 = "<tr.*?>(.*?)</tr>";
            Pattern pattern = Pattern.compile(regex1);
            Matcher matcher = pattern.matcher(content);
            //声明一个list对象
            List<String> list = new ArrayList<>();
            while (matcher.find()) {
                list.add(matcher.group(1));
            }

            String regex2 = "<span.*?>(.*?)</span>";
            Pattern pattern2 = Pattern.compile(regex2);
            for (String s1 : list) {
                ArrayList<String> values = new ArrayList<>();
                Matcher matcher2 = pattern2.matcher(s1);
                while (matcher2.find()) {
                    values.add(matcher2.group(1));
                }
                lines.add(values);
            }

            //提取结果为lines,其中包含的每一个list是表格中的一行数据
            //把每一行数据提取需要的字段转化为对象
            int index = 0;
            for (List<String> strings : lines) {
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

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return employees;
    }

    private Collection<ResultInfo> dataAnalysis(List<Employee> employees) {
        HashMap<Integer, ResultInfo> result = new HashMap<>();
        try {
            //统计项目经理
            Map<String, String> PMs = new HashMap();

            for (Employee employee : employees) {
                PMs.put(employee.getApproverCode(), employee.getApproverName());
            }

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


        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.values();
    }

    private String returnResult(Collection<ResultInfo> resultInfos) {
        StringBuilder result = new StringBuilder();
        result.append("序号\t项目经理工号\t项目经理名字\t未审批人数").append("\n");

        for (ResultInfo resultInfo : resultInfos) {
            result.append(resultInfo).append("\n");
        }
        return result.toString();
    }
}

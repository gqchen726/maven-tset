package ga.tianyuge.test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: guoqing.chen01@hand-china.com 2022-02-15 14:54
 **/

public class FileIOTest {

    public static void main(String[] args) {
        read("C:\\temp\\POS_CN_ADP_1001_POSIPL_20220126_042452_000001.dat");
    }

    public static void read(String fileName) {
        File file = new File(fileName);
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] buf = new byte[1024];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buf)) > 0) {
                System.out.print(bytesRead);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 数据库复制出来的数据转化为markDown表格格式
     * @throws IOException
     */
    @Test
    public void dbDataConvertMdTableFormat() throws IOException {
        File fileOfIn = new File("C:\\Users\\GuoqingChen01\\Desktop\\test-in.txt");
        File fileOfOut = new File("C:\\Users\\GuoqingChen01\\Desktop\\test-out.txt");
        FileReader fileReader = new FileReader(fileOfIn);
        FileWriter fileWriter = new FileWriter(fileOfOut);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        List<String> list = new ArrayList<>();
        while (true) {
            String s = bufferedReader.readLine();
            if (StringUtils.isBlank(s)) {
                break;
            }
            String[] strings = s.split("\t");
            list = Arrays.asList(strings);
            int index = 0;
            if (CollectionUtils.isNotEmpty(list)) {
                for (String s1 : list) {
                    index++;
                    bufferedWriter.write("| ");
                    bufferedWriter.write(s1);
                    bufferedWriter.write(" ");
                    if (index == 3) {
                        bufferedWriter.write("|");
                        bufferedWriter.newLine();
                    }
                }
            }
        }
        bufferedWriter.flush();
    }

    /**
     * 数据库复制的数据，转化为列表查询条件数据，字符串类型
     * @throws IOException
     */
    @Test
    public void dbDataConvertDbQueryListFormatOfString() throws IOException {
        File fileOfIn = new File("C:\\Users\\GuoqingChen01\\Desktop\\test-in.txt");
        File fileOfOut = new File("C:\\Users\\GuoqingChen01\\Desktop\\test-out.txt");
        FileReader fileReader = new FileReader(fileOfIn);
        FileWriter fileWriter = new FileWriter(fileOfOut);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        StringBuilder stringBuilder = new StringBuilder();
        while (true) {
            String s = bufferedReader.readLine();
            if (StringUtils.isBlank(s)) {
                break;
            }
            s = s.trim();
            stringBuilder.append("'").append(s).append("'").append(",");
        }
//        stringBuilder.replace(stringBuilder.length()-2, stringBuilder.length()-1, "");
        stringBuilder.deleteCharAt(stringBuilder.length()-1);
        bufferedWriter.write(stringBuilder.toString());
        bufferedWriter.flush();
    }

    /**
     * 金额汇总
     * @throws IOException
     */
    @Test
    public void sumOfMoney() throws IOException {
        File fileOfIn = new File("C:\\Users\\GuoqingChen01\\Desktop\\test-in.txt");
        File fileOfOut = new File("C:\\Users\\GuoqingChen01\\Desktop\\test-out.txt");
        FileReader fileReader = new FileReader(fileOfIn);
        FileWriter fileWriter = new FileWriter(fileOfOut);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        final BigDecimal[] totalAmount = {BigDecimal.ZERO};
        List<String> collect = bufferedReader.lines().filter(StringUtils::isNoneBlank).collect(Collectors.toList());
        collect.forEach(s -> {
            s = s.trim().replace(",", "");
            System.out.println(s);
            totalAmount[0] = totalAmount[0].add(new BigDecimal(s));
        });
        /*while (true) {
            String s = bufferedReader.readLine();
            if (StringUtils.isBlank(s)) {
                break;
            }
            s = s.trim().replace(",", "");
            if (StringUtils.isBlank(s)) {
                continue;
            }
            System.out.println(s);
            totalAmount = totalAmount.add(new BigDecimal(s));
        }*/
        bufferedWriter.write(totalAmount[0].stripTrailingZeros().toPlainString());
        bufferedWriter.flush();
    }

    /**
     * 数据库复制的数据，转化为列表查询条件数据，INT类型
     * @throws IOException
     */
    @Test
    public void dbDataConvertDbQueryListFormatOfInteger() throws IOException {
        File fileOfIn = new File("C:\\Users\\GuoqingChen01\\Desktop\\test-in.txt");
        File fileOfOut = new File("C:\\Users\\GuoqingChen01\\Desktop\\test-out.txt");
        FileReader fileReader = new FileReader(fileOfIn);
        FileWriter fileWriter = new FileWriter(fileOfOut);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        StringBuilder stringBuilder = new StringBuilder();
        while (true) {
            String s = bufferedReader.readLine();
            if (StringUtils.isBlank(s)) {
                break;
            }
            s = s.trim();
            stringBuilder.append(s).append(",");
        }
//        stringBuilder.replace(stringBuilder.length()-2, stringBuilder.length()-1, "");
        stringBuilder.deleteCharAt(stringBuilder.length()-1);
        bufferedWriter.write(stringBuilder.toString());
        bufferedWriter.flush();
    }

    /**
     * 日志中复制的查询参数数据，格式化
     * @throws IOException
     */
    @Test
    public void logDataConvertQueryParam() throws IOException {
        File fileOfIn = new File("C:\\Users\\GuoqingChen01\\Desktop\\test-in.txt");
        File fileOfOut = new File("C:\\Users\\GuoqingChen01\\Desktop\\test-out.txt");
        FileReader fileReader = new FileReader(fileOfIn);
        FileWriter fileWriter = new FileWriter(fileOfOut);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder stringBuilderTemp;
        while (true) {
            String s = bufferedReader.readLine();
            if (StringUtils.isBlank(s)) {
                break;
            }
            s = s.trim();
            String[] split = s.split(",");
            String sTemp = null;
            for (String s1 : split) {
                s1 = s1.trim();
                sTemp = s1;
                stringBuilderTemp = new StringBuilder(s1);
                int start = stringBuilderTemp.indexOf("(");
                int end = stringBuilderTemp.indexOf(")")+1;
                s1 = stringBuilderTemp.delete(start, end).toString();
                if (sTemp.contains("String")) {
                    s1 = "\"" + s1 + "\"";
                }
                stringBuilder.append(s1.trim()).append(",");
            }
        }
//        stringBuilder.replace(stringBuilder.length()-2, stringBuilder.length()-1, "");
        stringBuilder.deleteCharAt(stringBuilder.length()-1);
        bufferedWriter.write(stringBuilder.toString());
        bufferedWriter.flush();
    }

    /**
     * 处理文本中的反斜杠，一般处理json数据
     * @throws IOException
     */
    @Test
    public void textReplaceAllBackslash() throws IOException {
        File fileOfIn = new File("C:\\Users\\GuoqingChen01\\Desktop\\test-in.txt");
        File fileOfOut = new File("C:\\Users\\GuoqingChen01\\Desktop\\test-out.txt");
        FileReader fileReader = new FileReader(fileOfIn);
        FileWriter fileWriter = new FileWriter(fileOfOut);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        StringBuilder stringBuilder = new StringBuilder();
        while (true) {
            String s = bufferedReader.readLine();
            if (StringUtils.isBlank(s)) {
                break;
            }
            s = s.trim();
            stringBuilder.append(
                    s.replace("\\", "")
                        .replaceAll("\"\\[", "\\[")
                        .replace("]\"", "]")
                        .replace("\"{", "{")
                        .replace("}\"", "}")
            );
        }
//        stringBuilder.replace(stringBuilder.length()-2, stringBuilder.length()-1, "");
        /*// 删除头尾引号
        stringBuilder.deleteCharAt(0);stringBuilder.re
        stringBuilder.deleteCharAt(stringBuilder.length()-1);*/
        bufferedWriter.write(stringBuilder.toString());
        bufferedWriter.flush();
    }

    /**
     * 给json数据添加反斜杠
     * @throws IOException
     */
    @Test
    public void textReplaceAllBackslash1() throws IOException {
        File fileOfIn = new File("C:\\Users\\GuoqingChen01\\Desktop\\test-in.txt");
        File fileOfOut = new File("C:\\Users\\GuoqingChen01\\Desktop\\test-out.txt");
        FileReader fileReader = new FileReader(fileOfIn);
        FileWriter fileWriter = new FileWriter(fileOfOut);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        StringBuilder stringBuilder = new StringBuilder();
        while (true) {
            String s = bufferedReader.readLine();
            if (StringUtils.isBlank(s)) {
                break;
            }
            s = s.trim();
            stringBuilder.append(
                    s
                            .replace("\"", "\\\"")
                            .replaceAll("\\[", "\"\\[")
                            .replace("]", "]\"")
                            .replace("{", "\"{")
                            .replace("}", "}\"")
            );
        }
//        stringBuilder.replace(stringBuilder.length()-2, stringBuilder.length()-1, "");
        /*// 删除头尾引号
        stringBuilder.deleteCharAt(0);stringBuilder.re
        stringBuilder.deleteCharAt(stringBuilder.length()-1);*/
        bufferedWriter.write(stringBuilder.toString());
        bufferedWriter.flush();
    }

    @Test
    public void findCharacter() throws IOException {
        File fileOfIn = new File("C:\\Users\\GuoqingChen01\\Desktop\\test-in.txt");
        FileReader fileReader = new FileReader(fileOfIn);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        while (true) {
            String s = bufferedReader.readLine();
            if (StringUtils.isBlank(s)) {
                break;
            }
            s = s.trim();
            System.out.println(s.contains("\"failed\":true"));
        }
    }

    /**
     * 日志文件中查找固定字符串数量
     */
    @Test
    public void findStringNum() throws IOException {
        String key = "selectSettleWithSameAsnById -<==      Total: ";
        File fileOfIn =
                new File("C:\\Users\\GuoqingChen01\\Desktop\\SHSH\\日志\\nohup-find-44.log");
        File fileOfOut =
                new File("C:\\Users\\GuoqingChen01\\Desktop\\SHSH\\日志\\nohup-find-44-result.log");
        FileReader fileReader = new FileReader(fileOfIn);
        FileWriter fileWriter = new FileWriter(fileOfOut);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        StringBuilder stringBuilder = new StringBuilder();
        while (true) {
            String s = bufferedReader.readLine();
            if (StringUtils.isBlank(s)) {
                break;
            }
//            s = s.trim();
            if (s.contains(key)) {
                if (s.contains(key + "0")) continue;
                stringBuilder.append(s).append("\n");
            }
        }
//        stringBuilder.replace(stringBuilder.length()-2, stringBuilder.length()-1, "");
//        stringBuilder.deleteCharAt(stringBuilder.length()-1);
        bufferedWriter.write(stringBuilder.toString());
        bufferedWriter.flush();
    }


    /**
     * 批量替换,采购申请替换为redis命令
     */
    @Test
    public void replaceAllPurchaseRequestToRedisCommand() throws IOException {
        String sourceStr = "{\"businessNum\":\"REQ\",\"code\":\"1\",\"processId\":\"844\",\"tenantId\":3,\"complete\":\"true\",\"tenetId\":3}";
        File fileOfIn = new File("C:\\Users\\GuoqingChen01\\Desktop\\test-in.txt");
        File fileOfOut = new File("C:\\Users\\GuoqingChen01\\Desktop\\test-out.txt");
        FileReader fileReader = new FileReader(fileOfIn);
        FileWriter fileWriter = new FileWriter(fileOfOut);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        StringBuilder stringBuilder = new StringBuilder();
        int i = 0;
        while (true) {
            if (i == 0) {
                stringBuilder.append("ZADD srm:bpm-pr-head:0 ");
            }
            i++;
            String s = bufferedReader.readLine();
            if (StringUtils.isBlank(s)) {
                break;
            }
            stringBuilder.append(i + " ").append("\"").append(sourceStr.replace("REQ", s.trim()).replaceAll("\"", "\\\\\"")).append("\"").append(" ");
            if (i == 2) {
                stringBuilder.deleteCharAt(stringBuilder.length()-1);
                stringBuilder.append("\n");
                i = 0;
            }
        }
//        stringBuilder.replace(stringBuilder.length()-2, stringBuilder.length()-1, "");
        bufferedWriter.write(stringBuilder.toString());
        bufferedWriter.flush();
    }

    /**
     * 处理文本中的特殊字符
     * @throws IOException
     */
    @Test
    public void dealingWithSpecialCharacters() throws IOException {
        File fileOfIn = new File("C:\\Users\\GuoqingChen01\\Desktop\\test-in.txt");
        File fileOfOut = new File("C:\\Users\\GuoqingChen01\\Desktop\\test-out.txt");
        FileReader fileReader = new FileReader(fileOfIn);
        FileWriter fileWriter = new FileWriter(fileOfOut);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        StringBuilder stringBuilder = new StringBuilder();
        StringTest st = new StringTest();
        while (true) {
            String s = bufferedReader.readLine();
            if (StringUtils.isBlank(s)) {
                break;
            }
//            s = s.trim();
            stringBuilder.append(st.replaceSpecialChar(s)).append("\n");
        }
//        stringBuilder.replace(stringBuilder.length()-2, stringBuilder.length()-1, "");
        /*// 删除头尾引号
        stringBuilder.deleteCharAt(0);
        stringBuilder.deleteCharAt(stringBuilder.length()-1);*/
        bufferedWriter.write(stringBuilder.toString());
        bufferedWriter.flush();
    }

    /**
     * 空格替换为缩进
     * @throws IOException
     */
    @Test
    public void replaceSpacesWithIndents() throws IOException {
        File fileOfIn = new File("C:\\Users\\GuoqingChen01\\Desktop\\test-in.txt");
        File fileOfOut = new File("C:\\Users\\GuoqingChen01\\Desktop\\test-out.txt");
        FileReader fileReader = new FileReader(fileOfIn);
        FileWriter fileWriter = new FileWriter(fileOfOut);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        StringBuilder stringBuilder = new StringBuilder();
        StringTest st = new StringTest();
        while (true) {
            String s = bufferedReader.readLine();
            if (StringUtils.isBlank(s)) {
                break;
            }
//            s = s.trim();
            stringBuilder.append(s.replaceAll("  ", "\t")).append("\n");
        }
//        stringBuilder.replace(stringBuilder.length()-2, stringBuilder.length()-1, "");
        /*// 删除头尾引号
        stringBuilder.deleteCharAt(0);
        stringBuilder.deleteCharAt(stringBuilder.length()-1);*/
        bufferedWriter.write(stringBuilder.toString());
        bufferedWriter.flush();
    }

    /**
     * 数据对比，数据以6个横杠分隔(------)
     * @throws IOException
     */
    @Test
    public void dataComparison() throws IOException {
        File fileOfIn = new File("C:\\Users\\GuoqingChen01\\Desktop\\test-in.txt");
        File fileOfOut = new File("C:\\Users\\GuoqingChen01\\Desktop\\test-out.txt");
        FileReader fileReader = new FileReader(fileOfIn);
        FileWriter fileWriter = new FileWriter(fileOfOut);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        StringBuilder stringBuilder = new StringBuilder();
        List<String> data1 = new ArrayList<>();
        List<String> data2 = new ArrayList<>();
        boolean flag = false;
        while (true) {
            String s = bufferedReader.readLine();
            if (StringUtils.isBlank(s)) {
                break;
            }
            s = s.trim().replace("\t", "").replace(" ", "");
            if ("------".equals(s)) {
                flag = true;
                continue;
            }
            if (flag) {
                data2.add(s);
            } else {
                data1.add(s);
            }
        }
        // 复制数组
        List<String> data1a = new ArrayList<>(data1);
        List<String> data2a = new ArrayList<>(data2);
        List<String> data1b = new ArrayList<>(data1);
        List<String> data2b = new ArrayList<>(data2);
        // 差集
        data1a.removeAll(data2a);
        data2b.removeAll(data1b);
        stringBuilder
                .append("data1a - data2a: ")
                .append("\n")
                .append(String.join("\n", data1a))
                .append("\n")
                .append("data2b - data1b: ")
                .append("\n")
                .append(String.join("\n", data2b))
                .append("\n");
        bufferedWriter.write(stringBuilder.toString());
        bufferedWriter.flush();
    }

    /**
     * 数据去重
     * @throws IOException
     */
    @Test
    public void dataDeduplication() throws IOException {
        File fileOfIn = new File("C:\\Users\\GuoqingChen01\\Desktop\\test-in.txt");
        File fileOfOut = new File("C:\\Users\\GuoqingChen01\\Desktop\\test-out.txt");
        FileReader fileReader = new FileReader(fileOfIn);
        FileWriter fileWriter = new FileWriter(fileOfOut);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        StringBuilder stringBuilder = new StringBuilder();
        List<String> stringList = new ArrayList<>();
        List<String> stringSet = new ArrayList<>();
        while (true) {
            String s = bufferedReader.readLine();
            if (StringUtils.isBlank(s)) {
                break;
            }
            stringList.add(s.trim());
        }
        stringSet = stringList.stream().distinct().collect(Collectors.toList());
        List<Long> longSet = new ArrayList<>();
        List<Long> longList = new ArrayList<>();
        try {
            longSet = stringSet.stream().map(Long::valueOf).collect(Collectors.toList());
            Collections.sort(longSet);
            longList = stringList.stream().map(Long::valueOf).collect(Collectors.toList());
            Collections.sort(longList);
        } catch (Exception ignored) {

        }
        StringBuilder finalStringBuilder1 = stringBuilder;
        if (CollectionUtils.isNotEmpty(longSet)) {
            longSet.forEach(t -> finalStringBuilder1.append(t).append("\n"));
        } else {
            stringSet.forEach(t -> finalStringBuilder1.append(t).append("\n"));
        }
        bufferedWriter.write(stringBuilder.toString());
        if (CollectionUtils.isNotEmpty(longSet)) {
            // 查找重复数据
            bufferedWriter.write("--------以下为重复数据---------");
            List<Long> duplicateData = new ArrayList<>();
            stringBuilder = new StringBuilder("\n");
            StringBuilder finalStringBuilder = stringBuilder;
            for (Long aLong : longList) {
                if (duplicateData.contains(aLong)) {
                    finalStringBuilder.append(aLong).append("\n");
                } else {
                    duplicateData.add(aLong);
                }
            }
            bufferedWriter.write(stringBuilder.toString());
            // 查找缺失数据
            List<Long> missingData = new ArrayList<>();
            for (int i = 1; i < 91; i++) {
                missingData.add((long) i);
            }
            missingData.removeAll(longSet);
            bufferedWriter.write("--------以下为缺失数据---------");
            stringBuilder = new StringBuilder("\n");
            StringBuilder finalStringBuilder2 = stringBuilder;
            missingData.forEach(t -> finalStringBuilder2.append(t).append("\n"));
            bufferedWriter.write(stringBuilder.toString());
        }
        bufferedWriter.flush();
    }

    /**
     * 从JSON格式的接口日志中提取内容，并统计大小
     * @throws IOException
     */
    @Test
    public void extractContentFromJsonFormatInterfaceLog() throws IOException {
        File fileOfIn = new File("C:\\Users\\GuoqingChen01\\Desktop\\test-in.txt");
        File fileOfOut = new File("C:\\Users\\GuoqingChen01\\Desktop\\test-out.txt");
        FileReader fileReader = new FileReader(fileOfIn);
        FileWriter fileWriter = new FileWriter(fileOfOut);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        StringBuilder stringBuilder = new StringBuilder();
        Set<String> stringSet = new HashSet<>();
        while (true) {
            String s = bufferedReader.readLine();
            if (StringUtils.isBlank(s)) {
                break;
            }
//            s = s.trim();
            JSONObject jsonObject = JSONObject.parseObject(s);
            for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
                String key = entry.getKey();
                if ("data".equals(key)) {
                    String decrypt = entry.getValue().toString();
//                    decrypt = decrypt.startsWith("\"") ? decrypt.substring(1, decrypt.length()-1) : decrypt;
                    decrypt = decrypt.replace("\\", "");
                    entry.setValue(decrypt);
                }
            }
            if (jsonObject.containsKey("body")) {
                JSONArray bodyList = JSONObject.parseArray(JSONObject.toJSONString(jsonObject.get("body")));
                bodyList.forEach(t -> {
                    JSONObject body = JSONObject.parseObject(JSONObject.toJSONString(t));
                    if (body.containsKey("rcvTrxLineList")) {
                        JSONArray rcvTrxLineList = JSONObject.parseArray(JSONObject.toJSONString(body.get("rcvTrxLineList")));
                        rcvTrxLineList.forEach( e -> {
                            JSONObject jo = JSONObject.parseObject(JSONObject.toJSONString(e));
                            if (jo.containsKey("esFromPoNum") && jo.containsKey("esFromPoLineNum")) {
                                stringSet.add(StringUtils.join(jo.get("esFromPoNum"), "-", jo.get("esFromPoLineNum")));
                            }
                        });

                    }
                });

            }
        }
        StringBuilder finalStringBuilder1 = stringBuilder;
        if (CollectionUtils.isNotEmpty(stringSet)) {
            stringSet.forEach(t -> finalStringBuilder1.append(t).append("\n"));
        }
        List<String> list = new ArrayList<>(stringSet);
        Collections.sort(list);
        stringBuilder.append("total count: ").append(list.size());
        List<Long> longList = new ArrayList<>();
        bufferedWriter.write(finalStringBuilder1.toString());
        bufferedWriter.write("\n");
        list.forEach(str -> longList.add(Long.valueOf(str.split("-")[1].substring(2,5))));
        stringBuilder = new StringBuilder();
        StringBuilder finalStringBuilder = stringBuilder;
        Collections.sort(longList);
        longList.forEach(l -> finalStringBuilder.append(l).append("\n"));
        bufferedWriter.write(finalStringBuilder.toString());
        bufferedWriter.flush();
    }

    @Test
    public void downloadFileTest() {
        String fileUrl = "http://10.10.101.71:9000/dev-private-bucket/purchase-contract/3/522b202304d44b1b9c75f8703b7c62e0@测试合同-炼化-1018-cyq.docx";
        // 1、创建一个httpClient客户端对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 2、创建一个HttpPost请求
        HttpGet http = new HttpGet(fileUrl);
        http.setHeader("Accept", "*/*");
        http.setHeader("Accept-Encoding", "gzip, deflate, br");
        http.setHeader("Connection", "keep-alive");
        http.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36");

        FileOutputStream fileOutputStream = null;
        InputStream inputStream = null;
        try {
            CloseableHttpResponse execute = httpClient.execute(http);

            inputStream = execute.getEntity().getContent();
            File file = new File("D:\\temp\\a.docx");
            fileOutputStream = new FileOutputStream(file);
            int b;
            while ((b = inputStream.read()) != -1) {
                fileOutputStream.write(b);
            }
            fileOutputStream.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileOutputStream.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}

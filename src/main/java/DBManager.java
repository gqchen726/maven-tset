/**
 * @author: guoqing.chen01@hand-china.com 2022-02-11 11:19
 **/

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DBManager {
    /********************静态块可以提高效率***********/
    static {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String[] args){

        File file = new File("C:/temp/PhysicalCount_5030_01_00815_20220126_104256_1.txt");

        FileReader fileReader = null;
        List<String> lines = new ArrayList<>();

        String lineBa = null;
        String replace = null;
        try {
            fileReader = new FileReader(file);

            StringBuilder content = new StringBuilder();
            String alineString = null;
            BufferedReader bufferedReader = null;
            bufferedReader = new BufferedReader(fileReader);
            while ((alineString = bufferedReader.readLine()) != null) {
                String[] split = alineString.split("\t");
                lines.add(split[1]);
            }


        DBManager c = new DBManager();
        String sql = "INSERT INTO [dbo].[test_test] ([test]) VALUES ('1');";

        Connection con;
        Statement sta = null;
        ResultSet rs;

        String url = "jdbc:sqlserver://10.64.227.5:1433;DatabaseName=LVMHPOSSUPTEST";
        try {
            /**第一个sa是你的SQLserver用户名,第二个是此用户名所对应的密码***/
            con = DriverManager.getConnection(url, "Hand", "h@nd");
            sta = con.createStatement();
            System.out.println("连接成功");
        } catch (SQLException e) {
            System.out.println("连接失败");
            e.printStackTrace();
        }

        for (String line : lines) {
            replace = sql.replace("1", line);
            lineBa = line;
            sta.execute(replace);

        }



        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("错误数据: "+lineBa);
            System.out.println(replace);
        }
    }
}

package ga.tianyuge.test;

import org.junit.Test;

import java.sql.*;

/**
 * description
 *
 * @author guoqing.chen01@hand-china.com 2022/09/14 13:21
 */
public class JdbcTest {

    @Test
    public void test1() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://110.42.157.115:3306/travel_shanxi", "travel_shanxi", "travel_shanxi");
        String s = "五金材料（Ｐ7\\P 3）";
        System.out.println("before change : " + s);
        String s1 = s;
        if (s.contains("\\")) {
            s1 = s.replaceAll("\\\\", "\\\\\\\\\\\\\\\\");
//            String s1 = StringEscapeUtils.escapeSql(s);
            System.out.println("after change : " + s1);
        }
        PreparedStatement pstmt = conn.prepareStatement("select * from test where name like '%"+s1+"%'");
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            System.out.println("result: "+rs.getString("name"));
        }
    }
}

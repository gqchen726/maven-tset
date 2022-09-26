package ga.tianyuge.test;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * @author: guoqing.chen01@hand-china.com 2021-12-10 14:43
 **/

public class Test {
    private static final Logger logger = LoggerFactory.getLogger(Test.class);
    public static void main(String[] args) {
        try {
            String days = "0";
            if ("".equals(days)) {
                days = "3";
            } else if (Integer.valueOf(days) == 0) {
                days = "3";
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String date = sdf.format(new Date());
            Calendar c = Calendar.getInstance();
            logger.warn("c: "+c.getTime());

            c.setTime(sdf.parse(date));
            c.add(Calendar.DAY_OF_MONTH, (-1) * Integer.parseInt(days) + 1);
            String startDate = sdf.format(c.getTime());
            c.setTime(sdf.parse(date));
//            c.add(Calendar.DAY_OF_MONTH, -1);
            String endDate = sdf.format(c.getTime());

            ArrayList<Object> objects = new ArrayList<>();

            /*//定义二维数组
            int[][] myarr = {{1,2,3},{3,4,5,6},{2,4,5,6,7,8,3,4,5,6,7,8}};
            //遍历二维数组
            for (int i = 0; i < myarr.length; i++) {
                for (int j = 0; i < myarr[i].length; j++) {
                    System.out.println(myarr[i][j]);
                }
            }*/


            logger.warn("startDate: "+startDate);
            logger.warn("endDate: "+endDate);
            logger.warn("warn");


            /*String url = "mysql://username:password@host/database?reconnect=true";
            String[] infos = url.split("//")[1].split("@");
            String usernameAndPassword = infos[0];
            String[] identityInformations = usernameAndPassword.split(":");
            String username = identityInformations[0];
            String password = identityInformations[1];
            String hostAndDatabases = infos[1];*/



        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }
}

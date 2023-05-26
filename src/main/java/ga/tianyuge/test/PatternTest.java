package ga.tianyuge.test;

import cn.hutool.core.lang.Assert;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description: description
 * @author: GuoqingChen
 * @Time: 2023/05/25 14:02
 * @Email: guoqing.chen01@hand-china.com
 */
public class PatternTest {
    @Test
    public void test1() {
        String fileUrl = "http://scptest.shenghongpec.com:9000/dev-private-bucket/hsdr01/3/d6366f88e17c4ae7a1d8038fb28176953e@test.doc";
        Pattern pattern;
        Matcher matcher;
        pattern = Pattern.compile("\\.pdf$");
        matcher = pattern.matcher(fileUrl);
        if (!matcher.find()) {
            pattern = Pattern.compile("[^/]*\\.docx");
            matcher = pattern.matcher(fileUrl);
            String fileFullName = null;
            if (matcher.find() && matcher.groupCount() > 0) {
                fileFullName = matcher.group(0);
            } else {
                fileFullName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
            }
            System.out.println(fileFullName);
            String[] splitArray = fileFullName.split("\\.");
            Assert.isTrue(splitArray.length>1);
            System.out.println(splitArray[splitArray.length-1]);
        }
    }
}

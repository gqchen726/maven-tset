package ga.tianyuge.test;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * description
 *
 * @author guoqing.chen01@hand-china.com 2022/10/10 9:37
 */
public class LogTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogTest.class);

    @Test
    public void test1() {
        Map<String, String> map = new HashMap<>();
        map.put("result", "1");
        LOGGER.info("map : {}", map);
    }
}

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author guoqing.chen01@hand-china.com
 * @description
 * @Date Created in Administrator 2022/04/07 下午 09:08
 */
@Slf4j
public class MapTest {
    public static void main(String[] args) {
        Map<Object, Object> objectObjectHashMap = new ConcurrentHashMap<>(4);
//        Map<Object, Object> objectObjectHashMap = new HashMap<>(4);
        String s = null;
//        objectObjectHashMap.put(null,s + 123);
        objectObjectHashMap.put(null,null);
        objectObjectHashMap.put("null","null");
        objectObjectHashMap.put("1","1");
        objectObjectHashMap.put("123","123");

//        log.info(objectObjectHashMap.get("null").toString());
//        log.info(objectObjectHashMap.get(null+"123").toString());
        Object o = objectObjectHashMap.get(null);
        System.out.println(o);
    }
}

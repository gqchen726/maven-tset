package ga.tianyuge.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

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

    @Test
    public void test1() {
        List<String> list = new ArrayList<>();
//        list.add("1");
//        list.add("2");
//        list.add("3");
//        list.add("4");
//        list.add("5");

        Map<String, String> collect = list.stream().collect(Collectors.toMap(t -> t.toString(), t -> t, (v1, v2) -> v1));
        String s = collect.get("1");
        System.out.println(s);
    }

    @Test
    public void test2() {
        Map<String, String> map1 = new HashMap<>();
        map1.put("1", "1");
        map1.put("2", "2");
        map1.put("3", "3");
        Map<String, String> map2 = new HashMap<>(map1);
        map1.put("4", "4");
        map1.put("5", "5");
        System.out.println(map2);

    }
}

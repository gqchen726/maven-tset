package ga.tianyuge.test;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.ObjectUtils;
import org.junit.Test;

/**
 * description
 *
 * @author guoqing.chen01@hand-china.com 2022/10/21 10:32
 */
public class ConvertExcelTest {

    @Test
    public void jsonConvertExcel() {
        String json = "{\"result\": \"1\"}";
        JSONArray objects;
        JSONObject jsonObject = null;
        try {
            objects = JSONObject.parseArray(json);
            objects.forEach(System.out::println);
        } catch (JSONException e) {
            jsonObject = JSONObject.parseObject(json);
            System.out.println(jsonObject);
        }
    }
}

import java.util.ArrayList;
import java.util.List;

/**
 * @Author guoqing.chen01@hand-china.com
 * @description
 * @Date Created in Administrator 2022/06/10 10:34
 */
public class DescartesUtil {
    public static void main(String[] args) {
        List<List<String>> list = new ArrayList<>();
        List<String> listSub1 = new ArrayList<>();
        List<String> listSub2 = new ArrayList<>();
        List<String> listSub3 = new ArrayList<>();
        List<String> listSub4 = new ArrayList<>();
        listSub1.add("1");
        listSub1.add("2");
        listSub2.add("3");
        listSub2.add("4");
        listSub3.add("a");
        listSub3.add("b");
        listSub4.add("c");
        listSub4.add("d");
        list.add(listSub1);
        list.add(listSub2);
        list.add(listSub3);
        list.add(listSub4);
        List<List<String>> result = new ArrayList<>();
        descartes(list, result, 0, new ArrayList<>());
        // System.out.println(JSON.toJSONString(result));
    }
    /**
     * Created on 2014年4月27日
     * <p>
     * Discription:笛卡尔乘积算法
     * 把一个List{[1,2],[3,4],[a,b]}转化成List{[1,3,a],[1,3,b],[1,4
     * ,a],[1,4,b],[2,3,a],[2,3,b],[2,4,a],[2,4,b]}数组输出
     * </p>
     *
     * @param dimvalue 原List
     * @param result 通过乘积转化后的数组
     * @param layer 中间参数
     * @param curList 中间参数
     */
    private static void descartes(List<List<String>> dimvalue,
                                  List<List<String>> result, int layer, List<String> curList) {
        if (layer < dimvalue.size() - 1) {
            if (dimvalue.get(layer).size() == 0) {
                DescartesUtil.descartes(dimvalue, result, layer + 1, curList);
            } else {
                for (int i = 0; i < dimvalue.get(layer).size(); i++) {
                    List<String> list = new ArrayList<String>(curList);
                    list.add(dimvalue.get(layer).get(i));
                    DescartesUtil.descartes(dimvalue, result, layer + 1, list);
                }
            }
        } else if (layer == dimvalue.size() - 1) {
            if (dimvalue.get(layer).size() == 0) {
                result.add(curList);
            } else {
                for (int i = 0; i < dimvalue.get(layer).size(); i++) {
                    List<String> list = new ArrayList<>(curList);
                    list.add(dimvalue.get(layer).get(i));
                    result.add(list);
                }
            }
        }
    }
}

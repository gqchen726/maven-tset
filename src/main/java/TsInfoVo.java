import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Ts信息值对象
 *
 * @author chunlin.qi@hand-china.com
 * @version 1.0
 * @description
 * @date 2021/12/28
 */
@Data
public class TsInfoVo implements Serializable {
    /**
     * 部门总人数
     */
    private Integer totalEmployee = 104;
    /**
     * 缺勤人数
     */
    private Integer absentEmployee = 0;

    /**
     * 缺勤名单
     */
    private List<Map> absentDetailList;
}

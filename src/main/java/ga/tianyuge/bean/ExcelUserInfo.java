package ga.tianyuge.bean;

import lombok.Data;

/**
 * 用户数据(excel)
 *
 * @author chunlin.qi@hand-china.com
 * @version 1.0
 * @description
 * @date 2022/1/4
 */
@Data
public class ExcelUserInfo {
    /**
     * 工号
     */
    private String userCode;

    /**
     * 姓名
     */
    private String userName;

    /**
     * 手机号
     */
    private String userPhone;
}

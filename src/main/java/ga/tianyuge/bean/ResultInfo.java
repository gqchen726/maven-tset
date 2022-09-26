package ga.tianyuge.bean;

/**
 * ts审批统计结果
 * @author: guoqing.chen01@hand-china.com 2022-01-28 16:22
 **/

public class ResultInfo {
    private Integer id;
    private String PMId;
    private String PMName;
    private Integer NoApprovalDays;

    public ResultInfo() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPMId() {
        return PMId;
    }

    public void setPMId(String PMId) {
        int length = PMId.length();
        if (length == 3) {
            PMId = PMId + "  ";
        }
        if (length == 4) {
            PMId = PMId + " ";
        }

        this.PMId = PMId;
    }

    public String getPMName() {
        return PMName;
    }

    public void setPMName(String PMName) {
        int length = PMName.length();
        if (length == 2) {
            PMName = PMName + "  ";
        }
        this.PMName = PMName;
    }

    public Integer getNoApprovalDays() {
        return NoApprovalDays;
    }

    public void setNoApprovalDays(Integer noApprovalDays) {
        NoApprovalDays = noApprovalDays;
    }

    @Override
    public String toString() {
        return " " + id + "\t" + "  " + PMId + "\t\t" + "  " + PMName + "\t\t" + "  " + NoApprovalDays + "\t";
    }
}

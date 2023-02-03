package ga.tianyuge.bean;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 简要的员工信息
 * @author: guoqing.chen01@hand-china.com 2022-01-28 16:29
 **/

public class Employee {
    private Long employeeId;
    private String employeeCode;
    private String name;
    private String approvalStatus;
    private String approverCode;
    private String approverName;
    private BigDecimal price;

    private Map<String, String> flex;

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Map<String, String> getFlex() {
        return flex;
    }

    public void setFlex(Map<String, String> flex) {
        this.flex = flex;
    }

    public Employee() {
    }

    public Employee(String employeeCode, String name) {
        this.employeeCode = employeeCode;
        this.name = name;
    }

    public Employee(Long employeeId, String employeeCode, String name) {
        this.employeeId = employeeId;
        this.employeeCode = employeeCode;
        this.name = name;
    }

    public Employee(String employeeCode, String name, Map<String, String> flex) {
        this.employeeCode = employeeCode;
        this.name = name;
        this.flex = flex;
    }

    public Employee(Long employeeId, String employeeCode, String name, Map<String, String> flex) {
        this.employeeId = employeeId;
        this.employeeCode = employeeCode;
        this.name = name;
        this.flex = flex;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public String getApproverCode() {
        return approverCode;
    }

    public void setApproverCode(String approverCode) {
        this.approverCode = approverCode;
    }

    public String getApproverName() {
        return approverName;
    }

    public void setApproverName(String approverName) {
        this.approverName = approverName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}

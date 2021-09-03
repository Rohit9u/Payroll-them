package model;

public class ListDepartment {
    private String DepartmentName;
    private String DepartmentId;
    private String userid;
    public ListDepartment(){

    }
    public ListDepartment(String name,String id,String uids){
        this.DepartmentName=name;
        this.DepartmentId=id;
        this.userid=uids;
    }

    public String getDepartmentName() {
        return DepartmentName;
    }

    public void setDepartmentName(String departmentName) {
        DepartmentName = departmentName;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getDepartmentId() {
        return DepartmentId;
    }

    public void setDepartmentId(String departmentId) {
        DepartmentId = departmentId;
    }
}

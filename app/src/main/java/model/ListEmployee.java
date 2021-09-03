package model;

public class ListEmployee {
    private String name;
//    private String age;
//    private String Phoneno;
//    private String address;
//    private String Department;
//    private String loan;
    //private String fund;
//    private String email;
//    private String gender;
    private String employeesId;
//    private String userid;
//    private String accountno;
//    private String pfundrate;
//    private String joiningdate;


    public ListEmployee() {
    }

    public ListEmployee(String name, String employeesId) {
        this.name = name;

        this.employeesId = employeesId;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }




    public String getEmployeesId() {
        return employeesId;
    }

    public void setEmployeesId(String employeesId) {
        this.employeesId = employeesId;
    }


}

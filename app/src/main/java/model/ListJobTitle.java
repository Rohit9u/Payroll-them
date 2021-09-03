package model;

import com.google.firebase.firestore.auth.User;

public class ListJobTitle {
    private String Job_Title;
    private String Job_Id;
    private String Job_Salary;
    private String Department_Name;
    private String Department_Id;
    private String User_id;

    public String getDepartment_Name() {
        return Department_Name;
    }

    public void setDepartment_Name(String department_Name) {
        Department_Name = department_Name;
    }

    public String getUser_id() {
        return User_id;
    }

    public void setUser_id(String user_id) {
        User_id = user_id;
    }

    public String getDepartment_Id() {
        return Department_Id;
    }

    public void setDepartment_Id(String department_Id) {
        Department_Id = department_Id;
    }

    public ListJobTitle(String job_Title, String job_Id, String job_Salary, String department_Name, String department_Id,String userid) {
        Job_Title = job_Title;
        Job_Id = job_Id;
        User_id=userid;
        Job_Salary = job_Salary;
        Department_Name = department_Name;
        Department_Id = department_Id;
    }

    public ListJobTitle() {
    }

    public String getJob_Title() {
        return Job_Title;
    }

    public void setJob_Title(String job_Title) {
        Job_Title = job_Title;
    }

    public String getJob_Id() {
        return Job_Id;
    }

    public void setJob_Id(String job_Id) {
        Job_Id = job_Id;
    }

    public String getJob_Salary() {
        return Job_Salary;
    }

    public void setJob_Salary(String job_Salary) {
        Job_Salary = job_Salary;
    }
}

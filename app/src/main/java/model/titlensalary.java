package model;

public class titlensalary {
    private String JobTitle;
    private String Salary;

    public titlensalary() {
    }

    public titlensalary(String jobTitle, String salary) {
        JobTitle = jobTitle;
        Salary = salary;
    }

    public String getJobTitle() {
        return JobTitle;
    }

    public void setJobTitle(String jobTitle) {
        JobTitle = jobTitle;
    }

    public String getSalary() {
        return Salary;
    }

    public void setSalary(String salary) {
        Salary = salary;
    }
}

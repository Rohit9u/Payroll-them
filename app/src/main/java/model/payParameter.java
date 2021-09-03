package model;

public class payParameter {
    private String employeeId;
    private String year;
    private String month;
    private String absent;
    private String seasonalBonus;
    private String otherBonus;
    private String overTime;
    private String userid;
    private String otherDeduction;
//    private String employeeName;
//    private String datePaid;
//    private String taxDeduction;
//    private String salaryPaid;
//    private String totalDeduction;
//    private String pfCut;
//    private String urlImage;

    public payParameter() {}

    public payParameter(String employeeId, String year, String month, String absent, String seasonalBonus, String otherBonus, String overTime, String userid, String otherDeduction) {
        this.employeeId = employeeId;
        this.year = year;
        this.month = month;
        this.absent = absent;
        this.seasonalBonus = seasonalBonus;
        this.otherBonus = otherBonus;
        this.overTime = overTime;
        this.userid = userid;
        this.otherDeduction = otherDeduction;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getAbsent() {
        return absent;
    }

    public void setAbsent(String absent) {
        this.absent = absent;
    }

    public String getOtherBonus() {
        return otherBonus;
    }

    public void setOtherBonus(String otherBonus) {
        this.otherBonus = otherBonus;
    }


    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getSeasonalBonus() {
        return seasonalBonus;
    }

    public void setSeasonalBonus(String seasonalBonus) {
        this.seasonalBonus = seasonalBonus;
    }

    public String getOverTime() {
        return overTime;
    }

    public void setOverTime(String overTime) {
        this.overTime = overTime;
    }

    public String getOtherDeduction() {
        return otherDeduction;
    }

    public void setOtherDeduction(String otherDeduction) {
        this.otherDeduction = otherDeduction;
    }
}


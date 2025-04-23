package Entity;

import java.util.Date;

public class Employee {
    private int employeeId;
    private String employeeName;
    private Date startDate;
    private String phone;
    private String position;
    private double salary;
    private String gender;
    
    public Employee() {
    }
    
    public Employee(int employeeId, String employeeName, Date startDate, String phone, String position, double salary, String gender) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.startDate = startDate;
        this.phone = phone;
        this.position = position;
        this.salary = salary;
        this.gender = gender;
    }
    
    public int getEmployeeId() {
        return employeeId;
    }
    
    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }
    
    public String getEmployeeName() {
        return employeeName;
    }
    
    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }
    
    public Date getStartDate() {
        return startDate;
    }
    
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getPosition() {
        return position;
    }
    
    public void setPosition(String position) {
        this.position = position;
    }
    
    public double getSalary() {
        return salary;
    }
    
    public void setSalary(double salary) {
        this.salary = salary;
    }
    
    public String getGender() {
        return gender;
    }
    
    public void setGender(String gender) {
        this.gender = gender;
    }
    
    @Override
    public String toString() {
        return employeeName;
    }
}
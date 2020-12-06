package com.example.employeeapp;

public class User {
    public String fullName;
    public String email;
    public String password;
    public String district;
    public String department;

    public User(String fName, String emailP, String pWord, String districtP, String departmentP){
        this.fullName = fName;
        this.email = emailP;
        this.password = pWord;
        this.district = districtP;
        this.department = departmentP;
    }
}

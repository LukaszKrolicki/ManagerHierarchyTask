package com.app.keepittask.models;

import java.util.ArrayList;
import java.util.List;

public class Employee {
    private String email;
    private List<Employee> directReports;

    public Employee(String email) {
        this.email = email;
        this.directReports = new ArrayList<>();
    }

    public void addDirectReport(Employee employee) {
        this.directReports.add(employee);
    }
}


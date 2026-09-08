package com.example.salary_calculator.calc;

public class SalaryConfig {
    private final double monthlySalary;   // 月薪，元
    private final double workDaysPerMonth; // 如 21.75
    private final double hoursPerDay;      // 如 8.0


    public SalaryConfig(double monthlySalary, double workDaysPerMonth, double hoursPerDay) {
        this.monthlySalary = monthlySalary;
        this.workDaysPerMonth = workDaysPerMonth;
        this.hoursPerDay = hoursPerDay;
    }

    public double getMonthlySalary(){
        return monthlySalary;
    }
    public double getWorkDaysPerMonth(){
        return workDaysPerMonth;
    }

    public double getHoursPerDay(){
        return hoursPerDay;
    }
}

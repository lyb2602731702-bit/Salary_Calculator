package com.example.salary_calculator.calc;

public class SalaryConfig {
    private final double monthlySalary;   // 月薪，元
    private final double workDaysPerMonth; // 如 21.75
    private final double hoursPerDay;      // 如 8.0

    /**
     * 构造薪资配置
     * @param monthlySalary 月薪，单位元
     * @param workDaysPerMonth 每月计薪工作日
     * @param hoursPerDay 每日工作小时
     */
    public SalaryConfig(double monthlySalary, double workDaysPerMonth, double hoursPerDay) {
        this.monthlySalary = monthlySalary;
        this.workDaysPerMonth = workDaysPerMonth;
        this.hoursPerDay = hoursPerDay;
    }

    /** 月薪*/
    public double getMonthlySalary(){
        return monthlySalary;
    }
    /** 月工作天数*/
    public double getWorkDaysPerMonth(){
        return workDaysPerMonth;
    }
    /** 日工作小时数*/
    public double getHoursPerDay(){
        return hoursPerDay;
    }
}

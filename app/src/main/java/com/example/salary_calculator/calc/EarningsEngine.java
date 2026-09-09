package com.example.salary_calculator.calc;

public class EarningsEngine {
    private final SalaryConfig config;

    public EarningsEngine(SalaryConfig config){
        this.config = config;
    }
    /** 有多少秒 */
    public double getWorkSecondsPerMonth(){
        double days = config.getWorkDaysPerMonth();
        double hours = config.getHoursPerDay();
        return days * hours * 3600L;
    }

    /** 每秒值多少钱 */
    private double getSalaryPerSecond(){
        double monthSalary = config.getMonthlySalary();
        double totalSeconds = getWorkSecondsPerMonth();
        return monthSalary / totalSeconds;

    }

    /** 已工作 seconds 秒，赚了多少钱 */
    public double getEarnedAmount(long workedSeconds) {
        if (workedSeconds<0){
            return 0;
        }
        double  playSecond = getSalaryPerSecond();
        return workedSeconds * playSecond;
    }
}

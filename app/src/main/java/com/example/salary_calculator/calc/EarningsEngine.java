package com.example.salary_calculator.calc;

public class EarningsEngine {
    private final SalaryConfig config;

    public EarningsEngine(SalaryConfig config){
        this.config = config;
    }
    /** 一个月有多少工作秒 */
    public double getWorkSecondsPerMonth(){
        double days = config.getWorkDaysPerMonth();
        double hours = config.getHoursPerDay();
        double totalSeconds = days * hours * 3600L;
        return totalSeconds;
    }

    /** 每秒值多少钱 */
    private double getSalaryPerSecond(){

    }

    /** 已工作 seconds 秒，赚了多少钱 */
    public double getEarnedAmount(long workedSeconds) {
        // TODO: 秒数 × 每秒薪资；workedSeconds < 0 时你自己定规则（返回 0 或抛异常）
    }
}

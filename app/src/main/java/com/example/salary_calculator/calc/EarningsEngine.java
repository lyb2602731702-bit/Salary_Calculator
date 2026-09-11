package com.example.salary_calculator.calc;

import java.util.Calendar;

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

    /** 已工作 赚了多少钱 */
    public double getEarnedAmount(long workedSeconds) {
        if (workedSeconds<0){
            return 0;
        }
        double  playSecond = getSalaryPerSecond();
        return workedSeconds * playSecond;
    }

    /** 获取今天工作了多少秒*/
    public long getTodayWorkedSeconds(Calendar now){
        int hour = now.get(Calendar.HOUR_OF_DAY);
        int minute = now.get(Calendar.MINUTE);
        int second = now.get(Calendar.SECOND);
        //当前时刻距离今天0点的总秒
        long currentSec = hour * 3600 + minute * 60 + second;

        //固定基准
        long startWork = 30600;   // 08:30 上班
        long lunchStart = 43200;  // 12:00 午休开始
        long lunchEnd = 48600;    // 13:30 午休结束
        long endWork = 64800;     // 18:00 下班

        if(currentSec < startWork){
            return 0;
        }
        if(currentSec >= endWork){
            return 28800;
        }
        if(currentSec <= lunchStart){
            return currentSec - startWork;
        }
        if(currentSec <lunchEnd){
            return lunchStart - startWork;
        }
        long morningWork = lunchStart - startWork;
        long afternoonWork = currentSec - lunchEnd;
        return morningWork + afternoonWork;
    }
}

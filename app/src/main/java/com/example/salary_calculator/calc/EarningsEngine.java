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
        WorkDayRule rule = config.getWorkDayRule();
        long startWork = rule.getStartSecond();
        long lunchStart = rule.getLunchStartSecond();
        long lunchEnd = rule.getLunchEndSecond();
        long endWork = rule.getEndSecond();

        if(currentSec < startWork){
            return 0;
        }
        if(currentSec >= endWork){
            return (lunchStart - startWork) + (endWork - lunchEnd);
        }
        if(currentSec <= lunchStart){
            return currentSec - startWork;
        }
        if(currentSec <lunchEnd){
            return lunchStart - startWork;
        }
        //上午工作时间
        long morningWork = lunchStart - startWork;
        //下午工作时间
        long afternoonWork = currentSec - lunchEnd;
        return morningWork + afternoonWork;
    }
}

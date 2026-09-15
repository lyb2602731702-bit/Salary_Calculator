package com.example.salary_calculator.calc;

public class WorkDayRule {
    private final int startSecond;       // 上班，距 0 点的秒
    private final int lunchStartSecond;  // 午休开始
    private final int lunchEndSecond;    // 午休结束
    private final int endSecond;         // 下班

    public WorkDayRule(int startSecond, int lunchStartSecond, int lunchEndSecond, int endSecond) {
        this.startSecond = startSecond;
        this.lunchStartSecond = lunchStartSecond;
        this.lunchEndSecond = lunchEndSecond;
        this.endSecond = endSecond;
    }

    /** 上班时间*/
    public int getStartSecond(){
        return startSecond;
    }
    /** 午休时间开始*/
    public int getLunchStartSecond(){
        return lunchStartSecond;
    }
    /** 午休时间结束*/
    public int getLunchEndSecond(){
        return lunchEndSecond;
    }
    /** 下班时间*/
    public int getEndSecond(){
        return endSecond;
    }

    /** 方便：用「时、分」创建，避免每次手算 30600 */
    public static WorkDayRule of(int startH, int startM,
                                 int lunchStartH, int lunchStartM,
                                 int lunchEndH, int lunchEndM,
                                 int endH, int endM) {
        return new WorkDayRule(
                toSecond(startH, startM),
                toSecond(lunchStartH, lunchStartM),
                toSecond(lunchEndH, lunchEndM),
                toSecond(endH, endM)
        );
    }
    private static int toSecond(int hour, int minute) {
        return hour * 3600 + minute * 60;
    }
}

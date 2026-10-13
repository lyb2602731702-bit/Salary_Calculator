package com.example.salary_calculator.calc;

/**
 * 一天的作息规则（上班 / 午休 / 下班）。
 * <p>
 * 作用：把四个时间点整理成一份数据，交给 {@link SalaryConfig} 保存；
 * {@link EarningsEngine} 再从配置里读取，用来算「今天已工作多少秒」。
 * 时间统一存成「距离当天 0 点的秒数」，方便比较大小。
 * 例如 8:30 → 8*3600+30*60 = 30600。
 */
public class WorkDayRule {
    private final int startSecond;       // 上班，距 0 点的秒
    private final int lunchStartSecond;  // 午休开始
    private final int lunchEndSecond;    // 午休结束
    private final int endSecond;         // 下班

    /** 直接用「距 0 点的秒数」创建（一般更常用下面的 {@link #of}） */
    public WorkDayRule(int startSecond, int lunchStartSecond, int lunchEndSecond, int endSecond) {
        this.startSecond = startSecond;
        this.lunchStartSecond = lunchStartSecond;
        this.lunchEndSecond = lunchEndSecond;
        this.endSecond = endSecond;
    }

    /** 上班时刻（距 0 点的秒） */
    public int getStartSecond(){
        return startSecond;
    }
    /** 午休开始（距 0 点的秒） */
    public int getLunchStartSecond(){
        return lunchStartSecond;
    }
    /** 午休结束（距 0 点的秒） */
    public int getLunchEndSecond(){
        return lunchEndSecond;
    }
    /** 下班时刻（距 0 点的秒） */
    public int getEndSecond(){
        return endSecond;
    }

    /**
     * 用「时、分」创建一整套作息，避免自己手算 30600 这种秒数。
     * 参数顺序：上班 → 午休开始 → 午休结束 → 下班。
     * 例：of(8, 30, 12, 0, 13, 30, 18, 0)
     */
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

    /** 把时、分换算成距当天 0 点的总秒数 */
    private static int toSecond(int hour, int minute) {
        return hour * 3600 + minute * 60;
    }
}

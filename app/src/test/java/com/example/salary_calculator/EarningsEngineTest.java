package com.example.salary_calculator;

import static org.junit.Assert.assertEquals;

import com.example.salary_calculator.calc.EarningsEngine;
import com.example.salary_calculator.calc.SalaryConfig;

import org.junit.Test;

public class EarningsEngineTest {

    @Test
    public void workSecondsPerMonth_isCorrect() {
        SalaryConfig config = new SalaryConfig(21750, 21.75, 8);
        EarningsEngine engine = new EarningsEngine(config);
        //输出每月秒数
        double seconds_result = engine.getWorkSecondsPerMonth();
        double money_result = engine.getEarnedAmount(2);

        System.out.println("每月总秒数=" + seconds_result + ",目前赚到的钱=" + money_result);

        assertEquals(125.0, engine.getEarnedAmount(3600), 0.01);
        assertEquals(0.0, engine.getEarnedAmount(0), 0.001);
        assertEquals(626400, engine.getWorkSecondsPerMonth(), 0.001);
    }
}

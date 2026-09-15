package com.example.salary_calculator;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.salary_calculator.calc.EarningsEngine;
import com.example.salary_calculator.calc.SalaryConfig;
import com.example.salary_calculator.calc.WorkDayRule;
import com.example.salary_calculator.databinding.ActivityMainBinding;

import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private EarningsEngine engine;
    //工作时间
    private long workedSeconds = 0;
    private  final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        WorkDayRule rule = WorkDayRule.of(8,30,12,0,13,30,18,0);
        SalaryConfig config = new SalaryConfig(3000,25,8,rule);
        engine = new EarningsEngine(config);
        startTicking();
        init();
    }

    private void init(){
        binding.btnSettings.setOnClickListener(v ->{
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        });
    }

    /** 更新文字*/
    private void updateEarnedText(){
        double money = engine.getEarnedAmount(workedSeconds);
        String moneyText = String.format(Locale.CHINA,"¥%.2f",money);
        binding.tvEarned.setText(moneyText);
    }

    /** 文字循环*/
    private void startTicking(){
        handler.post(new Runnable() {
            @Override
            public void run() {
                //1.秒数 +1
                Calendar now = Calendar.getInstance();
                workedSeconds = engine.getTodayWorkedSeconds(now);
                //2.更新ui文字
                updateEarnedText();
                //3.执行1000ms，循环
                handler.postDelayed(this,1000);
            }
        });
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
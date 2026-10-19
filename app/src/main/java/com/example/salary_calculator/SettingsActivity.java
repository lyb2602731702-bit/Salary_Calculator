package com.example.salary_calculator;

import static android.app.ProgressDialog.show;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.salary_calculator.databinding.ActivitySettingsBinding;

public class SettingsActivity extends AppCompatActivity {
    private ActivitySettingsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        loadSettings();
        setupSaveButton();
        setupTimePicker(binding.tvStart);
        setupTimePicker(binding.tvLunchStart);
        setupTimePicker(binding.tvLunchEnd);
        setupTimePicker(binding.tvEnd);
    }

    private void loadSettings() {
        SharedPreferences sp = getSharedPreferences("salary_settings", MODE_PRIVATE);
        String start = sp.getString("start", "8:30");
        String lunchStart = sp.getString("lunch_start", "12:00");
        String lunchEnd = sp.getString("lunch_end", "13:30");
        String end = sp.getString("end", "18:00");
        String pay = sp.getString("pay", "8000");
        String work = sp.getString("work", "26");
        String hour = sp.getString("hour", "8");
        binding.tvStart.setText(start);
        binding.tvLunchStart.setText(lunchStart);
        binding.tvLunchEnd.setText(lunchEnd);
        binding.tvEnd.setText(end);
        binding.etMonthSalary.setText(pay);
        binding.etMonthWorking.setText(work);
        binding.etDayWorking.setText(hour);
    }

    private void setupTimePicker(TextView target){
        target.setOnClickListener(v -> {
            // 1) 先读当前显示的 "8:30"，拆成小时、分钟，当作滚轮初始位置
            String text = target.getText().toString().trim();
            String[] parts = text.split(":");
            int hour = Integer.parseInt(parts[0]);
            int minute = Integer.parseInt(parts[1]);
            // 2) 弹出系统时间选择器
            new android.app.TimePickerDialog(
                    this,

                    //(view,hourOfDay,minute1) -> {}：Lambda，时间选中回调
                    (view,hourOfDay,minute1) ->{

                        // 3) 用户选完：拼回 "8:30" 显示到 TextView
                        String newText = hourOfDay + ":" + String.format("%02d", minute1);

                        // 上面分钟用了补零；小时你也可以同样补零，先能跑即可
                        target.setText(newText);
                    },
                    hour,
                    minute,
                    true// true = 24 小时制
            ).show();
                }
        );
    }

    private void setupSaveButton() {
        SharedPreferences sp = getSharedPreferences("salary_settings", MODE_PRIVATE);
        binding.btnSave.setOnClickListener(v -> {
                    String start = binding.tvStart.getText().toString().trim();
                    String lunchStart = binding.tvLunchStart.getText().toString().trim();
                    String lunchEnd = binding.tvLunchEnd.getText().toString().trim();
                    String end = binding.tvEnd.getText().toString().trim();
                    String pay = binding.etMonthSalary.getText().toString().trim();
                    String work = binding.etMonthWorking.getText().toString().trim();
                    String hour = binding.etDayWorking.getText().toString().trim();

                    if (!isValidTime(start)
                            || !isValidTime(lunchStart)
                            || !isValidTime(lunchEnd)
                            || !isValidTime(end)
                    ) {
                        Toast.makeText(this, "时间格式请用 8:30", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    // 拿到SP编辑器，批量存入6组键值
                    sp.edit()
                            .putString("start", start)
                            .putString("lunch_start", lunchStart)
                            .putString("lunch_end", lunchEnd)
                            .putString("end", end)
                            .putString("pay", pay)
                            .putString("work", work)
                            .putString("hour",hour)
                            .apply();   // 异步写入，常用这个
                    finish();
                }
        );
    }

    private boolean isValidTime(String text) {
        String[] parts = text.split(":");
        if (parts.length != 2) return false;
        try {
            int h = Integer.parseInt(parts[0]);
            int m = Integer.parseInt(parts[1]);
            return h >= 0 && h <= 23 && m >= 0 && m <= 59;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
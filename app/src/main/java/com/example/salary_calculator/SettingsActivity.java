package com.example.salary_calculator;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.salary_calculator.calc.EarningsEngine;
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
    }

    private void loadSettings() {
        SharedPreferences sp = getSharedPreferences("salary_settings", MODE_PRIVATE);
        String start = sp.getString("start", "8:30");
        String lunchStart = sp.getString("lunch_start", "12:00");
        String lunchEnd = sp.getString("lunch_end", "13:30");
        String end = sp.getString("end", "18:00");
        binding.etStart.setText(start);
        binding.etLunchStart.setText(lunchStart);
        binding.etLunchEnd.setText(lunchEnd);
        binding.etEnd.setText(end);
    }

    private void setupSaveButton() {
        binding.btnSave.setOnClickListener(v -> {
                    String start = binding.etStart.getText().toString().trim();
                    String lunchStart = binding.etLunchStart.getText().toString().trim();
                    String lunchEnd = binding.etLunchEnd.getText().toString().trim();
                    String end = binding.etEnd.getText().toString().trim();

                    SharedPreferences sp = getSharedPreferences("salary_settings", MODE_PRIVATE);
                    sp.edit()
                            .putString("start", start)
                            .putString("lunch_start", lunchStart)
                            .putString("lunch_end", lunchEnd)
                            .putString("end", end)
                            .apply();   // 异步写入，常用这个
                    finish();
                }
        );
    }

    private boolean isValidTime(String text){
        String[] parts = text.split(":");
        if (parts.length != 2)return false;
        try{
            int h = Integer.parseInt(parts[0]);
            int m = Integer.parseInt(parts[1]);
            return h >= 0 && h<= 23 && m>=0 && m <= 59;
        }catch (NumberFormatException e){
            return false;
        }
    }


}
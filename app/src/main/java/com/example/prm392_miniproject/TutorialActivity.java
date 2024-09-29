package com.example.prm392_miniproject;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

public class TutorialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        // Nút "Quay lại" trên ActionBar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Xử lý sự kiện nút "Quay lại" trong layout
        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> {
            getOnBackPressedDispatcher().onBackPressed();
        });

        // Sử dụng OnBackPressedCallback để tuỳ chỉnh hành vi khi nhấn nút "Back"
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Xử lý tuỳ chỉnh khi nhấn nút back
                finish(); // Quay lại trang trước
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            getOnBackPressedDispatcher().onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}


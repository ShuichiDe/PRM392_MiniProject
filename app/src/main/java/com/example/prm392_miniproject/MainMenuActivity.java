package com.example.prm392_miniproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.example.prm392_miniproject.MainActivity;
import com.example.prm392_miniproject.R;
import com.example.prm392_miniproject.TutorialActivity;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Button playButton = findViewById(R.id.play_button);
        Button tutorialButton = findViewById(R.id.tutorial_button);

        // Xử lý sự kiện nhấn nút Vào chơi
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển đến Activity chơi chính (ví dụ: GameActivity)
                Intent intent = new Intent(MainMenuActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // Xử lý sự kiện nhấn nút Hướng dẫn
        tutorialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển đến Activity hướng dẫn (TutorialActivity)
                Intent intent = new Intent(MainMenuActivity.this, TutorialActivity.class);
                startActivity(intent);
            }
        });
    }
}

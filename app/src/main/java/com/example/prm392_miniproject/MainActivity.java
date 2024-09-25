package com.example.prm392_miniproject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private SeekBar horse1, horse2, horse3;
    private Button startButton;
    private Handler handler;
    private Runnable runnable;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        horse1 = findViewById(R.id.horse1);
        horse2 = findViewById(R.id.horse2);
        horse3 = findViewById(R.id.horse3);
        startButton = findViewById(R.id.startButton);
        handler = new Handler();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        startButton.setOnClickListener(v -> startRace());
    }

    private void startRace() {
        horse1.setProgress(0);
        horse2.setProgress(0);
        horse3.setProgress(0);

        // Reset the handler and runnable
        handler.removeCallbacks(runnable);
        runnable = new Runnable() {
            @Override
            public void run() {
                updateHorses();
                if (horse1.getProgress() < 100 && horse2.getProgress() < 100 && horse3.getProgress() < 100) {
                    handler.postDelayed(this, 100); // Update every 100 milliseconds
                } else {
                    determineWinner();
                }
            }
        };

        handler.post(runnable); // Start the race
    }

    private void updateHorses() {
        Random random = new Random();
        horse1.setProgress(horse1.getProgress() + random.nextInt(10));
        horse2.setProgress(horse2.getProgress() + random.nextInt(10));
        horse3.setProgress(horse3.getProgress() + random.nextInt(10));
    }

    private void determineWinner() {
        int progress1 = horse1.getProgress();
        int progress2 = horse2.getProgress();
        int progress3 = horse3.getProgress();

        if (progress1 >= 100) {
            showWinner("Horse 1 Wins!");
        } else if (progress2 >= 100) {
            showWinner("Horse 2 Wins!");
        } else if (progress3 >= 100) {
            showWinner("Horse 3 Wins!");
        } else {
            showWinner("It's a Tie!");
        }
    }

    private void showWinner(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        handler.removeCallbacks(runnable); // Stop the race
    }
}

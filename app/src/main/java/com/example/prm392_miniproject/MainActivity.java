package com.example.prm392_miniproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private SeekBar dog1, dog2, dog3;
    private Button startButton, placeBetButton;
    private EditText betAmountInput1, betAmountInput2, betAmountInput3;
    private TextView playerMoneyText;
    private int playerMoney = 100;
    private int betAmount1, betAmount2, betAmount3;
    private boolean hasPlacedBet = false;
    private Handler handler;
    private Runnable runnable;
    private Button btnBack;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dog1 = findViewById(R.id.dog1);
        dog2 = findViewById(R.id.dog2);
        dog3 = findViewById(R.id.dog3);
        startButton = findViewById(R.id.startButton);
        placeBetButton = findViewById(R.id.betButton);
        betAmountInput1 = findViewById(R.id.betAmountInput1);
        betAmountInput2 = findViewById(R.id.betAmountInput2);
        betAmountInput3 = findViewById(R.id.betAmountInput3);
        playerMoneyText = findViewById(R.id.playerMoneyText);

        handler = new Handler();


        if (getIntent().hasExtra("playerMoney")) {
            playerMoney = getIntent().getIntExtra("playerMoney", 100);  // Số tiền mặc định là 1000
        }

        updatePlayerMoneyText();

        placeBetButton.setOnClickListener(v -> placeBet());
        startButton.setOnClickListener(v -> startRace());


        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());
    }


    private void updatePlayerMoneyText() {
        playerMoneyText.setText("Số tiền của bạn: " + playerMoney);
    }


    private void placeBet() {

        String betAmountString1 = betAmountInput1.getText().toString();
        String betAmountString2 = betAmountInput2.getText().toString();
        String betAmountString3 = betAmountInput3.getText().toString();

        betAmount1 = betAmountString1.isEmpty() ? 0 : Integer.parseInt(betAmountString1);
        betAmount2 = betAmountString2.isEmpty() ? 0 : Integer.parseInt(betAmountString2);
        betAmount3 = betAmountString3.isEmpty() ? 0 : Integer.parseInt(betAmountString3);


        int totalBet = betAmount1 + betAmount2 + betAmount3;


        if (totalBet > playerMoney) {
            Toast.makeText(this, "Số tiền cược vượt quá số tiền bạn có!", Toast.LENGTH_SHORT).show();
            return;
        }


        if (totalBet == 0) {
            Toast.makeText(this, "Hãy đặt cược ít nhất một con chó!", Toast.LENGTH_SHORT).show();
            return;
        }


        hasPlacedBet = true;
        Toast.makeText(this, "Đặt cược thành công!", Toast.LENGTH_SHORT).show();
        startButton.setEnabled(true); // Kích hoạt nút bắt đầu đua
    }


    private void startRace() {
        if (!hasPlacedBet) {
            Toast.makeText(this, "Hãy đặt cược trước khi bắt đầu cuộc đua!", Toast.LENGTH_SHORT).show();
            return;
        }


        dog1.setProgress(0);
        dog2.setProgress(0);
        dog3.setProgress(0);

        handler.removeCallbacks(runnable);
        runnable = new Runnable() {
            @Override
            public void run() {
                updateHorses();
                if (dog1.getProgress() < 100 && dog2.getProgress() < 100 && dog3.getProgress() < 100) {
                    handler.postDelayed(this, 100);
                } else {
                    determineWinner();
                }
            }
        };

        handler.post(runnable);
    }


    private void updateHorses() {
        Random random = new Random();
        dog1.setProgress(dog1.getProgress() + random.nextInt(10));
        dog2.setProgress(dog2.getProgress() + random.nextInt(10));
        dog3.setProgress(dog3.getProgress() + random.nextInt(10));
    }


    private void determineWinner() {
        int progress1 = dog1.getProgress();
        int progress2 = dog2.getProgress();
        int progress3 = dog3.getProgress();

        int winningHorse;
        if (progress1 >= 100) {
            winningHorse = 1;
        } else if (progress2 >= 100) {
            winningHorse = 2;
        } else if (progress3 >= 100) {
            winningHorse = 3;
        } else {
            return;
        }


        playerMoney -= (betAmount1 + betAmount2 + betAmount3);

        boolean hasWon = false;


        if (winningHorse == 1 && betAmount1 > 0) {
            playerMoney += betAmount1 * 2;
            hasWon = true;
        } else if (winningHorse == 2 && betAmount2 > 0) {
            playerMoney += betAmount2 * 2;
            hasWon = true;
        } else if (winningHorse == 3 && betAmount3 > 0) {
            playerMoney += betAmount3 * 2;
            hasWon = true;
        }


        Intent intent = new Intent(MainActivity.this, ResultActivity.class);
        intent.putExtra("playerMoney", playerMoney);
        intent.putExtra("hasWon", hasWon);
        startActivity(intent);


        if (playerMoney <= 0) {
            startButton.setEnabled(false);
            placeBetButton.setEnabled(false);
        }

        hasPlacedBet = false;
        startButton.setEnabled(false);
    }


}

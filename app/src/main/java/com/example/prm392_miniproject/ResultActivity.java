package com.example.prm392_miniproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    private int playerMoney;
    private boolean hasWon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // Get data from Intent
        Intent intent = getIntent();
        playerMoney = intent.getIntExtra("playerMoney", 0);
        hasWon = intent.getBooleanExtra("hasWon", false);

        // Get references to the UI elements
        TextView resultTextView = findViewById(R.id.resultTextView);
        TextView playerMoneyTextView = findViewById(R.id.playerMoneyTextView);
        Button continueButton = findViewById(R.id.continueButton);
        Button mainMenuButton = findViewById(R.id.mainMenuButton);

        // Update UI based on the result
        if (hasWon) {
            resultTextView.setText("Chúc mừng! Bạn đã thắng cược.");
        } else {
            resultTextView.setText("Rất tiếc! Bạn đã thua cược.");
        }
        playerMoneyTextView.setText("Số tiền của bạn: " + playerMoney);

        // Handle Continue button click
        continueButton.setOnClickListener(v -> {
            // Quay lại MainActivity với số tiền hiện tại
            Intent continueIntent = new Intent(ResultActivity.this, MainActivity.class);
            continueIntent.putExtra("playerMoney", playerMoney);  // Truyền số tiền hiện tại
            startActivity(continueIntent);
            finish();  // Kết thúc ResultActivity
        });


        // Handle Main Menu button click
        mainMenuButton.setOnClickListener(v -> {
            // Go back to the main menu or end the game
            Intent mainMenuIntent = new Intent(ResultActivity.this, MainMenuActivity.class);
            startActivity(mainMenuIntent);
            finish();
        });
    }
}

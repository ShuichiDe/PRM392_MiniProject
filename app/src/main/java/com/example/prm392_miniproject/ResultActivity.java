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


        Intent intent = getIntent();
        playerMoney = intent.getIntExtra("playerMoney", 0);
        hasWon = intent.getBooleanExtra("hasWon", false);


        TextView resultTextView = findViewById(R.id.resultTextView);
        if (hasWon) {
            resultTextView.setText("Chúc mừng! Bạn đã thắng cược!");
        } else {
            resultTextView.setText("Rất tiếc! Bạn đã thua cược!");
        }


        TextView playerMoneyTextView = findViewById(R.id.playerMoneyTextView);
        playerMoneyTextView.setText("Số tiền của bạn: " + playerMoney);


        Button continueButton = findViewById(R.id.continueButton);
        continueButton.setOnClickListener(v -> {
            Intent mainIntent = new Intent(ResultActivity.this, MainActivity.class);
            mainIntent.putExtra("playerMoney", playerMoney);
            startActivity(mainIntent);
            finish();
        });


        Button mainMenuButton = findViewById(R.id.mainMenuButton);
        mainMenuButton.setOnClickListener(v -> {
            Intent menuIntent = new Intent(ResultActivity.this, MainMenuActivity.class);
            menuIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(menuIntent);
            finish();
        });
    }
}

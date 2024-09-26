package com.example.prm392_miniproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    private MoneyManager moneyManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // Lấy dữ liệu từ BettingActivity
        Intent intent = getIntent();
        int betAmount = intent.getIntExtra("betAmount", 0);
        int selectedHorse = intent.getIntExtra("selectedHorse", 0);
        int playerMoney = intent.getIntExtra("playerMoney", 0);

        moneyManager = new MoneyManager(playerMoney);

        // Giả lập kết quả đua, ví dụ Chó 2 thắng
        int winningHorse = 2;

        TextView resultText = findViewById(R.id.resultText);
        if (winningHorse == selectedHorse) {
            moneyManager.addMoney(betAmount);
            resultText.setText("Bạn đã thắng! Số tiền hiện tại: " + moneyManager.getPlayerMoney());
        } else {
            moneyManager.subtractMoney(betAmount);
            resultText.setText("Bạn đã thua! Số tiền hiện tại: " + moneyManager.getPlayerMoney());
        }

        // Kiểm tra điều kiện game over
        if (moneyManager.isGameOver()) {
            resultText.setText(resultText.getText() + "\nGame Over! Bạn hết tiền.");
        }
    }
}

package com.example.prm392_miniproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BettingActivity extends AppCompatActivity {
    private MoneyManager moneyManager;
    private int betAmount;  // Số tiền đặt cược
    private int selectedHorse;  // Chó được chọn để đặt cược

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_betting);

        // Khởi tạo lớp quản lý tiền
        moneyManager = new MoneyManager(1000);  // Số tiền ban đầu là 1000

        TextView txtMoney = findViewById(R.id.txtMoney);
        txtMoney.setText("Số tiền: " + moneyManager.getPlayerMoney());

        Button betButton = findViewById(R.id.betButton);
        betButton.setOnClickListener(v -> {
            EditText edtBetAmount = findViewById(R.id.betAmount);
            betAmount = Integer.parseInt(edtBetAmount.getText().toString());

            // Validate tiền đặt cược
            if (betAmount > moneyManager.getPlayerMoney()) {
                Toast.makeText(this, "Số tiền đặt cược lớn hơn số tiền bạn có!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Chọn chó để đặt cược
            RadioGroup radioGroup = findViewById(R.id.radioGroup);
            int checkedId = radioGroup.getCheckedRadioButtonId();
            if (checkedId == R.id.rbHorse1) {
                selectedHorse = 1;
            } else if (checkedId == R.id.rbHorse2) {
                selectedHorse = 2;
            } else if (checkedId == R.id.rbHorse3) {
                selectedHorse = 3;
            }

            // Chuyển sang Activity kết quả sau khi đặt cược
            Intent intent = new Intent(BettingActivity.this, ResultActivity.class);
            intent.putExtra("betAmount", betAmount);
            intent.putExtra("selectedHorse", selectedHorse);
            intent.putExtra("playerMoney", moneyManager.getPlayerMoney());
            startActivity(intent);
        });
    }
}

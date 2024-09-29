package com.example.prm392_miniproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private SeekBar dog1, dog2, dog3;
    private Button startButton, placeBetButton;
    private TextView betAmountInput, playerMoneyText;
    private RadioGroup horseSelectionGroup;
    private int playerMoney = 100;
    private int betAmount;
    private int selectedHorse;
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
        betAmountInput = findViewById(R.id.betAmountInput);
        playerMoneyText = findViewById(R.id.playerMoneyText);
        horseSelectionGroup = findViewById(R.id.horseSelectionGroup);

        handler = new Handler();
        updatePlayerMoneyText();
        placeBetButton.setOnClickListener(v -> placeBet());
        startButton.setOnClickListener(v -> startRace());

        // Thêm nút "Quay lại"
        btnBack = findViewById(R.id.btnBack);

        // Xử lý sự kiện khi nhấn nút "Quay lại"
        btnBack.setOnClickListener(v -> {
            // Sử dụng OnBackPressedDispatcher để xử lý quay lại
            getOnBackPressedDispatcher().onBackPressed();
        });

        // Đăng ký callback cho hành vi khi nhấn nút "Back"
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Logic tùy chỉnh khi nhấn nút "Back"
                Toast.makeText(MainActivity.this, "Nút quay lại được nhấn", Toast.LENGTH_SHORT).show();
                finish(); // Quay lại hoặc kết thúc Activity
            }
        });

        if (getIntent().hasExtra("playerMoney")) {
            playerMoney = getIntent().getIntExtra("playerMoney", 100); // Giá trị mặc định là 100
        } else {
            playerMoney = 100;  // Số tiền mặc định nếu không có dữ liệu trước đó
        }
        updatePlayerMoneyText();

    }


    private void updatePlayerMoneyText() {
        playerMoneyText.setText("Số tiền của bạn: " + playerMoney);
    }

    private void placeBet() {
        String betAmountString = betAmountInput.getText().toString();
        if (betAmountString.isEmpty()) {
            Toast.makeText(this, "Hãy nhập số tiền cược!", Toast.LENGTH_SHORT).show();
            return;
        }

        betAmount = Integer.parseInt(betAmountString);
        if (betAmount > playerMoney) {
            Toast.makeText(this, "Số tiền cược vượt quá số tiền bạn có!", Toast.LENGTH_SHORT).show();
        } else if (betAmount <= 0) {
            Toast.makeText(this, "Số tiền cược phải lớn hơn 0!", Toast.LENGTH_SHORT).show();
        } else {
            int selectedHorseId = horseSelectionGroup.getCheckedRadioButtonId();
            if (selectedHorseId == -1) {
                Toast.makeText(this, "Hãy chọn một con chó để đặt cược!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (selectedHorseId == R.id.radio_horse1) {
                selectedHorse = 1;
            } else if (selectedHorseId == R.id.radio_horse2) {
                selectedHorse = 2;
            } else if (selectedHorseId == R.id.radio_horse3) {
                selectedHorse = 3;
            }

            hasPlacedBet = true;
            Toast.makeText(this, "Đặt cược thành công! Chó " + selectedHorse + " được chọn.", Toast.LENGTH_SHORT).show();
            startButton.setEnabled(true); // Kích hoạt nút bắt đầu đua
        }
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
            return; // Kết thúc hàm nếu không có chú chó nào đạt đến 100
        }

        boolean hasWon = (winningHorse == selectedHorse);  // Kiểm tra kết quả thắng/thua
        if (hasWon) {
            playerMoney += betAmount;  // Cộng tiền nếu thắng
        } else {
            playerMoney -= betAmount;  // Trừ tiền nếu thua
        }

        // Chuyển đến trang ResultActivity
        Intent intent = new Intent(MainActivity.this, ResultActivity.class);
        intent.putExtra("playerMoney", playerMoney);
        intent.putExtra("hasWon", hasWon);
        startActivity(intent);

        // Nếu người chơi hết tiền
        if (playerMoney <= 0) {
            startButton.setEnabled(false);
            placeBetButton.setEnabled(false);
        }

        hasPlacedBet = false;
        startButton.setEnabled(false);
    }




}

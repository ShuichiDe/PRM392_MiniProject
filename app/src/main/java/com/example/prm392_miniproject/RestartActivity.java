package com.example.prm392_miniproject;



import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class RestartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Button finishRaceButton = findViewById(R.id.finishRaceButton);

        // Finish the race and trigger the restart popup
        finishRaceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRestartDialog();
            }
        });
    }

    private void showRestartDialog() {
        // tao message neu nguoi choi muon quay lai
        new AlertDialog.Builder(this)
                .setTitle("Game Over")
                .setMessage("Do you want to restart the game?")
                .setPositiveButton("Restart", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // restart game (load current activity again)
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // thoat game (quay lai menu)
                        Intent mainIntent = new Intent(RestartActivity.this, MainActivity.class);
                        startActivity(mainIntent);
                        finish();
                    }
                })
                .setCancelable(false)
                .show();
    }
}


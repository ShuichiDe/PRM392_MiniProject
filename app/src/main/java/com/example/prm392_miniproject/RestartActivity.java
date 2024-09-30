package com.example.prm392_miniproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class RestartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Button finishRaceButton = findViewById(R.id.finishRaceButton);

        // Check if the button exists in the layout
        if (finishRaceButton == null) {
            Toast.makeText(this, "Button not found. Check your layout file.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Finish the race and trigger the restart popup
        finishRaceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RestartActivity.this, "Button Clicked", Toast.LENGTH_SHORT).show();
                showRestartDialog();
            }
        });
    }

    private void showRestartDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Game Over")
                .setMessage("Do you want to restart the game?")
                .setPositiveButton("Restart", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Restart current activity
                        recreate();  // This restarts the activity
                    }
                })
                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Exit and go to the main menu
                        Intent mainIntent = new Intent(RestartActivity.this, MainMenuActivity.class);
                        startActivity(mainIntent);
                        finish();  // Finish current activity to clear it from back stack
                    }
                })
                .setCancelable(true)  // Dialog can be dismissed by pressing back
                .show();
    }

    // Optional: Prevent users from accidentally going back during the dialog
    @Override
    public void onBackPressed() {
        // Handle if necessary or just call super
        super.onBackPressed();
    }
}

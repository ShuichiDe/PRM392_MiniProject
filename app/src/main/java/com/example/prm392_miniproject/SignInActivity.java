package com.example.prm392_miniproject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText txtUserName;
    private EditText txtPassWord;
    private Button btnSignin;
    private final String REQUIRE = "require";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        txtUserName = findViewById(R.id.userName);
        txtPassWord = findViewById(R.id.password);
        btnSignin = findViewById(R.id.signIn);
        btnSignin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.signIn) {
            signIn(txtUserName.getText().toString(), txtPassWord.getText().toString());
        }
    }

    private boolean validateInput(String user, String pass) {
        if (TextUtils.isEmpty(user)) {
            txtUserName.setError(REQUIRE);
            return false;
        }
        if (TextUtils.isEmpty(pass)) {
            txtPassWord.setError(REQUIRE);
            return false;
        }
        return true;
    }

    private void signIn(String user, String pass) {
        if (!validateInput(user, pass)) {
            return;
        }
        // Add authentication logic if needed

        Intent intent = new Intent(this, MainMenuActivity.class);
        startActivity(intent);
        finish();
    }
}
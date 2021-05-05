package edu.cuhk.csci3310.cupo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText loginEmailInput = findViewById(R.id.loginEmailInput);
        EditText loginPasswordInput = findViewById(R.id.loginPasswordInput);
        Button loginButton = findViewById(R.id.loginButton);
        TextView signUp = findViewById(R.id.signUp);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("cupo", loginEmailInput.getText().toString() + " " + loginPasswordInput.getText().toString());
            }
        });

        signUp.setTextColor(0xff651fff);
        Activity ctx = this;
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ctx, SignupActivity.class));
            }
        });
    }
}
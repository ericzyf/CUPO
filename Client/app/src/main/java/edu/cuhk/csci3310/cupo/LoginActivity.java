package edu.cuhk.csci3310.cupo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText loginEmailInput = findViewById(R.id.loginEmailInput);
        EditText loginPasswordInput = findViewById(R.id.loginPasswordInput);
        Button loginButton = findViewById(R.id.loginButton);
        TextView gotoSignup = findViewById(R.id.gotoSignup);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleLogin(loginEmailInput.getText().toString(), loginPasswordInput.getText().toString());
            }
        });

        gotoSignup.setTextColor(0xff651fff);
        gotoSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SignupActivity.class));
            }
        });
    }

    private void handleLogin(final String email, final String password) {
        Log.d("cupo", email + "," + password);

        if (email.length() == 0 || password.length() == 0) {
            Toast.makeText(getApplicationContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Backend.getInstance().auth(email, password, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("cupo", e.toString());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (response.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Welcome back, " + email, Toast.LENGTH_SHORT).show();

                            // parse response
                            try {
                                JSONObject userJson = new JSONObject(response.body().string());
                                MainActivity.USER = new Backend.User(
                                        userJson.getString("email"),
                                        userJson.getString("username"),
                                        userJson.getLong("timestamp")
                                );
                                MainActivity.USER.gender = userJson.getString("gender");
                                MainActivity.USER.phone = userJson.getString("phone");
                                MainActivity.USER.bio = userJson.getString("bio");
                            } catch (Exception e) {

                            }

                            // goto MainActivity
                            startActivity(
                                    new Intent(getApplicationContext(), MainActivity.class)
                                            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK)
                            );
                        } else {
                            Toast.makeText(getApplicationContext(), "Email or password error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
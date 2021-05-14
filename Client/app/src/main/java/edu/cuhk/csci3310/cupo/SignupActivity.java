package edu.cuhk.csci3310.cupo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        EditText signupEmailInput = findViewById(R.id.signupEmailInput);
        EditText signupPasswordInput = findViewById(R.id.signupPasswordInput);
        EditText signupPasswordInput2 = findViewById(R.id.signupPasswordInput2);
        EditText signupCode = findViewById(R.id.signupCode);
        Button signupButton = findViewById(R.id.signupButton);
        ProgressBar signupProgress = findViewById(R.id.signupProgress);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSignup(
                        signupEmailInput.getText().toString(),
                        signupPasswordInput.getText().toString(),
                        signupPasswordInput2.getText().toString(),
                        signupCode.getText().toString()
                );
            }
        });
        // hide progress bar onCreate
        signupProgress.setVisibility(View.GONE);

        Button v = findViewById(R.id.signupV);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = signupEmailInput.getText().toString();
                if (email.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Please fill in your email address", Toast.LENGTH_SHORT).show();
                    return;
                }

                Backend.getInstance().verificationPost(email, new Callback() {
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
                                    Toast.makeText(getApplicationContext(), "Verification code has been sent to your CUHK mailbox", Toast.LENGTH_SHORT).show();
                                    v.setEnabled(false);
                                } else {
                                    Toast.makeText(getApplicationContext(), "Please use CUHK Email", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
            }
        });
    }

    private void handleSignup(final String email, final String pwd, final String pwd2, final String code) {
        Log.d("cupo", email + "," + pwd + "," + pwd2 + "," + code);

        if (email.length() == 0 || pwd.length() == 0 || pwd2.length() == 0 || code.length() == 0) {
            Toast.makeText(getApplicationContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!pwd.equals(pwd2)) {
            Toast.makeText(getApplicationContext(), "Password and confirmation password mismatch", Toast.LENGTH_SHORT).show();
            return;
        }

        // hide sign up button
        final Button button = findViewById(R.id.signupButton);
        button.setVisibility(View.GONE);
        // show indeterminate progress bar
        final ProgressBar progress = findViewById(R.id.signupProgress);
        progress.setVisibility(View.VISIBLE);

        Backend.getInstance().createUser(email, pwd, code, new Callback() {
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
                            Toast.makeText(getApplicationContext(), "Account created successfully", Toast.LENGTH_SHORT).show();

                            // parse response
                            try {
                                JSONObject userJson = new JSONObject(response.body().string());
                                MainActivity.USER = new Backend.User(
                                        userJson.getString("email"),
                                        userJson.getString("username"),
                                        userJson.getLong("timestamp")
                                );
                            } catch (Exception e) {

                            }

                            // goto MainActivity
                            startActivity(
                                    new Intent(getApplicationContext(), MainActivity.class)
                                            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK)
                            );
                        } else {
                            Toast.makeText(getApplicationContext(), "Wrong verification code or account already exists", Toast.LENGTH_SHORT).show();
                        }

                        button.setVisibility(View.VISIBLE);
                        progress.setVisibility(View.GONE);
                    }
                });
            }
        });
    }
}
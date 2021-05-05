package edu.cuhk.csci3310.cupo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        getSupportActionBar().setTitle("Sign Up");

        EditText signupEmailInput = findViewById(R.id.signupEmailInput);
        EditText signupPasswordInput = findViewById(R.id.signupPasswordInput);
        EditText signupPasswordInput2 = findViewById(R.id.signupPasswordInput2);
        Button signupButton = findViewById(R.id.signupButton);
        ProgressBar signupProgress = findViewById(R.id.signupProgress);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSignup(
                        signupEmailInput.getText().toString(),
                        signupPasswordInput.getText().toString(),
                        signupPasswordInput2.getText().toString()
                );
            }
        });
        // hide progress bar onCreate
        signupProgress.setVisibility(View.GONE);
    }

    private void handleSignup(final String email, final String pwd, final String pwd2) {
        Log.d("cupo", email + "," + pwd + "," + pwd2);

        if (email.length() == 0 || pwd.length() == 0 || pwd2.length() == 0) {
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

        Backend.getInstance().createUser(email, pwd, new Callback() {
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
                        } else {
                            Toast.makeText(getApplicationContext(), "Account already exists", Toast.LENGTH_SHORT).show();
                        }

                        button.setVisibility(View.VISIBLE);
                        progress.setVisibility(View.GONE);
                    }
                });
            }
        });
    }
}
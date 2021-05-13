package edu.cuhk.csci3310.cupo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Timestamp;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ViewProfileActivity extends AppCompatActivity {

    private Backend.User user;
    private TextView viewEmail;
    private TextView viewUsername;
    private TextView viewTimestamp;
    private TextView viewGender;
    private TextView viewPhone;
    private TextView viewBio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        viewEmail = findViewById(R.id.viewEmail);
        viewUsername = findViewById(R.id.viewUsername);
        viewTimestamp = findViewById(R.id.viewTimestamp);
        viewGender = findViewById(R.id.viewGender);
        viewPhone = findViewById(R.id.viewPhone);
        viewBio = findViewById(R.id.viewBio);

        final String email = getIntent().getStringExtra("email");
        Backend.getInstance().userInfo(email, new Callback() {
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
                            try {
                                JSONObject res = new JSONObject(response.body().string());
                                user = new Backend.User(
                                        res.getString("email"),
                                        res.getString("username"),
                                        res.getLong("timestamp")
                                );
                                user.gender = res.getString("gender");
                                user.phone = res.getString("phone");
                                user.bio = res.getString("bio");
                            } catch (Exception e) {

                            }

                            // setText
                            viewEmail.setText(user.email);
                            viewUsername.setText(user.username);
                            viewTimestamp.setText(new Timestamp(user.getTimestamp()).toString());
                            viewGender.setText(user.gender);
                            viewPhone.setText(user.phone);
                            viewBio.setText(user.bio);
                        } else {
                            Toast.makeText(getApplicationContext(), "User not found", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
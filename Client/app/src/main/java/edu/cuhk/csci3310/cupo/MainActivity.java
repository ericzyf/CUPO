package edu.cuhk.csci3310.cupo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity {

    public static final String SHARED_PREFS = "edu.cuhk.csci3310.cupo.pref";
    public static final OkHttpClient httpClient = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set up SharedPreferences
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        prefs.edit()
                .putString("API_URL", "http://118.195.184.108:5000")
                .apply();

        startActivity(new Intent(this, LoginActivity.class));
    }
}
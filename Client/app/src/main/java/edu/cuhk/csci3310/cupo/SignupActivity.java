package edu.cuhk.csci3310.cupo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        getSupportActionBar().setTitle("Sign Up");

        EditText signupEmailInput = findViewById(R.id.signupEmailInput);
        EditText signupPasswordInput = findViewById(R.id.signupPasswordInput);
        EditText signupPasswordInput2 = findViewById(R.id.signupPasswordInput2);
        Button signup = findViewById(R.id.signupButton);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("cupo", signupEmailInput.getText().toString() + "," + signupPasswordInput.getText().toString() + "," + signupPasswordInput2.getText().toString());
            }
        });
    }
}
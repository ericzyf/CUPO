package edu.cuhk.csci3310.cupo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
                int res = handleSignup(
                        signupEmailInput.getText().toString(),
                        signupPasswordInput.getText().toString(),
                        signupPasswordInput2.getText().toString()
                );
                Log.d("cupo", "handleSignup: " + res);

                switch (res) {
                    case 1: {
                        Toast.makeText(getApplicationContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case 2: {
                        Toast.makeText(getApplicationContext(), "Password and confirmation password mismatch", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
            }
        });
    }

    /**
     * @param email
     * @param pwd
     * @param pwd2
     * @return
     * 0: success
     * 1: has incomplete fields
     * 2: pwd != pwd2
     */
    private int handleSignup(String email, String pwd, String pwd2) {
        // TODO
        Log.d("cupo", email + "," + pwd + "," + pwd2);
        if (email.length() == 0 || pwd.length() == 0 || pwd2.length() == 0) {
            return 1;
        }
        if (!pwd.equals(pwd2)) {
            return 2;
        }

        return 0;
    }
}
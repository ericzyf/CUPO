package edu.cuhk.csci3310.cupo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class CreatePostActivity extends AppCompatActivity {

    private Button createPostButton;
    private EditText postTitle;
    private EditText postContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        postTitle = findViewById(R.id.createPostTitle);
        postContent = findViewById(R.id.createPostContent);

        createPostButton = findViewById(R.id.createPostButton);
        createPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleCreatePost(
                        postTitle.getText().toString(),
                        postContent.getText().toString()
                );
            }
        });
    }

    private void handleCreatePost(final String title, final String content) {
        if (title.length() == 0 || content.length() == 0) {
            Toast.makeText(getApplicationContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Backend.getInstance().createPost(MainActivity.USER.email, title, content, new Callback() {
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
                            Toast.makeText(getApplicationContext(), "Post created successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Failed to create post. Please try again later.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
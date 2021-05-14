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

public class ReplyPostActivity extends AppCompatActivity {

    private long postId;
    private Button submitReplyButton;
    private EditText replyContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply_post);

        postId = getIntent().getLongExtra("postId", -1);
        submitReplyButton = findViewById(R.id.submitReplyButton);
        replyContent = findViewById(R.id.replyContent);

        submitReplyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String content = replyContent.getText().toString();
                Backend.getInstance().createPostReply(postId, MainActivity.USER.email, content, new Callback() {
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
                                    Toast.makeText(getApplicationContext(), "Reply successfully", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Failed to reply to this post", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
            }
        });
    }
}
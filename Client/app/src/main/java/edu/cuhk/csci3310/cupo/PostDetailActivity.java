package edu.cuhk.csci3310.cupo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class PostDetailActivity extends AppCompatActivity {

    private Backend.Post post;
    private TextView postDetailTitle;
    private TextView postDetailUsername;
    private TextView postDetailTime;
    private TextView postDetailContent;
    private Button replyButton;

    private RecyclerView repliesRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        Intent intent = getIntent();
        post = intent.getParcelableExtra("post");

        postDetailTitle = findViewById(R.id.postDetailTitle);
        postDetailUsername = findViewById(R.id.postDetailUsername);
        postDetailTime = findViewById(R.id.postDetailTime);
        postDetailContent = findViewById(R.id.postDetailContent);
        replyButton = findViewById(R.id.replyButton);
        repliesRecyclerView = findViewById(R.id.repliesRecyclerView);

        replyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ReplyPostActivity.class);
                intent.putExtra("post", post);
                startActivity(intent);
            }
        });

        postDetailTitle.setText(post.getTitle());
        postDetailUsername.setText("@" + post.getUsername());
        postDetailTime.setText(new Timestamp(post.getTimestamp()).toString());
        postDetailContent.setText(post.getContent());

        postDetailUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ViewProfileActivity.class);
                intent.putExtra("email", post.getEmail());
                startActivity(intent);
            }
        });

        Backend.getInstance().getPostReplies(post.getId(), new Callback() {
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
                            // get post replies
                            List<Backend.PostReply> replies = new ArrayList<>();
                            try {
                                JSONArray res = new JSONArray(response.body().string());
                                for (int i = 0; i < res.length(); ++i) {
                                    JSONObject reply = res.getJSONObject(i);
                                    replies.add(new Backend.PostReply(
                                            reply.getString("email"),
                                            reply.getString("username"),
                                            reply.getString("content"),
                                            reply.getLong("timestamp")
                                    ));
                                }
                            } catch (Exception e) {

                            }

                            setupRecyclerView(replies);
                        }
                    }
                });
            }
        });
    }

    private void setupRecyclerView(final List<Backend.PostReply> replies) {
        repliesRecyclerView.setAdapter(new PostReplyAdapter(this, replies));
        repliesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onBackPressed() {
        startActivity(
                new Intent(this, MainActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK)
        );
    }
}
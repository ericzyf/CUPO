package edu.cuhk.csci3310.cupo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.sql.Timestamp;

public class PostDetailActivity extends AppCompatActivity {

    private Backend.Post post;
    private TextView postDetailTitle;
    private TextView postDetailUsername;
    private TextView postDetailTime;
    private TextView postDetailContent;

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
    }
}
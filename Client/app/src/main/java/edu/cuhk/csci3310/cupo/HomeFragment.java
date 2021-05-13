package edu.cuhk.csci3310.cupo;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class HomeFragment extends Fragment {

    private FloatingActionButton fab;
    private RecyclerView postsRecyclerView;
    private ProgressBar loadPostsProgress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fab = view.findViewById(R.id.fab);
        fab.setColorFilter(Color.WHITE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("cupo", "Create post fab clicked");
            }
        });

        postsRecyclerView = view.findViewById(R.id.postsRecyclerView);
        loadPostsProgress = view.findViewById(R.id.loadPostsProgress);

        Backend.getInstance().getAllPosts(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("cupo", e.toString());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (response.isSuccessful()) {
                            // get posts
                            List<Backend.Post> posts = new ArrayList<>();
                            try {
                                JSONArray res = new JSONArray(response.body().string());
                                for (int i = 0; i < res.length(); ++i) {
                                    JSONObject post = res.getJSONObject(i);
                                    posts.add(new Backend.Post(
                                            post.getLong("id"),
                                            post.getString("email"),
                                            post.getString("username"),
                                            post.getString("title"),
                                            post.getString("content"),
                                            post.getLong("timestamp")
                                    ));
                                }
                            } catch (Exception e) {

                            }

                            setupRecyclerView(posts);
                        }
                    }
                });
            }
        });
    }

    private void setupRecyclerView(final List<Backend.Post> posts) {
        loadPostsProgress.setVisibility(View.GONE);

        postsRecyclerView.setAdapter(new PostAdapter(getContext(), posts));
        postsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
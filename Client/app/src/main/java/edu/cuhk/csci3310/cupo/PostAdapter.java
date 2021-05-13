package edu.cuhk.csci3310.cupo;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Timestamp;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private Context context;
    private List<Backend.Post> posts;

    public PostAdapter(Context ctx, final List<Backend.Post> p) {
        context = ctx;
        posts = p;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.post_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Backend.Post post = posts.get(position);

        holder.title.setText(post.getTitle());
        holder.username.setText(post.getUsername());

        Timestamp ts = new Timestamp(post.getTimestamp());
        holder.time.setText(ts.toString());
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView title;
        public TextView username;
        public TextView time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.postTitle);
            username = itemView.findViewById(R.id.postUsername);
            time = itemView.findViewById(R.id.postTime);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            context.startActivity(new Intent(context, PostDetailActivity.class));
        }
    }
}

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

public class PostReplyAdapter extends RecyclerView.Adapter<PostReplyAdapter.ViewHolder> {

    private Context context;
    private List<Backend.PostReply> replies;

    public PostReplyAdapter(Context ctx, final List<Backend.PostReply> r) {
        context = ctx;
        replies = r;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.postreply_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindPostReply(replies.get(position));
    }

    @Override
    public int getItemCount() {
        return replies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView username;
        private TextView time;
        private TextView content;
        private Backend.PostReply reply;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.postReplyUsername);
            time = itemView.findViewById(R.id.postReplyTime);
            content = itemView.findViewById(R.id.postReplyContent);
            reply = null;
        }

        public void bindPostReply(final Backend.PostReply r) {
            reply = r;

            username.setText("@" + r.getUsername());
            time.setText(new Timestamp(r.getTimestamp()).toString());
            content.setText(r.getContent());

            username.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ViewProfileActivity.class);
                    intent.putExtra("email", r.getEmail());
                    context.startActivity(intent);
                }
            });
        }
    }

}

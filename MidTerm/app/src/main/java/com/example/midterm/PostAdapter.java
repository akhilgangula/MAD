package com.example.midterm;

import android.app.AlertDialog;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.midterm.response.AuthenticationResponse;
import com.example.midterm.response.PostPOJO;
import com.example.midterm.response.PostResponse;

import java.util.ArrayList;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    List<PostPOJO> list_posts;
    AuthenticationResponse auth;
    int page;
    public PostAdapter(List<PostPOJO> list_posts, AuthenticationResponse auth, int page, IDeletePost mListener) {
        this.mListener= mListener;
        this.page = page;
        this.list_posts = list_posts;
        this.auth = auth;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_card_view, parent, false);
        return new PostAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.postText.setText(list_posts.get(position).getPost_text());
        holder.createdBy.setText(list_posts.get(position).getCreated_by_name());
        holder.createdAt.setText(list_posts.get(position).getCreated_at());
        holder.deleteIcon.setVisibility(list_posts.get(position).getCreated_by_uid().equals(auth.getUser_id()) ? View.VISIBLE : View.INVISIBLE);
        holder.deleteIcon.setOnClickListener(view -> {
            AlertDialog.Builder builder =new AlertDialog.Builder(view.getContext());
            builder.setTitle(view.getContext().getString(R.string.warn))
                    .setMessage(view.getContext().getString(R.string.delete_msg))
                    .setPositiveButton(view.getContext().getString(R.string.ok),(dialogInterface,i)->{
                        Service.deletePost(auth, list_posts.get(position).getPost_id(), new Handler(message -> {
                            if(message.arg1 != 1){
                                Toast.makeText(view.getContext(), view.getContext().getString(R.string.delete_post_title), Toast.LENGTH_SHORT).show();
                            }
                            mListener.postDeleted();
                            return false;
                        }));
                        dialogInterface.dismiss();
                    });
            builder.create().show();

        });
    }

    @Override
    public int getItemCount() {
        return list_posts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView postText, createdBy, createdAt;
        public final ImageButton deleteIcon;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            createdAt = itemView.findViewById(R.id.post_at);
            createdBy = itemView.findViewById(R.id.post_user);
            postText = itemView.findViewById(R.id.post_text);
            deleteIcon = itemView.findViewById(R.id.delete_post);
        }
    }

    IDeletePost mListener;
    interface IDeletePost {
        void postDeleted();
    }
}

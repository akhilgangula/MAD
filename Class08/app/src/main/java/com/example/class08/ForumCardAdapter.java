package com.example.class08;

import android.app.AlertDialog;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Map;

public class ForumCardAdapter extends RecyclerView.Adapter<ForumCardAdapter.ViewHolder> {
    List<Forum> list_posts;
    String uuid;
    Map<String, String> userIdToName;
    public ForumCardAdapter(List<Forum> list_forum, Map<String, String> userIdToName, String uuid, IDeletePost mListener) {
        this.mListener= mListener;
        this.list_posts = list_forum;
        this.uuid = uuid;
        this.userIdToName = userIdToName;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.forum_card, parent, false);
        return new ForumCardAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(list_posts.get(position).title);
        holder.desc.setText(list_posts.get(position).desc);
        holder.user.setText(userIdToName.get(list_posts.get(position).uuid));
        holder.time.setText(list_posts.get(position).date);
        holder.deleteIcon.setVisibility(list_posts.get(position).uuid.equals(uuid) ? View.VISIBLE : View.INVISIBLE);
        holder.deleteIcon.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setTitle(view.getContext().getString(R.string.warn))
                    .setMessage(view.getContext().getString(R.string.delete_msg))
                    .setPositiveButton(view.getContext().getString(R.string.ok),(dialogInterface,i)->{
                        // TODO: delete a forum from fireStore
                        dialogInterface.dismiss();
                        mListener.postDeleted(list_posts.get(position));
                    });
            builder.create().show();

        });
    }

    @Override
    public int getItemCount() {
        return list_posts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView title, user, desc, time;
        public final ImageButton deleteIcon;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.card_title);
            desc = itemView.findViewById(R.id.card_desc);
            user = itemView.findViewById(R.id.card_user);
            time = itemView.findViewById(R.id.card_time);
            deleteIcon = itemView.findViewById(R.id.card_delete);
        }
    }

    IDeletePost mListener;
    interface IDeletePost {
        void postDeleted(Forum forum);
    }
}
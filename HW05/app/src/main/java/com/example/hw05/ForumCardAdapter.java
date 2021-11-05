package com.example.hw05;

import android.app.AlertDialog;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;

public class ForumCardAdapter extends RecyclerView.Adapter<ForumCardAdapter.ViewHolder> {
    List<Forum> list_posts;
    String uuid;
    Map<String, String> userIdToName;
    public ForumCardAdapter(List<Forum> list_forum, Map<String, String> userIdToName, String uuid, IForumCard mListener) {
        this.mListener= mListener;
        this.list_posts = list_forum;
        this.uuid = uuid;
        this.userIdToName = userIdToName;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.forum_card, parent, false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(list_posts.get(position).title);
        holder.desc.setText(list_posts.get(position).desc);
        holder.user.setText(userIdToName.get(list_posts.get(position).uuid));
        holder.time.setText(list_posts.get(position).date == null ? null : Util.getFormattedDate(list_posts.get(position).date));
        holder.deleteIcon.setVisibility(list_posts.get(position).uuid.equals(uuid) ? View.VISIBLE : View.INVISIBLE);
        holder.deleteIcon.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setTitle(view.getContext().getString(R.string.warn))
                    .setMessage(view.getContext().getString(R.string.delete_msg))
                    .setPositiveButton(view.getContext().getString(R.string.ok),(dialogInterface,i)->{
                        dialogInterface.dismiss();
                        mListener.forumDeleted(list_posts.get(position));
                    });
            builder.create().show();

        });
        Boolean liked = list_posts.get(position).likeMap.getOrDefault(uuid, false);
        holder.likeIcon.setImageResource( liked ? R.mipmap.like_icon: R.mipmap.dis_like_icon);
        holder.likeIcon.setOnClickListener(view -> mListener.forumLiked(list_posts.get(position).id, uuid, !liked));
        holder.likes.setText(Long.toString(list_posts.get(position).likeMap.values().stream().filter(value -> value).count()));
        holder.card.setOnClickListener(view -> mListener.forumClicked(list_posts.get(position)));
    }

    @Override
    public int getItemCount() {
        return list_posts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public final CardView card;
        public final TextView title, user, desc, time, likes;
        public final ImageButton deleteIcon, likeIcon;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.card_title);
            desc = itemView.findViewById(R.id.card_desc);
            user = itemView.findViewById(R.id.card_user);
            time = itemView.findViewById(R.id.card_time);
            likes = itemView.findViewById(R.id.card_likes);
            deleteIcon = itemView.findViewById(R.id.card_delete);
            likeIcon = itemView.findViewById(R.id.card_like);
            card = itemView.findViewById(R.id.card);
        }
    }

    IForumCard mListener;
    interface IForumCard {
        void forumDeleted(Forum forum);
        void forumClicked(Forum forum);
        void forumLiked(String documentId, String userId, boolean like);
    }
}
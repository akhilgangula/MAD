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

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;
import java.util.Map;

public class CommentCardAdapter extends RecyclerView.Adapter<CommentCardAdapter.ViewHolder>  {

    List<Comment> commentList;
    String currentUser;
    String docId;
    Map<String, String> userIdToName;
    public CommentCardAdapter(List<Comment> commentList, Map<String, String> userIdToName, String documentId, ICommentCard mListener) {
        this.mListener= mListener;
        this.commentList = commentList;
        this.currentUser = FirebaseAuth.getInstance().getUid();
        this.userIdToName = userIdToName;
        docId = documentId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_card, parent, false);
        return new CommentCardAdapter.ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.userName.setText(userIdToName.get(commentList.get(position).userId));
        holder.desc.setText(commentList.get(position).commentDesc);
        holder.timeStamp.setText(commentList.get(position).timeStamp == null ? null : Util.getFormattedDate(commentList.get(position).timeStamp));
        holder.deleteIcon.setVisibility(commentList.get(position).userId.equals(currentUser) ? View.VISIBLE : View.INVISIBLE);
        holder.deleteIcon.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setTitle(view.getContext().getString(R.string.warn))
                    .setMessage(view.getContext().getString(R.string.delete_comment_msg))
                    .setPositiveButton(view.getContext().getString(R.string.ok),(dialogInterface,i)->{
                        dialogInterface.dismiss();
                        mListener.commentDeleted(docId, commentList.get(position).id);
                    });
            builder.create().show();
        });
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView userName, desc, timeStamp;
        public final ImageButton deleteIcon;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.comment_user_name);
            desc = itemView.findViewById(R.id.comment_desc);
            timeStamp = itemView.findViewById(R.id.comment_timestamp);
            deleteIcon = itemView.findViewById(R.id.comment_delete);
        }
    }

    ICommentCard mListener;
    interface ICommentCard {
        void commentDeleted(String documentId, String commentId);
    }
}

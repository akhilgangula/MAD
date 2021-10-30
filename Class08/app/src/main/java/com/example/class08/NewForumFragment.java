package com.example.class08;

import android.app.AlertDialog;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.type.DateTime;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewForumFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewForumFragment extends Fragment {

    // TODO: Rename and change types of parameters
    private String uuid;

    public NewForumFragment(INewForum mListener, String uuid) {
        this.mListener = mListener;
        this.uuid = uuid;
    }

    public NewForumFragment() {
        // Required empty public constructor
    }

    public static NewForumFragment newInstance(INewForum mListener, String uuid) {
        NewForumFragment fragment = new NewForumFragment(mListener, uuid);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_forum, container, false);
        getActivity().setTitle(getString(R.string.title_new_forum));
        view.findViewById(R.id.new_forum_submit_btn).setOnClickListener(view1 -> {
            String title = ((EditText)view.findViewById(R.id.new_forum_title)).getText().toString();
            String desc = ((EditText)view.findViewById(R.id.new_forum_desc)).getText().toString();
            if(title.isEmpty()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(getString(R.string.error))
                        .setMessage(getString(R.string.error_title))
                        .setPositiveButton(getString(R.string.ok),(dialogInterface,i)->{
                            dialogInterface.dismiss();
                        });
                builder.create().show();
                return;
            }
            if(desc.isEmpty()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(getString(R.string.error))
                        .setMessage(getString(R.string.error_desc))
                        .setPositiveButton(getString(R.string.ok),(dialogInterface,i)->{
                            dialogInterface.dismiss();
                        });
                builder.create().show();
                return;
            }
            LocalDateTime myDateObj = LocalDateTime.now();
            DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT);
            String formattedDate = myDateObj.format(myFormatObj);
            FireStoreConnector
                    .getInstance()
                    .addForum(title, desc, uuid, formattedDate)
                    .addOnSuccessListener(documentReference -> Log.d(this.getClass().toString(), "DocumentSnapshot added with ID: " + documentReference.getId()))
                    .addOnFailureListener(e -> Log.w(this.getClass().toString(), "Error adding document", e));
            mListener.createNewForum(uuid);
        });
        view.findViewById(R.id.new_forum_cancel).setOnClickListener(view1 -> mListener.cancelNewForum(uuid));
        return view;
    }

    INewForum mListener;
    interface INewForum {
        void createNewForum(String uuid);
        void cancelNewForum(String uuid);
    }
}
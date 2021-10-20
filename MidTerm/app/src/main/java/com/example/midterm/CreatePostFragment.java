package com.example.midterm;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.example.midterm.response.AuthenticationFailedResponse;
import com.example.midterm.response.AuthenticationResponse;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreatePostFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreatePostFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    AuthenticationResponse auth;
    ICreatePost mListener;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CreatePostFragment() {
        // Required empty public constructor
    }

    public CreatePostFragment(ICreatePost mListener) {
        this.mListener = mListener;
    }

    public static CreatePostFragment newInstance(ICreatePost mListener, AuthenticationResponse authenticationResponse) {
        CreatePostFragment fragment = new CreatePostFragment(mListener);
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, authenticationResponse);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            auth = (AuthenticationResponse) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_post, container, false);
        getActivity().setTitle(getString(R.string.create_post_title));
        view.findViewById(R.id.create_post_btn).setOnClickListener(view1 -> {
            String post = ((EditText) view.findViewById(R.id.post_create_text)).getText().toString();
            if (!post.isEmpty()) {

                Service.createPost(auth, post, new Handler(message -> {
                    if (message.arg1 != 1) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setTitle(Constants.ERROR_TITLE)
                                .setMessage(((AuthenticationFailedResponse) message.obj).getMessage())
                                .setPositiveButton(getString(R.string.ok), (dialogInterface, i) -> {
                                    dialogInterface.dismiss();
                                });
                        builder.create().show();
                    }
                    mListener.onCreatePost(auth);
                    return false;
                }));
            }
        });
        view.findViewById(R.id.post_create_cancel).setOnClickListener(view1 -> {
            mListener.cancelCreatePost(auth);
        });
        return view;
    }

    interface ICreatePost {
        void onCreatePost(AuthenticationResponse response);
        void cancelCreatePost(AuthenticationResponse response);
    }
}
package com.example.hw05;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment {

    private IRegister mListener;
    private FirebaseAuth mAuth;

    public RegisterFragment(IRegister mListener) {
        this.mListener = mListener;
    }

    public RegisterFragment() {
        // Required empty public constructor
    }

    public static RegisterFragment newInstance(IRegister mListener) {
        RegisterFragment fragment = new RegisterFragment(mListener);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
    }

    private void createAccount(String email, String password, String name) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        FirebaseUser user = mAuth.getCurrentUser();
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setDisplayName(name).build();

                        user.updateProfile(profileUpdates)
                                .addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()) {
                                        FireStoreConnector
                                                .getInstance()
                                                .addUser(user.getUid(), user.getDisplayName())
                                                .addOnSuccessListener(documentReference -> Toast.makeText(getContext(), getString(R.string.login_success), Toast.LENGTH_SHORT).show())
                                                .addOnFailureListener(e -> Log.w("TAG", "Error adding document", e));
                                        mListener.onAccountCreation(user.getUid());
                                    } else {
                                        Log.e(this.getClass().toString(), "Failed while updating name" + task.getException().toString());
                                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                        builder.setTitle(getString(R.string.error))
                                                .setMessage(getString(R.string.error_user_creation))
                                                .setPositiveButton(getString(R.string.ok), (dialogInterface, i) -> {
                                                    dialogInterface.dismiss();
                                                });
                                        builder.create().show();
                                        return;
                                    }
                                });
                        ;
                    } else {
                        Log.e("createAccount", task.getException().toString());
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setTitle(getString(R.string.error))
                                .setMessage(getString(R.string.error_user_creation))
                                .setPositiveButton(getString(R.string.ok), (dialogInterface, i) -> {
                                    dialogInterface.dismiss();
                                });
                        builder.create().show();
                        return;
                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        getActivity().setTitle(getString(R.string.title_create));
        view.findViewById(R.id.register_submit).setOnClickListener(view1 -> {
            String email = ((EditText) view.findViewById(R.id.register_email)).getText().toString();
            String password = ((EditText) view.findViewById(R.id.register_password)).getText().toString();
            String name = ((EditText) view.findViewById(R.id.register_name)).getText().toString();
            if (name.isEmpty()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(getString(R.string.error))
                        .setMessage(getString(R.string.error_name))
                        .setPositiveButton(getString(R.string.ok), (dialogInterface, i) -> {
                            dialogInterface.dismiss();
                        });
                builder.create().show();
                return;
            }
            if (email.isEmpty()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(getString(R.string.error))
                        .setMessage(getString(R.string.error_username))
                        .setPositiveButton(getString(R.string.ok), (dialogInterface, i) -> {
                            dialogInterface.dismiss();
                        });
                builder.create().show();
                return;
            }
            if (password.isEmpty()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(getString(R.string.error))
                        .setMessage(getString(R.string.error_password))
                        .setPositiveButton(getString(R.string.ok), (dialogInterface, i) -> {
                            dialogInterface.dismiss();
                        });
                builder.create().show();
                return;
            }
            createAccount(email, password, name);
        });
        view.findViewById(R.id.register_cancel).setOnClickListener(view1 -> mListener.onCancelRegistration());
        return view;
    }

    interface IRegister {
        void onAccountCreation(String uuid);

        void onCancelRegistration();
    }
}
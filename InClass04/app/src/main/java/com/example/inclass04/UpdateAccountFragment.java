package com.example.inclass04;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UpdateAccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpdateAccountFragment extends Fragment {

    private static final String ACCOUNT = "param1";
    IUpdateListener mListener;
    private DataServices.Account account;

    public UpdateAccountFragment() {
        // Required empty public constructor
    }

    public static UpdateAccountFragment newInstance(DataServices.Account param1) {
        UpdateAccountFragment fragment = new UpdateAccountFragment();
        Bundle args = new Bundle();
        args.putSerializable(ACCOUNT, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            account = (DataServices.Account) getArguments().getSerializable(ACCOUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_update_account, container, false);
        view.findViewById(R.id.buttonUpdateSubmit).setOnClickListener(view1 -> {
            String newName = ((EditText) view.findViewById(R.id.updateNameTxtBox)).getText().toString();
            String newPassword = ((EditText) view.findViewById(R.id.updatePasswordTxtBox)).getText().toString();
            if (newName.isEmpty()) {
                Toast.makeText(getActivity(), getString(R.string.name_validation_error), Toast.LENGTH_SHORT).show();
                return;
            } else if (newPassword.isEmpty()) {
                Toast.makeText(getActivity(), getString(R.string.password_validation_error), Toast.LENGTH_SHORT).show();
                return;
            }
            DataServices.AccountRequestTask task = DataServices.update(account, newName, newPassword);
            if (task.isSuccessful()) {
                Toast.makeText(getActivity(), getString(R.string.update_success), Toast.LENGTH_SHORT).show();
                mListener.onUpdateSubmit(task.getAccount());
            } else {
                Toast.makeText(getContext(), task.getErrorMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        view.findViewById(R.id.buttonUpdateCancel).setOnClickListener(view1 -> mListener.onUpdateCancel());
        ((TextView) view.findViewById(R.id.emailLbl)).setText(account.getEmail());
        ((EditText) view.findViewById(R.id.updateNameTxtBox)).setText(account.getName());
        ((EditText) view.findViewById(R.id.updatePasswordTxtBox)).setText(account.getPassword());
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof IUpdateListener) {
            mListener = (IUpdateListener) context;
        } else {
            throw new RuntimeException(context.toString() + "must implement IRegisterListener");
        }
    }

    public interface IUpdateListener {
        void onUpdateSubmit(DataServices.Account account);
        void onUpdateCancel();
    }
}
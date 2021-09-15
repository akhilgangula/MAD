package com.example.inclass04;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class RegisterFragment extends Fragment {

    IRegisterListener mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        view.findViewById(R.id.register_submit_btn).setOnClickListener(view1 -> {
            String account_email = ((EditText) view.findViewById(R.id.register_email_field)).getText().toString();
            String account_password = ((EditText) view.findViewById(R.id.register_pass_field)).getText().toString();
            String account_name = ((EditText) view.findViewById(R.id.register_name_field)).getText().toString();
            if (account_name.isEmpty()) {
                Toast.makeText(getActivity(), getString(R.string.name_validation_error), Toast.LENGTH_SHORT).show();
                return;
            } else if (account_email.isEmpty()) {
                Toast.makeText(getActivity(), getString(R.string.email_validation_error), Toast.LENGTH_SHORT).show();
                return;
            } else if (account_password.isEmpty()) {
                Toast.makeText(getActivity(), getString(R.string.password_validation_error), Toast.LENGTH_SHORT).show();
                return;
            }
            DataServices.AccountRequestTask task = DataServices.register(account_name, account_email, account_password);

            if (task.isSuccessful()) {
                Toast.makeText(getActivity(), getString(R.string.creation_success), Toast.LENGTH_SHORT).show();
                mListener.onSubmit(task.getAccount());
            } else {
                Toast.makeText(getActivity(), task.getErrorMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        view.findViewById(R.id.register_cancel).setOnClickListener(view1 -> mListener.onCancel());
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof IRegisterListener) {
            mListener = (IRegisterListener) context;
        } else {
            throw new RuntimeException(context.toString() + "must implement IRegisterListener");
        }
    }

    public interface IRegisterListener {
        void onSubmit(DataServices.Account account);

        void onCancel();
    }
}
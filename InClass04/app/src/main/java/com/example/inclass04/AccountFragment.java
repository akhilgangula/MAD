package com.example.inclass04;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountFragment extends Fragment {

    private static final String ACCOUNT = "ACCOUNT";

    private DataServices.Account account;

    public AccountFragment() {
        // Required empty public constructor
    }

    public static AccountFragment newInstance(DataServices.Account account) {
        AccountFragment fragment = new AccountFragment();
        Bundle args = new Bundle();
        args.putSerializable(ACCOUNT, account);
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
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        ((TextView)view.findViewById(R.id.account_name)).setText(account.getName());
        view.findViewById(R.id.account_edit).setOnClickListener(view1 -> mListener.onEditProfile(account));
        view.findViewById(R.id.account_logout).setOnClickListener(view1 -> mListener.onLogOut());
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof IAccountListener) {
            mListener = (IAccountListener) context;
        } else {
            throw new RuntimeException(context.toString() + "must implement IAccountListener");
        }
    }

    public void updateAccount(DataServices.Account newAccount) {
        account = newAccount;
    }

    IAccountListener mListener;
    public interface IAccountListener {
        void onEditProfile(DataServices.Account account);
        void onLogOut();
    }
}
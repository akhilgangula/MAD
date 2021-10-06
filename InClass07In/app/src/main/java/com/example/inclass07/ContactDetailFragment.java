package com.example.inclass07;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.io.Serializable;

public class ContactDetailFragment extends Fragment {

    private static final String ARG_PARAM2 = "param2";
    private IDetailView mListener;
    private Contact contact;
    public ContactDetailFragment(IDetailView mListener) {
        this.mListener = mListener;
    }

    public ContactDetailFragment() {
        // Required empty public constructor
    }

    public static ContactDetailFragment newInstance(IDetailView mListener, Contact contact) {
        ContactDetailFragment fragment = new ContactDetailFragment(mListener);
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM2, (Serializable) contact);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            contact = (Contact) getArguments().getSerializable(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact_detail, container, false);
        getActivity().setTitle(Util.DETAIL_CONTACT_TITLE);
        ((TextView) view.findViewById(R.id.contact_detail_name)).setText(contact.getName());
        ((TextView) view.findViewById(R.id.contact_detail_email)).setText(contact.getEmail());
        ((TextView) view.findViewById(R.id.contact_detail_phone)).setText(contact.getPhone());
        ((TextView) view.findViewById(R.id.contact_detail_phone_type)).setText(contact.getPhoneType());
        ((TextView) view.findViewById(R.id.contact_detail_id)).setText(contact.getCid());

        view.findViewById(R.id.contact_detail_update).setOnClickListener(view1 -> {
            mListener.updateContactInit(contact);
        });
        view.findViewById(R.id.contact_detail_delete).setOnClickListener(view1 -> {
            Service.deleteContact(contact.getCid(), new Handler(message -> {
                int success = message.arg1;
                Toast.makeText(getContext(), success == 1 ? getString(R.string.delete_success) : getString(R.string.delete_failed), Toast.LENGTH_SHORT).show();
                mListener.onDeleteContact();
                return false;
            }));
        });
        return view;
    }

    interface IDetailView {
        void updateContactInit(Contact contact);
        void onDeleteContact();
    }
}
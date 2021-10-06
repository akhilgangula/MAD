package com.example.inclass07;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ContactListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContactListFragment extends Fragment {

    private List<Contact> contacts = new ArrayList<>();
    ContactAdapter adapter;

    public ContactListFragment(IContactDelete mListener) {
        this.mListener = mListener;
    }

    public ContactListFragment() {
        // Required empty public constructor
    }

    public static ContactListFragment newInstance(IContactDelete mListener) {
        ContactListFragment fragment = new ContactListFragment(mListener);
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact_list, container, false);
        getActivity().setTitle(Util.CONTACT_TITLE);
        Service.getContacts(new Handler(message -> {
            Bundle bundle= message.getData();
            List<Contact> data =(List<Contact>)bundle.getSerializable(Service.KEY);
            adapter.setContactList(data);
            adapter.notifyDataSetChanged();
            return false;
        }));
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ContactAdapter(contacts, getActivity(), mListener);
        recyclerView.setAdapter(adapter);
        view.findViewById(R.id.add_contact_btn).setOnClickListener(view1 -> {
            mListener.onContactAddition();
        });
        return view;
    }

    IContactDelete mListener;
    interface IContactDelete {
        void onContactAddition();
        void openContactDetail(Contact contact);
    }

}
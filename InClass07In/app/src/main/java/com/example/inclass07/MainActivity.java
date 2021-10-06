package com.example.inclass07;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements ContactListFragment.IContactDelete, NewContactFragment.INewContact, ContactDetailFragment.IDetailView, ContactUpdateFragment.IUpdateView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.containerView, new ContactListFragment(this), Util.FRAGMENT_TAG)
                .commit();

    }

    @Override
    public void onContactAddition() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.containerView, NewContactFragment.newInstance( this),  Util.FRAGMENT_TAG)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void openContactDetail(Contact contact) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.containerView, ContactDetailFragment.newInstance(this, contact),  Util.FRAGMENT_TAG)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void cancelAddNew() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.containerView, ContactListFragment.newInstance(this),  Util.FRAGMENT_TAG)
                .commit();
    }

    @Override
    public void onSubmitAddNew() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.containerView, ContactListFragment.newInstance(this),  Util.FRAGMENT_TAG)
                .commit();
    }

    @Override
    public void updateContactInit(Contact contact) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.containerView, ContactUpdateFragment.newInstance(this, contact),  Util.FRAGMENT_TAG)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onDeleteContact() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.containerView, ContactListFragment.newInstance(this),  Util.FRAGMENT_TAG)
                .commit();
    }

    @Override
    public void onUpdateComplete(Contact contact) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.containerView, ContactDetailFragment.newInstance(this, contact),  Util.FRAGMENT_TAG)
                .commit();
    }
}
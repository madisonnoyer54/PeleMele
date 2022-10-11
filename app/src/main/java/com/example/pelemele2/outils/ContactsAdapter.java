package com.example.pelemele2.outils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;
import com.example.pelemele2.Contact;
import com.example.pelemele2.R;

import java.util.ArrayList;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsItemViewHolder> {

    private ArrayList<Contact> list;

    public ContactsAdapter(ArrayList<Contact> list){
        this.list = list;
    }


    @Override
    public ContactsItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // CREATE VIEW HOLDER AND INFLATING ITS XML LAYOUT
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_contacts_item, parent, false);

        return new ContactsItemViewHolder(view);
    }

    // UPDATE VIEW HOLDER WITH A GITHUBUSER
    @Override
    public void onBindViewHolder(ContactsItemViewHolder viewHolder, int position) {
        viewHolder.updateContacte(this.list.get(position));
    }

    // RETURN THE TOTAL COUNT OF ITEMS IN THE LIST
    @Override
    public int getItemCount() {
        return this.list.size();
    }


}





package com.example.pelemele2.outils;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.example.pelemele2.Contact;
import com.example.pelemele2.R;


public class ContactsItemViewHolder extends RecyclerView.ViewHolder {

    private TextView nom;
    private TextView num;


    public ContactsItemViewHolder(View itemView) {
        super(itemView);
        nom = itemView.findViewById(R.id.item_Nom);
        num = itemView.findViewById(R.id.item_Num);

    }

    public void updateContacte(Contact contact){
        this.nom.setText(contact.getNom());
        this.num.setText(contact.getNum());
    }

}
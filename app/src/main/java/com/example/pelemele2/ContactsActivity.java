package com.example.pelemele2;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pelemele2.outils.ContactsAdapter;

import java.util.ArrayList;

public class ContactsActivity extends AppCompatActivity {
    private ArrayList<Contact> list;
    private  RecyclerView recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        RecyclerView recycler =  findViewById(R.id.Recy);
        list = new ArrayList<>();


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 50);

        }

        Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);

        if(phones != null) {
            while (phones.moveToNext()) {
                @SuppressLint("Range") String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                @SuppressLint("Range") String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                list.add(new Contact(phoneNumber, name));

            }

            ContactsAdapter adapteur = new ContactsAdapter(list);
            recycler.setAdapter(adapteur);
            recycler.setLayoutManager(new LinearLayoutManager(this));

            DividerItemDecoration deco = new DividerItemDecoration(recycler.getContext(), DividerItemDecoration.VERTICAL);
            recycler.addItemDecoration(deco);
            phones.close();

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true ;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.quitter) {
            finish();
            return true;
        }
        if (item.getItemId() == R.id.l_chrono) {
            Intent ic = new Intent(ContactsActivity.this, ChronometreActivity.class);
            startActivity(ic);
            return true;
        }
        return false;
    }
}
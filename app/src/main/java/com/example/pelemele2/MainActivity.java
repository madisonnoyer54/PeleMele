package com.example.pelemele2;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.*;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import androidx.core.app.ActivityCompat;

import java.io.*;


public class MainActivity extends AppCompatActivity {
    static final int PHOTO = 1;
    private  Bitmap photo;

    @Override
    protected void onCreate(Bundle savedInstanceState){
          super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    public void bonjour(View view){
        Toast.makeText(MainActivity.this, "Bonjour", Toast.LENGTH_SHORT).show();
    }

    public void chrono(View view){
        Intent ic = new Intent(MainActivity.this, ChronometreActivity.class);
        startActivity(ic);
    }


    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                        assert data != null;
                        photo = (Bitmap)data.getExtras().get("data");

                    }


                }

            });

    public void photo(View view){

        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        someActivityResultLauncher.launch(intent);
    }

    
    public void derniere(View view) throws IOException {
         if(photo != null){
             FileOutputStream fos = openFileOutput("image.data", MODE_PRIVATE);
             photo.compress(Bitmap.CompressFormat.PNG, 100, fos);
             fos.flush();
             FileInputStream fis = openFileInput("image.data");
             Bitmap bm = BitmapFactory.decodeStream(fis);

             AlertDialog.Builder builder = new AlertDialog.Builder(this);
             View view2 = LayoutInflater.from(this).inflate(R.layout.activity_d, (ViewGroup) null);
             ImageView iv = (ImageView) view2.findViewById(R.id.imaged);
             iv.setImageBitmap(bm);
             builder.setView(view2);
             builder.show();
         }

    }

        public void longue (View view){
            Intent ic = new Intent(MainActivity.this, LongActivity.class);
            startActivity(ic);
        }

        public void meteo (View view){
            Intent ic = new Intent(MainActivity.this, MeteoActivity.class);
            startActivity(ic);
        }

        public void contacts (View view){
            Intent ic = new Intent(MainActivity.this, ContactsActivity.class);
            startActivity(ic);
        }

        public void capteurs (View view){
            Intent ic = new Intent(MainActivity.this, CapteursActivity.class);
            startActivity(ic);
        }

        public void select (View view){
            Intent ic = new Intent(MainActivity.this, SelectActivity.class);
            startActivity(ic);
        }


        @Override
        public boolean onCreateOptionsMenu (Menu menu){
            getMenuInflater().inflate(R.menu.menu_main, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected (MenuItem item){
            if (item.getItemId() == R.id.quitter) {
                finish();
                return true;
            }
            if (item.getItemId() == R.id.l_chrono) {
                Intent ic = new Intent(MainActivity.this, ChronometreActivity.class);
                startActivity(ic);
                return true;
            }
            return false;
        }


    }
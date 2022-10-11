package com.example.pelemele2;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LongActivity extends AppCompatActivity {
    private int progressStatus = 0;
    private Handler handler = new Handler();

    private ProgressBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_long);

        bar = findViewById(R.id.progressBar2);
        bar.setVisibility(View.INVISIBLE);

    }


    public void play_long(View view){
        bar.setVisibility(View.VISIBLE);
        Toast.makeText(LongActivity.this, "debut", Toast.LENGTH_SHORT).show() ;

        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(() -> {
            try {
                Thread.sleep(10000);
                runOnUiThread(() -> {
                    Toast.makeText(LongActivity.this, "fin", Toast.LENGTH_SHORT).show() ;
                    bar.setVisibility(View.INVISIBLE);
                });
            } catch (InterruptedException e) {

            }
        });
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
            Intent ic = new Intent(LongActivity.this, ChronometreActivity.class);
            startActivity(ic);
            return true;
        }
        return false;
    }
}
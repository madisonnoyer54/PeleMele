package com.example.pelemele2;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class ChronometreActivity extends AppCompatActivity {
    GregorianCalendar gCal;
    ImageButton stop, start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chronometre);


        stop = findViewById(R.id.stop);
        start = findViewById(R.id.start);

        // Empecher a l'utilisateur d'arreter avant de lancer
        stop.setEnabled(false);

        start.setOnClickListener((v) -> {
            gCal = new GregorianCalendar();
            Toast.makeText(ChronometreActivity.this, "Il est actuellement" + gCal.get(Calendar.HOUR)  + "h" + gCal.get(Calendar.MINUTE) + "m" + gCal.get(Calendar.SECOND) +"s" , Toast.LENGTH_SHORT).show();

            stop.setEnabled(true);
            start.setEnabled(false);

        });



        stop.setOnClickListener((v) -> {

            //Création de la date pour arrêter le chronometre
            GregorianCalendar gCal2 = new GregorianCalendar();
            //Mise a jour du temps du chronometre
            gCal2.add(Calendar.HOUR, - gCal.get (Calendar.HOUR));
            gCal2.add(Calendar.MINUTE,- gCal.get (Calendar.MINUTE));
            gCal2.add(Calendar.SECOND, - gCal.get (Calendar.SECOND));
            gCal2.add(Calendar.MILLISECOND, - gCal.get (Calendar.MILLISECOND));


            if (gCal2.get(10) == 0 && gCal2.get(12) == 0) {
                Toast.makeText(this, "Le temps ecouler est de " + gCal2.get(Calendar.SECOND) + "s ", Toast.LENGTH_SHORT).show();
            } else if (gCal2.get(10) == 0) {
                Toast.makeText(this, "Le temps ecouler est de" + gCal2.get(Calendar.MINUTE) + "m " +gCal2.get(Calendar.SECOND) + "s ", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Le temps ecouler est de" + gCal2.get(10) + "h " +gCal2.get(Calendar.MINUTE) + "m " + gCal2.get(Calendar.SECOND) + "s ", Toast.LENGTH_SHORT).show();
            }

            stop.setEnabled(false);
            start.setEnabled(true);
        });



    }

    public void start(View view){
        stop.setEnabled(true);
        start.setEnabled(false);
        Toast.makeText(ChronometreActivity.this, "start !", Toast.LENGTH_SHORT).show();

        gCal = new GregorianCalendar(); // demarer le chrono
        Toast.makeText(ChronometreActivity.this, gCal.get(Calendar.HOUR)  + ":" + gCal.get(Calendar.MINUTE) + ":" + gCal.get(Calendar.SECOND) + ":" + gCal.get(Calendar.MILLISECOND), Toast.LENGTH_SHORT).show();

    }

    public void stop(View view){
        stop.setEnabled(false);
        start.setEnabled(true);

        //Création de la date pour arrêter le chronometre
        GregorianCalendar gCal2 = new GregorianCalendar();
        //Mise a jour du temps du chronometre
        gCal2.add(Calendar.HOUR, - gCal.get (Calendar.HOUR));
        gCal2.add(Calendar.MINUTE,- gCal.get (Calendar.MINUTE));
        gCal2.add(Calendar.SECOND, - gCal.get (Calendar.SECOND));
        gCal2.add(Calendar.MILLISECOND, - gCal.get (Calendar.MILLISECOND));


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
            Toast.makeText(ChronometreActivity.this, "Le chrono est deja lancer", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }
}
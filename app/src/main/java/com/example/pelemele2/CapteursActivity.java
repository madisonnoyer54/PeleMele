package com.example.pelemele2;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.*;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.pelemele2.outils.VueCapteurActivity;

public class CapteursActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManagerACC;
    private Sensor sensorACC;

   // private SensorManager sensorManagerMA;
  //  private Sensor sensorMA;
  //  private float[] oriantation;

    private float[] linear_acceleration;
    private Switch aSwitch;

    VueCapteurActivity vue;

    private boolean act;
    private float orianta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capteurs);
        this.setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT );

        vue = findViewById(R.id.view);
        act = false;

        linear_acceleration = new float[3];

        orianta = 0;

        aSwitch = findViewById(R.id.switch1);

        // Accelerometer
        sensorManagerACC = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorACC = sensorManagerACC.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // Magnétometre

        /*
        sensorManagerMA = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorMA = sensorManagerMA.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

         */

    }

    public void switch2(View view){
        act = !act;

        if(act){
            Toast.makeText(CapteursActivity.this,"desactiver",Toast.LENGTH_SHORT).show();
            sensorManagerACC.unregisterListener( this);
            //  sensorManagerMA.unregisterListener(this);
        }
        else{
            Toast.makeText(CapteursActivity.this,"activer",Toast.LENGTH_SHORT).show();
            sensorManagerACC.registerListener((SensorEventListener) this,sensorACC,20000);
           // sensorManagerMA.registerListener(this,sensorMA,20000);
        }
    }


    public void onSensorChanged(SensorEvent event){
        // Accelerometre
        final float alpha = (float) 0.8;
        // Isolate the force of gravity with the low-pass filter.
        float[] gravity = new float[3];
        gravity[0] = (alpha * gravity[0] + (1 - alpha) * event.values[0]);
        gravity[1] = (alpha * gravity[1] + (1 - alpha) * event.values[1]);
        gravity[2] = (alpha * gravity[2] + (1 - alpha) * event.values[2]);

        // Remove the gravity contribution with the high-pass filter.
        linear_acceleration[0] = event.values[0] - gravity[0];
        linear_acceleration[1] = event.values[1] - gravity[1];
        linear_acceleration[2] = event.values[2] - gravity[2];

        /*

        float[] mGravity = new float[0];
        float[] mGeomagnetic = new float[0];
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
            mGravity = event.values;

        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
            mGeomagnetic = event.values;
        if (mGravity != null && mGeomagnetic != null) {
            float R[] = new float[9];
            float I[] = new float[9];
            boolean success = SensorManager.getRotationMatrix(R, I, mGravity, mGeomagnetic);
            if (success) {
                float[] orientation = new float[3];
                SensorManager.getOrientation(R, orientation);
                orianta = orientation[0]; // orientation contains: azimut, pitch and roll
            }
        }

         */

        // Acellerometre
        vue.passage(linear_acceleration/*,orianta */);
        vue.invalidate();


    }

    public void onResume() {
        super.onResume();
        this.sensorManagerACC.unregisterListener(this);
        this.sensorManagerACC.registerListener(this, this.sensorACC, 10000);

/*
        this.sensorManagerMA.unregisterListener(this);
        this.sensorManagerMA.registerListener(this, this.sensorMA, 10000);



 */
    }

    @Override
    public void onPause() {
        super.onPause();
        this.sensorManagerACC.unregisterListener(this);
        //this.sensorManagerMA.unregisterListener(this);
        
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {


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
            Intent ic = new Intent(CapteursActivity.this, ChronometreActivity.class);
            startActivity(ic);
            return true;
        }
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}
package com.example.pelemele2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.MessageFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MeteoActivity extends AppCompatActivity {
    private FusedLocationProviderClient fusedLocationClient;
    private double longitude;
    private double latitude;
    TextView hum ;
    ImageView image_hum ;
    TextView temp;
    ImageView image_temp ;
    TextView temp_min;
    TextView temp_max;
    ImageView icone ;
    TextView loca ;

    private double tempe;
    private double humi;
    private String nom;
    private double max;
    private double min;
    private Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meteo);
         hum = findViewById(R.id.hum);
        image_hum = findViewById(R.id.image_hum);
        temp = findViewById(R.id.temp);
         image_temp = findViewById(R.id.image_temp);
        temp_min = findViewById(R.id.temp_min);
         temp_max = findViewById(R.id.temp_max);
         icone = findViewById(R.id.icon);
         loca = findViewById(R.id.loca_nom);

        hum.setVisibility(View.INVISIBLE);
        temp.setVisibility(View.INVISIBLE);
        image_hum.setVisibility(View.INVISIBLE);
        image_temp.setVisibility(View.INVISIBLE);
        temp_min.setVisibility(View.INVISIBLE);
        temp_max.setVisibility(View.INVISIBLE);
        icone.setVisibility(View.INVISIBLE);
        loca.setVisibility(View.INVISIBLE);




    }

    public void loca(View view) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        // Les permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 48);
        }

            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                ExecutorService service = Executors.newSingleThreadExecutor();
                                service.execute(() -> {

                                        longitude = location.getLongitude();
                                        latitude = location.getLatitude();

                                    try {
                                        meteo();
                                    } catch (JSONException e) {
                                        throw new RuntimeException(e);
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }

                                runOnUiThread(() -> {
                                    while (nom == null) {
                                        try {
                                            Thread.sleep(100);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    Toast.makeText(MeteoActivity.this, "Latitude: " + MeteoActivity.this.latitude + " \nLongitude: " + MeteoActivity.this.longitude, Toast.LENGTH_SHORT).show();
                                    temp.setText(MessageFormat.format("{0}°C", Integer.valueOf((int) MeteoActivity.this.tempe)));
                                    hum.setText(MessageFormat.format("{0}%", Integer.valueOf((int) MeteoActivity.this.humi)));
                                    loca.setText(MessageFormat.format("{0}", MeteoActivity.this.nom));
                                    image_hum.setVisibility(View.VISIBLE);
                                    image_temp.setVisibility(View.VISIBLE);
                                    temp_min.setText(MessageFormat.format("↓ : {0}°C", Integer.valueOf((int) MeteoActivity.this.min)));
                                    temp_max.setText(MessageFormat.format("↑ : {0}°C", Integer.valueOf((int) MeteoActivity.this.max)));
                                    icone.setVisibility(View.VISIBLE);
                                    icone.setImageBitmap(MeteoActivity.this.bitmap);

                                    hum.setVisibility(View.VISIBLE);
                                    temp.setVisibility(View.VISIBLE);
                                    image_hum.setVisibility(View.VISIBLE);
                                    image_temp.setVisibility(View.VISIBLE);
                                    temp_min.setVisibility(View.VISIBLE);
                                    temp_max.setVisibility(View.VISIBLE);
                                    icone.setVisibility(View.VISIBLE);
                                    loca.setVisibility(View.VISIBLE);
                                });
                            });




                            }
                        }
                    });


    }



            public void meteo() throws JSONException, IOException {
                String url = "http://api.openweathermap.org/data/2.5/weather?lat=" + this.latitude + "&lon=" + this.longitude + "&appid=6c2ad30eae15b61aae3d2e90079beb1d&lang=fr";
                try {
                    InputStream in = new URL(url).openStream();
                    JSONObject res = readStream(in);
                    this.tempe = res.getJSONObject("main").getDouble("temp") - 273.15d;
                    this.humi = res.getJSONObject("main").getDouble("humidity");
                    this.nom = res.getString("name");
                    this.max = res.getJSONObject("main").getDouble("temp_max") - 273.15d;
                    this.min = res.getJSONObject("main").getDouble("temp_min") - 273.15d;
                    JSONObject desc = (JSONObject) res.getJSONArray("weather").get(0);
                    URL url2 = new URL("http://openweathermap.org/img/wn/" + desc.get("icon") + "@2x.png");
                    HttpURLConnection urlConnection = (HttpURLConnection) url2.openConnection();
                    InputStream in2 = new BufferedInputStream(urlConnection.getInputStream());
                    this.bitmap = BitmapFactory.decodeStream(in2);
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                    Log.i("MeteoActivity", "erreur meteo : " + e.getMessage());
                }
    }



    private JSONObject readStream(InputStream is) throws IOException, JSONException {
        StringBuilder sb = new StringBuilder();
        BufferedReader r = new BufferedReader(new InputStreamReader(is), 1000);
        for(String line = r.readLine(); line != null; line = r.readLine()){
            sb.append(line);
        }
        is.close();
        return new JSONObject((sb.toString()));
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
            Intent ic = new Intent(MeteoActivity.this, ChronometreActivity.class);
            startActivity(ic);
            return true;
        }
        return false;
    }
}
package com.example.pelemele2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.*;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.*;
import android.widget.ImageView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.internal.view.SupportMenu;

import static java.lang.Math.abs;

public class SelectActivity extends AppCompatActivity {
    private Rect rectangle;
    private int compte;
    private ImageView selectImage;
    private SurfaceView surfaceView;
    private int x1;
    private int x2;
    private int y1;
    private int y2;
    private   Bitmap bip;
    private   Bitmap bitmapfini;

    private BitmapDrawable imagedraw;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.x1 = 0;
        this.y1 = 0;
        this.x2 = 0;
        this.y2 = 0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        setContentView(R.layout.activity_select);
       this.selectImage = (ImageView) findViewById(R.id.imageselect);
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        this.surfaceView = surfaceView;
        surfaceView.setZOrderOnTop(true);

        this.selectImage.setOnTouchListener((v, event) -> {
            SurfaceHolder sfhTrackHolder = SelectActivity.this.surfaceView.getHolder();
            sfhTrackHolder.setFormat(-2);
            event.getActionMasked();
            Log.i("selectActivity", "nombre de click: " + event.getPointerCount());
            if (event.getPointerCount() == 2) {
                SelectActivity.this.x1 = (int) event.getX(0);
                SelectActivity.this.y1 = (int) event.getY(0);
                SelectActivity.this.x2 = (int) event.getX(1);
                SelectActivity.this.y2 = (int) event.getY(1);
                SelectActivity.this.rectangle = new Rect(SelectActivity.this.x1, SelectActivity.this.y1, SelectActivity.this.x2, SelectActivity.this.y2);
                Log.i("rectangle", SelectActivity.this.rectangle.toString());
                Bitmap bitmap = Bitmap.createBitmap(SelectActivity.this.surfaceView.getWidth(), SelectActivity.this.surfaceView.getHeight(), Bitmap.Config.ARGB_8888);
                Paint paint = new Paint();
                paint.setColor(Color.BLUE);
                paint.setAntiAlias(true);
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth(8.0f);

                new Canvas(bitmap);
                Canvas canvas = sfhTrackHolder.lockCanvas();
                canvas.drawColor(0, PorterDuff.Mode.CLEAR);
                canvas.drawRect(SelectActivity.this.rectangle, paint);
                sfhTrackHolder.unlockCanvasAndPost(canvas);


                if(rectangle != null){
                    imagedraw = (BitmapDrawable) selectImage.getDrawable();
                    bip = Bitmap.createScaledBitmap(imagedraw.getBitmap(), selectImage.getWidth(),selectImage.getHeight(),false);


                    if(bip != null  ){
                        bitmapfini = Bitmap.createBitmap(bip,rectangle.left,rectangle.bottom,abs(rectangle.width()),abs(rectangle.height()),null,true);
                    }

                    if(bitmapfini != null){
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        View view2 = LayoutInflater.from(this).inflate(R.layout.activity_d, (ViewGroup) null);
                        ImageView iv = (ImageView) view2.findViewById(R.id.imaged);
                        iv.setImageBitmap(bitmapfini);
                        builder.setView(view2);
                        builder.show();
                    }

                }

            }





            return true;
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
            Intent ic = new Intent(SelectActivity.this, ChronometreActivity.class);
            startActivity(ic);
            return true;
        }
        return false;
    }
}
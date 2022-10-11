package com.example.pelemele2.outils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class VueCapteurActivity extends View {
    private Paint paint;
    //private Paint paint1;
    private float[] result;
  //  private  float orianta;

    public VueCapteurActivity(Context context) {
        super(context);
        init();
    }

    public VueCapteurActivity(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public VueCapteurActivity(Context context, AttributeSet attributeSet, int defStyleAttr) {
        super(context, attributeSet, defStyleAttr);
        init();
    }

    public VueCapteurActivity(Context context, AttributeSet attributeSet, int defStyleAttr, int defStyleRes) {
        super(context, attributeSet, defStyleAttr, defStyleRes);
        init();
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLine(550, 700, 550 - (result[0] * 50), (result[1] * 50) + 700, this.paint);
     //   canvas.drawLine(550, 700, 550 - (result[0] * 50), (result[1] * 50) + 700, this.paint1);
    }

    public void init() {
        this.result = new float[3];
        Paint paint = new Paint();
        this.paint = paint;
        paint.setColor(Color.BLACK);
        this.paint.setAntiAlias(true);
        this.paint.setStyle(Paint.Style.STROKE);
        this.paint.setStrokeWidth(8.0f);

        /*
        Paint paint1 = new Paint();
        this.paint1 = paint;
        paint.setColor(Color.RED);
        this.paint1.setAntiAlias(true);
        this.paint1.setStyle(Paint.Style.STROKE);
        this.paint1.setStrokeWidth(8.0f);

         */
    }

    public void passage(float[] resultat/*, float orianta*/) {
        this.result = resultat;
        //this.orianta = orianta;
    }


}

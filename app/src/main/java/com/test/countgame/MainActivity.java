package com.test.countgame;

import android.app.Activity;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.os.CountDownTimer;
import java.util.Random;
import android.view.Display;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    int count = 0;
    int timer = 30;
    float sensorX, sensorY, sensorZ;
    int frame = 0;
    ProgressBar bar = null;
    PopupWindow popupWin;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        bar = (ProgressBar)findViewById(R.id.time);
        bar.setMax(timer);

    }

    protected void onResume() {
        super.onResume();
        // Listenerの登録
        Sensor accel = sensorManager.getDefaultSensor(
                Sensor.TYPE_ACCELEROMETER);

        sensorManager.registerListener(this, accel, SensorManager.SENSOR_DELAY_NORMAL);

    }


    @Override
    protected void onPause() {
        super.onPause();
        // Listenerを解除
        sensorManager.unregisterListener(this);
    }
    public void onSensorChanged(SensorEvent event) {


        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            sensorX = event.values[0];
            sensorY = event.values[1];
            sensorZ = event.values[2];
            if(sensorX>10){
                frame = 5;
            }
            else if(sensorY>10){
                frame = 5;
            }
        }
            String strTmp = "加速度センサー\n"
                + " X: " + sensorX + "\n"
                + " Y: " + sensorY + "\n"
                + " Z: " + sensorZ;

        Log.d("xyz", strTmp);

    }
    public void onAccuracyChanged(Sensor e,int i) {

    }

        CountDownTimer countDownTimer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millsUntilFinished) {

                drawMogura();

                if(frame > 0){
                    frame--;
                }
            }

            @Override
            public void onFinish() {


                /*ポップアップに表示するレイアウトの設定*/
                LinearLayout popLayout
                        = (LinearLayout)getLayoutInflater().inflate(
                        R.layout.resultpopup, null);
                TextView popupText
                        = (TextView)popLayout.findViewById(R.id.scoreView);
                popupText.setText(new Integer(count).toString());

                /*ポップアップの作成*/
                View v = getWindow().getDecorView();
                popupWin = new PopupWindow(v);
                popupWin.setWindowLayoutMode(
                        WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                popupWin.setContentView(popLayout);
                popupWin.setBackgroundDrawable(null);
                popupWin.showAsDropDown(v, 0, 0);
                Button closePopup = (Button)findViewById(R.id.retryButton);



            }
        }.start();




    public void onRetryButton(View v){
        countDownTimer.start();
        popupWin.dismiss();
        timer = 30;
        count = 0;

    }

    public void onTap(View v){
        drawMogura();
        count++;
    }

    public void drawMogura() {
        // カウントの加算

        timer--;
        bar.setProgress(timer);




        this.findViewById(R.id.moguraView).setVisibility(View.VISIBLE);//見える
        this.findViewById(R.id.moguraView2).setVisibility(View.VISIBLE);
        this.findViewById(R.id.moguraView3).setVisibility(View.GONE);//見えない

        int moguH = findViewById(R.id.moguraView).getHeight();
        int mogu2H = findViewById(R.id.moguraView2).getHeight();
        int moguW = findViewById(R.id.moguraView).getWidth();
        int mogu2W = findViewById(R.id.moguraView2).getWidth();


//        if (count % 2 == 0) {
//            this.findViewById(R.id.moguraView).setVisibility(View.GONE);
//            this.findViewById(R.id.moguraView2).setVisibility(View.GONE);
//        }
//         表示位置の変更
        WindowManager wm = getWindowManager();
        Display disp = wm.getDefaultDisplay();
        int displayW = disp.getWidth();//this.findViewById(R.id.activity_main).getWidth();

        Random rx = new Random();
        Random ry = new Random();
            int w1 = (int) (displayW * 0.5);
            int w2 = rx.nextInt(w1);
            if (w2 + moguW > displayW) {
            w2 -= moguW;
            }

            this.findViewById(R.id.moguraView).setX(w2);



            w2 = rx.nextInt(w1);
            if (w2 + mogu2W > displayW) {
                w2 -= mogu2W;
            }

            this.findViewById(R.id.moguraView2).setX(w2);

            w2 = rx.nextInt(w1);
            if (w2 + mogu2W > displayW) {
                w2 -= mogu2W;
            }

            this.findViewById(R.id.moguraView3).setX(w2);

            int displayH = disp.getWidth();//(R.id.activity_main).getHeight();

            int h1 = (int) (displayH * 0.5);
            int h2 = ry.nextInt(h1);
            if (h2 + moguH > displayH) {
                h2 -= moguH;
            }

            this.findViewById(R.id.moguraView).setTranslationY(h2);


            h2 = ry.nextInt(h1);
            if (h2 + mogu2H > displayH) {
                h2 -= mogu2H;

            }

            this.findViewById(R.id.moguraView2).setTranslationY(h2);

        h2 = ry.nextInt(h1);
        if (h2 + mogu2H > displayH) {
            h2 -= mogu2H;

        }

        this.findViewById(R.id.moguraView3).setTranslationY(h2);




        if(frame>=1){
            this.findViewById(R.id.moguraView3).setVisibility(View.VISIBLE);
        }
        }

}
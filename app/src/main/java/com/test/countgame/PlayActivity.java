package com.test.countgame;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.view.Display;
import android.view.WindowManager;

public class PlayActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    int count = 0;

    final int const_timer=30;
    int timer = const_timer;
    float sensorX, sensorY, sensorZ;
    int frame = 0;
    ProgressBar bar = null;
    PopupWindow popupWin;
    Timer ttimer = null;
    TimerTask task=null;
    SharedPreferences prefs = null;
    Integer array[] = {-1,-1,-1,-1};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_activity);
        prefs = getPreferences(Context.MODE_PRIVATE);


        array[0]= prefs.getInt("topScore",0);
         array[1]= prefs.getInt("secondScore",0);
         array[2]= prefs.getInt("thirdScore",0);

        ttimer = new Timer();
        task= new TimerTask() {
            @Override
            public void run() {
                handler.sendMessage(Message.obtain(handler,0,null));
            }
        };
        ttimer.schedule(task,300,1000);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        bar = (ProgressBar)findViewById(R.id.time);
        bar.setMax(const_timer);

    }








    private Handler handler = new Handler(){
        public void handleMessage(Message msg){

            drawMogura();

            timer--;
            if(timer <=0) {
                onTimerFinish();
            }
            if(frame > 0){
                frame--;
            }
            bar.setProgress(timer);
        }
    };


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

       // Log.d("xyz", strTmp);

    }
    public void onAccuracyChanged(Sensor e,int i) {

    }


            private void onTimerFinish() {

                /*ポップアップに表示するレイアウトの設定*/
                LinearLayout popLayout
                        = (LinearLayout)getLayoutInflater().inflate(
                        R.layout.resultpopup, null);
                TextView popupText
                        = (TextView)popLayout.findViewById(R.id.scoreView);
                popupText.setText("the socre is :"+new Integer(count).toString());

                array[3] = count;
                Arrays.sort(array, Collections.reverseOrder());
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt("TopScore", array[0]);
                editor.putInt("SecondScore", array[1]);
                editor.putInt("ThirdScore", array[2]);
                editor.apply();
                TextView scoreHistory = (TextView)popLayout.findViewById(R.id.HistoryView);
                scoreHistory.setText(String.format("TopScore:"+Integer.toString(array[0])+"%n"+"SecondScore:"+Integer.toString(array[1])+"%n"+"ThirdScore:"+ Integer.toString(array[2])));

                View v = getWindow().getDecorView();
                popupWin = new PopupWindow(v);
                popupWin.setWindowLayoutMode(
                        WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                popupWin.setContentView(popLayout);
                popupWin.setBackgroundDrawable(null);
                popupWin.showAsDropDown(v, 0, 0);
                Button closePopup = (Button)findViewById(R.id.retryButton);
                ttimer.cancel();

            }




    public void onRetryButton(View v){
        if(ttimer!=null){
            ttimer=null;
        }
        findViewById(R.id.moguraView3).setVisibility(View.GONE);

        ttimer = new Timer();

        task= new TimerTask() {
            @Override
            public void run() {
                handler.sendMessage(Message.obtain(handler,0,null));
            }
        };
        ttimer.schedule(task,300,1000);

        popupWin.dismiss();
        timer = const_timer;
        count = 0;

    }

    public void onTap(View v){
        drawMogura();
        count++;// カウントの加算
    }
    public void onSpecialTap(View v){
        drawMogura();
        count =count +5;// カウントの加算
    }

    public Display disp(){
        //         表示位置の変更
        WindowManager wm = getWindowManager();
        Display disp = wm.getDefaultDisplay();
        return disp;
    }

    public void drawMogura() {

        this.findViewById(R.id.moguraView).setVisibility(View.VISIBLE);//見える
        this.findViewById(R.id.moguraView2).setVisibility(View.VISIBLE);
        this.findViewById(R.id.moguraView3).setVisibility(View.GONE);//見えない

        int displayW = disp().getWidth();


        Random rx = new Random();
        Random ry = new Random();
            int w1 = (int) (displayW * 0.5);
            int w2 = rx.nextInt(w1);


            this.findViewById(R.id.moguraView).setTranslationX(w2);



            int w3 = rx.nextInt(w1);
            for (int i = 0;i < 10;i++) {
                if (Math.abs(w3-w2) <= 230) {
                    w3 = rx.nextInt(w1);
                }
                else{
                    break;
                }
            }

            this.findViewById(R.id.moguraView2).setTranslationX(w3);

            int w4 = rx.nextInt(w1);
        for (int i = 0;i < 10;i++) {
            if (Math.abs(w3-w2) <= 230 && Math.abs(w4-w3)<=230) {
                w4 = rx.nextInt(w1);
            }
            else{
                break;
            }
        }
                this.findViewById(R.id.moguraView3).setX(w4);

                int displayH = disp().getWidth();

                int h1 = (int) (displayH * 0.5);
                int h2 = ry.nextInt(h1);

                    this.findViewById(R.id.moguraView).setTranslationY(h2);


                    int h3 = ry.nextInt(h1);
                for (int i = 0;i < 10;i++) {
                    if (Math.abs(h3 - h2) <= 230) {
                        h3 = rx.nextInt(h1);
                    }
                    else {
                        break;
                    }
                }
                    this.findViewById(R.id.moguraView2).setTranslationY(h3);

                    int h4 = ry.nextInt(h1);
                for (int i = 0;i < 10;i++) {
                    if (Math.abs(h3-h2) <= 230 && Math.abs(h4-h3)<=230) {
                        h4 = rx.nextInt(w1);
                    }
                    else{
                        break;
                    }
                }
                    this.findViewById(R.id.moguraView3).setTranslationY(h4);




                        if (frame >= 1) {
                            this.findViewById(R.id.moguraView3).setVisibility(View.VISIBLE);
                        }
                    }

                }

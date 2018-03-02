package com.test.countgame;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.os.CountDownTimer;
import java.util.Random;
import android.view.Display;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    int count = 0;
    int timer = 10;
    ProgressBar bar = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bar = (ProgressBar)findViewById(R.id.time);
        bar.setMax(timer);

        Button popupButton = (Button)findViewById(R.id.button3);
        //ポップアップを表示するボタン
        popupButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                PopupWindow popupWin;

                /*ポップアップに表示するレイアウトの設定*/
                LinearLayout popLayout
                        = (LinearLayout)getLayoutInflater().inflate(
                        R.layout.popuptest, null);
//                TextView popupText
//                        = (TextView)popLayout.findViewById(R.id.textView);
//                popupText.setText("Popup Text");

                /*ポップアップの作成*/
                popupWin = new PopupWindow(v);
                popupWin.setWindowLayoutMode(
                        WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
                popupWin.setContentView(popLayout);
                popupWin.setBackgroundDrawable(null);
                popupWin.showAsDropDown(v, 0, 0);
                //ボタンの下にポップアップを表示
            }
        });




    }


        CountDownTimer countDownTimer = new CountDownTimer(10000000, 1000) {
            @Override
            public void onTick(long millsUntilFinished) {
                onTv();
            }

            @Override
            public void onFinish() {
                ;//finish();
            }
        }.start();





    public void onTv(View v) {
        // カウントの加算
        count++;
        //((TextView) findViewById(R.id.tv)).setText("" + count);
        this.findViewById(R.id.moguraView).setVisibility(View.VISIBLE);
        this.findViewById(R.id.moguraView2).setVisibility(View.VISIBLE);


        // 表示位置の変更
        int w = findViewById(R.id.activity_main).getWidth();
        int w1 = (int) (w * 0.8);
        int w2 = new Random().nextInt(w1);
        //((TextView) findViewById(R.id.tv)).setTranslationX(w2);
        this.findViewById(R.id.moguraView).setTranslationX(w2);
        this.findViewById(R.id.moguraView2).setTranslationX(w2);


        int h = findViewById(R.id.activity_main).getHeight();
        int h1 = (int) (h * 0.8);
        int h2 = new Random().nextInt(h1);
        //((TextView) findViewById(R.id.tv)).setTranslationY(h2);
        this.findViewById(R.id.moguraView).setTranslationY(h2);
        this.findViewById(R.id.moguraView2).setTranslationY(h2);
    }
    public void onTv() {
        // カウントの加算
        count++;
        timer --;
        bar.setProgress(timer);
        //((TextView) this.findViewById(R.id.tv)).setText("" + count);
        this.findViewById(R.id.moguraView).setVisibility(View.VISIBLE);
        this.findViewById(R.id.moguraView2).setVisibility(View.VISIBLE);

//        if(count%2 == 0) {
//            this.findViewById(R.id.moguraView).setVisibility(View.GONE);
//            this.findViewById(R.id.moguraView2).setVisibility(View.GONE);//見えなくなるやつ
//        }
        // 表示位置の変更
        WindowManager wm = getWindowManager();
        Display disp = wm.getDefaultDisplay();
        int w = disp.getWidth();//this.findViewById(R.id.activity_main).getWidth();
        int w1 = (int) (w * 0.8);
        int w2 = new Random().nextInt(w1);
        //((TextView) findViewById(R.id.tv)).setTranslationX(w2);
        this.findViewById(R.id.moguraView).setTranslationX(w2);
        this.findViewById(R.id.moguraView2).setTranslationX(w2);
        int h = disp.getWidth();//(R.id.activity_main).getHeight();
        int h1 = (int) (h * 0.8);
        int h2 = new Random().nextInt(h1);
        //((TextView) findViewById(R.id.tv)).setTranslationY(h2);
        //((TextView) findViewById(R.id.tv)).setTextSize((int)(h2*0.1));
        this.findViewById(R.id.moguraView).setTranslationX(h2);
        this.findViewById(R.id.moguraView2).setTranslationY(h2);
    }

}
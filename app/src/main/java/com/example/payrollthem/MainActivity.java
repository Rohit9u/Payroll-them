package com.example.payrollthem;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static int SPLASH_SCREEN=5000;
    private ImageView imageView;
    private TextView title;
    private TextView quote;
    private Animation topAnimator;
    private Animation bottomAnimator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView=findViewById(R.id.logoz);
        title=findViewById(R.id.title);
        quote=findViewById(R.id.quote);
        topAnimator= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnimator=AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
        imageView.setAnimation(topAnimator);
        title.setAnimation(bottomAnimator);
        quote.setAnimation(bottomAnimator);
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intentt=new Intent(MainActivity.this,chooseUser.class);
                startActivity(intentt);
                finish();
            }
        },SPLASH_SCREEN);
        if(getIntent().getBooleanExtra("EXIT",false)){
            finish();
        }


    }


}
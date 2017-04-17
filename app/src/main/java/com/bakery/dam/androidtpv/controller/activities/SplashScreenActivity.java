package com.bakery.dam.androidtpv.controller.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import com.bakery.dam.androidtpv.R;
import com.bakery.dam.androidtpv.controller.activities.login.LoginActivity;


public class SplashScreenActivity extends AppCompatActivity {

    private static final String TAG = SplashScreenActivity.class.getSimpleName();
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final int SPLASH_SCREEN_MIN_LENGTH = 5000;
        final int SPLASH_SCREEN_ANIM_TIME = getResources().getInteger(android.R.integer.config_longAnimTime);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Log.i(TAG, "onCreate: ");

        handler = new Handler();

        final RelativeLayout appIcon = (RelativeLayout) SplashScreenActivity.this.findViewById(R.id.splashscreen);

        final Animation animAfterLoad = AnimationUtils.loadAnimation(this, android.R.anim.fade_out);
        animAfterLoad.setInterpolator(new AccelerateInterpolator());
        animAfterLoad.setDuration(SPLASH_SCREEN_ANIM_TIME);

        animAfterLoad.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationEnd(Animation animation) {
                Log.i(TAG, "animAfterLoad: END ");
                if (appIcon != null) {
                    appIcon.setVisibility(View.GONE);
                }
                goMain();
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationStart(Animation animation) {
            }
        });

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (appIcon != null)
                    appIcon.startAnimation(animAfterLoad);
            }
        }, SPLASH_SCREEN_MIN_LENGTH);

    }

    private void goMain() {
        Intent mainIntent = new Intent(SplashScreenActivity.this, LoginActivity.class);
        SplashScreenActivity.this.startActivity(mainIntent);
        overridePendingTransition(R.anim.zoom_enter, android.R.anim.fade_out);
        SplashScreenActivity.this.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
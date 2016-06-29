package com.attribes.olo.kababjees.Screens;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.attribes.olo.kababjees.R;

public class SplashScreen extends Activity {

    final int SPLASH_TIME_OUT = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Thread welcomeThread = new Thread() {
            int wait = 0;

            @Override
            public void run() {
                try {
                    super.run();
                    while (wait < SPLASH_TIME_OUT) {
                        sleep(100);
                        wait += 100;
                    }
                } catch (Exception e) {
                    System.out.println("EXc=" + e);
                } finally {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
            }
        };
        welcomeThread.start();
    }
}

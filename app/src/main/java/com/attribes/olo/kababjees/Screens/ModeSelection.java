package com.attribes.olo.kababjees.Screens;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.attribes.olo.kababjees.R;
import com.attribes.olo.kababjees.fragments.ReservationFragment;

public class ModeSelection extends AppCompatActivity {

    private Toolbar toolbar;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private Button orderNowBtn , reservationBtn;
    private LinearLayout btnLayout;
    private LinearLayout containerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode_selection);

        intViews();
    }

    private void intViews() {

        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        toolbar= (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        mTitle = mDrawerTitle = getSupportActionBar().getTitle();

        btnLayout = (LinearLayout) findViewById(R.id.buttonLayout);
        containerLayout = (LinearLayout) findViewById(R.id.frameLayout);
        orderNowBtn = (Button) findViewById(R.id.orderOnline);
        reservationBtn  = (Button) findViewById(R.id.reservationPanel);

        orderNowBtn.setOnClickListener(new OrderOnlineListner());
        reservationBtn.setOnClickListener(new ReservationListner());
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }



    private class OrderOnlineListner implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
    }

    private class ReservationListner implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            btnLayout.setVisibility(View.GONE);
            FrameLayout frameLayout = (FrameLayout)findViewById(R.id.frame_mode_container) ;
            frameLayout.setBackgroundResource(0);

            ReservationFragment reservefragment = new  ReservationFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame_mode_container, reservefragment).commit();
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        return true;
    }
}

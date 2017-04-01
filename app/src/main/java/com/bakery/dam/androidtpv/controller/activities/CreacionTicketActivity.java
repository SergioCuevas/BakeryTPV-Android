package com.bakery.dam.androidtpv.controller.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.bakery.dam.androidtpv.R;
import com.bakery.dam.androidtpv.controller.managers.ProductCallback;
import com.bakery.dam.androidtpv.model.Producto;

import java.util.List;

public class CreacionTicketActivity extends AppCompatActivity implements ProductCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_creacion_ticket);
        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //añadimos al "menu"
        tabs.addTab(tabs.newTab().setText("CALCULADORA"));
        tabs.addTab(tabs.newTab().setText("PRODUCTO"));
        //le damos una posición


        // Setear adaptador al viewpager.

        ViewPager viewPager = (ViewPager) findViewById(R.id.ViewPagerPrincipal);
        Adapter_ViewPager adapter_viewPager = new Adapter_ViewPager(getSupportFragmentManager(),tabs.getTabCount());
        viewPager.setAdapter(adapter_viewPager);
        tabs.setTabGravity(TabLayout.GRAVITY_FILL);
        tabs.setTabMode(TabLayout.MODE_FIXED);

        // Setear adaptador al viewpager.
        //ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        //tabs.setupWithViewPager(viewPager);


        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));


        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    @Override
    public void onSuccess(Object product) {

    }

    @Override
    public void onFailure(Throwable t) {

    }
}

package com.bakery.dam.androidtpv.controller.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
//http://www.codigoandroid.es/curso-programacion-android/tutorial-android-interfaz-de-usuario-con-pestanas/
/**
 * Created by DAM on 27/3/17.
 */

public class Adapter_ViewPager extends FragmentPagerAdapter {

    int numDeSecciones;

    public Adapter_ViewPager(FragmentManager fm, int numDeSecciones) {
        super(fm);
        this.numDeSecciones=numDeSecciones;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new FragmentProductos();
            case 1:
                return new FragmentProductos();

                default:
                    return null;
        }
    }

    @Override
    public int getCount() {
        return numDeSecciones;
    }
}

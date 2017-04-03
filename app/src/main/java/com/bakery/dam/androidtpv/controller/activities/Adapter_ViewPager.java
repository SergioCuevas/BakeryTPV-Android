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
    private long id;
    public Adapter_ViewPager(FragmentManager fm, int numDeSecciones, long id) {
        super(fm);
        this.numDeSecciones=numDeSecciones;
        this.id = id;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                FragmentProductos fp = new FragmentProductos();
                fp.setId(this.id);
                return fp;

            case 1:
                return new FragmentCalculadora();

                default:
                    return null;
        }
    }

    @Override
    public int getCount() {
        return numDeSecciones;
    }
}

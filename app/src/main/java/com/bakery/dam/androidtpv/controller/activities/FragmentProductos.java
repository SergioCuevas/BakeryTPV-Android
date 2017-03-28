package com.bakery.dam.androidtpv.controller.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bakery.dam.androidtpv.R;

/**
 * Created by DAM on 28/3/17.
 */

public class FragmentProductos extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //  "Inflamos" el archivo XML correspondiente a esta sección.
        return inflater.inflate(R.layout.calculadora,container,false);
    }
}

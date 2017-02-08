package com.bakery.dam.androidtpv.controller.activities.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bakery.dam.androidtpv.R;
import com.bakery.dam.androidtpv.controller.managers.UserLoginManager;
import com.bakery.dam.androidtpv.controller.services.TipoService;
import com.bakery.dam.androidtpv.model.Tipo;
import com.bakery.dam.androidtpv.model.UserToken;
import com.bakery.dam.androidtpv.util.CustomProperties;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static Retrofit retrofit;
    private TipoService tipoService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        retrofit = new Retrofit.Builder()
                .baseUrl(CustomProperties.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        tipoService = retrofit.create(TipoService.class);
        Tipo t = new Tipo();
        t.setNombre("Pasteles");
        t.setDescripcion("Pasteles");
        tipoService.createTipo(t).enqueue(new Callback<Tipo>() {
            @Override
            public void onResponse(Call<Tipo> call, Response<Tipo> response) {
                if (response.isSuccessful()) {
                    System.out.println("tipo creado");

                }
            }

            @Override
            public void onFailure(Call<Tipo> call, Throwable t) {
                System.out.println("tipo no creado" + t);
            }
        });

    }


}

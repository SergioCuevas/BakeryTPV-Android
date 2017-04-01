package com.bakery.dam.androidtpv.controller.managers;

import android.util.Log;

import com.bakery.dam.androidtpv.controller.services.OfferService;
import com.bakery.dam.androidtpv.controller.services.ProductService;
import com.bakery.dam.androidtpv.controller.services.TicketService;
import com.bakery.dam.androidtpv.controller.services.TipoService;
import com.bakery.dam.androidtpv.model.Oferta;
import com.bakery.dam.androidtpv.model.Producto;
import com.bakery.dam.androidtpv.model.Ticket;
import com.bakery.dam.androidtpv.model.Tipo;
import com.bakery.dam.androidtpv.util.CustomProperties;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by DAM on 23/3/17.
 */

public class TipoManager {
    private static TipoManager ourInstance;
    private List<Tipo> tipos;
    private Retrofit retrofit;
    private TipoService tipoService;

    private TipoManager() {
        retrofit = new Retrofit.Builder()
                .baseUrl(CustomProperties.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())

                .build();
        tipoService = retrofit.create(TipoService.class);
    }

    public static TipoManager getInstance() {
        if (ourInstance == null) {
            ourInstance = new TipoManager();
        }

        return ourInstance;
    }

    public synchronized void getAllTipos(final TipoCallback productCallback) {
        Call<List<Tipo>> call = tipoService.getAllTipos(UserLoginManager.getInstance().getBearerToken());

        call.enqueue(new Callback<List<Tipo>>() {
            @Override
            public void onResponse(Call<List<Tipo>> call, Response<List<Tipo>> response) {
                tipos = response.body();
                int code = response.code();

                if (code == 200 || code == 201) {
                    productCallback.onSuccessTipo(tipos);

                } else {
                    productCallback.onFailure(new Throwable("ERROR" + code + ", " + response.raw().message()));
                }
            }

            @Override
            public void onFailure(Call<List<Tipo>> call, Throwable t) {
                Log.e("TeamManager->", t.toString());
                productCallback.onFailure(t);
            }
        });
    }

}

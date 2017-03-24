package com.bakery.dam.androidtpv.controller.managers;

import android.util.Log;

import com.bakery.dam.androidtpv.controller.services.OfferService;
import com.bakery.dam.androidtpv.controller.services.ProductService;
import com.bakery.dam.androidtpv.controller.services.TicketService;
import com.bakery.dam.androidtpv.model.Oferta;
import com.bakery.dam.androidtpv.model.Producto;
import com.bakery.dam.androidtpv.model.Ticket;
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

public class OfferManager {
    private static OfferManager ourInstance;
    private List<Oferta> offers;
    private Retrofit retrofit;
    private OfferService offerService;

    private OfferManager() {
        retrofit = new Retrofit.Builder()
                .baseUrl(CustomProperties.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())

                .build();
        offerService = retrofit.create(OfferService.class);
    }

    public static OfferManager getInstance() {
        if (ourInstance == null) {
            ourInstance = new OfferManager();
        }

        return ourInstance;
    }

    /* GET - GET ALL TEAMS */

    public synchronized void getOffersByTicket(final OfferCallback offerCallback, long id) {
        Call<List<Oferta>> call = offerService.getOffersByTicket(UserLoginManager.getInstance().getBearerToken(), id);

        call.enqueue(new Callback<List<Oferta>>() {
            @Override
            public void onResponse(Call<List<Oferta>> call, Response<List<Oferta>> response) {
                offers = response.body();

                int code = response.code();

                if (code == 200 || code == 201) {
                    offerCallback.onSuccessOffer(offers);
                } else {
                    offerCallback.onFailure(new Throwable("ERROR" + code + ", " + response.raw().message()));
                }
            }

            @Override
            public void onFailure(Call<List<Oferta>> call, Throwable t) {
                Log.e("TeamManager->", t.toString());
                offerCallback.onFailure(t);
            }

        });
    }
}

package com.bakery.dam.androidtpv.controller.services;

import com.bakery.dam.androidtpv.model.Oferta;
import com.bakery.dam.androidtpv.model.Producto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by DAM on 24/3/17.
 */

public interface OfferService {


    @GET("api/ofertas")
    Call<List<Oferta>> getAllOffers(
            @Header("Authorization") String Authorization
    );


    @GET("api/tickets/ofertas/{id}")
    Call<List<Oferta>> getOffersByTicket(

            @Header("Authorization") String Authorization,
            @Path("id") long id);

    @POST("api/ofertas")
    Call<Producto> createOffer(
            @Header("Authorization") String Authorization,
            @Body Oferta offer

    );

    @PUT("api/ofertas")
    Call<Producto> updateOffer(

    );

}

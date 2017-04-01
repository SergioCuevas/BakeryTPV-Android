package com.bakery.dam.androidtpv.controller.services;

import com.bakery.dam.androidtpv.model.Producto;
import com.bakery.dam.androidtpv.model.Ticket;
import com.bakery.dam.androidtpv.model.Tipo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by DAM on 24/2/17.
 */

public interface TipoService {
    @GET("/api/tipos")
    Call<List<Tipo>> getAllTipos(
            @Header("Authorization") String Authorization
    );

    @DELETE("api/tipos/{id}")
    Call<Void> deleteTipo(

            @Header("Authorization") String Authorization,
            @Path("id") long id);

    @POST("api/tipos")
    Call<Tipo> createTicket(
            @Header("Authorization") String Authorization,
            @Body Tipo tipo

    );
}

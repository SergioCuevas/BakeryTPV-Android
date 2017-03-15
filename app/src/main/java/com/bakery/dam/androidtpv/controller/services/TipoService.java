package com.bakery.dam.androidtpv.controller.services;

import com.bakery.dam.androidtpv.model.Tipo;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by DAM on 8/2/17.
 */

public interface TipoService {

    @POST("api/tipos")
    Call<Tipo> createTipo(@Header("Authorization")String authorization, @Body Tipo tipo);
}

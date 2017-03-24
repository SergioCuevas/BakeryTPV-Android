package com.bakery.dam.androidtpv.controller.services;

/**
 * Created by DAM on 2/2/17.
 */

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

public interface ProductService {

    @GET("api/productos")
    Call<List<Producto>> getAllProductos(
            @Header("Authorization") String Authorization
    );


    @GET("api/tickets/productos/{id}")
    Call<List<Producto>> getProducts(

            @Header("Authorization") String Authorization,
            @Path("id") long id);

    @POST("api/productos")
    Call<Producto> createProduct(
            @Header("Authorization") String Authorization,
            @Body Producto product

    );

    @PUT("api/productos")
    Call<Producto> updateProduct(

    );

}

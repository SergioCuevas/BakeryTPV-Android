package com.bakery.dam.androidtpv.controller.services;

/**
 * Created by DAM on 2/2/17.
 */

import com.bakery.dam.androidtpv.model.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface ProductService {

    @GET("api/productos")
    Call<List<Product>> getAllProductos(
            @Header("Authorization") String Authorization
    );

    @POST("api/productos")
    Call<Product> createProduct(
            @Header("Authorization") String Authorization,
            @Body Product product

    );

    @PUT("api/productos")
    Call<Product> updateProduct(

    );

}

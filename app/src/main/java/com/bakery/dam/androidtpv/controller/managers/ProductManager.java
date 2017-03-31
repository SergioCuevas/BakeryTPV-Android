package com.bakery.dam.androidtpv.controller.managers;

import android.util.Log;

import com.bakery.dam.androidtpv.controller.services.ProductService;
import com.bakery.dam.androidtpv.model.Producto;
import com.bakery.dam.androidtpv.util.CustomProperties;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by DAM on 23/3/17.
 */

public class ProductManager {
    private static ProductManager ourInstance;
    private List<Producto> products;
    private Retrofit retrofit;
    private ProductService productService;

    private ProductManager() {
        retrofit = new Retrofit.Builder()
                .baseUrl(CustomProperties.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())

                .build();
        productService = retrofit.create(ProductService.class);
    }

    public static ProductManager getInstance() {
        if (ourInstance == null) {
            ourInstance = new ProductManager();
        }

        return ourInstance;
    }

    /* GET - GET ALL TEAMS */
    public synchronized void getAllProductos(final ProductCallback productCallback) {
        Call<List<Producto>> call = productService.getAllProductos(UserLoginManager.getInstance().getBearerToken());

        call.enqueue(new Callback<List<Producto>>() {
            @Override
            public void onResponse(Call<List<Producto>> call, Response<List<Producto>> response) {
                products = response.body();
                int code = response.code();

                if (code == 200 || code == 201) {
                    productCallback.onSuccess(products);

                } else {
                    productCallback.onFailure(new Throwable("ERROR" + code + ", " + response.raw().message()));
                }
            }

            @Override
            public void onFailure(Call<List<Producto>> call, Throwable t) {
                Log.e("TeamManager->", t.toString());
                productCallback.onFailure(t);
            }
        });
    }

    public synchronized void getProductsByTicket(final ProductCallback productCallback, long id) {
        Call<List<Producto>> call = productService.getProducts(UserLoginManager.getInstance().getBearerToken(), id);

        call.enqueue(new Callback<List<Producto>>() {
            @Override
            public void onResponse(Call<List<Producto>> call, Response<List<Producto>> response) {
                products = response.body();

                int code = response.code();

                if (code == 200 || code == 201) {
                    productCallback.onSuccess(products);
                } else {
                    productCallback.onFailure(new Throwable("ERROR" + code + ", " + response.raw().message()));
                }
            }

            @Override
            public void onFailure(Call<List<Producto>> call, Throwable t) {
                Log.e("TeamManager->", t.toString());
                productCallback.onFailure(t);
            }

        });
    }
}

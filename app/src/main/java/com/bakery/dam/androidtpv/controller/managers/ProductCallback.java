package com.bakery.dam.androidtpv.controller.managers;

import com.bakery.dam.androidtpv.model.Producto;
import com.bakery.dam.androidtpv.model.Ticket;

import java.util.List;

/**
 * Created by DAM on 23/3/17.
 */

public interface ProductCallback {
    void onSuccess(Object product);
    void onFailure(Throwable t);
}

package com.bakery.dam.androidtpv.controller.managers;

import com.bakery.dam.androidtpv.model.Oferta;
import com.bakery.dam.androidtpv.model.Producto;

import java.util.List;

/**
 * Created by DAM on 24/3/17.
 */

public interface OfferCallback {
    void onSuccessOffer(List<Oferta> offer);
    void onFailure(Throwable t);
}

package com.bakery.dam.androidtpv.controller.managers;

import com.bakery.dam.androidtpv.model.Ticket;
import com.bakery.dam.androidtpv.model.UserToken;

import java.util.List;

/**
 * Created by DAM on 24/2/17.
 */

public interface TicketCallback {
    void onSuccess(List<Ticket> ticket);
    void onFailure(Throwable t);
}

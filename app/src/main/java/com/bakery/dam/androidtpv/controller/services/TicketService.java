package com.bakery.dam.androidtpv.controller.services;

import com.bakery.dam.androidtpv.model.Ticket;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

/**
 * Created by DAM on 24/2/17.
 */

public interface TicketService {
    @GET("/api/tickets")
    Call<List<Ticket>> getAllTickets(
            @Header("Authorization") String Authorization
    );
}

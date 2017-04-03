package com.bakery.dam.androidtpv.controller.services;

import com.bakery.dam.androidtpv.model.Oferta;
import com.bakery.dam.androidtpv.model.Producto;
import com.bakery.dam.androidtpv.model.Ticket;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by DAM on 24/2/17.
 */

public interface TicketService {
    @GET("/api/tickets")
    Call<List<Ticket>> getAllTickets(
            @Header("Authorization") String Authorization
    );

    @GET("api/tickets/{id}")
    Call<List<Ticket>> getTicketById(

            @Header("Authorization") String Authorization,
            @Path("id") long id);

    @DELETE("api/tickets/{id}")
    Call<Void> deleteTicket(

            @Header("Authorization") String Authorization,
            @Path("id") long id);

    @POST("api/tickets")
    Call<Ticket> createTicket(
            @Header("Authorization") String Authorization,
            @Body Ticket ticket

    );

    @PUT("api/tickets/producto/add/{id}")
    Call<Ticket> updateTicketProducto(
            @Header("Authorization") String Authorization,
            @Body Producto producto, @Path("id") long id

    );

    @PUT("api/tickets/oferta/add/{id}")
    Call<Ticket> updateTicketOferta(
            @Header("Authorization") String Authorization,
            @Body Oferta oferta, @Path("id") long id

    );
}

package com.bakery.dam.androidtpv.controller.managers;

import android.util.Log;

import com.bakery.dam.androidtpv.controller.services.TicketService;
import com.bakery.dam.androidtpv.model.Ticket;
import com.bakery.dam.androidtpv.util.CustomProperties;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by DAM on 24/2/17.
 */

public class TicketManager {
    private static TicketManager ourInstance;
    private List<Ticket> tickets;
    private Retrofit retrofit;
    private TicketService ticketService;

    private TicketManager() {
        retrofit = new Retrofit.Builder()
                .baseUrl(CustomProperties.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())

                .build();

        ticketService = retrofit.create(TicketService.class);
    }

    public static TicketManager getInstance() {
        if (ourInstance == null) {
            ourInstance = new TicketManager();
        }

        return ourInstance;
    }

    /* GET - GET ALL TEAMS */

    public synchronized void getAllTickets(final TicketCallback ticketCallback) {
        Call<List<Ticket>> call = ticketService.getAllTickets(UserLoginManager.getInstance().getBearerToken());

        call.enqueue(new Callback<List<Ticket>>() {
            @Override
            public void onResponse(Call<List<Ticket>> call, Response<List<Ticket>> response) {
                tickets = response.body();

                int code = response.code();

                if (code == 200 || code == 201) {
                    ticketCallback.onSuccessTicket(tickets);
                } else {
                    ticketCallback.onFailure(new Throwable("ERROR" + code + ", " + response.raw().message()));
                }
            }

            @Override
            public void onFailure(Call<List<Ticket>> call, Throwable t) {
                Log.e("TeamManager->", t.toString());
                ticketCallback.onFailure(t);
            }

        });
    }
    public synchronized void getTicketById(final TicketCallback ticketCallback, long id) {
        Call<List<Ticket>> call = ticketService.getTicketById(UserLoginManager.getInstance().getBearerToken(), id);

        call.enqueue(new Callback<List<Ticket>>() {
            @Override
            public void onResponse(Call<List<Ticket>> call, Response<List<Ticket>> response) {
                tickets = response.body();

                int code = response.code();

                if (code == 200 || code == 201) {
                    ticketCallback.onSuccessTicket(tickets);
                } else {
                    ticketCallback.onFailure(new Throwable("ERROR" + code + ", " + response.raw().message()));
                }
            }

            @Override
            public void onFailure(Call<List<Ticket>> call, Throwable t) {
                Log.e("TeamManager->", t.toString());
                ticketCallback.onFailure(t);
            }

        });
    }



}

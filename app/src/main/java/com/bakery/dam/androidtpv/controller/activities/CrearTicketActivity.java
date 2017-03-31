package com.bakery.dam.androidtpv.controller.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bakery.dam.androidtpv.R;
import com.bakery.dam.androidtpv.controller.activities.main.MenuActivity;
import com.bakery.dam.androidtpv.controller.managers.TicketCallback;
import com.bakery.dam.androidtpv.controller.managers.TicketManager;
import com.bakery.dam.androidtpv.model.Ticket;

import java.util.Date;

public class CrearTicketActivity extends AppCompatActivity implements TicketCallback {

    private EditText et;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_ticket);
        et= (EditText) findViewById(R.id.mesanumber);
        btn = (Button) findViewById(R.id.crearticket);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!et.getText().toString().equals("")) {
                    Ticket ticket = new Ticket();
                    String text = et.getText().toString();
                    int mesa = Integer.parseInt(text);
                    ticket.setMesa(mesa);
                    TicketManager.getInstance().createTicket(CrearTicketActivity.this, ticket);
                }
            }
        });
    }

    @Override
    public void onSuccessTicket(Object o) {
        Intent i = new Intent(CrearTicketActivity.this, MenuActivity.class);
        startActivity(i);
    }

    @Override
    public void onFailure(Throwable t) {

    }
}

package com.bakery.dam.androidtpv.controller.activities;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.bakery.dam.androidtpv.R;
import com.bakery.dam.androidtpv.controller.activities.main.MenuActivity;
import com.bakery.dam.androidtpv.controller.managers.TicketCallback;
import com.bakery.dam.androidtpv.controller.managers.TicketManager;
import com.bakery.dam.androidtpv.model.Oferta;
import com.bakery.dam.androidtpv.model.Producto;
import com.bakery.dam.androidtpv.model.Ticket;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

public class CrearTicketActivity extends AppCompatActivity implements TicketCallback {

    private EditText et;
    private Button btn;
    private Toolbar toolbar;
    private LinearLayout cargacrear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View myView = findViewById(R.id.alerta);
        setContentView(R.layout.activity_crear_ticket);
        et= (EditText) findViewById(R.id.mesanumber);
        btn = (Button) findViewById(R.id.crearticket);
        toolbar = (Toolbar) findViewById(R.id.barra);
        cargacrear = (LinearLayout) findViewById(R.id.cargarcreacion);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!et.getText().toString().equals("")&&(Integer.parseInt(et.getText().toString())<=10&&Integer.parseInt(et.getText().toString())>=0)) {
                    toolbar.setVisibility(View.INVISIBLE);
                    btn.setVisibility(View.INVISIBLE);
                    et.setVisibility(View.INVISIBLE);
                    cargacrear.setVisibility(View.VISIBLE);
                    Ticket ticket = new Ticket();
                    String text = et.getText().toString();
                    int mesa = Integer.parseInt(text);
                    ticket.setMesa(mesa);
                    ticket.setCerrado(false);
                    ticket.setCantidad(new BigDecimal(0));
                    ticket.setProductos(new ArrayList<Producto>());
                    ticket.setOfertas(new ArrayList<Oferta>());
                    TicketManager.getInstance().createTicket(CrearTicketActivity.this, ticket);
                } else {
                    Snackbar.make(myView, "El número de la mesa debe estar  entre 0 y 10", Snackbar.LENGTH_SHORT)
                            .show();
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
    public void onSuccessDelete(Object o) {

    }

    @Override
    public void onFailure(Throwable t) {

    }
}

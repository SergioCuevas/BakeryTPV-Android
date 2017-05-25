package com.bakery.dam.androidtpv.controller.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bakery.dam.androidtpv.R;
import com.bakery.dam.androidtpv.controller.activities.main.MenuActivity;
import com.bakery.dam.androidtpv.controller.activities.main.ProductListActivity;
import com.bakery.dam.androidtpv.controller.managers.TicketCallback;
import com.bakery.dam.androidtpv.controller.managers.TicketManager;
import com.bakery.dam.androidtpv.controller.services.TicketService;
import com.bakery.dam.androidtpv.model.Ticket;

public class CambiarMesaActivity extends AppCompatActivity implements TicketCallback{

    public long id;
    private EditText et;
    private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiar_mesa);
        et= (EditText) findViewById(R.id.mesanumber);
        btn = (Button) findViewById(R.id.modificarticket);
        Intent intent=this.getIntent();
        id= intent.getLongExtra("id", 0);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!et.getText().toString().equals("")) {

                    String text = et.getText().toString();
                    int mesa = Integer.parseInt(text);
                    TicketManager.getInstance().updateTicketMesa(CambiarMesaActivity.this, id,mesa);

                }
            }
        });
    }

    @Override
    public void onSuccessTicket(Object o) {
        Intent i = new Intent(CambiarMesaActivity.this, ProductListActivity.class);
        i.putExtra("id", id);
        startActivity(i);
    }

    @Override
    public void onSuccessDelete(Object o) {

    }

    @Override
    public void onFailure(Throwable t) {

    }
}
package com.bakery.dam.androidtpv.controller.activities.main;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bakery.dam.androidtpv.R;
import com.bakery.dam.androidtpv.controller.activities.login.LoginActivity;
import com.bakery.dam.androidtpv.controller.managers.TicketCallback;
import com.bakery.dam.androidtpv.controller.managers.TicketManager;
import com.bakery.dam.androidtpv.controller.services.TicketService;
import com.bakery.dam.androidtpv.model.Ticket;

import java.util.List;

import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements TicketCallback{

    private RecyclerView recyclerView;

    private static Retrofit retrofit;
    private TicketService ticketService;
    private List<Ticket> tickets;
    private RecyclerView llista;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        llista= (RecyclerView) findViewById(R.id.llista);
        TicketManager.getInstance().getAllTickets(MainActivity.this);


    }

    @Override
    public void onSuccess(List<Ticket> ticket) {
        tickets = ticket;
        llista.setAdapter(new SimpleItemRecyclerViewAdapter(tickets));
    }

    @Override
    public void onFailure(Throwable t) {
        Intent i = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(i);
        finish();
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<Ticket> ticketsView;

        public SimpleItemRecyclerViewAdapter(List<Ticket> items) {
            ticketsView = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.llista_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            holder.mItem = ticketsView.get(position);
            holder.mMesaView.setText(ticketsView.get(position).getMesa().toString());
            holder.mPrecioView.setText(ticketsView.get(position).getCantidad()+"€");
            holder.mDateView.setText(ticketsView.get(position).getFecha().toString());
            Typeface face= Typeface.createFromAsset(getAssets(),"fonts/montserrat.ttf");
            holder.mMesaView.setTypeface(face);
            Typeface faceb= Typeface.createFromAsset(getAssets(), "fonts/montserratb.ttf");
            holder.mPrecioView.setTypeface(faceb);
            holder.mTableView.setImageResource(R.drawable.table);
        }

        @Override
        public int getItemCount() {
            return ticketsView.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mMesaView;
            public final TextView mPrecioView;
            public final TextView mDateView;
            public final ImageView mTableView;
            public Ticket mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mMesaView = (TextView) view.findViewById(R.id.mesa);
                mPrecioView = (TextView) view.findViewById(R.id.precio);
                mDateView = (TextView) view.findViewById(R.id.fecha);
                mTableView= (ImageView) view.findViewById(R.id.table);
            }


        }
    }

}

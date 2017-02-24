package com.bakery.dam.androidtpv.controller.activities.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bakery.dam.androidtpv.R;
import com.bakery.dam.androidtpv.controller.activities.login.LoginActivity;
import com.bakery.dam.androidtpv.controller.managers.TicketCallback;
import com.bakery.dam.androidtpv.controller.managers.TicketManager;
import com.bakery.dam.androidtpv.controller.managers.UserLoginManager;
import com.bakery.dam.androidtpv.controller.services.TicketService;
import com.bakery.dam.androidtpv.controller.services.TipoService;
import com.bakery.dam.androidtpv.model.Ticket;
import com.bakery.dam.androidtpv.model.Tipo;
import com.bakery.dam.androidtpv.model.UserToken;
import com.bakery.dam.androidtpv.util.CustomProperties;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements TicketCallback{

    private static Retrofit retrofit;
    private TicketService ticketService;
    private List<Ticket> tickets;
    private ListView llista;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        llista= (ListView) findViewById(R.id.ticketList);
        TicketManager.getInstance().getAllTickets(MainActivity.this);



    }

    @Override
    public void onSuccess(List<Ticket> ticket) {
        tickets = ticket;
        PartsAdapter adapter = new PartsAdapter(this, tickets);
        llista.setAdapter(adapter);
    }

    @Override
    public void onFailure(Throwable t) {
        Intent i = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(i);
        finish();
    }

    public class PartsAdapter extends BaseAdapter {
        private Context context;
        private List<Ticket> tickets;
        public PartsAdapter(Context context, List<Ticket> tickets){
            this.context=context;
            this.tickets=tickets;
        }
        @Override
        public int getCount() {
            return tickets.size();
        }

        @Override
        public Object getItem(int position) {
            return tickets.get(position);
        }

        @Override
        public long getItemId(int position) {
            int id= tickets.get(position).getCantidad();
            return id;
        }

        public class ViewHolder{
            public TextView tvMesa;
            public TextView tvFecha;
            public TextView tvPrecio;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View myView =convertView;
            if(myView ==null) {
                //Inflo la lista con el layout que he creado (llista_item)
                LayoutInflater inflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
                myView = inflater.inflate(R.layout.llista_item, parent, false);
                ViewHolder holder= new ViewHolder();
                holder.tvMesa=(TextView) myView.findViewById(R.id.mesa);
                holder.tvFecha=(TextView) myView.findViewById(R.id.fecha);
                holder.tvPrecio=(TextView) myView.findViewById(R.id.precio);
                myView.setTag(holder);
            }
            ViewHolder holder= (ViewHolder) myView.getTag();

            //Voy asignando los datos
            Ticket ticket = tickets.get(position);
            String mesa=ticket.getMesa()+"";
            String precio = ticket.getCantidad()+"€";
            String fecha = ticket.getFecha()+"";

            holder.tvMesa.setText(mesa);
            holder.tvFecha.setText(fecha);
            holder.tvPrecio.setText(precio);

            return myView;
        }
    }

}

package com.bakery.dam.androidtpv.controller.activities.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bakery.dam.androidtpv.R;

import com.bakery.dam.androidtpv.controller.activities.Dialogs.TicketDialog;
import com.bakery.dam.androidtpv.controller.activities.login.LoginActivity;
import com.bakery.dam.androidtpv.controller.managers.TicketCallback;
import com.bakery.dam.androidtpv.controller.managers.TicketManager;
import com.bakery.dam.androidtpv.controller.services.TicketService;
import com.bakery.dam.androidtpv.model.Ticket;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements TicketCallback, TicketDialog.TicketDialogListener{

    private RecyclerView recyclerView;

    private static Retrofit retrofit;
    private TicketService ticketService;
    private List<Ticket> tickets;
    private ListView llista;
    private FloatingActionButton add;
    private DialogFragment newFragment = new TicketDialog();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        llista= (ListView) findViewById(R.id.llista);
        TicketManager.getInstance().getAllTickets(MainActivity.this);
        add = (FloatingActionButton) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, MenuActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onSuccessTicket(Object ticket) {
        tickets = (List<Ticket>) ticket;
        llista.setAdapter(new PartsAdapter(this, tickets));
    }

    @Override
    public void onFailure(Throwable t) {
        Intent i = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        newFragment.dismiss();
        EditText mesa = (EditText) findViewById(R.id.numMesa);
        int num = Integer.parseInt(String.valueOf(mesa.getText()));
        Toast.makeText(
                this,
                "Seleccionaste:" + num,
                Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }

    public class PartsAdapter extends BaseAdapter {
        private Context context;
        private List<Ticket> tickets;
        public PartsAdapter(Context context, List<Ticket> tickets){
            this.context=context;
            tickets.add(new Ticket());
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
            int id= (int) tickets.get(position).getCantidad();
            return id;
        }

        public class ViewHolder{
            public TextView tvMesa;
            public TextView tvFecha;
            public TextView tvPrecio;
            public ImageView ivImage;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (tickets.get(position).getMesa() != null) {
                View myView = convertView;
                if (myView == null) {
                    //Inflo la lista con el layout que he creado (llista_item)
                    LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
                    myView = inflater.inflate(R.layout.llista_item, parent, false);
                    ViewHolder holder = new ViewHolder();
                    holder.tvMesa = (TextView) myView.findViewById(R.id.mesa);
                    holder.tvFecha = (TextView) myView.findViewById(R.id.fecha);
                    holder.tvPrecio = (TextView) myView.findViewById(R.id.precio);
                    holder.ivImage = (ImageView) myView.findViewById(R.id.table);
                    myView.setTag(holder);
                }
                ViewHolder holder = (ViewHolder) myView.getTag();

                //Voy asignando los datos
                Ticket ticket = tickets.get(position);
                Date date=new Date();
                String mesa = ticket.getMesa() + "";
                String precio = ticket.getCantidad() + "€";
                String fecha = ticket.getFecha() + "";
                fecha=fecha.substring(11, 16);
                if (!"0".equals(mesa)) {
                    holder.tvMesa.setText(mesa);
                    holder.ivImage.setImageResource(R.drawable.local);

                } else {
                    holder.ivImage.setImageResource(R.drawable.llevar);
                    holder.tvMesa.setText("");
                }
                holder.tvPrecio.setText(precio);
                holder.tvFecha.setText(fecha);

                return myView;

            } else {
                View myView;
                //Inflo la lista con el layout que he creado (llista_item)
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
                myView = inflater.inflate(R.layout.llista_item, parent, false);
                ViewHolder holder = new ViewHolder();
                holder.tvMesa = (TextView) myView.findViewById(R.id.mesa);
                holder.tvFecha = (TextView) myView.findViewById(R.id.fecha);
                holder.tvPrecio = (TextView) myView.findViewById(R.id.precio);
                holder.ivImage = (ImageView) myView.findViewById(R.id.table);
                myView.setTag(holder);
                return myView;
            }
        }
    }

}

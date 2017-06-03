package com.bakery.dam.androidtpv.controller.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bakery.dam.androidtpv.R;
import com.bakery.dam.androidtpv.controller.activities.main.BaseActivity;
import com.bakery.dam.androidtpv.controller.activities.main.MenuActivity;
import com.bakery.dam.androidtpv.controller.activities.main.ProductListActivity;
import com.bakery.dam.androidtpv.controller.managers.OfferCallback;
import com.bakery.dam.androidtpv.controller.managers.OfferManager;
import com.bakery.dam.androidtpv.controller.managers.ProductCallback;
import com.bakery.dam.androidtpv.controller.managers.ProductManager;
import com.bakery.dam.androidtpv.controller.managers.TicketCallback;
import com.bakery.dam.androidtpv.controller.managers.TicketManager;
import com.bakery.dam.androidtpv.model.Oferta;
import com.bakery.dam.androidtpv.model.Producto;
import com.bakery.dam.androidtpv.model.Ticket;
import com.daimajia.swipe.SwipeLayout;

import java.util.ArrayList;
import java.util.List;

public class CobrarActivity extends BaseActivity implements TicketCallback, ProductCallback, OfferCallback{
    TextView precio;
    ListView lista;
    Button cobrar;
    private Long id;
    Ticket ts;
    private List<Object> productsAndOffers = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cobrar);
        precio = (TextView) findViewById(R.id.precio);
        lista = (ListView) findViewById(R.id.listcobrar);
        cobrar = (Button) findViewById(R.id.cobrarticket);

        cobrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TicketManager.getInstance().updateTicketCerrado(CobrarActivity.this, ts);
            }
        });

    }

    @Override
    public void onResume()
    {  // After a pause OR at startup
        super.onResume();
        //Refresh your stuff here
        Intent intent=this.getIntent();
        id= intent.getLongExtra("id", 0);
        productsAndOffers = new ArrayList<>();
        TicketManager.getInstance().getTicketById(CobrarActivity.this, id);

        OfferManager.getInstance().getOffersByTicket(CobrarActivity.this, id);
        ProductManager.getInstance().getProductsByTicket(CobrarActivity.this, id);
    }

    @Override
    public void onSuccessOffer(Object offer) {
        if(offer instanceof ArrayList) {
            for (Oferta o : (ArrayList<Oferta>)offer) {
                productsAndOffers.add(o);
            }
        }
        lista.setAdapter(new CobrarActivity.ProductsAdapter(this, productsAndOffers));
    }

    @Override
    public void onSuccess(Object product) {
        if(product instanceof ArrayList) {
            for (Producto p : (ArrayList<Producto>)product) {
                productsAndOffers.add(p);
            }
        }
        lista.setAdapter(new CobrarActivity.ProductsAdapter(this, productsAndOffers));
    }

    @Override
    public void onSuccessTicket(Object o) {
        ts = (Ticket) o;
        if(ts.getCerrado()){
            Intent i = new Intent(CobrarActivity.this, MenuActivity.class);
            startActivity(i);
        }
        precio.setText(""+ts.getCantidad());
    }

    @Override
    public void onSuccessDelete(Object o) {

    }

    @Override
    public void onFailure(Throwable t) {

    }

    public class ProductsAdapter extends BaseAdapter {
        private Context context;
        private List<Object> products;
        public ProductsAdapter(Context context, List<Object> products){
            this.context=context;
            this.products=products;
        }
        @Override
        public int getCount() {
            return products.size();
        }

        @Override
        public Object getItem(int position) {
            return products.get(position);
        }

        @Override
        public long getItemId(int position) {
            int id= 0;
            return id;
        }

        public class ViewHolder{
            public TextView tvNombre;
        }

        @Override
        public View getView(final int position, final View convertView, ViewGroup parent) {
            View myView = convertView;
            if (myView == null) {
                //Inflo la lista con el layout que he creado (llista_item)
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
                myView = inflater.inflate(R.layout.cobrar_item_list, parent, false);
                final CobrarActivity.ProductsAdapter.ViewHolder holder = new CobrarActivity.ProductsAdapter.ViewHolder();
                holder.tvNombre = (TextView) myView.findViewById(R.id.nombreProductooferta);
                myView.setTag(holder);
            }
            CobrarActivity.ProductsAdapter.ViewHolder holder = (CobrarActivity.ProductsAdapter.ViewHolder) myView.getTag();

            //Voy asignando los datos
            if(products.get(position) instanceof Producto){
                Producto producto = (Producto) products.get(position);
                String nombre = producto.getNombre() + "";
                holder.tvNombre.setText(nombre);
            } else {
                Oferta oferta = (Oferta) products.get(position);
                String nombre = oferta.getNombre() + "";
                holder.tvNombre.setText(nombre);
            }
            return myView;
        }
    }
}

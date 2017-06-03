package com.bakery.dam.androidtpv.controller.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.bakery.dam.androidtpv.model.LineaOferta;
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
    FloatingActionButton separar;
    private Long id;
    public boolean porSeparado=false;
    LinearLayout contenido;
    Ticket ts;
    private List<Object> productsAndOffers = new ArrayList<>();
    private ArrayList<Object> poSeparado = new ArrayList<>();
    private List<Boolean> seleccionado = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cobrar);
        precio = (TextView) findViewById(R.id.precio);
        lista = (ListView) findViewById(R.id.listcobrar);
        cobrar = (Button) findViewById(R.id.cobrarticket);
        separar = (FloatingActionButton) findViewById(R.id.separado);
        contenido = (LinearLayout) findViewById(R.id.contenido);
        cobrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(porSeparado==false) {
                    TicketManager.getInstance().updateTicketCerrado(CobrarActivity.this, ts);
                } else {
                    poSeparado = new ArrayList<>();
                    Ticket t = ts;
                    t.setProductos(new ArrayList<Producto>());
                    t.setOfertas(new ArrayList<Oferta>());
                    t.setCantidad(0);
                    for(int i = 0; i<productsAndOffers.size(); i++){
                        if(seleccionado.get(i)==true){
                            poSeparado.add(productsAndOffers.get(i));
                            if(productsAndOffers.get(i) instanceof Producto) {
                                t.getProductos().add((Producto) productsAndOffers.get(i));
                                t.setCantidad(t.getCantidad()+((Producto) productsAndOffers.get(i)).getPrecio());
                            } else {
                                t.getOfertas().add((Oferta) productsAndOffers.get(i));
                                t.setCantidad(t.getCantidad()+((Oferta) productsAndOffers.get(i)).getPrecio());
                            }
                        }
                    }
                    t.setCerrado(true);
                    t.setId(null);
                    TicketManager.getInstance().createTicket(CobrarActivity.this, t);
                    TicketManager.getInstance().updateTicketSeparado(CobrarActivity.this, ts, poSeparado);
                }
            }
        });

        separar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(porSeparado==false) {
                    porSeparado = true;
                    contenido.setBackgroundColor(getResources().getColor(R.color.Green));
                }
                else {
                    porSeparado = false;
                    contenido.setBackgroundColor(getResources().getColor(R.color.LightSlateGray));
                    for (int i = 0; i < lista.getChildCount(); i++)
                    {
                        View v = lista.getChildAt(i);
                        v.setBackgroundColor(getResources().getColor(R.color.White));
                        seleccionado.set(i, false);
                    }
                }
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
                seleccionado.add(false);
            }
        }
        iniciarLista();
    }

    @Override
    public void onSuccess(Object product) {
        if(product instanceof ArrayList) {
            for (Producto p : (ArrayList<Producto>)product) {
                productsAndOffers.add(p);
                seleccionado.add(false);
            }
        }
        iniciarLista();
    }

    public void iniciarLista(){
        lista.setAdapter(new CobrarActivity.ProductsAdapter(this, productsAndOffers));
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(porSeparado==true){
                    int color = 0;
                    Drawable background = view.getBackground();
                    if (background instanceof ColorDrawable) {
                        color = ((ColorDrawable) background).getColor();
                    }
                    if(color==getResources().getColor(R.color.Green)){
                        view.setBackgroundColor(getResources().getColor(R.color.White));
                        seleccionado.set(i, false);
                    } else {
                        view.setBackgroundColor(getResources().getColor(R.color.Green));
                        seleccionado.set(i, true);
                    }
                }
            }
        });
    }

    @Override
    public void onSuccessTicket(Object o) {
        if(o instanceof Ticket) {
            if (((Ticket) o).getCerrado() == false) {
                ts = (Ticket) o;
                if (ts.getCerrado()) {
                    Intent i = new Intent(CobrarActivity.this, MenuActivity.class);
                    startActivity(i);
                }
                precio.setText("" + ts.getCantidad());
            } else {
                for(Object obj : poSeparado){
                    if(obj instanceof Producto) {
                        for (int i = productsAndOffers.size()-1; i >=0;i--){
                            if(productsAndOffers.get(i) instanceof Producto) {
                                if (productsAndOffers.get(i) == ((Producto) obj).getId()) {
                                    productsAndOffers.remove(i);
                                    i = -1;
                                }
                            }
                        }
                    } else {
                        productsAndOffers.remove(obj);
                    }

                }
                iniciarLista();
            }
        }
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
                if(seleccionado.get(position)==false){
                    myView.setBackgroundColor(getResources().getColor(R.color.White));
                } else{
                    myView.setBackgroundColor(getResources().getColor(R.color.Green));
                }
            } else {
                Oferta oferta = (Oferta) products.get(position);
                String nombre = oferta.getNombre() + "";
                holder.tvNombre.setText(nombre);
            }
            return myView;
        }
    }
}

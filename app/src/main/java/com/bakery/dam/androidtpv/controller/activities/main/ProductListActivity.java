package com.bakery.dam.androidtpv.controller.activities.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bakery.dam.androidtpv.R;
import com.bakery.dam.androidtpv.controller.activities.CambiarMesaActivity;
import com.bakery.dam.androidtpv.controller.activities.CreacionTicketActivity;
import com.bakery.dam.androidtpv.controller.managers.OfferCallback;
import com.bakery.dam.androidtpv.controller.managers.OfferManager;
import com.bakery.dam.androidtpv.controller.managers.ProductCallback;
import com.bakery.dam.androidtpv.controller.managers.ProductManager;
import com.bakery.dam.androidtpv.controller.managers.TicketCallback;
import com.bakery.dam.androidtpv.controller.managers.TicketManager;
import com.bakery.dam.androidtpv.model.Oferta;
import com.bakery.dam.androidtpv.model.Producto;
import com.bakery.dam.androidtpv.model.Ticket;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.swipe.SwipeLayout;

import java.util.ArrayList;
import java.util.List;

public class ProductListActivity extends BaseActivity implements ProductCallback, OfferCallback, TicketCallback{
    private ListView llista;
    private List<Producto> productos;
    private List<Object> productsAndOffers = new ArrayList<>();
    private List<Oferta> offers;
    private List<Integer> quantity = new ArrayList<>();
    private ProductManager productManager;
    private TicketManager ticketManager;
    private OfferManager offerManager;
    private TextView tvPrecio;
    private ImageView tvMesa;
    private FloatingActionButton fb;
    private Long id;
    private Toolbar tb;
    private List<Integer> imgs = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imgs.add(R.drawable.takeaway);
        imgs.add(R.drawable.mesa1);
        imgs.add(R.drawable.mesa2);
        imgs.add(R.drawable.mesa3);
        imgs.add(R.drawable.mesa4);
        imgs.add(R.drawable.mesa5);
        imgs.add(R.drawable.mesa6);
        imgs.add(R.drawable.mesa7);
        imgs.add(R.drawable.mesa8);
        imgs.add(R.drawable.mesa9);
        imgs.add(R.drawable.mesa10);
        Activity activity = this;
        //Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbarproductlist);
        setContentView(R.layout.activity_product_list_menu);
        llista= (ListView) findViewById(R.id.productos);
        productsAndOffers = new ArrayList<>();
        fb = (FloatingActionButton) findViewById(R.id.addProduct);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProductListActivity.this, CreacionTicketActivity.class);
                i.putExtra("id", id);
                startActivity(i);
            }
        });
        //tvPrecio= (TextView) findViewById(R.id.preciototal);
        //tvMesa= (ImageView) findViewById(R.id.mesanumero);

        /*tvMesa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProductListActivity.this, CambiarMesaActivity.class);
                i.putExtra("id", id);
                startActivity(i);

            }
        });*/
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onResume()
    {  // After a pause OR at startup
        super.onResume();
        //Refresh your stuff here
        Intent intent=this.getIntent();
        id= intent.getLongExtra("id", 0);
        if(id==0||id==null) {
            SharedPreferences settings = getSharedPreferences("pref", 0);
            long id = settings.getLong("id", 0L);
        }
        productsAndOffers = new ArrayList<>();
        productos = new ArrayList<>();
        offers = new ArrayList<>();
        quantity = new ArrayList<>();
        TicketManager.getInstance().getTicketById(ProductListActivity.this, id);

        OfferManager.getInstance().getOffersByTicket(ProductListActivity.this, id);
        ProductManager.getInstance().getProductsByTicket(ProductListActivity.this, id);
    }
    @Override
    public void onPause(){
        super.onPause();
        SharedPreferences settings = getSharedPreferences("pref",0);
        SharedPreferences.Editor editor = settings.edit();
        // Necessary to clear first if we save preferences onPause.
        editor.clear();
        editor.putLong("id", id);
        editor.commit();
    }

    @Override
    public void onSuccess(Object product) {
        if(product instanceof ArrayList) {
            productos = (List<Producto>) product;
            int position = 0;
            boolean contains;
            for (Producto p : productos) {
                contains = false;
                for (Object productoQ : productsAndOffers) {
                    if (productoQ instanceof Producto) {
                        Producto pr = (Producto) productoQ;
                        if (pr.getId() == p.getId()) {
                            contains = true;
                            position = productsAndOffers.indexOf(pr);
                        }
                    }
                }
                if (contains == false) {

                    productsAndOffers.add(p);
                    quantity.add(1);
                } else {
                    quantity.set(position, quantity.get(position) + 1);
                }
            }
            llista.setAdapter(new ProductListActivity.ProductsAdapter(this, productsAndOffers, quantity));
        }
    }

    @Override
    public void onSuccessOffer(Object offer) {
        offers= (List<Oferta>) offer;
        int position= 0;
        boolean contains = false;
        for(Oferta o : offers){
            for(Object ofertaQ:productsAndOffers){
                if(ofertaQ instanceof Oferta) {
                    Oferta of = (Oferta) ofertaQ;
                    if (of.getId() == o.getId()) {
                        contains = true;
                        position = productsAndOffers.indexOf(of);
                    }
                }
            }
            if(contains==false){
                productsAndOffers.add(o);
                quantity.add(1);
            } else{
                quantity.set(position, quantity.get(position)+1);
            }
        }
        llista.setAdapter(new ProductListActivity.ProductsAdapter(this, productsAndOffers, quantity));
    }

    @Override
    public void onSuccessTicket(Object ticket) {
        Ticket ts = (Ticket) ticket;
        Ticket t=ts;
        //tvMesa.setImageResource(imgs.get(t.getMesa()));
        //tvPrecio.setText(t.getCantidad()+"€");
    }



    @Override
    public void onSuccessDelete(Object o) {
        ProductListActivity.this.onResume();
    }

    @Override
    public void onFailure(Throwable t) {
        int a;
    }

    public class ProductsAdapter extends BaseAdapter {
        private Context context;
        private List<Object> products;
        private List<Integer> quantity;
        public ProductsAdapter(Context context, List<Object> products, List<Integer> quantity){
            this.context=context;
            this.products=products;
            this.quantity=quantity;
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
            public TextView tvCantidad;
            public ImageView ivImage;
            public EditText etCantidadmodicable;
            public SwipeLayout swipeLayout;
        }

        @Override
        public View getView(final int position, final View convertView, ViewGroup parent) {
                View myView = convertView;
                if (myView == null) {
                    //Inflo la lista con el layout que he creado (llista_item)
                    LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
                    myView = inflater.inflate(R.layout.item_product_list, parent, false);
                    final ProductListActivity.ProductsAdapter.ViewHolder holder = new ProductListActivity.ProductsAdapter.ViewHolder();
                    holder.tvNombre = (TextView) myView.findViewById(R.id.nombreProducto);
                    holder.tvCantidad = (TextView) myView.findViewById(R.id.cantidadTotal);
                    holder.ivImage = (ImageView) myView.findViewById(R.id.imagenProducto);
                    holder.etCantidadmodicable = (EditText) myView.findViewById(R.id.cantidadproductostickets);
                    holder.swipeLayout = (SwipeLayout) myView.findViewById(R.id.swipelayout);
                    holder.swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
                        @Override
                        public void onStartOpen(SwipeLayout layout) {

                        }

                        @Override
                        public void onOpen(SwipeLayout layout) {
                            try {
                                int cantidad = Integer.parseInt(holder.etCantidadmodicable.getText().toString());
                                if (productsAndOffers.get(position) instanceof Producto) {
                                    TicketManager.getInstance().deleteTicketProducto(ProductListActivity.this, (Producto) productsAndOffers.get(position), id, cantidad);
                                    holder.swipeLayout.close();
                                }
                                ProductListActivity.this.onResume();
                                Log.d("Borraddoooo", "aaaaa");
                            } catch (Exception e){
                                Snackbar.make(layout, "Debes añadir la cantidad que quieres borrar", Snackbar.LENGTH_SHORT)
                                        .show();
                                holder.swipeLayout.close();
                            }
                        }

                        @Override
                        public void onStartClose(SwipeLayout layout) {

                        }

                        @Override
                        public void onClose(SwipeLayout layout) {

                        }

                        @Override
                        public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {

                        }

                        @Override
                        public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {

                        }
                    });
                    myView.setTag(holder);
                }
                ProductListActivity.ProductsAdapter.ViewHolder holder = (ProductListActivity.ProductsAdapter.ViewHolder) myView.getTag();

                //Voy asignando los datos
                if(products.get(position) instanceof Producto){
                    Producto producto = (Producto) products.get(position);
                    String nombre = producto.getNombre() + "";
                    String cantidad = quantity.get(position) + "";
                    String Image = producto.getImagen();
                    holder.tvNombre.setText(nombre);
                    holder.tvCantidad.setText(cantidad);
                    //holder.etCantidadmodicable.setText(cantidad);
                    if(Image != null) {
                        byte[] imageAsBytes = Base64.decode(Image, Base64.DEFAULT);
                        holder.ivImage.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
                        holder.ivImage.setMaxWidth(80);
                        holder.ivImage.setMaxWidth(80);
                    }else {
                        holder.ivImage.setImageResource(R.drawable.bakerylogoticketsrojo);
                    }
                } else {
                    Oferta oferta = (Oferta) products.get(position);
                    String nombre = oferta.getNombre() + "";
                    String Image = oferta.getImagen();
                    String cantidad = quantity.get(position) + "";
                    holder.tvNombre.setText(nombre);
                    holder.tvCantidad.setText(cantidad);
                    if(Image != null) {
                        byte[] imageAsBytes = Base64.decode(Image, Base64.DEFAULT);
                        holder.ivImage.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
                        holder.ivImage.setMaxWidth(80);
                        holder.ivImage.setMaxWidth(80);
                    }else {
                        holder.ivImage.setImageResource(R.drawable.bakerylogoticketsrojo);
                    }
                }
                return myView;
        }
    }
}

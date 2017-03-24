package com.bakery.dam.androidtpv.controller.activities.main;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bakery.dam.androidtpv.R;
import com.bakery.dam.androidtpv.controller.managers.OfferCallback;
import com.bakery.dam.androidtpv.controller.managers.OfferManager;
import com.bakery.dam.androidtpv.controller.managers.ProductCallback;
import com.bakery.dam.androidtpv.controller.managers.ProductManager;
import com.bakery.dam.androidtpv.controller.managers.TicketManager;
import com.bakery.dam.androidtpv.controller.services.OfferService;
import com.bakery.dam.androidtpv.controller.services.ProductService;
import com.bakery.dam.androidtpv.model.Oferta;
import com.bakery.dam.androidtpv.model.Producto;
import com.bakery.dam.androidtpv.model.Ticket;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductListActivity extends AppCompatActivity implements ProductCallback, OfferCallback{
    private ListView llista;
    private List<Producto> productos;
    private List<Object> productsAndOffers = new ArrayList<>();
    private List<Oferta> offers;
    private List<Integer> quantity = new ArrayList<>();
    private ProductManager productManager;
    private OfferManager offerManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        llista= (ListView) findViewById(R.id.productos);
        ProductManager.getInstance().getProductsByTicket(ProductListActivity.this, 2);
        OfferManager.getInstance().getOffersByTicket(ProductListActivity.this, 2);

    }

    @Override
    public void onSuccess(List<Producto> product) {
        productos=product;
        for(Producto p : productos){
            if(!productsAndOffers.contains(p)){

                productsAndOffers.add(p);
                quantity.add(1);
            } else{
                int position = productsAndOffers.indexOf(p);
                quantity.set(position, quantity.get(position)+1);
            }
        }
    }

    @Override
    public void onSuccessOffer(List<Oferta> offer) {
        offers=offer;
        for(Oferta o : offers){
            if(!productsAndOffers.contains(o)){
                productsAndOffers.add(o);
                quantity.add(1);
            } else{
                int position = productsAndOffers.indexOf(o);
                quantity.set(position, quantity.get(position)+1);
            }
        }
        llista.setAdapter(new ProductListActivity.ProductsAdapter(this, productsAndOffers, quantity));
    }

    @Override
    public void onFailure(Throwable t) {

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
            int id= (int) products.get(position);
            return id;
        }

        public class ViewHolder{
            public TextView tvNombre;
            public TextView tvCantidad;;
            public ImageView ivImage;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
                View myView = convertView;
                if (myView == null) {
                    //Inflo la lista con el layout que he creado (llista_item)
                    LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
                    myView = inflater.inflate(R.layout.item_product_list, parent, false);
                    ProductListActivity.ProductsAdapter.ViewHolder holder = new ProductListActivity.ProductsAdapter.ViewHolder();
                    holder.tvNombre = (TextView) myView.findViewById(R.id.nombreProducto);
                    holder.tvCantidad = (TextView) myView.findViewById(R.id.cantidadTotal);
                    holder.ivImage = (ImageView) myView.findViewById(R.id.imagenProducto);
                    myView.setTag(holder);
                }
                ProductListActivity.ProductsAdapter.ViewHolder holder = (ProductListActivity.ProductsAdapter.ViewHolder) myView.getTag();

                //Voy asignando los datos
                if(products.get(position) instanceof Producto){
                    Producto producto = (Producto) products.get(position);
                    String nombre = producto.getNombre() + "";
                    String cantidad = quantity.get(position) + "";
                    holder.tvNombre.setText(nombre);
                    holder.tvCantidad.setText(cantidad);
                } else {
                    Oferta oferta = (Oferta) products.get(position);
                    String nombre = oferta.getNombre() + "";
                    String cantidad = quantity.get(position) + "";
                    holder.tvNombre.setText(nombre);
                    holder.tvCantidad.setText(cantidad);
                }
                return myView;
        }
    }
}

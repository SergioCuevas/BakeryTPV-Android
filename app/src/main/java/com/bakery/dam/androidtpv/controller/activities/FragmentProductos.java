package com.bakery.dam.androidtpv.controller.activities;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bakery.dam.androidtpv.R;
import com.bakery.dam.androidtpv.controller.managers.ProductCallback;
import com.bakery.dam.androidtpv.controller.managers.ProductManager;
import com.bakery.dam.androidtpv.controller.managers.TicketManager;
import com.bakery.dam.androidtpv.model.Oferta;
import com.bakery.dam.androidtpv.model.Producto;

import java.util.List;

/**
 * Created by DAM on 28/3/17.
 */

public class FragmentProductos extends Fragment implements ProductCallback
{
    TicketManager ticketManager;
    private RecyclerView llista;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {



        view = inflater.inflate(R.layout.fragment_productos, container, false);
        llista= (RecyclerView) view.findViewById(R.id.cardproductosid);
        //ProductManager.getInstance().getAllProductos(FragmentProductos.this);

        return view;
    }

    @Override
    public void onSuccess(List<Producto> product) {
        //llista.setAdapter(new ProductoAdapter(view.getContext(), product));
    }

    @Override
    public void onFailure(Throwable t) {

    }

    public class ProductoAdapter extends BaseAdapter {
        private Context context;
        private List<Producto> products;
        public ProductoAdapter(Context context, List<Producto> products){
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
            int id= (int) products.get(position).getId();
            return id;
        }

        public class ViewHolder{
            public TextView tvNombre;
            public TextView tvDescription;
            public TextView tvPrice;;
            public ImageView ivImage;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View myView = convertView;
            if (myView == null) {

                LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
                myView = inflater.inflate(R.layout.card_productos, parent, false);
                FragmentProductos.ProductoAdapter.ViewHolder holder = new FragmentProductos.ProductoAdapter.ViewHolder();
                holder.tvNombre = (TextView) myView.findViewById(R.id.card_productname);
                holder.tvDescription = (TextView) myView.findViewById(R.id.card_productdescription);
                holder.tvPrice = (TextView) myView.findViewById(R.id.card_productprice);
                holder.ivImage = (ImageView) myView.findViewById(R.id.imageView3);
                myView.setTag(holder);
            }
            FragmentProductos.ProductoAdapter.ViewHolder holder = (FragmentProductos.ProductoAdapter.ViewHolder) myView.getTag();

            //Voy asignando los datos
            if(products.get(position) instanceof Producto){
                Producto producto = (Producto) products.get(position);
                String nombre = producto.getNombre() + "";
                String cantidad = quantity.get(position) + "";
                String description = producto.getDescripcion()+"";
                String price = producto.getPrecio()+"";
                String image = producto.getImagen()+"";

                holder.tvNombre.setText(nombre);
                holder.tvDescription.setText(description);
                holder.tvPrice.setText(price);
            } else {
                Oferta oferta = (Oferta) products.get(position);
                String nombre = oferta.getNombre() + "";
                String cantidad = quantity.get(position) + "";
                holder.tvNombre.setText(nombre);
            }
            if(position%2==0){
                myView.setBackgroundColor(Color.rgb(255, 246 ,238));
            }
            return myView;
        }
    }
}

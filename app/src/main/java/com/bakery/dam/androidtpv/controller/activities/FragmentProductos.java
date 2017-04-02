package com.bakery.dam.androidtpv.controller.activities;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bakery.dam.androidtpv.R;
import com.bakery.dam.androidtpv.controller.managers.OfferCallback;
import com.bakery.dam.androidtpv.controller.managers.OfferManager;
import com.bakery.dam.androidtpv.controller.managers.ProductCallback;
import com.bakery.dam.androidtpv.controller.managers.ProductManager;
import com.bakery.dam.androidtpv.controller.managers.TicketManager;
import com.bakery.dam.androidtpv.controller.managers.TipoCallback;
import com.bakery.dam.androidtpv.controller.managers.TipoManager;
import com.bakery.dam.androidtpv.model.Oferta;
import com.bakery.dam.androidtpv.model.Producto;
import com.bakery.dam.androidtpv.model.Tipo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DAM on 28/3/17.
 */

public class FragmentProductos extends Fragment implements ProductCallback, OfferCallback, TipoCallback
{
    TicketManager ticketManager;
    private ListView llista;
    private Spinner spinner;
    private List<Object> productos = new ArrayList<>();
    private List<String> tipos;
    private EditText search;
    public long id;
    private ImageButton searchButton;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_productos, container, false);
        search = (EditText) view.findViewById(R.id.search);
        searchButton = (ImageButton) view.findViewById(R.id.searchbutton);
        llista= (ListView) view.findViewById(R.id.list_view_productos);
        tipos=new ArrayList<>();
        tipos.add("Todos");
        tipos.add("Ofertas");
        CreacionTicketActivity ca=new CreacionTicketActivity();
        spinner= (Spinner) view.findViewById(R.id.spinnertipos);
        TipoManager.getInstance().getAllTipos(FragmentProductos.this);
        ProductManager.getInstance().getAllProductos(FragmentProductos.this);
        OfferManager.getInstance().getAllOffers(FragmentProductos.this);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!search.getText().toString().equals("")){
                    productos = new ArrayList<>();
                    ProductManager.getInstance().getProductsByNombre(FragmentProductos.this, search.getText().toString());
                    KeyboardUtil k = new KeyboardUtil();
                    k.hideKeyboard((Activity) view.getContext());
                }
            }
        });
        return view;
    }

    public void setId(long id){
        this.id=id;
    }


    public class KeyboardUtil
    {
        public void hideKeyboard(Activity activity)
        {
            try
            {
                InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
            catch (Exception e)
            {
                // Ignore exceptions if any
            }
        }
    }
    @Override
    public void onSuccess(Object product) {
        List<Producto>productosList= (List<Producto>) product;
        for (Producto p:productosList){
            productos.add(p);
        }
        Update();
    }

    @Override
    public void onSuccessOffer(Object offer) {
        List<Oferta>offersList= (List<Oferta>) offer;
        for (Oferta o:offersList){
            productos.add(o);
        }
        ProductoAdapter pa = new ProductoAdapter(this.getContext(), productos);
        llista.setAdapter(pa);
    }


    @Override
    public void onSuccessTipo(Object o) {
        final List<Tipo>tiposList= (List<Tipo>) o;
        for (Tipo t:tiposList){
            tipos.add(t.getNombre());
        }
        spinner.setAdapter(new SpinnerAdapter(this.getContext(), tipos));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(tipos.get(i).equals("Todos")){
                    productos=new ArrayList<>();
                    ProductManager.getInstance().getAllProductos(FragmentProductos.this);
                    OfferManager.getInstance().getAllOffers(FragmentProductos.this);
                } else if(tipos.get(i).equals("Ofertas")){
                    productos=new ArrayList<>();
                    OfferManager.getInstance().getAllOffers(FragmentProductos.this);
                } else {
                    productos=new ArrayList<>();
                    ProductManager.getInstance().getProductsByTipo(FragmentProductos.this, tiposList.get(i-2).getNombre());
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void Update(){
    ProductoAdapter pa = new ProductoAdapter(this.getContext(), productos);
    llista.setAdapter(pa);
    }

    @Override
    public void onFailure(Throwable t) {

    }

    public class ProductoAdapter extends BaseAdapter {
        private Context context;
        private List<Object> products;
        public ProductoAdapter(Context context, List<Object> products){
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
            int id=  0;
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
                String description = producto.getDescripcion()+"";
                String price = producto.getPrecio()+"";
                String image = producto.getImagen()+"";

                holder.tvNombre.setText(nombre);
                holder.tvDescription.setText(description);
                holder.tvPrice.setText(price);
            } else {
                Oferta oferta = (Oferta) products.get(position);
                String nombre = oferta.getNombre() + "";
                String description = oferta.getDescripcion()+"";
                String price = oferta.getPrecio()+"";
                String image = oferta.getImagen()+"";

                holder.tvNombre.setText(nombre);
                holder.tvDescription.setText(description);
                holder.tvPrice.setText(price);
            }
            return myView;
        }
    }

    public class SpinnerAdapter extends BaseAdapter {
        private Context context;
        private List<String> tipos;
        public SpinnerAdapter(Context context, List<String> tipos){
            this.context=context;
            this.tipos=tipos;
        }
        @Override
        public int getCount() {
            return tipos.size();
        }

        @Override
        public Object getItem(int position) {
            return tipos.get(position);
        }

        @Override
        public long getItemId(int position) {
            int id=  0;
            return id;
        }

        public class ViewHolder{
            public TextView tvNombre;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View myView = convertView;
            if (myView == null) {

                LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
                myView = inflater.inflate(R.layout.spinner, parent, false);
                FragmentProductos.SpinnerAdapter.ViewHolder holder = new FragmentProductos.SpinnerAdapter.ViewHolder();
                holder.tvNombre = (TextView) myView.findViewById(R.id.nombretipo);
                myView.setTag(holder);
            }
            FragmentProductos.SpinnerAdapter.ViewHolder holder = (FragmentProductos.SpinnerAdapter.ViewHolder) myView.getTag();

            holder.tvNombre.setText(tipos.get(position));


            return myView;
        }
    }
}
package com.bakery.dam.androidtpv.controller.activities.main;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bakery.dam.androidtpv.R;
import com.bakery.dam.androidtpv.controller.activities.CreacionTicketActivity;
import com.bakery.dam.androidtpv.controller.activities.CrearTicketActivity;
import com.bakery.dam.androidtpv.controller.activities.Dialogs.TicketDialog;
import com.bakery.dam.androidtpv.controller.activities.login.LoginActivity;
import com.bakery.dam.androidtpv.controller.managers.TicketCallback;
import com.bakery.dam.androidtpv.controller.managers.TicketManager;
import com.bakery.dam.androidtpv.controller.services.TicketService;
import com.bakery.dam.androidtpv.model.Ticket;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.swipe.SwipeLayout;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Retrofit;

public class MenuActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, TicketCallback {
    private RecyclerView recyclerView;

    private List<Ticket> tickets;
    private ListView llista;
    private SwipeRefreshLayout swipeRefreshLayout;
    long id;
    private LinearLayout linearLayout;
    private List<Integer> imgs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        linearLayout = (LinearLayout) findViewById(R.id.cargarmenu);
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

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                tickets = new ArrayList<>();
                TicketManager.getInstance().getAllTickets(MenuActivity.this);
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        llista= (ListView) findViewById(R.id.llista);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        TicketManager.getInstance().getAllTickets(MenuActivity.this);
        swipeRefreshLayout.setVisibility(View.INVISIBLE);
        linearLayout.setVisibility(View.VISIBLE);
    }

    /*@Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }*/

    @Override
    public void onSuccessTicket(final Object ticket) {
        if(ticket!=null) {
            tickets = (List<Ticket>) ticket;
            llista.setAdapter(new MenuActivity.PartsAdapter(this, tickets));
            llista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    if (tickets.get(i).getMesa()!=null) {
                        Intent intent = new Intent(MenuActivity.this, ProductListActivity.class);
                        intent.putExtra("id", (long) tickets.get(i).getId());
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(MenuActivity.this, CrearTicketActivity.class);
                        startActivity(intent);
                    }
                }
            });
        } else {
            Intent intent = new Intent(MenuActivity.this, MenuActivity.class);
            startActivity(intent);
        }
        swipeRefreshLayout.setRefreshing(false);
        swipeRefreshLayout.setVisibility(View.VISIBLE);
        linearLayout.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onSuccessDelete(Object o) {

    }


    @Override
    public void onFailure(Throwable t) {

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
            int id= (int) 0;
            return id;
        }

        public class ViewHolder{
            public ImageView tvMesa;
            public TextView tvFecha;
            public TextView tvPrecio;
            public ImageView ivImage;
            public ImageView ivImage2;
            public SwipeLayout swipeLayout;
            public Button delete;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (tickets.get(position).getMesa() != null) {
                View myView = convertView;
                    //Inflo la lista con el layout que he creado (llista_item)
                    LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
                    myView = inflater.inflate(R.layout.llista_item, parent, false);
                    MenuActivity.PartsAdapter.ViewHolder holder = new MenuActivity.PartsAdapter.ViewHolder();
                    holder.tvMesa = (ImageView) myView.findViewById(R.id.mesa);
                    holder.tvFecha = (TextView) myView.findViewById(R.id.fecha);
                    holder.tvPrecio = (TextView) myView.findViewById(R.id.precio);
                    holder.ivImage2 = (ImageView) myView.findViewById(R.id.imageView2);
                    holder.ivImage2.setImageResource(R.drawable.eurocoin);
                    holder.swipeLayout = (SwipeLayout) myView.findViewById(R.id.swipe);
                    if(position%2==0){
                        myView.setBackgroundColor(Color.rgb(255, 246 ,238));
                    }
                    else {
                        myView.setBackgroundColor(Color.rgb(255, 255 ,255));
                    }
                    holder.ivImage = (ImageView) myView.findViewById(R.id.table);

                    myView.setTag(holder);

                    holder.swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
                        @Override
                        public void onClose(SwipeLayout layout) {
                        }
                        @Override
                        public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {
                        }
                        @Override
                        public void onStartOpen(SwipeLayout layout) {

                        }
                        @Override
                        public void onOpen(final SwipeLayout layout) {

                            YoYo.with(Techniques.Tada).duration(500).delay(100).playOn(layout.findViewById(R.id.trash));
                            id = (long) tickets.get(position).getId();
                            TicketManager.getInstance().deleteTicket(MenuActivity.this, (long) tickets.get(position).getId());

                        }
                        @Override
                        public void onStartClose(SwipeLayout layout) {
                        }

                        @Override
                        public void onHandRelease(final SwipeLayout layout, final float xvel, final float yvel) {
                        }
                    });



                //Voy asignando los datos
                Ticket ticket = tickets.get(position);
                Date date=new Date();
                String mesa = ticket.getMesa() + "";
                String precio = ticket.getCantidad() + "";
                String fecha = ticket.getFecha() + "";
                fecha=fecha.substring(11, 16);
                holder.tvMesa.setImageResource(imgs.get(Integer.parseInt(mesa)));
                if (!"0".equals(mesa)) {

                    holder.ivImage.setImageResource(R.drawable.local);

                } else {
                    holder.ivImage.setImageResource(R.drawable.llevar);

                }
                holder.ivImage2.setImageResource(R.drawable.eurocoin);
                holder.tvPrecio.setText(precio);
                holder.tvFecha.setText(fecha);
                if(position%2==0){
                    myView.setBackgroundColor(Color.rgb(255, 246 ,238));
                } else {
                    myView.setBackgroundColor(Color.rgb(255, 255 ,255));
                }
                return myView;

            } else {
                View myView;

                LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
                myView = inflater.inflate(R.layout.item_add, parent, false);
                MenuActivity.PartsAdapter.ViewHolder holder = new MenuActivity.PartsAdapter.ViewHolder();
                holder.tvMesa = (ImageView) myView.findViewById(R.id.add);
                myView.setTag(holder);
                holder.tvMesa.setImageResource(R.drawable.addrojo);
                return myView;
            }
        }

    }
}

package com.example.pr_idi.mydatabaseexample;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.List;

public class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    FilmData filmData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_base);
        setView();
        filmData = new FilmData(this);
        filmData.open();
    }

    protected void setView() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){

        @Override
        public void onDrawerClosed(View drawerView) {
            super.onDrawerClosed(drawerView);
        }

        @Override
        public void onDrawerOpened(View drawerView) {
            super.onDrawerOpened(drawerView);
            InputMethodManager inputMethodManager = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    };
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id){
            case R.id.inici:
                startActivity(new Intent(getApplicationContext(),Inici.class));
                break;
            case R.id.dades:
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                break;
            case R.id.buscar:
                startActivity(new Intent(getApplicationContext(),Buscar.class));
                break;
            case R.id.crear:
                startActivity(new Intent(getApplicationContext(),Crear.class));
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.base, menu);
        return true;
    }

    @Override
    public  boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.buscarmenu:
                startActivity(new Intent(getApplicationContext(),Buscar.class));
                break;
            case R.id.crearmenu:
                startActivity(new Intent(getApplicationContext(),Crear.class));
                break;
            case R.id.reset:
                List<Film> films = filmData.getAllFilms();
                for (Film f : films ){
                    filmData.deleteFilm(f);
                }
                filmData.createFilm("Blade Runner","Ridley Scoot","EUA",1982, "Harrison Ford", 8);
                filmData.createFilm("Rocky Horror Picture Show","Jim Sharman","UK",1975,"Tim Curry",5);
                filmData.createFilm("The Godfather","Francis Ford Coppla","EUA",1972,"Al Pacino",7);
                filmData.createFilm("Toy Story","John Lasseter","EUA",1995,"Tom Hanks",10);
                CharSequence text = "S'ha reinicialitzat la base de dades";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(getApplicationContext(),text,duration);
                toast.show();
                if (!getClass().isAssignableFrom(PeliRateDelete.class)) {
                    System.out.println("HOLA");
                    Intent refresh = new Intent(this, getClass());
                    startActivity(refresh);
                }else {
                    Intent refresh = new Intent(this, getClass());
                    refresh.putExtra("id", PeliRateDelete.peli.getTitle());
                    startActivity(refresh);
                }
                break;
            case R.id.about:
                startActivity(new Intent(getApplicationContext(),Info.class));
                break;
            case R.id.help:
                startActivity(new Intent(getApplicationContext(),Ajuda.class));
                break;
        }
        return true;
    }

    @Override
    public void setContentView(int layoutResID) {

        DrawerLayout fullLayout = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
        FrameLayout frameLayout = (FrameLayout) fullLayout.findViewById(R.id.frame_layout_base);

        getLayoutInflater().inflate(layoutResID, frameLayout, true);

        super.setContentView(fullLayout);
        setView();
    }
}

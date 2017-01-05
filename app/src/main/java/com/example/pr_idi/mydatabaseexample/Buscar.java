package com.example.pr_idi.mydatabaseexample;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.LinkedList;
import java.util.List;

public class Buscar extends BaseActivity {
    FilmData filmData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buscar);
        setTitle("Buscar");

        filmData = new FilmData(this);
        filmData.open();

        SearchView sv = (SearchView) findViewById(R.id.searchview);
        //ArrayAdapter<Film> adapter;// = (ArrayAdapter<Film>) lv.getAdapter();
        sv.setQueryHint("Buscar aquí...");
        sv.setIconifiedByDefault(false);
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!callSearch(query)){
                    List<String> notfound = new LinkedList<>();
                    notfound.add("No s'han trobat pelicules on hi participi aquest actor o que tinguin aquest titol");
                    final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1,notfound);
                    ListView lv = (ListView) findViewById(R.id.listbusca);
                    lv.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                callSearch(query);
                return true;
            }

            public boolean callSearch(String query) {

                int s = query.length();

                List<Film> values = filmData.getAllFilms();
                List<Film> res = new LinkedList<>();
                for (Film f : values) {
                    if (s > 0){
                        String t = f.getProtagonist();
                    String n = f.getTitle();
                    if (s < t.length()) t = t.substring(0, s);
                    if (s < n.length()) n = n.substring(0, s);
                    if (t.equalsIgnoreCase(query) || n.equalsIgnoreCase(query)) res.add(f);
                    }
                }
                final ArrayAdapter<Film> adapter = new ArrayAdapter<Film>(getBaseContext(), android.R.layout.simple_list_item_1, res);
                ListView lv = (ListView) findViewById(R.id.listbusca);
                lv.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                if (!res.isEmpty()) return true;
                else return false;
            }
        });

        ListView lv = (ListView) findViewById(R.id.listbusca);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(getApplicationContext(),PeliRateDelete.class));
            }
        });
    }


/*

    public void onClick(View view) {
        @SuppressWarnings("unchecked")
        SearchView sv = (SearchView) findViewById(R.id.searchview);
        ListView lv = (ListView) findViewById(R.id.listbusca);
        ArrayAdapter<Film> adapter = (ArrayAdapter<Film>) lv.getAdapter();
        switch (view.getId()){
            case R.id.buscabut:
                String nom = sv.getQuery().toString();
                int s = nom.length();

                List<Film> values = filmData.getAllFilms();
                List<Film> res = new LinkedList<>();
                for (Film f : values){
                    String t = f.getProtagonist();
                    String n = f.getTitle();
                    if (s < t.length()) t = t.substring(0,s);
                    if (s < n.length()) n = n.substring(0,s);
                    if (t.equalsIgnoreCase(nom) || n.equalsIgnoreCase(nom)) res.add(f);

                }
                adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, res);
                lv.setAdapter(adapter);
                break;
        }
        adapter.notifyDataSetChanged();
    }*/
}

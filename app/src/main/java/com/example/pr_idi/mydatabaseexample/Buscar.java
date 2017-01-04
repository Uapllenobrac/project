package com.example.pr_idi.mydatabaseexample;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.SearchView;

import java.lang.reflect.Array;
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
    }


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
                    if (t.equalsIgnoreCase(nom)) res.add(f);


                    if (s < n.length()) n = n.substring(0,s);
                    if (n.equalsIgnoreCase(nom)) res.add(f);

                }
                adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, res);
                lv.setAdapter(adapter);
                break;
        }
        adapter.notifyDataSetChanged();
    }
}

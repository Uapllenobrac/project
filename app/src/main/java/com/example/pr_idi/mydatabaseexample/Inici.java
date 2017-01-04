package com.example.pr_idi.mydatabaseexample;


import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Inici extends BaseActivity {
    private FilmData filmData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inici);
        setTitle("Inici");

        filmData = new FilmData(this);
        filmData.open();

        List<Film> values = filmData.getAllFilms();
        Collections.sort(values, new Comparator<Film>() {
            @Override
            public int compare(Film f1, Film f2) {
                return f1.getTitle().compareTo(f2.getTitle());
            }
        });

        // use the SimpleCursorAdapter to show the
        // elements in a ListView
        ArrayAdapter<Film> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, values);

        ListView lv = (ListView) findViewById(R.id.titlelist);
        lv.setAdapter(adapter);
    }
}

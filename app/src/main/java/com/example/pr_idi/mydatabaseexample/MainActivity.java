package com.example.pr_idi.mydatabaseexample;


import java.util.List;
import java.util.Random;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends BaseActivity {
    private FilmData filmData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        filmData = new FilmData(this);
        filmData.open();

        List<Film> values = filmData.getAllFilms();

        // use the SimpleCursorAdapter to show the
        // elements in a ListView
        ArrayAdapter<Film> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, values);

        ListView lv = (ListView) findViewById(R.id.listmain);
        lv.setAdapter(adapter);
    }

    // Will be called via the onClick attribute
    // of the buttons in main.xml
    public void onClick(View view) {
        @SuppressWarnings("unchecked")

        ListView lv = (ListView) findViewById(R.id.listmain);
        ArrayAdapter<Film> adapter = (ArrayAdapter<Film>) lv.getAdapter();
        Film film;
        switch (view.getId()) {
            case R.id.add:
                String[] newFilm = new String[] { "Blade Runner", "Ridley Scott", "Rocky Horror Picture Show", "Jim Sharman", "The Godfather", "Francis Ford Coppola", "Toy Story", "John Lasseter" };
                String[] actors = new String[]{"Harrison Ford","Tim Curry","Al Pacino","Tom Hanks"};
                String[] countrys = new String[]{"EUA","UK","EUA","EUA"};
                int[] years = new int[]{1982,1975,1972,1995};
                int[] rates = new int[]{8,7,8,10};
                int nextInt = new Random().nextInt(4);
                // save the new film to the database
                film = filmData.createFilm(newFilm[nextInt*2], newFilm[nextInt*2 + 1],countrys[nextInt],years[nextInt],actors[nextInt],rates[nextInt]);
                adapter.add(film);
                break;
            case R.id.delete:
                if (lv.getAdapter().getCount() > 0) {
                    film = (Film) lv.getAdapter().getItem(lv.getAdapter().getCount()-1);
                    filmData.deleteFilm(film);
                    adapter.remove(film);
                }
                break;
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        filmData.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        filmData.close();
        super.onPause();
    }

}